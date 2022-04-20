package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;

public class BlockStainedGlass extends BlockBreakable
{
    public static final PropertyEnum COLOR;
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.byMetadata(n));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    public BlockStainedGlass(final Material material) {
        super(material, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.COLOR)).getMetadata();
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.COLOR)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlass.COLOR });
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.COLOR)).getMapColor();
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    static {
        COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }
}
