package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer
{
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n >> 1;
        final int n6 = n2 >> 1;
        final int n7 = (n3 >> 1) + 2;
        final int n8 = (n4 >> 1) + 2;
        final int[] ints = this.parent.getInts(n5, n6, n7, n8);
        final int n9 = n7 - 1 << 1;
        final int[] intCache = IntCache.getIntCache(n9 * (n8 - 1 << 1));
        int n10 = 0;
        while (0 < n8 - 1) {
            n10 = 0 * n9;
            int n11 = ints[0 + 0 * n7];
            int n12 = ints[0 + 1 * n7];
            while (0 < n7 - 1) {
                this.initChunkSeed(0 + n5 << 1, 0 + n6 << 1);
                final int n13 = ints[1 + 0 * n7];
                final int n14 = ints[1 + 1 * n7];
                intCache[0] = n11;
                final int[] array = intCache;
                final int n15 = 0;
                ++n10;
                array[n15 + n9] = this.selectRandom(n11, n12);
                intCache[0] = this.selectRandom(n11, n13);
                final int[] array2 = intCache;
                final int n16 = 0;
                ++n10;
                array2[n16 + n9] = this.selectModeOrRandom(n11, n13, n12, n14);
                n11 = n13;
                n12 = n14;
                int n17 = 0;
                ++n17;
            }
            int n18 = 0;
            ++n18;
        }
        final int[] intCache2 = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            System.arraycopy(intCache, (0 + (n2 & 0x1)) * n9 + (n & 0x1), intCache2, 0 * n3, n3);
            ++n10;
        }
        return intCache2;
    }
    
    public static GenLayer magnify(final long n, final GenLayer genLayer, final int n2) {
        GenLayer genLayer2 = genLayer;
        while (0 < n2) {
            genLayer2 = new GenLayerZoom(n + 0, genLayer2);
            int n3 = 0;
            ++n3;
        }
        return genLayer2;
    }
    
    public GenLayerZoom(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
}
