package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;

public class BlockRedstoneRepeater extends BlockRedstoneDiode
{
    public static final PropertyBool LOCKED;
    public static final PropertyInteger DELAY;
    
    static {
        LOCKED = PropertyBool.create("locked");
        DELAY = PropertyInteger.create("delay", 1, 4);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.repeater;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockRedstoneRepeater.LOCKED, this.isLocked(blockAccess, blockPos, blockState));
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return false;
        }
        world.setBlockState(blockPos, blockState.cycleProperty(BlockRedstoneRepeater.DELAY), 3);
        return true;
    }
    
    @Override
    protected int getDelay(final IBlockState blockState) {
        return (int)blockState.getValue(BlockRedstoneRepeater.DELAY) * 2;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneRepeater.FACING);
            final double n = blockPos.getX() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            final double n2 = blockPos.getY() + 0.4f + (random.nextFloat() - 0.5f) * 0.2;
            final double n3 = blockPos.getZ() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            float n4 = -5.0f;
            if (random.nextBoolean()) {
                n4 = (float)((int)blockState.getValue(BlockRedstoneRepeater.DELAY) * 2 - 1);
            }
            final float n5 = n4 / 16.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n + n5 * enumFacing.getFrontOffsetX(), n2, n3 + n5 * enumFacing.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    protected boolean canPowerSide(final Block block) {
        return isRedstoneRepeaterBlockID(block);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneRepeater.FACING, BlockRedstoneRepeater.DELAY, BlockRedstoneRepeater.LOCKED });
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.repeater;
    }
    
    protected BlockRedstoneRepeater(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneRepeater.FACING, EnumFacing.NORTH).withProperty(BlockRedstoneRepeater.DELAY, 1).withProperty(BlockRedstoneRepeater.LOCKED, false));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockRedstoneRepeater.FACING)).getHorizontalIndex();
        final int n2 = 0x0 | (int)blockState.getValue(BlockRedstoneRepeater.DELAY) - 1 << 2;
        return 0;
    }
    
    @Override
    protected IBlockState getPoweredState(final IBlockState blockState) {
        return Blocks.powered_repeater.getDefaultState().withProperty(BlockRedstoneRepeater.FACING, blockState.getValue(BlockRedstoneRepeater.FACING)).withProperty(BlockRedstoneRepeater.DELAY, blockState.getValue(BlockRedstoneRepeater.DELAY)).withProperty(BlockRedstoneRepeater.LOCKED, blockState.getValue(BlockRedstoneRepeater.LOCKED));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneRepeater.FACING, EnumFacing.getHorizontal(n)).withProperty(BlockRedstoneRepeater.LOCKED, false).withProperty(BlockRedstoneRepeater.DELAY, 1 + (n >> 2));
    }
    
    @Override
    public boolean isLocked(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return this.getPowerOnSides(blockAccess, blockPos, blockState) > 0;
    }
    
    @Override
    protected IBlockState getUnpoweredState(final IBlockState blockState) {
        return Blocks.unpowered_repeater.getDefaultState().withProperty(BlockRedstoneRepeater.FACING, blockState.getValue(BlockRedstoneRepeater.FACING)).withProperty(BlockRedstoneRepeater.DELAY, blockState.getValue(BlockRedstoneRepeater.DELAY)).withProperty(BlockRedstoneRepeater.LOCKED, blockState.getValue(BlockRedstoneRepeater.LOCKED));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public String getLocalizedName() {
        return "\u5b2e\u5b33\u5b22\u5b2a\u5b69\u5b23\u5b2e\u5b28\u5b23\u5b22\u5b69\u5b29\u5b26\u5b2a\u5b22";
    }
}
