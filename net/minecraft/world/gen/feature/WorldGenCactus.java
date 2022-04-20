package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenCactus extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add)) {
                while (0 < 1 + random.nextInt(random.nextInt(3) + 1)) {
                    if (Blocks.cactus.canBlockStay(world, add)) {
                        world.setBlockState(add.up(0), Blocks.cactus.getDefaultState(), 2);
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
}
