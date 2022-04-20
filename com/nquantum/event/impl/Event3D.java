package com.nquantum.event.impl;

import com.nquantum.event.*;

public class Event3D extends Event
{
    public float partialTicks;
    
    public Event3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
