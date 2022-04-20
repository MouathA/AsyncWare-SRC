package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenFire extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add) && world.getBlockState(add.down()).getBlock() == Blocks.netherrack) {
                world.setBlockState(add, Blocks.fire.getDefaultState(), 2);
            }
            int n = 0;
            ++n;
        }
    }
}
