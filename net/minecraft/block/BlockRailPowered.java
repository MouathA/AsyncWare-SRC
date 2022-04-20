package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public class BlockRailPowered extends BlockRailBase
{
    public static final PropertyEnum SHAPE;
    public static final PropertyBool POWERED;
    
    @Override
    public IProperty getShapeProperty() {
        return BlockRailPowered.SHAPE;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumRailDirection)blockState.getValue(BlockRailPowered.SHAPE)).getMetadata();
        if (blockState.getValue(BlockRailPowered.POWERED)) {}
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRailPowered.SHAPE, BlockRailPowered.POWERED });
    }
    
    @Override
    protected void onNeighborChangedInternal(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/block/BlockRailPowered.POWERED:Lnet/minecraft/block/properties/PropertyBool;
        //     4: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //     9: checkcast       Ljava/lang/Boolean;
        //    12: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    15: istore          5
        //    17: aload_1        
        //    18: aload_2        
        //    19: invokevirtual   net/minecraft/world/World.isBlockPowered:(Lnet/minecraft/util/BlockPos;)Z
        //    22: ifne            33
        //    25: aload_0        
        //    26: aload_1        
        //    27: aload_2        
        //    28: aload_3        
        //    29: aload_0        
        //    30: aload_1        
        //    31: aload_2        
        //    32: aload_3        
        //    33: iconst_1       
        //    34: goto            38
        //    37: iconst_0       
        //    38: istore          6
        //    40: iload           6
        //    42: iload           5
        //    44: if_icmpeq       104
        //    47: aload_1        
        //    48: aload_2        
        //    49: aload_3        
        //    50: getstatic       net/minecraft/block/BlockRailPowered.POWERED:Lnet/minecraft/block/properties/PropertyBool;
        //    53: iload           6
        //    55: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    58: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    63: iconst_3       
        //    64: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    67: pop            
        //    68: aload_1        
        //    69: aload_2        
        //    70: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    73: aload_0        
        //    74: invokevirtual   net/minecraft/world/World.notifyNeighborsOfStateChange:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)V
        //    77: aload_3        
        //    78: getstatic       net/minecraft/block/BlockRailPowered.SHAPE:Lnet/minecraft/block/properties/PropertyEnum;
        //    81: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    86: checkcast       Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
        //    89: invokevirtual   net/minecraft/block/BlockRailBase$EnumRailDirection.isAscending:()Z
        //    92: ifeq            104
        //    95: aload_1        
        //    96: aload_2        
        //    97: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   100: aload_0        
        //   101: invokevirtual   net/minecraft/world/World.notifyNeighborsOfStateChange:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)V
        //   104: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0033 (coming from #0032).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        SHAPE = PropertyEnum.create("shape", EnumRailDirection.class, (Predicate)new Predicate() {
            public boolean apply(final EnumRailDirection enumRailDirection) {
                return enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST;
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumRailDirection)o);
            }
        });
        POWERED = PropertyBool.create("powered");
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRailPowered.SHAPE, EnumRailDirection.byMetadata(n & 0x7)).withProperty(BlockRailPowered.POWERED, (n & 0x8) > 0);
    }
    
    protected BlockRailPowered() {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRailPowered.SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty(BlockRailPowered.POWERED, false));
    }
}
