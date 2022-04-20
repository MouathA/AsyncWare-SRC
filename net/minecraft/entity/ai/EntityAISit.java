package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAISit extends EntityAIBase
{
    private boolean isSitting;
    private EntityTameable theEntity;
    
    public void setSitting(final boolean isSitting) {
        this.isSitting = isSitting;
    }
    
    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }
    
    public EntityAISit(final EntityTameable theEntity) {
        this.theEntity = theEntity;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }
        if (this.theEntity.isInWater()) {
            return false;
        }
        if (!this.theEntity.onGround) {
            return false;
        }
        final EntityLivingBase owner = this.theEntity.getOwner();
        return owner == null || ((this.theEntity.getDistanceSqToEntity(owner) >= 144.0 || owner.getAITarget() == null) && this.isSitting);
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }
}
