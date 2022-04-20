package net.minecraft.world.gen;

import net.minecraft.world.gen.structure.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.world.gen.feature.*;
import com.google.common.base.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;

public class ChunkProviderHell implements IChunkProvider
{
    private final WorldGenerator field_177467_w;
    private final WorldGenGlowStone1 field_177469_u;
    private final WorldGenFire field_177470_t;
    private final NoiseGeneratorOctaves netherNoiseGen1;
    double[] noiseData4;
    private double[] netherrackExclusivityNoise;
    public final NoiseGeneratorOctaves netherNoiseGen6;
    private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private final World worldObj;
    private final boolean field_177466_i;
    double[] noiseData3;
    private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    private final Random hellRNG;
    private double[] slowsandNoise;
    private final GeneratorBushFeature field_177471_z;
    private final MapGenNetherBridge genNetherBridge;
    private final NoiseGeneratorOctaves netherNoiseGen2;
    double[] noiseData5;
    private double[] gravelNoise;
    private final WorldGenHellLava field_177473_x;
    private final WorldGenGlowStone2 field_177468_v;
    private double[] noiseField;
    double[] noiseData2;
    private final NoiseGeneratorOctaves netherNoiseGen3;
    public final NoiseGeneratorOctaves netherNoiseGen7;
    private final GeneratorBushFeature field_177465_A;
    double[] noiseData1;
    private final MapGenBase netherCaveGenerator;
    private final WorldGenHellLava field_177472_y;
    
    public void func_180515_a(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int n3 = this.worldObj.func_181545_F() / 2 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, n * 4, 0, n2 * 4, 5, 17, 5);
        final double n4 = 0.125;
        final double n5 = this.noiseField[0];
        final double n6 = this.noiseField[17];
        final double n7 = this.noiseField[85];
        final double n8 = this.noiseField[102];
        final double n9 = (this.noiseField[1] - n5) * n4;
        final double n10 = (this.noiseField[18] - n6) * n4;
        final double n11 = (this.noiseField[86] - n7) * n4;
        final double n12 = (this.noiseField[103] - n8) * n4;
        final double n13 = n5;
        final double n14 = n6;
        final double n15 = 0.25;
        double n16 = n13;
        final double n17 = (n14 - n13) * n15;
        while (true) {
            IBlockState blockState = null;
            if (0 < n3) {
                blockState = Blocks.lava.getDefaultState();
            }
            if (n16 > 0.0) {
                blockState = Blocks.netherrack.getDefaultState();
            }
            chunkPrimer.setBlockState(0, 0, 0, blockState);
            n16 += n17;
            int n18 = 0;
            ++n18;
        }
    }
    
    public ChunkProviderHell(final World worldObj, final boolean field_177466_i, final long n) {
        this.slowsandNoise = new double[256];
        this.gravelNoise = new double[256];
        this.netherrackExclusivityNoise = new double[256];
        this.field_177470_t = new WorldGenFire();
        this.field_177469_u = new WorldGenGlowStone1();
        this.field_177468_v = new WorldGenGlowStone2();
        this.field_177467_w = new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14, (Predicate)BlockHelper.forBlock(Blocks.netherrack));
        this.field_177473_x = new WorldGenHellLava(Blocks.flowing_lava, true);
        this.field_177472_y = new WorldGenHellLava(Blocks.flowing_lava, false);
        this.field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
        this.genNetherBridge = new MapGenNetherBridge();
        this.netherCaveGenerator = new MapGenCavesHell();
        this.worldObj = worldObj;
        this.field_177466_i = field_177466_i;
        this.hellRNG = new Random(n);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        worldObj.func_181544_b(63);
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        this.genNetherBridge.generate(this, this.worldObj, n, n2, null);
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        if (enumCreatureType == EnumCreatureType.MONSTER) {
            if (this.genNetherBridge.func_175795_b(blockPos)) {
                return this.genNetherBridge.getSpawnList();
            }
            if (this.genNetherBridge.func_175796_a(this.worldObj, blockPos) && this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.nether_brick) {
                return this.genNetherBridge.getSpawnList();
            }
        }
        return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    public void func_180516_b(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int n3 = this.worldObj.func_181545_F() + 1;
        final double n4 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, n * 16, n2 * 16, 0, 16, 16, 1, n4, n4, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, n * 16, 109, n2 * 16, 16, 1, 16, n4, 1.0, n4);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, n * 16, n2 * 16, 0, 16, 16, 1, n4 * 2.0, n4 * 2.0, n4 * 2.0);
        final boolean b = this.slowsandNoise[0] + this.hellRNG.nextDouble() * 0.2 > 0.0;
        final boolean b2 = this.gravelNoise[0] + this.hellRNG.nextDouble() * 0.2 > 0.0;
        final int n5 = (int)(this.netherrackExclusivityNoise[0] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
        IBlockState blockState = Blocks.netherrack.getDefaultState();
        IBlockState blockState2 = Blocks.netherrack.getDefaultState();
        while (true) {
            if (127 < 127 - this.hellRNG.nextInt(5) && 127 > this.hellRNG.nextInt(5)) {
                final IBlockState blockState3 = chunkPrimer.getBlockState(0, 127, 0);
                if (blockState3.getBlock() != null && blockState3.getBlock().getMaterial() != Material.air && blockState3.getBlock() == Blocks.netherrack) {
                    if (n5 <= 0) {
                        blockState = null;
                        blockState2 = Blocks.netherrack.getDefaultState();
                    }
                    else if (127 >= n3 - 4 && 127 <= n3 + 1) {
                        blockState = Blocks.netherrack.getDefaultState();
                        blockState2 = Blocks.netherrack.getDefaultState();
                        if (b2) {
                            blockState = Blocks.gravel.getDefaultState();
                            blockState2 = Blocks.netherrack.getDefaultState();
                        }
                        if (b) {
                            blockState = Blocks.soul_sand.getDefaultState();
                            blockState2 = Blocks.soul_sand.getDefaultState();
                        }
                    }
                    if (127 < n3 && (blockState == null || blockState.getBlock().getMaterial() == Material.air)) {
                        blockState = Blocks.lava.getDefaultState();
                    }
                    if (127 >= n3 - 1) {
                        chunkPrimer.setBlockState(0, 127, 0, blockState);
                    }
                    else {
                        chunkPrimer.setBlockState(0, 127, 0, blockState2);
                    }
                }
            }
            else {
                chunkPrimer.setBlockState(0, 127, 0, Blocks.bedrock.getDefaultState());
            }
            int n6 = 0;
            --n6;
        }
    }
    
    private double[] initializeNoiseField(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        final double n7 = 684.412;
        final double n8 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, n, n2, n3, n4, 1, n6, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, n, n2, n3, n4, 1, n6, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, n, n2, n3, n4, n5, n6, n7 / 80.0, n8 / 60.0, n7 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, n, n2, n3, n4, n5, n6, n7, n8, n7);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, n, n2, n3, n4, n5, n6, n7, n8, n7);
        final double[] array2 = new double[n5];
        int n12 = 0;
        while (0 < n5) {
            array2[0] = Math.cos(0 * 3.141592653589793 * 6.0 / n5) * 2.0;
            double n9 = 0;
            if (0 > n5 / 2) {
                n9 = n5 - 1 - 0;
            }
            if (n9 < 4.0) {
                final double n10 = 4.0 - n9;
                final double[] array3 = array2;
                final int n11 = 0;
                array3[n11] -= n10 * n10 * n10 * 10.0;
            }
            ++n12;
        }
        while (0 < n4) {
            while (0 < n6) {
                final double n13 = 0.0;
                while (0 < n5) {
                    final double n14 = array2[0];
                    final double n15 = this.noiseData2[0] / 512.0;
                    final double n16 = this.noiseData3[0] / 512.0;
                    final double n17 = (this.noiseData1[0] / 10.0 + 1.0) / 2.0;
                    double n18;
                    if (n17 < 0.0) {
                        n18 = n15;
                    }
                    else if (n17 > 1.0) {
                        n18 = n16;
                    }
                    else {
                        n18 = n15 + (n16 - n15) * n17;
                    }
                    double n19 = n18 - n14;
                    if (0 > n5 - 4) {
                        final double n20 = (0 - (n5 - 4)) / 3.0f;
                        n19 = n19 * (1.0 - n20) + -10.0 * n20;
                    }
                    if (0 < n13) {
                        final double clamp_double = MathHelper.clamp_double((n13 - 0) / 4.0, 0.0, 1.0);
                        n19 = n19 * (1.0 - clamp_double) + -10.0 * clamp_double;
                    }
                    array[0] = n19;
                    int n21 = 0;
                    ++n21;
                    int n22 = 0;
                    ++n22;
                }
                int n23 = 0;
                ++n23;
            }
            ++n12;
        }
        return array;
    }
    
    @Override
    public String makeString() {
        return "HellRandomLevelSource";
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.hellRNG.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.func_180515_a(n, n2, chunkPrimer);
        this.func_180516_b(n, n2, chunkPrimer);
        this.netherCaveGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        if (this.field_177466_i) {
            this.genNetherBridge.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final BiomeGenBase[] loadBlockGeneratorData = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, n * 16, n2 * 16, 16, 16);
        final byte[] biomeArray = chunk.getBiomeArray();
        while (0 < biomeArray.length) {
            biomeArray[0] = (byte)loadBlockGeneratorData[0].biomeID;
            int n3 = 0;
            ++n3;
        }
        chunk.resetRelightChecks();
        return chunk;
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = true;
        final BlockPos blockPos = new BlockPos(n * 16, 0, n2 * 16);
        this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, new ChunkCoordIntPair(n, n2));
        while (true) {
            this.field_177472_y.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return false;
    }
    
    @Override
    public void saveExtraData() {
    }
}
