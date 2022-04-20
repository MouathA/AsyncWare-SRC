package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorSimplex
{
    public double field_151612_b;
    private static int[][] field_151611_e;
    private int[] field_151608_f;
    private static final double field_151615_h;
    private static final double field_151609_g;
    public double field_151613_c;
    public static final double field_151614_a;
    public double field_151610_d;
    
    public void func_151606_a(final double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        while (0 < n4) {
            final double n8 = (n2 + 0) * n6 + this.field_151613_c;
            while (0 < n3) {
                final double n9 = (n + 0) * n5 + this.field_151612_b;
                final double n10 = (n9 + n8) * NoiseGeneratorSimplex.field_151609_g;
                final int func_151607_a = func_151607_a(n9 + n10);
                final int func_151607_a2 = func_151607_a(n8 + n10);
                final double n11 = (func_151607_a + func_151607_a2) * NoiseGeneratorSimplex.field_151615_h;
                final double n12 = func_151607_a - n11;
                final double n13 = func_151607_a2 - n11;
                final double n14 = n9 - n12;
                final double n15 = n8 - n13;
                if (n14 > n15) {}
                final double n16 = n14 - 0 + NoiseGeneratorSimplex.field_151615_h;
                final double n17 = n15 - 1 + NoiseGeneratorSimplex.field_151615_h;
                final double n18 = n14 - 1.0 + 2.0 * NoiseGeneratorSimplex.field_151615_h;
                final double n19 = n15 - 1.0 + 2.0 * NoiseGeneratorSimplex.field_151615_h;
                final int n20 = func_151607_a & 0xFF;
                final int n21 = func_151607_a2 & 0xFF;
                final int n22 = this.field_151608_f[n20 + this.field_151608_f[n21]] % 12;
                final int n23 = this.field_151608_f[n20 + 0 + this.field_151608_f[n21 + 1]] % 12;
                final int n24 = this.field_151608_f[n20 + 1 + this.field_151608_f[n21 + 1]] % 12;
                final double n25 = 0.5 - n14 * n14 - n15 * n15;
                double n26;
                if (n25 < 0.0) {
                    n26 = 0.0;
                }
                else {
                    final double n27 = n25 * n25;
                    n26 = n27 * n27 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n22], n14, n15);
                }
                final double n28 = 0.5 - n16 * n16 - n17 * n17;
                double n29;
                if (n28 < 0.0) {
                    n29 = 0.0;
                }
                else {
                    final double n30 = n28 * n28;
                    n29 = n30 * n30 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n23], n16, n17);
                }
                final double n31 = 0.5 - n18 * n18 - n19 * n19;
                double n32;
                if (n31 < 0.0) {
                    n32 = 0.0;
                }
                else {
                    final double n33 = n31 * n31;
                    n32 = n33 * n33 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n24], n18, n19);
                }
                final int n34 = 0;
                int n35 = 0;
                ++n35;
                final int n36 = n34;
                array[n36] += 70.0 * (n26 + n29 + n32) * n7;
                int n37 = 0;
                ++n37;
            }
            int n38 = 0;
            ++n38;
        }
    }
    
    private static double func_151604_a(final int[] array, final double n, final double n2) {
        return array[0] * n + array[1] * n2;
    }
    
    private static int func_151607_a(final double n) {
        return (n > 0.0) ? ((int)n) : ((int)n - 1);
    }
    
    public NoiseGeneratorSimplex(final Random random) {
        this.field_151608_f = new int[512];
        this.field_151612_b = random.nextDouble() * 256.0;
        this.field_151613_c = random.nextDouble() * 256.0;
        this.field_151610_d = random.nextDouble() * 256.0;
        while (true) {
            final int[] field_151608_f = this.field_151608_f;
            final int n = 0;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            field_151608_f[n] = n2;
        }
    }
    
    public NoiseGeneratorSimplex() {
        this(new Random());
    }
    
    static {
        NoiseGeneratorSimplex.field_151611_e = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
        field_151614_a = Math.sqrt(3.0);
        field_151609_g = 0.5 * (NoiseGeneratorSimplex.field_151614_a - 1.0);
        field_151615_h = (3.0 - NoiseGeneratorSimplex.field_151614_a) / 6.0;
    }
    
    public double func_151605_a(final double n, final double n2) {
        final double n3 = (n + n2) * (0.5 * (NoiseGeneratorSimplex.field_151614_a - 1.0));
        final int func_151607_a = func_151607_a(n + n3);
        final int func_151607_a2 = func_151607_a(n2 + n3);
        final double n4 = (3.0 - NoiseGeneratorSimplex.field_151614_a) / 6.0;
        final double n5 = (func_151607_a + func_151607_a2) * n4;
        final double n6 = func_151607_a - n5;
        final double n7 = func_151607_a2 - n5;
        final double n8 = n - n6;
        final double n9 = n2 - n7;
        if (n8 > n9) {}
        final double n10 = n8 - 0 + n4;
        final double n11 = n9 - 1 + n4;
        final double n12 = n8 - 1.0 + 2.0 * n4;
        final double n13 = n9 - 1.0 + 2.0 * n4;
        final int n14 = func_151607_a & 0xFF;
        final int n15 = func_151607_a2 & 0xFF;
        final int n16 = this.field_151608_f[n14 + this.field_151608_f[n15]] % 12;
        final int n17 = this.field_151608_f[n14 + 0 + this.field_151608_f[n15 + 1]] % 12;
        final int n18 = this.field_151608_f[n14 + 1 + this.field_151608_f[n15 + 1]] % 12;
        final double n19 = 0.5 - n8 * n8 - n9 * n9;
        double n20;
        if (n19 < 0.0) {
            n20 = 0.0;
        }
        else {
            final double n21 = n19 * n19;
            n20 = n21 * n21 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n16], n8, n9);
        }
        final double n22 = 0.5 - n10 * n10 - n11 * n11;
        double n23;
        if (n22 < 0.0) {
            n23 = 0.0;
        }
        else {
            final double n24 = n22 * n22;
            n23 = n24 * n24 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n17], n10, n11);
        }
        final double n25 = 0.5 - n12 * n12 - n13 * n13;
        double n26;
        if (n25 < 0.0) {
            n26 = 0.0;
        }
        else {
            final double n27 = n25 * n25;
            n26 = n27 * n27 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n18], n12, n13);
        }
        return 70.0 * (n20 + n23 + n26);
    }
}
