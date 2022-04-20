package com.nquantum.event.impl;

import com.nquantum.event.*;

public class Event2D extends Event
{
    private float height;
    private float width;
    
    public float getHeight() {
        return this.height;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public Event2D(final float width, final float height) {
        this.width = width;
        this.height = height;
    }
}
