package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockStainedGlassPane extends BlockPane
{
    public static final PropertyEnum COLOR;
    
    public BlockStainedGlassPane() {
        super(Material.glass, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlassPane.NORTH, false).withProperty(BlockStainedGlassPane.EAST, false).withProperty(BlockStainedGlassPane.SOUTH, false).withProperty(BlockStainedGlassPane.WEST, false).withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlassPane.NORTH, BlockStainedGlassPane.EAST, BlockStainedGlassPane.WEST, BlockStainedGlassPane.SOUTH, BlockStainedGlassPane.COLOR });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.COLOR)).getMetadata();
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    static {
        COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.COLOR)).getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.COLOR)).getMapColor();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (0 < EnumDyeColor.values().length) {
            list.add(new ItemStack(item, 1, 0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.byMetadata(n));
    }
}
