package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.biomePatternGeneratorChain.getInts(n, n2, n3, n4);
        final int[] ints2 = this.riverPatternGeneratorChain.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n3 * n4) {
            if (ints[0] != BiomeGenBase.ocean.biomeID && ints[0] != BiomeGenBase.deepOcean.biomeID) {
                if (ints2[0] == BiomeGenBase.river.biomeID) {
                    if (ints[0] == BiomeGenBase.icePlains.biomeID) {
                        intCache[0] = BiomeGenBase.frozenRiver.biomeID;
                    }
                    else if (ints[0] != BiomeGenBase.mushroomIsland.biomeID && ints[0] != BiomeGenBase.mushroomIslandShore.biomeID) {
                        intCache[0] = (ints2[0] & 0xFF);
                    }
                    else {
                        intCache[0] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                }
                else {
                    intCache[0] = ints[0];
                }
            }
            else {
                intCache[0] = ints[0];
            }
            int n5 = 0;
            ++n5;
        }
        return intCache;
    }
    
    public GenLayerRiverMix(final long n, final GenLayer biomePatternGeneratorChain, final GenLayer riverPatternGeneratorChain) {
        super(n);
        this.biomePatternGeneratorChain = biomePatternGeneratorChain;
        this.riverPatternGeneratorChain = riverPatternGeneratorChain;
    }
    
    @Override
    public void initWorldGenSeed(final long n) {
        this.biomePatternGeneratorChain.initWorldGenSeed(n);
        this.riverPatternGeneratorChain.initWorldGenSeed(n);
        super.initWorldGenSeed(n);
    }
}
