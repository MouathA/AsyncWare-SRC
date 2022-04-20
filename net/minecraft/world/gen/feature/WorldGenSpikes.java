package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class WorldGenSpikes extends WorldGenerator
{
    private Block baseBlockRequired;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.isAirBlock(blockPos) && world.getBlockState(blockPos.down()).getBlock() == this.baseBlockRequired) {
            final int n = random.nextInt(32) + 6;
            final int n2 = random.nextInt(4) + 1;
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = blockPos.getX() - n2; i <= blockPos.getX() + n2; ++i) {
                for (int j = blockPos.getZ() - n2; j <= blockPos.getZ() + n2; ++j) {
                    final int n3 = i - blockPos.getX();
                    final int n4 = j - blockPos.getZ();
                    if (n3 * n3 + n4 * n4 <= n2 * n2 + 1 && world.getBlockState(mutableBlockPos.func_181079_c(i, blockPos.getY() - 1, j)).getBlock() != this.baseBlockRequired) {
                        return false;
                    }
                }
            }
            for (int y = blockPos.getY(); y < blockPos.getY() + n && y < 256; ++y) {
                for (int k = blockPos.getX() - n2; k <= blockPos.getX() + n2; ++k) {
                    for (int l = blockPos.getZ() - n2; l <= blockPos.getZ() + n2; ++l) {
                        final int n5 = k - blockPos.getX();
                        final int n6 = l - blockPos.getZ();
                        if (n5 * n5 + n6 * n6 <= n2 * n2 + 1) {
                            world.setBlockState(new BlockPos(k, y, l), Blocks.obsidian.getDefaultState(), 2);
                        }
                    }
                }
            }
            final EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(world);
            entityEnderCrystal.setLocationAndAngles(blockPos.getX() + 0.5f, blockPos.getY() + n, blockPos.getZ() + 0.5f, random.nextFloat() * 360.0f, 0.0f);
            world.spawnEntityInWorld(entityEnderCrystal);
            world.setBlockState(blockPos.up(n), Blocks.bedrock.getDefaultState(), 2);
            return true;
        }
        return false;
    }
    
    public WorldGenSpikes(final Block baseBlockRequired) {
        this.baseBlockRequired = baseBlockRequired;
    }
}
