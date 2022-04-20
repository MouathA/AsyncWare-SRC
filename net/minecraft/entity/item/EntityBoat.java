package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityBoat extends Entity
{
    private double velocityY;
    private double velocityZ;
    private int boatPosRotationIncrements;
    private double speedMultiplier;
    private double boatZ;
    private double velocityX;
    private double boatY;
    private double boatPitch;
    private double boatX;
    private boolean isBoatEmpty;
    private double boatYaw;
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        double n = 0.0;
        while (true) {
            if (this.worldObj.isAABBInMaterial(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 0 / 5 - 0.125, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 1 / 5 - 0.125, this.getEntityBoundingBox().maxZ), Material.water)) {
                n += 1.0 / 5;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
            return true;
        }
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
        return true;
    }
    
    @Override
    public double getMountedYOffset() {
        return -0.3;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity == damageSource.getEntity() && damageSource instanceof EntityDamageSourceIndirect) {
            return false;
        }
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() + n * 10.0f);
        this.setBeenAttacked();
        final boolean b = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode;
        if (b || this.getDamageTaken() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
            if (!b && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                this.dropItemWithOffset(Items.boat, 1, 0.0f);
            }
            this.setDead();
        }
        return true;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getEntityBoundingBox();
    }
    
    public EntityBoat(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    public EntityBoat(final World world) {
        super(world);
        this.isBoatEmpty = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
    }
    
    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX + Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4);
        }
    }
    
    @Override
    public void setPositionAndRotation2(final double boatX, final double boatY, final double boatZ, final float rotationYaw, final float rotationPitch, final int n, final boolean b) {
        if (b && this.riddenByEntity != null) {
            this.posX = boatX;
            this.prevPosX = boatX;
            this.posY = boatY;
            this.prevPosY = boatY;
            this.posZ = boatZ;
            this.prevPosZ = boatZ;
            this.rotationYaw = rotationYaw;
            this.rotationPitch = rotationPitch;
            this.boatPosRotationIncrements = 0;
            this.setPosition(boatX, boatY, boatZ);
            final double n2 = 0.0;
            this.velocityX = n2;
            this.motionX = n2;
            final double n3 = 0.0;
            this.velocityY = n3;
            this.motionY = n3;
            final double n4 = 0.0;
            this.velocityZ = n4;
            this.motionZ = n4;
        }
        else {
            if (this.isBoatEmpty) {
                this.boatPosRotationIncrements = n + 5;
            }
            else {
                final double n5 = boatX - this.posX;
                final double n6 = boatY - this.posY;
                final double n7 = boatZ - this.posZ;
                if (n5 * n5 + n6 * n6 + n7 * n7 <= 1.0) {
                    return;
                }
                this.boatPosRotationIncrements = 3;
            }
            this.boatX = boatX;
            this.boatY = boatY;
            this.boatZ = boatZ;
            this.boatYaw = rotationYaw;
            this.boatPitch = rotationPitch;
            this.motionX = this.velocityX;
            this.motionY = this.velocityY;
            this.motionZ = this.velocityZ;
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 3.0f) {
                this.fall(this.fallDistance, 1.0f);
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                    if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        while (true) {
                            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0f);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (this.worldObj.getBlockState(new BlockPos(this).down()).getBlock().getMaterial() != Material.water && n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    public void setIsBoatEmpty(final boolean isBoatEmpty) {
        this.isBoatEmpty = isBoatEmpty;
    }
    
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
    }
    
    public void setTimeSinceHit(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    public void setDamageTaken(final float n) {
        this.dataWatcher.updateObject(19, n);
    }
}
