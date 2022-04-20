package com.nquantum.util.render;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class Draw
{
    public static void drawCirclePart(final double n, final double n2, final float n3, final float n4, final float n5, final int n6) {
        GL11.glBegin(6);
        GL11.glVertex2d(n, n2);
        final float n7 = (n4 - n3) / n6;
        while (0 <= n6) {
            final float n8 = n3 + 0 * n7;
            GL11.glVertex2d(n + MathHelper.sin(n8) * n5, n2 + MathHelper.cos(n8) * n5);
            int n9 = 0;
            ++n9;
        }
        GL11.glEnd();
    }
    
    public static void drawRoundedRect(final double n, final double n2, final double n3, final double n4, final float n5) {
        drawFillRectangle(n + n5, n2, n3 - 2.0f * n5, n4);
        drawFillRectangle(n, n2 + n5, n5, n4 - 2.0f * n5);
        drawFillRectangle(n + n3 - n5, n2 + n5, n5, n4 - 2.0f * n5);
        drawCirclePart(n + n5, n2 + n5, -3.1415927f, -1.5707964f, n5, 10);
        drawCirclePart(n + n5, n2 + n4 - n5, -1.5707964f, 0.0f, n5, 10);
        drawCirclePart(n + n3 - n5, n2 + n5, 1.5707964f, 3.1415927f, n5, 10);
        drawCirclePart(n + n3 - n5, n2 + n4 - n5, 0.0f, 1.5707964f, n5, 10);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public static void color(final int n) {
        GlStateManager.color((n & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n >> 16 & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void colorRGBA(final int n) {
        GlStateManager.color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void drawFillRectangle(final double n, final double n2, final double n3, final double n4) {
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n2 + n4);
        GL11.glVertex2d(n + n3, n2 + n4);
        GL11.glVertex2d(n + n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
    }
}
