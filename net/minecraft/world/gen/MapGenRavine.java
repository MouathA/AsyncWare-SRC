package net.minecraft.world.gen;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class MapGenRavine extends MapGenBase
{
    private float[] field_75046_d;
    
    @Override
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        if (this.rand.nextInt(50) != 0) {
            return;
        }
        final double n5 = n * 16 + this.rand.nextInt(16);
        final double n6 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
        final double n7 = n2 * 16 + this.rand.nextInt(16);
        while (true) {
            this.func_180707_a(this.rand.nextLong(), n3, n4, chunkPrimer, n5, n6, n7, (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f, this.rand.nextFloat() * 3.1415927f * 2.0f, (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f, 0, 0, 3.0);
            int n8 = 0;
            ++n8;
        }
    }
    
    protected void func_180707_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, final double n4, final double n5, final double n6, final float n7, final float n8, final float n9, int n10, int n11, final double n12) {
        final Random random = new Random(n);
        final double n13 = n2 * 16 + 8;
        final double n14 = n3 * 16 + 8;
        if (n11 <= 0) {
            final int n15 = this.range * 16 - 16;
            n11 = 1 - random.nextInt(0);
        }
        if (n10 == -1) {
            n10 = n11 / 2;
        }
        while (true) {
            final float n16 = 1.0f + random.nextFloat() * random.nextFloat() * 1.0f;
            this.field_75046_d[0] = n16 * n16;
            int n17 = 0;
            ++n17;
        }
    }
    
    public MapGenRavine() {
        this.field_75046_d = new float[1024];
    }
}
