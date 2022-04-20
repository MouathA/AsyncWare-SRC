package net.minecraft.block;

import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class BlockIce extends BlockBreakable
{
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > 11 - this.getLightOpacity()) {
            if (world.provider.doesWaterVaporize()) {
                world.setBlockToAir(blockToAir);
            }
            else {
                this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
                world.setBlockState(blockToAir, Blocks.water.getDefaultState());
            }
        }
    }
    
    public BlockIce() {
        super(Material.ice, false);
        this.slipperiness = 0.98f;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public int getMobilityFlag() {
        return 0;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockToAir, final IBlockState blockState, final TileEntity tileEntity) {
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        entityPlayer.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(entityPlayer)) {
            final ItemStack stackedBlock = this.createStackedBlock(blockState);
            if (stackedBlock != null) {
                Block.spawnAsEntity(world, blockToAir, stackedBlock);
            }
        }
        else {
            if (world.provider.doesWaterVaporize()) {
                world.setBlockToAir(blockToAir);
                return;
            }
            this.dropBlockAsItem(world, blockToAir, blockState, EnchantmentHelper.getFortuneModifier(entityPlayer));
            final Material material = world.getBlockState(blockToAir.down()).getBlock().getMaterial();
            if (material.blocksMovement() || material.isLiquid()) {
                world.setBlockState(blockToAir, Blocks.flowing_water.getDefaultState());
            }
        }
    }
}
