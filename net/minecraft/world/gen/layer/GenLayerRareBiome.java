package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRareBiome extends GenLayer
{
    public GenLayerRareBiome(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - 1, n2 - 1, n3 + 2, n4 + 2);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                final int n5 = ints[1 + 1 * (n3 + 2)];
                if (this.nextInt(57) == 0) {
                    if (n5 == BiomeGenBase.plains.biomeID) {
                        intCache[0 + 0 * n3] = BiomeGenBase.plains.biomeID + 128;
                    }
                    else {
                        intCache[0 + 0 * n3] = n5;
                    }
                }
                else {
                    intCache[0 + 0 * n3] = n5;
                }
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
        return intCache;
    }
}
