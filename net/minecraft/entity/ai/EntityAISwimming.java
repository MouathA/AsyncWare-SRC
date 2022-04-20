package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.isInLava();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
    
    public EntityAISwimming(final EntityLiving theEntity) {
        this.theEntity = theEntity;
        this.setMutexBits(4);
        ((PathNavigateGround)theEntity.getNavigator()).setCanSwim(true);
    }
}
