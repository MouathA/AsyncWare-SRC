package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockSapling extends BlockBush implements IGrowable
{
    public static final PropertyEnum TYPE;
    public static final PropertyInteger STAGE;
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.grow(world, blockPos, blockState, random);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
    }
    
    public void generateTree(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: bipush          10
        //     4: invokevirtual   java/util/Random.nextInt:(I)I
        //     7: ifne            21
        //    10: new             Lnet/minecraft/world/gen/feature/WorldGenBigTree;
        //    13: dup            
        //    14: iconst_1       
        //    15: invokespecial   net/minecraft/world/gen/feature/WorldGenBigTree.<init>:(Z)V
        //    18: goto            29
        //    21: new             Lnet/minecraft/world/gen/feature/WorldGenTrees;
        //    24: dup            
        //    25: iconst_1       
        //    26: invokespecial   net/minecraft/world/gen/feature/WorldGenTrees.<init>:(Z)V
        //    29: astore          5
        //    31: getstatic       net/minecraft/block/BlockSapling$1.$SwitchMap$net$minecraft$block$BlockPlanks$EnumType:[I
        //    34: aload_3        
        //    35: getstatic       net/minecraft/block/BlockSapling.TYPE:Lnet/minecraft/block/properties/PropertyEnum;
        //    38: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    43: checkcast       Lnet/minecraft/block/BlockPlanks$EnumType;
        //    46: invokevirtual   net/minecraft/block/BlockPlanks$EnumType.ordinal:()I
        //    49: iaload         
        //    50: tableswitch {
        //                2: 88
        //                3: 145
        //                4: 159
        //                5: 283
        //                6: 296
        //                7: 335
        //          default: 335
        //        }
        //    88: aload_0        
        //    89: aload_1        
        //    90: aload_2        
        //    91: iconst_0       
        //    92: iconst_0       
        //    93: getstatic       net/minecraft/block/BlockPlanks$EnumType.SPRUCE:Lnet/minecraft/block/BlockPlanks$EnumType;
        //    96: ifeq            117
        //    99: new             Lnet/minecraft/world/gen/feature/WorldGenMegaPineTree;
        //   102: dup            
        //   103: iconst_0       
        //   104: aload           4
        //   106: invokevirtual   java/util/Random.nextBoolean:()Z
        //   109: invokespecial   net/minecraft/world/gen/feature/WorldGenMegaPineTree.<init>:(ZZ)V
        //   112: astore          5
        //   114: goto            335
        //   117: iinc            7, -1
        //   120: goto            88
        //   123: iinc            6, -1
        //   126: goto            88
        //   129: goto            335
        //   132: new             Lnet/minecraft/world/gen/feature/WorldGenTaiga2;
        //   135: dup            
        //   136: iconst_1       
        //   137: invokespecial   net/minecraft/world/gen/feature/WorldGenTaiga2.<init>:(Z)V
        //   140: astore          5
        //   142: goto            335
        //   145: new             Lnet/minecraft/world/gen/feature/WorldGenForest;
        //   148: dup            
        //   149: iconst_1       
        //   150: iconst_0       
        //   151: invokespecial   net/minecraft/world/gen/feature/WorldGenForest.<init>:(ZZ)V
        //   154: astore          5
        //   156: goto            335
        //   159: getstatic       net/minecraft/init/Blocks.log:Lnet/minecraft/block/Block;
        //   162: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   165: getstatic       net/minecraft/block/BlockOldLog.VARIANT:Lnet/minecraft/block/properties/PropertyEnum;
        //   168: getstatic       net/minecraft/block/BlockPlanks$EnumType.JUNGLE:Lnet/minecraft/block/BlockPlanks$EnumType;
        //   171: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   176: astore          9
        //   178: getstatic       net/minecraft/init/Blocks.leaves:Lnet/minecraft/block/BlockLeaves;
        //   181: invokevirtual   net/minecraft/block/BlockLeaves.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   184: getstatic       net/minecraft/block/BlockOldLeaf.VARIANT:Lnet/minecraft/block/properties/PropertyEnum;
        //   187: getstatic       net/minecraft/block/BlockPlanks$EnumType.JUNGLE:Lnet/minecraft/block/BlockPlanks$EnumType;
        //   190: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   195: getstatic       net/minecraft/block/BlockLeaves.CHECK_DECAY:Lnet/minecraft/block/properties/PropertyBool;
        //   198: iconst_0       
        //   199: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   202: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   207: astore          10
        //   209: aload_0        
        //   210: aload_1        
        //   211: aload_2        
        //   212: iconst_0       
        //   213: iconst_0       
        //   214: getstatic       net/minecraft/block/BlockPlanks$EnumType.JUNGLE:Lnet/minecraft/block/BlockPlanks$EnumType;
        //   217: ifeq            241
        //   220: new             Lnet/minecraft/world/gen/feature/WorldGenMegaJungle;
        //   223: dup            
        //   224: iconst_1       
        //   225: bipush          10
        //   227: bipush          20
        //   229: aload           9
        //   231: aload           10
        //   233: invokespecial   net/minecraft/world/gen/feature/WorldGenMegaJungle.<init>:(ZIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;)V
        //   236: astore          5
        //   238: goto            335
        //   241: iinc            7, -1
        //   244: goto            209
        //   247: iinc            6, -1
        //   250: goto            209
        //   253: goto            335
        //   256: new             Lnet/minecraft/world/gen/feature/WorldGenTrees;
        //   259: dup            
        //   260: iconst_1       
        //   261: iconst_4       
        //   262: aload           4
        //   264: bipush          7
        //   266: invokevirtual   java/util/Random.nextInt:(I)I
        //   269: iadd           
        //   270: aload           9
        //   272: aload           10
        //   274: iconst_0       
        //   275: invokespecial   net/minecraft/world/gen/feature/WorldGenTrees.<init>:(ZILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
        //   278: astore          5
        //   280: goto            335
        //   283: new             Lnet/minecraft/world/gen/feature/WorldGenSavannaTree;
        //   286: dup            
        //   287: iconst_1       
        //   288: invokespecial   net/minecraft/world/gen/feature/WorldGenSavannaTree.<init>:(Z)V
        //   291: astore          5
        //   293: goto            335
        //   296: aload_0        
        //   297: aload_1        
        //   298: aload_2        
        //   299: iconst_0       
        //   300: iconst_0       
        //   301: getstatic       net/minecraft/block/BlockPlanks$EnumType.DARK_OAK:Lnet/minecraft/block/BlockPlanks$EnumType;
        //   304: ifeq            320
        //   307: new             Lnet/minecraft/world/gen/feature/WorldGenCanopyTree;
        //   310: dup            
        //   311: iconst_1       
        //   312: invokespecial   net/minecraft/world/gen/feature/WorldGenCanopyTree.<init>:(Z)V
        //   315: astore          5
        //   317: goto            335
        //   320: iinc            7, -1
        //   323: goto            296
        //   326: iinc            6, -1
        //   329: goto            296
        //   332: goto            335
        //   335: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //   338: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   341: astore          9
        //   343: aload_1        
        //   344: aload_2        
        //   345: iconst_0       
        //   346: iconst_0       
        //   347: iconst_0       
        //   348: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   351: aload           9
        //   353: iconst_4       
        //   354: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   357: pop            
        //   358: aload_1        
        //   359: aload_2        
        //   360: iconst_1       
        //   361: iconst_0       
        //   362: iconst_0       
        //   363: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   366: aload           9
        //   368: iconst_4       
        //   369: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   372: pop            
        //   373: aload_1        
        //   374: aload_2        
        //   375: iconst_0       
        //   376: iconst_0       
        //   377: iconst_1       
        //   378: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   381: aload           9
        //   383: iconst_4       
        //   384: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   387: pop            
        //   388: aload_1        
        //   389: aload_2        
        //   390: iconst_1       
        //   391: iconst_0       
        //   392: iconst_1       
        //   393: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   396: aload           9
        //   398: iconst_4       
        //   399: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   402: pop            
        //   403: goto            415
        //   406: aload_1        
        //   407: aload_2        
        //   408: aload           9
        //   410: iconst_4       
        //   411: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   414: pop            
        //   415: aload           5
        //   417: aload_1        
        //   418: aload           4
        //   420: aload_2        
        //   421: iconst_0       
        //   422: iconst_0       
        //   423: iconst_0       
        //   424: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   427: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   430: ifne            500
        //   433: aload_1        
        //   434: aload_2        
        //   435: iconst_0       
        //   436: iconst_0       
        //   437: iconst_0       
        //   438: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   441: aload_3        
        //   442: iconst_4       
        //   443: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   446: pop            
        //   447: aload_1        
        //   448: aload_2        
        //   449: iconst_1       
        //   450: iconst_0       
        //   451: iconst_0       
        //   452: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   455: aload_3        
        //   456: iconst_4       
        //   457: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   460: pop            
        //   461: aload_1        
        //   462: aload_2        
        //   463: iconst_0       
        //   464: iconst_0       
        //   465: iconst_1       
        //   466: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   469: aload_3        
        //   470: iconst_4       
        //   471: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   474: pop            
        //   475: aload_1        
        //   476: aload_2        
        //   477: iconst_1       
        //   478: iconst_0       
        //   479: iconst_1       
        //   480: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   483: aload_3        
        //   484: iconst_4       
        //   485: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   488: pop            
        //   489: goto            500
        //   492: aload_1        
        //   493: aload_2        
        //   494: aload_3        
        //   495: iconst_4       
        //   496: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   499: pop            
        //   500: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0296 (coming from #0323).
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
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            super.updateTick(world, blockPos, blockState, random);
            if (world.getLightFromNeighbors(blockPos.up()) >= 9 && random.nextInt(7) == 0) {
                this.grow(world, blockPos, blockState, random);
            }
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockSapling.TYPE)).getMetadata();
    }
    
    static {
        TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
        STAGE = PropertyInteger.create("stage", 0, 1);
    }
    
    protected BlockSapling() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.OAK).withProperty(BlockSapling.STAGE, 0));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockSapling.TYPE)).getMetadata();
        final int n2 = 0x0 | (int)blockState.getValue(BlockSapling.STAGE) << 3;
        return 0;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return world.rand.nextFloat() < 0.45;
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.byMetadata(n & 0x7)).withProperty(BlockSapling.STAGE, (n & 0x8) >> 3);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final BlockPlanks.EnumType[] values = BlockPlanks.EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    public void grow(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if ((int)blockState.getValue(BlockSapling.STAGE) == 0) {
            world.setBlockState(blockPos, blockState.cycleProperty(BlockSapling.STAGE), 4);
        }
        else {
            this.generateTree(world, blockPos, blockState, random);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSapling.TYPE, BlockSapling.STAGE });
    }
}
