package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockRail extends BlockRailBase
{
    public static final PropertyEnum SHAPE;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumRailDirection)blockState.getValue(BlockRail.SHAPE)).getMetadata();
    }
    
    protected BlockRail() {
        super(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRail.SHAPE, EnumRailDirection.NORTH_SOUTH));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRail.SHAPE, EnumRailDirection.byMetadata(n));
    }
    
    static {
        SHAPE = PropertyEnum.create("shape", EnumRailDirection.class);
    }
    
    @Override
    public IProperty getShapeProperty() {
        return BlockRail.SHAPE;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRail.SHAPE });
    }
    
    @Override
    protected void onNeighborChangedInternal(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (block.canProvidePower() && new Rail(world, blockPos, blockState).countAdjacentRails() == 3) {
            this.func_176564_a(world, blockPos, blockState, false);
        }
    }
}
