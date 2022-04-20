package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected String getLivingSound() {
        return "none";
    }
    
    @Override
    protected String getHurtSound() {
        return "none";
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected String getDeathSound() {
        return "none";
    }
    
    public EntityGolem(final World world) {
        super(world);
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
}
