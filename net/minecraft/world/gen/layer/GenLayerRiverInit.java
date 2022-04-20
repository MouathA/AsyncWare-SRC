package net.minecraft.world.gen.layer;

public class GenLayerRiverInit extends GenLayer
{
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                intCache[0 + 0 * n3] = ((ints[0 + 0 * n3] > 0) ? (this.nextInt(299999) + 2) : 0);
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
        return intCache;
    }
    
    public GenLayerRiverInit(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
}
