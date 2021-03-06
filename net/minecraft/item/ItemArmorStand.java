package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;

public class ItemArmorStand extends Item
{
    public ItemArmorStand() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        final BlockPos blockToAir = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos) ? blockPos : blockPos.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(blockToAir, enumFacing, itemStack)) {
            return false;
        }
        final BlockPos up = blockToAir.up();
        if ((!world.isAirBlock(blockToAir) && !world.getBlockState(blockToAir).getBlock().isReplaceable(world, blockToAir)) | (!world.isAirBlock(up) && !world.getBlockState(up).getBlock().isReplaceable(world, up))) {
            return false;
        }
        final double n4 = blockToAir.getX();
        final double n5 = blockToAir.getY();
        final double n6 = blockToAir.getZ();
        if (world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(n4, n5, n6, n4 + 1.0, n5 + 2.0, n6 + 1.0)).size() > 0) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockToAir(blockToAir);
            world.setBlockToAir(up);
            final EntityArmorStand entityArmorStand = new EntityArmorStand(world, n4 + 0.5, n5, n6 + 0.5);
            entityArmorStand.setLocationAndAngles(n4 + 0.5, n5, n6 + 0.5, MathHelper.floor_float((MathHelper.wrapAngleTo180_float(entityPlayer.rotationYaw - 180.0f) + 22.5f) / 45.0f) * 45.0f, 0.0f);
            this.applyRandomRotations(entityArmorStand, world.rand);
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("EntityTag", 10)) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                entityArmorStand.writeToNBTOptional(nbtTagCompound);
                nbtTagCompound.merge(tagCompound.getCompoundTag("EntityTag"));
                entityArmorStand.readFromNBT(nbtTagCompound);
            }
            world.spawnEntityInWorld(entityArmorStand);
        }
        --itemStack.stackSize;
        return true;
    }
    
    private void applyRandomRotations(final EntityArmorStand entityArmorStand, final Random random) {
        final Rotations headRotation = entityArmorStand.getHeadRotation();
        entityArmorStand.setHeadRotation(new Rotations(headRotation.getX() + random.nextFloat() * 5.0f, headRotation.getY() + (random.nextFloat() * 20.0f - 10.0f), headRotation.getZ()));
        final Rotations bodyRotation = entityArmorStand.getBodyRotation();
        entityArmorStand.setBodyRotation(new Rotations(bodyRotation.getX(), bodyRotation.getY() + (random.nextFloat() * 10.0f - 5.0f), bodyRotation.getZ()));
    }
}
