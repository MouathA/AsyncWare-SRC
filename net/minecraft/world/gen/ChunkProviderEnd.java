package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class ChunkProviderEnd implements IChunkProvider
{
    double[] noiseData2;
    public NoiseGeneratorOctaves noiseGen4;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData3;
    private NoiseGeneratorOctaves noiseGen2;
    private double[] densities;
    private NoiseGeneratorOctaves noiseGen1;
    private Random endRNG;
    double[] noiseData4;
    double[] noiseData5;
    private NoiseGeneratorOctaves noiseGen3;
    double[] noiseData1;
    private World endWorld;
    public NoiseGeneratorOctaves noiseGen5;
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.endRNG.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, n * 16, n2 * 16, 16, 16);
        this.func_180520_a(n, n2, chunkPrimer);
        this.func_180519_a(chunkPrimer);
        final Chunk chunk = new Chunk(this.endWorld, chunkPrimer, n, n2);
        final byte[] biomeArray = chunk.getBiomeArray();
        while (0 < biomeArray.length) {
            biomeArray[0] = (byte)this.biomesForGeneration[0].biomeID;
            int n3 = 0;
            ++n3;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    public ChunkProviderEnd(final World endWorld, final long n) {
        this.endWorld = endWorld;
        this.endRNG = new Random(n);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = true;
        final BlockPos blockPos = new BlockPos(n * 16, 0, n2 * 16);
        this.endWorld.getBiomeGenForCoords(blockPos.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, blockPos);
        BlockFalling.fallInstantly = false;
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.endWorld.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return false;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public String makeString() {
        return "RandomLevelSource";
    }
    
    public void func_180520_a(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        this.densities = this.initializeNoiseField(this.densities, n * 2, 0, n2 * 2, 3, 33, 3);
        final double n3 = 0.25;
        final double n4 = this.densities[0];
        final double n5 = this.densities[33];
        final double n6 = this.densities[99];
        final double n7 = this.densities[132];
        final double n8 = (this.densities[1] - n4) * n3;
        final double n9 = (this.densities[34] - n5) * n3;
        final double n10 = (this.densities[100] - n6) * n3;
        final double n11 = (this.densities[133] - n7) * n3;
        final double n12 = n4;
        final double n13 = n5;
        final double n14 = 0.125;
        double n15 = n12;
        final double n16 = (n13 - n12) * n14;
        while (true) {
            IBlockState defaultState = null;
            if (n15 > 0.0) {
                defaultState = Blocks.end_stone.getDefaultState();
            }
            chunkPrimer.setBlockState(0, 0, 0, defaultState);
            n15 += n16;
            int n17 = 0;
            ++n17;
        }
    }
    
    private double[] initializeNoiseField(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        final double n7 = 684.412;
        final double n8 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, n, n3, n4, n6, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, n, n3, n4, n6, 200.0, 200.0, 0.5);
        final double n9 = n7 * 2.0;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, n, n2, n3, n4, n5, n6, n9 / 80.0, n8 / 160.0, n9 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, n, n2, n3, n4, n5, n6, n9, n8, n9);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, n, n2, n3, n4, n5, n6, n9, n8, n9);
        while (0 < n4) {
            while (0 < n6) {
                final float n10 = (0 + n) / 1.0f;
                final float n11 = (0 + n3) / 1.0f;
                float n12 = 100.0f - MathHelper.sqrt_float(n10 * n10 + n11 * n11) * 8.0f;
                if (n12 > 80.0f) {
                    n12 = 80.0f;
                }
                if (n12 < -100.0f) {
                    n12 = -100.0f;
                }
                while (0 < n5) {
                    final double n13 = this.noiseData2[0] / 512.0;
                    final double n14 = this.noiseData3[0] / 512.0;
                    final double n15 = (this.noiseData1[0] / 10.0 + 1.0) / 2.0;
                    double n16;
                    if (n15 < 0.0) {
                        n16 = n13;
                    }
                    else if (n15 > 1.0) {
                        n16 = n14;
                    }
                    else {
                        n16 = n13 + (n14 - n13) * n15;
                    }
                    double n17 = n16 - 8.0 + n12;
                    if (0 > n5 / 2 - 8) {
                        final double clamp_double = MathHelper.clamp_double((0 - (n5 / 2 - 8)) / 64.0f, 0.0, 1.0);
                        n17 = n17 * (1.0 - clamp_double) + -3000.0 * clamp_double;
                    }
                    final double n18 = 8 / (8 - 1.0f);
                    array[0] = n17 * (1.0 - n18) + -30.0 * n18;
                    int n19 = 0;
                    ++n19;
                    int n20 = 0;
                    ++n20;
                }
                int n21 = 0;
                ++n21;
            }
            int n22 = 0;
            ++n22;
        }
        return array;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    public void func_180519_a(final ChunkPrimer chunkPrimer) {
        final IBlockState defaultState = Blocks.end_stone.getDefaultState();
        Blocks.end_stone.getDefaultState();
        while (true) {
            final IBlockState blockState = chunkPrimer.getBlockState(0, 127, 0);
            if (blockState.getBlock().getMaterial() != Material.air) {
                if (blockState.getBlock() == Blocks.stone) {
                    chunkPrimer.setBlockState(0, 127, 0, defaultState);
                }
            }
            int n = 0;
            --n;
        }
    }
}
