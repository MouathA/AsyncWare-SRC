package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockStairs extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyEnum SHAPE;
    private int rayTracePass;
    private boolean hasRaytraced;
    public static final PropertyEnum HALF;
    private static final int[][] field_150150_a;
    private final Block modelBlock;
    private final IBlockState modelState;
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return this.modelBlock.getMapColor(this.modelState);
    }
    
    protected BlockStairs(final IBlockState modelState) {
        super(modelState.getBlock().blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
        this.modelBlock = modelState.getBlock();
        this.modelState = modelState;
        this.setHardness(this.modelBlock.blockHardness);
        this.setResistance(this.modelBlock.blockResistance / 3.0f);
        this.setStepSound(this.modelBlock.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.modelBlock.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return this.modelBlock.modifyAcceleration(world, blockPos, entity, vec3);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final MovingObjectPosition[] array = new MovingObjectPosition[8];
        final IBlockState blockState = world.getBlockState(blockPos);
        final int[] array2 = BlockStairs.field_150150_a[((EnumFacing)blockState.getValue(BlockStairs.FACING)).getHorizontalIndex() + ((blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) ? 4 : 0)];
        this.hasRaytraced = true;
        while (true) {
            this.rayTracePass = 0;
            if (Arrays.binarySearch(array2, 0) < 0) {
                array[0] = super.collisionRayTrace(world, blockPos, vec3, vec4);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockStairs.FACING, entityLivingBase.getHorizontalFacing()).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
        return (enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) ? withProperty.withProperty(BlockStairs.HALF, EnumHalf.BOTTOM) : withProperty.withProperty(BlockStairs.HALF, EnumHalf.TOP);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return this.modelBlock.onBlockActivated(world, blockPos, this.modelState, entityPlayer, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.randomDisplayTick(world, blockPos, blockState, random);
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        HALF = PropertyEnum.create("half", EnumHalf.class);
        SHAPE = PropertyEnum.create("shape", EnumShape.class);
        field_150150_a = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.updateTick(world, blockPos, blockState, random);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.modelBlock.getMixedBrightnessForBlock(blockAccess, blockPos);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) {}
        final int n = 0x0 | 5 - ((EnumFacing)blockState.getValue(BlockStairs.FACING)).getIndex();
        return 0;
    }
    
    @Override
    public int tickRate(final World world) {
        return this.modelBlock.tickRate(world);
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return this.modelBlock.canCollideCheck(blockState, b);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return this.modelBlock.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.modelBlock.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    public static boolean isBlockStairs(final Block block) {
        return block instanceof BlockStairs;
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        this.modelBlock.onBlockDestroyedByExplosion(world, blockPos, explosion);
    }
    
    public void setBaseCollisionBounds(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue(BlockStairs.HALF) == EnumHalf.TOP) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.onNeighborBlockChange(world, blockPos, this.modelState, Blocks.air);
        this.modelBlock.onBlockAdded(world, blockPos, this.modelState);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p0, final BlockPos p1, final IBlockState p2, final AxisAlignedBB p3, final List p4, final Entity p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: invokevirtual   net/minecraft/block/BlockStairs.setBaseCollisionBounds:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)V
        //     6: aload_0        
        //     7: aload_1        
        //     8: aload_2        
        //     9: aload_3        
        //    10: aload           4
        //    12: aload           5
        //    14: aload           6
        //    16: invokespecial   net/minecraft/block/Block.addCollisionBoxesToList:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;)V
        //    19: aload_0        
        //    20: aload_1        
        //    21: aload_2        
        //    22: invokevirtual   net/minecraft/block/BlockStairs.func_176306_h:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z
        //    25: istore          7
        //    27: aload_0        
        //    28: aload_1        
        //    29: aload_2        
        //    30: aload_3        
        //    31: aload           4
        //    33: aload           5
        //    35: aload           6
        //    37: invokespecial   net/minecraft/block/Block.addCollisionBoxesToList:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;)V
        //    40: iload           7
        //    42: ifeq            64
        //    45: aload_0        
        //    46: aload_1        
        //    47: aload_2        
        //    48: if_acmpne       64
        //    51: aload_0        
        //    52: aload_1        
        //    53: aload_2        
        //    54: aload_3        
        //    55: aload           4
        //    57: aload           5
        //    59: aload           6
        //    61: invokespecial   net/minecraft/block/Block.addCollisionBoxesToList:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;)V
        //    64: aload_0        
        //    65: fconst_0       
        //    66: fconst_0       
        //    67: fconst_0       
        //    68: fconst_1       
        //    69: fconst_1       
        //    70: fconst_1       
        //    71: invokevirtual   net/minecraft/block/BlockStairs.setBlockBounds:(FFFFFF)V
        //    74: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0064 (coming from #0048).
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
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int func_176305_g(final IBlockAccess p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     7: astore_3       
        //     8: aload_3        
        //     9: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    12: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    17: checkcast       Lnet/minecraft/util/EnumFacing;
        //    20: astore          4
        //    22: aload_3        
        //    23: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    26: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    31: checkcast       Lnet/minecraft/block/BlockStairs$EnumHalf;
        //    34: astore          5
        //    36: aload           5
        //    38: getstatic       net/minecraft/block/BlockStairs$EnumHalf.TOP:Lnet/minecraft/block/BlockStairs$EnumHalf;
        //    41: if_acmpne       48
        //    44: iconst_1       
        //    45: goto            49
        //    48: iconst_0       
        //    49: istore          6
        //    51: aload           4
        //    53: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    56: if_acmpne       177
        //    59: aload_1        
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //    64: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    69: astore          7
        //    71: aload           7
        //    73: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    78: astore          8
        //    80: aload           8
        //    82: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //    85: ifeq            174
        //    88: aload           5
        //    90: aload           7
        //    92: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    95: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   100: if_acmpne       174
        //   103: aload           7
        //   105: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   108: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   113: checkcast       Lnet/minecraft/util/EnumFacing;
        //   116: astore          9
        //   118: aload           9
        //   120: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   123: if_acmpne       146
        //   126: aload_1        
        //   127: aload_2        
        //   128: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   131: aload_3        
        //   132: ifeq            146
        //   135: iload           6
        //   137: ifeq            144
        //   140: iconst_1       
        //   141: goto            145
        //   144: iconst_2       
        //   145: ireturn        
        //   146: aload           9
        //   148: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   151: if_acmpne       174
        //   154: aload_1        
        //   155: aload_2        
        //   156: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   159: aload_3        
        //   160: ifeq            174
        //   163: iload           6
        //   165: ifeq            172
        //   168: iconst_2       
        //   169: goto            173
        //   172: iconst_1       
        //   173: ireturn        
        //   174: goto            552
        //   177: aload           4
        //   179: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   182: if_acmpne       303
        //   185: aload_1        
        //   186: aload_2        
        //   187: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   190: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   195: astore          7
        //   197: aload           7
        //   199: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   204: astore          8
        //   206: aload           8
        //   208: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   211: ifeq            300
        //   214: aload           5
        //   216: aload           7
        //   218: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   221: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   226: if_acmpne       300
        //   229: aload           7
        //   231: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   234: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   239: checkcast       Lnet/minecraft/util/EnumFacing;
        //   242: astore          9
        //   244: aload           9
        //   246: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   249: if_acmpne       272
        //   252: aload_1        
        //   253: aload_2        
        //   254: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   257: aload_3        
        //   258: ifeq            272
        //   261: iload           6
        //   263: ifeq            270
        //   266: iconst_2       
        //   267: goto            271
        //   270: iconst_1       
        //   271: ireturn        
        //   272: aload           9
        //   274: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   277: if_acmpne       300
        //   280: aload_1        
        //   281: aload_2        
        //   282: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   285: aload_3        
        //   286: ifeq            300
        //   289: iload           6
        //   291: ifeq            298
        //   294: iconst_1       
        //   295: goto            299
        //   298: iconst_2       
        //   299: ireturn        
        //   300: goto            552
        //   303: aload           4
        //   305: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   308: if_acmpne       429
        //   311: aload_1        
        //   312: aload_2        
        //   313: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   316: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   321: astore          7
        //   323: aload           7
        //   325: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   330: astore          8
        //   332: aload           8
        //   334: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   337: ifeq            426
        //   340: aload           5
        //   342: aload           7
        //   344: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   347: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   352: if_acmpne       426
        //   355: aload           7
        //   357: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   360: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   365: checkcast       Lnet/minecraft/util/EnumFacing;
        //   368: astore          9
        //   370: aload           9
        //   372: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   375: if_acmpne       398
        //   378: aload_1        
        //   379: aload_2        
        //   380: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   383: aload_3        
        //   384: ifeq            398
        //   387: iload           6
        //   389: ifeq            396
        //   392: iconst_2       
        //   393: goto            397
        //   396: iconst_1       
        //   397: ireturn        
        //   398: aload           9
        //   400: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   403: if_acmpne       426
        //   406: aload_1        
        //   407: aload_2        
        //   408: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   411: aload_3        
        //   412: ifeq            426
        //   415: iload           6
        //   417: ifeq            424
        //   420: iconst_1       
        //   421: goto            425
        //   424: iconst_2       
        //   425: ireturn        
        //   426: goto            552
        //   429: aload           4
        //   431: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   434: if_acmpne       552
        //   437: aload_1        
        //   438: aload_2        
        //   439: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   442: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   447: astore          7
        //   449: aload           7
        //   451: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   456: astore          8
        //   458: aload           8
        //   460: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   463: ifeq            552
        //   466: aload           5
        //   468: aload           7
        //   470: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   473: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   478: if_acmpne       552
        //   481: aload           7
        //   483: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   486: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   491: checkcast       Lnet/minecraft/util/EnumFacing;
        //   494: astore          9
        //   496: aload           9
        //   498: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   501: if_acmpne       524
        //   504: aload_1        
        //   505: aload_2        
        //   506: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   509: aload_3        
        //   510: ifeq            524
        //   513: iload           6
        //   515: ifeq            522
        //   518: iconst_1       
        //   519: goto            523
        //   522: iconst_2       
        //   523: ireturn        
        //   524: aload           9
        //   526: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   529: if_acmpne       552
        //   532: aload_1        
        //   533: aload_2        
        //   534: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   537: aload_3        
        //   538: ifeq            552
        //   541: iload           6
        //   543: ifeq            550
        //   546: iconst_2       
        //   547: goto            551
        //   550: iconst_1       
        //   551: ireturn        
        //   552: iconst_0       
        //   553: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0552 (coming from #0538).
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
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStairs.FACING, BlockStairs.HALF, BlockStairs.SHAPE });
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.modelBlock.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.breakBlock(world, blockPos, this.modelState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStairs.HALF, ((n & 0x4) > 0) ? EnumHalf.TOP : EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.getFront(5 - (n & 0x3)));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.hasRaytraced) {
            this.setBlockBounds(0.5f * (this.rayTracePass % 2), 0.5f * (this.rayTracePass / 4 % 2), 0.5f * (this.rayTracePass / 2 % 2), 0.5f + 0.5f * (this.rayTracePass % 2), 0.5f + 0.5f * (this.rayTracePass / 4 % 2), 0.5f + 0.5f * (this.rayTracePass / 2 % 2));
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public float getExplosionResistance(final Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return this.modelBlock.getBlockLayer();
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess == blockPos) {
            switch (this.func_176305_g(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    break;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_RIGHT);
                    break;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_LEFT);
                    break;
                }
            }
        }
        else {
            switch (this.func_176307_f(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    break;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
                    break;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
                    break;
                }
            }
        }
        return blockState;
    }
    
    public int func_176307_f(final IBlockAccess p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     7: astore_3       
        //     8: aload_3        
        //     9: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    12: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    17: checkcast       Lnet/minecraft/util/EnumFacing;
        //    20: astore          4
        //    22: aload_3        
        //    23: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    26: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    31: checkcast       Lnet/minecraft/block/BlockStairs$EnumHalf;
        //    34: astore          5
        //    36: aload           5
        //    38: getstatic       net/minecraft/block/BlockStairs$EnumHalf.TOP:Lnet/minecraft/block/BlockStairs$EnumHalf;
        //    41: if_acmpne       48
        //    44: iconst_1       
        //    45: goto            49
        //    48: iconst_0       
        //    49: istore          6
        //    51: aload           4
        //    53: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    56: if_acmpne       177
        //    59: aload_1        
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //    64: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    69: astore          7
        //    71: aload           7
        //    73: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    78: astore          8
        //    80: aload           8
        //    82: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //    85: ifeq            174
        //    88: aload           5
        //    90: aload           7
        //    92: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    95: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   100: if_acmpne       174
        //   103: aload           7
        //   105: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   108: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   113: checkcast       Lnet/minecraft/util/EnumFacing;
        //   116: astore          9
        //   118: aload           9
        //   120: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   123: if_acmpne       146
        //   126: aload_1        
        //   127: aload_2        
        //   128: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   131: aload_3        
        //   132: ifeq            146
        //   135: iload           6
        //   137: ifeq            144
        //   140: iconst_1       
        //   141: goto            145
        //   144: iconst_2       
        //   145: ireturn        
        //   146: aload           9
        //   148: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   151: if_acmpne       174
        //   154: aload_1        
        //   155: aload_2        
        //   156: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   159: aload_3        
        //   160: ifeq            174
        //   163: iload           6
        //   165: ifeq            172
        //   168: iconst_2       
        //   169: goto            173
        //   172: iconst_1       
        //   173: ireturn        
        //   174: goto            552
        //   177: aload           4
        //   179: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   182: if_acmpne       303
        //   185: aload_1        
        //   186: aload_2        
        //   187: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   190: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   195: astore          7
        //   197: aload           7
        //   199: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   204: astore          8
        //   206: aload           8
        //   208: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   211: ifeq            300
        //   214: aload           5
        //   216: aload           7
        //   218: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   221: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   226: if_acmpne       300
        //   229: aload           7
        //   231: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   234: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   239: checkcast       Lnet/minecraft/util/EnumFacing;
        //   242: astore          9
        //   244: aload           9
        //   246: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   249: if_acmpne       272
        //   252: aload_1        
        //   253: aload_2        
        //   254: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   257: aload_3        
        //   258: ifeq            272
        //   261: iload           6
        //   263: ifeq            270
        //   266: iconst_2       
        //   267: goto            271
        //   270: iconst_1       
        //   271: ireturn        
        //   272: aload           9
        //   274: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   277: if_acmpne       300
        //   280: aload_1        
        //   281: aload_2        
        //   282: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   285: aload_3        
        //   286: ifeq            300
        //   289: iload           6
        //   291: ifeq            298
        //   294: iconst_1       
        //   295: goto            299
        //   298: iconst_2       
        //   299: ireturn        
        //   300: goto            552
        //   303: aload           4
        //   305: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   308: if_acmpne       429
        //   311: aload_1        
        //   312: aload_2        
        //   313: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   316: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   321: astore          7
        //   323: aload           7
        //   325: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   330: astore          8
        //   332: aload           8
        //   334: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   337: ifeq            426
        //   340: aload           5
        //   342: aload           7
        //   344: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   347: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   352: if_acmpne       426
        //   355: aload           7
        //   357: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   360: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   365: checkcast       Lnet/minecraft/util/EnumFacing;
        //   368: astore          9
        //   370: aload           9
        //   372: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   375: if_acmpne       398
        //   378: aload_1        
        //   379: aload_2        
        //   380: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   383: aload_3        
        //   384: ifeq            398
        //   387: iload           6
        //   389: ifeq            396
        //   392: iconst_2       
        //   393: goto            397
        //   396: iconst_1       
        //   397: ireturn        
        //   398: aload           9
        //   400: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   403: if_acmpne       426
        //   406: aload_1        
        //   407: aload_2        
        //   408: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   411: aload_3        
        //   412: ifeq            426
        //   415: iload           6
        //   417: ifeq            424
        //   420: iconst_1       
        //   421: goto            425
        //   424: iconst_2       
        //   425: ireturn        
        //   426: goto            552
        //   429: aload           4
        //   431: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   434: if_acmpne       552
        //   437: aload_1        
        //   438: aload_2        
        //   439: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   442: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   447: astore          7
        //   449: aload           7
        //   451: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   456: astore          8
        //   458: aload           8
        //   460: invokestatic    net/minecraft/block/BlockStairs.isBlockStairs:(Lnet/minecraft/block/Block;)Z
        //   463: ifeq            552
        //   466: aload           5
        //   468: aload           7
        //   470: getstatic       net/minecraft/block/BlockStairs.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //   473: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   478: if_acmpne       552
        //   481: aload           7
        //   483: getstatic       net/minecraft/block/BlockStairs.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   486: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   491: checkcast       Lnet/minecraft/util/EnumFacing;
        //   494: astore          9
        //   496: aload           9
        //   498: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   501: if_acmpne       524
        //   504: aload_1        
        //   505: aload_2        
        //   506: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   509: aload_3        
        //   510: ifeq            524
        //   513: iload           6
        //   515: ifeq            522
        //   518: iconst_1       
        //   519: goto            523
        //   522: iconst_2       
        //   523: ireturn        
        //   524: aload           9
        //   526: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   529: if_acmpne       552
        //   532: aload_1        
        //   533: aload_2        
        //   534: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   537: aload_3        
        //   538: ifeq            552
        //   541: iload           6
        //   543: ifeq            550
        //   546: iconst_2       
        //   547: goto            551
        //   550: iconst_1       
        //   551: ireturn        
        //   552: iconst_0       
        //   553: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0552 (coming from #0538).
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
    
    public enum EnumHalf implements IStringSerializable
    {
        private static final EnumHalf[] $VALUES;
        
        TOP("TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "bottom");
        
        private final String name;
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumHalf(final String s, final int n, final String name) {
            this.name = name;
        }
        
        static {
            $VALUES = new EnumHalf[] { EnumHalf.TOP, EnumHalf.BOTTOM };
        }
    }
    
    public enum EnumShape implements IStringSerializable
    {
        INNER_LEFT("INNER_LEFT", 1, "inner_left"), 
        STRAIGHT("STRAIGHT", 0, "straight"), 
        OUTER_LEFT("OUTER_LEFT", 3, "outer_left"), 
        INNER_RIGHT("INNER_RIGHT", 2, "inner_right");
        
        private final String name;
        private static final EnumShape[] $VALUES;
        
        OUTER_RIGHT("OUTER_RIGHT", 4, "outer_right");
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumShape[] { EnumShape.STRAIGHT, EnumShape.INNER_LEFT, EnumShape.INNER_RIGHT, EnumShape.OUTER_LEFT, EnumShape.OUTER_RIGHT };
        }
        
        private EnumShape(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
