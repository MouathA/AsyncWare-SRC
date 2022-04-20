package com.nquantum.util.raytrace;

import net.minecraft.util.*;

public class Node
{
    private boolean vclipableUp;
    public double fCost;
    public double hCost;
    public double gCost;
    private BlockPos vclipPosUp;
    private BlockPos vclipPosDown;
    private BlockPos blockPos;
    private boolean vclipableDown;
    private boolean walkable;
    private static final double range14 = 1.4142135623730951;
    private static final double range17 = 1.7320508075688772;
    private static final double range10 = 1.0;
    public int id;
    public Node parent;
    
    public boolean isVclipableDown() {
        return this.vclipableDown;
    }
    
    public BlockPos getVclipPosUp() {
        return this.vclipPosUp;
    }
    
    public Node setId(final int id) {
        this.id = id;
        return this;
    }
    
    public void setVclipPosUp(final BlockPos vclipPosUp) {
        this.vclipPosUp = vclipPosUp;
    }
    
    public void setVclipableUp(final boolean vclipableUp) {
        this.vclipableUp = vclipableUp;
    }
    
    public BlockPos getVclipPosDown() {
        return this.vclipPosDown;
    }
    
    public static double distance(final BlockPos blockPos, final BlockPos blockPos2) {
        final int n = blockPos.getX() - blockPos2.getX();
        final int n2 = blockPos.getY() - blockPos2.getY();
        final int n3 = blockPos.getZ() - blockPos2.getZ();
        return Math.sqrt(n * n + n2 * n2 + n3 * n3);
    }
    
    public void setVclipPosDown(final BlockPos vclipPosDown) {
        this.vclipPosDown = vclipPosDown;
    }
    
    public boolean isVclipableUp() {
        return this.vclipableUp;
    }
    
    public boolean isWalkable() {
        return this.walkable;
    }
    
    public void setVclipableDown(final boolean vclipableDown) {
        this.vclipableDown = vclipableDown;
    }
    
    public Node(final boolean walkable, final BlockPos blockPos) {
        this.id = 0;
        this.walkable = walkable;
        this.blockPos = blockPos;
    }
    
    public BlockPos getBlockpos() {
        return this.blockPos;
    }
}
