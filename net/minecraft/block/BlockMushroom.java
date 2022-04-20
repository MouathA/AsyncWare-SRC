package net.minecraft.block;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;

public class BlockMushroom extends BlockBush implements IGrowable
{
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.generateBigMushroom(world, blockPos, blockState, random);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: invokespecial   net/minecraft/block/BlockBush.canPlaceBlockAt:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //     6: ifeq            23
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: aload_0        
        //    13: invokevirtual   net/minecraft/block/BlockMushroom.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    16: iflt            23
        //    19: iconst_1       
        //    20: goto            24
        //    23: iconst_0       
        //    24: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0023 (coming from #0016).
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
    
    protected BlockMushroom() {
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: bipush          25
        //     4: invokevirtual   java/util/Random.nextInt:(I)I
        //     7: ifne            213
        //    10: aload_2        
        //    11: bipush          -4
        //    13: iconst_m1      
        //    14: bipush          -4
        //    16: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    19: aload_2        
        //    20: iconst_4       
        //    21: iconst_1       
        //    22: iconst_4       
        //    23: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    26: invokestatic    net/minecraft/util/BlockPos.getAllInBoxMutable:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
        //    29: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //    34: astore          7
        //    36: aload           7
        //    38: invokeinterface java/util/Iterator.hasNext:()Z
        //    43: ifeq            82
        //    46: aload           7
        //    48: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    53: checkcast       Lnet/minecraft/util/BlockPos;
        //    56: astore          8
        //    58: aload_1        
        //    59: aload           8
        //    61: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    64: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    69: aload_0        
        //    70: if_acmpne       79
        //    73: iinc            5, -1
        //    76: goto            36
        //    79: goto            36
        //    82: aload_2        
        //    83: aload           4
        //    85: iconst_3       
        //    86: invokevirtual   java/util/Random.nextInt:(I)I
        //    89: iconst_1       
        //    90: isub           
        //    91: aload           4
        //    93: iconst_2       
        //    94: invokevirtual   java/util/Random.nextInt:(I)I
        //    97: aload           4
        //    99: iconst_2       
        //   100: invokevirtual   java/util/Random.nextInt:(I)I
        //   103: isub           
        //   104: aload           4
        //   106: iconst_3       
        //   107: invokevirtual   java/util/Random.nextInt:(I)I
        //   110: iconst_1       
        //   111: isub           
        //   112: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   115: astore          7
        //   117: aload_1        
        //   118: aload           7
        //   120: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   123: ifeq            140
        //   126: aload_0        
        //   127: aload_1        
        //   128: aload           7
        //   130: aload_0        
        //   131: invokevirtual   net/minecraft/block/BlockMushroom.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   134: iflt            140
        //   137: aload           7
        //   139: astore_2       
        //   140: aload_2        
        //   141: aload           4
        //   143: iconst_3       
        //   144: invokevirtual   java/util/Random.nextInt:(I)I
        //   147: iconst_1       
        //   148: isub           
        //   149: aload           4
        //   151: iconst_2       
        //   152: invokevirtual   java/util/Random.nextInt:(I)I
        //   155: aload           4
        //   157: iconst_2       
        //   158: invokevirtual   java/util/Random.nextInt:(I)I
        //   161: isub           
        //   162: aload           4
        //   164: iconst_3       
        //   165: invokevirtual   java/util/Random.nextInt:(I)I
        //   168: iconst_1       
        //   169: isub           
        //   170: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   173: astore          7
        //   175: iinc            8, 1
        //   178: goto            117
        //   181: aload_1        
        //   182: aload           7
        //   184: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   187: ifeq            213
        //   190: aload_0        
        //   191: aload_1        
        //   192: aload           7
        //   194: aload_0        
        //   195: invokevirtual   net/minecraft/block/BlockMushroom.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   198: iflt            213
        //   201: aload_1        
        //   202: aload           7
        //   204: aload_0        
        //   205: invokevirtual   net/minecraft/block/BlockMushroom.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   208: iconst_2       
        //   209: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   212: pop            
        //   213: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0140 (coming from #0134).
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
    
    protected boolean canPlaceBlockOn(final Block block) {
        return block.isFullBlock();
    }
    
    public boolean generateBigMushroom(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        world.setBlockToAir(blockToAir);
        WorldGenerator worldGenerator = null;
        if (this == Blocks.brown_mushroom) {
            worldGenerator = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
        }
        else if (this == Blocks.red_mushroom) {
            worldGenerator = new WorldGenBigMushroom(Blocks.red_mushroom_block);
        }
        if (worldGenerator != null && worldGenerator.generate(world, random, blockToAir)) {
            return true;
        }
        world.setBlockState(blockToAir, blockState, 3);
        return false;
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return random.nextFloat() < 0.4;
    }
}
