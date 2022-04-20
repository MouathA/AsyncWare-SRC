package net.minecraft.entity.ai;

import net.minecraft.village.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIVillagerMate extends EntityAIBase
{
    private int matingTimeout;
    Village villageObj;
    private EntityVillager mate;
    private World worldObj;
    private EntityVillager villagerObj;
    
    @Override
    public void resetTask() {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating(false);
    }
    
    @Override
    public boolean continueExecuting() {
        return this.matingTimeout >= 0 && this == 0 && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate(false);
    }
    
    public EntityAIVillagerMate(final EntityVillager villagerObj) {
        this.villagerObj = villagerObj;
        this.worldObj = villagerObj.worldObj;
        this.setMutexBits(3);
    }
    
    private void giveBirth() {
        final EntityVillager child = this.villagerObj.createChild((EntityAgeable)this.mate);
        this.mate.setGrowingAge(6000);
        this.villagerObj.setGrowingAge(6000);
        this.mate.setIsWillingToMate(false);
        this.villagerObj.setIsWillingToMate(false);
        child.setGrowingAge(-24000);
        child.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(child);
        this.worldObj.setEntityState(child, (byte)12);
    }
    
    @Override
    public void updateTask() {
        --this.matingTimeout;
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0f, 30.0f);
        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25) {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25);
        }
        else if (this.matingTimeout == 0 && this.mate.isMating()) {
            this.giveBirth();
        }
        if (this.villagerObj.getRNG().nextInt(35) == 0) {
            this.worldObj.setEntityState(this.villagerObj, (byte)12);
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() != 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(500) != 0) {
            return false;
        }
        this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.villagerObj), 0);
        if (this.villageObj == null) {
            return false;
        }
        if (this != 0 || !this.villagerObj.getIsWillingToMate(true)) {
            return false;
        }
        final Entity nearestEntityWithinAABB = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0, 3.0, 8.0), this.villagerObj);
        if (nearestEntityWithinAABB == null) {
            return false;
        }
        this.mate = (EntityVillager)nearestEntityWithinAABB;
        return this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true);
    }
    
    @Override
    public void startExecuting() {
        this.matingTimeout = 300;
        this.villagerObj.setMating(true);
    }
}
