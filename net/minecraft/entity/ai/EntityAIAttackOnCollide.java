package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    private double targetX;
    Class classTarget;
    double speedTowardsTarget;
    PathEntity entityPathEntity;
    private double targetY;
    int attackTick;
    boolean longMemory;
    World worldObj;
    protected EntityCreature attacker;
    private double targetZ;
    private int delayCounter;
    
    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }
    
    public EntityAIAttackOnCollide(final EntityCreature entityCreature, final Class classTarget, final double n, final boolean b) {
        this(entityCreature, n, b);
        this.classTarget = classTarget;
    }
    
    @Override
    public void updateTask() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0f, 30.0f);
        final double distanceSq = this.attacker.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY, attackTarget.posZ);
        final double func_179512_a = this.func_179512_a(attackTarget);
        --this.delayCounter;
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(attackTarget)) && this.delayCounter <= 0 && ((this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0) || attackTarget.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0 || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.targetX = attackTarget.posX;
            this.targetY = attackTarget.getEntityBoundingBox().minY;
            this.targetZ = attackTarget.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            if (distanceSq > 1024.0) {
                this.delayCounter += 10;
            }
            else if (distanceSq > 256.0) {
                this.delayCounter += 5;
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(attackTarget, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (distanceSq <= func_179512_a && this.attackTick <= 0) {
            this.attackTick = 20;
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }
            this.attacker.attackEntityAsMob(attackTarget);
        }
    }
    
    public EntityAIAttackOnCollide(final EntityCreature attacker, final double speedTowardsTarget, final boolean longMemory) {
        this.attacker = attacker;
        this.worldObj = attacker.worldObj;
        this.speedTowardsTarget = speedTowardsTarget;
        this.longMemory = longMemory;
        this.setMutexBits(3);
    }
    
    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayCounter = 0;
    }
    
    protected double func_179512_a(final EntityLivingBase entityLivingBase) {
        return this.attacker.width * 2.0f * this.attacker.width * 2.0f + entityLivingBase.width;
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        if (this.classTarget != null && !this.classTarget.isAssignableFrom(attackTarget.getClass())) {
            return false;
        }
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(attackTarget);
        return this.entityPathEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        return attackTarget != null && attackTarget.isEntityAlive() && (this.longMemory ? this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(attackTarget)) : (!this.attacker.getNavigator().noPath()));
    }
}
