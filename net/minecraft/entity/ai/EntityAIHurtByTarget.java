package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIHurtByTarget extends EntityAITarget
{
    private boolean entityCallsForHelp;
    private int revengeTimerOld;
    private final Class[] targetClasses;
    
    protected void setEntityAttackTarget(final EntityCreature entityCreature, final EntityLivingBase attackTarget) {
        entityCreature.setAttackTarget(attackTarget);
    }
    
    public EntityAIHurtByTarget(final EntityCreature entityCreature, final boolean entityCallsForHelp, final Class... targetClasses) {
        super(entityCreature, false);
        this.entityCallsForHelp = entityCallsForHelp;
        this.targetClasses = targetClasses;
        this.setMutexBits(1);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();
        if (this.entityCallsForHelp) {
            final double targetDistance = this.getTargetDistance();
            for (final EntityCreature entityCreature : this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(targetDistance, 10.0, targetDistance))) {
                if (this.taskOwner != entityCreature && entityCreature.getAttackTarget() == null && !entityCreature.isOnSameTeam(this.taskOwner.getAITarget())) {
                    final Class[] targetClasses = this.targetClasses;
                    while (0 < targetClasses.length) {
                        if (entityCreature.getClass() == targetClasses[0]) {
                            break;
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
        }
        super.startExecuting();
    }
    
    @Override
    public boolean shouldExecute() {
        return this.taskOwner.getRevengeTimer() != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }
}
