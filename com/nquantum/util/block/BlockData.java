package com.nquantum.util.block;

import net.minecraft.util.*;

public class BlockData
{
    public final EnumFacing face;
    public final BlockPos pos;
    
    public BlockData(final BlockPos pos, final EnumFacing face) {
        this.pos = pos;
        this.face = face;
    }
}
