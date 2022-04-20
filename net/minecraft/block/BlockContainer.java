package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    protected boolean func_181087_e(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //     6: if_acmpne       36
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //    15: if_acmpne       36
        //    18: aload_0        
        //    19: aload_1        
        //    20: aload_2        
        //    21: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //    24: if_acmpne       36
        //    27: aload_0        
        //    28: aload_1        
        //    29: aload_2        
        //    30: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    33: if_acmpne       40
        //    36: iconst_1       
        //    37: goto            41
        //    40: iconst_0       
        //    41: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0036 (coming from #0015).
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
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(n, n2);
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    protected BlockContainer(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
    }
    
    protected BlockContainer(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.isBlockContainer = true;
    }
}
