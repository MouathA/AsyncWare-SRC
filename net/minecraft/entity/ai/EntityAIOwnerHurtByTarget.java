package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityLivingBase theOwnerAttacker;
    private int field_142051_e;
    EntityTameable theDefendingTameable;
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        final EntityLivingBase owner = this.theDefendingTameable.getOwner();
        if (owner != null) {
            this.field_142051_e = owner.getRevengeTimer();
        }
        super.startExecuting();
    }
    
    public EntityAIOwnerHurtByTarget(final EntityTameable theDefendingTameable) {
        super(theDefendingTameable, false);
        this.theDefendingTameable = theDefendingTameable;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return false;
        }
        final EntityLivingBase owner = this.theDefendingTameable.getOwner();
        if (owner == null) {
            return false;
        }
        this.theOwnerAttacker = owner.getAITarget();
        return owner.getRevengeTimer() != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, owner);
    }
}
