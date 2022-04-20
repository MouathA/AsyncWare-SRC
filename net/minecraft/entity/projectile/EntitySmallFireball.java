package net.minecraft.entity.projectile;

import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntitySmallFireball extends EntityFireball
{
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return false;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0f);
                this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
                if (!movingObjectPosition.entityHit.isImmuneToFire()) {
                    movingObjectPosition.entityHit.setFire(5);
                }
            }
            else {
                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving) {
                    this.worldObj.getGameRules().getBoolean("mobGriefing");
                }
                final BlockPos offset = movingObjectPosition.getBlockPos().offset(movingObjectPosition.sideHit);
                if (this.worldObj.isAirBlock(offset)) {
                    this.worldObj.setBlockState(offset, Blocks.fire.getDefaultState());
                }
            }
            this.setDead();
        }
    }
    
    public EntitySmallFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public EntitySmallFireball(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
}
