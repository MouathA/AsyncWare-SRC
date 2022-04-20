package com.nquantum.event.impl;

import com.nquantum.event.*;

public class EventPostMotionUpdate extends Event
{
    public double x;
    public boolean ground;
    public float pitch;
    public float yaw;
    public double y;
    public double z;
    
    public EventPostMotionUpdate(final float yaw, final float pitch, final boolean ground, final double x, final double y, final double z) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public boolean onGround() {
        return this.ground;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setGround(final boolean ground) {
        this.ground = ground;
    }
    
    public float getYaw() {
        return this.yaw;
    }
}
