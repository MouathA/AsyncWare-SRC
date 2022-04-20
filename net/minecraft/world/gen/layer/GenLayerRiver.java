package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRiver extends GenLayer
{
    public GenLayerRiver(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
    
    private int func_151630_c(final int n) {
        return (n >= 2) ? (2 + (n & 0x1)) : n;
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
                final int func_151630_c = this.func_151630_c(ints[0 + 1 * n7]);
                final int func_151630_c2 = this.func_151630_c(ints[2 + 1 * n7]);
                final int func_151630_c3 = this.func_151630_c(ints[1 + 0 * n7]);
                final int func_151630_c4 = this.func_151630_c(ints[1 + 2 * n7]);
                final int func_151630_c5 = this.func_151630_c(ints[1 + 1 * n7]);
                if (func_151630_c5 == func_151630_c && func_151630_c5 == func_151630_c3 && func_151630_c5 == func_151630_c2 && func_151630_c5 == func_151630_c4) {
                    intCache[0 + 0 * n3] = -1;
                }
                else {
                    intCache[0 + 0 * n3] = BiomeGenBase.river.biomeID;
                }
                int n8 = 0;
                ++n8;
            }
            int n9 = 0;
            ++n9;
        }
        return intCache;
    }
}
