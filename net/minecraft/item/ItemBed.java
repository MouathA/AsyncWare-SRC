package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;

public class ItemBed extends Item
{
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos up, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        final boolean replaceable = world.getBlockState(up).getBlock().isReplaceable(world, up);
        if (!replaceable) {
            up = up.up();
        }
        final EnumFacing horizontal = EnumFacing.getHorizontal(MathHelper.floor_double(entityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        final BlockPos offset = up.offset(horizontal);
        if (!entityPlayer.canPlayerEdit(up, enumFacing, itemStack) || !entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return false;
        }
        final boolean replaceable2 = world.getBlockState(offset).getBlock().isReplaceable(world, offset);
        final boolean b = replaceable || world.isAirBlock(up);
        final boolean b2 = replaceable2 || world.isAirBlock(offset);
        if (b && b2 && World.doesBlockHaveSolidTopSurface(world, up.down()) && World.doesBlockHaveSolidTopSurface(world, offset.down())) {
            final IBlockState withProperty = Blocks.bed.getDefaultState().withProperty(BlockBed.OCCUPIED, false).withProperty(BlockBed.FACING, horizontal).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
            if (world.setBlockState(up, withProperty, 3)) {
                world.setBlockState(offset, withProperty.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 3);
            }
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
