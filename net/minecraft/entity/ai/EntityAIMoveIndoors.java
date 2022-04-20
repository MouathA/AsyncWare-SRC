package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.util.*;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private VillageDoorInfo doorInfo;
    private int insidePosZ;
    private int insidePosX;
    private EntityCreature entityObj;
    
    @Override
    public boolean shouldExecute() {
        final BlockPos blockPos = new BlockPos(this.entityObj);
        if ((this.entityObj.worldObj.isDaytime() && (!this.entityObj.worldObj.isRaining() || this.entityObj.worldObj.getBiomeGenForCoords(blockPos).canSpawnLightningBolt())) || this.entityObj.worldObj.provider.getHasNoSky()) {
            return false;
        }
        if (this.entityObj.getRNG().nextInt(50) != 0) {
            return false;
        }
        if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
            return false;
        }
        final Village nearestVillage = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockPos, 14);
        if (nearestVillage == null) {
            return false;
        }
        this.doorInfo = nearestVillage.getDoorInfo(blockPos);
        return this.doorInfo != null;
    }
    
    public EntityAIMoveIndoors(final EntityCreature entityObj) {
        this.insidePosX = -1;
        this.insidePosZ = -1;
        this.entityObj = entityObj;
        this.setMutexBits(1);
    }
    
    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
        this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
        this.doorInfo = null;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.insidePosX = -1;
        final BlockPos insideBlockPos = this.doorInfo.getInsideBlockPos();
        final int x = insideBlockPos.getX();
        final int y = insideBlockPos.getY();
        final int z = insideBlockPos.getZ();
        if (this.entityObj.getDistanceSq(insideBlockPos) > 256.0) {
            final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, new Vec3(x + 0.5, y, z + 0.5));
            if (randomTargetBlockTowards != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord, 1.0);
            }
        }
        else {
            this.entityObj.getNavigator().tryMoveToXYZ(x + 0.5, y, z + 0.5, 1.0);
        }
    }
}
