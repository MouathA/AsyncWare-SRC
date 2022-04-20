package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
    private int field_151602_b;
    private NoiseGeneratorSimplex[] field_151603_a;
    
    public double[] func_151600_a(double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7, final double n8) {
        if (array != null && array.length >= n3 * n4) {
            while (0 < array.length) {
                array[0] = 0.0;
                int n9 = 0;
                ++n9;
            }
        }
        else {
            array = new double[n3 * n4];
        }
        double n10 = 1.0;
        double n11 = 1.0;
        while (0 < this.field_151602_b) {
            this.field_151603_a[0].func_151606_a(array, n, n2, n3, n4, n5 * n11 * n10, n6 * n11 * n10, 0.55 / n10);
            n11 *= n7;
            n10 *= n8;
            int n12 = 0;
            ++n12;
        }
        return array;
    }
    
    public double func_151601_a(final double n, final double n2) {
        double n3 = 0.0;
        double n4 = 1.0;
        while (0 < this.field_151602_b) {
            n3 += this.field_151603_a[0].func_151605_a(n * n4, n2 * n4) / n4;
            n4 /= 2.0;
            int n5 = 0;
            ++n5;
        }
        return n3;
    }
    
    public double[] func_151599_a(final double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        return this.func_151600_a(array, n, n2, n3, n4, n5, n6, n7, 0.5);
    }
    
    public NoiseGeneratorPerlin(final Random random, final int field_151602_b) {
        this.field_151602_b = field_151602_b;
        this.field_151603_a = new NoiseGeneratorSimplex[field_151602_b];
        while (0 < field_151602_b) {
            this.field_151603_a[0] = new NoiseGeneratorSimplex(random);
            int n = 0;
            ++n;
        }
    }
}
