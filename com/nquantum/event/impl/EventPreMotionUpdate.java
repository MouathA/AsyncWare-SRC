package com.nquantum.event.impl;

import com.nquantum.event.*;

public class EventPreMotionUpdate extends Event
{
    public double y;
    private float pitch;
    public double z;
    public double x;
    private float yaw;
    private boolean ground;
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setGround(final boolean ground) {
        this.ground = ground;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public EventPreMotionUpdate(final float yaw, final float pitch, final boolean ground, final double x, final double y, final double z) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean onGround() {
        return this.ground;
    }
    
    public float getPitch() {
        return this.pitch;
    }
}
