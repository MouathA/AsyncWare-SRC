package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;
import com.google.common.collect.*;

public class BiomeGenMutated extends BiomeGenBase
{
    protected BiomeGenBase baseBiome;
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.baseBiome.genTerrainBlocks(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return this.baseBiome.getFoliageColorAtPos(blockPos);
    }
    
    @Override
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        return this.baseBiome.isEqualTo(biomeGenBase);
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        this.baseBiome.theBiomeDecorator.decorate(world, random, this, blockPos);
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return this.baseBiome.getGrassColorAtPos(blockPos);
    }
    
    @Override
    public float getSpawningChance() {
        return this.baseBiome.getSpawningChance();
    }
    
    @Override
    public Class getBiomeClass() {
        return this.baseBiome.getBiomeClass();
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.baseBiome.genBigTreeChance(random);
    }
    
    @Override
    public TempCategory getTempCategory() {
        return this.baseBiome.getTempCategory();
    }
    
    public BiomeGenMutated(final int n, final BiomeGenBase baseBiome) {
        super(n);
        this.baseBiome = baseBiome;
        this.func_150557_a(baseBiome.color, true);
        this.biomeName = baseBiome.biomeName + " M";
        this.topBlock = baseBiome.topBlock;
        this.fillerBlock = baseBiome.fillerBlock;
        this.fillerBlockMetadata = baseBiome.fillerBlockMetadata;
        this.minHeight = baseBiome.minHeight;
        this.maxHeight = baseBiome.maxHeight;
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.waterColorMultiplier = baseBiome.waterColorMultiplier;
        this.enableSnow = baseBiome.enableSnow;
        this.enableRain = baseBiome.enableRain;
        this.spawnableCreatureList = Lists.newArrayList((Iterable)baseBiome.spawnableCreatureList);
        this.spawnableMonsterList = Lists.newArrayList((Iterable)baseBiome.spawnableMonsterList);
        this.spawnableCaveCreatureList = Lists.newArrayList((Iterable)baseBiome.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = Lists.newArrayList((Iterable)baseBiome.spawnableWaterCreatureList);
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.minHeight = baseBiome.minHeight + 0.1f;
        this.maxHeight = baseBiome.maxHeight + 0.2f;
    }
}
