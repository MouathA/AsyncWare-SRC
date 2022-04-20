package net.minecraft.world.gen.layer;

public class GenLayerAddIsland extends GenLayer
{
    public GenLayerAddIsland(final long n, final GenLayer parent) {
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
                final int n8 = ints[0 + 0 * n7];
                final int n9 = ints[2 + 0 * n7];
                final int n10 = ints[0 + 2 * n7];
                final int n11 = ints[2 + 2 * n7];
                final int n12 = ints[1 + 1 * n7];
                this.initChunkSeed(0 + n, 0 + n2);
                if (n12 != 0 || (n8 == 0 && n9 == 0 && n10 == 0 && n11 == 0)) {
                    if (n12 > 0 && (n8 == 0 || n9 == 0 || n10 == 0 || n11 == 0)) {
                        if (this.nextInt(5) == 0) {
                            if (n12 == 4) {
                                intCache[0 + 0 * n3] = 4;
                            }
                            else {
                                intCache[0 + 0 * n3] = 0;
                            }
                        }
                        else {
                            intCache[0 + 0 * n3] = n12;
                        }
                    }
                    else {
                        intCache[0 + 0 * n3] = n12;
                    }
                }
                else {
                    int n14 = 0;
                    if (n8 != 0) {
                        final int n13 = 1;
                        ++n14;
                        if (this.nextInt(n13) == 0) {}
                    }
                    if (n9 != 0) {
                        final int n15 = 1;
                        ++n14;
                        if (this.nextInt(n15) == 0) {}
                    }
                    if (n10 != 0) {
                        final int n16 = 1;
                        ++n14;
                        if (this.nextInt(n16) == 0) {}
                    }
                    if (n11 != 0) {
                        final int n17 = 1;
                        ++n14;
                        if (this.nextInt(n17) == 0) {}
                    }
                    if (this.nextInt(3) == 0) {
                        intCache[0 + 0 * n3] = 1;
                    }
                    else {
                        intCache[0 + 0 * n3] = 0;
                    }
                }
                int n18 = 0;
                ++n18;
            }
            int n19 = 0;
            ++n19;
        }
        return intCache;
    }
}
