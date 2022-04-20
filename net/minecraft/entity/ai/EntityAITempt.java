package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityAITempt extends EntityAIBase
{
    private boolean scaredByPlayerMovement;
    private double targetZ;
    private double targetY;
    private double pitch;
    private double speed;
    private boolean avoidWater;
    private EntityCreature temptedEntity;
    private int delayTemptCounter;
    private double targetX;
    private EntityPlayer temptingPlayer;
    private double yaw;
    private Item temptItem;
    private boolean isRunning;
    
    @Override
    public void startExecuting() {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
        this.avoidWater = ((PathNavigateGround)this.temptedEntity.getNavigator()).getAvoidsWater();
        ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater(false);
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs(this.temptingPlayer.rotationPitch - this.pitch) > 5.0 || Math.abs(this.temptingPlayer.rotationYaw - this.yaw) > 5.0) {
                    return false;
                }
            }
            else {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }
            this.pitch = this.temptingPlayer.rotationPitch;
            this.yaw = this.temptingPlayer.rotationYaw;
        }
        return this.shouldExecute();
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public EntityAITempt(final EntityCreature temptedEntity, final double speed, final Item temptItem, final boolean scaredByPlayerMovement) {
        this.temptedEntity = temptedEntity;
        this.speed = speed;
        this.temptItem = temptItem;
        this.scaredByPlayerMovement = scaredByPlayerMovement;
        this.setMutexBits(3);
        if (!(temptedEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        }
        this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return false;
        }
        final ItemStack currentEquippedItem = this.temptingPlayer.getCurrentEquippedItem();
        return currentEquippedItem != null && currentEquippedItem.getItem() == this.temptItem;
    }
    
    @Override
    public void updateTask() {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0f, (float)this.temptedEntity.getVerticalFaceSpeed());
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator().clearPathEntity();
        }
        else {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
        }
    }
    
    @Override
    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
        this.isRunning = false;
        ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater(this.avoidWater);
    }
}
