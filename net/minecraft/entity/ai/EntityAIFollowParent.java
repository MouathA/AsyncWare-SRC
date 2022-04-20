package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityAIFollowParent extends EntityAIBase
{
    EntityAnimal parentAnimal;
    private int delayCounter;
    double moveSpeed;
    EntityAnimal childAnimal;
    
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }
    
    @Override
    public void updateTask() {
        final int delayCounter = this.delayCounter - 1;
        this.delayCounter = delayCounter;
        if (delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
    
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        if (!this.parentAnimal.isEntityAlive()) {
            return false;
        }
        final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        return distanceSqToEntity >= 9.0 && distanceSqToEntity <= 256.0;
    }
    
    public EntityAIFollowParent(final EntityAnimal childAnimal, final double moveSpeed) {
        this.childAnimal = childAnimal;
        this.moveSpeed = moveSpeed;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        final List entitiesWithinAABB = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
        EntityAnimal parentAnimal = null;
        double n = Double.MAX_VALUE;
        for (final EntityAnimal entityAnimal : entitiesWithinAABB) {
            if (entityAnimal.getGrowingAge() >= 0) {
                final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(entityAnimal);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                parentAnimal = entityAnimal;
            }
        }
        if (parentAnimal == null) {
            return false;
        }
        if (n < 9.0) {
            return false;
        }
        this.parentAnimal = parentAnimal;
        return true;
    }
}
