package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    private int field_142050_e;
    EntityLivingBase theTarget;
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        final EntityLivingBase owner = this.theEntityTameable.getOwner();
        if (owner != null) {
            this.field_142050_e = owner.getLastAttackerTime();
        }
        super.startExecuting();
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        }
        final EntityLivingBase owner = this.theEntityTameable.getOwner();
        if (owner == null) {
            return false;
        }
        this.theTarget = owner.getLastAttacker();
        return owner.getLastAttackerTime() != this.field_142050_e && this.isSuitableTarget(this.theTarget, false) && this.theEntityTameable.shouldAttackEntity(this.theTarget, owner);
    }
    
    public EntityAIOwnerHurtTarget(final EntityTameable theEntityTameable) {
        super(theEntityTameable, false);
        this.theEntityTameable = theEntityTameable;
        this.setMutexBits(1);
    }
}
