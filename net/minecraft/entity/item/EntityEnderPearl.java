package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntityEnderPearl extends EntityThrowable
{
    private EntityLivingBase field_181555_c;
    
    @Override
    public void onUpdate() {
        final EntityLivingBase thrower = this.getThrower();
        if (thrower != null && thrower instanceof EntityPlayer && !thrower.isEntityAlive()) {
            this.setDead();
        }
        else {
            super.onUpdate();
        }
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        final EntityLivingBase thrower = this.getThrower();
        if (movingObjectPosition.entityHit != null) {
            if (movingObjectPosition.entityHit == this.field_181555_c) {
                return;
            }
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0.0f);
        }
        while (true) {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0, this.posZ, this.rand.nextGaussian(), 0.0, this.rand.nextGaussian(), new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    public EntityEnderPearl(final World world) {
        super(world);
    }
    
    public EntityEnderPearl(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    public EntityEnderPearl(final World world, final EntityLivingBase field_181555_c) {
        super(world, field_181555_c);
        this.field_181555_c = field_181555_c;
    }
}
