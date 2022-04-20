package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockCocoa extends BlockDirectional implements IGrowable
{
    public static final PropertyInteger AGE;
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockCocoa.FACING);
        final int intValue = (int)blockState.getValue(BlockCocoa.AGE);
        final int n = 4 + intValue * 2;
        final int n2 = 5 + intValue * 2;
        final float n3 = n / 2.0f;
        switch (BlockCocoa$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, (15.0f - n) / 16.0f, (8.0f + n3) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 2: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, 0.0625f, (8.0f + n3) / 16.0f, 0.75f, (1.0f + n) / 16.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0625f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, (1.0f + n) / 16.0f, 0.75f, (8.0f + n3) / 16.0f);
                break;
            }
            case 4: {
                this.setBlockBounds((15.0f - n) / 16.0f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, 0.9375f, 0.75f, (8.0f + n3) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCocoa.FACING, BlockCocoa.AGE });
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 2);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.dye;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCocoa.FACING, EnumFacing.getHorizontal(n)).withProperty(BlockCocoa.AGE, (n & 0xF) >> 2);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.AGE, (int)blockState.getValue(BlockCocoa.AGE) + 1), 2);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (blockPos == blockState) {
            this.dropBlock(world, blockPos, blockState);
        }
        else if (world.rand.nextInt(5) == 0) {
            final int intValue = (int)blockState.getValue(BlockCocoa.AGE);
            if (intValue < 2) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.AGE, intValue + 1), 2);
            }
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return EnumDyeColor.BROWN.getDyeDamage();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if ((int)blockState.getValue(BlockCocoa.AGE) >= 2) {}
        while (true) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()));
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.FACING, EnumFacing.fromAngle(entityLivingBase.rotationYaw)), 2);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, EnumFacing north, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (!north.getAxis().isHorizontal()) {
            north = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockCocoa.FACING, north.getOpposite()).withProperty(BlockCocoa.AGE, 0);
    }
    
    private void dropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        this.dropBlockAsItem(world, blockPos, blockState, 0);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockCocoa.AGE) < 2;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockCocoa.FACING)).getHorizontalIndex();
        final int n2 = 0x0 | (int)blockState.getValue(BlockCocoa.AGE) << 2;
        return 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (blockPos == blockState) {
            this.dropBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    public BlockCocoa() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCocoa.FACING, EnumFacing.NORTH).withProperty(BlockCocoa.AGE, 0));
        this.setTickRandomly(true);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
}
