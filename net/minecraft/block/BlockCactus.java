package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockCactus extends Block
{
    public static final PropertyInteger AGE;
    
    static {
        AGE = PropertyInteger.create("age", 0, 15);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + 1 - n, blockPos.getY() + 1 - n, blockPos.getZ() + 1 - n);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        entity.attackEntityFrom(DamageSource.cactus, 1.0f);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (blockPos != 0) {
            world.destroyBlock(blockPos, true);
        }
    }
    
    protected BlockCactus() {
        super(Material.cactus);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCactus.AGE, 0));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCactus.AGE, n);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final BlockPos up = blockPos.up();
        if (world.isAirBlock(up)) {
            while (world.getBlockState(blockPos.down(1)).getBlock() == this) {
                int n = 0;
                ++n;
            }
            final int intValue = (int)blockState.getValue(BlockCactus.AGE);
            if (intValue == 15) {
                world.setBlockState(up, this.getDefaultState());
                final IBlockState withProperty = blockState.withProperty(BlockCactus.AGE, 0);
                world.setBlockState(blockPos, withProperty, 4);
                this.onNeighborBlockChange(world, up, withProperty, this);
            }
            else {
                world.setBlockState(blockPos, blockState.withProperty(BlockCactus.AGE, intValue + 1), 4);
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCactus.AGE);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + 1 - n, blockPos.getY() + 1, blockPos.getZ() + 1 - n);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCactus.AGE });
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
