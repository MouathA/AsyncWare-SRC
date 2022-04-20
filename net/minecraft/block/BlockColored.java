package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;

public class BlockColored extends Block
{
    public static final PropertyEnum COLOR;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockColored.COLOR)).getMetadata();
    }
    
    public BlockColored(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
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
    
    static {
        COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockColored.COLOR)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(n));
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockColored.COLOR)).getMapColor();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockColored.COLOR });
    }
}
