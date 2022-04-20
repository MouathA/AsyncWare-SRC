package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature theEntity;
    
    public EntityAIRestrictSun(final EntityCreature theEntity) {
        this.theEntity = theEntity;
    }
    
    @Override
    public void resetTask() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }
    
    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
    }
}
