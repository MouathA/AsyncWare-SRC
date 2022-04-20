package net.minecraft.world;

import net.minecraft.block.state.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public interface IBlockAccess
{
    IBlockState getBlockState(final BlockPos p0);
    
    BiomeGenBase getBiomeGenForCoords(final BlockPos p0);
    
    int getStrongPower(final BlockPos p0, final EnumFacing p1);
    
    int getCombinedLight(final BlockPos p0, final int p1);
    
    WorldType getWorldType();
    
    boolean isAirBlock(final BlockPos p0);
    
    TileEntity getTileEntity(final BlockPos p0);
    
    boolean extendedLevelsInChunkCache();
}
