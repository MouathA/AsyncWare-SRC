package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class BlockSlab extends Block
{
    public static final PropertyEnum HALF;
    
    protected boolean canSilkHarvest() {
        return false;
    }
    
    public abstract String getUnlocalizedName(final int p0);
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public abstract Object getVariant(final ItemStack p0);
    
    @Override
    public int quantityDropped(final Random random) {
        return this.isDouble() ? 2 : 1;
    }
    
    public abstract IProperty getVariantProperty();
    
    public BlockSlab(final Material material) {
        super(material);
        if (this.isDouble()) {
            this.fullBlock = true;
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p0, final BlockPos p1, final EnumFacing p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/block/BlockSlab.isDouble:()Z
        //     4: ifeq            15
        //     7: aload_0        
        //     8: aload_1        
        //     9: aload_2        
        //    10: aload_3        
        //    11: invokespecial   net/minecraft/block/Block.shouldSideBeRendered:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //    14: ireturn        
        //    15: aload_3        
        //    16: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //    19: if_acmpeq       41
        //    22: aload_3        
        //    23: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //    26: if_acmpeq       41
        //    29: aload_0        
        //    30: aload_1        
        //    31: aload_2        
        //    32: aload_3        
        //    33: invokespecial   net/minecraft/block/Block.shouldSideBeRendered:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //    36: ifne            41
        //    39: iconst_0       
        //    40: ireturn        
        //    41: aload_2        
        //    42: aload_3        
        //    43: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    46: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    49: astore          4
        //    51: aload_1        
        //    52: aload_2        
        //    53: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    58: astore          5
        //    60: aload_1        
        //    61: aload           4
        //    63: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    68: astore          6
        //    70: aload           5
        //    72: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    77: if_acmpeq       100
        //    80: aload           5
        //    82: getstatic       net/minecraft/block/BlockSlab.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    85: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    90: getstatic       net/minecraft/block/BlockSlab$EnumBlockHalf.TOP:Lnet/minecraft/block/BlockSlab$EnumBlockHalf;
        //    93: if_acmpne       100
        //    96: iconst_1       
        //    97: goto            101
        //   100: iconst_0       
        //   101: istore          7
        //   103: aload           6
        //   105: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   110: if_acmpeq       133
        //   113: aload           6
        //   115: getstatic       net/minecraft/block/BlockSlab.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   118: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   123: getstatic       net/minecraft/block/BlockSlab$EnumBlockHalf.TOP:Lnet/minecraft/block/BlockSlab$EnumBlockHalf;
        //   126: if_acmpne       133
        //   129: iconst_1       
        //   130: goto            134
        //   133: iconst_0       
        //   134: istore          8
        //   136: iload           8
        //   138: ifeq            196
        //   141: aload_3        
        //   142: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //   145: if_acmpne       152
        //   148: iconst_1       
        //   149: goto            248
        //   152: aload_3        
        //   153: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   156: if_acmpne       173
        //   159: aload_0        
        //   160: aload_1        
        //   161: aload_2        
        //   162: aload_3        
        //   163: invokespecial   net/minecraft/block/Block.shouldSideBeRendered:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //   166: ifeq            173
        //   169: iconst_1       
        //   170: goto            248
        //   173: aload           5
        //   175: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   180: if_acmpeq       188
        //   183: iload           7
        //   185: ifne            192
        //   188: iconst_1       
        //   189: goto            248
        //   192: iconst_0       
        //   193: goto            248
        //   196: aload_3        
        //   197: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   200: if_acmpne       207
        //   203: iconst_1       
        //   204: goto            248
        //   207: aload_3        
        //   208: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //   211: if_acmpne       228
        //   214: aload_0        
        //   215: aload_1        
        //   216: aload_2        
        //   217: aload_3        
        //   218: invokespecial   net/minecraft/block/Block.shouldSideBeRendered:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //   221: ifeq            228
        //   224: iconst_1       
        //   225: goto            248
        //   228: aload           5
        //   230: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   235: if_acmpeq       243
        //   238: iload           7
        //   240: ifeq            247
        //   243: iconst_1       
        //   244: goto            248
        //   247: iconst_0       
        //   248: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.isDouble();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
        return this.isDouble() ? withProperty : ((enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) ? withProperty : withProperty.withProperty(BlockSlab.HALF, EnumBlockHalf.TOP));
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final IBlockState blockState = blockAccess.getBlockState(blockPos);
            if (blockState.getBlock() == this) {
                if (blockState.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
                    this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return super.getDamageValue(world, blockPos) & 0x7;
    }
    
    static {
        HALF = PropertyEnum.create("half", EnumBlockHalf.class);
    }
    
    public abstract boolean isDouble();
    
    @Override
    public boolean isFullCube() {
        return this.isDouble();
    }
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        private static final EnumBlockHalf[] $VALUES;
        private final String name;
        
        TOP("TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "bottom");
        
        static {
            $VALUES = new EnumBlockHalf[] { EnumBlockHalf.TOP, EnumBlockHalf.BOTTOM };
        }
        
        private EnumBlockHalf(final String s, final int n, final String name) {
            this.name = name;
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
