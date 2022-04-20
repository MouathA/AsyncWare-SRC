package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockRedSandstone extends Block
{
    public static final PropertyEnum TYPE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedSandstone.TYPE });
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockRedSandstone.TYPE)).getMetadata();
    }
    
    static {
        TYPE = PropertyEnum.create("type", EnumType.class);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockRedSandstone.TYPE)).getMetadata();
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
        return this.getDefaultState().withProperty(BlockRedSandstone.TYPE, EnumType.byMetadata(n));
    }
    
    public BlockRedSandstone() {
        super(Material.rock, BlockSand.EnumType.RED_SAND.getMapColor());
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedSandstone.TYPE, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        private final String unlocalizedName;
        
        SMOOTH("SMOOTH", 2, 2, "smooth_red_sandstone", "smooth"), 
        DEFAULT("DEFAULT", 0, 0, "red_sandstone", "default"), 
        CHISELED("CHISELED", 1, 1, "chiseled_red_sandstone", "chiseled");
        
        private final int meta;
        private static final EnumType[] $VALUES;
        private static final EnumType[] META_LOOKUP;
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
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
        
        public int getMetadata() {
            return this.meta;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
    }
}
