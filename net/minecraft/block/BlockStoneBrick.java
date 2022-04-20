package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockStoneBrick extends Block
{
    public static final int CRACKED_META;
    public static final int MOSSY_META;
    public static final PropertyEnum VARIANT;
    public static final int CHISELED_META;
    public static final int DEFAULT_META;
    
    public BlockStoneBrick() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStoneBrick.VARIANT, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneBrick.VARIANT)).getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneBrick.VARIANT)).getMetadata();
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
        DEFAULT_META = EnumType.DEFAULT.getMetadata();
        MOSSY_META = EnumType.MOSSY.getMetadata();
        CRACKED_META = EnumType.CRACKED.getMetadata();
        CHISELED_META = EnumType.CHISELED.getMetadata();
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
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStoneBrick.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStoneBrick.VARIANT });
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        
        MOSSY("MOSSY", 1, 1, "mossy_stonebrick", "mossy"), 
        DEFAULT("DEFAULT", 0, 0, "stonebrick", "default");
        
        private final String unlocalizedName;
        
        CRACKED("CRACKED", 2, 2, "cracked_stonebrick", "cracked");
        
        private static final EnumType[] $VALUES;
        private final String name;
        
        CHISELED("CHISELED", 3, 3, "chiseled_stonebrick", "chiseled");
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.MOSSY, EnumType.CRACKED, EnumType.CHISELED };
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
