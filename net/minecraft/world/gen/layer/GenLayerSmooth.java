package net.minecraft.world.gen.layer;

public class GenLayerSmooth extends GenLayer
{
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - 1;
        final int n6 = n2 - 1;
        final int n7 = n3 + 2;
        final int[] ints = this.parent.getInts(n5, n6, n7, n4 + 2);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                final int n8 = ints[0 + 1 * n7];
                final int n9 = ints[2 + 1 * n7];
                final int n10 = ints[1 + 0 * n7];
                final int n11 = ints[1 + 2 * n7];
                int n12 = ints[1 + 1 * n7];
                if (n8 == n9 && n10 == n11) {
                    this.initChunkSeed(0 + n, 0 + n2);
                    if (this.nextInt(2) == 0) {
                        n12 = n8;
                    }
                    else {
                        n12 = n10;
                    }
                }
                else {
                    if (n8 == n9) {
                        n12 = n8;
                    }
                    if (n10 == n11) {
                        n12 = n10;
                    }
                }
                intCache[0 + 0 * n3] = n12;
                int n13 = 0;
                ++n13;
            }
            int n14 = 0;
            ++n14;
        }
        return intCache;
    }
    
    public GenLayerSmooth(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
}
