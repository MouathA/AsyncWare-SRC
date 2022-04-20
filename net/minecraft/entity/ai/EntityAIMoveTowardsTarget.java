package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
    private double movePosY;
    private EntityLivingBase targetEntity;
    private float maxTargetDistance;
    private double speed;
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosZ;
    
    public EntityAIMoveTowardsTarget(final EntityCreature theEntity, final double speed, final float maxTargetDistance) {
        this.theEntity = theEntity;
        this.speed = speed;
        this.maxTargetDistance = maxTargetDistance;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        this.targetEntity = this.theEntity.getAttackTarget();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > this.maxTargetDistance * this.maxTargetDistance) {
            return false;
        }
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
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
        return !this.theEntity.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity(this.theEntity) < this.maxTargetDistance * this.maxTargetDistance;
    }
    
    @Override
    public void resetTask() {
        this.targetEntity = null;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
    }
}
