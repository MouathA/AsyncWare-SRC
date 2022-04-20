package com.nquantum.event.impl;

import com.nquantum.event.*;

public class EventKey extends Event
{
    private int key;
    
    public EventKey(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
