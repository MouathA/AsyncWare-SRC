package com.nquantum.event.impl;

import com.nquantum.event.*;
import net.minecraft.network.*;

public class EventSendPacket extends Event
{
    private Packet packet;
    
    public EventSendPacket(final Packet packet) {
        this.packet = null;
        this.setPacket(packet);
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
