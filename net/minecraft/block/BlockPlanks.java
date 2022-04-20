package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockPlanks extends Block
{
    public static final PropertyEnum VARIANT;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPlanks.VARIANT });
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
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPlanks.VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPlanks.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPlanks.VARIANT)).getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPlanks.VARIANT)).func_181070_c();
    }
    
    public BlockPlanks() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPlanks.VARIANT, EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String unlocalizedName;
        
        SPRUCE("SPRUCE", 1, 1, "spruce", MapColor.obsidianColor);
        
        private final String name;
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        
        BIRCH("BIRCH", 2, 2, "birch", MapColor.sandColor);
        
        private static final EnumType[] $VALUES;
        
        JUNGLE("JUNGLE", 3, 3, "jungle", MapColor.dirtColor), 
        DARK_OAK("DARK_OAK", 5, 5, "dark_oak", "big_oak", MapColor.brownColor), 
        OAK("OAK", 0, 0, "oak", MapColor.woodColor), 
        ACACIA("ACACIA", 4, 4, "acacia", MapColor.adobeColor);
        
        private final MapColor field_181071_k;
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName, final MapColor field_181071_k) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181071_k = field_181071_k;
        }
        
        private EnumType(final String s, final int n, final int n2, final String s2, final MapColor mapColor) {
            this(s, n, n2, s2, s2, mapColor);
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public MapColor func_181070_c() {
            return this.field_181071_k;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.OAK, EnumType.SPRUCE, EnumType.BIRCH, EnumType.JUNGLE, EnumType.ACACIA, EnumType.DARK_OAK };
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
        public String getName() {
            return this.name;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
