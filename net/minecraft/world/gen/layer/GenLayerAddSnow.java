package net.minecraft.world.gen.layer;

public class GenLayerAddSnow extends GenLayer
{
    public GenLayerAddSnow(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - 1;
        final int n6 = n2 - 1;
        final int n7 = n3 + 2;
        final int[] ints = this.parent.getInts(n5, n6, n7, n4 + 2);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                final int n8 = ints[1 + 1 * n7];
                this.initChunkSeed(0 + n, 0 + n2);
                if (n8 == 0) {
                    intCache[0 + 0 * n3] = 0;
                }
                else {
                    this.nextInt(6);
                    intCache[0 + 0 * n3] = 1;
                }
                int n9 = 0;
                ++n9;
            }
            int n10 = 0;
            ++n10;
        }
        return intCache;
    }
}
