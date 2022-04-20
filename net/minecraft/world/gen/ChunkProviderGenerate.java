package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;

public class ChunkProviderGenerate implements IChunkProvider
{
    double[] field_147426_g;
    private Random rand;
    private MapGenMineshaft mineshaftGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private NoiseGeneratorOctaves field_147431_j;
    private StructureOceanMonument oceanMonumentGenerator;
    double[] field_147427_d;
    private WorldType field_177475_o;
    private World worldObj;
    private final float[] parabolicField;
    private ChunkProviderSettings settings;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private NoiseGeneratorOctaves field_147432_k;
    private MapGenBase ravineGenerator;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves noiseGen5;
    private MapGenBase caveGenerator;
    private final boolean mapFeaturesEnabled;
    private BiomeGenBase[] biomesForGeneration;
    private Block field_177476_s;
    private MapGenVillage villageGenerator;
    double[] field_147428_e;
    private NoiseGeneratorOctaves field_147429_l;
    private final double[] field_147434_q;
    private double[] stoneNoise;
    double[] field_147425_f;
    private MapGenStronghold strongholdGenerator;
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        if (this.settings.useMonuments && this.mapFeaturesEnabled && chunk.getInhabitedTime() < 3600L) {
            final boolean b = false | this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkCoordIntPair(n, n2));
        }
        return false;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    @Override
    public String makeString() {
        return "RandomLevelSource";
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return ("Stronghold".equals(s) && this.strongholdGenerator != null) ? this.strongholdGenerator.getClosestStrongholdPos(world, blockPos) : null;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.rand.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.setBlocksInChunk(n, n2, chunkPrimer);
        this.replaceBlocksForBiome(n, n2, chunkPrimer, this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, n * 16, n2 * 16, 16, 16));
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useRavines) {
            this.ravineGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final byte[] biomeArray = chunk.getBiomeArray();
        while (0 < biomeArray.length) {
            biomeArray[0] = (byte)this.biomesForGeneration[0].biomeID;
            int n3 = 0;
            ++n3;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    public void replaceBlocksForBiome(final int n, final int n2, final ChunkPrimer chunkPrimer, final BiomeGenBase[] array) {
        final double n3 = 0.03125;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, n * 16, n2 * 16, 16, 16, n3 * 2.0, n3 * 2.0, 1.0);
        while (true) {
            array[0].genTerrainBlocks(this.worldObj, this.rand, chunkPrimer, n * 16 + 0, n2 * 16 + 0, this.stoneNoise[0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public void setBlocksInChunk(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, n * 4 - 2, n2 * 4 - 2, 10, 10);
        this.func_147423_a(n * 4, 0, n2 * 4);
        final double n3 = 0.125;
        final double n4 = this.field_147434_q[0];
        final double n5 = this.field_147434_q[33];
        final double n6 = this.field_147434_q[165];
        final double n7 = this.field_147434_q[198];
        final double n8 = (this.field_147434_q[1] - n4) * n3;
        final double n9 = (this.field_147434_q[34] - n5) * n3;
        final double n10 = (this.field_147434_q[166] - n6) * n3;
        final double n11 = (this.field_147434_q[199] - n7) * n3;
        final double n12 = n4;
        final double n13 = (n5 - n12) * 0.25;
        double n14 = n12 - n13;
        while (true) {
            if ((n14 += n13) > 0.0) {
                chunkPrimer.setBlockState(0, 0, 0, Blocks.stone.getDefaultState());
            }
            else if (0 < this.settings.seaLevel) {
                chunkPrimer.setBlockState(0, 0, 0, this.field_177476_s.getDefaultState());
            }
            int n15 = 0;
            ++n15;
        }
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, n, n2, null);
        }
    }
    
    private void func_147423_a(final int n, final int n2, final int n3) {
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, 0, 0, 5, 5, this.settings.depthNoiseScaleX, this.settings.depthNoiseScaleZ, this.settings.depthNoiseScaleExponent);
        final float coordinateScale = this.settings.coordinateScale;
        final float heightScale = this.settings.heightScale;
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, 0, n2, 0, 5, 33, 5, coordinateScale / this.settings.mainNoiseScaleX, heightScale / this.settings.mainNoiseScaleY, coordinateScale / this.settings.mainNoiseScaleZ);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, 0, n2, 0, 5, 33, 5, coordinateScale, heightScale, coordinateScale);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, 0, n2, 0, 5, 33, 5, coordinateScale, heightScale, coordinateScale);
        float n4 = 0.0f;
        float n5 = 0.0f;
        float n6 = 0.0f;
        final BiomeGenBase biomeGenBase = this.biomesForGeneration[22];
        while (true) {
            final BiomeGenBase biomeGenBase2 = this.biomesForGeneration[0];
            float n7 = this.settings.biomeDepthOffSet + biomeGenBase2.minHeight * this.settings.biomeDepthWeight;
            float n8 = this.settings.biomeScaleOffset + biomeGenBase2.maxHeight * this.settings.biomeScaleWeight;
            if (this.field_177475_o == WorldType.AMPLIFIED && n7 > 0.0f) {
                n7 = 1.0f + n7 * 2.0f;
                n8 = 1.0f + n8 * 4.0f;
            }
            float n9 = this.parabolicField[0] / (n7 + 2.0f);
            if (biomeGenBase2.minHeight > biomeGenBase.minHeight) {
                n9 /= 2.0f;
            }
            n4 += n8 * n9;
            n5 += n7 * n9;
            n6 += n9;
            int n10 = 0;
            ++n10;
        }
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    public ChunkProviderGenerate(final World worldObj, final long n, final boolean mapFeaturesEnabled, final String s) {
        this.field_177476_s = Blocks.water;
        this.stoneNoise = new double[256];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.oceanMonumentGenerator = new StructureOceanMonument();
        this.worldObj = worldObj;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.field_177475_o = worldObj.getWorldInfo().getTerrainType();
        this.rand = new Random(n);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];
        while (true) {
            this.parabolicField[0] = 10.0f / MathHelper.sqrt_float(8 + 0.2f);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = true;
        final int n3 = n * 16;
        final int n4 = n2 * 16;
        final BlockPos blockPos = new BlockPos(n3, 0, n4);
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos.add(16, 0, 16));
        this.rand.setSeed(this.worldObj.getSeed());
        this.rand.setSeed(n * (this.rand.nextLong() / 2L * 2L + 1L) + n2 * (this.rand.nextLong() / 2L * 2L + 1L) ^ this.worldObj.getSeed());
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(n, n2);
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        int n5 = 0;
        int n6 = 0;
        if (biomeGenForCoords != BiomeGenBase.desert && biomeGenForCoords != BiomeGenBase.desertHills && this.settings.useWaterLakes && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
            n5 = this.rand.nextInt(16) + 8;
            n6 = this.rand.nextInt(256);
            new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, blockPos.add(0, 0, this.rand.nextInt(16) + 8));
        }
        if (this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
            n5 = this.rand.nextInt(16) + 8;
            n6 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            final int n7 = this.rand.nextInt(16) + 8;
            if (0 < this.worldObj.func_181545_F() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
                new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, blockPos.add(0, 0, n7));
            }
        }
        if (this.settings.useDungeons) {
            while (0 < this.settings.dungeonChance) {
                n6 = this.rand.nextInt(16) + 8;
                new WorldGenDungeons().generate(this.worldObj, this.rand, blockPos.add(0, this.rand.nextInt(256), this.rand.nextInt(16) + 8));
                ++n5;
            }
        }
        biomeGenForCoords.decorate(this.worldObj, this.rand, new BlockPos(n3, 0, n4));
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomeGenForCoords, n3 + 8, n4 + 8, 16, 16, this.rand);
        final BlockPos add = blockPos.add(8, 0, 8);
        while (true) {
            final BlockPos precipitationHeight = this.worldObj.getPrecipitationHeight(add.add(0, 0, 0));
            final BlockPos down = precipitationHeight.down();
            if (this.worldObj.canBlockFreezeWater(down)) {
                this.worldObj.setBlockState(down, Blocks.ice.getDefaultState(), 2);
            }
            if (this.worldObj.canSnowAt(precipitationHeight, true)) {
                this.worldObj.setBlockState(precipitationHeight, Blocks.snow_layer.getDefaultState(), 2);
            }
            ++n6;
        }
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos);
        if (this.mapFeaturesEnabled) {
            if (enumCreatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(blockPos)) {
                return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
            }
            if (enumCreatureType == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.func_175796_a(this.worldObj, blockPos)) {
                return this.oceanMonumentGenerator.func_175799_b();
            }
        }
        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }
}
