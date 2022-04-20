package net.minecraft.world.gen.layer;

public class GenLayerVoronoiZoom extends GenLayer
{
    @Override
    public int[] getInts(int n, int n2, final int n3, final int n4) {
        n -= 2;
        n2 -= 2;
        final int n5 = n >> 2;
        final int n6 = n2 >> 2;
        final int n7 = (n3 >> 2) + 2;
        final int n8 = (n4 >> 2) + 2;
        final int[] ints = this.parent.getInts(n5, n6, n7, n8);
        final int n9 = n7 - 1 << 2;
        final int[] intCache = IntCache.getIntCache(n9 * (n8 - 1 << 2));
        while (0 < n8 - 1) {
            final int n10 = ints[0 + 0 * n7];
            final int n11 = ints[0 + 1 * n7];
            if (0 < n7 - 1) {
                this.initChunkSeed(0 + n5 << 2, 0 + n6 << 2);
                final double n12 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                final double n13 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                this.initChunkSeed(0 + n5 + 1 << 2, 0 + n6 << 2);
                final double n14 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                final double n15 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                this.initChunkSeed(0 + n5 << 2, 0 + n6 + 1 << 2);
                final double n16 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                final double n17 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                this.initChunkSeed(0 + n5 + 1 << 2, 0 + n6 + 1 << 2);
                final double n18 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                final double n19 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                final int n20 = ints[1 + 0 * n7] & 0xFF;
                final int n21 = ints[1 + 1 * n7] & 0xFF;
                int n22 = 0 * n9 + 0;
                while (true) {
                    final double n23 = (0 - n13) * (0 - n13) + (0 - n12) * (0 - n12);
                    final double n24 = (0 - n15) * (0 - n15) + (0 - n14) * (0 - n14);
                    final double n25 = (0 - n17) * (0 - n17) + (0 - n16) * (0 - n16);
                    final double n26 = (0 - n19) * (0 - n19) + (0 - n18) * (0 - n18);
                    if (n23 < n24 && n23 < n25 && n23 < n26) {
                        intCache[n22++] = n10;
                    }
                    else if (n24 < n23 && n24 < n25 && n24 < n26) {
                        intCache[n22++] = n20;
                    }
                    else if (n25 < n23 && n25 < n24 && n25 < n26) {
                        intCache[n22++] = n11;
                    }
                    else {
                        intCache[n22++] = n21;
                    }
                    int n27 = 0;
                    ++n27;
                }
            }
            else {
                int n28 = 0;
                ++n28;
            }
        }
        final int[] intCache2 = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            System.arraycopy(intCache, (0 + (n2 & 0x3)) * n9 + (n & 0x3), intCache2, 0 * n3, n3);
            int n29 = 0;
            ++n29;
        }
        return intCache2;
    }
    
    public GenLayerVoronoiZoom(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
}
