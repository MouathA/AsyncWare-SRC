package net.minecraft.entity.projectile;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityLargeFireball extends EntityFireball
{
    public int explosionPower;
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("ExplosionPower", 99)) {
            this.explosionPower = nbtTagCompound.getInteger("ExplosionPower");
        }
    }
    
    public EntityLargeFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.explosionPower = 1;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
                this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
            }
            final boolean boolean1 = this.worldObj.getGameRules().getBoolean("mobGriefing");
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, (float)this.explosionPower, boolean1, boolean1);
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("ExplosionPower", this.explosionPower);
    }
    
    public EntityLargeFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.explosionPower = 1;
    }
    
    public EntityLargeFireball(final World world) {
        super(world);
        this.explosionPower = 1;
    }
}
