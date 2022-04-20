package net.minecraft.dispenser;

import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public interface IBlockSource extends ILocatableSource
{
    double getY();
    
    double getX();
    
    BlockPos getBlockPos();
    
    TileEntity getBlockTileEntity();
    
    int getBlockMetadata();
    
    double getZ();
}
