package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movementSpeed;
    private double movePosZ;
    private double movePosY;
    
    public EntityAIMoveTowardsRestriction(final EntityCreature theEntity, final double movementSpeed) {
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        }
        final BlockPos homePosition = this.theEntity.getHomePosition();
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(homePosition.getX(), homePosition.getY(), homePosition.getZ()));
        if (randomTargetBlockTowards == null) {
            return false;
        }
        this.movePosX = randomTargetBlockTowards.xCoord;
        this.movePosY = randomTargetBlockTowards.yCoord;
        this.movePosZ = randomTargetBlockTowards.zCoord;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}
