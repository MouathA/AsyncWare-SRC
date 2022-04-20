package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockStone extends Block
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockStone.VARIANT) == EnumType.STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStone.VARIANT)).getMetadata();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStone.VARIANT)).getMetadata();
    }
    
    public BlockStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStone.VARIANT, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStone.VARIANT });
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStone.VARIANT)).func_181072_c();
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + EnumType.STONE.getUnlocalizedName() + ".name");
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] META_LOOKUP;
        
        GRANITE_SMOOTH("GRANITE_SMOOTH", 2, 2, MapColor.dirtColor, "smooth_granite", "graniteSmooth"), 
        STONE("STONE", 0, 0, MapColor.stoneColor, "stone");
        
        private static final EnumType[] $VALUES;
        
        ANDESITE("ANDESITE", 5, 5, MapColor.stoneColor, "andesite");
        
        private final MapColor field_181073_l;
        
        DIORITE("DIORITE", 3, 3, MapColor.quartzColor, "diorite");
        
        private final String name;
        
        GRANITE("GRANITE", 1, 1, MapColor.dirtColor, "granite"), 
        ANDESITE_SMOOTH("ANDESITE_SMOOTH", 6, 6, MapColor.stoneColor, "smooth_andesite", "andesiteSmooth");
        
        private final int meta;
        private final String unlocalizedName;
        
        DIORITE_SMOOTH("DIORITE_SMOOTH", 4, 4, MapColor.quartzColor, "smooth_diorite", "dioriteSmooth");
        
        public MapColor func_181072_c() {
            return this.field_181073_l;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.GRANITE, EnumType.GRANITE_SMOOTH, EnumType.DIORITE, EnumType.DIORITE_SMOOTH, EnumType.ANDESITE, EnumType.ANDESITE_SMOOTH };
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
        
        public int getMetadata() {
            return this.meta;
        }
        
        private EnumType(final String s, final int n, final int meta, final MapColor field_181073_l, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181073_l = field_181073_l;
        }
        
        private EnumType(final String s, final int n, final int n2, final MapColor mapColor, final String s2) {
            this(s, n, n2, mapColor, s2, s2);
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
