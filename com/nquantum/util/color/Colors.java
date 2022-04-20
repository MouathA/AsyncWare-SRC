package com.nquantum.util.color;

import java.awt.*;
import com.nquantum.*;

public class Colors
{
    public static int B;
    public static int R;
    public static int G;
    public static int hudColor;
    
    public static int getFade2(final float n, final float n2, final float n3, final long n4) {
        return Color.HSBtoRGB((System.currentTimeMillis() + n4) % (int)(n * 1000.0f) / (n * 1000.0f), n2, n3);
    }
    
    public static int modifyColorAlpha(final int n, final int n2) {
        return getRgba(n >> 16 & 0xFF, n >> 8 & 0xFF, n * 255, n2);
    }
    
    public static int RGB(final float n, final float n2) {
        return Color.HSBtoRGB(System.currentTimeMillis() % 5000L / 5000.0f, n, n2);
    }
    
    public static int getColor(final int n, final int n2, final int n3) {
        return getColor(n, n2, n3, 255);
    }
    
    public static int fadeBetween(final int n, final int n2, float n3) {
        if (n3 > 1.0f) {
            n3 = 1.0f - n3 % 1.0f;
        }
        final double n4 = 1.0f - n3;
        return ((int)((n >> 24 & 0xFF) * n4 + (n2 >> 24 & 0xFF) * n3) & 0xFF) << 24 | ((int)((n >> 16 & 0xFF) * n4 + (n2 >> 16 & 0xFF) * n3) & 0xFF) << 16 | ((int)((n >> 8 & 0xFF) * n4 + (n2 >> 8 & 0xFF) * n3) & 0xFF) << 8 | ((int)((n & 0xFF) * n4 + (n2 & 0xFF) * n3) & 0xFF);
    }
    
    public static int multiplyColor(final int n, final float n2) {
        return getRgba((int)((n >> 16 & 0xFF) * n2), (int)((n >> 8 & 0xFF) * n2), (int)((n & 0xFF) * n2), n << 24 & 0xFF);
    }
    
    public static int RGB() {
        return Color.HSBtoRGB(System.currentTimeMillis() % 5000L / 5000.0f, 1.0f, 1.0f);
    }
    
    public static int getRgb(final int n, final int n2, final int n3) {
        return getRgba(n, n2, n3, 255);
    }
    
    public static int getFade(final float n, final float n2, final float n3) {
        return Color.HSBtoRGB(System.currentTimeMillis() % (int)(n * 10000.0f) / n * 10000.0f, n2, n3);
    }
    
    public static int getRgba(final int n, final int n2, final int n3, final int n4) {
        return (n4 & 0xFF000000) | (n & 0xFF0000) | (n2 & 0xFF00) | (n3 & 0xFF);
    }
    
    public static int getColor(final int n, final int n2, final int n3, final int n4) {
        return 0;
    }
    
    public static int getColor(final int n, final int n2) {
        return getColor(n, n, n, n2);
    }
    
    public static int RGBX(final float n, final float n2, final float n3, final long n4) {
        return Color.HSBtoRGB((System.currentTimeMillis() + n4) % (int)(n * 5000.0f) / (n * 5000.0f), 0.5f, 1.0f);
    }
    
    public static int darker(final int n, final float n2) {
        return ((int)((n >> 16 & 0xFF) * n2) & 0xFF) << 16 | ((int)((n >> 8 & 0xFF) * n2) & 0xFF) << 8 | ((int)((n & 0xFF) * n2) & 0xFF) | (n >> 24 & 0xFF & 0xFF) << 24;
    }
    
    public static int Astolfo(final int n, final float n2, final float n3) {
        final double n4;
        return Color.getHSBColor(((float)((n4 = Math.ceil((double)(System.currentTimeMillis() + n * 100)) / 5.0 % 360.0) / 360.0) < 0.5) ? (-(float)(n4 / 360.0)) : ((float)(n4 / 360.0)), n3, n2).getRGB();
    }
    
    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int n) {
        return getColor(n, n, n, 255);
    }
    
    static {
        Colors.R = (int)Asyncware.instance.settingsManager.getSettingByName("HUD R").getValDouble();
        Colors.G = (int)Asyncware.instance.settingsManager.getSettingByName("HUD G").getValDouble();
        Colors.B = (int)Asyncware.instance.settingsManager.getSettingByName("HUD B").getValDouble();
        Colors.hudColor = new Color(Colors.R, Colors.G, Colors.B, 255).getRGB();
    }
}
