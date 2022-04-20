package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenLakes extends WorldGenerator
{
    private Block block;
    
    public WorldGenLakes(final Block block) {
        this.block = block;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        for (blockPos = blockPos.add(-8, 0, -8); blockPos.getY() > 5 && world.isAirBlock(blockPos); blockPos = blockPos.down()) {}
        if (blockPos.getY() <= 4) {
            return false;
        }
        blockPos = blockPos.down(4);
        final boolean[] array = new boolean[2048];
        if (0 >= random.nextInt(4) + 4) {
            while (array[4] || (!array[132] && !array[12] && !array[5] && !array[3]) || !world.getBlockState(blockPos.add(0, 4, 0)).getBlock().getMaterial().isLiquid()) {
                int n = 0;
                ++n;
            }
            return false;
        }
        final double n2 = random.nextDouble() * 6.0 + 3.0;
        final double n3 = random.nextDouble() * 4.0 + 2.0;
        final double n4 = random.nextDouble() * 6.0 + 3.0;
        final double n5 = random.nextDouble() * (16.0 - n2 - 2.0) + 1.0 + n2 / 2.0;
        final double n6 = random.nextDouble() * (8.0 - n3 - 4.0) + 2.0 + n3 / 2.0;
        final double n7 = random.nextDouble() * (16.0 - n4 - 2.0) + 1.0 + n4 / 2.0;
        while (true) {
            final double n8 = (1 - n5) / (n2 / 2.0);
            final double n9 = (1 - n6) / (n3 / 2.0);
            final double n10 = (1 - n7) / (n4 / 2.0);
            if (n8 * n8 + n9 * n9 + n10 * n10 < 1.0) {
                array[137] = true;
            }
            int n11 = 0;
            ++n11;
        }
    }
}
