package optfine;

import net.minecraft.client.renderer.*;

public class Blender
{
    public static final int BLEND_REPLACE = 7;
    public static final int BLEND_ALPHA = 0;
    public static final int BLEND_ADD = 1;
    public static final int BLEND_DEFAULT = 1;
    public static final int BLEND_SUBSTRACT = 2;
    public static final int BLEND_MULTIPLY = 3;
    public static final int BLEND_DODGE = 4;
    public static final int BLEND_BURN = 5;
    public static final int BLEND_SCREEN = 6;
    
    public static int parseBlend(String trim) {
        if (trim == null) {
            return 1;
        }
        trim = trim.toLowerCase().trim();
        if (trim.equals("alpha")) {
            return 0;
        }
        if (trim.equals("add")) {
            return 1;
        }
        if (trim.equals("subtract")) {
            return 2;
        }
        if (trim.equals("multiply")) {
            return 3;
        }
        if (trim.equals("dodge")) {
            return 4;
        }
        if (trim.equals("burn")) {
            return 5;
        }
        if (trim.equals("screen")) {
            return 6;
        }
        if (trim.equals("replace")) {
            return 7;
        }
        Config.warn("Unknown blend: " + trim);
        return 1;
    }
    
    public static void clearBlend(final float n) {
        GlStateManager.blendFunc(770, 1);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
    }
    
    public static void setupBlend(final int n, final float n2) {
        switch (n) {
            case 0: {
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
            case 1: {
                GlStateManager.blendFunc(770, 1);
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
            case 2: {
                GlStateManager.blendFunc(775, 0);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 3: {
                GlStateManager.blendFunc(774, 771);
                GlStateManager.color(n2, n2, n2, n2);
                break;
            }
            case 4: {
                GlStateManager.blendFunc(1, 1);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 5: {
                GlStateManager.blendFunc(0, 769);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 6: {
                GlStateManager.blendFunc(1, 769);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 7: {
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
        }
    }
}
