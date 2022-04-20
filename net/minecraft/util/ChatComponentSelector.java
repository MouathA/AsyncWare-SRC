package net.minecraft.util;

import java.util.*;

public class ChatComponentSelector extends ChatComponentStyle
{
    private final String selector;
    
    @Override
    public String toString() {
        return "SelectorComponent{pattern='" + this.selector + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ChatComponentSelector && this.selector.equals(((ChatComponentSelector)o).selector) && super.equals(o));
    }
    
    public ChatComponentSelector(final String selector) {
        this.selector = selector;
    }
    
    public String getSelector() {
        return this.selector;
    }
    
    @Override
    public String getUnformattedTextForChat() {
        return this.selector;
    }
    
    @Override
    public ChatComponentSelector createCopy() {
        final ChatComponentSelector chatComponentSelector = new ChatComponentSelector(this.selector);
        chatComponentSelector.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentSelector.appendSibling(iterator.next().createCopy());
        }
        return chatComponentSelector;
    }
}
