package net.minecraft.util;

import java.util.*;

public class MathHelper
{
    public static boolean fastMath;
    private static final float[] SIN_TABLE_FAST;
    public static final float PI = 3.1415927f;
    public static final float deg2Rad = 0.017453292f;
    private static final int SIN_COUNT = 4096;
    public static final float PId2 = 1.5707964f;
    public static final float SQRT_2;
    private static final String __OBFID = "CL_00001496";
    public static final float PI2 = 6.2831855f;
    private static final double[] field_181164_e;
    private static final float radFull = 6.2831855f;
    private static final int SIN_BITS = 12;
    private static final double[] field_181165_f;
    private static final float degFull = 360.0f;
    private static final int SIN_MASK = 4095;
    private static final float radToIndex = 651.8986f;
    private static final float degToIndex = 11.377778f;
    private static final float[] SIN_TABLE;
    
    public static int normalizeAngle(final int n, final int n2) {
        return (n % n2 + n2) % n2;
    }
    
    public static int bucketInt(final int n, final int n2) {
        return (n < 0) ? (-((-n - 1) / n2) - 1) : (n / n2);
    }
    
    public static double func_181159_b(double n, double n2) {
        final double n3 = n2 * n2 + n * n;
        if (Double.isNaN(n3)) {
            return Double.NaN;
        }
        final boolean b = n < 0.0;
        if (b) {
            n = -n;
        }
        final boolean b2 = n2 < 0.0;
        if (b2) {
            n2 = -n2;
        }
        final boolean b3 = n > n2;
        if (b3) {
            final double n4 = n2;
            n2 = n;
            n = n4;
        }
        final double func_181161_i = func_181161_i(n3);
        n2 *= func_181161_i;
        n *= func_181161_i;
        final double n5 = 1.7592186044416E13 + n;
        final int n6 = (int)Double.doubleToRawLongBits(n5);
        final double n7 = MathHelper.field_181164_e[n6];
        final double n8 = n * MathHelper.field_181165_f[n6] - n2 * (n5 - 1.7592186044416E13);
        double n9 = n7 + (6.0 + n8 * n8) * n8 * 0.16666666666666666;
        if (b3) {
            n9 = 1.5707963267948966 - n9;
        }
        if (b2) {
            n9 = 3.141592653589793 - n9;
        }
        if (b) {
            n9 = -n9;
        }
        return n9;
    }
    
    public static long getPositionRandom(final Vec3i vec3i) {
        return getCoordinateRandom(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public static float randomFloatClamp(final Random random, final float n, final float n2) {
        return (n >= n2) ? n : (random.nextFloat() * (n2 - n) + n);
    }
    
    public static UUID getRandomUuid(final Random random) {
        return new UUID((random.nextLong() & 0xFFFFFFFFFFFF0FFFL) | 0x4000L, (random.nextLong() & 0x3FFFFFFFFFFFFFFFL) | Long.MIN_VALUE);
    }
    
    public static int roundUpToPowerOfTwo(final int n) {
        final int n2 = n - 1;
        final int n3 = n2 | n2 >> 1;
        final int n4 = n3 | n3 >> 2;
        final int n5 = n4 | n4 >> 4;
        final int n6 = n5 | n5 >> 8;
        return (n6 | n6 >> 16) + 1;
    }
    
    public static int floor_double(final double n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static int func_154353_e(final double n) {
        return (int)((n >= 0.0) ? n : (-n + 1.0));
    }
    
    public static int parseIntWithDefaultAndMax(final String s, final int n, final int n2) {
        return Math.max(n2, parseIntWithDefault(s, n));
    }
    
    private static int calculateLogBaseTwoDeBruijn(int n) {
        n = ((n != 0) ? n : roundUpToPowerOfTwo(n));
        return MathHelper.multiplyDeBruijnBitPosition[(int)(n * 125613361L >> 27) & 0x1F];
    }
    
    public static int ceiling_double_int(final double n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int truncateDoubleToInt(final double n) {
        return (int)(n + 1024.0) - 1024;
    }
    
    public static double parseDoubleWithDefaultAndMax(final String s, final double n, final double n2) {
        return Math.max(n2, parseDoubleWithDefault(s, n));
    }
    
    public static int getRandomIntegerInRange(final Random random, final int n, final int n2) {
        return (n >= n2) ? n : (random.nextInt(n2 - n + 1) + n);
    }
    
    public static double parseDoubleWithDefault(final String s, final double n) {
        return Double.parseDouble(s);
    }
    
    public static int parseIntWithDefault(final String s, final int n) {
        return Integer.parseInt(s);
    }
    
    public static double denormalizeClamp(final double n, final double n2, final double n3) {
        return (n3 < 0.0) ? n : ((n3 > 1.0) ? n2 : (n + (n2 - n) * n3));
    }
    
    static {
        SQRT_2 = sqrt_float(2.0f);
        SIN_TABLE_FAST = new float[4096];
        MathHelper.fastMath = false;
        SIN_TABLE = new float[65536];
        while (true) {
            MathHelper.SIN_TABLE[0] = (float)Math.sin(0 * 3.141592653589793 * 2.0 / 65536.0);
            int n = 0;
            ++n;
        }
    }
    
    public static long floor_double_long(final double n) {
        final long n2 = (long)n;
        return (n < n2) ? (n2 - 1L) : n2;
    }
    
    public static int abs_int(final int n) {
        return (n >= 0) ? n : (-n);
    }
    
    public static double func_181161_i(double longBitsToDouble) {
        final double n = 0.5 * longBitsToDouble;
        longBitsToDouble = Double.longBitsToDouble(6910469410427058090L - (Double.doubleToRawLongBits(longBitsToDouble) >> 1));
        longBitsToDouble *= 1.5 - n * longBitsToDouble * longBitsToDouble;
        return longBitsToDouble;
    }
    
    public static float sin(final float n) {
        return MathHelper.fastMath ? MathHelper.SIN_TABLE_FAST[(int)(n * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(n * 10430.378f) & 0xFFFF];
    }
    
    public static float wrapAngleTo180_float(float n) {
        n %= 360.0f;
        if (n >= 180.0f) {
            n -= 360.0f;
        }
        if (n < -180.0f) {
            n += 360.0f;
        }
        return n;
    }
    
    public static double clamp_double(final double n, final double n2, final double n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static float sqrt_double(final double n) {
        return (float)Math.sqrt(n);
    }
    
    public static double func_181160_c(final double n, final double n2, final double n3) {
        return (n - n2) / (n3 - n2);
    }
    
    public static float abs(final float n) {
        return (n >= 0.0f) ? n : (-n);
    }
    
    public static int floor_float(final float n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static float cos(final float n) {
        return MathHelper.fastMath ? MathHelper.SIN_TABLE_FAST[(int)((n + 1.5707964f) * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(n * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static long getCoordinateRandom(final int n, final int n2, final int n3) {
        final long n4 = (long)(n * 3129871) ^ n3 * 116129781L ^ (long)n2;
        return n4 * n4 * 42317861L + n4 * 11L;
    }
    
    public static int func_154354_b(final int n, int n2) {
        if (n2 == 0) {
            return 0;
        }
        if (n == 0) {
            return n2;
        }
        if (n < 0) {
            n2 *= -1;
        }
        final int n3 = n % n2;
        return (n3 == 0) ? n : (n + n2 - n3);
    }
    
    public static int clamp_int(final int n, final int n2, final int n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static int func_180181_b(final int n, final int n2, final int n3) {
        return ((n << 8) + n2 << 8) + n3;
    }
    
    public static boolean epsilonEquals(final float n, final float n2) {
        return abs(n2 - n) < 1.0E-5f;
    }
    
    public static float clamp_float(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static int calculateLogBaseTwo(final int n) {
        return calculateLogBaseTwoDeBruijn(n) - ((n == 0) ? 1 : 0);
    }
    
    public static int ceiling_float_int(final float n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int func_181758_c(final float n, final float n2, final float n3) {
        final int n4 = (int)(n * 6.0f) % 6;
        final float n5 = n * 6.0f - n4;
        final float n6 = n3 * (1.0f - n2);
        final float n7 = n3 * (1.0f - n5 * n2);
        final float n8 = n3 * (1.0f - (1.0f - n5) * n2);
        float n9 = 0.0f;
        float n10 = 0.0f;
        float n11 = 0.0f;
        switch (n4) {
            case 0: {
                n9 = n3;
                n10 = n8;
                n11 = n6;
                break;
            }
            case 1: {
                n9 = n7;
                n10 = n3;
                n11 = n6;
                break;
            }
            case 2: {
                n9 = n6;
                n10 = n3;
                n11 = n8;
                break;
            }
            case 3: {
                n9 = n6;
                n10 = n7;
                n11 = n3;
                break;
            }
            case 4: {
                n9 = n8;
                n10 = n6;
                n11 = n3;
                break;
            }
            case 5: {
                n9 = n3;
                n10 = n6;
                n11 = n7;
                break;
            }
            default: {
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + n + ", " + n2 + ", " + n3);
            }
        }
        return clamp_int((int)(n9 * 255.0f), 0, 255) << 16 | clamp_int((int)(n10 * 255.0f), 0, 255) << 8 | clamp_int((int)(n11 * 255.0f), 0, 255);
    }
    
    public static int func_180188_d(final int n, final int n2) {
        return (n & 0xFF000000) | (int)(((n & 0xFF0000) >> 16) * (float)((n2 & 0xFF0000) >> 16) / 255.0f) << 16 | (int)(((n & 0xFF00) >> 8) * (float)((n2 & 0xFF00) >> 8) / 255.0f) << 8 | (int)(((n & 0xFF) >> 0) * (float)((n2 & 0xFF) >> 0) / 255.0f);
    }
    
    public static double wrapAngleTo180_double(double n) {
        n %= 360.0;
        if (n >= 180.0) {
            n -= 360.0;
        }
        if (n < -180.0) {
            n += 360.0;
        }
        return n;
    }
    
    public static int func_180183_b(final float n, final float n2, final float n3) {
        return func_180181_b(floor_float(n * 255.0f), floor_float(n2 * 255.0f), floor_float(n3 * 255.0f));
    }
    
    public static double average(final long[] array) {
        long n = 0L;
        while (0 < array.length) {
            n += array[0];
            int n2 = 0;
            ++n2;
        }
        return n / (double)array.length;
    }
    
    public static double getRandomDoubleInRange(final Random random, final double n, final double n2) {
        return (n >= n2) ? n : (random.nextDouble() * (n2 - n) + n);
    }
    
    public static double func_181162_h(final double n) {
        return n - Math.floor(n);
    }
    
    public static double abs_max(double n, double n2) {
        if (n < 0.0) {
            n = -n;
        }
        if (n2 < 0.0) {
            n2 = -n2;
        }
        return (n > n2) ? n : n2;
    }
    
    public static float sqrt_float(final float n) {
        return (float)Math.sqrt(n);
    }
}
