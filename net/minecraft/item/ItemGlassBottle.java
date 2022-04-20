package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;

public class ItemGlassBottle extends Item
{
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
            if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
                --itemStack.stackSize;
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                if (itemStack.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }
        return itemStack;
    }
    
    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
}
