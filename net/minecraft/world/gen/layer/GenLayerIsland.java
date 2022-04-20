package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer
{
    public GenLayerIsland(final long n) {
        super(n);
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(n + 0, n2 + 0);
                intCache[0 + 0 * n3] = ((this.nextInt(10) == 0) ? 1 : 0);
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
        if (n > -n3 && n <= 0 && n2 > -n4 && n2 <= 0) {
            intCache[-n + -n2 * n3] = 1;
        }
        return intCache;
    }
}
