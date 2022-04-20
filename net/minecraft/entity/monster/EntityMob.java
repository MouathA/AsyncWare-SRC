package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class EntityMob extends EntityCreature implements IMob
{
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        if (entity instanceof EntityLivingBase) {
            n += EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)entity).getCreatureAttribute());
            final int n2 = 0 + EnchantmentHelper.getKnockbackModifier(this);
        }
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), n);
        if (attackEntity) {
            final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
            if (fireAspectModifier > 0) {
                entity.setFire(fireAspectModifier * 4);
            }
            this.applyEnchantments(this, entity);
        }
        return attackEntity;
    }
    
    @Override
    protected String getFallSoundString(final int n) {
        return (n > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    
    @Override
    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }
    
    @Override
    public boolean getCanSpawnHere() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntityMob.worldObj:Lnet/minecraft/world/World;
        //     4: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //     7: getstatic       net/minecraft/world/EnumDifficulty.PEACEFUL:Lnet/minecraft/world/EnumDifficulty;
        //    10: if_acmpeq       28
        //    13: aload_0        
        //    14: if_icmple       28
        //    17: aload_0        
        //    18: invokespecial   net/minecraft/entity/EntityCreature.getCanSpawnHere:()Z
        //    21: ifeq            28
        //    24: iconst_1       
        //    25: goto            29
        //    28: iconst_0       
        //    29: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected boolean canDropLoot() {
        return true;
    }
    
    @Override
    protected String getDeathSound() {
        return "game.hostile.die";
    }
    
    public EntityMob(final World world) {
        super(world);
        this.experienceValue = 5;
    }
    
    @Override
    protected String getHurtSound() {
        return "game.hostile.hurt";
    }
    
    @Override
    protected String getSwimSound() {
        return "game.hostile.swim";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return 0.5f - this.worldObj.getLightBrightness(blockPos);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (super.attackEntityFrom(damageSource, n)) {
            final Entity entity = damageSource.getEntity();
            return this.riddenByEntity == entity || this.ridingEntity == entity || true;
        }
        return false;
    }
    
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        if (this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }
}
