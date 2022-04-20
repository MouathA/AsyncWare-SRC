package com.nquantum.event.impl;

import com.nquantum.event.*;
import net.minecraft.network.*;

public class EventReceivePacket extends Event
{
    private Packet packet;
    
    public EventReceivePacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
