package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIWander extends EntityAIBase
{
    private double speed;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int executionChance;
    private boolean mustUpdate;
    private EntityCreature entity;
    
    public EntityAIWander(final EntityCreature entityCreature, final double n) {
        this(entityCreature, n, 120);
    }
    
    public void makeUpdate() {
        this.mustUpdate = true;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.mustUpdate) {
            if (this.entity.getAge() >= 100) {
                return false;
            }
            if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
                return false;
            }
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
        if (randomTarget == null) {
            return false;
        }
        this.xPosition = randomTarget.xCoord;
        this.yPosition = randomTarget.yCoord;
        this.zPosition = randomTarget.zCoord;
        this.mustUpdate = false;
        return true;
    }
    
    public EntityAIWander(final EntityCreature entity, final double speed, final int executionChance) {
        this.entity = entity;
        this.speed = speed;
        this.executionChance = executionChance;
        this.setMutexBits(1);
    }
    
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
    
    public void setExecutionChance(final int executionChance) {
        this.executionChance = executionChance;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }
}
