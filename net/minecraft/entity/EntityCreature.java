package net.minecraft.entity;

import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;

public abstract class EntityCreature extends EntityLiving
{
    private BlockPos homePosition;
    public static final UUID FLEEING_SPEED_MODIFIER_UUID;
    private float maximumHomeDistance;
    private EntityAIBase aiBase;
    private boolean isMovementAITaskSet;
    public static final AttributeModifier FLEEING_SPEED_MODIFIER;
    
    public boolean hasPath() {
        return !this.navigator.noPath();
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPosition(new BlockPos(this));
    }
    
    public EntityCreature(final World world) {
        super(world);
        this.homePosition = BlockPos.ORIGIN;
        this.maximumHomeDistance = -1.0f;
        this.aiBase = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
    
    public BlockPos getHomePosition() {
        return this.homePosition;
    }
    
    public boolean isWithinHomeDistanceFromPosition(final BlockPos blockPos) {
        return this.maximumHomeDistance == -1.0f || this.homePosition.distanceSq(blockPos) < this.maximumHomeDistance * this.maximumHomeDistance;
    }
    
    public float getBlockPathWeight(final BlockPos blockPos) {
        return 0.0f;
    }
    
    static {
        FLEEING_SPEED_MODIFIER_UUID = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        FLEEING_SPEED_MODIFIER = new AttributeModifier(EntityCreature.FLEEING_SPEED_MODIFIER_UUID, "Fleeing speed bonus", 2.0, 2).setSaved(false);
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    protected void func_142017_o(final float n) {
    }
    
    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }
    
    public void setHomePosAndDistance(final BlockPos homePosition, final int n) {
        this.homePosition = homePosition;
        this.maximumHomeDistance = (float)n;
    }
    
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            final Entity leashedToEntity = this.getLeashedToEntity();
            this.setHomePosAndDistance(new BlockPos((int)leashedToEntity.posX, (int)leashedToEntity.posY, (int)leashedToEntity.posZ), 5);
            final float distanceToEntity = this.getDistanceToEntity(leashedToEntity);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (distanceToEntity > 10.0f) {
                    this.clearLeashed(true, true);
                }
                return;
            }
            if (!this.isMovementAITaskSet) {
                this.tasks.addTask(2, this.aiBase);
                if (this.getNavigator() instanceof PathNavigateGround) {
                    ((PathNavigateGround)this.getNavigator()).setAvoidsWater(false);
                }
                this.isMovementAITaskSet = true;
            }
            this.func_142017_o(distanceToEntity);
            if (distanceToEntity > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(leashedToEntity, 1.0);
            }
            if (distanceToEntity > 6.0f) {
                final double n = (leashedToEntity.posX - this.posX) / distanceToEntity;
                final double n2 = (leashedToEntity.posY - this.posY) / distanceToEntity;
                final double n3 = (leashedToEntity.posZ - this.posZ) / distanceToEntity;
                this.motionX += n * Math.abs(n) * 0.4;
                this.motionY += n2 * Math.abs(n2) * 0.4;
                this.motionZ += n3 * Math.abs(n3) * 0.4;
            }
            if (distanceToEntity > 10.0f) {
                this.clearLeashed(true, true);
            }
        }
        else if (!this.getLeashed() && this.isMovementAITaskSet) {
            this.isMovementAITaskSet = false;
            this.tasks.removeTask(this.aiBase);
            if (this.getNavigator() instanceof PathNavigateGround) {
                ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
            }
            this.detachHome();
        }
    }
    
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0f;
    }
}
