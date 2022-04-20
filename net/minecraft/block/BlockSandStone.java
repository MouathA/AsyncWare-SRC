package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockSandStone extends Block
{
    public static final PropertyEnum TYPE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSandStone.TYPE });
    }
    
    public BlockSandStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSandStone.TYPE, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.sandColor;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSandStone.TYPE)).getMetadata();
    }
    
    static {
        TYPE = PropertyEnum.create("type", EnumType.class);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSandStone.TYPE)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSandStone.TYPE, EnumType.byMetadata(n));
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
        SMOOTH("SMOOTH", 2, 2, "smooth_sandstone", "smooth");
        
        private final int metadata;
        private final String unlocalizedName;
        private final String name;
        
        DEFAULT("DEFAULT", 0, 0, "sandstone", "default");
        
        private static final EnumType[] META_LOOKUP;
        
        CHISELED("CHISELED", 1, 1, "chiseled_sandstone", "chiseled");
        
        private static final EnumType[] $VALUES;
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int metadata, final String name, final String unlocalizedName) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
    }
}
