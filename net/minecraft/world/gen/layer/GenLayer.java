package net.minecraft.world.gen.layer;

import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.biome.*;

public abstract class GenLayer
{
    protected GenLayer parent;
    private long chunkSeed;
    protected long baseSeed;
    private long worldGenSeed;
    
    public static GenLayer[] initializeAllBiomeGenerators(final long n, final WorldType worldType, final String s) {
        final GenLayer magnify = GenLayerZoom.magnify(1000L, new GenLayerDeepOcean(4L, new GenLayerAddMushroomIsland(5L, new GenLayerAddIsland(4L, new GenLayerZoom(2003L, new GenLayerZoom(2002L, new GenLayerEdge(3L, new GenLayerEdge(2L, new GenLayerEdge(2L, new GenLayerAddIsland(3L, new GenLayerAddSnow(2L, new GenLayerRemoveTooMuchOcean(2L, new GenLayerAddIsland(70L, new GenLayerAddIsland(50L, new GenLayerAddIsland(2L, new GenLayerZoom(2001L, new GenLayerAddIsland(1L, new GenLayerFuzzyZoom(2000L, new GenLayerIsland(1L)))))))))), GenLayerEdge.Mode.COOL_WARM), GenLayerEdge.Mode.HEAT_ICE), GenLayerEdge.Mode.SPECIAL)))))), 0);
        if (worldType == WorldType.CUSTOMIZED && s.length() > 0) {
            final ChunkProviderSettings func_177864_b = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
            final int biomeSize = func_177864_b.biomeSize;
            final int riverSize = func_177864_b.riverSize;
        }
        if (worldType == WorldType.LARGE_BIOMES) {}
        final GenLayerRiverInit genLayerRiverInit = new GenLayerRiverInit(100L, GenLayerZoom.magnify(1000L, magnify, 0));
        final GenLayerHills genLayerHills = new GenLayerHills(1000L, new GenLayerBiomeEdge(1000L, GenLayerZoom.magnify(1000L, new GenLayerBiome(200L, magnify, worldType, s), 2)), GenLayerZoom.magnify(1000L, genLayerRiverInit, 2));
        final GenLayerSmooth genLayerSmooth = new GenLayerSmooth(1000L, new GenLayerRiver(1L, GenLayerZoom.magnify(1000L, GenLayerZoom.magnify(1000L, genLayerRiverInit, 2), 6)));
        GenLayer genLayer = new GenLayerRareBiome(1001L, genLayerHills);
        while (true) {
            genLayer = new GenLayerAddIsland(3L, new GenLayerZoom(1000, genLayer));
            int n2 = 0;
            ++n2;
        }
    }
    
    public GenLayer(final long baseSeed) {
        this.baseSeed = baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += baseSeed;
    }
    
    protected static boolean isBiomeOceanic(final int n) {
        return n == BiomeGenBase.ocean.biomeID || n == BiomeGenBase.deepOcean.biomeID || n == BiomeGenBase.frozenOcean.biomeID;
    }
    
    protected int selectModeOrRandom(final int n, final int n2, final int n3, final int n4) {
        return (n2 == n3 && n3 == n4) ? n2 : ((n == n2 && n == n3) ? n : ((n == n2 && n == n4) ? n : ((n == n3 && n == n4) ? n : ((n == n2 && n3 != n4) ? n : ((n == n3 && n2 != n4) ? n : ((n == n4 && n2 != n3) ? n : ((n2 == n3 && n != n4) ? n2 : ((n2 == n4 && n != n3) ? n2 : ((n3 == n4 && n != n2) ? n3 : this.selectRandom(n, n2, n3, n4))))))))));
    }
    
    protected static boolean biomesEqualOrMesaPlateau(final int n, final int n2) {
        if (n == n2) {
            return true;
        }
        if (n != BiomeGenBase.mesaPlateau_F.biomeID && n != BiomeGenBase.mesaPlateau.biomeID) {
            final BiomeGenBase biome = BiomeGenBase.getBiome(n);
            final BiomeGenBase biome2 = BiomeGenBase.getBiome(n2);
            return biome != null && biome2 != null && biome.isEqualTo(biome2);
        }
        return n2 == BiomeGenBase.mesaPlateau_F.biomeID || n2 == BiomeGenBase.mesaPlateau.biomeID;
    }
    
    public abstract int[] getInts(final int p0, final int p1, final int p2, final int p3);
    
    protected int nextInt(final int n) {
        int n2 = (int)((this.chunkSeed >> 24) % n);
        if (n2 < 0) {
            n2 += n;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return n2;
    }
    
    public void initChunkSeed(final long n, final long n2) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n2;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += n2;
    }
    
    public void initWorldGenSeed(final long worldGenSeed) {
        this.worldGenSeed = worldGenSeed;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(worldGenSeed);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }
    
    protected int selectRandom(final int... array) {
        return array[this.nextInt(array.length)];
    }
}
