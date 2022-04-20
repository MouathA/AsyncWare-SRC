package net.minecraft.world.gen;

import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class MapGenCaves extends MapGenBase
{
    protected void func_180702_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, double n4, double n5, double n6, final float n7, float n8, float n9, int i, int n10, final double n11) {
        final double n12 = n2 * 16 + 8;
        final double n13 = n3 * 16 + 8;
        float n14 = 0.0f;
        float n15 = 0.0f;
        final Random random = new Random(n);
        if (n10 <= 0) {
            final int n16 = this.range * 16 - 16;
            n10 = 1 - random.nextInt(0);
        }
        if (i == -1) {
            i = n10 / 2;
        }
        final int n17 = random.nextInt(n10 / 2) + n10 / 4;
        final boolean b = random.nextInt(6) == 0;
        while (i < n10) {
            final double n18 = 1.5 + MathHelper.sin(i * 3.1415927f / n10) * n7 * 1.0f;
            final double n19 = n18 * n11;
            final float cos = MathHelper.cos(n9);
            final float sin = MathHelper.sin(n9);
            n4 += MathHelper.cos(n8) * cos;
            n5 += sin;
            n6 += MathHelper.sin(n8) * cos;
            if (b) {
                n9 *= 0.92f;
            }
            else {
                n9 *= 0.7f;
            }
            n9 += n15 * 0.1f;
            n8 += n14 * 0.1f;
            final float n20 = n15 * 0.9f;
            final float n21 = n14 * 0.75f;
            n15 = n20 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            n14 = n21 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            final double n22 = n4 - n12;
            final double n23 = n6 - n13;
            final double n24 = n10 - i;
            final double n25 = n7 + 2.0f + 16.0f;
            if (n22 * n22 + n23 * n23 - n24 * n24 > n25 * n25) {
                return;
            }
            if (n4 >= n12 - 16.0 - n18 * 2.0 && n6 >= n13 - 16.0 - n18 * 2.0 && n4 <= n12 + 16.0 + n18 * 2.0 && n6 <= n13 + 16.0 + n18 * 2.0) {
                final int n26 = MathHelper.floor_double(n4 - n18) - n2 * 16 - 1;
                final int n27 = MathHelper.floor_double(n4 + n18) - n2 * 16 + 1;
                final int n28 = MathHelper.floor_double(n5 - n19) - 1;
                final int n29 = MathHelper.floor_double(n5 + n19) + 1;
                final int n30 = MathHelper.floor_double(n6 - n18) - n3 * 16 - 1;
                final int n31 = MathHelper.floor_double(n6 + n18) - n3 * 16 + 1;
            }
            ++i;
        }
    }
    
    @Override
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
        if (this.rand.nextInt(7) != 0) {}
    }
    
    protected void func_180703_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, final double n4, final double n5, final double n6) {
        this.func_180702_a(n, n2, n3, chunkPrimer, n4, n5, n6, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }
}
