package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;

public class BlockCrops extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCrops.AGE });
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, 0);
        if (!world.isRemote) {
            final int intValue = (int)blockState.getValue(BlockCrops.AGE);
            if (intValue >= 7) {
                while (0 < 3 + n2) {
                    if (world.rand.nextInt(15) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getSeed(), 1, 0));
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
        }
    }
    
    protected static float getGrowthChance(final Block block, final World world, final BlockPos blockPos) {
        float n = 1.0f;
        final BlockPos down = blockPos.down();
        while (true) {
            float n2 = 0.0f;
            final IBlockState blockState = world.getBlockState(down.add(-1, 0, -1));
            if (blockState.getBlock() == Blocks.farmland) {
                n2 = 1.0f;
                if ((int)blockState.getValue(BlockFarmland.MOISTURE) > 0) {
                    n2 = 3.0f;
                }
            }
            n += n2 / 4.0f;
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    public void grow(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int n = (int)blockState.getValue(BlockCrops.AGE) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
        world.setBlockState(blockPos, blockState.withProperty(BlockCrops.AGE, 7), 2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCrops.AGE);
    }
    
    protected Item getCrop() {
        return Items.wheat;
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return (world.getLight(blockPos) >= 8 || world.canSeeSky(blockPos)) && this == world.getBlockState(blockPos.down()).getBlock();
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 7);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.getSeed();
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
            final int intValue = (int)blockState.getValue(BlockCrops.AGE);
            if (intValue < 7 && random.nextInt((int)(25.0f / getGrowthChance(this, world, blockPos)) + 1) == 0) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCrops.AGE, intValue + 1), 2);
            }
        }
    }
    
    protected Item getSeed() {
        return Items.wheat_seeds;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCrops.AGE, n);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return ((int)blockState.getValue(BlockCrops.AGE) == 7) ? this.getCrop() : this.getSeed();
    }
    
    protected BlockCrops() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCrops.AGE, 0));
        this.setTickRandomly(true);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(BlockCrops.soundTypeGrass);
        this.disableStats();
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockCrops.AGE) < 7;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.grow(world, blockPos, blockState);
    }
}
