package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;

public class WorldGenBlockBlob extends WorldGenerator
{
    private final int field_150544_b;
    private final Block field_150545_a;
    
    public WorldGenBlockBlob(final Block field_150545_a, final int field_150544_b) {
        super(false);
        this.field_150545_a = field_150545_a;
        this.field_150544_b = field_150544_b;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        while (blockPos.getY() > 3) {
            if (!world.isAirBlock(blockPos.down())) {
                final Block block = world.getBlockState(blockPos.down()).getBlock();
                if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone) {
                    break;
                }
            }
            blockPos = blockPos.down();
        }
        if (blockPos.getY() <= 3) {
            return false;
        }
        final int i = this.field_150544_b;
        while (i >= 0) {
            final int n = i + random.nextInt(2);
            final int n2 = i + random.nextInt(2);
            final int n3 = i + random.nextInt(2);
            final float n4 = (n + n2 + n3) * 0.333f + 0.5f;
            for (final BlockPos blockPos2 : BlockPos.getAllInBox(blockPos.add(-n, -n2, -n3), blockPos.add(n, n2, n3))) {
                if (blockPos2.distanceSq(blockPos) <= n4 * n4) {
                    world.setBlockState(blockPos2, this.field_150545_a.getDefaultState(), 4);
                }
            }
            blockPos = blockPos.add(-(i + 1) + random.nextInt(2 + i * 2), 0 - random.nextInt(2), -(i + 1) + random.nextInt(2 + i * 2));
            int n5 = 0;
            ++n5;
        }
        return true;
    }
}
