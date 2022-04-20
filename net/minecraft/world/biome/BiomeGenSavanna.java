package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeGenSavanna extends BiomeGenBase
{
    private static final WorldGenSavannaTree field_150627_aC;
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final Mutated mutated = new Mutated(n, this);
        mutated.temperature = (this.temperature + 1.0f) * 0.5f;
        mutated.minHeight = this.minHeight * 0.5f + 0.3f;
        mutated.maxHeight = this.maxHeight * 0.5f + 1.2f;
        return mutated;
    }
    
    static {
        field_150627_aC = new WorldGenSavannaTree(false);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (random.nextInt(5) > 0) ? BiomeGenSavanna.field_150627_aC : this.worldGeneratorTrees;
    }
    
    protected BiomeGenSavanna(final int n) {
        super(n);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        BiomeGenSavanna.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
        while (true) {
            final int n = random.nextInt(16) + 8;
            final int n2 = random.nextInt(16) + 8;
            BiomeGenSavanna.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, 0, n2)).getY() + 32), n2));
            int n3 = 0;
            ++n3;
        }
    }
    
    public static class Mutated extends BiomeGenMutated
    {
        public Mutated(final int n, final BiomeGenBase biomeGenBase) {
            super(n, biomeGenBase);
            this.theBiomeDecorator.treesPerChunk = 2;
            this.theBiomeDecorator.flowersPerChunk = 2;
            this.theBiomeDecorator.grassPerChunk = 5;
        }
        
        @Override
        public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.stone.getDefaultState();
                this.fillerBlock = Blocks.stone.getDefaultState();
            }
            else if (n3 > -0.5) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
        }
        
        @Override
        public void decorate(final World world, final Random random, final BlockPos blockPos) {
            this.theBiomeDecorator.decorate(world, random, this, blockPos);
        }
    }
}
