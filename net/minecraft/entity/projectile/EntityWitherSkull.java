package net.minecraft.entity.projectile;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWitherSkull extends EntityFireball
{
    public EntityWitherSkull(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public float getExplosionResistance(final Explosion p0, final World p1, final BlockPos p2, final IBlockState p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: aload           4
        //     6: invokespecial   net/minecraft/entity/projectile/EntityFireball.getExplosionResistance:(Lnet/minecraft/world/Explosion;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)F
        //     9: fstore          5
        //    11: aload           4
        //    13: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    18: astore          6
        //    20: aload_0        
        //    21: if_icmpne       41
        //    24: aload           6
        //    26: invokestatic    net/minecraft/entity/boss/EntityWither.func_181033_a:(Lnet/minecraft/block/Block;)Z
        //    29: ifeq            41
        //    32: ldc             0.8
        //    34: fload           5
        //    36: invokestatic    java/lang/Math.min:(FF)F
        //    39: fstore          5
        //    41: fload           5
        //    43: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public EntityWitherSkull(final World world, final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        super(world, entityLivingBase, n, n2, n3);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, 0);
    }
    
    public void setInvulnerable(final boolean b) {
        this.dataWatcher.updateObject(10, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return false;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (movingObjectPosition.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f)) {
                        if (!movingObjectPosition.entityHit.isEntityAlive()) {
                            this.shootingEntity.heal(5.0f);
                        }
                        else {
                            this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
                        }
                    }
                }
                else {
                    movingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0f);
                }
                if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
                    if (this.worldObj.getDifficulty() != EnumDifficulty.NORMAL) {
                        if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {}
                    }
                    ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 800, 1));
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
            this.setDead();
        }
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    public EntityWitherSkull(final World world) {
        super(world);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected float getMotionFactor() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       9
        //     4: ldc             0.73
        //     6: goto            13
        //     9: aload_0        
        //    10: invokespecial   net/minecraft/entity/projectile/EntityFireball.getMotionFactor:()F
        //    13: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
