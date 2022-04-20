package com.nquantum.event.impl;

import com.nquantum.event.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EventCollide extends Event
{
    private double posX;
    private double posZ;
    private Entity entity;
    private Block block;
    private AxisAlignedBB boundingBox;
    private double posY;
    
    public double getPosY() {
        return this.posY;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public EventCollide(final Entity entity, final double posX, final double posY, final double posZ, final AxisAlignedBB boundingBox, final Block block) {
        this.entity = entity;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.boundingBox = boundingBox;
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
