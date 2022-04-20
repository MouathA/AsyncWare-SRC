package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class BlockHay extends BlockRotatedPillar
{
    public BlockHay() {
        super(Material.grass, MapColor.yellowColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHay.AXIS, EnumFacing.Axis.Y));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHay.AXIS });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        final int n2 = n & 0xC;
        if (n2 == 4) {
            axis = EnumFacing.Axis.X;
        }
        else if (n2 == 8) {
            axis = EnumFacing.Axis.Z;
        }
        return this.getDefaultState().withProperty(BlockHay.AXIS, axis);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockHay.AXIS, enumFacing.getAxis());
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final EnumFacing.Axis axis = (EnumFacing.Axis)blockState.getValue(BlockHay.AXIS);
        if (axis != EnumFacing.Axis.X) {
            if (axis == EnumFacing.Axis.Z) {}
        }
        return 0;
    }
}
