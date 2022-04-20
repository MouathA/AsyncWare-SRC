package net.minecraft.item;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class ItemLilyPad extends ItemColored
{
    public ItemLilyPad(final Block block) {
        super(block, false);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(itemStack.getMetadata()));
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (!entityPlayer.canPlayerEdit(blockPos.offset(movingObjectPositionFromPlayer.sideHit), movingObjectPositionFromPlayer.sideHit, itemStack)) {
                return itemStack;
            }
            final BlockPos up = blockPos.up();
            final IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock().getMaterial() == Material.water && (int)blockState.getValue(BlockLiquid.LEVEL) == 0 && world.isAirBlock(up)) {
                world.setBlockState(up, Blocks.waterlily.getDefaultState());
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
}
