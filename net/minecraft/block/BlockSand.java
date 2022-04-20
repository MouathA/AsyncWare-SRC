package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockSand extends BlockFalling
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT)).getMetadata();
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSand.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT)).getMapColor();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSand.VARIANT });
    }
    
    public BlockSand() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSand.VARIANT, EnumType.SAND));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final int meta;
        
        SAND("SAND", 0, 0, "sand", "default", MapColor.sandColor), 
        RED_SAND("RED_SAND", 1, 1, "red_sand", "red", MapColor.adobeColor);
        
        private static final EnumType[] $VALUES;
        private final MapColor mapColor;
        private final String unlocalizedName;
        private final String name;
        private static final EnumType[] META_LOOKUP;
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName, final MapColor mapColor) {
            this.meta = meta;
            this.name = name;
            this.mapColor = mapColor;
            this.unlocalizedName = unlocalizedName;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.SAND, EnumType.RED_SAND };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        public MapColor getMapColor() {
            return this.mapColor;
        }
    }
}
