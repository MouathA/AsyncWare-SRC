package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class BlockAir extends Block
{
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return false;
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    protected BlockAir() {
        super(Material.air);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
