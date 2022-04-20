package net.minecraft.block;

import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockNetherWart extends BlockBush
{
    public static final PropertyInteger AGE;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockNetherWart.AGE, n);
    }
    
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.soul_sand;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockNetherWart.AGE);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.nether_wart;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 3);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (world.isRemote) {
            return;
        }
        if ((int)blockState.getValue(BlockNetherWart.AGE) >= 3) {
            final int n3 = 2 + world.rand.nextInt(3);
            if (n2 > 0) {
                final int n4 = 1 + world.rand.nextInt(n2 + 1);
            }
        }
        while (true) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.nether_wart));
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNetherWart.AGE });
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, IBlockState withProperty, final Random random) {
        final int intValue = (int)withProperty.getValue(BlockNetherWart.AGE);
        if (intValue < 3 && random.nextInt(10) == 0) {
            withProperty = withProperty.withProperty(BlockNetherWart.AGE, intValue + 1);
            world.setBlockState(blockPos, withProperty, 2);
        }
        super.updateTick(world, blockPos, withProperty, random);
    }
    
    protected BlockNetherWart() {
        super(Material.plants, MapColor.redColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNetherWart.AGE, 0));
        this.setTickRandomly(true);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
    }
}
