package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class BlockRailBase extends Block
{
    protected final boolean isPowered;
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final EnumRailDirection enumRailDirection = (EnumRailDirection)blockState.getValue(this.getShapeProperty());
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {}
            if (enumRailDirection != EnumRailDirection.ASCENDING_EAST || World.doesBlockHaveSolidTopSurface(world, blockToAir.east())) {
                if (enumRailDirection != EnumRailDirection.ASCENDING_WEST || World.doesBlockHaveSolidTopSurface(world, blockToAir.west())) {
                    if (enumRailDirection != EnumRailDirection.ASCENDING_NORTH || World.doesBlockHaveSolidTopSurface(world, blockToAir.north())) {
                        if (enumRailDirection != EnumRailDirection.ASCENDING_SOUTH || !World.doesBlockHaveSolidTopSurface(world, blockToAir.south())) {}
                    }
                }
            }
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    protected void onNeighborChangedInternal(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
    }
    
    public abstract IProperty getShapeProperty();
    
    protected IBlockState func_176564_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return world.isRemote ? blockState : new Rail(world, blockPos, blockState).func_180364_a(world.isBlockPowered(blockPos), b).getBlockState();
    }
    
    public static boolean isRailBlock(final World world, final BlockPos blockPos) {
        return isRailBlock(world.getBlockState(blockPos));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (((EnumRailDirection)blockState.getValue(this.getShapeProperty())).isAscending()) {
            world.notifyNeighborsOfStateChange(blockPos.up(), this);
        }
        if (this.isPowered) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
        }
    }
    
    protected BlockRailBase(final boolean isPowered) {
        super(Material.circuits);
        this.isPowered = isPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public static boolean isRailBlock(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return block == Blocks.rail || block == Blocks.golden_rail || block == Blocks.detector_rail || block == Blocks.activator_rail;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumRailDirection enumRailDirection = (blockState.getBlock() == this) ? ((EnumRailDirection)blockState.getValue(this.getShapeProperty())) : null;
        if (enumRailDirection != null && enumRailDirection.isAscending()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getMobilityFlag() {
        return 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState func_176564_a) {
        if (!world.isRemote) {
            func_176564_a = this.func_176564_a(world, blockPos, func_176564_a, true);
            if (this.isPowered) {
                this.onNeighborBlockChange(world, blockPos, func_176564_a, this);
            }
        }
    }
    
    public enum EnumRailDirection implements IStringSerializable
    {
        SOUTH_WEST("SOUTH_WEST", 7, 7, "south_west"), 
        NORTH_EAST("NORTH_EAST", 9, 9, "north_east");
        
        private static final EnumRailDirection[] $VALUES;
        
        NORTH_SOUTH("NORTH_SOUTH", 0, 0, "north_south"), 
        SOUTH_EAST("SOUTH_EAST", 6, 6, "south_east");
        
        private static final EnumRailDirection[] META_LOOKUP;
        
        ASCENDING_WEST("ASCENDING_WEST", 3, 3, "ascending_west"), 
        NORTH_WEST("NORTH_WEST", 8, 8, "north_west"), 
        ASCENDING_NORTH("ASCENDING_NORTH", 4, 4, "ascending_north"), 
        ASCENDING_EAST("ASCENDING_EAST", 2, 2, "ascending_east"), 
        EAST_WEST("EAST_WEST", 1, 1, "east_west");
        
        private final int meta;
        private final String name;
        
        ASCENDING_SOUTH("ASCENDING_SOUTH", 5, 5, "ascending_south");
        
        public static EnumRailDirection byMetadata(final int n) {
            if (0 >= EnumRailDirection.META_LOOKUP.length) {}
            return EnumRailDirection.META_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumRailDirection(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
        
        static {
            $VALUES = new EnumRailDirection[] { EnumRailDirection.NORTH_SOUTH, EnumRailDirection.EAST_WEST, EnumRailDirection.ASCENDING_EAST, EnumRailDirection.ASCENDING_WEST, EnumRailDirection.ASCENDING_NORTH, EnumRailDirection.ASCENDING_SOUTH, EnumRailDirection.SOUTH_EAST, EnumRailDirection.SOUTH_WEST, EnumRailDirection.NORTH_WEST, EnumRailDirection.NORTH_EAST };
            META_LOOKUP = new EnumRailDirection[values().length];
            final EnumRailDirection[] values = values();
            while (0 < values.length) {
                final EnumRailDirection enumRailDirection = values[0];
                EnumRailDirection.META_LOOKUP[enumRailDirection.getMetadata()] = enumRailDirection;
                int n = 0;
                ++n;
            }
        }
        
        public boolean isAscending() {
            return this == EnumRailDirection.ASCENDING_NORTH || this == EnumRailDirection.ASCENDING_EAST || this == EnumRailDirection.ASCENDING_SOUTH || this == EnumRailDirection.ASCENDING_WEST;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public class Rail
    {
        final BlockRailBase this$0;
        private final boolean isPowered;
        private IBlockState state;
        private final World world;
        private final BlockPos pos;
        private final BlockRailBase block;
        private final List field_150657_g;
        
        protected int countAdjacentRails() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
            //     6: astore_2       
            //     7: aload_2        
            //     8: invokeinterface java/util/Iterator.hasNext:()Z
            //    13: ifeq            48
            //    16: aload_2        
            //    17: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    22: astore_3       
            //    23: aload_3        
            //    24: checkcast       Lnet/minecraft/util/EnumFacing;
            //    27: astore          4
            //    29: aload_0        
            //    30: aload_0        
            //    31: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //    34: aload           4
            //    36: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
            //    39: ifne            45
            //    42: iinc            1, 1
            //    45: goto            7
            //    48: iconst_0       
            //    49: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0007 (coming from #0045).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        
        public Rail func_180364_a(final boolean p0, final boolean p1) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //     4: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
            //     7: astore_3       
            //     8: aload_0        
            //     9: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //    12: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
            //    15: astore          4
            //    17: aload_0        
            //    18: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //    21: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
            //    24: astore          5
            //    26: aload_0        
            //    27: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //    30: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
            //    33: astore          6
            //    35: aload_0        
            //    36: aload_3        
            //    37: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_180361_d:(Lnet/minecraft/util/BlockPos;)Z
            //    40: istore          7
            //    42: aload_0        
            //    43: aload           4
            //    45: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_180361_d:(Lnet/minecraft/util/BlockPos;)Z
            //    48: istore          8
            //    50: aload_0        
            //    51: aload           5
            //    53: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_180361_d:(Lnet/minecraft/util/BlockPos;)Z
            //    56: istore          9
            //    58: aload_0        
            //    59: aload           6
            //    61: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_180361_d:(Lnet/minecraft/util/BlockPos;)Z
            //    64: istore          10
            //    66: aconst_null    
            //    67: astore          11
            //    69: iload           7
            //    71: ifne            79
            //    74: iload           8
            //    76: ifeq            94
            //    79: iload           9
            //    81: ifne            94
            //    84: iload           10
            //    86: ifne            94
            //    89: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //    92: astore          11
            //    94: iload           9
            //    96: ifne            104
            //    99: iload           10
            //   101: ifeq            119
            //   104: iload           7
            //   106: ifne            119
            //   109: iload           8
            //   111: ifne            119
            //   114: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.EAST_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   117: astore          11
            //   119: aload_0        
            //   120: getfield        net/minecraft/block/BlockRailBase$Rail.isPowered:Z
            //   123: ifne            226
            //   126: iload           8
            //   128: ifeq            151
            //   131: iload           10
            //   133: ifeq            151
            //   136: iload           7
            //   138: ifne            151
            //   141: iload           9
            //   143: ifne            151
            //   146: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   149: astore          11
            //   151: iload           8
            //   153: ifeq            176
            //   156: iload           9
            //   158: ifeq            176
            //   161: iload           7
            //   163: ifne            176
            //   166: iload           10
            //   168: ifne            176
            //   171: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   174: astore          11
            //   176: iload           7
            //   178: ifeq            201
            //   181: iload           9
            //   183: ifeq            201
            //   186: iload           8
            //   188: ifne            201
            //   191: iload           10
            //   193: ifne            201
            //   196: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   199: astore          11
            //   201: iload           7
            //   203: ifeq            226
            //   206: iload           10
            //   208: ifeq            226
            //   211: iload           8
            //   213: ifne            226
            //   216: iload           9
            //   218: ifne            226
            //   221: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   224: astore          11
            //   226: aload           11
            //   228: ifnonnull       395
            //   231: iload           7
            //   233: ifne            241
            //   236: iload           8
            //   238: ifeq            246
            //   241: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   244: astore          11
            //   246: iload           9
            //   248: ifne            256
            //   251: iload           10
            //   253: ifeq            261
            //   256: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.EAST_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   259: astore          11
            //   261: aload_0        
            //   262: getfield        net/minecraft/block/BlockRailBase$Rail.isPowered:Z
            //   265: ifne            395
            //   268: iload_1        
            //   269: ifeq            335
            //   272: iload           8
            //   274: ifeq            287
            //   277: iload           10
            //   279: ifeq            287
            //   282: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   285: astore          11
            //   287: iload           9
            //   289: ifeq            302
            //   292: iload           8
            //   294: ifeq            302
            //   297: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   300: astore          11
            //   302: iload           10
            //   304: ifeq            317
            //   307: iload           7
            //   309: ifeq            317
            //   312: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   315: astore          11
            //   317: iload           7
            //   319: ifeq            395
            //   322: iload           9
            //   324: ifeq            395
            //   327: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   330: astore          11
            //   332: goto            395
            //   335: iload           7
            //   337: ifeq            350
            //   340: iload           9
            //   342: ifeq            350
            //   345: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   348: astore          11
            //   350: iload           10
            //   352: ifeq            365
            //   355: iload           7
            //   357: ifeq            365
            //   360: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   363: astore          11
            //   365: iload           9
            //   367: ifeq            380
            //   370: iload           8
            //   372: ifeq            380
            //   375: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   378: astore          11
            //   380: iload           8
            //   382: ifeq            395
            //   385: iload           10
            //   387: ifeq            395
            //   390: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.SOUTH_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   393: astore          11
            //   395: aload           11
            //   397: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   400: if_acmpne       442
            //   403: aload_0        
            //   404: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   407: aload_3        
            //   408: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
            //   411: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
            //   414: ifeq            422
            //   417: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.ASCENDING_NORTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   420: astore          11
            //   422: aload_0        
            //   423: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   426: aload           4
            //   428: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
            //   431: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
            //   434: ifeq            442
            //   437: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.ASCENDING_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   440: astore          11
            //   442: aload           11
            //   444: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.EAST_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   447: if_acmpne       490
            //   450: aload_0        
            //   451: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   454: aload           6
            //   456: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
            //   459: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
            //   462: ifeq            470
            //   465: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.ASCENDING_EAST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   468: astore          11
            //   470: aload_0        
            //   471: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   474: aload           5
            //   476: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
            //   479: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
            //   482: ifeq            490
            //   485: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.ASCENDING_WEST:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   488: astore          11
            //   490: aload           11
            //   492: ifnonnull       500
            //   495: getstatic       net/minecraft/block/BlockRailBase$EnumRailDirection.NORTH_SOUTH:Lnet/minecraft/block/BlockRailBase$EnumRailDirection;
            //   498: astore          11
            //   500: aload_0        
            //   501: aload           11
            //   503: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_180360_a:(Lnet/minecraft/block/BlockRailBase$EnumRailDirection;)V
            //   506: aload_0        
            //   507: aload_0        
            //   508: getfield        net/minecraft/block/BlockRailBase$Rail.state:Lnet/minecraft/block/state/IBlockState;
            //   511: aload_0        
            //   512: getfield        net/minecraft/block/BlockRailBase$Rail.block:Lnet/minecraft/block/BlockRailBase;
            //   515: invokevirtual   net/minecraft/block/BlockRailBase.getShapeProperty:()Lnet/minecraft/block/properties/IProperty;
            //   518: aload           11
            //   520: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
            //   525: putfield        net/minecraft/block/BlockRailBase$Rail.state:Lnet/minecraft/block/state/IBlockState;
            //   528: iload_2        
            //   529: ifne            550
            //   532: aload_0        
            //   533: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   536: aload_0        
            //   537: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //   540: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
            //   543: aload_0        
            //   544: getfield        net/minecraft/block/BlockRailBase$Rail.state:Lnet/minecraft/block/state/IBlockState;
            //   547: if_acmpeq       627
            //   550: aload_0        
            //   551: getfield        net/minecraft/block/BlockRailBase$Rail.world:Lnet/minecraft/world/World;
            //   554: aload_0        
            //   555: getfield        net/minecraft/block/BlockRailBase$Rail.pos:Lnet/minecraft/util/BlockPos;
            //   558: aload_0        
            //   559: getfield        net/minecraft/block/BlockRailBase$Rail.state:Lnet/minecraft/block/state/IBlockState;
            //   562: iconst_3       
            //   563: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
            //   566: pop            
            //   567: iconst_0       
            //   568: aload_0        
            //   569: getfield        net/minecraft/block/BlockRailBase$Rail.field_150657_g:Ljava/util/List;
            //   572: invokeinterface java/util/List.size:()I
            //   577: if_icmpge       627
            //   580: aload_0        
            //   581: aload_0        
            //   582: getfield        net/minecraft/block/BlockRailBase$Rail.field_150657_g:Ljava/util/List;
            //   585: iconst_0       
            //   586: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   591: checkcast       Lnet/minecraft/util/BlockPos;
            //   594: invokespecial   net/minecraft/block/BlockRailBase$Rail.findRailAt:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/BlockRailBase$Rail;
            //   597: astore          13
            //   599: aload           13
            //   601: ifnull          621
            //   604: aload           13
            //   606: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_150651_b:()V
            //   609: aload           13
            //   611: aload_0        
            //   612: ifne            621
            //   615: aload           13
            //   617: aload_0        
            //   618: invokespecial   net/minecraft/block/BlockRailBase$Rail.func_150645_c:(Lnet/minecraft/block/BlockRailBase$Rail;)V
            //   621: iinc            12, 1
            //   624: goto            567
            //   627: aload_0        
            //   628: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0621 (coming from #0612).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        
        private Rail findRailAt(final BlockPos blockPos) {
            final IBlockState blockState = this.world.getBlockState(blockPos);
            if (BlockRailBase.isRailBlock(blockState)) {
                return this.this$0.new Rail(this.world, blockPos, blockState);
            }
            final BlockPos up = blockPos.up();
            final IBlockState blockState2 = this.world.getBlockState(up);
            if (BlockRailBase.isRailBlock(blockState2)) {
                return this.this$0.new Rail(this.world, up, blockState2);
            }
            final BlockPos down = blockPos.down();
            final IBlockState blockState3 = this.world.getBlockState(down);
            return BlockRailBase.isRailBlock(blockState3) ? this.this$0.new Rail(this.world, down, blockState3) : null;
        }
        
        private void func_150651_b() {
            while (0 < this.field_150657_g.size()) {
                final Rail rail = this.findRailAt(this.field_150657_g.get(0));
                int n2 = 0;
                if (rail != null && rail.func_150653_a(this)) {
                    this.field_150657_g.set(0, rail.pos);
                }
                else {
                    final List field_150657_g = this.field_150657_g;
                    final int n = 0;
                    --n2;
                    field_150657_g.remove(n);
                }
                ++n2;
            }
        }
        
        private boolean func_180361_d(final BlockPos blockPos) {
            final Rail rail = this.findRailAt(blockPos);
            if (rail == null) {
                return false;
            }
            rail.func_150651_b();
            return rail.func_150649_b(this);
        }
        
        public Rail(final BlockRailBase this$0, final World world, final BlockPos pos, final IBlockState state) {
            this.this$0 = this$0;
            this.field_150657_g = Lists.newArrayList();
            this.world = world;
            this.pos = pos;
            this.state = state;
            this.block = (BlockRailBase)state.getBlock();
            final EnumRailDirection enumRailDirection = (EnumRailDirection)state.getValue(this$0.getShapeProperty());
            this.isPowered = this.block.isPowered;
            this.func_180360_a(enumRailDirection);
        }
        
        public IBlockState getBlockState() {
            return this.state;
        }
        
        private boolean func_180363_c(final BlockPos blockPos) {
            while (0 < this.field_150657_g.size()) {
                final BlockPos blockPos2 = this.field_150657_g.get(0);
                if (blockPos2.getX() == blockPos.getX() && blockPos2.getZ() == blockPos.getZ()) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        private void func_150645_c(final Rail rail) {
            this.field_150657_g.add(rail.pos);
            final BlockPos north = this.pos.north();
            final BlockPos south = this.pos.south();
            final BlockPos west = this.pos.west();
            final BlockPos east = this.pos.east();
            final boolean func_180363_c = this.func_180363_c(north);
            final boolean func_180363_c2 = this.func_180363_c(south);
            final boolean func_180363_c3 = this.func_180363_c(west);
            final boolean func_180363_c4 = this.func_180363_c(east);
            EnumRailDirection enumRailDirection = null;
            if (func_180363_c || func_180363_c2) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            if (func_180363_c3 || func_180363_c4) {
                enumRailDirection = EnumRailDirection.EAST_WEST;
            }
            if (!this.isPowered) {
                if (func_180363_c2 && func_180363_c4 && !func_180363_c && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.SOUTH_EAST;
                }
                if (func_180363_c2 && func_180363_c3 && !func_180363_c && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.SOUTH_WEST;
                }
                if (func_180363_c && func_180363_c3 && !func_180363_c2 && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.NORTH_WEST;
                }
                if (func_180363_c && func_180363_c4 && !func_180363_c2 && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.NORTH_EAST;
                }
            }
            if (enumRailDirection == EnumRailDirection.NORTH_SOUTH) {
                if (BlockRailBase.isRailBlock(this.world, north.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_NORTH;
                }
                if (BlockRailBase.isRailBlock(this.world, south.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_SOUTH;
                }
            }
            if (enumRailDirection == EnumRailDirection.EAST_WEST) {
                if (BlockRailBase.isRailBlock(this.world, east.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_EAST;
                }
                if (BlockRailBase.isRailBlock(this.world, west.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_WEST;
                }
            }
            if (enumRailDirection == null) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            this.state = this.state.withProperty(this.block.getShapeProperty(), enumRailDirection);
            this.world.setBlockState(this.pos, this.state, 3);
        }
        
        private boolean func_150653_a(final Rail rail) {
            return this.func_180363_c(rail.pos);
        }
        
        private void func_180360_a(final EnumRailDirection enumRailDirection) {
            this.field_150657_g.clear();
            switch (BlockRailBase$1.$SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[enumRailDirection.ordinal()]) {
                case 1: {
                    this.field_150657_g.add(this.pos.north());
                    this.field_150657_g.add(this.pos.south());
                    break;
                }
                case 2: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.east());
                    break;
                }
                case 3: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.east().up());
                    break;
                }
                case 4: {
                    this.field_150657_g.add(this.pos.west().up());
                    this.field_150657_g.add(this.pos.east());
                    break;
                }
                case 5: {
                    this.field_150657_g.add(this.pos.north().up());
                    this.field_150657_g.add(this.pos.south());
                    break;
                }
                case 6: {
                    this.field_150657_g.add(this.pos.north());
                    this.field_150657_g.add(this.pos.south().up());
                    break;
                }
                case 7: {
                    this.field_150657_g.add(this.pos.east());
                    this.field_150657_g.add(this.pos.south());
                    break;
                }
                case 8: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.south());
                    break;
                }
                case 9: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.north());
                    break;
                }
                case 10: {
                    this.field_150657_g.add(this.pos.east());
                    this.field_150657_g.add(this.pos.north());
                    break;
                }
            }
        }
    }
}
