package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenWaterlily extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final int n = blockPos.getX() + random.nextInt(8) - random.nextInt(8);
            final int n2 = blockPos.getY() + random.nextInt(4) - random.nextInt(4);
            final int n3 = blockPos.getZ() + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(new BlockPos(n, n2, n3)) && Blocks.waterlily.canPlaceBlockAt(world, new BlockPos(n, n2, n3))) {
                world.setBlockState(new BlockPos(n, n2, n3), Blocks.waterlily.getDefaultState(), 2);
            }
            int n4 = 0;
            ++n4;
        }
    }
}
