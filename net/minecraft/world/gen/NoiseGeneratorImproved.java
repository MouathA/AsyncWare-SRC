package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorImproved extends NoiseGenerator
{
    private static final double[] field_152384_h;
    private static final double[] field_152385_i;
    private static final double[] field_152381_e;
    public double zCoord;
    public double yCoord;
    public double xCoord;
    private int[] permutations;
    private static final double[] field_152383_g;
    private static final double[] field_152382_f;
    
    static {
        field_152381_e = new double[] { 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0 };
        field_152382_f = new double[] { 1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0 };
        field_152383_g = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0 };
        field_152384_h = new double[] { 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0 };
        field_152385_i = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0 };
    }
    
    public final double grad(final int n, final double n2, final double n3, final double n4) {
        final int n5 = n & 0xF;
        return NoiseGeneratorImproved.field_152381_e[n5] * n2 + NoiseGeneratorImproved.field_152382_f[n5] * n3 + NoiseGeneratorImproved.field_152383_g[n5] * n4;
    }
    
    public final double lerp(final double n, final double n2, final double n3) {
        return n2 + n * (n3 - n2);
    }
    
    public NoiseGeneratorImproved(final Random random) {
        this.permutations = new int[512];
        this.xCoord = random.nextDouble() * 256.0;
        this.yCoord = random.nextDouble() * 256.0;
        this.zCoord = random.nextDouble() * 256.0;
        while (true) {
            final int[] permutations = this.permutations;
            final int n = 0;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            permutations[n] = n2;
        }
    }
    
    public NoiseGeneratorImproved() {
        this(new Random());
    }
    
    public final double func_76309_a(final int n, final double n2, final double n3) {
        final int n4 = n & 0xF;
        return NoiseGeneratorImproved.field_152384_h[n4] * n2 + NoiseGeneratorImproved.field_152385_i[n4] * n3;
    }
    
    public void populateNoiseArray(final double[] array, final double n, final double n2, final double n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9, final double n10) {
        if (n5 == 1) {
            final double n11 = 1.0 / n10;
            while (0 < n4) {
                final double n12 = n + 0 * n7 + this.xCoord;
                int n13 = (int)n12;
                if (n12 < n13) {
                    --n13;
                }
                final int n14 = n13 & 0xFF;
                final double n15 = n12 - n13;
                final double n16 = n15 * n15 * n15 * (n15 * (n15 * 6.0 - 15.0) + 10.0);
                while (0 < n6) {
                    final double n17 = n3 + 0 * n9 + this.zCoord;
                    int n18 = (int)n17;
                    if (n17 < n18) {
                        --n18;
                    }
                    final int n19 = n18 & 0xFF;
                    final double n20 = n17 - n18;
                    final double n21 = n20 * n20 * n20 * (n20 * (n20 * 6.0 - 15.0) + 10.0);
                    final int n22 = this.permutations[n14] + 0;
                    final int n23 = this.permutations[0] + n19;
                    final int n24 = this.permutations[n14 + 1] + 0;
                    final int n25 = this.permutations[0] + n19;
                    final double lerp = this.lerp(n21, this.lerp(n16, this.func_76309_a(this.permutations[0], n15, n20), this.grad(this.permutations[-1], n15 - 1.0, 0.0, n20)), this.lerp(n16, this.grad(this.permutations[1], n15, 0.0, n20 - 1.0), this.grad(this.permutations[0], n15 - 1.0, 0.0, n20 - 1.0)));
                    final int n26 = 0;
                    int n27 = 0;
                    ++n27;
                    final int n28 = n26;
                    array[n28] += lerp * n11;
                    int n29 = 0;
                    ++n29;
                }
                int n30 = 0;
                ++n30;
            }
        }
        else {
            final double n31 = 1.0 / n10;
            while (0 < n4) {
                final double n32 = n + 0 * n7 + this.xCoord;
                int n33 = (int)n32;
                if (n32 < n33) {
                    --n33;
                }
                final int n34 = n33 & 0xFF;
                final double n35 = n32 - n33;
                final double n36 = n35 * n35 * n35 * (n35 * (n35 * 6.0 - 15.0) + 10.0);
                while (0 < n6) {
                    final double n37 = n3 + 0 * n9 + this.zCoord;
                    int n38 = (int)n37;
                    if (n37 < n38) {
                        --n38;
                    }
                    final int n39 = n38 & 0xFF;
                    final double n40 = n37 - n38;
                    final double n41 = n40 * n40 * n40 * (n40 * (n40 * 6.0 - 15.0) + 10.0);
                    while (0 < n5) {
                        final double n42 = n2 + 0 * n8 + this.yCoord;
                        int n43 = (int)n42;
                        if (n42 < n43) {
                            --n43;
                        }
                        final int n44 = n43 & 0xFF;
                        final double n45 = n42 - n43;
                        final double n46 = n45 * n45 * n45 * (n45 * (n45 * 6.0 - 15.0) + 10.0);
                        final int n47 = this.permutations[n34] + n44;
                        final int n48 = this.permutations[0] + n39;
                        final int n49 = this.permutations[1] + n39;
                        final int n50 = this.permutations[n34 + 1] + n44;
                        final int n27 = this.permutations[0] + n39;
                        final int n51 = this.permutations[1] + n39;
                        final double lerp2 = this.lerp(n41, this.lerp(n46, this.lerp(n36, this.grad(this.permutations[0], n35, n45, n40), this.grad(this.permutations[0], n35 - 1.0, n45, n40)), this.lerp(n36, this.grad(this.permutations[0], n35, n45 - 1.0, n40), this.grad(this.permutations[0], n35 - 1.0, n45 - 1.0, n40))), this.lerp(n46, this.lerp(n36, this.grad(this.permutations[1], n35, n45, n40 - 1.0), this.grad(this.permutations[1], n35 - 1.0, n45, n40 - 1.0)), this.lerp(n36, this.grad(this.permutations[1], n35, n45 - 1.0, n40 - 1.0), this.grad(this.permutations[1], n35 - 1.0, n45 - 1.0, n40 - 1.0))));
                        final int n52 = 0;
                        int n22 = 0;
                        ++n22;
                        final int n53 = n52;
                        array[n53] += lerp2 * n31;
                        int n54 = 0;
                        ++n54;
                    }
                    int n55 = 0;
                    ++n55;
                }
                int n29 = 0;
                ++n29;
            }
        }
    }
}
