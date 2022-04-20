package net.minecraft.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityDragonPart extends Entity
{
    public final String partName;
    public final IEntityMultiPart entityDragonObj;
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return !this.isEntityInvulnerable(damageSource) && this.entityDragonObj.attackEntityFromPart(this, damageSource, n);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isEntityEqual(final Entity entity) {
        return this == entity || this.entityDragonObj == entity;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public EntityDragonPart(final IEntityMultiPart entityDragonObj, final String partName, final float n, final float n2) {
        super(entityDragonObj.getWorld());
        this.setSize(n, n2);
        this.entityDragonObj = entityDragonObj;
        this.partName = partName;
    }
}
