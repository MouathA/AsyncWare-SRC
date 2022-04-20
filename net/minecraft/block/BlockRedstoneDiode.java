package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public abstract class BlockRedstoneDiode extends BlockDirectional
{
    protected final boolean isRepeaterPowered;
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isRepeaterPowered) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
        super.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    public boolean isAssociated(final Block block) {
        return block == this.getPoweredState(this.getDefaultState()).getBlock() || block == this.getUnpoweredState(this.getDefaultState()).getBlock();
    }
    
    protected int getTickDelay(final IBlockState blockState) {
        return this.getDelay(blockState);
    }
    
    protected abstract IBlockState getPoweredState(final IBlockState p0);
    
    protected boolean isPowered(final IBlockState blockState) {
        return this.isRepeaterPowered;
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!this.isLocked(world, blockPos, blockState)) {
            final boolean shouldBePowered = this.shouldBePowered(world, blockPos, blockState);
            if (this.isRepeaterPowered && !shouldBePowered) {
                world.setBlockState(blockPos, this.getUnpoweredState(blockState), 2);
            }
            else if (!this.isRepeaterPowered) {
                world.setBlockState(blockPos, this.getPoweredState(blockState), 2);
                if (!shouldBePowered) {
                    world.updateBlockTick(blockPos, this.getPoweredState(blockState).getBlock(), this.getTickDelay(blockState), -1);
                }
            }
        }
    }
    
    protected void updateState(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: invokevirtual   net/minecraft/block/BlockRedstoneDiode.isLocked:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //     7: ifne            82
        //    10: aload_0        
        //    11: aload_1        
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/block/BlockRedstoneDiode.shouldBePowered:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //    17: istore          4
        //    19: aload_0        
        //    20: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    23: ifeq            31
        //    26: iload           4
        //    28: ifeq            43
        //    31: aload_0        
        //    32: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    35: ifne            82
        //    38: iload           4
        //    40: ifeq            82
        //    43: aload_1        
        //    44: aload_2        
        //    45: aload_0        
        //    46: invokevirtual   net/minecraft/world/World.isBlockTickPending:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)Z
        //    49: ifne            82
        //    52: aload_0        
        //    53: aload_1        
        //    54: aload_2        
        //    55: aload_3        
        //    56: ifeq            62
        //    59: goto            69
        //    62: aload_0        
        //    63: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    66: ifeq            69
        //    69: aload_1        
        //    70: aload_2        
        //    71: aload_0        
        //    72: aload_0        
        //    73: aload_3        
        //    74: invokevirtual   net/minecraft/block/BlockRedstoneDiode.getDelay:(Lnet/minecraft/block/state/IBlockState;)I
        //    77: bipush          -2
        //    79: invokevirtual   net/minecraft/world/World.updateBlockTick:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //    82: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0082 (coming from #0079).
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
    
    protected int getActiveSignal(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return 15;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockRedstoneDiode.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    protected abstract int getDelay(final IBlockState p0);
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    protected void notifyNeighbors(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.FACING);
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        world.notifyBlockOfStateChange(offset, this);
        world.notifyNeighborsOfStateExcept(offset, this, enumFacing);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        if (blockState > 0) {
            world.scheduleUpdate(blockPos, this, 1);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        return this.isAssociated(block);
    }
    
    protected abstract IBlockState getUnpoweredState(final IBlockState p0);
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && super.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.canBlockStay(world, blockToAir)) {
            this.updateState(world, blockToAir, blockState);
        }
        else {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockToAir.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.isPowered(blockState) ? ((blockState.getValue(BlockRedstoneDiode.FACING) == enumFacing) ? this.getActiveSignal(blockAccess, blockPos, blockState) : 0) : 0;
    }
    
    protected int calculateInputStrength(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.FACING);
        final BlockPos offset = blockPos.offset(enumFacing);
        final int redstonePower = world.getRedstonePower(offset, enumFacing);
        if (redstonePower >= 15) {
            return redstonePower;
        }
        final IBlockState blockState2 = world.getBlockState(offset);
        return Math.max(redstonePower, (blockState2.getBlock() == Blocks.redstone_wire) ? ((int)blockState2.getValue(BlockRedstoneWire.POWER)) : 0);
    }
    
    protected BlockRedstoneDiode(final boolean isRepeaterPowered) {
        super(Material.circuits);
        this.isRepeaterPowered = isRepeaterPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    protected int getPowerOnSide(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return (int)(this.canPowerSide(block) ? ((block == Blocks.redstone_wire) ? blockState.getValue(BlockRedstoneWire.POWER) : blockAccess.getStrongPower(blockPos, enumFacing)) : 0);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing.getAxis() != EnumFacing.Axis.Y;
    }
    
    public boolean isLocked(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return false;
    }
    
    protected int getPowerOnSides(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.FACING);
        final EnumFacing rotateY = enumFacing.rotateY();
        final EnumFacing rotateYCCW = enumFacing.rotateYCCW();
        return Math.max(this.getPowerOnSide(blockAccess, blockPos.offset(rotateY), rotateY), this.getPowerOnSide(blockAccess, blockPos.offset(rotateYCCW), rotateYCCW));
    }
    
    protected boolean canPowerSide(final Block block) {
        return block.canProvidePower();
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.getWeakPower(blockAccess, blockPos, blockState, enumFacing);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
}
