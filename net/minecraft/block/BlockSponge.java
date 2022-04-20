package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockSponge extends Block
{
    public static final PropertyBool WET;
    
    protected void tryAbsorb(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/block/BlockSponge.WET:Lnet/minecraft/block/properties/PropertyBool;
        //     4: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //     9: checkcast       Ljava/lang/Boolean;
        //    12: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    15: ifne            58
        //    18: aload_0        
        //    19: aload_1        
        //    20: aload_2        
        //    21: ifne            58
        //    24: aload_1        
        //    25: aload_2        
        //    26: aload_3        
        //    27: getstatic       net/minecraft/block/BlockSponge.WET:Lnet/minecraft/block/properties/PropertyBool;
        //    30: iconst_1       
        //    31: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    34: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    39: iconst_2       
        //    40: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    43: pop            
        //    44: aload_1        
        //    45: sipush          2001
        //    48: aload_2        
        //    49: getstatic       net/minecraft/init/Blocks.water:Lnet/minecraft/block/BlockStaticLiquid;
        //    52: invokestatic    net/minecraft/block/Block.getIdFromBlock:(Lnet/minecraft/block/Block;)I
        //    55: invokevirtual   net/minecraft/world/World.playAuxSFX:(ILnet/minecraft/util/BlockPos;I)V
        //    58: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0058 (coming from #0021).
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
    
    protected BlockSponge() {
        super(Material.sponge);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSponge.WET, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".dry.name");
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSponge.WET, (n & 0x1) == 0x1);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockSponge.WET)) ? 1 : 0;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockSponge.WET)) ? 1 : 0;
    }
    
    static {
        WET = PropertyBool.create("wet");
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.tryAbsorb(world, blockPos, blockState);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSponge.WET });
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (blockState.getValue(BlockSponge.WET)) {
            final EnumFacing random2 = EnumFacing.random(random);
            if (random2 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(world, blockPos.offset(random2))) {
                final double n = blockPos.getX();
                final double n2 = blockPos.getY();
                final double n3 = blockPos.getZ();
                double n4;
                double n5;
                double n6;
                if (random2 == EnumFacing.DOWN) {
                    n4 = n2 - 0.05;
                    n5 = n + random.nextDouble();
                    n6 = n3 + random.nextDouble();
                }
                else {
                    n4 = n2 + random.nextDouble() * 0.8;
                    if (random2.getAxis() == EnumFacing.Axis.X) {
                        n6 = n3 + random.nextDouble();
                        if (random2 == EnumFacing.EAST) {
                            n5 = n + 1.0;
                        }
                        else {
                            n5 = n + 0.05;
                        }
                    }
                    else {
                        n5 = n + random.nextDouble();
                        if (random2 == EnumFacing.SOUTH) {
                            n6 = n3 + 1.0;
                        }
                        else {
                            n6 = n3 + 0.05;
                        }
                    }
                }
                world.spawnParticle(EnumParticleTypes.DRIP_WATER, n5, n4, n6, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.tryAbsorb(world, blockPos, blockState);
        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }
}
