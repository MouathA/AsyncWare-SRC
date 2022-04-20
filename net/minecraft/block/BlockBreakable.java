package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockBreakable extends Block
{
    private boolean ignoreSimilarity;
    
    protected BlockBreakable(final Material material, final boolean b) {
        this(material, b, material.getMaterialMapColor());
    }
    
    protected BlockBreakable(final Material material, final boolean ignoreSimilarity, final MapColor mapColor) {
        super(material, mapColor);
        this.ignoreSimilarity = ignoreSimilarity;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (blockAccess.getBlockState(blockPos.offset(enumFacing.getOpposite())) != blockState) {
                return true;
            }
            if (block == this) {
                return false;
            }
        }
        return (this.ignoreSimilarity || block != this) && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
