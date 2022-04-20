package net.minecraft.block;

import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockMycelium extends Block
{
    public static final PropertyBool SNOWY;
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.up()) < 4 && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > 2) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
            }
            else if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
                while (true) {
                    final BlockPos add = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    final IBlockState blockState2 = world.getBlockState(add);
                    final Block block = world.getBlockState(add.up()).getBlock();
                    if (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(add.up()) >= 4 && block.getLightOpacity() <= 2) {
                        world.setBlockState(add, this.getDefaultState());
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockMycelium.SNOWY });
    }
    
    protected BlockMycelium() {
        super(Material.grass, MapColor.purpleColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockMycelium.SNOWY, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    static {
        SNOWY = PropertyBool.create("snowy");
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.randomDisplayTick(world, blockPos, blockState, random);
        if (random.nextInt(10) == 0) {
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, blockPos.getX() + random.nextFloat(), blockPos.getY() + 1.1f, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        return blockState.withProperty(BlockMycelium.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
    }
}
