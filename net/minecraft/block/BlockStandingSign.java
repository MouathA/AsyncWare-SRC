package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockStandingSign extends BlockSign
{
    public static final PropertyInteger ROTATION;
    
    static {
        ROTATION = PropertyInteger.create("rotation", 0, 15);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStandingSign.ROTATION });
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.down()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockStandingSign.ROTATION);
    }
    
    public BlockStandingSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStandingSign.ROTATION, 0));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStandingSign.ROTATION, n);
    }
}
