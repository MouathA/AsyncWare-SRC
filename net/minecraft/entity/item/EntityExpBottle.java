package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityExpBottle extends EntityThrowable
{
    @Override
    protected float getInaccuracy() {
        return -20.0f;
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }
    
    @Override
    protected float getVelocity() {
        return 0.7f;
    }
    
    public EntityExpBottle(final World world) {
        super(world);
    }
    
    public EntityExpBottle(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            this.worldObj.playAuxSFX(2002, new BlockPos(this), 0);
            int i = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
            }
            this.setDead();
        }
    }
    
    public EntityExpBottle(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
}
