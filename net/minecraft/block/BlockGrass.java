package net.minecraft.block;

import net.minecraft.world.biome.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.nquantum.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;

public class BlockGrass extends Block implements IGrowable
{
    public static final PropertyBool SNOWY;
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.getGrassColorAtPos(blockAccess, blockPos);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.up()) < 4 && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > 2) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
            else if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
                while (true) {
                    final BlockPos add = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    final Block block = world.getBlockState(add.up()).getBlock();
                    final IBlockState blockState2 = world.getBlockState(add);
                    if (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(add.up()) >= 4 && block.getLightOpacity() <= 2) {
                        world.setBlockState(add, Blocks.grass.getDefaultState());
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
    public EnumWorldBlockLayer getBlockLayer() {
        if (Asyncware.instance.moduleManager.getModuleByName("Xray").isToggled()) {
            return super.getBlockLayer();
        }
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return this.getBlockColor();
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        return blockState.withProperty(BlockGrass.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    static {
        SNOWY = PropertyBool.create("snowy");
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        final BlockPos up = blockPos.up();
        while (true) {
            final BlockPos blockPos2 = up;
            if (world.getBlockState(blockPos2).getBlock().blockMaterial == Material.air) {
                if (random.nextInt(8) == 0) {
                    final BlockFlower.EnumFlowerType pickRandomFlower = world.getBiomeGenForCoords(blockPos2).pickRandomFlower(random, blockPos2);
                    final BlockFlower block = pickRandomFlower.getBlockType().getBlock();
                    final IBlockState withProperty = block.getDefaultState().withProperty(block.getTypeProperty(), pickRandomFlower);
                    if (block.canBlockStay(world, blockPos2, withProperty)) {
                        world.setBlockState(blockPos2, withProperty, 3);
                    }
                }
                else {
                    final IBlockState withProperty2 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
                    if (Blocks.tallgrass.canBlockStay(world, blockPos2, withProperty2)) {
                        world.setBlockState(blockPos2, withProperty2, 3);
                    }
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockGrass.SNOWY });
    }
    
    protected BlockGrass() {
        super(Material.grass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockGrass.SNOWY, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
}
