package net.minecraft.block;

import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockBed extends BlockDirectional
{
    public static final PropertyEnum PART;
    public static final PropertyBool OCCUPIED;
    
    private EntityPlayer getPlayerInBed(final World world, final BlockPos blockPos) {
        for (final EntityPlayer entityPlayer : world.playerEntities) {
            if (entityPlayer.isPlayerSleeping() && entityPlayer.playerLocation.equals(blockPos)) {
                return entityPlayer;
            }
        }
        return null;
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final EnumFacing horizontal = EnumFacing.getHorizontal(n);
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockBed.PART, EnumPartType.HEAD).withProperty(BlockBed.FACING, horizontal).withProperty(BlockBed.OCCUPIED, (n & 0x4) > 0) : this.getDefaultState().withProperty(BlockBed.PART, EnumPartType.FOOT).withProperty(BlockBed.FACING, horizontal);
    }
    
    static {
        PART = PropertyEnum.create("part", EnumPartType.class);
        OCCUPIED = PropertyBool.create("occupied");
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (blockState.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, 0);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            final BlockPos offset = blockPos.offset(((EnumFacing)blockState.getValue(BlockBed.FACING)).getOpposite());
            if (world.getBlockState(offset).getBlock() == this) {
                world.setBlockToAir(offset);
            }
        }
    }
    
    public BlockBed() {
        super(Material.cloth);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBed.PART, EnumPartType.FOOT).withProperty(BlockBed.OCCUPIED, false));
        this.setBedBounds();
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.bed;
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.offset((EnumFacing)withProperty.getValue(BlockBed.FACING)));
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty(BlockBed.OCCUPIED, blockState.getValue(BlockBed.OCCUPIED));
            }
        }
        return withProperty;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockBed.FACING, BlockBed.PART, BlockBed.OCCUPIED });
    }
    
    private void setBedBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static BlockPos getSafeExitLocation(final World p0, final BlockPos p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     5: getstatic       net/minecraft/block/BlockBed.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //     8: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    13: checkcast       Lnet/minecraft/util/EnumFacing;
        //    16: astore_3       
        //    17: aload_1        
        //    18: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    21: istore          4
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    27: istore          5
        //    29: aload_1        
        //    30: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    33: istore          6
        //    35: iload           4
        //    37: aload_3        
        //    38: invokevirtual   net/minecraft/util/EnumFacing.getFrontOffsetX:()I
        //    41: iconst_0       
        //    42: imul           
        //    43: isub           
        //    44: iconst_1       
        //    45: isub           
        //    46: istore          8
        //    48: iload           6
        //    50: aload_3        
        //    51: invokevirtual   net/minecraft/util/EnumFacing.getFrontOffsetZ:()I
        //    54: iconst_0       
        //    55: imul           
        //    56: isub           
        //    57: iconst_1       
        //    58: isub           
        //    59: istore          9
        //    61: iload           8
        //    63: iconst_2       
        //    64: iadd           
        //    65: istore          10
        //    67: iload           9
        //    69: iconst_2       
        //    70: iadd           
        //    71: istore          11
        //    73: iload           8
        //    75: istore          12
        //    77: iload           12
        //    79: iload           10
        //    81: if_icmpgt       138
        //    84: iload           9
        //    86: istore          13
        //    88: iload           13
        //    90: iload           11
        //    92: if_icmpgt       132
        //    95: new             Lnet/minecraft/util/BlockPos;
        //    98: dup            
        //    99: iload           12
        //   101: iload           5
        //   103: iload           13
        //   105: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   108: astore          14
        //   110: aload_0        
        //   111: aload           14
        //   113: ifeq            126
        //   116: iload_2        
        //   117: ifgt            123
        //   120: aload           14
        //   122: areturn        
        //   123: iinc            2, -1
        //   126: iinc            13, 1
        //   129: goto            88
        //   132: iinc            12, 1
        //   135: goto            77
        //   138: iinc            7, 1
        //   141: goto            35
        //   144: aconst_null    
        //   145: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0088 (coming from #0129).
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
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBedBounds();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockBed.FACING)).getHorizontalIndex();
        if (blockState.getValue(BlockBed.PART) != EnumPartType.HEAD || (boolean)blockState.getValue(BlockBed.OCCUPIED)) {}
        return 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, BlockPos offset, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (blockState.getValue(BlockBed.PART) != EnumPartType.HEAD) {
            offset = offset.offset((EnumFacing)blockState.getValue(BlockBed.FACING));
            blockState = world.getBlockState(offset);
            if (blockState.getBlock() != this) {
                return true;
            }
        }
        if (!world.provider.canRespawnHere() || world.getBiomeGenForCoords(offset) == BiomeGenBase.hell) {
            world.setBlockToAir(offset);
            final BlockPos offset2 = offset.offset(((EnumFacing)blockState.getValue(BlockBed.FACING)).getOpposite());
            if (world.getBlockState(offset2).getBlock() == this) {
                world.setBlockToAir(offset2);
            }
            world.newExplosion(null, offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, 5.0f, true, true);
            return true;
        }
        if (blockState.getValue(BlockBed.OCCUPIED)) {
            if (this.getPlayerInBed(world, offset) != null) {
                entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }
            blockState = blockState.withProperty(BlockBed.OCCUPIED, false);
            world.setBlockState(offset, blockState, 4);
        }
        final EntityPlayer.EnumStatus trySleep = entityPlayer.trySleep(offset);
        if (trySleep == EntityPlayer.EnumStatus.OK) {
            blockState = blockState.withProperty(BlockBed.OCCUPIED, true);
            world.setBlockState(offset, blockState, 4);
            return true;
        }
        if (trySleep == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        }
        else if (trySleep == EntityPlayer.EnumStatus.NOT_SAFE) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockBed.FACING);
        if (blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            if (world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock() != this) {
                world.setBlockToAir(blockPos);
            }
        }
        else if (world.getBlockState(blockPos.offset(enumFacing)).getBlock() != this) {
            world.setBlockToAir(blockPos);
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) ? null : Items.bed;
    }
    
    public enum EnumPartType implements IStringSerializable
    {
        FOOT("FOOT", 1, "foot");
        
        private static final EnumPartType[] $VALUES;
        
        HEAD("HEAD", 0, "head");
        
        private final String name;
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumPartType[] { EnumPartType.HEAD, EnumPartType.FOOT };
        }
        
        private EnumPartType(final String s, final int n, final String name) {
            this.name = name;
        }
    }
}
