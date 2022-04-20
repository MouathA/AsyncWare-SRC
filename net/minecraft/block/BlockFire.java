package net.minecraft.block;

import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;

public class BlockFire extends Block
{
    public static final PropertyBool NORTH;
    public static final PropertyBool ALT;
    private final Map flammabilities;
    public static final PropertyBool SOUTH;
    public static final PropertyBool EAST;
    public static final PropertyBool WEST;
    private final Map encouragements;
    public static final PropertyBool FLIP;
    public static final PropertyInteger AGE;
    public static final PropertyInteger UPPER;
    
    private void catchOnFire(final World world, final BlockPos blockToAir, final int n, final Random random, final int n2) {
        if (random.nextInt(n) < this.getFlammability(world.getBlockState(blockToAir).getBlock())) {
            final IBlockState blockState = world.getBlockState(blockToAir);
            if (random.nextInt(n2 + 10) < 5 && !world.canLightningStrike(blockToAir)) {
                final int n3 = n2 + random.nextInt(5) / 4;
                world.setBlockState(blockToAir, this.getDefaultState().withProperty(BlockFire.AGE, 15), 3);
            }
            else {
                world.setBlockToAir(blockToAir);
            }
            if (blockState.getBlock() == Blocks.tnt) {
                Blocks.tnt.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.EXPLODE, true));
            }
        }
    }
    
    @Override
    public boolean isCollidable() {
        return false;
    }
    
    @Override
    public int tickRate(final World world) {
        return 30;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //     5: invokestatic    net/minecraft/world/World.doesBlockHaveSolidTopSurface:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //     8: ifne            23
        //    11: aload_0        
        //    12: aload_1        
        //    13: aload_2        
        //    14: if_icmpge       23
        //    17: aload_1        
        //    18: aload_2        
        //    19: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    22: pop            
        //    23: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0023 (coming from #0014).
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
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/block/BlockFire.updateTick:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 344.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int getNeighborEncouragement(final World world, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos)) {
            return 0;
        }
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            Math.max(this.getEncouragement(world.getBlockState(blockPos.offset(values[0])).getBlock()), 0);
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 15);
        FLIP = PropertyBool.create("flip");
        ALT = PropertyBool.create("alt");
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
        UPPER = PropertyInteger.create("upper", 0, 2);
    }
    
    private int getEncouragement(final Block block) {
        final Integer n = this.encouragements.get(block);
        return (n == null) ? 0 : n;
    }
    
    @Override
    public void randomDisplayTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: bipush          24
        //     4: invokevirtual   java/util/Random.nextInt:(I)I
        //     7: ifne            68
        //    10: aload_1        
        //    11: aload_2        
        //    12: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    15: i2f            
        //    16: ldc_w           0.5
        //    19: fadd           
        //    20: f2d            
        //    21: aload_2        
        //    22: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    25: i2f            
        //    26: ldc_w           0.5
        //    29: fadd           
        //    30: f2d            
        //    31: aload_2        
        //    32: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    35: i2f            
        //    36: ldc_w           0.5
        //    39: fadd           
        //    40: f2d            
        //    41: ldc_w           "fire.fire"
        //    44: fconst_1       
        //    45: aload           4
        //    47: invokevirtual   java/util/Random.nextFloat:()F
        //    50: fadd           
        //    51: aload           4
        //    53: invokevirtual   java/util/Random.nextFloat:()F
        //    56: ldc_w           0.7
        //    59: fmul           
        //    60: ldc_w           0.3
        //    63: fadd           
        //    64: iconst_0       
        //    65: invokevirtual   net/minecraft/world/World.playSound:(DDDLjava/lang/String;FFZ)V
        //    68: aload_1        
        //    69: aload_2        
        //    70: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    73: invokestatic    net/minecraft/world/World.doesBlockHaveSolidTopSurface:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //    76: ifne            494
        //    79: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //    82: aload_1        
        //    83: aload_2        
        //    84: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    87: ifle            494
        //    90: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //    93: aload_1        
        //    94: aload_2        
        //    95: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //    98: ifle            169
        //   101: aload_2        
        //   102: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   105: i2d            
        //   106: aload           4
        //   108: invokevirtual   java/util/Random.nextDouble:()D
        //   111: ldc2_w          0.10000000149011612
        //   114: dmul           
        //   115: dadd           
        //   116: dstore          6
        //   118: aload_2        
        //   119: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   122: i2d            
        //   123: aload           4
        //   125: invokevirtual   java/util/Random.nextDouble:()D
        //   128: dadd           
        //   129: dstore          8
        //   131: aload_2        
        //   132: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   135: i2d            
        //   136: aload           4
        //   138: invokevirtual   java/util/Random.nextDouble:()D
        //   141: dadd           
        //   142: dstore          10
        //   144: aload_1        
        //   145: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   148: dload           6
        //   150: dload           8
        //   152: dload           10
        //   154: dconst_0       
        //   155: dconst_0       
        //   156: dconst_0       
        //   157: iconst_0       
        //   158: newarray        I
        //   160: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   163: iinc            5, 1
        //   166: goto            101
        //   169: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   172: aload_1        
        //   173: aload_2        
        //   174: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   177: ifle            250
        //   180: aload_2        
        //   181: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   184: iconst_1       
        //   185: iadd           
        //   186: i2d            
        //   187: aload           4
        //   189: invokevirtual   java/util/Random.nextDouble:()D
        //   192: ldc2_w          0.10000000149011612
        //   195: dmul           
        //   196: dsub           
        //   197: dstore          6
        //   199: aload_2        
        //   200: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   203: i2d            
        //   204: aload           4
        //   206: invokevirtual   java/util/Random.nextDouble:()D
        //   209: dadd           
        //   210: dstore          8
        //   212: aload_2        
        //   213: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   216: i2d            
        //   217: aload           4
        //   219: invokevirtual   java/util/Random.nextDouble:()D
        //   222: dadd           
        //   223: dstore          10
        //   225: aload_1        
        //   226: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   229: dload           6
        //   231: dload           8
        //   233: dload           10
        //   235: dconst_0       
        //   236: dconst_0       
        //   237: dconst_0       
        //   238: iconst_0       
        //   239: newarray        I
        //   241: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   244: iinc            5, 1
        //   247: goto            180
        //   250: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   253: aload_1        
        //   254: aload_2        
        //   255: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   258: ifle            329
        //   261: aload_2        
        //   262: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   265: i2d            
        //   266: aload           4
        //   268: invokevirtual   java/util/Random.nextDouble:()D
        //   271: dadd           
        //   272: dstore          6
        //   274: aload_2        
        //   275: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   278: i2d            
        //   279: aload           4
        //   281: invokevirtual   java/util/Random.nextDouble:()D
        //   284: dadd           
        //   285: dstore          8
        //   287: aload_2        
        //   288: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   291: i2d            
        //   292: aload           4
        //   294: invokevirtual   java/util/Random.nextDouble:()D
        //   297: ldc2_w          0.10000000149011612
        //   300: dmul           
        //   301: dadd           
        //   302: dstore          10
        //   304: aload_1        
        //   305: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   308: dload           6
        //   310: dload           8
        //   312: dload           10
        //   314: dconst_0       
        //   315: dconst_0       
        //   316: dconst_0       
        //   317: iconst_0       
        //   318: newarray        I
        //   320: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   323: iinc            5, 1
        //   326: goto            261
        //   329: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   332: aload_1        
        //   333: aload_2        
        //   334: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   337: ifle            410
        //   340: aload_2        
        //   341: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   344: i2d            
        //   345: aload           4
        //   347: invokevirtual   java/util/Random.nextDouble:()D
        //   350: dadd           
        //   351: dstore          6
        //   353: aload_2        
        //   354: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   357: i2d            
        //   358: aload           4
        //   360: invokevirtual   java/util/Random.nextDouble:()D
        //   363: dadd           
        //   364: dstore          8
        //   366: aload_2        
        //   367: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   370: iconst_1       
        //   371: iadd           
        //   372: i2d            
        //   373: aload           4
        //   375: invokevirtual   java/util/Random.nextDouble:()D
        //   378: ldc2_w          0.10000000149011612
        //   381: dmul           
        //   382: dsub           
        //   383: dstore          10
        //   385: aload_1        
        //   386: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   389: dload           6
        //   391: dload           8
        //   393: dload           10
        //   395: dconst_0       
        //   396: dconst_0       
        //   397: dconst_0       
        //   398: iconst_0       
        //   399: newarray        I
        //   401: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   404: iinc            5, 1
        //   407: goto            340
        //   410: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   413: aload_1        
        //   414: aload_2        
        //   415: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   418: ifle            566
        //   421: aload_2        
        //   422: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   425: i2d            
        //   426: aload           4
        //   428: invokevirtual   java/util/Random.nextDouble:()D
        //   431: dadd           
        //   432: dstore          6
        //   434: aload_2        
        //   435: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   438: iconst_1       
        //   439: iadd           
        //   440: i2d            
        //   441: aload           4
        //   443: invokevirtual   java/util/Random.nextDouble:()D
        //   446: ldc2_w          0.10000000149011612
        //   449: dmul           
        //   450: dsub           
        //   451: dstore          8
        //   453: aload_2        
        //   454: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   457: i2d            
        //   458: aload           4
        //   460: invokevirtual   java/util/Random.nextDouble:()D
        //   463: dadd           
        //   464: dstore          10
        //   466: aload_1        
        //   467: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   470: dload           6
        //   472: dload           8
        //   474: dload           10
        //   476: dconst_0       
        //   477: dconst_0       
        //   478: dconst_0       
        //   479: iconst_0       
        //   480: newarray        I
        //   482: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   485: iinc            5, 1
        //   488: goto            421
        //   491: goto            566
        //   494: aload_2        
        //   495: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   498: i2d            
        //   499: aload           4
        //   501: invokevirtual   java/util/Random.nextDouble:()D
        //   504: dadd           
        //   505: dstore          6
        //   507: aload_2        
        //   508: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   511: i2d            
        //   512: aload           4
        //   514: invokevirtual   java/util/Random.nextDouble:()D
        //   517: ldc2_w          0.5
        //   520: dmul           
        //   521: dadd           
        //   522: ldc2_w          0.5
        //   525: dadd           
        //   526: dstore          8
        //   528: aload_2        
        //   529: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   532: i2d            
        //   533: aload           4
        //   535: invokevirtual   java/util/Random.nextDouble:()D
        //   538: dadd           
        //   539: dstore          10
        //   541: aload_1        
        //   542: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   545: dload           6
        //   547: dload           8
        //   549: dload           10
        //   551: dconst_0       
        //   552: dconst_0       
        //   553: dconst_0       
        //   554: iconst_0       
        //   555: newarray        I
        //   557: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   560: iinc            5, 1
        //   563: goto            494
        //   566: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0494 (coming from #0087).
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
    
    private int getFlammability(final Block block) {
        final Integer n = this.flammabilities.get(block);
        return (n == null) ? 0 : n;
    }
    
    public static void init() {
        Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
        Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
        Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
        Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.log, 5, 5);
        Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
        Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
        Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
        Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
        Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
        Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
        Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
        Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
        Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
        Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
        Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
        Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
        Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
        Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
        Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
    }
    
    @Override
    public boolean requiresUpdates() {
        return false;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFire.AGE);
    }
    
    public void setFireInfo(final Block block, final int n, final int n2) {
        this.encouragements.put(block, n);
        this.flammabilities.put(block, n2);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.tntColor;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void onBlockAdded(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.provider:Lnet/minecraft/world/WorldProvider;
        //     4: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //     7: ifgt            21
        //    10: getstatic       net/minecraft/init/Blocks.portal:Lnet/minecraft/block/BlockPortal;
        //    13: aload_1        
        //    14: aload_2        
        //    15: invokevirtual   net/minecraft/block/BlockPortal.func_176548_d:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //    18: ifne            68
        //    21: aload_1        
        //    22: aload_2        
        //    23: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    26: invokestatic    net/minecraft/world/World.doesBlockHaveSolidTopSurface:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //    29: ifne            47
        //    32: aload_0        
        //    33: aload_1        
        //    34: aload_2        
        //    35: if_icmpge       47
        //    38: aload_1        
        //    39: aload_2        
        //    40: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    43: pop            
        //    44: goto            68
        //    47: aload_1        
        //    48: aload_2        
        //    49: aload_0        
        //    50: aload_0        
        //    51: aload_1        
        //    52: invokevirtual   net/minecraft/block/BlockFire.tickRate:(Lnet/minecraft/world/World;)I
        //    55: aload_1        
        //    56: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //    59: bipush          10
        //    61: invokevirtual   java/util/Random.nextInt:(I)I
        //    64: iadd           
        //    65: invokevirtual   net/minecraft/world/World.scheduleUpdate:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;I)V
        //    68: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0047 (coming from #0035).
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
    public IBlockState getActualState(final IBlockState p0, final IBlockAccess p1, final BlockPos p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //     4: istore          4
        //     6: aload_3        
        //     7: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    10: istore          5
        //    12: aload_3        
        //    13: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    16: istore          6
        //    18: aload_2        
        //    19: aload_3        
        //    20: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    23: invokestatic    net/minecraft/world/World.doesBlockHaveSolidTopSurface:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //    26: ifne            229
        //    29: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //    32: aload_2        
        //    33: aload_3        
        //    34: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    37: ifle            229
        //    40: iload           4
        //    42: iload           5
        //    44: iadd           
        //    45: iload           6
        //    47: iadd           
        //    48: iconst_1       
        //    49: iand           
        //    50: iconst_1       
        //    51: if_icmpne       58
        //    54: iconst_1       
        //    55: goto            59
        //    58: iconst_0       
        //    59: istore          7
        //    61: iload           4
        //    63: iconst_2       
        //    64: idiv           
        //    65: iload           5
        //    67: iconst_2       
        //    68: idiv           
        //    69: iadd           
        //    70: iload           6
        //    72: iconst_2       
        //    73: idiv           
        //    74: iadd           
        //    75: iconst_1       
        //    76: iand           
        //    77: iconst_1       
        //    78: if_icmpne       85
        //    81: iconst_1       
        //    82: goto            86
        //    85: iconst_0       
        //    86: istore          8
        //    88: aload_0        
        //    89: aload_2        
        //    90: aload_3        
        //    91: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    94: ifle            109
        //    97: iload           7
        //    99: ifeq            106
        //   102: iconst_1       
        //   103: goto            107
        //   106: iconst_2       
        //   107: istore          9
        //   109: aload_1        
        //   110: getstatic       net/minecraft/block/BlockFire.NORTH:Lnet/minecraft/block/properties/PropertyBool;
        //   113: aload_0        
        //   114: aload_2        
        //   115: aload_3        
        //   116: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   119: invokevirtual   net/minecraft/block/BlockFire.canCatchFire:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //   122: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   125: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   130: getstatic       net/minecraft/block/BlockFire.EAST:Lnet/minecraft/block/properties/PropertyBool;
        //   133: aload_0        
        //   134: aload_2        
        //   135: aload_3        
        //   136: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   139: invokevirtual   net/minecraft/block/BlockFire.canCatchFire:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //   142: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   145: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   150: getstatic       net/minecraft/block/BlockFire.SOUTH:Lnet/minecraft/block/properties/PropertyBool;
        //   153: aload_0        
        //   154: aload_2        
        //   155: aload_3        
        //   156: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   159: invokevirtual   net/minecraft/block/BlockFire.canCatchFire:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //   162: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   165: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   170: getstatic       net/minecraft/block/BlockFire.WEST:Lnet/minecraft/block/properties/PropertyBool;
        //   173: aload_0        
        //   174: aload_2        
        //   175: aload_3        
        //   176: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   179: invokevirtual   net/minecraft/block/BlockFire.canCatchFire:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //   182: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   185: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   190: getstatic       net/minecraft/block/BlockFire.UPPER:Lnet/minecraft/block/properties/PropertyInteger;
        //   193: iconst_0       
        //   194: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   197: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   202: getstatic       net/minecraft/block/BlockFire.FLIP:Lnet/minecraft/block/properties/PropertyBool;
        //   205: iload           8
        //   207: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   210: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   215: getstatic       net/minecraft/block/BlockFire.ALT:Lnet/minecraft/block/properties/PropertyBool;
        //   218: iload           7
        //   220: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   223: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   228: areturn        
        //   229: aload_0        
        //   230: invokevirtual   net/minecraft/block/BlockFire.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   233: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0229 (coming from #0037).
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
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFire.AGE, n);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFire.AGE, BlockFire.NORTH, BlockFire.EAST, BlockFire.SOUTH, BlockFire.WEST, BlockFire.UPPER, BlockFire.FLIP, BlockFire.ALT });
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    protected BlockFire() {
        super(Material.fire);
        this.encouragements = Maps.newIdentityHashMap();
        this.flammabilities = Maps.newIdentityHashMap();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFire.AGE, 0).withProperty(BlockFire.FLIP, false).withProperty(BlockFire.ALT, false).withProperty(BlockFire.NORTH, false).withProperty(BlockFire.EAST, false).withProperty(BlockFire.SOUTH, false).withProperty(BlockFire.WEST, false).withProperty(BlockFire.UPPER, 0));
        this.setTickRandomly(true);
    }
}
