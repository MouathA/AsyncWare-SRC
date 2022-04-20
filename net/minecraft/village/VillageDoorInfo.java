package net.minecraft.village;

import net.minecraft.util.*;

public class VillageDoorInfo
{
    private final BlockPos doorBlockPos;
    private boolean isDetachedFromVillageFlag;
    private final EnumFacing insideDirection;
    private int doorOpeningRestrictionCounter;
    private final BlockPos insideBlock;
    private int lastActivityTimestamp;
    
    public void func_179849_a(final int lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    public int getInsideOffsetZ() {
        return this.insideDirection.getFrontOffsetZ() * 2;
    }
    
    public int getInsideOffsetX() {
        return this.insideDirection.getFrontOffsetX() * 2;
    }
    
    public boolean func_179850_c(final BlockPos blockPos) {
        return (blockPos.getX() - this.doorBlockPos.getX()) * this.insideDirection.getFrontOffsetX() + (blockPos.getZ() - this.doorBlockPos.getY()) * this.insideDirection.getFrontOffsetZ() >= 0;
    }
    
    public BlockPos getDoorBlockPos() {
        return this.doorBlockPos;
    }
    
    public int getDistanceSquared(final int n, final int n2, final int n3) {
        return (int)this.doorBlockPos.distanceSq(n, n2, n3);
    }
    
    private static EnumFacing getFaceDirection(final int n, final int n2) {
        return (n < 0) ? EnumFacing.WEST : ((n > 0) ? EnumFacing.EAST : ((n2 < 0) ? EnumFacing.NORTH : EnumFacing.SOUTH));
    }
    
    public VillageDoorInfo(final BlockPos doorBlockPos, final EnumFacing insideDirection, final int lastActivityTimestamp) {
        this.doorBlockPos = doorBlockPos;
        this.insideDirection = insideDirection;
        this.insideBlock = doorBlockPos.offset(insideDirection, 2);
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    public VillageDoorInfo(final BlockPos blockPos, final int n, final int n2, final int n3) {
        this(blockPos, getFaceDirection(n, n2), n3);
    }
    
    public BlockPos getInsideBlockPos() {
        return this.insideBlock;
    }
    
    public int getInsidePosY() {
        return this.lastActivityTimestamp;
    }
    
    public boolean getIsDetachedFromVillageFlag() {
        return this.isDetachedFromVillageFlag;
    }
    
    public int getDistanceToInsideBlockSq(final BlockPos blockPos) {
        return (int)this.insideBlock.distanceSq(blockPos);
    }
    
    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }
    
    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = 0;
    }
    
    public int getDistanceToDoorBlockSq(final BlockPos blockPos) {
        return (int)blockPos.distanceSq(this.getDoorBlockPos());
    }
    
    public void incrementDoorOpeningRestrictionCounter() {
        ++this.doorOpeningRestrictionCounter;
    }
    
    public void setIsDetachedFromVillageFlag(final boolean isDetachedFromVillageFlag) {
        this.isDetachedFromVillageFlag = isDetachedFromVillageFlag;
    }
}
