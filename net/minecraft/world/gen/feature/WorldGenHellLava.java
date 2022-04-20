package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class WorldGenHellLava extends WorldGenerator
{
    private final boolean field_94524_b;
    private final Block field_150553_a;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock() != Blocks.netherrack) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.air && world.getBlockState(blockPos).getBlock() != Blocks.netherrack) {
            return false;
        }
        int n = 0;
        if (world.getBlockState(blockPos.west()).getBlock() == Blocks.netherrack) {
            ++n;
        }
        if (world.getBlockState(blockPos.east()).getBlock() == Blocks.netherrack) {
            ++n;
        }
        if (world.getBlockState(blockPos.north()).getBlock() == Blocks.netherrack) {
            ++n;
        }
        if (world.getBlockState(blockPos.south()).getBlock() == Blocks.netherrack) {
            ++n;
        }
        if (world.getBlockState(blockPos.down()).getBlock() == Blocks.netherrack) {
            ++n;
        }
        int n2 = 0;
        if (world.isAirBlock(blockPos.west())) {
            ++n2;
        }
        if (world.isAirBlock(blockPos.east())) {
            ++n2;
        }
        if (world.isAirBlock(blockPos.north())) {
            ++n2;
        }
        if (world.isAirBlock(blockPos.south())) {
            ++n2;
        }
        if (world.isAirBlock(blockPos.down())) {
            ++n2;
        }
        if (!this.field_94524_b) {}
        return true;
    }
    
    public WorldGenHellLava(final Block field_150553_a, final boolean field_94524_b) {
        this.field_150553_a = field_150553_a;
        this.field_94524_b = field_94524_b;
    }
}
