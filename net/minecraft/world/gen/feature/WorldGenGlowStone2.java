package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class WorldGenGlowStone2 extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos)) {
            return false;
        }
        if (world.getBlockState(blockPos.up()).getBlock() != Blocks.netherrack) {
            return false;
        }
        world.setBlockState(blockPos, Blocks.glowstone.getDefaultState(), 2);
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
            if (world.getBlockState(add).getBlock().getMaterial() == Material.air) {
                final EnumFacing[] values = EnumFacing.values();
                while (0 < values.length) {
                    if (world.getBlockState(add.offset(values[0])).getBlock() == Blocks.glowstone) {
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            int n3 = 0;
            ++n3;
        }
    }
}
