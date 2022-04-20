package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockHugeMushroom extends Block
{
    private final Block smallBlock;
    public static final PropertyEnum VARIANT;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(this.smallBlock);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockHugeMushroom.VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return Math.max(0, random.nextInt(10) - 7);
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockHugeMushroom(final Material material, final MapColor mapColor, final Block smallBlock) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHugeMushroom.VARIANT, EnumType.ALL_OUTSIDE));
        this.smallBlock = smallBlock;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(this.smallBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        switch (BlockHugeMushroom$1.$SwitchMap$net$minecraft$block$BlockHugeMushroom$EnumType[((EnumType)blockState.getValue(BlockHugeMushroom.VARIANT)).ordinal()]) {
            case 1: {
                return MapColor.clothColor;
            }
            case 2: {
                return MapColor.sandColor;
            }
            case 3: {
                return MapColor.sandColor;
            }
            default: {
                return super.getMapColor(blockState);
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHugeMushroom.VARIANT });
    }
    
    public enum EnumType implements IStringSerializable
    {
        ALL_OUTSIDE("ALL_OUTSIDE", 11, 14, "all_outside"), 
        WEST("WEST", 3, 4, "west"), 
        NORTH("NORTH", 1, 2, "north"), 
        SOUTH("SOUTH", 7, 8, "south"), 
        ALL_STEM("ALL_STEM", 12, 15, "all_stem"), 
        CENTER("CENTER", 4, 5, "center"), 
        STEM("STEM", 9, 10, "stem"), 
        ALL_INSIDE("ALL_INSIDE", 10, 0, "all_inside"), 
        NORTH_EAST("NORTH_EAST", 2, 3, "north_east");
        
        private static final EnumType[] META_LOOKUP;
        
        SOUTH_EAST("SOUTH_EAST", 8, 9, "south_east");
        
        private static final EnumType[] $VALUES;
        
        SOUTH_WEST("SOUTH_WEST", 6, 7, "south_west"), 
        NORTH_WEST("NORTH_WEST", 0, 1, "north_west");
        
        private final String name;
        
        EAST("EAST", 5, 6, "east");
        
        private final int meta;
        
        static {
            $VALUES = new EnumType[] { EnumType.NORTH_WEST, EnumType.NORTH, EnumType.NORTH_EAST, EnumType.WEST, EnumType.CENTER, EnumType.EAST, EnumType.SOUTH_WEST, EnumType.SOUTH, EnumType.SOUTH_EAST, EnumType.STEM, EnumType.ALL_INSIDE, EnumType.ALL_OUTSIDE, EnumType.ALL_STEM };
            META_LOOKUP = new EnumType[16];
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
        
        private EnumType(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            final EnumType enumType = EnumType.META_LOOKUP[0];
            return (enumType == null) ? EnumType.META_LOOKUP[0] : enumType;
        }
    }
}
