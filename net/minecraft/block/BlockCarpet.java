package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockCarpet extends Block
{
    public static final PropertyEnum COLOR;
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockCarpet.COLOR)).getMapColor();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCarpet.COLOR, EnumDyeColor.byMetadata(n));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (true) {
            list.add(new ItemStack(item, 1, 0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCarpet.COLOR });
    }
    
    static {
        COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: invokespecial   net/minecraft/block/Block.canPlaceBlockAt:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //     6: ifeq            19
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0019 (coming from #0012).
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
    
    protected BlockCarpet() {
        super(Material.carpet);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCarpet.COLOR, EnumDyeColor.WHITE));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsFromMeta(0);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing == EnumFacing.UP || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsFromMeta(0);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    protected void setBlockBoundsFromMeta(final int n) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1 / 16.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBoundsFromMeta(0);
    }
    
    private boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (blockToAir == 0) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            return false;
        }
        return true;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockCarpet.COLOR)).getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockCarpet.COLOR)).getMetadata();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
