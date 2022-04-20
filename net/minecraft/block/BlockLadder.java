package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public class BlockLadder extends Block
{
    public static final PropertyDirection FACING;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockLadder.FACING)).getIndex();
    }
    
    protected BlockLadder() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLadder.FACING, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockToAir, (EnumFacing)blockState.getValue(BlockLadder.FACING))) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLadder.FACING });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockLadder.FACING, enumFacing);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (enumFacing.getAxis().isHorizontal() && this.canBlockStay(world, blockPos, enumFacing)) {
            return this.getDefaultState().withProperty(BlockLadder.FACING, enumFacing);
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (this.canBlockStay(world, blockPos, enumFacing2)) {
                return this.getDefaultState().withProperty(BlockLadder.FACING, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.west()).getBlock().isNormalCube() || world.getBlockState(blockPos.east()).getBlock().isNormalCube() || world.getBlockState(blockPos.north()).getBlock().isNormalCube() || world.getBlockState(blockPos.south()).getBlock().isNormalCube();
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    protected boolean canBlockStay(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final float n = 0.125f;
            switch (BlockLadder$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockState.getValue(BlockLadder.FACING)).ordinal()]) {
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
                    break;
                }
                case 3: {
                    this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                default: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
}
