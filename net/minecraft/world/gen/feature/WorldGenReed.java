package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenReed extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
            if (world.isAirBlock(add)) {
                final BlockPos down = add.down();
                if (world.getBlockState(down.west()).getBlock().getMaterial() == Material.water || world.getBlockState(down.east()).getBlock().getMaterial() == Material.water || world.getBlockState(down.north()).getBlock().getMaterial() == Material.water || world.getBlockState(down.south()).getBlock().getMaterial() == Material.water) {
                    while (0 < 2 + random.nextInt(random.nextInt(3) + 1)) {
                        if (Blocks.reeds.canBlockStay(world, add)) {
                            world.setBlockState(add.up(0), Blocks.reeds.getDefaultState(), 2);
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
}
