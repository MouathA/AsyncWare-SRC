package net.minecraft.entity;

import org.apache.commons.lang3.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;

public abstract class EntityHanging extends Entity
{
    protected BlockPos hangingPosition;
    public EnumFacing facingDirection;
    private int tickCounter1;
    
    private double func_174858_a(final int n) {
        return (n % 32 == 0) ? 0.5 : 0.0;
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final BlockPos hangingPosition = this.hangingPosition;
        this.hangingPosition = new BlockPos(posX, posY, posZ);
        if (!this.hangingPosition.equals(hangingPosition)) {
            this.updateBoundingBox();
            this.isAirBorne = true;
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    protected void updateFacingWithBoundingBox(final EnumFacing facingDirection) {
        Validate.notNull((Object)facingDirection);
        Validate.isTrue(facingDirection.getAxis().isHorizontal());
        this.facingDirection = facingDirection;
        final float n = (float)(this.facingDirection.getHorizontalIndex() * 90);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        this.updateBoundingBox();
    }
    
    public EntityHanging(final World world, final BlockPos hangingPosition) {
        this(world);
        this.hangingPosition = hangingPosition;
    }
    
    private void updateBoundingBox() {
        if (this.facingDirection != null) {
            final double n = this.hangingPosition.getX() + 0.5;
            final double n2 = this.hangingPosition.getY() + 0.5;
            final double n3 = this.hangingPosition.getZ() + 0.5;
            final double func_174858_a = this.func_174858_a(this.getWidthPixels());
            final double func_174858_a2 = this.func_174858_a(this.getHeightPixels());
            final double n4 = n - this.facingDirection.getFrontOffsetX() * 0.46875;
            final double n5 = n3 - this.facingDirection.getFrontOffsetZ() * 0.46875;
            final double posY = n2 + func_174858_a2;
            final EnumFacing rotateYCCW = this.facingDirection.rotateYCCW();
            final double posX = n4 + func_174858_a * rotateYCCW.getFrontOffsetX();
            final double posZ = n5 + func_174858_a * rotateYCCW.getFrontOffsetZ();
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            double n6 = this.getWidthPixels();
            final double n7 = this.getHeightPixels();
            double n8 = this.getWidthPixels();
            if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
                n8 = 1.0;
            }
            else {
                n6 = 1.0;
            }
            final double n9 = n6 / 32.0;
            final double n10 = n7 / 32.0;
            final double n11 = n8 / 32.0;
            this.setEntityBoundingBox(new AxisAlignedBB(posX - n9, posY - n10, posZ - n11, posX + n9, posY + n10, posZ + n11));
        }
    }
    
    @Override
    public void addVelocity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    public EntityHanging(final World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    public BlockPos getHangingPosition() {
        return this.hangingPosition;
    }
    
    public abstract int getHeightPixels();
    
    @Override
    public void moveEntity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(damageSource.getEntity());
        }
        return true;
    }
    
    @Override
    protected boolean shouldSetPosAfterLoading() {
        return false;
    }
    
    @Override
    public boolean hitByEntity(final Entity entity) {
        return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.hangingPosition = new BlockPos(nbtTagCompound.getInteger("TileX"), nbtTagCompound.getInteger("TileY"), nbtTagCompound.getInteger("TileZ"));
        EnumFacing enumFacing;
        if (nbtTagCompound.hasKey("Direction", 99)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Direction"));
            this.hangingPosition = this.hangingPosition.offset(enumFacing);
        }
        else if (nbtTagCompound.hasKey("Facing", 99)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Facing"));
        }
        else {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte("Dir"));
        }
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
        nbtTagCompound.setInteger("TileX", this.getHangingPosition().getX());
        nbtTagCompound.setInteger("TileY", this.getHangingPosition().getY());
        nbtTagCompound.setInteger("TileZ", this.getHangingPosition().getZ());
    }
    
    @Override
    public EnumFacing getHorizontalFacing() {
        return this.facingDirection;
    }
    
    public abstract void onBroken(final Entity p0);
    
    public abstract int getWidthPixels();
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
            this.tickCounter1 = 0;
            if (!this.isDead && this == 0) {
                this.setDead();
                this.onBroken(null);
            }
        }
    }
}
