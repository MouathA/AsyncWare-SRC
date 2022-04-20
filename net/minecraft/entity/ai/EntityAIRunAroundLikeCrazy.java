package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private EntityHorse horseHost;
    private double targetY;
    private double targetZ;
    private double speed;
    private double targetX;
    
    @Override
    public void startExecuting() {
        this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }
    
    @Override
    public void updateTask() {
        if (this.horseHost.getRNG().nextInt(50) == 0) {
            if (this.horseHost.riddenByEntity instanceof EntityPlayer) {
                final int temper = this.horseHost.getTemper();
                final int maxTemper = this.horseHost.getMaxTemper();
                if (maxTemper > 0 && this.horseHost.getRNG().nextInt(maxTemper) < temper) {
                    this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
                    this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
                    return;
                }
                this.horseHost.increaseTemper(5);
            }
            this.horseHost.riddenByEntity.mountEntity(null);
            this.horseHost.riddenByEntity = null;
            this.horseHost.makeHorseRearWithSound();
            this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.horseHost.isTame() || this.horseHost.riddenByEntity == null) {
            return false;
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
        if (randomTarget == null) {
            return false;
        }
        this.targetX = randomTarget.xCoord;
        this.targetY = randomTarget.yCoord;
        this.targetZ = randomTarget.zCoord;
        return true;
    }
    
    public EntityAIRunAroundLikeCrazy(final EntityHorse horseHost, final double speed) {
        this.horseHost = horseHost;
        this.speed = speed;
        this.setMutexBits(1);
    }
}
