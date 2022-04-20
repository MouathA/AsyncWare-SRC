package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockDirt extends Block
{
    public static final PropertyEnum VARIANT;
    public static final PropertyBool SNOWY;
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return (blockState.getBlock() != this) ? 0 : ((DirtType)blockState.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
            final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
            withProperty = withProperty.withProperty(BlockDirt.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
        }
        return withProperty;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((DirtType)blockState.getValue(BlockDirt.VARIANT)).func_181066_d();
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", DirtType.class);
        SNOWY = PropertyBool.create("snowy");
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        DirtType dirt = (DirtType)blockState.getValue(BlockDirt.VARIANT);
        if (dirt == DirtType.PODZOL) {
            dirt = DirtType.DIRT;
        }
        return dirt.getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
    }
    
    protected BlockDirt() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirt.VARIANT, DirtType.DIRT).withProperty(BlockDirt.SNOWY, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((DirtType)blockState.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDirt.VARIANT, BlockDirt.SNOWY });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.byMetadata(n));
    }
    
    public enum DirtType implements IStringSerializable
    {
        PODZOL("PODZOL", 2, 2, "podzol", MapColor.obsidianColor), 
        DIRT("DIRT", 0, 0, "dirt", "default", MapColor.dirtColor);
        
        private final String name;
        private static final DirtType[] METADATA_LOOKUP;
        private final String unlocalizedName;
        
        COARSE_DIRT("COARSE_DIRT", 1, 1, "coarse_dirt", "coarse", MapColor.dirtColor);
        
        private final MapColor field_181067_h;
        private static final DirtType[] $VALUES;
        private final int metadata;
        
        static {
            $VALUES = new DirtType[] { DirtType.DIRT, DirtType.COARSE_DIRT, DirtType.PODZOL };
            METADATA_LOOKUP = new DirtType[values().length];
            final DirtType[] values = values();
            while (0 < values.length) {
                final DirtType dirtType = values[0];
                DirtType.METADATA_LOOKUP[dirtType.getMetadata()] = dirtType;
                int n = 0;
                ++n;
            }
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        private DirtType(final String s, final int n, final int n2, final String s2, final MapColor mapColor) {
            this(s, n, n2, s2, s2, mapColor);
        }
        
        private DirtType(final String s, final int n, final int metadata, final String name, final String unlocalizedName, final MapColor field_181067_h) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181067_h = field_181067_h;
        }
        
        public MapColor func_181066_d() {
            return this.field_181067_h;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public static DirtType byMetadata(final int n) {
            if (0 >= DirtType.METADATA_LOOKUP.length) {}
            return DirtType.METADATA_LOOKUP[0];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
