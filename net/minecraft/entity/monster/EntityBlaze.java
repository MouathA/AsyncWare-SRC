package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;

public class EntityBlaze extends EntityMob
{
    private int heightOffsetUpdateTime;
    private float heightOffset;
    
    @Override
    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }
    
    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }
    
    public void setOnFire(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x1);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFE);
        }
        this.dataWatcher.updateObject(16, b2);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.blaze.death";
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        if (!this.worldObj.isRemote) {
            super.onLivingUpdate();
            return;
        }
        if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
            this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.fire", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
        }
        while (true) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        final EntityLivingBase attackTarget = this.getAttackTarget();
        if (attackTarget != null && attackTarget.posY + attackTarget.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }
    
    public EntityBlaze(final World world) {
        super(world);
        this.heightOffset = 0.5f;
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.tasks.addTask(4, new AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (b) {
            while (0 < this.rand.nextInt(2 + n)) {
                this.dropItem(Items.blaze_rod, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Items.blaze_rod;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }
    
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    static class AIFireballAttack extends EntityAIBase
    {
        private EntityBlaze blaze;
        private int field_179468_c;
        private int field_179467_b;
        
        @Override
        public void resetTask() {
            this.blaze.setOnFire(false);
        }
        
        @Override
        public void updateTask() {
            --this.field_179468_c;
            final EntityLivingBase attackTarget = this.blaze.getAttackTarget();
            final double distanceSqToEntity = this.blaze.getDistanceSqToEntity(attackTarget);
            if (distanceSqToEntity < 4.0) {
                if (this.field_179468_c <= 0) {
                    this.field_179468_c = 20;
                    this.blaze.attackEntityAsMob(attackTarget);
                }
                this.blaze.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
            }
            else if (distanceSqToEntity < 256.0) {
                final double n = attackTarget.posX - this.blaze.posX;
                final double n2 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (this.blaze.posY + this.blaze.height / 2.0f);
                final double n3 = attackTarget.posZ - this.blaze.posZ;
                if (this.field_179468_c <= 0) {
                    ++this.field_179467_b;
                    if (this.field_179467_b == 1) {
                        this.field_179468_c = 60;
                        this.blaze.setOnFire(true);
                    }
                    else if (this.field_179467_b <= 4) {
                        this.field_179468_c = 6;
                    }
                    else {
                        this.field_179468_c = 100;
                        this.field_179467_b = 0;
                        this.blaze.setOnFire(false);
                    }
                    if (this.field_179467_b > 1) {
                        final float n4 = MathHelper.sqrt_float(MathHelper.sqrt_double(distanceSqToEntity)) * 0.5f;
                        this.blaze.worldObj.playAuxSFXAtEntity(null, 1009, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
                        while (true) {
                            final EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.blaze.worldObj, this.blaze, n + this.blaze.getRNG().nextGaussian() * n4, n2, n3 + this.blaze.getRNG().nextGaussian() * n4);
                            entitySmallFireball.posY = this.blaze.posY + this.blaze.height / 2.0f + 0.5;
                            this.blaze.worldObj.spawnEntityInWorld(entitySmallFireball);
                            int n5 = 0;
                            ++n5;
                        }
                    }
                }
                this.blaze.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0f, 10.0f);
            }
            else {
                this.blaze.getNavigator().clearPathEntity();
                this.blaze.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
            }
            super.updateTask();
        }
        
        @Override
        public void startExecuting() {
            this.field_179467_b = 0;
        }
        
        public AIFireballAttack(final EntityBlaze blaze) {
            this.blaze = blaze;
            this.setMutexBits(3);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.blaze.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }
    }
}
