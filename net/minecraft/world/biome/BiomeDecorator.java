package net.minecraft.world.biome;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class BiomeDecorator
{
    protected WorldGenerator mushroomBrownGen;
    protected int treesPerChunk;
    protected BlockPos field_180294_c;
    protected WorldGenerator coalGen;
    protected WorldGenerator bigMushroomGen;
    protected int flowersPerChunk;
    protected int clayPerChunk;
    protected WorldGenerator lapisGen;
    protected World currentWorld;
    protected WorldGenerator gravelGen;
    protected int sandPerChunk2;
    protected WorldGenerator dirtGen;
    protected WorldGenerator cactusGen;
    protected WorldGenerator gravelAsSandGen;
    protected ChunkProviderSettings chunkProviderSettings;
    protected WorldGenerator redstoneGen;
    protected WorldGenerator dioriteGen;
    protected WorldGenerator reedGen;
    protected int mushroomsPerChunk;
    protected WorldGenFlowers yellowFlowerGen;
    public boolean generateLakes;
    protected int cactiPerChunk;
    protected WorldGenerator waterlilyGen;
    protected int sandPerChunk;
    protected int bigMushroomsPerChunk;
    protected WorldGenerator sandGen;
    protected WorldGenerator graniteGen;
    protected int deadBushPerChunk;
    protected int grassPerChunk;
    protected WorldGenerator mushroomRedGen;
    protected WorldGenerator goldGen;
    protected WorldGenerator diamondGen;
    protected int reedsPerChunk;
    protected WorldGenerator ironGen;
    protected WorldGenerator andesiteGen;
    protected int waterlilyPerChunk;
    protected Random randomGenerator;
    protected WorldGenerator clayGen;
    
    public void decorate(final World currentWorld, final Random randomGenerator, final BiomeGenBase biomeGenBase, final BlockPos field_180294_c) {
        if (this.currentWorld != null) {
            throw new RuntimeException("Already decorating");
        }
        this.currentWorld = currentWorld;
        final String generatorOptions = currentWorld.getWorldInfo().getGeneratorOptions();
        if (generatorOptions != null) {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(generatorOptions).func_177864_b();
        }
        else {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
        }
        this.randomGenerator = randomGenerator;
        this.field_180294_c = field_180294_c;
        this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
        this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
        this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
        this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
        this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
        this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
        this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
        this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
        this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
        this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
        this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
        this.genDecorations(biomeGenBase);
        this.currentWorld = null;
        this.randomGenerator = null;
    }
    
    protected void genStandardOre2(final int n, final WorldGenerator worldGenerator, final int n2, final int n3) {
        while (0 < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(n3) + this.randomGenerator.nextInt(n3) + n2 - n3, this.randomGenerator.nextInt(16)));
            int n4 = 0;
            ++n4;
        }
    }
    
    protected void genStandardOre1(final int n, final WorldGenerator worldGenerator, final int n2, int n3) {
        ++n3;
        while (0 < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(0) + 0, this.randomGenerator.nextInt(16)));
            int n4 = 0;
            ++n4;
        }
    }
    
    protected void genDecorations(final BiomeGenBase p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/world/biome/BiomeDecorator.generateOres:()V
        //     4: iconst_0       
        //     5: aload_0        
        //     6: getfield        net/minecraft/world/biome/BiomeDecorator.sandPerChunk2:I
        //     9: if_icmpge       79
        //    12: aload_0        
        //    13: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    16: bipush          16
        //    18: invokevirtual   java/util/Random.nextInt:(I)I
        //    21: bipush          8
        //    23: iadd           
        //    24: istore_3       
        //    25: aload_0        
        //    26: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    29: bipush          16
        //    31: invokevirtual   java/util/Random.nextInt:(I)I
        //    34: bipush          8
        //    36: iadd           
        //    37: istore          4
        //    39: aload_0        
        //    40: getfield        net/minecraft/world/biome/BiomeDecorator.sandGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //    43: aload_0        
        //    44: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //    47: aload_0        
        //    48: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    51: aload_0        
        //    52: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //    55: aload_0        
        //    56: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //    59: iconst_0       
        //    60: iconst_0       
        //    61: iload           4
        //    63: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    66: invokevirtual   net/minecraft/world/World.getTopSolidOrLiquidBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //    69: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //    72: pop            
        //    73: iinc            2, 1
        //    76: goto            4
        //    79: iconst_0       
        //    80: aload_0        
        //    81: getfield        net/minecraft/world/biome/BiomeDecorator.clayPerChunk:I
        //    84: if_icmpge       154
        //    87: aload_0        
        //    88: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    91: bipush          16
        //    93: invokevirtual   java/util/Random.nextInt:(I)I
        //    96: bipush          8
        //    98: iadd           
        //    99: istore_3       
        //   100: aload_0        
        //   101: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   104: bipush          16
        //   106: invokevirtual   java/util/Random.nextInt:(I)I
        //   109: bipush          8
        //   111: iadd           
        //   112: istore          4
        //   114: aload_0        
        //   115: getfield        net/minecraft/world/biome/BiomeDecorator.clayGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   118: aload_0        
        //   119: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   122: aload_0        
        //   123: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   126: aload_0        
        //   127: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   130: aload_0        
        //   131: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   134: iconst_0       
        //   135: iconst_0       
        //   136: iload           4
        //   138: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   141: invokevirtual   net/minecraft/world/World.getTopSolidOrLiquidBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   144: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   147: pop            
        //   148: iinc            2, 1
        //   151: goto            79
        //   154: iconst_0       
        //   155: aload_0        
        //   156: getfield        net/minecraft/world/biome/BiomeDecorator.sandPerChunk:I
        //   159: if_icmpge       229
        //   162: aload_0        
        //   163: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   166: bipush          16
        //   168: invokevirtual   java/util/Random.nextInt:(I)I
        //   171: bipush          8
        //   173: iadd           
        //   174: istore_3       
        //   175: aload_0        
        //   176: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   179: bipush          16
        //   181: invokevirtual   java/util/Random.nextInt:(I)I
        //   184: bipush          8
        //   186: iadd           
        //   187: istore          4
        //   189: aload_0        
        //   190: getfield        net/minecraft/world/biome/BiomeDecorator.gravelAsSandGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   193: aload_0        
        //   194: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   197: aload_0        
        //   198: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   201: aload_0        
        //   202: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   205: aload_0        
        //   206: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   209: iconst_0       
        //   210: iconst_0       
        //   211: iload           4
        //   213: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   216: invokevirtual   net/minecraft/world/World.getTopSolidOrLiquidBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   219: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   222: pop            
        //   223: iinc            2, 1
        //   226: goto            154
        //   229: aload_0        
        //   230: getfield        net/minecraft/world/biome/BiomeDecorator.treesPerChunk:I
        //   233: istore_2       
        //   234: aload_0        
        //   235: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   238: bipush          10
        //   240: invokevirtual   java/util/Random.nextInt:(I)I
        //   243: ifne            249
        //   246: iinc            2, 1
        //   249: goto            355
        //   252: aload_0        
        //   253: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   256: bipush          16
        //   258: invokevirtual   java/util/Random.nextInt:(I)I
        //   261: bipush          8
        //   263: iadd           
        //   264: istore          4
        //   266: aload_0        
        //   267: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   270: bipush          16
        //   272: invokevirtual   java/util/Random.nextInt:(I)I
        //   275: bipush          8
        //   277: iadd           
        //   278: istore          5
        //   280: aload_1        
        //   281: aload_0        
        //   282: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   285: invokevirtual   net/minecraft/world/biome/BiomeGenBase.genBigTreeChance:(Ljava/util/Random;)Lnet/minecraft/world/gen/feature/WorldGenAbstractTree;
        //   288: astore          6
        //   290: aload           6
        //   292: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_175904_e:()V
        //   295: aload_0        
        //   296: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   299: aload_0        
        //   300: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   303: iload           4
        //   305: iconst_0       
        //   306: iload           5
        //   308: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   311: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   314: astore          7
        //   316: aload           6
        //   318: aload_0        
        //   319: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   322: aload_0        
        //   323: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   326: aload           7
        //   328: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   331: ifeq            349
        //   334: aload           6
        //   336: aload_0        
        //   337: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   340: aload_0        
        //   341: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   344: aload           7
        //   346: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_180711_a:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V
        //   349: iinc            3, 1
        //   352: goto            249
        //   355: iconst_0       
        //   356: aload_0        
        //   357: getfield        net/minecraft/world/biome/BiomeDecorator.bigMushroomsPerChunk:I
        //   360: if_icmpge       432
        //   363: aload_0        
        //   364: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   367: bipush          16
        //   369: invokevirtual   java/util/Random.nextInt:(I)I
        //   372: bipush          8
        //   374: iadd           
        //   375: istore          4
        //   377: aload_0        
        //   378: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   381: bipush          16
        //   383: invokevirtual   java/util/Random.nextInt:(I)I
        //   386: bipush          8
        //   388: iadd           
        //   389: istore          5
        //   391: aload_0        
        //   392: getfield        net/minecraft/world/biome/BiomeDecorator.bigMushroomGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   395: aload_0        
        //   396: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   399: aload_0        
        //   400: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   403: aload_0        
        //   404: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   407: aload_0        
        //   408: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   411: iload           4
        //   413: iconst_0       
        //   414: iload           5
        //   416: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   419: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   422: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   425: pop            
        //   426: iinc            3, 1
        //   429: goto            355
        //   432: iconst_0       
        //   433: aload_0        
        //   434: getfield        net/minecraft/world/biome/BiomeDecorator.flowersPerChunk:I
        //   437: if_icmpge       594
        //   440: aload_0        
        //   441: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   444: bipush          16
        //   446: invokevirtual   java/util/Random.nextInt:(I)I
        //   449: bipush          8
        //   451: iadd           
        //   452: istore          4
        //   454: aload_0        
        //   455: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   458: bipush          16
        //   460: invokevirtual   java/util/Random.nextInt:(I)I
        //   463: bipush          8
        //   465: iadd           
        //   466: istore          5
        //   468: aload_0        
        //   469: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   472: aload_0        
        //   473: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   476: iload           4
        //   478: iconst_0       
        //   479: iload           5
        //   481: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   484: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   487: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   490: bipush          32
        //   492: iadd           
        //   493: istore          6
        //   495: iload           6
        //   497: ifle            588
        //   500: aload_0        
        //   501: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   504: iload           6
        //   506: invokevirtual   java/util/Random.nextInt:(I)I
        //   509: istore          7
        //   511: aload_0        
        //   512: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   515: iload           4
        //   517: iload           7
        //   519: iload           5
        //   521: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   524: astore          8
        //   526: aload_1        
        //   527: aload_0        
        //   528: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   531: aload           8
        //   533: invokevirtual   net/minecraft/world/biome/BiomeGenBase.pickRandomFlower:(Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/BlockFlower$EnumFlowerType;
        //   536: astore          9
        //   538: aload           9
        //   540: invokevirtual   net/minecraft/block/BlockFlower$EnumFlowerType.getBlockType:()Lnet/minecraft/block/BlockFlower$EnumFlowerColor;
        //   543: invokevirtual   net/minecraft/block/BlockFlower$EnumFlowerColor.getBlock:()Lnet/minecraft/block/BlockFlower;
        //   546: astore          10
        //   548: aload           10
        //   550: invokevirtual   net/minecraft/block/BlockFlower.getMaterial:()Lnet/minecraft/block/material/Material;
        //   553: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   556: if_acmpeq       588
        //   559: aload_0        
        //   560: getfield        net/minecraft/world/biome/BiomeDecorator.yellowFlowerGen:Lnet/minecraft/world/gen/feature/WorldGenFlowers;
        //   563: aload           10
        //   565: aload           9
        //   567: invokevirtual   net/minecraft/world/gen/feature/WorldGenFlowers.setGeneratedBlock:(Lnet/minecraft/block/BlockFlower;Lnet/minecraft/block/BlockFlower$EnumFlowerType;)V
        //   570: aload_0        
        //   571: getfield        net/minecraft/world/biome/BiomeDecorator.yellowFlowerGen:Lnet/minecraft/world/gen/feature/WorldGenFlowers;
        //   574: aload_0        
        //   575: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   578: aload_0        
        //   579: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   582: aload           8
        //   584: invokevirtual   net/minecraft/world/gen/feature/WorldGenFlowers.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   587: pop            
        //   588: iinc            3, 1
        //   591: goto            432
        //   594: iconst_0       
        //   595: aload_0        
        //   596: getfield        net/minecraft/world/biome/BiomeDecorator.grassPerChunk:I
        //   599: if_icmpge       711
        //   602: aload_0        
        //   603: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   606: bipush          16
        //   608: invokevirtual   java/util/Random.nextInt:(I)I
        //   611: bipush          8
        //   613: iadd           
        //   614: istore          4
        //   616: aload_0        
        //   617: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   620: bipush          16
        //   622: invokevirtual   java/util/Random.nextInt:(I)I
        //   625: bipush          8
        //   627: iadd           
        //   628: istore          5
        //   630: aload_0        
        //   631: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   634: aload_0        
        //   635: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   638: iload           4
        //   640: iconst_0       
        //   641: iload           5
        //   643: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   646: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   649: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   652: iconst_2       
        //   653: imul           
        //   654: istore          6
        //   656: iload           6
        //   658: ifle            705
        //   661: aload_0        
        //   662: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   665: iload           6
        //   667: invokevirtual   java/util/Random.nextInt:(I)I
        //   670: istore          7
        //   672: aload_1        
        //   673: aload_0        
        //   674: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   677: invokevirtual   net/minecraft/world/biome/BiomeGenBase.getRandomWorldGenForGrass:(Ljava/util/Random;)Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   680: aload_0        
        //   681: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   684: aload_0        
        //   685: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   688: aload_0        
        //   689: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   692: iload           4
        //   694: iload           7
        //   696: iload           5
        //   698: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   701: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   704: pop            
        //   705: iinc            3, 1
        //   708: goto            594
        //   711: iconst_0       
        //   712: aload_0        
        //   713: getfield        net/minecraft/world/biome/BiomeDecorator.deadBushPerChunk:I
        //   716: if_icmpge       827
        //   719: aload_0        
        //   720: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   723: bipush          16
        //   725: invokevirtual   java/util/Random.nextInt:(I)I
        //   728: bipush          8
        //   730: iadd           
        //   731: istore          4
        //   733: aload_0        
        //   734: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   737: bipush          16
        //   739: invokevirtual   java/util/Random.nextInt:(I)I
        //   742: bipush          8
        //   744: iadd           
        //   745: istore          5
        //   747: aload_0        
        //   748: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   751: aload_0        
        //   752: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   755: iload           4
        //   757: iconst_0       
        //   758: iload           5
        //   760: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   763: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   766: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   769: iconst_2       
        //   770: imul           
        //   771: istore          6
        //   773: iload           6
        //   775: ifle            821
        //   778: aload_0        
        //   779: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   782: iload           6
        //   784: invokevirtual   java/util/Random.nextInt:(I)I
        //   787: istore          7
        //   789: new             Lnet/minecraft/world/gen/feature/WorldGenDeadBush;
        //   792: dup            
        //   793: invokespecial   net/minecraft/world/gen/feature/WorldGenDeadBush.<init>:()V
        //   796: aload_0        
        //   797: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   800: aload_0        
        //   801: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   804: aload_0        
        //   805: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   808: iload           4
        //   810: iload           7
        //   812: iload           5
        //   814: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   817: invokevirtual   net/minecraft/world/gen/feature/WorldGenDeadBush.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   820: pop            
        //   821: iinc            3, 1
        //   824: goto            711
        //   827: iconst_0       
        //   828: aload_0        
        //   829: getfield        net/minecraft/world/biome/BiomeDecorator.waterlilyPerChunk:I
        //   832: if_icmpge       981
        //   835: aload_0        
        //   836: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   839: bipush          16
        //   841: invokevirtual   java/util/Random.nextInt:(I)I
        //   844: bipush          8
        //   846: iadd           
        //   847: istore          4
        //   849: aload_0        
        //   850: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   853: bipush          16
        //   855: invokevirtual   java/util/Random.nextInt:(I)I
        //   858: bipush          8
        //   860: iadd           
        //   861: istore          5
        //   863: aload_0        
        //   864: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   867: aload_0        
        //   868: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   871: iload           4
        //   873: iconst_0       
        //   874: iload           5
        //   876: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   879: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   882: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   885: iconst_2       
        //   886: imul           
        //   887: istore          6
        //   889: iload           6
        //   891: ifle            975
        //   894: aload_0        
        //   895: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   898: iload           6
        //   900: invokevirtual   java/util/Random.nextInt:(I)I
        //   903: istore          7
        //   905: aload_0        
        //   906: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   909: iload           4
        //   911: iload           7
        //   913: iload           5
        //   915: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   918: astore          8
        //   920: aload           8
        //   922: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   925: ifle            957
        //   928: aload           8
        //   930: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   933: astore          9
        //   935: aload_0        
        //   936: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   939: aload           9
        //   941: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   944: ifne            950
        //   947: goto            957
        //   950: aload           9
        //   952: astore          8
        //   954: goto            920
        //   957: aload_0        
        //   958: getfield        net/minecraft/world/biome/BiomeDecorator.waterlilyGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   961: aload_0        
        //   962: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   965: aload_0        
        //   966: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   969: aload           8
        //   971: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   974: pop            
        //   975: iinc            3, 1
        //   978: goto            827
        //   981: iconst_0       
        //   982: aload_0        
        //   983: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomsPerChunk:I
        //   986: if_icmpge       1188
        //   989: aload_0        
        //   990: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   993: iconst_4       
        //   994: invokevirtual   java/util/Random.nextInt:(I)I
        //   997: ifne            1067
        //  1000: aload_0        
        //  1001: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1004: bipush          16
        //  1006: invokevirtual   java/util/Random.nextInt:(I)I
        //  1009: bipush          8
        //  1011: iadd           
        //  1012: istore          4
        //  1014: aload_0        
        //  1015: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1018: bipush          16
        //  1020: invokevirtual   java/util/Random.nextInt:(I)I
        //  1023: bipush          8
        //  1025: iadd           
        //  1026: istore          5
        //  1028: aload_0        
        //  1029: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1032: aload_0        
        //  1033: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1036: iload           4
        //  1038: iconst_0       
        //  1039: iload           5
        //  1041: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1044: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1047: astore          6
        //  1049: aload_0        
        //  1050: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomBrownGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1053: aload_0        
        //  1054: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1057: aload_0        
        //  1058: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1061: aload           6
        //  1063: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1066: pop            
        //  1067: aload_0        
        //  1068: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1071: bipush          8
        //  1073: invokevirtual   java/util/Random.nextInt:(I)I
        //  1076: ifne            1182
        //  1079: aload_0        
        //  1080: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1083: bipush          16
        //  1085: invokevirtual   java/util/Random.nextInt:(I)I
        //  1088: bipush          8
        //  1090: iadd           
        //  1091: istore          4
        //  1093: aload_0        
        //  1094: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1097: bipush          16
        //  1099: invokevirtual   java/util/Random.nextInt:(I)I
        //  1102: bipush          8
        //  1104: iadd           
        //  1105: istore          5
        //  1107: aload_0        
        //  1108: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1111: aload_0        
        //  1112: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1115: iload           4
        //  1117: iconst_0       
        //  1118: iload           5
        //  1120: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1123: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1126: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1129: iconst_2       
        //  1130: imul           
        //  1131: istore          6
        //  1133: iload           6
        //  1135: ifle            1182
        //  1138: aload_0        
        //  1139: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1142: iload           6
        //  1144: invokevirtual   java/util/Random.nextInt:(I)I
        //  1147: istore          7
        //  1149: aload_0        
        //  1150: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1153: iload           4
        //  1155: iload           7
        //  1157: iload           5
        //  1159: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1162: astore          8
        //  1164: aload_0        
        //  1165: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomRedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1168: aload_0        
        //  1169: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1172: aload_0        
        //  1173: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1176: aload           8
        //  1178: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1181: pop            
        //  1182: iinc            3, 1
        //  1185: goto            981
        //  1188: aload_0        
        //  1189: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1192: iconst_4       
        //  1193: invokevirtual   java/util/Random.nextInt:(I)I
        //  1196: ifne            1295
        //  1199: aload_0        
        //  1200: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1203: bipush          16
        //  1205: invokevirtual   java/util/Random.nextInt:(I)I
        //  1208: bipush          8
        //  1210: iadd           
        //  1211: istore_3       
        //  1212: aload_0        
        //  1213: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1216: bipush          16
        //  1218: invokevirtual   java/util/Random.nextInt:(I)I
        //  1221: bipush          8
        //  1223: iadd           
        //  1224: istore          4
        //  1226: aload_0        
        //  1227: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1230: aload_0        
        //  1231: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1234: iconst_0       
        //  1235: iconst_0       
        //  1236: iload           4
        //  1238: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1241: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1244: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1247: iconst_2       
        //  1248: imul           
        //  1249: istore          5
        //  1251: iload           5
        //  1253: ifle            1295
        //  1256: aload_0        
        //  1257: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1260: iload           5
        //  1262: invokevirtual   java/util/Random.nextInt:(I)I
        //  1265: istore          6
        //  1267: aload_0        
        //  1268: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomBrownGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1271: aload_0        
        //  1272: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1275: aload_0        
        //  1276: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1279: aload_0        
        //  1280: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1283: iconst_0       
        //  1284: iload           6
        //  1286: iload           4
        //  1288: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1291: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1294: pop            
        //  1295: aload_0        
        //  1296: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1299: bipush          8
        //  1301: invokevirtual   java/util/Random.nextInt:(I)I
        //  1304: ifne            1403
        //  1307: aload_0        
        //  1308: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1311: bipush          16
        //  1313: invokevirtual   java/util/Random.nextInt:(I)I
        //  1316: bipush          8
        //  1318: iadd           
        //  1319: istore_3       
        //  1320: aload_0        
        //  1321: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1324: bipush          16
        //  1326: invokevirtual   java/util/Random.nextInt:(I)I
        //  1329: bipush          8
        //  1331: iadd           
        //  1332: istore          4
        //  1334: aload_0        
        //  1335: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1338: aload_0        
        //  1339: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1342: iconst_0       
        //  1343: iconst_0       
        //  1344: iload           4
        //  1346: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1349: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1352: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1355: iconst_2       
        //  1356: imul           
        //  1357: istore          5
        //  1359: iload           5
        //  1361: ifle            1403
        //  1364: aload_0        
        //  1365: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1368: iload           5
        //  1370: invokevirtual   java/util/Random.nextInt:(I)I
        //  1373: istore          6
        //  1375: aload_0        
        //  1376: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomRedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1379: aload_0        
        //  1380: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1383: aload_0        
        //  1384: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1387: aload_0        
        //  1388: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1391: iconst_0       
        //  1392: iload           6
        //  1394: iload           4
        //  1396: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1399: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1402: pop            
        //  1403: iconst_0       
        //  1404: aload_0        
        //  1405: getfield        net/minecraft/world/biome/BiomeDecorator.reedsPerChunk:I
        //  1408: if_icmpge       1516
        //  1411: aload_0        
        //  1412: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1415: bipush          16
        //  1417: invokevirtual   java/util/Random.nextInt:(I)I
        //  1420: bipush          8
        //  1422: iadd           
        //  1423: istore          4
        //  1425: aload_0        
        //  1426: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1429: bipush          16
        //  1431: invokevirtual   java/util/Random.nextInt:(I)I
        //  1434: bipush          8
        //  1436: iadd           
        //  1437: istore          5
        //  1439: aload_0        
        //  1440: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1443: aload_0        
        //  1444: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1447: iload           4
        //  1449: iconst_0       
        //  1450: iload           5
        //  1452: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1455: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1458: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1461: iconst_2       
        //  1462: imul           
        //  1463: istore          6
        //  1465: iload           6
        //  1467: ifle            1510
        //  1470: aload_0        
        //  1471: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1474: iload           6
        //  1476: invokevirtual   java/util/Random.nextInt:(I)I
        //  1479: istore          7
        //  1481: aload_0        
        //  1482: getfield        net/minecraft/world/biome/BiomeDecorator.reedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1485: aload_0        
        //  1486: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1489: aload_0        
        //  1490: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1493: aload_0        
        //  1494: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1497: iload           4
        //  1499: iload           7
        //  1501: iload           5
        //  1503: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1506: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1509: pop            
        //  1510: iinc            3, 1
        //  1513: goto            1403
        //  1516: aload_0        
        //  1517: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1520: bipush          16
        //  1522: invokevirtual   java/util/Random.nextInt:(I)I
        //  1525: bipush          8
        //  1527: iadd           
        //  1528: istore          4
        //  1530: aload_0        
        //  1531: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1534: bipush          16
        //  1536: invokevirtual   java/util/Random.nextInt:(I)I
        //  1539: bipush          8
        //  1541: iadd           
        //  1542: istore          5
        //  1544: aload_0        
        //  1545: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1548: aload_0        
        //  1549: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1552: iload           4
        //  1554: iconst_0       
        //  1555: iload           5
        //  1557: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1560: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1563: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1566: iconst_2       
        //  1567: imul           
        //  1568: istore          6
        //  1570: iload           6
        //  1572: ifle            1615
        //  1575: aload_0        
        //  1576: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1579: iload           6
        //  1581: invokevirtual   java/util/Random.nextInt:(I)I
        //  1584: istore          7
        //  1586: aload_0        
        //  1587: getfield        net/minecraft/world/biome/BiomeDecorator.reedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1590: aload_0        
        //  1591: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1594: aload_0        
        //  1595: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1598: aload_0        
        //  1599: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1602: iload           4
        //  1604: iload           7
        //  1606: iload           5
        //  1608: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1611: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1614: pop            
        //  1615: iinc            3, 1
        //  1618: goto            1516
        //  1621: aload_0        
        //  1622: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1625: bipush          32
        //  1627: invokevirtual   java/util/Random.nextInt:(I)I
        //  1630: ifne            1732
        //  1633: aload_0        
        //  1634: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1637: bipush          16
        //  1639: invokevirtual   java/util/Random.nextInt:(I)I
        //  1642: bipush          8
        //  1644: iadd           
        //  1645: istore_3       
        //  1646: aload_0        
        //  1647: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1650: bipush          16
        //  1652: invokevirtual   java/util/Random.nextInt:(I)I
        //  1655: bipush          8
        //  1657: iadd           
        //  1658: istore          4
        //  1660: aload_0        
        //  1661: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1664: aload_0        
        //  1665: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1668: iconst_0       
        //  1669: iconst_0       
        //  1670: iload           4
        //  1672: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1675: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1678: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1681: iconst_2       
        //  1682: imul           
        //  1683: istore          5
        //  1685: iload           5
        //  1687: ifle            1732
        //  1690: aload_0        
        //  1691: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1694: iload           5
        //  1696: invokevirtual   java/util/Random.nextInt:(I)I
        //  1699: istore          6
        //  1701: new             Lnet/minecraft/world/gen/feature/WorldGenPumpkin;
        //  1704: dup            
        //  1705: invokespecial   net/minecraft/world/gen/feature/WorldGenPumpkin.<init>:()V
        //  1708: aload_0        
        //  1709: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1712: aload_0        
        //  1713: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1716: aload_0        
        //  1717: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1720: iconst_0       
        //  1721: iload           6
        //  1723: iload           4
        //  1725: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1728: invokevirtual   net/minecraft/world/gen/feature/WorldGenPumpkin.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1731: pop            
        //  1732: iconst_0       
        //  1733: aload_0        
        //  1734: getfield        net/minecraft/world/biome/BiomeDecorator.cactiPerChunk:I
        //  1737: if_icmpge       1845
        //  1740: aload_0        
        //  1741: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1744: bipush          16
        //  1746: invokevirtual   java/util/Random.nextInt:(I)I
        //  1749: bipush          8
        //  1751: iadd           
        //  1752: istore          4
        //  1754: aload_0        
        //  1755: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1758: bipush          16
        //  1760: invokevirtual   java/util/Random.nextInt:(I)I
        //  1763: bipush          8
        //  1765: iadd           
        //  1766: istore          5
        //  1768: aload_0        
        //  1769: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1772: aload_0        
        //  1773: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1776: iload           4
        //  1778: iconst_0       
        //  1779: iload           5
        //  1781: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1784: invokevirtual   net/minecraft/world/World.getHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1787: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1790: iconst_2       
        //  1791: imul           
        //  1792: istore          6
        //  1794: iload           6
        //  1796: ifle            1839
        //  1799: aload_0        
        //  1800: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1803: iload           6
        //  1805: invokevirtual   java/util/Random.nextInt:(I)I
        //  1808: istore          7
        //  1810: aload_0        
        //  1811: getfield        net/minecraft/world/biome/BiomeDecorator.cactusGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1814: aload_0        
        //  1815: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1818: aload_0        
        //  1819: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1822: aload_0        
        //  1823: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1826: iload           4
        //  1828: iload           7
        //  1830: iload           5
        //  1832: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1835: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1838: pop            
        //  1839: iinc            3, 1
        //  1842: goto            1732
        //  1845: aload_0        
        //  1846: getfield        net/minecraft/world/biome/BiomeDecorator.generateLakes:Z
        //  1849: ifeq            2061
        //  1852: aload_0        
        //  1853: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1856: bipush          16
        //  1858: invokevirtual   java/util/Random.nextInt:(I)I
        //  1861: bipush          8
        //  1863: iadd           
        //  1864: istore          4
        //  1866: aload_0        
        //  1867: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1870: bipush          16
        //  1872: invokevirtual   java/util/Random.nextInt:(I)I
        //  1875: bipush          8
        //  1877: iadd           
        //  1878: istore          5
        //  1880: aload_0        
        //  1881: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1884: sipush          248
        //  1887: invokevirtual   java/util/Random.nextInt:(I)I
        //  1890: bipush          8
        //  1892: iadd           
        //  1893: istore          6
        //  1895: iload           6
        //  1897: ifle            1950
        //  1900: aload_0        
        //  1901: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1904: iload           6
        //  1906: invokevirtual   java/util/Random.nextInt:(I)I
        //  1909: istore          7
        //  1911: aload_0        
        //  1912: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1915: iload           4
        //  1917: iload           7
        //  1919: iload           5
        //  1921: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1924: astore          8
        //  1926: new             Lnet/minecraft/world/gen/feature/WorldGenLiquids;
        //  1929: dup            
        //  1930: getstatic       net/minecraft/init/Blocks.flowing_water:Lnet/minecraft/block/BlockDynamicLiquid;
        //  1933: invokespecial   net/minecraft/world/gen/feature/WorldGenLiquids.<init>:(Lnet/minecraft/block/Block;)V
        //  1936: aload_0        
        //  1937: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1940: aload_0        
        //  1941: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1944: aload           8
        //  1946: invokevirtual   net/minecraft/world/gen/feature/WorldGenLiquids.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1949: pop            
        //  1950: iinc            3, 1
        //  1953: goto            1852
        //  1956: aload_0        
        //  1957: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1960: bipush          16
        //  1962: invokevirtual   java/util/Random.nextInt:(I)I
        //  1965: bipush          8
        //  1967: iadd           
        //  1968: istore          4
        //  1970: aload_0        
        //  1971: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1974: bipush          16
        //  1976: invokevirtual   java/util/Random.nextInt:(I)I
        //  1979: bipush          8
        //  1981: iadd           
        //  1982: istore          5
        //  1984: aload_0        
        //  1985: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1988: aload_0        
        //  1989: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1992: aload_0        
        //  1993: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1996: sipush          240
        //  1999: invokevirtual   java/util/Random.nextInt:(I)I
        //  2002: bipush          8
        //  2004: iadd           
        //  2005: invokevirtual   java/util/Random.nextInt:(I)I
        //  2008: bipush          8
        //  2010: iadd           
        //  2011: invokevirtual   java/util/Random.nextInt:(I)I
        //  2014: istore          6
        //  2016: aload_0        
        //  2017: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  2020: iload           4
        //  2022: iload           6
        //  2024: iload           5
        //  2026: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  2029: astore          7
        //  2031: new             Lnet/minecraft/world/gen/feature/WorldGenLiquids;
        //  2034: dup            
        //  2035: getstatic       net/minecraft/init/Blocks.flowing_lava:Lnet/minecraft/block/BlockDynamicLiquid;
        //  2038: invokespecial   net/minecraft/world/gen/feature/WorldGenLiquids.<init>:(Lnet/minecraft/block/Block;)V
        //  2041: aload_0        
        //  2042: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  2045: aload_0        
        //  2046: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  2049: aload           7
        //  2051: invokevirtual   net/minecraft/world/gen/feature/WorldGenLiquids.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  2054: pop            
        //  2055: iinc            3, 1
        //  2058: goto            1956
        //  2061: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
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
    
    protected void generateOres() {
        this.genStandardOre1(this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        this.genStandardOre2(this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
    }
    
    public BiomeDecorator() {
        this.clayGen = new WorldGenClay(4);
        this.sandGen = new WorldGenSand(Blocks.sand, 7);
        this.gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
        this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
        this.mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
        this.bigMushroomGen = new WorldGenBigMushroom();
        this.reedGen = new WorldGenReed();
        this.cactusGen = new WorldGenCactus();
        this.waterlilyGen = new WorldGenWaterlily();
        this.flowersPerChunk = 2;
        this.grassPerChunk = 1;
        this.sandPerChunk = 1;
        this.sandPerChunk2 = 3;
        this.clayPerChunk = 1;
        this.generateLakes = true;
    }
}
