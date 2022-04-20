package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockQuartz extends Block
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        final EnumType enumType = (EnumType)blockState.getValue(BlockQuartz.VARIANT);
        return (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) ? enumType.getMetadata() : EnumType.LINES_Y.getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, EnumType.DEFAULT.getMetadata()));
        list.add(new ItemStack(item, 1, EnumType.CHISELED.getMetadata()));
        list.add(new ItemStack(item, 1, EnumType.LINES_Y.getMetadata()));
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (n4 != EnumType.LINES_Y.getMetadata()) {
            return (n4 == EnumType.CHISELED.getMetadata()) ? this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.CHISELED) : this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.DEFAULT);
        }
        switch (BlockQuartz$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[enumFacing.getAxis().ordinal()]) {
            case 1: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Z);
            }
            case 2: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_X);
            }
            default: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockQuartz.VARIANT)).getMetadata();
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        final EnumType enumType = (EnumType)blockState.getValue(BlockQuartz.VARIANT);
        return (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) ? super.createStackedBlock(blockState) : new ItemStack(Item.getItemFromBlock(this), 1, EnumType.LINES_Y.getMetadata());
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.quartzColor;
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockQuartz.VARIANT });
    }
    
    public BlockQuartz() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockQuartz.VARIANT, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        CHISELED("CHISELED", 1, 1, "chiseled", "chiseled"), 
        LINES_X("LINES_X", 3, 3, "lines_x", "lines"), 
        LINES_Y("LINES_Y", 2, 2, "lines_y", "lines");
        
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        private final String unlocalizedName;
        private final String field_176805_h;
        
        LINES_Z("LINES_Z", 4, 4, "lines_z", "lines"), 
        DEFAULT("DEFAULT", 0, 0, "default", "default");
        
        private static final EnumType[] $VALUES;
        
        private EnumType(final String s, final int n, final int meta, final String field_176805_h, final String unlocalizedName) {
            this.meta = meta;
            this.field_176805_h = field_176805_h;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String toString() {
            return this.unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String getName() {
            return this.field_176805_h;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.LINES_Y, EnumType.LINES_X, EnumType.LINES_Z };
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
