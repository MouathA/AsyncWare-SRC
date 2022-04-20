package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public abstract class ChatComponentStyle implements IChatComponent
{
    private ChatStyle style;
    protected List siblings;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatComponentStyle)) {
            return false;
        }
        final ChatComponentStyle chatComponentStyle = (ChatComponentStyle)o;
        return this.siblings.equals(chatComponentStyle.siblings) && this.getChatStyle().equals(chatComponentStyle.getChatStyle());
    }
    
    @Override
    public List getSiblings() {
        return this.siblings;
    }
    
    @Override
    public final String getFormattedText() {
        final StringBuilder sb = new StringBuilder();
        for (final IChatComponent chatComponent : this) {
            sb.append(chatComponent.getChatStyle().getFormattingCode());
            sb.append(chatComponent.getUnformattedTextForChat());
            sb.append(EnumChatFormatting.RESET);
        }
        return sb.toString();
    }
    
    public ChatComponentStyle() {
        this.siblings = Lists.newArrayList();
    }
    
    @Override
    public ChatStyle getChatStyle() {
        if (this.style == null) {
            this.style = new ChatStyle();
            final Iterator<IChatComponent> iterator = this.siblings.iterator();
            while (iterator.hasNext()) {
                iterator.next().getChatStyle().setParentStyle(this.style);
            }
        }
        return this.style;
    }
    
    @Override
    public Iterator iterator() {
        return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatComponentStyle[] { this }), createDeepCopyIterator(this.siblings));
    }
    
    @Override
    public IChatComponent setChatStyle(final ChatStyle style) {
        this.style = style;
        final Iterator<IChatComponent> iterator = this.siblings.iterator();
        while (iterator.hasNext()) {
            iterator.next().getChatStyle().setParentStyle(this.getChatStyle());
        }
        return this;
    }
    
    @Override
    public IChatComponent appendSibling(final IChatComponent chatComponent) {
        chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
        this.siblings.add(chatComponent);
        return this;
    }
    
    @Override
    public String toString() {
        return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
    }
    
    @Override
    public int hashCode() {
        return 31 * this.style.hashCode() + this.siblings.hashCode();
    }
    
    @Override
    public final String getUnformattedText() {
        final StringBuilder sb = new StringBuilder();
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getUnformattedTextForChat());
        }
        return sb.toString();
    }
    
    @Override
    public IChatComponent appendText(final String s) {
        return this.appendSibling(new ChatComponentText(s));
    }
    
    public static Iterator createDeepCopyIterator(final Iterable iterable) {
        return Iterators.transform(Iterators.concat(Iterators.transform((Iterator)iterable.iterator(), (Function)new Function() {
            public Object apply(final Object o) {
                return this.apply((IChatComponent)o);
            }
            
            public Iterator apply(final IChatComponent chatComponent) {
                return chatComponent.iterator();
            }
        })), (Function)new Function() {
            public IChatComponent apply(final IChatComponent chatComponent) {
                final IChatComponent copy = chatComponent.createCopy();
                copy.setChatStyle(copy.getChatStyle().createDeepCopy());
                return copy;
            }
            
            public Object apply(final Object o) {
                return this.apply((IChatComponent)o);
            }
        });
    }
}
