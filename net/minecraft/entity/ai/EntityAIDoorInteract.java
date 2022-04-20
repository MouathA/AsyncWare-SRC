package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    float entityPositionZ;
    protected BlockDoor doorBlock;
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    protected BlockPos doorPosition;
    protected EntityLiving theEntity;
    
    @Override
    public void updateTask() {
        if (this.entityPositionX * (float)(this.doorPosition.getX() + 0.5f - this.theEntity.posX) + this.entityPositionZ * (float)(this.doorPosition.getZ() + 0.5f - this.theEntity.posZ) < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return false;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final PathEntity path = pathNavigateGround.getPath();
        if (path != null && !path.isFinished() && pathNavigateGround.getEnterDoors()) {
            while (0 < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength())) {
                final PathPoint pathPointFromIndex = path.getPathPointFromIndex(0);
                this.doorPosition = new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord + 1, pathPointFromIndex.zCoord);
                if (this.theEntity.getDistanceSq(this.doorPosition.getX(), this.theEntity.posY, this.doorPosition.getZ()) <= 2.25) {
                    this.doorBlock = this.getBlockDoor(this.doorPosition);
                    if (this.doorBlock != null) {
                        return true;
                    }
                }
                int n = 0;
                ++n;
            }
            this.doorPosition = new BlockPos(this.theEntity).up();
            this.doorBlock = this.getBlockDoor(this.doorPosition);
            return this.doorBlock != null;
        }
        return false;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }
    
    private BlockDoor getBlockDoor(final BlockPos blockPos) {
        final Block block = this.theEntity.worldObj.getBlockState(blockPos).getBlock();
        return (block instanceof BlockDoor && block.getMaterial() == Material.wood) ? ((BlockDoor)block) : null;
    }
    
    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)(this.doorPosition.getX() + 0.5f - this.theEntity.posX);
        this.entityPositionZ = (float)(this.doorPosition.getZ() + 0.5f - this.theEntity.posZ);
    }
    
    public EntityAIDoorInteract(final EntityLiving theEntity) {
        this.doorPosition = BlockPos.ORIGIN;
        this.theEntity = theEntity;
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }
}
