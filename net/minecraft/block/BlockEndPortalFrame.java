package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockEndPortalFrame extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyBool EYE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockEndPortalFrame.FACING, BlockEndPortalFrame.EYE });
    }
    
    public BlockEndPortalFrame() {
        super(Material.rock, MapColor.greenColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockEndPortalFrame.FACING, EnumFacing.NORTH).withProperty(BlockEndPortalFrame.EYE, false));
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getValue(BlockEndPortalFrame.EYE) ? 15 : 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockEndPortalFrame.FACING, entityLivingBase.getHorizontalFacing().getOpposite()).withProperty(BlockEndPortalFrame.EYE, false);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        if (world.getBlockState(blockPos).getValue(BlockEndPortalFrame.EYE)) {
            this.setBlockBounds(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        this.setBlockBoundsForItemRender();
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        EYE = PropertyBool.create("eye");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockEndPortalFrame.FACING)).getHorizontalIndex();
        if (blockState.getValue(BlockEndPortalFrame.EYE)) {}
        return 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockEndPortalFrame.EYE, (n & 0x4) != 0x0).withProperty(BlockEndPortalFrame.FACING, EnumFacing.getHorizontal(n & 0x3));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
}
