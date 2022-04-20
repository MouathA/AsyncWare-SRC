package net.minecraft.entity.passive;

import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;

public class EntitySquid extends EntityWaterMob
{
    public float prevSquidYaw;
    public float squidRotation;
    private float randomMotionSpeed;
    public float tentacleAngle;
    private float field_70871_bB;
    private float randomMotionVecX;
    public float prevSquidRotation;
    private float randomMotionVecY;
    public float lastTentacleAngle;
    public float squidPitch;
    public float prevSquidPitch;
    private float rotationVelocity;
    public float squidYaw;
    private float randomMotionVecZ;
    
    public void func_175568_b(final float randomMotionVecX, final float randomMotionVecY, final float randomMotionVecZ) {
        this.randomMotionVecX = randomMotionVecX;
        this.randomMotionVecY = randomMotionVecY;
        this.randomMotionVecZ = randomMotionVecZ;
    }
    
    public boolean func_175567_n() {
        return this.randomMotionVecX != 0.0f || this.randomMotionVecY != 0.0f || this.randomMotionVecZ != 0.0f;
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
    
    @Override
    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.6000000238418579, 0.0), Material.water, this);
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.posY > 45.0 && this.posY < this.worldObj.func_181545_F() && super.getCanSpawnHere();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if (this.squidRotation > 6.283185307179586) {
            if (this.worldObj.isRemote) {
                this.squidRotation = 6.2831855f;
            }
            else {
                this.squidRotation -= (float)6.283185307179586;
                if (this.rand.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
                }
                this.worldObj.setEntityState(this, (byte)19);
            }
        }
        if (this.inWater) {
            if (this.squidRotation < 3.1415927f) {
                final float n = this.squidRotation / 3.1415927f;
                this.tentacleAngle = MathHelper.sin(n * n * 3.1415927f) * 3.1415927f * 0.25f;
                if (n > 0.75) {
                    this.randomMotionSpeed = 1.0f;
                    this.field_70871_bB = 1.0f;
                }
                else {
                    this.field_70871_bB *= 0.8f;
                }
            }
            else {
                this.tentacleAngle = 0.0f;
                this.randomMotionSpeed *= 0.9f;
                this.field_70871_bB *= 0.99f;
            }
            if (!this.worldObj.isRemote) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }
            final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-(float)MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0f / 3.1415927f - this.renderYawOffset) * 0.1f;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw += (float)(3.141592653589793 * this.field_70871_bB * 1.5);
            this.squidPitch += (-(float)MathHelper.func_181159_b(sqrt_double, this.motionY) * 180.0f / 3.1415927f - this.squidPitch) * 0.1f;
        }
        else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927f * 0.25f;
            if (!this.worldObj.isRemote) {
                this.motionX = 0.0;
                this.motionY -= 0.08;
                this.motionY *= 0.9800000190734863;
                this.motionZ = 0.0;
            }
            this.squidPitch += (float)((-90.0f - this.squidPitch) * 0.02);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3 + n) + 1) {
            this.entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), 0.0f);
            int n2 = 0;
            ++n2;
        }
    }
    
    public EntitySquid(final World world) {
        super(world);
        this.setSize(0.95f, 0.95f);
        this.rand.setSeed(1 + this.getEntityId());
        this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
        this.tasks.addTask(0, new AIMoveRandom(this));
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 19) {
            this.squidRotation = 0.0f;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    static class AIMoveRandom extends EntityAIBase
    {
        private EntitySquid squid;
        
        @Override
        public boolean shouldExecute() {
            return true;
        }
        
        @Override
        public void updateTask() {
            if (this.squid.getAge() > 100) {
                this.squid.func_175568_b(0.0f, 0.0f, 0.0f);
            }
            else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.func_175567_n()) {
                final float n = this.squid.getRNG().nextFloat() * 3.1415927f * 2.0f;
                this.squid.func_175568_b(MathHelper.cos(n) * 0.2f, -0.1f + this.squid.getRNG().nextFloat() * 0.2f, MathHelper.sin(n) * 0.2f);
            }
        }
        
        public AIMoveRandom(final EntitySquid squid) {
            this.squid = squid;
        }
    }
}
