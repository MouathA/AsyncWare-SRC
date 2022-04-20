package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
            if (block == Blocks.grass) {
                return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
            }
            if (block == Blocks.dirt) {
                switch (ItemHoe$1.$SwitchMap$net$minecraft$block$BlockDirt$DirtType[((BlockDirt.DirtType)blockState.getValue(BlockDirt.VARIANT)).ordinal()]) {
                    case 1: {
                        return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
                    }
                    case 2: {
                        return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return false;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
    
    protected boolean useHoe(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, blockState.getBlock().stepSound.getStepSound(), (blockState.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, blockState.getBlock().stepSound.getFrequency() * 0.8f);
        if (world.isRemote) {
            return true;
        }
        world.setBlockState(blockPos, blockState);
        itemStack.damageItem(1, entityPlayer);
        return true;
    }
    
    public ItemHoe(final ToolMaterial theToolMaterial) {
        this.theToolMaterial = theToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(theToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
}
