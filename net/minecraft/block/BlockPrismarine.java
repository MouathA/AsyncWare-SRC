package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockPrismarine extends Block
{
    public static final int DARK_META;
    public static final PropertyEnum VARIANT;
    public static final int BRICKS_META;
    public static final int ROUGH_META;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPrismarine.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPrismarine.VARIANT });
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPrismarine.ROUGH_META));
        list.add(new ItemStack(item, 1, BlockPrismarine.BRICKS_META));
        list.add(new ItemStack(item, 1, BlockPrismarine.DARK_META));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPrismarine.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPrismarine.VARIANT)).getMetadata();
    }
    
    public BlockPrismarine() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPrismarine.VARIANT, EnumType.ROUGH));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
        ROUGH_META = EnumType.ROUGH.getMetadata();
        BRICKS_META = EnumType.BRICKS.getMetadata();
        DARK_META = EnumType.DARK.getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return (blockState.getValue(BlockPrismarine.VARIANT) == EnumType.ROUGH) ? MapColor.cyanColor : MapColor.diamondColor;
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + EnumType.ROUGH.getUnlocalizedName() + ".name");
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        
        DARK("DARK", 2, 2, "dark_prismarine", "dark");
        
        private final String unlocalizedName;
        private final int meta;
        private static final EnumType[] META_LOOKUP;
        private static final EnumType[] $VALUES;
        
        ROUGH("ROUGH", 0, 0, "prismarine", "rough"), 
        BRICKS("BRICKS", 1, 1, "prismarine_bricks", "bricks");
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.ROUGH, EnumType.BRICKS, EnumType.DARK };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
