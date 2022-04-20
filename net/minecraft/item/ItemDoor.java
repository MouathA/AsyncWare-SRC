package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;

public class ItemDoor extends Item
{
    private Block block;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return false;
        }
        if (!this.block.canPlaceBlockAt(world, offset)) {
            return false;
        }
        placeDoor(world, offset, EnumFacing.fromAngle(entityPlayer.rotationYaw), this.block);
        --itemStack.stackSize;
        return true;
    }
    
    public static void placeDoor(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final Block block) {
        final BlockPos offset = blockPos.offset(enumFacing.rotateY());
        final BlockPos offset2 = blockPos.offset(enumFacing.rotateYCCW());
        final int n = (world.getBlockState(offset2).getBlock().isNormalCube() + world.getBlockState(offset2.up()).getBlock().isNormalCube()) ? 1 : 0;
        final int n2 = (world.getBlockState(offset).getBlock().isNormalCube() + world.getBlockState(offset.up()).getBlock().isNormalCube()) ? 1 : 0;
        final boolean b = world.getBlockState(offset2).getBlock() == block || world.getBlockState(offset2.up()).getBlock() == block;
        final boolean b2 = world.getBlockState(offset).getBlock() == block || world.getBlockState(offset.up()).getBlock() == block;
        if ((b && !b2) || n2 > n) {}
        final BlockPos up = blockPos.up();
        final IBlockState withProperty = block.getDefaultState().withProperty(BlockDoor.FACING, enumFacing).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.RIGHT);
        world.setBlockState(blockPos, withProperty.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        world.setBlockState(up, withProperty.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        world.notifyNeighborsOfStateChange(blockPos, block);
        world.notifyNeighborsOfStateChange(up, block);
    }
    
    public ItemDoor(final Block block) {
        this.block = block;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
