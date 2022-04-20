package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public abstract class BlockStoneSlab extends BlockSlab
{
    public static final PropertyBool SEAMLESS;
    public static final PropertyEnum VARIANT;
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlab.VARIANT)).func_181074_c();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlab.VARIANT)).getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab)) {
            final EnumType[] values = EnumType.values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                if (enumType != EnumType.WOOD) {
                    list.add(new ItemStack(item, 1, enumType.getMetadata()));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockStoneSlab.SEAMLESS, BlockStoneSlab.VARIANT }) : new BlockState(this, new IProperty[] { BlockStoneSlab.HALF, BlockStoneSlab.VARIANT });
    }
    
    @Override
    public String getUnlocalizedName(final int n) {
        return super.getUnlocalizedName() + "." + EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    static {
        SEAMLESS = PropertyBool.create("seamless");
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.byMetadata(n & 0x7));
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = withProperty.withProperty(BlockStoneSlab.SEAMLESS, (n & 0x8) != 0x0);
        }
        else {
            blockState = withProperty.withProperty(BlockStoneSlab.HALF, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumType)blockState.getValue(BlockStoneSlab.VARIANT)).getMetadata();
        if (this.isDouble()) {
            if (blockState.getValue(BlockStoneSlab.SEAMLESS)) {}
        }
        else if (blockState.getValue(BlockStoneSlab.HALF) == EnumBlockHalf.TOP) {}
        return 0;
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return EnumType.byMetadata(itemStack.getMetadata() & 0x7);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    public IProperty getVariantProperty() {
        return BlockStoneSlab.VARIANT;
    }
    
    public BlockStoneSlab() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty(BlockStoneSlab.SEAMLESS, false);
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlab.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlab.VARIANT, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        STONE("STONE", 0, 0, MapColor.stoneColor, "stone");
        
        private final int meta;
        private final String unlocalizedName;
        
        BRICK("BRICK", 4, 4, MapColor.redColor, "brick"), 
        WOOD("WOOD", 2, 2, MapColor.woodColor, "wood_old", "wood");
        
        private final MapColor field_181075_k;
        
        COBBLESTONE("COBBLESTONE", 3, 3, MapColor.stoneColor, "cobblestone", "cobble"), 
        SAND("SAND", 1, 1, MapColor.sandColor, "sandstone", "sand");
        
        private final String name;
        
        SMOOTHBRICK("SMOOTHBRICK", 5, 5, MapColor.stoneColor, "stone_brick", "smoothStoneBrick");
        
        private static final EnumType[] $VALUES;
        
        QUARTZ("QUARTZ", 7, 7, MapColor.quartzColor, "quartz"), 
        NETHERBRICK("NETHERBRICK", 6, 6, MapColor.netherrackColor, "nether_brick", "netherBrick");
        
        private static final EnumType[] META_LOOKUP;
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private EnumType(final String s, final int n, final int meta, final MapColor field_181075_k, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.field_181075_k = field_181075_k;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public MapColor func_181074_c() {
            return this.field_181075_k;
        }
        
        private EnumType(final String s, final int n, final int n2, final MapColor mapColor, final String s2) {
            this(s, n, n2, mapColor, s2, s2);
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.SAND, EnumType.WOOD, EnumType.COBBLESTONE, EnumType.BRICK, EnumType.SMOOTHBRICK, EnumType.NETHERBRICK, EnumType.QUARTZ };
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
