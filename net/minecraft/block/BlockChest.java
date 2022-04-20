package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockChest extends BlockContainer
{
    public static final PropertyDirection FACING;
    public final int chestType;
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityChest();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockChest.FACING, entityLivingBase.getHorizontalFacing());
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP) ? this.getWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            tileEntity.updateContainingBlockInfo();
        }
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    public IBlockState checkForSurroundingChests(final World world, final BlockPos blockPos, IBlockState withProperty) {
        if (world.isRemote) {
            return withProperty;
        }
        final IBlockState blockState = world.getBlockState(blockPos.north());
        final IBlockState blockState2 = world.getBlockState(blockPos.south());
        final IBlockState blockState3 = world.getBlockState(blockPos.west());
        final IBlockState blockState4 = world.getBlockState(blockPos.east());
        EnumFacing enumFacing = (EnumFacing)withProperty.getValue(BlockChest.FACING);
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        final Block block3 = blockState3.getBlock();
        final Block block4 = blockState4.getBlock();
        if (block != this && block2 != this) {
            final boolean fullBlock = block.isFullBlock();
            final boolean fullBlock2 = block2.isFullBlock();
            if (block3 == this || block4 == this) {
                final BlockPos blockPos2 = (block3 == this) ? blockPos.west() : blockPos.east();
                final IBlockState blockState5 = world.getBlockState(blockPos2.north());
                final IBlockState blockState6 = world.getBlockState(blockPos2.south());
                enumFacing = EnumFacing.SOUTH;
                EnumFacing enumFacing2;
                if (block3 == this) {
                    enumFacing2 = (EnumFacing)blockState3.getValue(BlockChest.FACING);
                }
                else {
                    enumFacing2 = (EnumFacing)blockState4.getValue(BlockChest.FACING);
                }
                if (enumFacing2 == EnumFacing.NORTH) {
                    enumFacing = EnumFacing.NORTH;
                }
                final Block block5 = blockState5.getBlock();
                final Block block6 = blockState6.getBlock();
                if ((fullBlock || block5.isFullBlock()) && !fullBlock2 && !block6.isFullBlock()) {
                    enumFacing = EnumFacing.SOUTH;
                }
                if ((fullBlock2 || block6.isFullBlock()) && !fullBlock && !block5.isFullBlock()) {
                    enumFacing = EnumFacing.NORTH;
                }
            }
        }
        else {
            final BlockPos blockPos3 = (block == this) ? blockPos.north() : blockPos.south();
            final IBlockState blockState7 = world.getBlockState(blockPos3.west());
            final IBlockState blockState8 = world.getBlockState(blockPos3.east());
            enumFacing = EnumFacing.EAST;
            EnumFacing enumFacing3;
            if (block == this) {
                enumFacing3 = (EnumFacing)blockState.getValue(BlockChest.FACING);
            }
            else {
                enumFacing3 = (EnumFacing)blockState2.getValue(BlockChest.FACING);
            }
            if (enumFacing3 == EnumFacing.WEST) {
                enumFacing = EnumFacing.WEST;
            }
            final Block block7 = blockState7.getBlock();
            final Block block8 = blockState8.getBlock();
            if ((block3.isFullBlock() || block7.isFullBlock()) && !block4.isFullBlock() && !block8.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
            }
            if ((block4.isFullBlock() || block8.isFullBlock()) && !block3.isFullBlock() && !block7.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
        }
        withProperty = withProperty.withProperty(BlockChest.FACING, enumFacing);
        world.setBlockState(blockPos, withProperty, 3);
        return withProperty;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //     4: astore          4
        //     6: aload_2        
        //     7: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //    10: astore          5
        //    12: aload_2        
        //    13: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //    16: astore          6
        //    18: aload_2        
        //    19: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //    22: astore          7
        //    24: aload_1        
        //    25: aload           4
        //    27: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    30: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    35: aload_0        
        //    36: if_acmpne       51
        //    39: aload_0        
        //    40: aload_1        
        //    41: aload           4
        //    43: if_acmpeq       48
        //    46: iconst_0       
        //    47: ireturn        
        //    48: iinc            3, 1
        //    51: aload_1        
        //    52: aload           5
        //    54: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    57: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    62: aload_0        
        //    63: if_acmpne       78
        //    66: aload_0        
        //    67: aload_1        
        //    68: aload           5
        //    70: if_acmpeq       75
        //    73: iconst_0       
        //    74: ireturn        
        //    75: iinc            3, 1
        //    78: aload_1        
        //    79: aload           6
        //    81: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    84: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    89: aload_0        
        //    90: if_acmpne       105
        //    93: aload_0        
        //    94: aload_1        
        //    95: aload           6
        //    97: if_acmpeq       102
        //   100: iconst_0       
        //   101: ireturn        
        //   102: iinc            3, 1
        //   105: aload_1        
        //   106: aload           7
        //   108: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   111: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   116: aload_0        
        //   117: if_acmpne       132
        //   120: aload_0        
        //   121: aload_1        
        //   122: aload           7
        //   124: if_acmpeq       129
        //   127: iconst_0       
        //   128: ireturn        
        //   129: iinc            3, 1
        //   132: iconst_1       
        //   133: goto            137
        //   136: iconst_0       
        //   137: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0132 (coming from #0129).
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
    public int getRenderType() {
        return 2;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(this.getLockableContainer(world, blockPos));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockChest.FACING });
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockChest.FACING)).getIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockChest.FACING, enumFacing);
    }
    
    public IBlockState correctFacing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        EnumFacing enumFacing = null;
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            final IBlockState blockState2 = world.getBlockState(blockPos.offset(enumFacing2));
            if (blockState2.getBlock() == this) {
                return blockState;
            }
            if (!blockState2.getBlock().isFullBlock()) {
                continue;
            }
            if (enumFacing != null) {
                enumFacing = null;
                break;
            }
            enumFacing = enumFacing2;
        }
        if (enumFacing != null) {
            return blockState.withProperty(BlockChest.FACING, enumFacing.getOpposite());
        }
        EnumFacing enumFacing3 = (EnumFacing)blockState.getValue(BlockChest.FACING);
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.rotateY();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        return blockState.withProperty(BlockChest.FACING, enumFacing3);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForSurroundingChests(world, blockPos, blockState);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this) {
                this.checkForSurroundingChests(world, offset, blockState2);
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos.north()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getBlockState(blockPos.south()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (blockAccess.getBlockState(blockPos.west()).getBlock() == this) {
            this.setBlockBounds(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getBlockState(blockPos.east()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    public ILockableContainer getLockableContainer(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //     5: astore_3       
        //     6: aload_3        
        //     7: instanceof      Lnet/minecraft/tileentity/TileEntityChest;
        //    10: ifne            15
        //    13: aconst_null    
        //    14: areturn        
        //    15: aload_3        
        //    16: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //    19: astore          4
        //    21: aload_0        
        //    22: aload_1        
        //    23: aload_2        
        //    24: ifne            29
        //    27: aconst_null    
        //    28: areturn        
        //    29: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //    32: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //    35: astore          5
        //    37: aload           5
        //    39: invokeinterface java/util/Iterator.hasNext:()Z
        //    44: ifeq            173
        //    47: aload           5
        //    49: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    54: astore          6
        //    56: aload           6
        //    58: checkcast       Lnet/minecraft/util/EnumFacing;
        //    61: astore          7
        //    63: aload_2        
        //    64: aload           7
        //    66: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    69: astore          8
        //    71: aload_1        
        //    72: aload           8
        //    74: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    77: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    82: astore          9
        //    84: aload           9
        //    86: aload_0        
        //    87: if_acmpne       170
        //    90: aload_0        
        //    91: aload_1        
        //    92: aload           8
        //    94: ifne            99
        //    97: aconst_null    
        //    98: areturn        
        //    99: aload_1        
        //   100: aload           8
        //   102: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //   105: astore          10
        //   107: aload           10
        //   109: instanceof      Lnet/minecraft/tileentity/TileEntityChest;
        //   112: ifeq            170
        //   115: aload           7
        //   117: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   120: if_acmpeq       152
        //   123: aload           7
        //   125: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   128: if_acmpeq       152
        //   131: new             Lnet/minecraft/inventory/InventoryLargeChest;
        //   134: dup            
        //   135: ldc             "container.chestDouble"
        //   137: aload           4
        //   139: aload           10
        //   141: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //   144: invokespecial   net/minecraft/inventory/InventoryLargeChest.<init>:(Ljava/lang/String;Lnet/minecraft/world/ILockableContainer;Lnet/minecraft/world/ILockableContainer;)V
        //   147: astore          4
        //   149: goto            37
        //   152: new             Lnet/minecraft/inventory/InventoryLargeChest;
        //   155: dup            
        //   156: ldc             "container.chestDouble"
        //   158: aload           10
        //   160: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //   163: aload           4
        //   165: invokespecial   net/minecraft/inventory/InventoryLargeChest.<init>:(Ljava/lang/String;Lnet/minecraft/world/ILockableContainer;Lnet/minecraft/world/ILockableContainer;)V
        //   168: astore          4
        //   170: goto            37
        //   173: aload           4
        //   175: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0170 (coming from #0112).
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
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess p0, final BlockPos p1, final IBlockState p2, final EnumFacing p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_1        
        //     7: aload_2        
        //     8: invokeinterface net/minecraft/world/IBlockAccess.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //    13: astore          6
        //    15: aload           6
        //    17: instanceof      Lnet/minecraft/tileentity/TileEntityChest;
        //    20: ifeq            33
        //    23: aload           6
        //    25: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //    28: getfield        net/minecraft/tileentity/TileEntityChest.numPlayersUsing:I
        //    31: istore          5
        //    33: iconst_0       
        //    34: iconst_0       
        //    35: bipush          15
        //    37: invokestatic    net/minecraft/util/MathHelper.clamp_int:(III)I
        //    40: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected BlockChest(final int chestType) {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockChest.FACING, EnumFacing.NORTH));
        this.chestType = chestType;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
    
    private boolean isBelowSolidBlock(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.up()).getBlock().isNormalCube();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final ILockableContainer lockableContainer = this.getLockableContainer(world, blockPos);
        if (lockableContainer != null) {
            entityPlayer.displayGUIChest(lockableContainer);
            if (this.chestType == 0) {
                entityPlayer.triggerAchievement(StatList.field_181723_aa);
            }
            else if (this.chestType == 1) {
                entityPlayer.triggerAchievement(StatList.field_181737_U);
            }
        }
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final EnumFacing opposite = EnumFacing.getHorizontal(MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3).getOpposite();
        withProperty = withProperty.withProperty(BlockChest.FACING, opposite);
        final BlockPos north = blockPos.north();
        final BlockPos south = blockPos.south();
        final BlockPos west = blockPos.west();
        final BlockPos east = blockPos.east();
        final boolean b = this == world.getBlockState(north).getBlock();
        final boolean b2 = this == world.getBlockState(south).getBlock();
        final boolean b3 = this == world.getBlockState(west).getBlock();
        final boolean b4 = this == world.getBlockState(east).getBlock();
        if (!b && !b2 && !b3 && !b4) {
            world.setBlockState(blockPos, withProperty, 3);
        }
        else if (opposite.getAxis() != EnumFacing.Axis.X || (!b && !b2)) {
            if (opposite.getAxis() == EnumFacing.Axis.Z && (b3 || b4)) {
                if (b3) {
                    world.setBlockState(west, withProperty, 3);
                }
                else {
                    world.setBlockState(east, withProperty, 3);
                }
                world.setBlockState(blockPos, withProperty, 3);
            }
        }
        else {
            if (b) {
                world.setBlockState(north, withProperty, 3);
            }
            else {
                world.setBlockState(south, withProperty, 3);
            }
            world.setBlockState(blockPos, withProperty, 3);
        }
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                ((TileEntityChest)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
}
