package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.village.*;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;
    
    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
    }
    
    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(this.entityObj);
        final Village nearestVillage = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockPos, 16);
        if (nearestVillage == null) {
            return false;
        }
        this.frontDoor = nearestVillage.getNearestDoor(blockPos);
        return this.frontDoor != null && this.frontDoor.getDistanceToInsideBlockSq(blockPos) < 2.25;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.worldObj.isDaytime() && (!this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.func_179850_c(new BlockPos(this.entityObj)));
    }
    
    public EntityAIRestrictOpenDoor(final EntityCreature entityObj) {
        this.entityObj = entityObj;
        if (!(entityObj.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
    }
    
    @Override
    public void resetTask() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
        this.frontDoor = null;
    }
}
