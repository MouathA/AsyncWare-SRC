package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class WorldGenMegaJungle extends WorldGenHugeTrees
{
    private void func_181632_a(final World world, final Random random, final BlockPos blockPos, final PropertyBool propertyBool) {
        if (random.nextInt(3) > 0 && world.isAirBlock(blockPos)) {
            this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty(propertyBool, true));
        }
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int func_150533_a = this.func_150533_a(random);
        if (!this.func_175929_a(world, random, blockPos, func_150533_a)) {
            return false;
        }
        this.func_175930_c(world, blockPos.up(func_150533_a), 2);
        int n = blockPos.getY() + func_150533_a - 2 - random.nextInt(4);
        if (0 <= blockPos.getY() + func_150533_a / 2) {
            while (0 < func_150533_a) {
                final BlockPos up = blockPos.up(0);
                if (this.func_150523_a(world.getBlockState(up).getBlock())) {
                    this.setBlockAndNotifyAdequately(world, up, this.woodMetadata);
                }
                if (0 < func_150533_a - 1) {
                    final BlockPos east = up.east();
                    if (this.func_150523_a(world.getBlockState(east).getBlock())) {
                        this.setBlockAndNotifyAdequately(world, east, this.woodMetadata);
                    }
                    final BlockPos east2 = up.south().east();
                    if (this.func_150523_a(world.getBlockState(east2).getBlock())) {
                        this.setBlockAndNotifyAdequately(world, east2, this.woodMetadata);
                    }
                    final BlockPos south = up.south();
                    if (this.func_150523_a(world.getBlockState(south).getBlock())) {
                        this.setBlockAndNotifyAdequately(world, south, this.woodMetadata);
                    }
                }
                ++n;
            }
            return true;
        }
        final float n2 = random.nextFloat() * 3.1415927f * 2.0f;
        final int n3 = blockPos.getX() + (int)(0.5f + MathHelper.cos(n2) * 4.0f);
        final int n4 = blockPos.getZ() + (int)(0.5f + MathHelper.sin(n2) * 4.0f);
        while (true) {
            this.setBlockAndNotifyAdequately(world, new BlockPos(blockPos.getX() + (int)(1.5f + MathHelper.cos(n2) * 0), -3, blockPos.getZ() + (int)(1.5f + MathHelper.sin(n2) * 0)), this.woodMetadata);
            int n5 = 0;
            ++n5;
        }
    }
    
    private void func_175930_c(final World world, final BlockPos blockPos, final int n) {
        while (true) {
            this.func_175925_a(world, blockPos.up(-2), n + 1 + 2);
            int n2 = 0;
            ++n2;
        }
    }
    
    public WorldGenMegaJungle(final boolean b, final int n, final int n2, final IBlockState blockState, final IBlockState blockState2) {
        super(b, n, n2, blockState, blockState2);
    }
}
