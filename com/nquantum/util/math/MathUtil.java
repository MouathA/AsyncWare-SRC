package com.nquantum.util.math;

public class MathUtil
{
    public static double distance(final float n, final float n2, final float n3, final float n4) {
        return Math.sqrt((n - n3) * (n - n3) + (n2 - n4) * (n2 - n4));
    }
    
    public static int Xorshift32(long n) {
        n ^= n << 21;
        n ^= n >>> 35;
        n ^= n << 4;
        return (int)n;
    }
}
