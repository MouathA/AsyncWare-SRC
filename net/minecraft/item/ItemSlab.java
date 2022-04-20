package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class ItemSlab extends ItemBlock
{
    private final BlockSlab doubleSlab;
    private final BlockSlab singleSlab;
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, BlockPos offset, final EnumFacing enumFacing, final EntityPlayer entityPlayer, final ItemStack itemStack) {
        final BlockPos blockPos = offset;
        final IProperty variantProperty = this.singleSlab.getVariantProperty();
        final Object variant = this.singleSlab.getVariant(itemStack);
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == this.singleSlab) {
            final boolean b = blockState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;
            if (((enumFacing == EnumFacing.UP && !b) || (enumFacing == EnumFacing.DOWN && b)) && variant == blockState.getValue(variantProperty)) {
                return true;
            }
        }
        offset = offset.offset(enumFacing);
        final IBlockState blockState2 = world.getBlockState(offset);
        return (blockState2.getBlock() == this.singleSlab && variant == blockState2.getValue(variantProperty)) || super.canPlaceBlockOnSide(world, blockPos, enumFacing, entityPlayer, itemStack);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.singleSlab.getUnlocalizedName(itemStack.getMetadata());
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final Object variant = this.singleSlab.getVariant(itemStack);
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            final IProperty variantProperty = this.singleSlab.getVariantProperty();
            final Comparable value = blockState.getValue(variantProperty);
            final BlockSlab.EnumBlockHalf enumBlockHalf = (BlockSlab.EnumBlockHalf)blockState.getValue(BlockSlab.HALF);
            if (((enumFacing == EnumFacing.UP && enumBlockHalf == BlockSlab.EnumBlockHalf.BOTTOM) || (enumFacing == EnumFacing.DOWN && enumBlockHalf == BlockSlab.EnumBlockHalf.TOP)) && value == variant) {
                final IBlockState withProperty = this.doubleSlab.getDefaultState().withProperty(variantProperty, value);
                if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, blockPos, withProperty)) && world.setBlockState(blockPos, withProperty, 3)) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0f) / 2.0f, this.doubleSlab.stepSound.getFrequency() * 0.8f);
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        return blockPos.offset(enumFacing) == variant || super.onItemUse(itemStack, entityPlayer, world, blockPos, enumFacing, n, n2, n3);
    }
    
    public ItemSlab(final Block block, final BlockSlab singleSlab, final BlockSlab doubleSlab) {
        super(block);
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
}
