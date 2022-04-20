package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public abstract class BlockStoneSlabNew extends BlockSlab
{
    public static final PropertyEnum VARIANT;
    public static final PropertyBool SEAMLESS;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlabNew.VARIANT)).func_181068_c();
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockStoneSlabNew.SEAMLESS, BlockStoneSlabNew.VARIANT }) : new BlockState(this, new IProperty[] { BlockStoneSlabNew.HALF, BlockStoneSlabNew.VARIANT });
    }
    
    static {
        SEAMLESS = PropertyBool.create("seamless");
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
            final EnumType[] values = EnumType.values();
            while (0 < values.length) {
                list.add(new ItemStack(item, 1, values[0].getMetadata()));
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".red_sandstone.name");
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return EnumType.byMetadata(itemStack.getMetadata() & 0x7);
    }
    
    @Override
    public IProperty getVariantProperty() {
        return BlockStoneSlabNew.VARIANT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlabNew.VARIANT, EnumType.byMetadata(n & 0x7));
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = withProperty.withProperty(BlockStoneSlabNew.SEAMLESS, (n & 0x8) != 0x0);
        }
        else {
            blockState = withProperty.withProperty(BlockStoneSlabNew.HALF, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public String getUnlocalizedName(final int n) {
        return super.getUnlocalizedName() + "." + EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlabNew.VARIANT)).getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumType)blockState.getValue(BlockStoneSlabNew.VARIANT)).getMetadata();
        if (this.isDouble()) {
            if (blockState.getValue(BlockStoneSlabNew.SEAMLESS)) {}
        }
        else if (blockState.getValue(BlockStoneSlabNew.HALF) == EnumBlockHalf.TOP) {}
        return 0;
    }
    
    public BlockStoneSlabNew() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty(BlockStoneSlabNew.SEAMLESS, false);
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlabNew.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlabNew.VARIANT, EnumType.RED_SANDSTONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        private static final EnumType[] META_LOOKUP;
        
        RED_SANDSTONE("RED_SANDSTONE", 0, 0, "red_sandstone", BlockSand.EnumType.RED_SAND.getMapColor());
        
        private final int meta;
        private final MapColor field_181069_e;
        private static final EnumType[] $VALUES;
        
        public MapColor func_181068_c() {
            return this.field_181069_e;
        }
        
        public String getUnlocalizedName() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final MapColor field_181069_e) {
            this.meta = meta;
            this.name = name;
            this.field_181069_e = field_181069_e;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.RED_SANDSTONE };
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
    }
}
