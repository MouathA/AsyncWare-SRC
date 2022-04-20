package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class ItemReed extends Item
{
    private Block block;
    
    public ItemReed(final Block block) {
        this.block = block;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, EnumFacing up, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(offset);
        final Block block = blockState.getBlock();
        if (block == Blocks.snow_layer && (int)blockState.getValue(BlockSnow.LAYERS) < 1) {
            up = EnumFacing.UP;
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        if (!entityPlayer.canPlayerEdit(offset, up, itemStack)) {
            return false;
        }
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (world.canBlockBePlaced(this.block, offset, false, up, null, itemStack) && world.setBlockState(offset, this.block.onBlockPlaced(world, offset, up, n, n2, n3, 0, entityPlayer), 3)) {
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this.block) {
                ItemBlock.setTileEntityNBT(world, entityPlayer, offset, itemStack);
                blockState2.getBlock().onBlockPlacedBy(world, offset, blockState2, entityPlayer, itemStack);
            }
            world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
}
