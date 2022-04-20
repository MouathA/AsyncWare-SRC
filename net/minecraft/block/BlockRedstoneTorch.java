package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;

public class BlockRedstoneTorch extends BlockTorch
{
    private static Map toggles;
    private final boolean isOn;
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    private boolean shouldBeOff(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing opposite = ((EnumFacing)blockState.getValue(BlockRedstoneTorch.FACING)).getOpposite();
        return world.isSidePowered(blockPos.offset(opposite), opposite);
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.DOWN) ? this.getWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    @Override
    public int tickRate(final World world) {
        return 2;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    static {
        BlockRedstoneTorch.toggles = Maps.newHashMap();
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (this.isOn && blockState.getValue(BlockRedstoneTorch.FACING) != enumFacing) ? 15 : 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isOn) {
            double n = blockPos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            double n2 = blockPos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2;
            double n3 = blockPos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneTorch.FACING);
            if (enumFacing.getAxis().isHorizontal()) {
                final EnumFacing opposite = enumFacing.getOpposite();
                n += 0.27 * opposite.getFrontOffsetX();
                n2 += 0.22;
                n3 += 0.27 * opposite.getFrontOffsetZ();
            }
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isOn) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    protected BlockRedstoneTorch(final boolean isOn) {
        this.isOn = isOn;
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isOn) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        return block == Blocks.unlit_redstone_torch || block == Blocks.redstone_torch;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.onNeighborChangeInternal(world, blockPos, blockState) && this.isOn == this.shouldBeOff(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: invokespecial   net/minecraft/block/BlockRedstoneTorch.shouldBeOff:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //     7: istore          5
        //     9: getstatic       net/minecraft/block/BlockRedstoneTorch.toggles:Ljava/util/Map;
        //    12: aload_1        
        //    13: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    18: checkcast       Ljava/util/List;
        //    21: astore          6
        //    23: aload           6
        //    25: ifnull          76
        //    28: aload           6
        //    30: invokeinterface java/util/List.isEmpty:()Z
        //    35: ifne            76
        //    38: aload_1        
        //    39: invokevirtual   net/minecraft/world/World.getTotalWorldTime:()J
        //    42: aload           6
        //    44: iconst_0       
        //    45: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    50: checkcast       Lnet/minecraft/block/BlockRedstoneTorch$Toggle;
        //    53: getfield        net/minecraft/block/BlockRedstoneTorch$Toggle.time:J
        //    56: lsub           
        //    57: ldc2_w          60
        //    60: lcmp           
        //    61: ifle            76
        //    64: aload           6
        //    66: iconst_0       
        //    67: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //    72: pop            
        //    73: goto            23
        //    76: aload_0        
        //    77: getfield        net/minecraft/block/BlockRedstoneTorch.isOn:Z
        //    80: ifeq            289
        //    83: iload           5
        //    85: ifeq            327
        //    88: aload_1        
        //    89: aload_2        
        //    90: getstatic       net/minecraft/init/Blocks.unlit_redstone_torch:Lnet/minecraft/block/Block;
        //    93: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    96: getstatic       net/minecraft/block/BlockRedstoneTorch.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    99: aload_3        
        //   100: getstatic       net/minecraft/block/BlockRedstoneTorch.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   103: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   108: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   113: iconst_3       
        //   114: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   117: pop            
        //   118: aload_0        
        //   119: aload_1        
        //   120: aload_2        
        //   121: goto            327
        //   124: aload_1        
        //   125: aload_2        
        //   126: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   129: i2f            
        //   130: ldc             0.5
        //   132: fadd           
        //   133: f2d            
        //   134: aload_2        
        //   135: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   138: i2f            
        //   139: ldc             0.5
        //   141: fadd           
        //   142: f2d            
        //   143: aload_2        
        //   144: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   147: i2f            
        //   148: ldc             0.5
        //   150: fadd           
        //   151: f2d            
        //   152: ldc             "random.fizz"
        //   154: ldc             0.5
        //   156: ldc             2.6
        //   158: aload_1        
        //   159: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   162: invokevirtual   java/util/Random.nextFloat:()F
        //   165: aload_1        
        //   166: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   169: invokevirtual   java/util/Random.nextFloat:()F
        //   172: fsub           
        //   173: ldc             0.8
        //   175: fmul           
        //   176: fadd           
        //   177: invokevirtual   net/minecraft/world/World.playSoundEffect:(DDDLjava/lang/String;FF)V
        //   180: aload_2        
        //   181: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   184: i2d            
        //   185: aload           4
        //   187: invokevirtual   java/util/Random.nextDouble:()D
        //   190: ldc2_w          0.6
        //   193: dmul           
        //   194: dadd           
        //   195: ldc2_w          0.2
        //   198: dadd           
        //   199: dstore          8
        //   201: aload_2        
        //   202: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   205: i2d            
        //   206: aload           4
        //   208: invokevirtual   java/util/Random.nextDouble:()D
        //   211: ldc2_w          0.6
        //   214: dmul           
        //   215: dadd           
        //   216: ldc2_w          0.2
        //   219: dadd           
        //   220: dstore          10
        //   222: aload_2        
        //   223: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   226: i2d            
        //   227: aload           4
        //   229: invokevirtual   java/util/Random.nextDouble:()D
        //   232: ldc2_w          0.6
        //   235: dmul           
        //   236: dadd           
        //   237: ldc2_w          0.2
        //   240: dadd           
        //   241: dstore          12
        //   243: aload_1        
        //   244: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_NORMAL:Lnet/minecraft/util/EnumParticleTypes;
        //   247: dload           8
        //   249: dload           10
        //   251: dload           12
        //   253: dconst_0       
        //   254: dconst_0       
        //   255: dconst_0       
        //   256: iconst_0       
        //   257: newarray        I
        //   259: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   262: iinc            7, 1
        //   265: goto            180
        //   268: aload_1        
        //   269: aload_2        
        //   270: aload_1        
        //   271: aload_2        
        //   272: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   275: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   280: sipush          160
        //   283: invokevirtual   net/minecraft/world/World.scheduleUpdate:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;I)V
        //   286: goto            327
        //   289: iload           5
        //   291: ifne            327
        //   294: aload_0        
        //   295: aload_1        
        //   296: aload_2        
        //   297: aload_1        
        //   298: aload_2        
        //   299: getstatic       net/minecraft/init/Blocks.redstone_torch:Lnet/minecraft/block/Block;
        //   302: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   305: getstatic       net/minecraft/block/BlockRedstoneTorch.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   308: aload_3        
        //   309: getstatic       net/minecraft/block/BlockRedstoneTorch.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   312: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   317: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   322: iconst_3       
        //   323: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   326: pop            
        //   327: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0327 (coming from #0326).
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
    
    static class Toggle
    {
        BlockPos pos;
        long time;
        
        public Toggle(final BlockPos pos, final long time) {
            this.pos = pos;
            this.time = time;
        }
    }
}
