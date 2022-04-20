package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class BlockPistonBase extends Block
{
    public static final PropertyBool EXTENDED;
    private final boolean isSticky;
    public static final PropertyDirection FACING;
    
    public static EnumFacing getFacing(final int n) {
        final int n2 = n & 0x7;
        return (n2 > 5) ? null : EnumFacing.getFront(n2);
    }
    
    private void checkForMove(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonBase.FACING);
        final boolean shouldBeExtended = this.shouldBeExtended(world, blockPos, enumFacing);
        if (shouldBeExtended && !(boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            if (new BlockPistonStructureHelper(world, blockPos, enumFacing, true).canMove()) {
                world.addBlockEvent(blockPos, this, 0, enumFacing.getIndex());
            }
        }
        else if (!shouldBeExtended && (boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.EXTENDED, false), 2);
            world.addBlockEvent(blockPos, this, 1, enumFacing.getIndex());
        }
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, EnumFacing.UP);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.FACING, getFacingFromEntity(world, blockPos, entityLivingBase)), 2);
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    public static EnumFacing getFacingFromEntity(final World world, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (MathHelper.abs((float)entityLivingBase.posX - blockPos.getX()) < 2.0f && MathHelper.abs((float)entityLivingBase.posZ - blockPos.getZ()) < 2.0f) {
            final double n = entityLivingBase.posY + entityLivingBase.getEyeHeight();
            if (n - blockPos.getY() > 2.0) {
                return EnumFacing.UP;
            }
            if (blockPos.getY() - n > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        return entityLivingBase.getHorizontalFacing().getOpposite();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this && (boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonBase.FACING);
            if (enumFacing != null) {
                switch (BlockPistonBase$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                    case 1: {
                        this.setBlockBounds(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 2: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                        break;
                    }
                    case 3: {
                        this.setBlockBounds(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 4: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                        break;
                    }
                    case 5: {
                        this.setBlockBounds(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 6: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                        break;
                    }
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public BlockPistonBase(final boolean isSticky) {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonBase.FACING, EnumFacing.NORTH).withProperty(BlockPistonBase.EXTENDED, false));
        this.isSticky = isSticky;
        this.setStepSound(BlockPistonBase.soundTypePiston);
        this.setHardness(0.5f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    static {
        FACING = PropertyDirection.create("facing");
        EXTENDED = PropertyBool.create("extended");
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonBase.FACING, BlockPistonBase.EXTENDED });
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, getFacingFromEntity(world, blockPos, entityLivingBase)).withProperty(BlockPistonBase.EXTENDED, false);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, getFacing(n)).withProperty(BlockPistonBase.EXTENDED, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonBase.FACING)).getIndex();
        if (blockState.getValue(BlockPistonBase.EXTENDED)) {}
        return 0;
    }
    
    private boolean shouldBeExtended(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing2 = values[0];
            if (enumFacing2 != enumFacing && world.isSidePowered(blockPos.offset(enumFacing2), enumFacing2)) {
                return true;
            }
            int length = 0;
            ++length;
        }
        if (world.isSidePowered(blockPos, EnumFacing.DOWN)) {
            return true;
        }
        blockPos.up();
        int length = EnumFacing.values().length;
        return false;
    }
    
    @Override
    public boolean onBlockEventReceived(final World p0, final BlockPos p1, final IBlockState p2, final int p3, final int p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/block/BlockPistonBase.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //     4: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //     9: checkcast       Lnet/minecraft/util/EnumFacing;
        //    12: astore          6
        //    14: aload_1        
        //    15: getfield        net/minecraft/world/World.isRemote:Z
        //    18: ifne            76
        //    21: aload_0        
        //    22: aload_1        
        //    23: aload_2        
        //    24: aload           6
        //    26: invokespecial   net/minecraft/block/BlockPistonBase.shouldBeExtended:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //    29: istore          7
        //    31: iload           7
        //    33: ifeq            64
        //    36: iload           4
        //    38: iconst_1       
        //    39: if_icmpne       64
        //    42: aload_1        
        //    43: aload_2        
        //    44: aload_3        
        //    45: getstatic       net/minecraft/block/BlockPistonBase.EXTENDED:Lnet/minecraft/block/properties/PropertyBool;
        //    48: iconst_1       
        //    49: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    52: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    57: iconst_2       
        //    58: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    61: pop            
        //    62: iconst_0       
        //    63: ireturn        
        //    64: iload           7
        //    66: ifne            76
        //    69: iload           4
        //    71: ifne            76
        //    74: iconst_0       
        //    75: ireturn        
        //    76: iload           4
        //    78: ifne            159
        //    81: aload_0        
        //    82: aload_1        
        //    83: aload_2        
        //    84: aload           6
        //    86: aload_1        
        //    87: aload_2        
        //    88: aload_3        
        //    89: getstatic       net/minecraft/block/BlockPistonBase.EXTENDED:Lnet/minecraft/block/properties/PropertyBool;
        //    92: iconst_1       
        //    93: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    96: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   101: iconst_2       
        //   102: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   105: pop            
        //   106: aload_1        
        //   107: aload_2        
        //   108: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   111: i2d            
        //   112: ldc2_w          0.5
        //   115: dadd           
        //   116: aload_2        
        //   117: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   120: i2d            
        //   121: ldc2_w          0.5
        //   124: dadd           
        //   125: aload_2        
        //   126: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   129: i2d            
        //   130: ldc2_w          0.5
        //   133: dadd           
        //   134: ldc_w           "tile.piston.out"
        //   137: ldc             0.5
        //   139: aload_1        
        //   140: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   143: invokevirtual   java/util/Random.nextFloat:()F
        //   146: ldc             0.25
        //   148: fmul           
        //   149: ldc_w           0.6
        //   152: fadd           
        //   153: invokevirtual   net/minecraft/world/World.playSoundEffect:(DDDLjava/lang/String;FF)V
        //   156: goto            486
        //   159: iload           4
        //   161: iconst_1       
        //   162: if_icmpne       486
        //   165: aload_1        
        //   166: aload_2        
        //   167: aload           6
        //   169: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   172: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //   175: astore          7
        //   177: aload           7
        //   179: instanceof      Lnet/minecraft/tileentity/TileEntityPiston;
        //   182: ifeq            193
        //   185: aload           7
        //   187: checkcast       Lnet/minecraft/tileentity/TileEntityPiston;
        //   190: invokevirtual   net/minecraft/tileentity/TileEntityPiston.clearPistonTileEntity:()V
        //   193: aload_1        
        //   194: aload_2        
        //   195: getstatic       net/minecraft/init/Blocks.piston_extension:Lnet/minecraft/block/BlockPistonMoving;
        //   198: invokevirtual   net/minecraft/block/BlockPistonMoving.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   201: getstatic       net/minecraft/block/BlockPistonMoving.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //   204: aload           6
        //   206: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   211: getstatic       net/minecraft/block/BlockPistonMoving.TYPE:Lnet/minecraft/block/properties/PropertyEnum;
        //   214: aload_0        
        //   215: getfield        net/minecraft/block/BlockPistonBase.isSticky:Z
        //   218: ifeq            227
        //   221: getstatic       net/minecraft/block/BlockPistonExtension$EnumPistonType.STICKY:Lnet/minecraft/block/BlockPistonExtension$EnumPistonType;
        //   224: goto            230
        //   227: getstatic       net/minecraft/block/BlockPistonExtension$EnumPistonType.DEFAULT:Lnet/minecraft/block/BlockPistonExtension$EnumPistonType;
        //   230: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   235: iconst_3       
        //   236: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   239: pop            
        //   240: aload_1        
        //   241: aload_2        
        //   242: aload_0        
        //   243: iload           5
        //   245: invokevirtual   net/minecraft/block/BlockPistonBase.getStateFromMeta:(I)Lnet/minecraft/block/state/IBlockState;
        //   248: aload           6
        //   250: iconst_0       
        //   251: iconst_1       
        //   252: invokestatic    net/minecraft/block/BlockPistonMoving.newTileEntity:(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/EnumFacing;ZZ)Lnet/minecraft/tileentity/TileEntity;
        //   255: invokevirtual   net/minecraft/world/World.setTileEntity:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/tileentity/TileEntity;)V
        //   258: aload_0        
        //   259: getfield        net/minecraft/block/BlockPistonBase.isSticky:Z
        //   262: ifeq            424
        //   265: aload_2        
        //   266: aload           6
        //   268: invokevirtual   net/minecraft/util/EnumFacing.getFrontOffsetX:()I
        //   271: iconst_2       
        //   272: imul           
        //   273: aload           6
        //   275: invokevirtual   net/minecraft/util/EnumFacing.getFrontOffsetY:()I
        //   278: iconst_2       
        //   279: imul           
        //   280: aload           6
        //   282: invokevirtual   net/minecraft/util/EnumFacing.getFrontOffsetZ:()I
        //   285: iconst_2       
        //   286: imul           
        //   287: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   290: astore          8
        //   292: aload_1        
        //   293: aload           8
        //   295: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   298: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   303: astore          9
        //   305: aload           9
        //   307: getstatic       net/minecraft/init/Blocks.piston_extension:Lnet/minecraft/block/BlockPistonMoving;
        //   310: if_acmpne       359
        //   313: aload_1        
        //   314: aload           8
        //   316: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //   319: astore          11
        //   321: aload           11
        //   323: instanceof      Lnet/minecraft/tileentity/TileEntityPiston;
        //   326: ifeq            359
        //   329: aload           11
        //   331: checkcast       Lnet/minecraft/tileentity/TileEntityPiston;
        //   334: astore          12
        //   336: aload           12
        //   338: invokevirtual   net/minecraft/tileentity/TileEntityPiston.getFacing:()Lnet/minecraft/util/EnumFacing;
        //   341: aload           6
        //   343: if_acmpne       359
        //   346: aload           12
        //   348: invokevirtual   net/minecraft/tileentity/TileEntityPiston.isExtending:()Z
        //   351: ifeq            359
        //   354: aload           12
        //   356: invokevirtual   net/minecraft/tileentity/TileEntityPiston.clearPistonTileEntity:()V
        //   359: goto            435
        //   362: aload           9
        //   364: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   367: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   370: if_acmpeq       421
        //   373: aload           9
        //   375: aload_1        
        //   376: aload           8
        //   378: aload           6
        //   380: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   383: iconst_0       
        //   384: if_acmpne       421
        //   387: aload           9
        //   389: invokevirtual   net/minecraft/block/Block.getMobilityFlag:()I
        //   392: ifeq            411
        //   395: aload           9
        //   397: getstatic       net/minecraft/init/Blocks.piston:Lnet/minecraft/block/BlockPistonBase;
        //   400: if_acmpeq       411
        //   403: aload           9
        //   405: getstatic       net/minecraft/init/Blocks.sticky_piston:Lnet/minecraft/block/BlockPistonBase;
        //   408: if_acmpne       421
        //   411: aload_0        
        //   412: aload_1        
        //   413: aload_2        
        //   414: aload           6
        //   416: iconst_0       
        //   417: invokespecial   net/minecraft/block/BlockPistonBase.doMove:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Z)Z
        //   420: pop            
        //   421: goto            435
        //   424: aload_1        
        //   425: aload_2        
        //   426: aload           6
        //   428: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   431: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //   434: pop            
        //   435: aload_1        
        //   436: aload_2        
        //   437: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   440: i2d            
        //   441: ldc2_w          0.5
        //   444: dadd           
        //   445: aload_2        
        //   446: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   449: i2d            
        //   450: ldc2_w          0.5
        //   453: dadd           
        //   454: aload_2        
        //   455: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   458: i2d            
        //   459: ldc2_w          0.5
        //   462: dadd           
        //   463: ldc_w           "tile.piston.in"
        //   466: ldc             0.5
        //   468: aload_1        
        //   469: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   472: invokevirtual   java/util/Random.nextFloat:()F
        //   475: ldc_w           0.15
        //   478: fmul           
        //   479: ldc_w           0.6
        //   482: fadd           
        //   483: invokevirtual   net/minecraft/world/World.playSoundEffect:(DDDLjava/lang/String;FF)V
        //   486: iconst_1       
        //   487: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0486 (coming from #0156).
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
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
}
