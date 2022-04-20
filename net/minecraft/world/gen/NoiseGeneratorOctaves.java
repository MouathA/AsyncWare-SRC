package net.minecraft.world.gen;

import net.minecraft.util.*;
import java.util.*;

public class NoiseGeneratorOctaves extends NoiseGenerator
{
    private int octaves;
    private NoiseGeneratorImproved[] generatorCollection;
    
    public double[] generateNoiseOctaves(final double[] array, final int n, final int n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        return this.generateNoiseOctaves(array, n, 10, n2, n3, 1, n4, n5, 1.0, n6);
    }
    
    public double[] generateNoiseOctaves(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        else {
            while (0 < array.length) {
                array[0] = 0.0;
                int n10 = 0;
                ++n10;
            }
        }
        double n11 = 1.0;
        while (0 < this.octaves) {
            final double n12 = n * n11 * n7;
            final double n13 = n2 * n11 * n8;
            final double n14 = n3 * n11 * n9;
            final long floor_double_long = MathHelper.floor_double_long(n12);
            final long floor_double_long2 = MathHelper.floor_double_long(n14);
            this.generatorCollection[0].populateNoiseArray(array, n12 - floor_double_long + floor_double_long % 16777216L, n13, n14 - floor_double_long2 + floor_double_long2 % 16777216L, n4, n5, n6, n7 * n11, n8 * n11, n9 * n11, n11);
            n11 /= 2.0;
            int n15 = 0;
            ++n15;
        }
        return array;
    }
    
    public NoiseGeneratorOctaves(final Random random, final int octaves) {
        this.octaves = octaves;
        this.generatorCollection = new NoiseGeneratorImproved[octaves];
        while (0 < octaves) {
            this.generatorCollection[0] = new NoiseGeneratorImproved(random);
            int n = 0;
            ++n;
        }
    }
}
