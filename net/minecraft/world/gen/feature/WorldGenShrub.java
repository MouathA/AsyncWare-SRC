package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenShrub extends WorldGenTrees
{
    private final IBlockState woodMetadata;
    private final IBlockState leavesMetadata;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && blockPos.getY() > 0) {
            blockPos = blockPos.down();
        }
        final Block block2 = world.getBlockState(blockPos).getBlock();
        if (block2 == Blocks.dirt || block2 == Blocks.grass) {
            blockPos = blockPos.up();
            this.setBlockAndNotifyAdequately(world, blockPos, this.woodMetadata);
            for (int i = blockPos.getY(); i <= blockPos.getY() + 2; ++i) {
                for (int n = 2 - (i - blockPos.getY()), j = blockPos.getX() - n; j <= blockPos.getX() + n; ++j) {
                    final int n2 = j - blockPos.getX();
                    for (int k = blockPos.getZ() - n; k <= blockPos.getZ() + n; ++k) {
                        final int n3 = k - blockPos.getZ();
                        if (Math.abs(n2) != n || Math.abs(n3) != n || random.nextInt(2) != 0) {
                            final BlockPos blockPos2 = new BlockPos(j, i, k);
                            if (!world.getBlockState(blockPos2).getBlock().isFullBlock()) {
                                this.setBlockAndNotifyAdequately(world, blockPos2, this.leavesMetadata);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public WorldGenShrub(final IBlockState woodMetadata, final IBlockState leavesMetadata) {
        super(false);
        this.woodMetadata = woodMetadata;
        this.leavesMetadata = leavesMetadata;
    }
}
