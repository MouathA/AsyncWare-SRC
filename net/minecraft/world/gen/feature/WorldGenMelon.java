package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenMelon extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (Blocks.melon_block.canPlaceBlockAt(world, add) && world.getBlockState(add.down()).getBlock() == Blocks.grass) {
                world.setBlockState(add, Blocks.melon_block.getDefaultState(), 2);
            }
            int n = 0;
            ++n;
        }
    }
}
