package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import java.util.*;

public class BlockStaticLiquid extends BlockLiquid
{
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            this.updateLiquid(world, blockPos, blockState);
        }
    }
    
    private void updateLiquid(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockDynamicLiquid flowingBlock = BlockLiquid.getFlowingBlock(this.blockMaterial);
        world.setBlockState(blockPos, flowingBlock.getDefaultState().withProperty(BlockStaticLiquid.LEVEL, blockState.getValue(BlockStaticLiquid.LEVEL)), 2);
        world.scheduleUpdate(blockPos, flowingBlock, this.tickRate(world));
    }
    
    protected BlockStaticLiquid(final Material material) {
        super(material);
        this.setTickRandomly(false);
        if (material == Material.lava) {
            this.setTickRandomly(true);
        }
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/block/BlockStaticLiquid.blockMaterial:Lnet/minecraft/block/material/Material;
        //     4: getstatic       net/minecraft/block/material/Material.lava:Lnet/minecraft/block/material/Material;
        //     7: if_acmpne       201
        //    10: aload_1        
        //    11: invokevirtual   net/minecraft/world/World.getGameRules:()Lnet/minecraft/world/GameRules;
        //    14: ldc             "doFireTick"
        //    16: invokevirtual   net/minecraft/world/GameRules.getBoolean:(Ljava/lang/String;)Z
        //    19: ifeq            201
        //    22: aload           4
        //    24: iconst_3       
        //    25: invokevirtual   java/util/Random.nextInt:(I)I
        //    28: istore          5
        //    30: iload           5
        //    32: ifle            134
        //    35: aload_2        
        //    36: astore          6
        //    38: iconst_0       
        //    39: iload           5
        //    41: if_icmpge       131
        //    44: aload           6
        //    46: aload           4
        //    48: iconst_3       
        //    49: invokevirtual   java/util/Random.nextInt:(I)I
        //    52: iconst_1       
        //    53: isub           
        //    54: iconst_1       
        //    55: aload           4
        //    57: iconst_3       
        //    58: invokevirtual   java/util/Random.nextInt:(I)I
        //    61: iconst_1       
        //    62: isub           
        //    63: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    66: astore          6
        //    68: aload_1        
        //    69: aload           6
        //    71: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    74: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    79: astore          8
        //    81: aload           8
        //    83: getfield        net/minecraft/block/Block.blockMaterial:Lnet/minecraft/block/material/Material;
        //    86: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //    89: if_acmpne       113
        //    92: aload_0        
        //    93: aload_1        
        //    94: aload           6
        //    96: if_icmpge       125
        //    99: aload_1        
        //   100: aload           6
        //   102: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   105: invokevirtual   net/minecraft/block/BlockFire.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   108: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //   111: pop            
        //   112: return         
        //   113: aload           8
        //   115: getfield        net/minecraft/block/Block.blockMaterial:Lnet/minecraft/block/material/Material;
        //   118: invokevirtual   net/minecraft/block/material/Material.blocksMovement:()Z
        //   121: ifeq            125
        //   124: return         
        //   125: iinc            7, 1
        //   128: goto            38
        //   131: goto            201
        //   134: aload_2        
        //   135: aload           4
        //   137: iconst_3       
        //   138: invokevirtual   java/util/Random.nextInt:(I)I
        //   141: iconst_1       
        //   142: isub           
        //   143: iconst_0       
        //   144: aload           4
        //   146: iconst_3       
        //   147: invokevirtual   java/util/Random.nextInt:(I)I
        //   150: iconst_1       
        //   151: isub           
        //   152: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   155: astore          7
        //   157: aload_1        
        //   158: aload           7
        //   160: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   163: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   166: ifeq            195
        //   169: aload_0        
        //   170: aload_1        
        //   171: aload           7
        //   173: invokespecial   net/minecraft/block/BlockStaticLiquid.getCanBlockBurn:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //   176: ifeq            195
        //   179: aload_1        
        //   180: aload           7
        //   182: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   185: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   188: invokevirtual   net/minecraft/block/BlockFire.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   191: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //   194: pop            
        //   195: iinc            6, 1
        //   198: goto            134
        //   201: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0125 (coming from #0096).
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
    
    private boolean getCanBlockBurn(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().getMaterial().getCanBurn();
    }
}
