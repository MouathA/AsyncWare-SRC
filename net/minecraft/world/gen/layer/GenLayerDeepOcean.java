package net.minecraft.world.gen.layer;

public class GenLayerDeepOcean extends GenLayer
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
                final int n8 = ints[1 + 0 * (n3 + 2)];
                final int n9 = ints[2 + 1 * (n3 + 2)];
                final int n10 = ints[0 + 1 * (n3 + 2)];
                final int n11 = ints[1 + 2 * (n3 + 2)];
                final int n12 = ints[1 + 1 * n7];
                int n13 = 0;
                if (n8 == 0) {
                    ++n13;
                }
                if (n9 == 0) {
                    ++n13;
                }
                if (n10 == 0) {
                    ++n13;
                }
                if (n11 == 0) {
                    ++n13;
                }
                if (n12 == 0) {}
                intCache[0 + 0 * n3] = n12;
                int n14 = 0;
                ++n14;
            }
            int n15 = 0;
            ++n15;
        }
        return intCache;
    }
    
    public GenLayerDeepOcean(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
}
