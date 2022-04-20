package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class DestroyBlockProgress
{
    private final int miningPlayerEntId;
    private final BlockPos position;
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;
    
    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }
    
    public void setPartialBlockDamage(final int n) {
        this.partialBlockProgress = 10;
    }
    
    public void setCloudUpdateTick(final int createdAtCloudUpdateTick) {
        this.createdAtCloudUpdateTick = createdAtCloudUpdateTick;
    }
    
    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public DestroyBlockProgress(final int miningPlayerEntId, final BlockPos position) {
        this.miningPlayerEntId = miningPlayerEntId;
        this.position = position;
    }
}
