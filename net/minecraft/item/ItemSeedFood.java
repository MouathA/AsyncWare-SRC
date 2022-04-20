package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeedFood extends ItemFood
{
    private Block soilId;
    private Block crops;
    
    public ItemSeedFood(final int n, final float n2, final Block crops, final Block soilId) {
        super(n, n2, false);
        this.crops = crops;
        this.soilId = soilId;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock() == this.soilId && world.isAirBlock(blockPos.up())) {
            world.setBlockState(blockPos.up(), this.crops.getDefaultState());
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
}
