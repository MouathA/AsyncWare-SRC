package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeeds extends Item
{
    private Block crops;
    private Block soilBlockID;
    
    public ItemSeeds(final Block crops, final Block soilBlockID) {
        this.crops = crops;
        this.soilBlockID = soilBlockID;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock() == this.soilBlockID && world.isAirBlock(blockPos.up())) {
            world.setBlockState(blockPos.up(), this.crops.getDefaultState());
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
}
