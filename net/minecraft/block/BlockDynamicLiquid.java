package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockDynamicLiquid extends BlockLiquid
{
    int adjacentSourceBlocks;
    
    private void tryFlowInto(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockPos != blockState) {
            if (blockState.getBlock() != Blocks.air) {
                if (this.blockMaterial == Material.lava) {
                    this.triggerMixEffects(world, blockPos);
                }
                else {
                    blockState.getBlock().dropBlockAsItem(world, blockPos, blockState, 0);
                }
            }
            world.setBlockState(blockPos, this.getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, n), 3);
        }
    }
    
    private void placeStaticBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, BlockLiquid.getStaticBlock(this.blockMaterial).getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, blockState.getValue(BlockDynamicLiquid.LEVEL)), 2);
    }
    
    private Set getPossibleFlowDirections(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokestatic    java/util/EnumSet.noneOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //     5: astore          4
        //     7: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //    10: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //    13: astore          5
        //    15: aload           5
        //    17: invokeinterface java/util/Iterator.hasNext:()Z
        //    22: ifeq            159
        //    25: aload           5
        //    27: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    32: astore          6
        //    34: aload           6
        //    36: checkcast       Lnet/minecraft/util/EnumFacing;
        //    39: astore          7
        //    41: aload_2        
        //    42: aload           7
        //    44: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    47: astore          8
        //    49: aload_1        
        //    50: aload           8
        //    52: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    55: astore          9
        //    57: aload_0        
        //    58: aload_1        
        //    59: aload           8
        //    61: aload           9
        //    63: ifne            156
        //    66: aload           9
        //    68: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    73: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    76: aload_0        
        //    77: getfield        net/minecraft/block/BlockDynamicLiquid.blockMaterial:Lnet/minecraft/block/material/Material;
        //    80: if_acmpne       102
        //    83: aload           9
        //    85: getstatic       net/minecraft/block/BlockDynamicLiquid.LEVEL:Lnet/minecraft/block/properties/PropertyInteger;
        //    88: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    93: checkcast       Ljava/lang/Integer;
        //    96: invokevirtual   java/lang/Integer.intValue:()I
        //    99: ifle            156
        //   102: aload_0        
        //   103: aload_1        
        //   104: aload           8
        //   106: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   109: aload_1        
        //   110: aload           8
        //   112: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   115: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   118: ifne            139
        //   121: aload_0        
        //   122: aload_1        
        //   123: aload           8
        //   125: iconst_1       
        //   126: aload           7
        //   128: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   131: invokespecial   net/minecraft/block/BlockDynamicLiquid.func_176374_a:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;ILnet/minecraft/util/EnumFacing;)I
        //   134: istore          10
        //   136: goto            139
        //   139: aload           4
        //   141: invokeinterface java/util/Set.clear:()V
        //   146: aload           4
        //   148: aload           7
        //   150: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   155: pop            
        //   156: goto            15
        //   159: aload           4
        //   161: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0156).
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
    
    protected int checkAdjacentBlock(final World world, final BlockPos blockPos, final int n) {
        this.getLevel(world, blockPos);
        ++this.adjacentSourceBlocks;
        return (n >= 0 && 0 >= n) ? n : 0;
    }
    
    private int func_176374_a(final World p0, final BlockPos p1, final int p2, final EnumFacing p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //     6: astore          6
        //     8: aload           6
        //    10: invokeinterface java/util/Iterator.hasNext:()Z
        //    15: ifeq            153
        //    18: aload           6
        //    20: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    25: astore          7
        //    27: aload           7
        //    29: checkcast       Lnet/minecraft/util/EnumFacing;
        //    32: astore          8
        //    34: aload           8
        //    36: aload           4
        //    38: if_acmpeq       150
        //    41: aload_2        
        //    42: aload           8
        //    44: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    47: astore          9
        //    49: aload_1        
        //    50: aload           9
        //    52: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    55: astore          10
        //    57: aload_0        
        //    58: aload_1        
        //    59: aload           9
        //    61: aload           10
        //    63: ifne            150
        //    66: aload           10
        //    68: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    73: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    76: aload_0        
        //    77: getfield        net/minecraft/block/BlockDynamicLiquid.blockMaterial:Lnet/minecraft/block/material/Material;
        //    80: if_acmpne       102
        //    83: aload           10
        //    85: getstatic       net/minecraft/block/BlockDynamicLiquid.LEVEL:Lnet/minecraft/block/properties/PropertyInteger;
        //    88: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    93: checkcast       Ljava/lang/Integer;
        //    96: invokevirtual   java/lang/Integer.intValue:()I
        //    99: ifle            150
        //   102: aload_0        
        //   103: aload_1        
        //   104: aload           9
        //   106: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   109: aload           10
        //   111: ifne            116
        //   114: iload_3        
        //   115: ireturn        
        //   116: iload_3        
        //   117: iconst_4       
        //   118: if_icmpge       150
        //   121: aload_0        
        //   122: aload_1        
        //   123: aload           9
        //   125: iload_3        
        //   126: iconst_1       
        //   127: iadd           
        //   128: aload           8
        //   130: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   133: invokespecial   net/minecraft/block/BlockDynamicLiquid.func_176374_a:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;ILnet/minecraft/util/EnumFacing;)I
        //   136: istore          11
        //   138: iload           11
        //   140: sipush          1000
        //   143: if_icmpge       150
        //   146: iload           11
        //   148: istore          5
        //   150: goto            8
        //   153: sipush          1000
        //   156: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0150 (coming from #0063).
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
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        (int)blockState.getValue(BlockDynamicLiquid.LEVEL);
        if (this.blockMaterial != Material.lava || !world.provider.doesWaterVaporize()) {}
        this.tickRate(world);
        this.placeStaticBlock(world, blockPos, blockState);
        final IBlockState blockState2 = world.getBlockState(blockPos.down());
        if (blockPos.down() != blockState2) {
            if (this.blockMaterial == Material.lava && world.getBlockState(blockPos.down()).getBlock().getMaterial() == Material.water) {
                world.setBlockState(blockPos.down(), Blocks.stone.getDefaultState());
                this.triggerMixEffects(world, blockPos.down());
                return;
            }
            this.tryFlowInto(world, blockPos.down(), blockState2, 8);
        }
        else {
            for (final EnumFacing enumFacing : this.getPossibleFlowDirections(world, blockPos)) {
                this.tryFlowInto(world, blockPos.offset(enumFacing), world.getBlockState(blockPos.offset(enumFacing)), 1);
            }
        }
    }
    
    protected BlockDynamicLiquid(final Material material) {
        super(material);
    }
}
