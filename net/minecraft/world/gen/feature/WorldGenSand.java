package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenSand extends WorldGenerator
{
    private Block block;
    private int radius;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
            return false;
        }
        for (int n = random.nextInt(this.radius - 2) + 2, i = blockPos.getX() - n; i <= blockPos.getX() + n; ++i) {
            for (int j = blockPos.getZ() - n; j <= blockPos.getZ() + n; ++j) {
                final int n2 = i - blockPos.getX();
                final int n3 = j - blockPos.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    for (int k = blockPos.getY() - 2; k <= blockPos.getY() + 2; ++k) {
                        final BlockPos blockPos2 = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos2).getBlock();
                        if (block == Blocks.dirt || block == Blocks.grass) {
                            world.setBlockState(blockPos2, this.block.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public WorldGenSand(final Block block, final int radius) {
        this.block = block;
        this.radius = radius;
    }
}
