package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public class BlockTorch extends Block
{
    public static final PropertyDirection FACING;
    
    @Override
    public boolean canPlaceBlockAt(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   net/minecraft/block/properties/PropertyDirection.getAllowedValues:()Ljava/util/Collection;
        //     6: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    11: astore_3       
        //    12: aload_3        
        //    13: invokeinterface java/util/Iterator.hasNext:()Z
        //    18: ifeq            45
        //    21: aload_3        
        //    22: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    27: checkcast       Lnet/minecraft/util/EnumFacing;
        //    30: astore          4
        //    32: aload_0        
        //    33: aload_1        
        //    34: aload_2        
        //    35: aload           4
        //    37: ifeq            42
        //    40: iconst_1       
        //    41: ireturn        
        //    42: goto            12
        //    45: iconst_0       
        //    46: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0012 (coming from #0042).
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
    
    protected BlockTorch() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTorch.FACING, EnumFacing.UP));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.onNeighborChangeInternal(world, blockPos, blockState);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTorch.FACING);
        final double n = blockPos.getX() + 0.5;
        final double n2 = blockPos.getY() + 0.7;
        final double n3 = blockPos.getZ() + 0.5;
        final double n4 = 0.22;
        final double n5 = 0.27;
        if (enumFacing.getAxis().isHorizontal()) {
            final EnumFacing opposite = enumFacing.getOpposite();
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
        }
        else {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTorch.FACING });
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        switch (BlockTorch$2.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockState.getValue(BlockTorch.FACING)).ordinal()]) {
            case 1: {}
            case 2: {}
            case 3: {}
        }
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        IBlockState blockState = null;
        switch (n) {
            case 1: {
                blockState = defaultState.withProperty(BlockTorch.FACING, EnumFacing.EAST);
                break;
            }
            case 2: {
                blockState = defaultState.withProperty(BlockTorch.FACING, EnumFacing.WEST);
                break;
            }
            case 3: {
                blockState = defaultState.withProperty(BlockTorch.FACING, EnumFacing.SOUTH);
                break;
            }
            case 4: {
                blockState = defaultState.withProperty(BlockTorch.FACING, EnumFacing.NORTH);
                break;
            }
            default: {
                blockState = defaultState.withProperty(BlockTorch.FACING, EnumFacing.UP);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (enumFacing != 0) {
            return this.getDefaultState().withProperty(BlockTorch.FACING, enumFacing);
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (world.isBlockNormalCube(blockPos.offset(enumFacing2.getOpposite()), true)) {
                return this.getDefaultState().withProperty(BlockTorch.FACING, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
            
            public boolean apply(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.DOWN;
            }
        });
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    protected boolean onNeighborChangeInternal(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: if_acmpne       9
        //     7: iconst_1       
        //     8: ireturn        
        //     9: aload_3        
        //    10: getstatic       net/minecraft/block/BlockTorch.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    13: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    18: checkcast       Lnet/minecraft/util/EnumFacing;
        //    21: astore          4
        //    23: aload           4
        //    25: invokevirtual   net/minecraft/util/EnumFacing.getAxis:()Lnet/minecraft/util/EnumFacing$Axis;
        //    28: astore          5
        //    30: aload           4
        //    32: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    35: astore          6
        //    37: aload           5
        //    39: invokevirtual   net/minecraft/util/EnumFacing$Axis.isHorizontal:()Z
        //    42: ifeq            62
        //    45: aload_1        
        //    46: aload_2        
        //    47: aload           6
        //    49: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    52: iconst_1       
        //    53: invokevirtual   net/minecraft/world/World.isBlockNormalCube:(Lnet/minecraft/util/BlockPos;Z)Z
        //    56: ifne            62
        //    59: goto            81
        //    62: aload           5
        //    64: invokevirtual   net/minecraft/util/EnumFacing$Axis.isVertical:()Z
        //    67: ifeq            81
        //    70: aload_0        
        //    71: aload_1        
        //    72: aload_2        
        //    73: aload           6
        //    75: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    78: ifeq            81
        //    81: aload_0        
        //    82: aload_1        
        //    83: aload_2        
        //    84: aload_3        
        //    85: iconst_0       
        //    86: invokevirtual   net/minecraft/block/BlockTorch.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    89: aload_1        
        //    90: aload_2        
        //    91: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    94: pop            
        //    95: iconst_1       
        //    96: ireturn        
        //    97: iconst_0       
        //    98: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0081 (coming from #0078).
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
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final EnumFacing enumFacing = (EnumFacing)world.getBlockState(blockPos).getValue(BlockTorch.FACING);
        final float n = 0.15f;
        if (enumFacing == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
        }
        else if (enumFacing == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
        }
        else if (enumFacing == EnumFacing.NORTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
        }
        else {
            final float n2 = 0.1f;
            this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
        }
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
}
