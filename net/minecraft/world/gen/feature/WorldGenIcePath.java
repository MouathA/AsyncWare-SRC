package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenIcePath extends WorldGenerator
{
    private int basePathWidth;
    private Block block;
    
    public WorldGenIcePath(final int basePathWidth) {
        this.block = Blocks.packed_ice;
        this.basePathWidth = basePathWidth;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        while (world.isAirBlock(down) && down.getY() > 2) {
            down = down.down();
        }
        if (world.getBlockState(down).getBlock() != Blocks.snow) {
            return false;
        }
        for (int n = random.nextInt(this.basePathWidth - 2) + 2, i = down.getX() - n; i <= down.getX() + n; ++i) {
            for (int j = down.getZ() - n; j <= down.getZ() + n; ++j) {
                final int n2 = i - down.getX();
                final int n3 = j - down.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    for (int k = down.getY() - 1; k <= down.getY() + 1; ++k) {
                        final BlockPos blockPos = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos).getBlock();
                        if (block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
                            world.setBlockState(blockPos, this.block.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
