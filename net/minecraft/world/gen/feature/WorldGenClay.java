package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class WorldGenClay extends WorldGenerator
{
    private int numberOfBlocks;
    private Block field_150546_a;
    
    public WorldGenClay(final int numberOfBlocks) {
        this.field_150546_a = Blocks.clay;
        this.numberOfBlocks = numberOfBlocks;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
            return false;
        }
        for (int n = random.nextInt(this.numberOfBlocks - 2) + 2, i = blockPos.getX() - n; i <= blockPos.getX() + n; ++i) {
            for (int j = blockPos.getZ() - n; j <= blockPos.getZ() + n; ++j) {
                final int n2 = i - blockPos.getX();
                final int n3 = j - blockPos.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    for (int k = blockPos.getY() - 1; k <= blockPos.getY() + 1; ++k) {
                        final BlockPos blockPos2 = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos2).getBlock();
                        if (block == Blocks.dirt || block == Blocks.clay) {
                            world.setBlockState(blockPos2, this.field_150546_a.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
