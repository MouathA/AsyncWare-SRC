package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.world.*;

public class BlockWallSign extends BlockSign
{
    public static final PropertyDirection FACING;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockWallSign.FACING });
    }
    
    public BlockWallSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWallSign.FACING, EnumFacing.NORTH));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockWallSign.FACING, enumFacing);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing enumFacing = (EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockWallSign.FACING);
        final float n = 0.28125f;
        final float n2 = 0.78125f;
        final float n3 = 0.0f;
        final float n4 = 1.0f;
        final float n5 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        switch (BlockWallSign$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                break;
            }
            case 3: {
                this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                break;
            }
        }
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockWallSign.FACING)).getIndex();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockWallSign.FACING)).getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
}
