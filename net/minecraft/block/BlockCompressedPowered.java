package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockCompressedPowered extends Block
{
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    public BlockCompressedPowered(final Material material, final MapColor mapColor) {
        super(material, mapColor);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return 15;
    }
}
