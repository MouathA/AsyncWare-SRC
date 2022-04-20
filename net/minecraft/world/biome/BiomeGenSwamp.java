package net.minecraft.world.biome;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.material.*;

public class BiomeGenSwamp extends BiomeGenBase
{
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return (BiomeGenSwamp.GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() * 0.0225, blockPos.getZ() * 0.0225) < -0.1) ? 5011004 : 6975545;
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.worldGeneratorSwamp;
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return 6975545;
    }
    
    protected BiomeGenSwamp(final int n) {
        super(n);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 1;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.theBiomeDecorator.sandPerChunk2 = 0;
        this.theBiomeDecorator.sandPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 5;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (BiomeGenSwamp.GRASS_COLOR_NOISE.func_151601_a(n * 0.25, n2 * 0.25) > 0.0) {
            while (chunkPrimer.getBlockState(n2 & 0xF, 255, n & 0xF).getBlock().getMaterial() == Material.air) {
                int n4 = 0;
                --n4;
            }
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
}
