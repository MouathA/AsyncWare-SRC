package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class BlockFarmland extends Block
{
    public static final PropertyInteger MOISTURE;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFarmland.MOISTURE, n & 0x7);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFarmland.MOISTURE });
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        switch (BlockFarmland$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return true;
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                final Block block = blockAccess.getBlockState(blockPos).getBlock();
                return !block.isOpaqueCube() && block != Blocks.farmland;
            }
            default: {
                return super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
            }
        }
    }
    
    protected BlockFarmland() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFarmland.MOISTURE, 0));
        this.setTickRandomly(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        if (world.getBlockState(blockPos.up()).getBlock().getMaterial().isSolid()) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFarmland.MOISTURE);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
    }
    
    static {
        MOISTURE = PropertyInteger.create("moisture", 0, 7);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.dirt);
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/block/BlockFarmland.MOISTURE:Lnet/minecraft/block/properties/PropertyInteger;
        //     4: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //     9: checkcast       Ljava/lang/Integer;
        //    12: invokevirtual   java/lang/Integer.intValue:()I
        //    15: istore          5
        //    17: aload_0        
        //    18: aload_1        
        //    19: aload_2        
        //    20: ifeq            86
        //    23: aload_1        
        //    24: aload_2        
        //    25: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    28: invokevirtual   net/minecraft/world/World.canLightningStrike:(Lnet/minecraft/util/BlockPos;)Z
        //    31: ifne            86
        //    34: iload           5
        //    36: ifle            65
        //    39: aload_1        
        //    40: aload_2        
        //    41: aload_3        
        //    42: getstatic       net/minecraft/block/BlockFarmland.MOISTURE:Lnet/minecraft/block/properties/PropertyInteger;
        //    45: iload           5
        //    47: iconst_1       
        //    48: isub           
        //    49: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    52: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    57: iconst_2       
        //    58: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    61: pop            
        //    62: goto            114
        //    65: aload_0        
        //    66: aload_1        
        //    67: aload_2        
        //    68: ifne            114
        //    71: aload_1        
        //    72: aload_2        
        //    73: getstatic       net/minecraft/init/Blocks.dirt:Lnet/minecraft/block/Block;
        //    76: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    79: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //    82: pop            
        //    83: goto            114
        //    86: iload           5
        //    88: bipush          7
        //    90: if_icmpge       114
        //    93: aload_1        
        //    94: aload_2        
        //    95: aload_3        
        //    96: getstatic       net/minecraft/block/BlockFarmland.MOISTURE:Lnet/minecraft/block/properties/PropertyInteger;
        //    99: bipush          7
        //   101: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   104: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   109: iconst_2       
        //   110: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   113: pop            
        //   114: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0114 (coming from #0068).
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
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        if (entity instanceof EntityLivingBase) {
            if (!world.isRemote && world.rand.nextFloat() < n - 0.5f) {
                if (!(entity instanceof EntityPlayer) && !world.getGameRules().getBoolean("mobGriefing")) {
                    return;
                }
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
            super.onFallenUpon(world, blockPos, entity, n);
        }
    }
}
