package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import java.util.regex.*;

public class ChatComponentTranslation extends ChatComponentStyle
{
    List children;
    private final String key;
    private final Object[] formatArgs;
    public static final Pattern stringVariablePattern;
    private long lastTranslationUpdateTimeInMilliseconds;
    private final Object syncLock;
    
    @Override
    public String getUnformattedTextForChat() {
        this.ensureInitialized();
        final StringBuilder sb = new StringBuilder();
        final Iterator<IChatComponent> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getUnformattedTextForChat());
        }
        return sb.toString();
    }
    
    @Override
    public IChatComponent setChatStyle(final ChatStyle chatStyle) {
        super.setChatStyle(chatStyle);
        final Object[] formatArgs = this.formatArgs;
        while (0 < formatArgs.length) {
            final Object o = formatArgs[0];
            if (o instanceof IChatComponent) {
                ((IChatComponent)o).getChatStyle().setParentStyle(this.getChatStyle());
            }
            int n = 0;
            ++n;
        }
        if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
            final Iterator<IChatComponent> iterator = this.children.iterator();
            while (iterator.hasNext()) {
                iterator.next().getChatStyle().setParentStyle(chatStyle);
            }
        }
        return this;
    }
    
    public ChatComponentTranslation(final String key, final Object... formatArgs) {
        this.syncLock = new Object();
        this.lastTranslationUpdateTimeInMilliseconds = -1L;
        this.children = Lists.newArrayList();
        this.key = key;
        this.formatArgs = formatArgs;
        while (0 < formatArgs.length) {
            final Object o = formatArgs[0];
            if (o instanceof IChatComponent) {
                ((IChatComponent)o).getChatStyle().setParentStyle(this.getChatStyle());
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * super.hashCode() + this.key.hashCode()) + Arrays.hashCode(this.formatArgs);
    }
    
    private IChatComponent getFormatArgumentAsComponent(final int n) {
        if (n >= this.formatArgs.length) {
            throw new ChatComponentTranslationFormatException(this, n);
        }
        final Object o = this.formatArgs[n];
        IChatComponent chatComponent;
        if (o instanceof IChatComponent) {
            chatComponent = (IChatComponent)o;
        }
        else {
            chatComponent = new ChatComponentText((o == null) ? "null" : o.toString());
            chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
        }
        return chatComponent;
    }
    
    synchronized void ensureInitialized() {
        // monitorenter(syncLock = this.syncLock)
        final long lastTranslationUpdateTimeInMilliseconds = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
        if (lastTranslationUpdateTimeInMilliseconds == this.lastTranslationUpdateTimeInMilliseconds) {
            // monitorexit(syncLock)
            return;
        }
        this.lastTranslationUpdateTimeInMilliseconds = lastTranslationUpdateTimeInMilliseconds;
        this.children.clear();
        // monitorexit(syncLock)
        this.initializeFromFormat(StatCollector.translateToLocal(this.key));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatComponentTranslation)) {
            return false;
        }
        final ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation)o;
        return Arrays.equals(this.formatArgs, chatComponentTranslation.formatArgs) && this.key.equals(chatComponentTranslation.key) && super.equals(o);
    }
    
    @Override
    public ChatComponentTranslation createCopy() {
        final Object[] array = new Object[this.formatArgs.length];
        while (0 < this.formatArgs.length) {
            if (this.formatArgs[0] instanceof IChatComponent) {
                array[0] = ((IChatComponent)this.formatArgs[0]).createCopy();
            }
            else {
                array[0] = this.formatArgs[0];
            }
            int n = 0;
            ++n;
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(this.key, array);
        chatComponentTranslation.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentTranslation.appendSibling(iterator.next().createCopy());
        }
        return chatComponentTranslation;
    }
    
    @Override
    public Iterator iterator() {
        this.ensureInitialized();
        return Iterators.concat(ChatComponentStyle.createDeepCopyIterator(this.children), ChatComponentStyle.createDeepCopyIterator(this.siblings));
    }
    
    public Object[] getFormatArgs() {
        return this.formatArgs;
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    @Override
    public String toString() {
        return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    static {
        stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    }
    
    protected void initializeFromFormat(final String s) {
        final Matcher matcher = ChatComponentTranslation.stringVariablePattern.matcher(s);
        while (matcher.find(0)) {
            final int start = matcher.start();
            final int end = matcher.end();
            if (start > 0) {
                final ChatComponentText chatComponentText = new ChatComponentText(String.format(s.substring(0, start), new Object[0]));
                chatComponentText.getChatStyle().setParentStyle(this.getChatStyle());
                this.children.add(chatComponentText);
            }
            final String group = matcher.group(2);
            final String substring = s.substring(start, end);
            if ("%".equals(group) && "%%".equals(substring)) {
                final ChatComponentText chatComponentText2 = new ChatComponentText("%");
                chatComponentText2.getChatStyle().setParentStyle(this.getChatStyle());
                this.children.add(chatComponentText2);
            }
            else {
                if (!"s".equals(group)) {
                    throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + substring + "'");
                }
                final String group2 = matcher.group(1);
                int n;
                if (group2 != null) {
                    n = Integer.parseInt(group2) - 1;
                }
                else {
                    n = 0;
                    int n2 = 0;
                    ++n2;
                }
                final int n3 = n;
                if (n3 >= this.formatArgs.length) {
                    continue;
                }
                this.children.add(this.getFormatArgumentAsComponent(n3));
            }
        }
        if (0 < s.length()) {
            final ChatComponentText chatComponentText3 = new ChatComponentText(String.format(s.substring(0), new Object[0]));
            chatComponentText3.getChatStyle().setParentStyle(this.getChatStyle());
            this.children.add(chatComponentText3);
        }
    }
    
    public String getKey() {
        return this.key;
    }
}
