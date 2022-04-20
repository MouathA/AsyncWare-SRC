package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIPlay extends EntityAIBase
{
    private EntityLivingBase targetVillager;
    private double speed;
    private int playTime;
    private EntityVillager villagerObj;
    
    @Override
    public void resetTask() {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }
    
    @Override
    public void updateTask() {
        --this.playTime;
        if (this.targetVillager != null) {
            if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0) {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
            }
        }
        else if (this.villagerObj.getNavigator().noPath()) {
            final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            if (randomTarget == null) {
                return;
            }
            this.villagerObj.getNavigator().tryMoveToXYZ(randomTarget.xCoord, randomTarget.yCoord, randomTarget.zCoord, this.speed);
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() >= 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(400) != 0) {
            return false;
        }
        final List entitiesWithinAABB = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0, 3.0, 6.0));
        double n = Double.MAX_VALUE;
        for (final EntityVillager targetVillager : entitiesWithinAABB) {
            if (targetVillager != this.villagerObj && !targetVillager.isPlaying() && targetVillager.getGrowingAge() < 0) {
                final double distanceSqToEntity = targetVillager.getDistanceSqToEntity(this.villagerObj);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                this.targetVillager = targetVillager;
            }
        }
        return this.targetVillager != null || RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3) != null;
    }
    
    @Override
    public void startExecuting() {
        if (this.targetVillager != null) {
            this.villagerObj.setPlaying(true);
        }
        this.playTime = 1000;
    }
    
    public EntityAIPlay(final EntityVillager villagerObj, final double speed) {
        this.villagerObj = villagerObj;
        this.speed = speed;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean continueExecuting() {
        return this.playTime > 0;
    }
}
