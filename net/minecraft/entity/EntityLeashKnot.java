package net.minecraft.entity;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EntityLeashKnot extends EntityHanging
{
    @Override
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        return false;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        final ItemStack heldItem = entityPlayer.getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.lead && !this.worldObj.isRemote) {
            final double n = 7.0;
            for (final EntityLiving entityLiving : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - n, this.posY - n, this.posZ - n, this.posX + n, this.posY + n, this.posZ + n))) {
                if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == entityPlayer) {
                    entityLiving.setLeashedToEntity(this, true);
                }
            }
        }
        if (!this.worldObj.isRemote) {}
        return true;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    @Override
    public int getHeightPixels() {
        return 9;
    }
    
    public EntityLeashKnot(final World world) {
        super(world);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void onBroken(final Entity entity) {
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public float getEyeHeight() {
        return -0.0625f;
    }
    
    public static EntityLeashKnot getKnotForPosition(final World world, final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (final EntityLeashKnot entityLeashKnot : world.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB(x - 1.0, y - 1.0, z - 1.0, x + 1.0, y + 1.0, z + 1.0))) {
            if (entityLeashKnot.getHangingPosition().equals(blockPos)) {
                return entityLeashKnot;
            }
        }
        return null;
    }
    
    public static EntityLeashKnot createKnot(final World world, final BlockPos blockPos) {
        final EntityLeashKnot entityLeashKnot = new EntityLeashKnot(world, blockPos);
        entityLeashKnot.forceSpawn = true;
        world.spawnEntityInWorld(entityLeashKnot);
        return entityLeashKnot;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        return n < 1024.0;
    }
    
    public EntityLeashKnot(final World world, final BlockPos blockPos) {
        super(world, blockPos);
        this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875, this.posY - 0.25 + 0.125, this.posZ - 0.1875, this.posX + 0.1875, this.posY + 0.25 + 0.125, this.posZ + 0.1875));
    }
    
    @Override
    public int getWidthPixels() {
        return 9;
    }
    
    public boolean onValidSurface() {
        return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
    }
    
    public void updateFacingWithBoundingBox(final EnumFacing enumFacing) {
    }
}
