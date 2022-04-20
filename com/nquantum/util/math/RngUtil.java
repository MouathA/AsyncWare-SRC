package com.nquantum.util.math;

import java.security.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;
import java.util.concurrent.*;
import java.math.*;

public enum RngUtil
{
    private static final RngUtil[] $VALUES;
    
    INSTANCE("INSTANCE", 0);
    
    public static double getIncre(final double n, final double n2) {
        final double n3 = 1.0 / n2;
        return Math.round(n * n3) / n3;
    }
    
    public static double secRanDouble(final double n, final double n2) {
        return new SecureRandom().nextDouble() * (n2 - n) + n;
    }
    
    private RngUtil(final String s, final int n) {
    }
    
    public static float getRandomInRange(final float n, final float n2) {
        return new Random().nextFloat() * (n2 - n) + n;
    }
    
    public static float secRanFloat(final float n, final float n2) {
        return new SecureRandom().nextFloat() * (n2 - n) + n;
    }
    
    public static int getJumpEffect() {
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump)) {
            return Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static double newRound(final double n, final int n2) {
        final int n3 = (int)Math.pow(10.0, n2);
        return Math.round(n * n3) / (double)n3;
    }
    
    public static int secRanInt(final int n, final int n2) {
        return new SecureRandom().nextInt() * (n2 - n) + n;
    }
    
    public static int getRandomInRange(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }
    
    public static float round(float n, final float n2, final boolean b) {
        if (b) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                n -= n % n2;
            }
            else {
                n += n % n2;
            }
        }
        else {
            n -= n % n2;
        }
        return n;
    }
    
    public static double getRandomInRange(final double n, final double n2) {
        double n3 = new Random().nextDouble() * (n2 - n);
        if (n3 > n2) {
            n3 = n2;
        }
        double n4 = n3 + n;
        if (n4 > n2) {
            n4 = n2;
        }
        return n4;
    }
    
    public static float setRandom(final float n, final float n2) {
        return n + new Random().nextFloat() * (n2 - n);
    }
    
    static {
        $VALUES = new RngUtil[] { RngUtil.INSTANCE };
    }
    
    public static int getRandomInteger(final int n, final int n2) {
        return (int)(Math.random() * (n - n2)) + n2;
    }
    
    public static double preciseRound(final double n, final double n2) {
        final double pow = Math.pow(10.0, n2);
        return Math.round(n * pow) / pow;
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double setRandom(final double n, final double n2) {
        return n + new Random().nextDouble() * (n2 - n);
    }
    
    public static double roundToPlace(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float round(float n, final float n2) {
        return n %= n2;
    }
    
    public static double defaultSpeed() {
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static double map(final double n, final double n2, final double n3, final double n4) {
        return Math.min(Math.max(n3 + n / n2 * (n4 - n3), n3), n4);
    }
    
    public static double roundPlace(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
}
