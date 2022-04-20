package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public abstract class BlockBasePressurePlate extends Block
{
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState0(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public int tickRate(final World world) {
        return 20;
    }
    
    protected abstract int computeRedstoneStrength(final World p0, final BlockPos p1);
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            final int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength > 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }
    
    protected void setBlockBoundsBasedOnState0(final IBlockState blockState) {
        if (this.getRedstoneStrength(blockState) > 0) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.03125f, 0.9375f);
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.0625f, 0.9375f);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (blockToAir.down() == 0) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote) {
            final int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength == 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return true;
    }
    
    protected void updateState(final World world, final BlockPos blockPos, IBlockState setRedstoneStrength, final int n) {
        final int computeRedstoneStrength = this.computeRedstoneStrength(world, blockPos);
        final boolean b = n > 0;
        final boolean b2 = computeRedstoneStrength > 0;
        if (n != computeRedstoneStrength) {
            setRedstoneStrength = this.setRedstoneStrength(setRedstoneStrength, computeRedstoneStrength);
            world.setBlockState(blockPos, setRedstoneStrength, 2);
            this.updateNeighbors(world, blockPos);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (!b2 && b) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.5f);
        }
        else if (b2 && !b) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (b2) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    protected abstract IBlockState setRedstoneStrength(final IBlockState p0, final int p1);
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.getRedstoneStrength(blockState) > 0) {
            this.updateNeighbors(world, blockPos);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    protected BlockBasePressurePlate(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    protected abstract int getRedstoneStrength(final IBlockState p0);
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.375f, 0.0f, 1.0f, 0.625f, 1.0f);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public boolean func_181623_g() {
        return true;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP) ? this.getRedstoneStrength(blockState) : 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    protected AxisAlignedBB getSensitiveAABB(final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + 0.125f, blockPos.getY(), blockPos.getZ() + 0.125f, blockPos.getX() + 1 - 0.125f, blockPos.getY() + 0.25, blockPos.getZ() + 1 - 0.125f);
    }
    
    protected void updateNeighbors(final World world, final BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.down(), this);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.getRedstoneStrength(blockState);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.canBePlacedOn(world, blockPos.down());
    }
    
    protected BlockBasePressurePlate(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
    }
}
