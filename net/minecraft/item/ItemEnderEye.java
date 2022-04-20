package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.stats.*;

public class ItemEnderEye extends Item
{
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack) || blockState.getBlock() != Blocks.end_portal_frame || (boolean)blockState.getValue(BlockEndPortalFrame.EYE)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        world.setBlockState(blockPos, blockState.withProperty(BlockEndPortalFrame.EYE, true), 2);
        world.updateComparatorOutputLevel(blockPos, Blocks.end_portal_frame);
        --itemStack.stackSize;
        while (true) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f, blockPos.getY() + 0.8125f, blockPos.getZ() + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f, 0.0, 0.0, 0.0, new int[0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, false);
        if (movingObjectPositionFromPlayer != null && movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && world.getBlockState(movingObjectPositionFromPlayer.getBlockPos()).getBlock() == Blocks.end_portal_frame) {
            return itemStack;
        }
        if (!world.isRemote) {
            final BlockPos strongholdPos = world.getStrongholdPos("Stronghold", new BlockPos(entityPlayer));
            if (strongholdPos != null) {
                final EntityEnderEye entityEnderEye = new EntityEnderEye(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
                entityEnderEye.moveTowards(strongholdPos);
                world.spawnEntityInWorld(entityEnderEye);
                world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemEnderEye.itemRand.nextFloat() * 0.4f + 0.8f));
                world.playAuxSFXAtEntity(null, 1002, new BlockPos(entityPlayer), 0);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
}
