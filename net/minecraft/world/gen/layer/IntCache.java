package net.minecraft.world.gen.layer;

import com.google.common.collect.*;
import java.util.*;

public class IntCache
{
    private static List freeLargeArrays;
    private static List inUseLargeArrays;
    private static List freeSmallArrays;
    private static List inUseSmallArrays;
    
    static {
        IntCache.freeSmallArrays = Lists.newArrayList();
        IntCache.inUseSmallArrays = Lists.newArrayList();
        IntCache.freeLargeArrays = Lists.newArrayList();
        IntCache.inUseLargeArrays = Lists.newArrayList();
    }
    
    public static synchronized int[] getIntCache(final int intCacheSize) {
        if (intCacheSize <= 256) {
            if (IntCache.freeSmallArrays.isEmpty()) {
                final int[] array = new int[256];
                IntCache.inUseSmallArrays.add(array);
                return array;
            }
            final int[] array2 = IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - 1);
            IntCache.inUseSmallArrays.add(array2);
            return array2;
        }
        else {
            if (intCacheSize > 256) {
                IntCache.intCacheSize = intCacheSize;
                IntCache.freeLargeArrays.clear();
                IntCache.inUseLargeArrays.clear();
                final int[] array3 = new int[256];
                IntCache.inUseLargeArrays.add(array3);
                return array3;
            }
            if (IntCache.freeLargeArrays.isEmpty()) {
                final int[] array4 = new int[256];
                IntCache.inUseLargeArrays.add(array4);
                return array4;
            }
            final int[] array5 = IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - 1);
            IntCache.inUseLargeArrays.add(array5);
            return array5;
        }
    }
    
    public static synchronized String getCacheSizes() {
        return "cache: " + IntCache.freeLargeArrays.size() + ", tcache: " + IntCache.freeSmallArrays.size() + ", allocated: " + IntCache.inUseLargeArrays.size() + ", tallocated: " + IntCache.inUseSmallArrays.size();
    }
    
    public static synchronized void resetIntCache() {
        if (!IntCache.freeLargeArrays.isEmpty()) {
            IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - 1);
        }
        if (!IntCache.freeSmallArrays.isEmpty()) {
            IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - 1);
        }
        IntCache.freeLargeArrays.addAll(IntCache.inUseLargeArrays);
        IntCache.freeSmallArrays.addAll(IntCache.inUseSmallArrays);
        IntCache.inUseLargeArrays.clear();
        IntCache.inUseSmallArrays.clear();
    }
}
