package com.nquantum.event.impl;

import com.nquantum.event.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EventBoundingBox extends Event
{
    public AxisAlignedBB boundingBox;
    private double y;
    private Block block;
    private double z;
    private double x;
    
    public BlockPos getBlockPos(final BlockPos blockPos) {
        return blockPos;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public double getX() {
        return this.x;
    }
    
    public BlockPos getBlockPos() {
        return this.getBlockPos(new BlockPos(this.x, this.y, this.z));
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public EventBoundingBox(final AxisAlignedBB boundingBox, final Block block, final double x, final double y, final double z) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
