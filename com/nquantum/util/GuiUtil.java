package com.nquantum.util;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class GuiUtil extends Gui
{
    private static final Minecraft mc;
    public static GuiUtil instance;
    
    public static void drawChromaStringAstolfo(final String s, final int n, final int n2, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < s.split("\n").length) {
            int n3 = n;
            final char[] charArray = s.split("\n")[0].toCharArray();
            while (0 < charArray.length) {
                final char c = charArray[0];
                final long n4 = System.currentTimeMillis() - n3 * 10L - (n2 - 0 * 10L) * 10L;
                final float n5 = 2000.0f;
                drawString(String.valueOf(c), n3, n2 + 0 * (GuiUtil.mc.fontRendererObj.FONT_HEIGHT + 2), Color.HSBtoRGB(n4 % (int)n5 / n5, 0.6f, 0.8f), b);
                n3 += GuiUtil.mc.fontRendererObj.getStringWidth(String.valueOf(c));
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    public static void drawPartialCircle(final float n, final float n2, final float n3, final int n4, final int n5, final float n6, final Color color, final boolean b) {
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        if (b) {
            GL11.glEnable(2848);
        }
        else {
            GL11.glDisable(2848);
        }
        GL11.glLineWidth(n6);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glBegin(3);
        final float n7 = 0.017453292f;
        while (true) {
            final float n8 = -90 * n7;
            GL11.glVertex2f(n + (float)Math.cos(n8) * n3, n2 + (float)Math.sin(n8) * n3);
            int n9 = 0;
            ++n9;
        }
    }
    
    public static void drawScaledCs(final String s, final float n, final float n2, final boolean b) {
        GL11.glPushMatrix();
        GuiUtil.mc.fontRendererObj.drawString(s, n - 0.7f, n2 - 0.7f, new Color(0, 0, 0).getRGB(), false);
        GuiUtil.mc.fontRendererObj.drawString(s, n + 0.7f, n2 + 0.7f, new Color(0, 0, 0).getRGB(), false);
        GL11.glPopMatrix();
        GuiUtil.mc.fontRendererObj.drawString(s, n - 0.2f, n2 - 0.2f, -1, false);
    }
    
    private static void drawRoundedRect(final float n, final float n2, final float n3, final float n4, final float n5) {
        final float n6 = 90.0f / 18;
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glBegin(5);
        GL11.glVertex2f(n + n5, n2);
        GL11.glVertex2f(n + n5, n4);
        GL11.glVertex2f(n3 - n5, n2);
        GL11.glVertex2f(n3 - n5, n4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(n, n2 + n5);
        GL11.glVertex2f(n + n5, n2 + n5);
        GL11.glVertex2f(n, n4 - n5);
        GL11.glVertex2f(n + n5, n4 - n5);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(n3, n2 + n5);
        GL11.glVertex2f(n3 - n5, n2 + n5);
        GL11.glVertex2f(n3, n4 - n5);
        GL11.glVertex2f(n3 - n5, n4 - n5);
        GL11.glEnd();
        GL11.glBegin(6);
        final float n7 = n3 - n5;
        final float n8 = n2 + n5;
        GL11.glVertex2f(n7, n8);
        while (true) {
            final float n9 = 0 * n6;
            GL11.glVertex2f((float)(n7 + n5 * Math.cos(Math.toRadians(n9))), (float)(n8 - n5 * Math.sin(Math.toRadians(n9))));
            int n10 = 0;
            ++n10;
        }
    }
    
    public static void drawRectOutline(final int n, final int n2, final int n3, final int n4, final int n5) {
        Gui.drawRect(n - 1, n2 - 1, n3 + 1, n2, n5);
        Gui.drawRect(n3, n2, n3 + 1, n4, n5);
        Gui.drawRect(n - 1, n4, n3 + 1, n4 + 1, n5);
        Gui.drawRect(n - 1, n2, n, n4, n5);
    }
    
    public static int drawCenteredString(final String s, final int n, final int n2, final int n3) {
        return drawString(s, n - GuiUtil.mc.fontRendererObj.getStringWidth(s) / 2, n2, n3);
    }
    
    public static void drawFilledShape(final float[] array, final Color color, final boolean b) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        if (b) {
            GL11.glEnable(2848);
        }
        else {
            GL11.glDisable(2848);
        }
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glBegin(9);
        while (0 < array.length) {
            GL11.glVertex2f(array[0], array[1]);
            final int n;
            n += 2;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static void drawRoundedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        GlStateManager.color((n6 >> 16 & 0xFF) / 255.0f, (n6 >> 8 & 0xFF) / 255.0f, (n6 & 0xFF) / 255.0f, (n6 >> 24 & 0xFF) / 255.0f);
        drawRoundedRect(n, n2, n3, n4, n5);
    }
    
    public static void drawChromaString(final String s, final int n, final int n2, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < s.split("\n").length) {
            int n3 = n;
            final char[] charArray = s.split("\n")[0].toCharArray();
            while (0 < charArray.length) {
                final char c = charArray[0];
                final long n4 = System.currentTimeMillis() - n3 * 10L - (n2 - 0 * 10L) * 10L;
                final float n5 = 1000.0f;
                drawString(String.valueOf(c), n3, n2 + 0 * (GuiUtil.mc.fontRendererObj.FONT_HEIGHT + 2), Color.HSBtoRGB(n4 % (int)n5 / n5, 0.8f, 0.8f), b);
                n3 += GuiUtil.mc.fontRendererObj.getStringWidth(String.valueOf(c));
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    public static void drawLines(final float[] array, final float n, final Color color, final boolean b) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        if (b) {
            GL11.glEnable(2848);
        }
        else {
            GL11.glDisable(2848);
        }
        GL11.glLineWidth(n);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glBegin(1);
        while (0 < array.length) {
            GL11.glVertex2f(array[0], array[1]);
            final int n2;
            n2 += 2;
        }
        GL11.glEnd();
        GL11.glEnable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static int drawScaledString(final String s, final int n, final int n2, final boolean b, final float n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.scale(n3, n3, 1.0f);
        final int drawString = drawString(s, (int)(n / n3), (int)(n2 / n3), b);
        GlStateManager.scale(Math.pow(n3, -1.0), Math.pow(n3, -1.0), 1.0);
        return drawString;
    }
    
    public static int drawString(final String s, final int n, final int n2, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return drawString(s, n, n2, 16777215, b);
    }
    
    public static int drawCenteredString(final String s, final int n, final int n2, final int n3, final boolean b) {
        return drawString(s, n - GuiUtil.mc.fontRendererObj.getStringWidth(s) / 2, n2, n3, b);
    }
    
    public static int drawString(final String s, final int n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return drawString(s, n, n2, n3, false);
    }
    
    public static void drawFilledRect(final float n, final float n2, final float n3, final float n4, final int n5, final boolean b) {
        drawFilledShape(new float[] { n, n2, n, n4, n3, n4, n3, n2 }, new Color(n5, true), b);
    }
    
    public static int drawString(final String s, final int n, final int n2) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return drawString(s, n, n2, 16777215);
    }
    
    public static void drawRoundedRect(final int n, final int n2, final int n3, final int n4, final float n5, final int n6) {
        GlStateManager.color((n6 >> 16 & 0xFF) / 255.0f, (n6 >> 8 & 0xFF) / 255.0f, (n6 & 0xFF) / 255.0f, (n6 >> 24 & 0xFF) / 255.0f);
        drawRoundedRect((float)n, (float)n2, (float)n3, (float)n4, n5);
    }
    
    public static void drawVerticalLine(final float n, final float n2, final float n3, final float n4, final int n5, final boolean b) {
        drawLines(new float[] { n, n2, n, n3 }, n4, new Color(n5, true), b);
    }
    
    public static int drawString(final String s, final int n, final int n2, final int n3, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final String[] split = s.split("\n");
        if (split.length > 1) {
            while (0 < split.length) {
                final int n4 = 0 + GuiUtil.mc.fontRendererObj.drawString(split[0], (float)n, (float)(n2 + 0 * (GuiUtil.mc.fontRendererObj.FONT_HEIGHT + 2)), n3, b);
                int n5 = 0;
                ++n5;
            }
            return 0;
        }
        return GuiUtil.mc.fontRendererObj.drawString(s, (float)n, (float)n2, n3, b);
    }
    
    public static int drawScaledCenteredString(final String s, final int n, final int n2, final boolean b, final float n3) {
        return drawScaledString(s, n - GuiUtil.mc.fontRendererObj.getStringWidth(s) / 2, n2, b, n3);
    }
    
    public static int drawCenteredString(final String s, final int n, final int n2) {
        return drawString(s, n - GuiUtil.mc.fontRendererObj.getStringWidth(s) / 2, n2);
    }
    
    public static int drawString(final String s, final float n, final float n2, final int n3, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return GuiUtil.mc.fontRendererObj.drawString(s, n, n2, n3, b);
    }
    
    public static void drawPartialCircle(final int n, final int n2, final float n3, final int n4, final int n5, final float n6, final Color color, final boolean b) {
        drawPartialCircle(n, n2, n3, n4, n5, n6, color, b);
    }
    
    public static void drawCircle(final float n, final float n2, final float n3, final float n4, final Color color, final boolean b) {
        drawPartialCircle(n, n2, n3, 0, 360, n4, color, b);
    }
    
    public static void drawLine(final float n, final float n2, final float n3, final float n4, final int n5, final boolean b) {
        drawLines(new float[] { n, n3, n2, n3 }, n4, new Color(n5, true), b);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public GuiUtil() {
        GuiUtil.instance = this;
    }
    
    public static int drawScaledString(final String s, final int n, final int n2, final boolean b, final float n3, final boolean b2) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.scale(n3, n3, 1.0f);
        final int drawString = drawString(s, (int)(n / n3), (int)(n2 / n3), b);
        GlStateManager.scale(Math.pow(n3, -1.0), Math.pow(n3, -1.0), 1.0);
        return drawString;
    }
    
    public static void drawChromaStringModern(final String s, final int n, final int n2, final boolean b) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < s.split("\n").length) {
            final char[] charArray = s.split("\n")[0].toCharArray();
            while (0 < charArray.length) {
                final char c = charArray[0];
                final long n3 = System.currentTimeMillis() - n * 10L - (n2 - 0 * 10L) * 10L;
                final float n4 = 2000.0f;
                Color.HSBtoRGB(n3 % (int)n4 / n4, 0.8f, 0.8f);
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
    }
    
    public void drawGradientRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator.getInstance().getWorldRenderer();
        GlStateManager.shadeModel(7424);
    }
}
