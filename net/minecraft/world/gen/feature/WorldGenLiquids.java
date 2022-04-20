package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class WorldGenLiquids extends WorldGenerator
{
    private Block block;
    
    public WorldGenLiquids(final Block block) {
        this.block = block;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock() != Blocks.stone) {
            return false;
        }
        if (world.getBlockState(blockPos.down()).getBlock() != Blocks.stone) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.air && world.getBlockState(blockPos).getBlock() != Blocks.stone) {
            return false;
        }
        int n = 0;
        if (world.getBlockState(blockPos.west()).getBlock() == Blocks.stone) {
            ++n;
        }
        if (world.getBlockState(blockPos.east()).getBlock() == Blocks.stone) {
            ++n;
        }
        if (world.getBlockState(blockPos.north()).getBlock() == Blocks.stone) {
            ++n;
        }
        if (world.getBlockState(blockPos.south()).getBlock() == Blocks.stone) {
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
        return true;
    }
}
