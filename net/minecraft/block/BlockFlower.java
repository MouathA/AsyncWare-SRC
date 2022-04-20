package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;

public abstract class BlockFlower extends BlockBush
{
    protected PropertyEnum type;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(this.getTypeProperty(), EnumFlowerType.getType(this.getBlockType(), n));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumFlowerType)blockState.getValue(this.getTypeProperty())).getMeta();
    }
    
    public abstract EnumFlowerColor getBlockType();
    
    public IProperty getTypeProperty() {
        if (this.type == null) {
            this.type = PropertyEnum.create("type", EnumFlowerType.class, (Predicate)new Predicate(this) {
                final BlockFlower this$0;
                
                public boolean apply(final EnumFlowerType enumFlowerType) {
                    return enumFlowerType.getBlockType() == this.this$0.getBlockType();
                }
                
                public boolean apply(final Object o) {
                    return this.apply((EnumFlowerType)o);
                }
            });
        }
        return this.type;
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFlowerType)blockState.getValue(this.getTypeProperty())).getMeta();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { this.getTypeProperty() });
    }
    
    protected BlockFlower() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getTypeProperty(), (this.getBlockType() == EnumFlowerColor.RED) ? EnumFlowerType.POPPY : EnumFlowerType.DANDELION));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumFlowerType[] types = EnumFlowerType.getTypes(this.getBlockType());
        while (0 < types.length) {
            list.add(new ItemStack(item, 1, types[0].getMeta()));
            int n = 0;
            ++n;
        }
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        private final String name;
        private static final EnumFlowerType[][] TYPES_FOR_BLOCK;
        
        DANDELION("DANDELION", 0, EnumFlowerColor.YELLOW, 0, "dandelion"), 
        ALLIUM("ALLIUM", 3, EnumFlowerColor.RED, 2, "allium");
        
        private final int meta;
        
        ORANGE_TULIP("ORANGE_TULIP", 6, EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"), 
        BLUE_ORCHID("BLUE_ORCHID", 2, EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"), 
        POPPY("POPPY", 1, EnumFlowerColor.RED, 0, "poppy");
        
        private final EnumFlowerColor blockType;
        private static final EnumFlowerType[] $VALUES;
        
        WHITE_TULIP("WHITE_TULIP", 7, EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"), 
        RED_TULIP("RED_TULIP", 5, EnumFlowerColor.RED, 4, "red_tulip", "tulipRed");
        
        private final String unlocalizedName;
        
        HOUSTONIA("HOUSTONIA", 4, EnumFlowerColor.RED, 3, "houstonia"), 
        OXEYE_DAISY("OXEYE_DAISY", 9, EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy"), 
        PINK_TULIP("PINK_TULIP", 8, EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink");
        
        public static EnumFlowerType[] getTypes(final EnumFlowerColor enumFlowerColor) {
            return EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private EnumFlowerType(final String s, final int n, final EnumFlowerColor blockType, final int meta, final String name, final String unlocalizedName) {
            this.blockType = blockType;
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumFlowerType[] { EnumFlowerType.DANDELION, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY };
            TYPES_FOR_BLOCK = new EnumFlowerType[EnumFlowerColor.values().length][];
            final EnumFlowerColor[] values = EnumFlowerColor.values();
            while (0 < values.length) {
                final EnumFlowerColor enumFlowerColor = values[0];
                final Collection filter = Collections2.filter((Collection)Lists.newArrayList((Object[])values()), (Predicate)new Predicate(enumFlowerColor) {
                    final EnumFlowerColor val$blockflower$enumflowercolor;
                    
                    public boolean apply(final Object o) {
                        return this.apply((EnumFlowerType)o);
                    }
                    
                    public boolean apply(final EnumFlowerType enumFlowerType) {
                        return enumFlowerType.getBlockType() == this.val$blockflower$enumflowercolor;
                    }
                });
                EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()] = filter.toArray(new EnumFlowerType[filter.size()]);
                int n = 0;
                ++n;
            }
        }
        
        public static EnumFlowerType getType(final EnumFlowerColor enumFlowerColor, final int n) {
            final EnumFlowerType[] array = EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
            if (0 >= array.length) {}
            return array[0];
        }
        
        private EnumFlowerType(final String s, final int n, final EnumFlowerColor enumFlowerColor, final int n2, final String s2) {
            this(s, n, enumFlowerColor, n2, s2, s2);
        }
        
        public EnumFlowerColor getBlockType() {
            return this.blockType;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public enum EnumFlowerColor
    {
        private static final EnumFlowerColor[] $VALUES;
        
        RED("RED", 1), 
        YELLOW("YELLOW", 0);
        
        public BlockFlower getBlock() {
            return (this == EnumFlowerColor.YELLOW) ? Blocks.yellow_flower : Blocks.red_flower;
        }
        
        private EnumFlowerColor(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumFlowerColor[] { EnumFlowerColor.YELLOW, EnumFlowerColor.RED };
        }
    }
}
