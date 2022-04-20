package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;

public class BlockBush extends Block
{
    protected BlockBush(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this != world.getBlockState(blockPos.down()).getBlock();
    }
    
    protected BlockBush(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setTickRandomly(true);
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 3.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    protected void checkAndDropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    protected BlockBush() {
        this(Material.plants);
    }
}
