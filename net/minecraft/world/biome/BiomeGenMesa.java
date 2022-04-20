package net.minecraft.world.biome;

import net.minecraft.world.gen.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenMesa extends BiomeGenBase
{
    private NoiseGeneratorPerlin field_150624_aF;
    private boolean field_150620_aI;
    private boolean field_150626_aH;
    private long field_150622_aD;
    private IBlockState[] field_150621_aC;
    private NoiseGeneratorPerlin field_150625_aG;
    private NoiseGeneratorPerlin field_150623_aE;
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final boolean b = this.biomeID == BiomeGenBase.mesa.biomeID;
        final BiomeGenMesa biomeGenMesa = new BiomeGenMesa(n, b, this.field_150620_aI);
        if (!b) {
            biomeGenMesa.setHeight(BiomeGenMesa.height_LowHills);
            biomeGenMesa.setBiomeName(this.biomeName + " M");
        }
        else {
            biomeGenMesa.setBiomeName(this.biomeName + " (Bryce)");
        }
        biomeGenMesa.func_150557_a(this.color, true);
        return biomeGenMesa;
    }
    
    @Override
    public void genTerrainBlocks(final World p0, final Random p1, final ChunkPrimer p2, final int p3, final int p4, final double p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150621_aC:[Lnet/minecraft/block/state/IBlockState;
        //     4: ifnull          19
        //     7: aload_0        
        //     8: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150622_aD:J
        //    11: aload_1        
        //    12: invokevirtual   net/minecraft/world/World.getSeed:()J
        //    15: lcmp           
        //    16: ifeq            27
        //    19: aload_0        
        //    20: aload_1        
        //    21: invokevirtual   net/minecraft/world/World.getSeed:()J
        //    24: invokespecial   net/minecraft/world/biome/BiomeGenMesa.func_150619_a:(J)V
        //    27: aload_0        
        //    28: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150623_aE:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    31: ifnull          53
        //    34: aload_0        
        //    35: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150624_aF:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    38: ifnull          53
        //    41: aload_0        
        //    42: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150622_aD:J
        //    45: aload_1        
        //    46: invokevirtual   net/minecraft/world/World.getSeed:()J
        //    49: lcmp           
        //    50: ifeq            94
        //    53: new             Ljava/util/Random;
        //    56: dup            
        //    57: aload_0        
        //    58: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150622_aD:J
        //    61: invokespecial   java/util/Random.<init>:(J)V
        //    64: astore          8
        //    66: aload_0        
        //    67: new             Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    70: dup            
        //    71: aload           8
        //    73: iconst_4       
        //    74: invokespecial   net/minecraft/world/gen/NoiseGeneratorPerlin.<init>:(Ljava/util/Random;I)V
        //    77: putfield        net/minecraft/world/biome/BiomeGenMesa.field_150623_aE:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    80: aload_0        
        //    81: new             Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    84: dup            
        //    85: aload           8
        //    87: iconst_1       
        //    88: invokespecial   net/minecraft/world/gen/NoiseGeneratorPerlin.<init>:(Ljava/util/Random;I)V
        //    91: putfield        net/minecraft/world/biome/BiomeGenMesa.field_150624_aF:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //    94: aload_0        
        //    95: aload_1        
        //    96: invokevirtual   net/minecraft/world/World.getSeed:()J
        //    99: putfield        net/minecraft/world/biome/BiomeGenMesa.field_150622_aD:J
        //   102: dconst_0       
        //   103: dstore          8
        //   105: aload_0        
        //   106: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150626_aH:Z
        //   109: ifeq            251
        //   112: iload           4
        //   114: bipush          -16
        //   116: iand           
        //   117: iload           5
        //   119: bipush          15
        //   121: iand           
        //   122: iadd           
        //   123: istore          10
        //   125: iload           5
        //   127: bipush          -16
        //   129: iand           
        //   130: iload           4
        //   132: bipush          15
        //   134: iand           
        //   135: iadd           
        //   136: istore          11
        //   138: dload           6
        //   140: invokestatic    java/lang/Math.abs:(D)D
        //   143: aload_0        
        //   144: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150623_aE:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //   147: iload           10
        //   149: i2d            
        //   150: ldc2_w          0.25
        //   153: dmul           
        //   154: iload           11
        //   156: i2d            
        //   157: ldc2_w          0.25
        //   160: dmul           
        //   161: invokevirtual   net/minecraft/world/gen/NoiseGeneratorPerlin.func_151601_a:(DD)D
        //   164: invokestatic    java/lang/Math.min:(DD)D
        //   167: dstore          12
        //   169: dload           12
        //   171: dconst_0       
        //   172: dcmpl          
        //   173: ifle            251
        //   176: ldc2_w          0.001953125
        //   179: dstore          14
        //   181: aload_0        
        //   182: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150624_aF:Lnet/minecraft/world/gen/NoiseGeneratorPerlin;
        //   185: iload           10
        //   187: i2d            
        //   188: dload           14
        //   190: dmul           
        //   191: iload           11
        //   193: i2d            
        //   194: dload           14
        //   196: dmul           
        //   197: invokevirtual   net/minecraft/world/gen/NoiseGeneratorPerlin.func_151601_a:(DD)D
        //   200: invokestatic    java/lang/Math.abs:(D)D
        //   203: dstore          16
        //   205: dload           12
        //   207: dload           12
        //   209: dmul           
        //   210: ldc2_w          2.5
        //   213: dmul           
        //   214: dstore          8
        //   216: dload           16
        //   218: ldc2_w          50.0
        //   221: dmul           
        //   222: invokestatic    java/lang/Math.ceil:(D)D
        //   225: ldc2_w          14.0
        //   228: dadd           
        //   229: dstore          18
        //   231: dload           8
        //   233: dload           18
        //   235: dcmpl          
        //   236: ifle            243
        //   239: dload           18
        //   241: dstore          8
        //   243: dload           8
        //   245: ldc2_w          64.0
        //   248: dadd           
        //   249: dstore          8
        //   251: iload           4
        //   253: bipush          15
        //   255: iand           
        //   256: istore          10
        //   258: iload           5
        //   260: bipush          15
        //   262: iand           
        //   263: istore          11
        //   265: aload_1        
        //   266: invokevirtual   net/minecraft/world/World.func_181545_F:()I
        //   269: istore          12
        //   271: getstatic       net/minecraft/init/Blocks.stained_hardened_clay:Lnet/minecraft/block/Block;
        //   274: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   277: astore          13
        //   279: aload_0        
        //   280: getfield        net/minecraft/world/biome/BiomeGenMesa.fillerBlock:Lnet/minecraft/block/state/IBlockState;
        //   283: astore          14
        //   285: dload           6
        //   287: ldc2_w          3.0
        //   290: ddiv           
        //   291: ldc2_w          3.0
        //   294: dadd           
        //   295: aload_2        
        //   296: invokevirtual   java/util/Random.nextDouble:()D
        //   299: ldc2_w          0.25
        //   302: dmul           
        //   303: dadd           
        //   304: d2i            
        //   305: istore          15
        //   307: dload           6
        //   309: ldc2_w          3.0
        //   312: ddiv           
        //   313: ldc2_w          3.141592653589793
        //   316: dmul           
        //   317: invokestatic    java/lang/Math.cos:(D)D
        //   320: dconst_0       
        //   321: dcmpl          
        //   322: ifle            329
        //   325: iconst_1       
        //   326: goto            330
        //   329: iconst_0       
        //   330: istore          16
        //   332: aload_3        
        //   333: iload           11
        //   335: sipush          255
        //   338: iload           10
        //   340: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.getBlockState:(III)Lnet/minecraft/block/state/IBlockState;
        //   343: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   348: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   351: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   354: if_acmpne       383
        //   357: sipush          255
        //   360: dload           8
        //   362: d2i            
        //   363: if_icmpge       383
        //   366: aload_3        
        //   367: iload           11
        //   369: sipush          255
        //   372: iload           10
        //   374: getstatic       net/minecraft/init/Blocks.stone:Lnet/minecraft/block/Block;
        //   377: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   380: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   383: sipush          255
        //   386: aload_2        
        //   387: iconst_5       
        //   388: invokevirtual   java/util/Random.nextInt:(I)I
        //   391: if_icmpgt       414
        //   394: aload_3        
        //   395: iload           11
        //   397: sipush          255
        //   400: iload           10
        //   402: getstatic       net/minecraft/init/Blocks.bedrock:Lnet/minecraft/block/Block;
        //   405: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   408: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   411: goto            875
        //   414: aload_3        
        //   415: iload           11
        //   417: sipush          255
        //   420: iload           10
        //   422: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.getBlockState:(III)Lnet/minecraft/block/state/IBlockState;
        //   425: astore          20
        //   427: aload           20
        //   429: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   434: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   437: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   440: if_acmpne       446
        //   443: goto            875
        //   446: aload           20
        //   448: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   453: getstatic       net/minecraft/init/Blocks.stone:Lnet/minecraft/block/Block;
        //   456: if_acmpne       875
        //   459: iload           15
        //   461: ifgt            478
        //   464: aconst_null    
        //   465: astore          13
        //   467: getstatic       net/minecraft/init/Blocks.stone:Lnet/minecraft/block/Block;
        //   470: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   473: astore          14
        //   475: goto            512
        //   478: sipush          255
        //   481: iload           12
        //   483: iconst_4       
        //   484: isub           
        //   485: if_icmplt       512
        //   488: sipush          255
        //   491: iload           12
        //   493: iconst_1       
        //   494: iadd           
        //   495: if_icmpgt       512
        //   498: getstatic       net/minecraft/init/Blocks.stained_hardened_clay:Lnet/minecraft/block/Block;
        //   501: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   504: astore          13
        //   506: aload_0        
        //   507: getfield        net/minecraft/world/biome/BiomeGenMesa.fillerBlock:Lnet/minecraft/block/state/IBlockState;
        //   510: astore          14
        //   512: sipush          255
        //   515: iload           12
        //   517: if_icmpge       549
        //   520: aload           13
        //   522: ifnull          541
        //   525: aload           13
        //   527: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   532: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   535: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   538: if_acmpne       549
        //   541: getstatic       net/minecraft/init/Blocks.water:Lnet/minecraft/block/BlockStaticLiquid;
        //   544: invokevirtual   net/minecraft/block/BlockStaticLiquid.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   547: astore          13
        //   549: iload           15
        //   551: iconst_0       
        //   552: sipush          255
        //   555: iload           12
        //   557: isub           
        //   558: invokestatic    java/lang/Math.max:(II)I
        //   561: iadd           
        //   562: istore          17
        //   564: sipush          255
        //   567: iload           12
        //   569: iconst_1       
        //   570: isub           
        //   571: if_icmpge       635
        //   574: aload_3        
        //   575: iload           11
        //   577: sipush          255
        //   580: iload           10
        //   582: aload           14
        //   584: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   587: aload           14
        //   589: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   594: getstatic       net/minecraft/init/Blocks.stained_hardened_clay:Lnet/minecraft/block/Block;
        //   597: if_acmpne       875
        //   600: aload_3        
        //   601: iload           11
        //   603: sipush          255
        //   606: iload           10
        //   608: aload           14
        //   610: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   615: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   618: getstatic       net/minecraft/block/BlockColored.COLOR:Lnet/minecraft/block/properties/PropertyEnum;
        //   621: getstatic       net/minecraft/item/EnumDyeColor.ORANGE:Lnet/minecraft/item/EnumDyeColor;
        //   624: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   629: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   632: goto            875
        //   635: aload_0        
        //   636: getfield        net/minecraft/world/biome/BiomeGenMesa.field_150620_aI:Z
        //   639: ifeq            711
        //   642: sipush          255
        //   645: bipush          86
        //   647: iload           15
        //   649: iconst_2       
        //   650: imul           
        //   651: iadd           
        //   652: if_icmple       711
        //   655: iload           16
        //   657: ifeq            691
        //   660: aload_3        
        //   661: iload           11
        //   663: sipush          255
        //   666: iload           10
        //   668: getstatic       net/minecraft/init/Blocks.dirt:Lnet/minecraft/block/Block;
        //   671: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   674: getstatic       net/minecraft/block/BlockDirt.VARIANT:Lnet/minecraft/block/properties/PropertyEnum;
        //   677: getstatic       net/minecraft/block/BlockDirt$DirtType.COARSE_DIRT:Lnet/minecraft/block/BlockDirt$DirtType;
        //   680: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   685: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   688: goto            875
        //   691: aload_3        
        //   692: iload           11
        //   694: sipush          255
        //   697: iload           10
        //   699: getstatic       net/minecraft/init/Blocks.grass:Lnet/minecraft/block/BlockGrass;
        //   702: invokevirtual   net/minecraft/block/BlockGrass.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   705: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   708: goto            875
        //   711: sipush          255
        //   714: iload           12
        //   716: iconst_3       
        //   717: iadd           
        //   718: iload           15
        //   720: iadd           
        //   721: if_icmpgt       742
        //   724: aload_3        
        //   725: iload           11
        //   727: sipush          255
        //   730: iload           10
        //   732: aload_0        
        //   733: getfield        net/minecraft/world/biome/BiomeGenMesa.topBlock:Lnet/minecraft/block/state/IBlockState;
        //   736: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   739: goto            875
        //   742: goto            777
        //   745: iload           16
        //   747: ifeq            761
        //   750: getstatic       net/minecraft/init/Blocks.hardened_clay:Lnet/minecraft/block/Block;
        //   753: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   756: astore          21
        //   758: goto            796
        //   761: aload_0        
        //   762: iload           4
        //   764: sipush          255
        //   767: iload           5
        //   769: invokespecial   net/minecraft/world/biome/BiomeGenMesa.func_180629_a:(III)Lnet/minecraft/block/state/IBlockState;
        //   772: astore          21
        //   774: goto            796
        //   777: getstatic       net/minecraft/init/Blocks.stained_hardened_clay:Lnet/minecraft/block/Block;
        //   780: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   783: getstatic       net/minecraft/block/BlockColored.COLOR:Lnet/minecraft/block/properties/PropertyEnum;
        //   786: getstatic       net/minecraft/item/EnumDyeColor.ORANGE:Lnet/minecraft/item/EnumDyeColor;
        //   789: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   794: astore          21
        //   796: aload_3        
        //   797: iload           11
        //   799: sipush          255
        //   802: iload           10
        //   804: aload           21
        //   806: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   809: goto            875
        //   812: goto            875
        //   815: iinc            17, -1
        //   818: aload_3        
        //   819: iload           11
        //   821: sipush          255
        //   824: iload           10
        //   826: getstatic       net/minecraft/init/Blocks.stained_hardened_clay:Lnet/minecraft/block/Block;
        //   829: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   832: getstatic       net/minecraft/block/BlockColored.COLOR:Lnet/minecraft/block/properties/PropertyEnum;
        //   835: getstatic       net/minecraft/item/EnumDyeColor.ORANGE:Lnet/minecraft/item/EnumDyeColor;
        //   838: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   843: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   846: goto            875
        //   849: aload_0        
        //   850: iload           4
        //   852: sipush          255
        //   855: iload           5
        //   857: invokespecial   net/minecraft/world/biome/BiomeGenMesa.func_180629_a:(III)Lnet/minecraft/block/state/IBlockState;
        //   860: astore          21
        //   862: aload_3        
        //   863: iload           11
        //   865: sipush          255
        //   868: iload           10
        //   870: aload           21
        //   872: invokevirtual   net/minecraft/world/chunk/ChunkPrimer.setBlockState:(IIILnet/minecraft/block/state/IBlockState;)V
        //   875: iinc            19, -1
        //   878: goto            332
        //   881: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public BiomeGenMesa(final int n, final boolean field_150626_aH, final boolean field_150620_aI) {
        super(n);
        this.field_150626_aH = field_150626_aH;
        this.field_150620_aI = field_150620_aI;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0f, 0.0f);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
        this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();
        if (field_150620_aI) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }
    
    private void func_150619_a(final long n) {
        Arrays.fill(this.field_150621_aC = new IBlockState[64], Blocks.hardened_clay.getDefaultState());
        final Random random = new Random(n);
        this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);
        while (true) {
            int n2 = 0 + (random.nextInt(5) + 1);
            this.field_150621_aC[0] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
            ++n2;
        }
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.worldGeneratorTrees;
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return 10387789;
    }
    
    private IBlockState func_180629_a(final int n, final int n2, final int n3) {
        return this.field_150621_aC[(n2 + (int)Math.round(this.field_150625_aG.func_151601_a(n * 1.0 / 512.0, n * 1.0 / 512.0) * 2.0) + 64) % 64];
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return 9470285;
    }
}
