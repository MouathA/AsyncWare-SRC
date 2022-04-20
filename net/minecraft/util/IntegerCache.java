package net.minecraft.util;

public class IntegerCache
{
    private static final Integer[] field_181757_a;
    
    public static Integer func_181756_a(final int n) {
        return (n > 0 && n < IntegerCache.field_181757_a.length) ? IntegerCache.field_181757_a[n] : Integer.valueOf(n);
    }
    
    static {
        field_181757_a = new Integer[65535];
        while (0 < IntegerCache.field_181757_a.length) {
            IntegerCache.field_181757_a[0] = 0;
            int n = 0;
            ++n;
        }
    }
}
