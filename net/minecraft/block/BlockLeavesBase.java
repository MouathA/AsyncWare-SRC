package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockLeavesBase extends Block
{
    protected boolean fancyGraphics;
    
    protected BlockLeavesBase(final Material material, final boolean fancyGraphics) {
        super(material);
        this.fancyGraphics = fancyGraphics;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (this.fancyGraphics || blockAccess.getBlockState(blockPos).getBlock() != this) && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
}
