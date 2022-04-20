package net.minecraft.client.gui;

import net.minecraft.util.*;

public class ChatLine
{
    private final IChatComponent lineString;
    private final int chatLineID;
    private final int updateCounterCreated;
    
    public IChatComponent getChatComponent() {
        return this.lineString;
    }
    
    public ChatLine(final int updateCounterCreated, final IChatComponent lineString, final int chatLineID) {
        this.lineString = lineString;
        this.updateCounterCreated = updateCounterCreated;
        this.chatLineID = chatLineID;
    }
    
    public int getChatLineID() {
        return this.chatLineID;
    }
    
    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }
}
