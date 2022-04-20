package net.minecraft.world.gen.feature;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;

public class WorldGenMinable extends WorldGenerator
{
    private final Predicate predicate;
    private final int numberOfBlocks;
    private final IBlockState oreBlock;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final float n = random.nextFloat() * 3.1415927f;
        final double n2 = blockPos.getX() + 8 + MathHelper.sin(n) * this.numberOfBlocks / 8.0f;
        final double n3 = blockPos.getX() + 8 - MathHelper.sin(n) * this.numberOfBlocks / 8.0f;
        final double n4 = blockPos.getZ() + 8 + MathHelper.cos(n) * this.numberOfBlocks / 8.0f;
        final double n5 = blockPos.getZ() + 8 - MathHelper.cos(n) * this.numberOfBlocks / 8.0f;
        final double n6 = blockPos.getY() + random.nextInt(3) - 2;
        final double n7 = blockPos.getY() + random.nextInt(3) - 2;
        while (0 < this.numberOfBlocks) {
            final float n8 = 0 / (float)this.numberOfBlocks;
            final double n9 = n2 + (n3 - n2) * n8;
            final double n10 = n6 + (n7 - n6) * n8;
            final double n11 = n4 + (n5 - n4) * n8;
            final double n12 = random.nextDouble() * this.numberOfBlocks / 16.0;
            final double n13 = (MathHelper.sin(3.1415927f * n8) + 1.0f) * n12 + 1.0;
            final double n14 = (MathHelper.sin(3.1415927f * n8) + 1.0f) * n12 + 1.0;
            final int floor_double = MathHelper.floor_double(n9 - n13 / 2.0);
            final int floor_double2 = MathHelper.floor_double(n10 - n14 / 2.0);
            final int floor_double3 = MathHelper.floor_double(n11 - n13 / 2.0);
            final int floor_double4 = MathHelper.floor_double(n9 + n13 / 2.0);
            final int floor_double5 = MathHelper.floor_double(n10 + n14 / 2.0);
            final int floor_double6 = MathHelper.floor_double(n11 + n13 / 2.0);
            for (int i = floor_double; i <= floor_double4; ++i) {
                final double n15 = (i + 0.5 - n9) / (n13 / 2.0);
                if (n15 * n15 < 1.0) {
                    for (int j = floor_double2; j <= floor_double5; ++j) {
                        final double n16 = (j + 0.5 - n10) / (n14 / 2.0);
                        if (n15 * n15 + n16 * n16 < 1.0) {
                            for (int k = floor_double3; k <= floor_double6; ++k) {
                                final double n17 = (k + 0.5 - n11) / (n13 / 2.0);
                                if (n15 * n15 + n16 * n16 + n17 * n17 < 1.0) {
                                    final BlockPos blockPos2 = new BlockPos(i, j, k);
                                    if (this.predicate.apply((Object)world.getBlockState(blockPos2))) {
                                        world.setBlockState(blockPos2, this.oreBlock, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int n18 = 0;
            ++n18;
        }
        return true;
    }
    
    public WorldGenMinable(final IBlockState blockState, final int n) {
        this(blockState, n, (Predicate)BlockHelper.forBlock(Blocks.stone));
    }
    
    public WorldGenMinable(final IBlockState oreBlock, final int numberOfBlocks, final Predicate predicate) {
        this.oreBlock = oreBlock;
        this.numberOfBlocks = numberOfBlocks;
        this.predicate = predicate;
    }
}
