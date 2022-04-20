package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public abstract class WorldGenAbstractTree extends WorldGenerator
{
    public WorldGenAbstractTree(final boolean b) {
        super(b);
    }
    
    protected void func_175921_a(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != Blocks.dirt) {
            this.setBlockAndNotifyAdequately(world, blockPos, Blocks.dirt.getDefaultState());
        }
    }
    
    public void func_180711_a(final World world, final Random random, final BlockPos blockPos) {
    }
    
    protected boolean func_150523_a(final Block block) {
        final Material material = block.getMaterial();
        return material == Material.air || material == Material.leaves || block == Blocks.grass || block == Blocks.dirt || block == Blocks.log || block == Blocks.log2 || block == Blocks.sapling || block == Blocks.vine;
    }
}
