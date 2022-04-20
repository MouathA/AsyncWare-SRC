package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;

public class ItemShears extends Item
{
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return (block != Blocks.web && block.getMaterial() != Material.leaves) ? ((block == Blocks.wool) ? 5.0f : super.getStrVsBlock(itemStack, block)) : 15.0f;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getMaterial() != Material.leaves && block != Blocks.web && block != Blocks.tallgrass && block != Blocks.vine && block != Blocks.tripwire && block != Blocks.wool) {
            return super.onBlockDestroyed(itemStack, world, block, blockPos, entityLivingBase);
        }
        itemStack.damageItem(1, entityLivingBase);
        return true;
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.web || block == Blocks.redstone_wire || block == Blocks.tripwire;
    }
    
    public ItemShears() {
        this.setMaxStackSize(1);
        this.setMaxDamage(238);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
}
