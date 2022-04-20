package net.minecraft.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
    public boolean allowLeashing() {
        return false;
    }
    
    @Override
    protected boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    public EntityAmbientCreature(final World world) {
        super(world);
    }
}
