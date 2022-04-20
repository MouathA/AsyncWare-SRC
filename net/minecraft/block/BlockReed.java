package net.minecraft.block;

import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockReed extends Block
{
    public static final PropertyInteger AGE;
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        return this.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //     5: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     8: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    13: getstatic       net/minecraft/init/Blocks.reeds:Lnet/minecraft/block/BlockReed;
        //    16: if_acmpeq       26
        //    19: aload_0        
        //    20: aload_1        
        //    21: aload_2        
        //    22: aload_3        
        //    23: ifeq            144
        //    26: aload_1        
        //    27: aload_2        
        //    28: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    31: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //    34: ifeq            144
        //    37: aload_1        
        //    38: aload_2        
        //    39: iconst_1       
        //    40: invokevirtual   net/minecraft/util/BlockPos.down:(I)Lnet/minecraft/util/BlockPos;
        //    43: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    46: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    51: aload_0        
        //    52: if_acmpne       61
        //    55: iinc            5, 1
        //    58: goto            37
        //    61: aload_3        
        //    62: getstatic       net/minecraft/block/BlockReed.AGE:Lnet/minecraft/block/properties/PropertyInteger;
        //    65: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    70: checkcast       Ljava/lang/Integer;
        //    73: invokevirtual   java/lang/Integer.intValue:()I
        //    76: istore          6
        //    78: iload           6
        //    80: bipush          15
        //    82: if_icmpne       121
        //    85: aload_1        
        //    86: aload_2        
        //    87: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    90: aload_0        
        //    91: invokevirtual   net/minecraft/block/BlockReed.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    94: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //    97: pop            
        //    98: aload_1        
        //    99: aload_2        
        //   100: aload_3        
        //   101: getstatic       net/minecraft/block/BlockReed.AGE:Lnet/minecraft/block/properties/PropertyInteger;
        //   104: iconst_0       
        //   105: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   108: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   113: iconst_4       
        //   114: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   117: pop            
        //   118: goto            144
        //   121: aload_1        
        //   122: aload_2        
        //   123: aload_3        
        //   124: getstatic       net/minecraft/block/BlockReed.AGE:Lnet/minecraft/block/properties/PropertyInteger;
        //   127: iload           6
        //   129: iconst_1       
        //   130: iadd           
        //   131: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   134: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   139: iconst_4       
        //   140: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   143: pop            
        //   144: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0026 (coming from #0023).
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
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos.down()).getBlock();
        if (block == this) {
            return true;
        }
        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
            return false;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next()).down()).getBlock().getMaterial() == Material.water) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.reeds;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockReed.AGE });
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 15);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }
    
    protected BlockReed() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockReed.AGE, 0));
        final float n = 0.375f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
        this.setTickRandomly(true);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockReed.AGE, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockReed.AGE);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.reeds;
    }
}
