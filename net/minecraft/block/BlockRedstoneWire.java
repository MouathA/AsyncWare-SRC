package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public class BlockRedstoneWire extends Block
{
    public static final PropertyEnum EAST;
    public static final PropertyInteger POWER;
    public static final PropertyEnum SOUTH;
    private final Set blocksNeedingUpdate;
    public static final PropertyEnum NORTH;
    private boolean canProvidePower;
    public static final PropertyEnum WEST;
    
    private IBlockState calculateCurrentChanges(final World p0, final BlockPos p1, final BlockPos p2, final IBlockState p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: astore          5
        //     4: aload           4
        //     6: getstatic       net/minecraft/block/BlockRedstoneWire.POWER:Lnet/minecraft/block/properties/PropertyInteger;
        //     9: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    14: checkcast       Ljava/lang/Integer;
        //    17: invokevirtual   java/lang/Integer.intValue:()I
        //    20: istore          6
        //    22: aload_0        
        //    23: aload_1        
        //    24: aload_3        
        //    25: iconst_0       
        //    26: invokespecial   net/minecraft/block/BlockRedstoneWire.getMaxCurrentStrength:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;I)I
        //    29: istore          7
        //    31: aload_0        
        //    32: iconst_0       
        //    33: putfield        net/minecraft/block/BlockRedstoneWire.canProvidePower:Z
        //    36: aload_1        
        //    37: aload_2        
        //    38: invokevirtual   net/minecraft/world/World.isBlockIndirectlyGettingPowered:(Lnet/minecraft/util/BlockPos;)I
        //    41: istore          8
        //    43: aload_0        
        //    44: iconst_1       
        //    45: putfield        net/minecraft/block/BlockRedstoneWire.canProvidePower:Z
        //    48: iload           8
        //    50: ifle            63
        //    53: iload           8
        //    55: iconst_m1      
        //    56: if_icmple       63
        //    59: iload           8
        //    61: istore          7
        //    63: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //    66: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //    69: astore          10
        //    71: aload           10
        //    73: invokeinterface java/util/Iterator.hasNext:()Z
        //    78: ifeq            268
        //    81: aload           10
        //    83: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    88: astore          11
        //    90: aload           11
        //    92: checkcast       Lnet/minecraft/util/EnumFacing;
        //    95: astore          12
        //    97: aload_2        
        //    98: aload           12
        //   100: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   103: astore          13
        //   105: aload           13
        //   107: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   110: aload_3        
        //   111: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   114: if_icmpne       129
        //   117: aload           13
        //   119: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   122: aload_3        
        //   123: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   126: if_icmpeq       133
        //   129: iconst_1       
        //   130: goto            134
        //   133: iconst_0       
        //   134: istore          14
        //   136: iload           14
        //   138: ifeq            151
        //   141: aload_0        
        //   142: aload_1        
        //   143: aload           13
        //   145: iconst_0       
        //   146: invokespecial   net/minecraft/block/BlockRedstoneWire.getMaxCurrentStrength:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;I)I
        //   149: istore          9
        //   151: aload_1        
        //   152: aload           13
        //   154: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   157: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   162: invokevirtual   net/minecraft/block/Block.isNormalCube:()Z
        //   165: ifeq            219
        //   168: aload_1        
        //   169: aload_2        
        //   170: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   173: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   176: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   181: invokevirtual   net/minecraft/block/Block.isNormalCube:()Z
        //   184: ifne            219
        //   187: iload           14
        //   189: ifeq            265
        //   192: aload_2        
        //   193: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   196: aload_3        
        //   197: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   200: if_icmplt       265
        //   203: aload_0        
        //   204: aload_1        
        //   205: aload           13
        //   207: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   210: iconst_0       
        //   211: invokespecial   net/minecraft/block/BlockRedstoneWire.getMaxCurrentStrength:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;I)I
        //   214: istore          9
        //   216: goto            71
        //   219: aload_1        
        //   220: aload           13
        //   222: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   225: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   230: invokevirtual   net/minecraft/block/Block.isNormalCube:()Z
        //   233: ifne            265
        //   236: iload           14
        //   238: ifeq            265
        //   241: aload_2        
        //   242: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   245: aload_3        
        //   246: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   249: if_icmpgt       265
        //   252: aload_0        
        //   253: aload_1        
        //   254: aload           13
        //   256: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   259: iconst_0       
        //   260: invokespecial   net/minecraft/block/BlockRedstoneWire.getMaxCurrentStrength:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;I)I
        //   263: istore          9
        //   265: goto            71
        //   268: goto            283
        //   271: goto            283
        //   274: goto            283
        //   277: iinc            7, -1
        //   280: goto            283
        //   283: iload           8
        //   285: iconst_m1      
        //   286: if_icmple       293
        //   289: iload           8
        //   291: istore          7
        //   293: iload           6
        //   295: iconst_0       
        //   296: if_icmpeq       389
        //   299: aload           4
        //   301: getstatic       net/minecraft/block/BlockRedstoneWire.POWER:Lnet/minecraft/block/properties/PropertyInteger;
        //   304: iconst_0       
        //   305: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   308: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   313: astore          4
        //   315: aload_1        
        //   316: aload_2        
        //   317: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   320: aload           5
        //   322: if_acmpne       334
        //   325: aload_1        
        //   326: aload_2        
        //   327: aload           4
        //   329: iconst_2       
        //   330: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   333: pop            
        //   334: aload_0        
        //   335: getfield        net/minecraft/block/BlockRedstoneWire.blocksNeedingUpdate:Ljava/util/Set;
        //   338: aload_2        
        //   339: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   344: pop            
        //   345: invokestatic    net/minecraft/util/EnumFacing.values:()[Lnet/minecraft/util/EnumFacing;
        //   348: astore          10
        //   350: aload           10
        //   352: arraylength    
        //   353: istore          11
        //   355: iconst_0       
        //   356: iload           11
        //   358: if_icmpge       389
        //   361: aload           10
        //   363: iconst_0       
        //   364: aaload         
        //   365: astore          13
        //   367: aload_0        
        //   368: getfield        net/minecraft/block/BlockRedstoneWire.blocksNeedingUpdate:Ljava/util/Set;
        //   371: aload_2        
        //   372: aload           13
        //   374: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   377: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   382: pop            
        //   383: iinc            12, 1
        //   386: goto            355
        //   389: aload           4
        //   391: areturn        
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
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty(BlockRedstoneWire.WEST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.WEST));
        blockState = blockState.withProperty(BlockRedstoneWire.EAST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.EAST));
        blockState = blockState.withProperty(BlockRedstoneWire.NORTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.NORTH));
        blockState = blockState.withProperty(BlockRedstoneWire.SOUTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.SOUTH));
        return blockState;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneWire.POWER, n);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.redstone;
    }
    
    protected static boolean canConnectUpwardsTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return canConnectUpwardsTo(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockRedstoneWire.POWER);
    }
    
    static {
        NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
        EAST = PropertyEnum.create("east", EnumAttachPosition.class);
        SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
        WEST = PropertyEnum.create("west", EnumAttachPosition.class);
        POWER = PropertyInteger.create("power", 0, 15);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    private int getMaxCurrentStrength(final World world, final BlockPos blockPos, final int n) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return n;
        }
        final int intValue = (int)world.getBlockState(blockPos).getValue(BlockRedstoneWire.POWER);
        return (intValue > n) ? intValue : n;
    }
    
    private EnumAttachPosition getAttachPosition(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
        if (blockAccess.getBlockState(offset) == enumFacing && (block.isBlockNormalCube() || !canConnectUpwardsTo(blockAccess.getBlockState(offset.down())))) {
            return (!blockAccess.getBlockState(blockPos.up()).getBlock().isBlockNormalCube() && block.isBlockNormalCube() && canConnectUpwardsTo(blockAccess.getBlockState(offset.up()))) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
        }
        return EnumAttachPosition.SIDE;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    private IBlockState updateSurroundingRedstone(final World world, final BlockPos blockPos, IBlockState calculateCurrentChanges) {
        calculateCurrentChanges = this.calculateCurrentChanges(world, blockPos, blockPos, calculateCurrentChanges);
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();
        final Iterator<BlockPos> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            world.notifyNeighborsOfStateChange(iterator.next(), this);
        }
        return calculateCurrentChanges;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.VERTICAL.iterator();
            while (iterator.hasNext()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(iterator.next()), this);
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator2.hasNext()) {
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(iterator2.next()));
            }
            final Iterator iterator3 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator3.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator3.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, offset.up());
                }
                else {
                    this.notifyWireNeighborsOfStateChange(world, offset.down());
                }
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.isRemote:Z
        //     4: ifne            38
        //     7: aload_0        
        //     8: aload_1        
        //     9: aload_2        
        //    10: ifne            24
        //    13: aload_0        
        //    14: aload_1        
        //    15: aload_2        
        //    16: aload_3        
        //    17: invokespecial   net/minecraft/block/BlockRedstoneWire.updateSurroundingRedstone:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;
        //    20: pop            
        //    21: goto            38
        //    24: aload_0        
        //    25: aload_1        
        //    26: aload_2        
        //    27: aload_3        
        //    28: iconst_0       
        //    29: invokevirtual   net/minecraft/block/BlockRedstoneWire.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    32: aload_1        
        //    33: aload_2        
        //    34: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    37: pop            
        //    38: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0038 (coming from #0037).
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
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (!world.isRemote) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(iterator.next()));
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator2.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator2.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, offset.up());
                }
                else {
                    this.notifyWireNeighborsOfStateChange(world, offset.down());
                }
            }
        }
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        return (blockState.getBlock() != this) ? super.colorMultiplier(blockAccess, blockPos, n) : this.colorMultiplier((int)blockState.getValue(BlockRedstoneWire.POWER));
    }
    
    @Override
    public int getWeakPower(final IBlockAccess p0, final BlockPos p1, final IBlockState p2, final EnumFacing p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/block/BlockRedstoneWire.canProvidePower:Z
        //     4: ifne            9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: aload_3        
        //    10: getstatic       net/minecraft/block/BlockRedstoneWire.POWER:Lnet/minecraft/block/properties/PropertyInteger;
        //    13: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    18: checkcast       Ljava/lang/Integer;
        //    21: invokevirtual   java/lang/Integer.intValue:()I
        //    24: istore          5
        //    26: iload           5
        //    28: ifne            33
        //    31: iconst_0       
        //    32: ireturn        
        //    33: aload           4
        //    35: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //    38: if_acmpne       44
        //    41: iload           5
        //    43: ireturn        
        //    44: ldc             Lnet/minecraft/util/EnumFacing;.class
        //    46: invokestatic    java/util/EnumSet.noneOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //    49: astore          6
        //    51: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //    54: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //    57: astore          7
        //    59: aload           7
        //    61: invokeinterface java/util/Iterator.hasNext:()Z
        //    66: ifeq            104
        //    69: aload           7
        //    71: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    76: astore          8
        //    78: aload           8
        //    80: checkcast       Lnet/minecraft/util/EnumFacing;
        //    83: astore          9
        //    85: aload_0        
        //    86: aload_1        
        //    87: aload_2        
        //    88: aload           9
        //    90: ifne            101
        //    93: aload           6
        //    95: aload           9
        //    97: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //   100: pop            
        //   101: goto            59
        //   104: aload           4
        //   106: invokevirtual   net/minecraft/util/EnumFacing.getAxis:()Lnet/minecraft/util/EnumFacing$Axis;
        //   109: invokevirtual   net/minecraft/util/EnumFacing$Axis.isHorizontal:()Z
        //   112: ifeq            126
        //   115: aload           6
        //   117: invokevirtual   java/util/EnumSet.isEmpty:()Z
        //   120: ifeq            126
        //   123: iload           5
        //   125: ireturn        
        //   126: aload           6
        //   128: aload           4
        //   130: invokevirtual   java/util/EnumSet.contains:(Ljava/lang/Object;)Z
        //   133: ifeq            165
        //   136: aload           6
        //   138: aload           4
        //   140: invokevirtual   net/minecraft/util/EnumFacing.rotateYCCW:()Lnet/minecraft/util/EnumFacing;
        //   143: invokevirtual   java/util/EnumSet.contains:(Ljava/lang/Object;)Z
        //   146: ifne            165
        //   149: aload           6
        //   151: aload           4
        //   153: invokevirtual   net/minecraft/util/EnumFacing.rotateY:()Lnet/minecraft/util/EnumFacing;
        //   156: invokevirtual   java/util/EnumSet.contains:(Ljava/lang/Object;)Z
        //   159: ifne            165
        //   162: iload           5
        //   164: ireturn        
        //   165: iconst_0       
        //   166: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0059 (coming from #0101).
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
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final int intValue = (int)blockState.getValue(BlockRedstoneWire.POWER);
        if (intValue != 0) {
            final double n = blockPos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final double n2 = blockPos.getY() + 0.0625f;
            final double n3 = blockPos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final float n4 = intValue / 15.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, n4 * 0.6f + 0.4f, Math.max(0.0f, n4 * n4 * 0.7f - 0.5f), Math.max(0.0f, n4 * n4 * 0.6f - 0.7f), new int[0]);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneWire.NORTH, BlockRedstoneWire.EAST, BlockRedstoneWire.SOUTH, BlockRedstoneWire.WEST, BlockRedstoneWire.POWER });
    }
    
    protected static boolean canConnectUpwardsTo(final IBlockState blockState) {
        return canConnectTo(blockState, (EnumFacing)null);
    }
    
    public BlockRedstoneWire() {
        super(Material.circuits);
        this.canProvidePower = true;
        this.blocksNeedingUpdate = Sets.newHashSet();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneWire.NORTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.EAST, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.SOUTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.WEST, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.POWER, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    @Override
    public boolean canProvidePower() {
        return this.canProvidePower;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.canProvidePower ? this.getWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    private int colorMultiplier(final int n) {
        final float n2 = n / 15.0f;
        float n3 = n2 * 0.6f + 0.4f;
        if (n == 0) {
            n3 = 0.3f;
        }
        float n4 = n2 * n2 * 0.7f - 0.5f;
        float n5 = n2 * n2 * 0.6f - 0.7f;
        if (n4 < 0.0f) {
            n4 = 0.0f;
        }
        if (n5 < 0.0f) {
            n5 = 0.0f;
        }
        return 0xFF000000 | MathHelper.clamp_int((int)(n3 * 255.0f), 0, 255) << 16 | MathHelper.clamp_int((int)(n4 * 255.0f), 0, 255) << 8 | MathHelper.clamp_int((int)(n5 * 255.0f), 0, 255);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    private void notifyWireNeighborsOfStateChange(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() == this) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    enum EnumAttachPosition implements IStringSerializable
    {
        UP("UP", 0, "up");
        
        private final String name;
        
        NONE("NONE", 2, "none"), 
        SIDE("SIDE", 1, "side");
        
        private static final EnumAttachPosition[] $VALUES;
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumAttachPosition(final String s, final int n, final String name) {
            this.name = name;
        }
        
        static {
            $VALUES = new EnumAttachPosition[] { EnumAttachPosition.UP, EnumAttachPosition.SIDE, EnumAttachPosition.NONE };
        }
    }
}
