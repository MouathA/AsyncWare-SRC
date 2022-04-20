package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class EntityAIMate extends EntityAIBase
{
    double moveSpeed;
    private EntityAnimal theAnimal;
    World theWorld;
    int spawnBabyDelay;
    private EntityAnimal targetMate;
    
    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }
    
    private void spawnBaby() {
        final EntityAgeable child = this.theAnimal.createChild(this.targetMate);
        if (child == null) {
            return;
        }
        EntityPlayer entityPlayer = this.theAnimal.getPlayerInLove();
        if (entityPlayer == null && this.targetMate.getPlayerInLove() != null) {
            entityPlayer = this.targetMate.getPlayerInLove();
        }
        if (entityPlayer != null) {
            entityPlayer.triggerAchievement(StatList.animalsBredStat);
            if (this.theAnimal instanceof EntityCow) {
                entityPlayer.triggerAchievement(AchievementList.breedCow);
            }
        }
        this.theAnimal.setGrowingAge(6000);
        this.targetMate.setGrowingAge(6000);
        this.theAnimal.resetInLove();
        this.targetMate.resetInLove();
        child.setGrowingAge(-24000);
        child.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0f, 0.0f);
        this.theWorld.spawnEntityInWorld(child);
        final Random rng = this.theAnimal.getRNG();
        while (true) {
            this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + (rng.nextDouble() * this.theAnimal.width * 2.0 - this.theAnimal.width), this.theAnimal.posY + (0.5 + rng.nextDouble() * this.theAnimal.height), this.theAnimal.posZ + (rng.nextDouble() * this.theAnimal.width * 2.0 - this.theAnimal.width), rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    private EntityAnimal getNearbyMate() {
        final float n = 8.0f;
        final List entitiesWithinAABB = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(n, n, n));
        double distanceSqToEntity = Double.MAX_VALUE;
        EntityAnimal entityAnimal = null;
        for (final EntityAnimal entityAnimal2 : entitiesWithinAABB) {
            if (this.theAnimal.canMateWith(entityAnimal2) && this.theAnimal.getDistanceSqToEntity(entityAnimal2) < distanceSqToEntity) {
                entityAnimal = entityAnimal2;
                distanceSqToEntity = this.theAnimal.getDistanceSqToEntity(entityAnimal2);
            }
        }
        return entityAnimal;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return false;
        }
        this.targetMate = this.getNearbyMate();
        return this.targetMate != null;
    }
    
    public EntityAIMate(final EntityAnimal theAnimal, final double moveSpeed) {
        this.theAnimal = theAnimal;
        this.theWorld = theAnimal.worldObj;
        this.moveSpeed = moveSpeed;
        this.setMutexBits(3);
    }
    
    @Override
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0f, (float)this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
    }
}
