package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class WorldGenVines extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        while (blockPos.getY() < 128) {
            if (world.isAirBlock(blockPos)) {
                final EnumFacing[] facings = EnumFacing.Plane.HORIZONTAL.facings();
                while (0 < facings.length) {
                    final EnumFacing enumFacing = facings[0];
                    if (Blocks.vine.canPlaceBlockOnSide(world, blockPos, enumFacing)) {
                        world.setBlockState(blockPos, Blocks.vine.getDefaultState().withProperty(BlockVine.NORTH, enumFacing == EnumFacing.NORTH).withProperty(BlockVine.EAST, enumFacing == EnumFacing.EAST).withProperty(BlockVine.SOUTH, enumFacing == EnumFacing.SOUTH).withProperty(BlockVine.WEST, enumFacing == EnumFacing.WEST), 2);
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            else {
                blockPos = blockPos.add(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
            }
            blockPos = blockPos.up();
        }
        return true;
    }
}
