package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;

public abstract class WorldGenerator
{
    private final boolean doBlockNotify;
    
    public void func_175904_e() {
    }
    
    protected void setBlockAndNotifyAdequately(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.doBlockNotify) {
            world.setBlockState(blockPos, blockState, 3);
        }
        else {
            world.setBlockState(blockPos, blockState, 2);
        }
    }
    
    public WorldGenerator(final boolean doBlockNotify) {
        this.doBlockNotify = doBlockNotify;
    }
    
    public abstract boolean generate(final World p0, final Random p1, final BlockPos p2);
    
    public WorldGenerator() {
        this(false);
    }
}
