package net.minecraft.world;

import net.minecraft.util.*;

public class ChunkCoordIntPair
{
    public final int chunkXPos;
    public final int chunkZPos;
    private int cachedHashCode;
    private static final String __OBFID = "CL_00000133";
    
    public int getXEnd() {
        return (this.chunkXPos << 4) + 15;
    }
    
    public int getZStart() {
        return this.chunkZPos << 4;
    }
    
    public BlockPos getBlock(final int n, final int n2, final int n3) {
        return new BlockPos((this.chunkXPos << 4) + n, n2, (this.chunkZPos << 4) + n3);
    }
    
    public BlockPos getCenterBlock(final int n) {
        return new BlockPos(this.getCenterXPos(), n, this.getCenterZPosition());
    }
    
    public ChunkCoordIntPair(final int chunkXPos, final int chunkZPos) {
        this.cachedHashCode = 0;
        this.chunkXPos = chunkXPos;
        this.chunkZPos = chunkZPos;
    }
    
    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }
    
    public int getZEnd() {
        return (this.chunkZPos << 4) + 15;
    }
    
    @Override
    public int hashCode() {
        if (this.cachedHashCode == 0) {
            this.cachedHashCode = (1664525 * this.chunkXPos + 1013904223 ^ 1664525 * (this.chunkZPos ^ 0xDEADBEEF) + 1013904223);
        }
        return this.cachedHashCode;
    }
    
    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }
    
    @Override
    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
    
    public int getXStart() {
        return this.chunkXPos << 4;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChunkCoordIntPair)) {
            return false;
        }
        final ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)o;
        return this.chunkXPos == chunkCoordIntPair.chunkXPos && this.chunkZPos == chunkCoordIntPair.chunkZPos;
    }
    
    public static long chunkXZ2Int(final int n, final int n2) {
        return ((long)n & 0xFFFFFFFFL) | ((long)n2 & 0xFFFFFFFFL) << 32;
    }
}
