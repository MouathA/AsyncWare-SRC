package com.nquantum.util.render;

import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.function.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import com.sun.javafx.geom.*;
import java.util.*;

public class RenderUtil
{
    private static final Frustum frustrum;
    private static int lastWidth;
    private static ICamera camera;
    private static ShaderGroup shaderGroup;
    private static int lastFactor;
    private static final Minecraft mc;
    private static final ArrayList depth;
    private static int lastHeight;
    private static Framebuffer framebuffer;
    private static final Map glCapMap;
    
    private static void glColor(final int n) {
        GlStateManager.color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void drawRoundedRectWithShadow(float n, float n2, float n3, float n4, final int n5, final int n6) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawInternalRoundedRect2(n - 2.5f, n2 - 1.6f, n3 + 2.6f, n4 + 2.5f, new Color(23, 23, 23, 100).getRGB(), 273698896);
        drawInternalRoundedRect2(n - 1.5f, n2 - 1.6f, n3 + 1.6f, n4 + 1.5f, new Color(23, 23, 23, 100).getRGB(), 811622496);
        drawInternalRoundedRect2(n - 0.5f, n2 - 0.6f, n3 + 0.6f, n4 + 0.5f, new Color(23, 23, 23, 100).getRGB(), 1347440720);
        drawVLine(n, n2 + 2.0f, n4 - 3.0f, n5);
        drawVLine(n3 - 1.0f, n2 + 2.0f, n4 - 3.0f, n5);
        drawHLine(n + 3.0f, n3 - 4.0f, n2, n5);
        drawHLine(n3 - 3.0f, n3 - 3.0f, n2, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n2 + 2.0f, n6);
        drawHLine(n + 2.0f, n + 2.0f, n2, n6);
        drawHLine(n + 0.0f, n + 0.0f, n2 + 2.0f, n6);
        drawHLine(n + 0.0f, n + 0.0f, n4 - 3.0f, n6);
        drawHLine(n + 2.0f, n + 2.0f, n4 - 1.0f, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n4 - 3.0f, n6);
        drawHLine(n3 - 3.0f, n3 - 3.0f, n4 - 1.0f, n6);
        drawHLine(n + 3.0f, n3 - 4.0f, n4 - 1.0f, n5);
        Gui.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n5);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void mask() {
        RenderUtil.depth.add(0, GL11.glGetInteger(2932));
        GL11.glEnable(6145);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(513);
        GL11.glColorMask(false, false, false, true);
    }
    
    public static void enableGlCap(final int n) {
        setGlCap(n, true);
    }
    
    public static void drawOutlinedBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawHLine(double n, double n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = (float)n;
            n = n2;
            n2 = n5;
        }
        Gui.drawRect(n, n3, n2 + 1.0, n3 + 1.0f, n4);
    }
    
    public static void pre() {
        if (RenderUtil.depth.isEmpty()) {
            GL11.glClearDepth(1.0);
            GL11.glClear(256);
        }
    }
    
    public static void drawInternalRoundedRect2(float n, float n2, float n3, float n4, final int n5, final int n6) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n, n2 + 5.5f, n4 - 6.5f, n5);
        drawVLine(n + 2.0f, n2 + 3.0f, n4 - 4.0f, n5);
        drawVLine(n3 - 3.0f, n2 + 3.0f, n4 - 4.0f, n5);
        drawVLine(n3 - 1.0f, n2 + 5.5f, n4 - 6.5f, n5);
        drawHLine(n + 6.5f, n3 - 7.5f, n2, n5);
        drawHLine(n + 4.0f, n3 - 4.5f, n2 + 2.0f, n5);
        drawHLine(n3 - 5.5f, n3 - 6.5f, n2, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n2 + 5.5f, n6);
        drawHLine(n + 4.5f, n + 5.5f, n2, n6);
        drawVLine(n + 0.0f, n2 + 3.5f, n2 + 6.5f, n6);
        drawVLine(n + 0.0f, n4 - 7.5f, n4 - 5.0f, n6);
        drawVLine(n + 5.5f, n4 - 2.5f, n4 - 0.0f, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n4 - 6.5f, n6);
        drawHLine(n3 - 5.5f, n3 - 6.5f, n4 - 1.0f, n6);
        drawHLine(n + 4.0f, n3 - 4.5f, n4 - 2.5f, n5);
        drawHLine(n + 6.5f, n3 - 8.0f, n4 - 1.0f, n5);
        Gui.drawRect(n + 3.0f, n2 + 3.0f, n3 - 3.0f, n4 - 3.0f, n5);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void render(final int n) {
        GL11.glDepthFunc(n);
        GL11.glColorMask(true, true, true, true);
    }
    
    public static void blur(final double n, final double n2, final double n3, final double n4, final int n5) {
        Stencil.write(false);
        Draw.drawRoundedRect(n, n2, n3, n4, 1.0f);
        Stencil.erase(true);
        blur(n5);
    }
    
    public static void render() {
        render(514);
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final int[] array = { 10, 0, 30, 15 };
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        instance.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(array[0], array[1], array[2], array[3]).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(array[0], array[1], array[2], array[3]).endVertex();
        instance.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public static void drawVLine(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        if (n5 <= 0.0f) {
            return;
        }
        GL11.glPushMatrix();
        final float n7 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n6 & 0xFF) / 255.0f;
        final int glGetInteger = GL11.glGetInteger(2900);
        GlStateManager.shadeModel(7425);
        GL11.glColor4f(n8, n9, n10, n7);
        final float glGetFloat = GL11.glGetFloat(2849);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex3d((double)n, (double)n2, 0.0);
        GL11.glVertex3d((double)n3, (double)n4, 0.0);
        GL11.glEnd();
        GlStateManager.shadeModel(glGetInteger);
        GL11.glLineWidth(glGetFloat);
        GL11.glPopMatrix();
    }
    
    public static void drawSolidEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void blur(final int values) {
        final ScaledResolution scaledResolution = new ScaledResolution(RenderUtil.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        if (scaledWidth != scaledHeight || RenderUtil.framebuffer == null || RenderUtil.shaderGroup == null) {}
        RenderUtil.lastFactor = scaleFactor;
        RenderUtil.lastWidth = scaledWidth;
        RenderUtil.lastHeight = scaledHeight;
        setValues(values);
        RenderUtil.framebuffer.bindFramebuffer(true);
        RenderUtil.shaderGroup.loadShaderGroup(RenderUtil.mc.timer.renderPartialTicks);
        RenderUtil.mc.getFramebuffer().bindFramebuffer(true);
    }
    
    public static void disableGlCap(final int n) {
        setGlCap(n, true);
    }
    
    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public static void blurMove(final double n, final double n2, final double n3, final double n4, final int values) {
        final ScaledResolution scaledResolution = new ScaledResolution(RenderUtil.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        if (scaledWidth != scaledHeight || RenderUtil.framebuffer == null || RenderUtil.shaderGroup == null) {}
        RenderUtil.lastFactor = scaleFactor;
        RenderUtil.lastWidth = scaledWidth;
        RenderUtil.lastHeight = scaledHeight;
        setValues(values);
        RenderUtil.framebuffer.bindFramebuffer(true);
        Stencil.write(false);
        Draw.drawRoundedRect(n, n2, n3, n4, 1.0f);
        Stencil.erase(true);
        RenderUtil.shaderGroup.loadShaderGroup(RenderUtil.mc.timer.renderPartialTicks);
        RenderUtil.shaderGroup.loadShaderGroup(RenderUtil.mc.timer.renderPartialTicks);
        RenderUtil.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
        RenderUtil.mc.getFramebuffer().bindFramebuffer(true);
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        return RenderUtil.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static void enableGlCap(final int... array) {
        while (0 < array.length) {
            setGlCap(array[0], true);
            int n = 0;
            ++n;
        }
    }
    
    public static void drawRectSized(final float n, final float n2, final float n3, final float n4, final int n5) {
        drawRect(n, n2, n + n3, n2 + n4, n5);
    }
    
    public static void drawInternalRoundedRect3(float n, float n2, float n3, float n4, final int n5, final int n6) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n, n2 + 4.0f, n4 - 5.0f, n5);
        drawVLine(n3 - 1.0f, n2 + 4.0f, n4 - 5.0f, n5);
        drawHLine(n + 5.0f, n3 - 6.0f, n2, n5);
        drawHLine(n3 - 4.0f, n3 - 4.0f, n2, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n2 + 3.0f, n6);
        drawHLine(n + 3.0f, n + 3.0f, n2, n6);
        drawHLine(n + 0.0f, n + 0.0f, n2 + 3.0f, n6);
        drawHLine(n + 0.0f, n + 0.0f, n4 - 4.0f, n6);
        drawHLine(n + 2.0f, n + 2.0f, n4 - 0.0f, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n4 - 4.0f, n6);
        drawHLine(n3 - 4.0f, n3 - 4.0f, n4 - 1.0f, n6);
        drawHLine(n + 5.0f, n3 - 6.0f, n4 - 1.0f, n5);
        Gui.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n5);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void setGlCap(final int n, final boolean b) {
        RenderUtil.glCapMap.put(n, GL11.glGetBoolean(n));
        setGlState(n, b);
    }
    
    private static boolean isInViewFrustrum(final AxisAlignedBB axisAlignedBB) {
        final Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        RenderUtil.frustrum.setPosition(renderViewEntity.posX, renderViewEntity.posY, renderViewEntity.posZ);
        return RenderUtil.frustrum.isBoundingBoxInFrustum(axisAlignedBB);
    }
    
    static {
        glCapMap = new HashMap();
        depth = new ArrayList();
        frustrum = new Frustum();
        RenderUtil.camera = new Frustum();
        mc = Minecraft.getMinecraft();
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
    }
    
    public static void setupLineSmooth() {
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glHint(3154, 4354);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(32925);
        GL11.glEnable(32926);
        GL11.glShadeModel(7425);
    }
    
    public static void drawTracerLine(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0, 0.0 + Minecraft.getMinecraft().thePlayer.getEyeHeight() - 0.2, 0.0);
        GL11.glVertex3d(n, n2, n3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static double lerp(final double n, final double n2, final double n3) {
        return (1.0 - n3) * n + n3 * n2;
    }
    
    public static void drawBlockBox(final BlockPos blockPos, final Color color, final boolean b) {
        final RenderManager renderManager = RenderUtil.mc.getRenderManager();
        final Timer timer = RenderUtil.mc.timer;
        final double n = blockPos.getX() - renderManager.renderPosX;
        final double n2 = blockPos.getY() - renderManager.renderPosY;
        final double n3 = blockPos.getZ() - renderManager.renderPosZ;
        AxisAlignedBB offset = new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0);
        final Block block = getBlock(blockPos);
        if (block != null) {
            final EntityPlayerSP thePlayer = RenderUtil.mc.thePlayer;
            offset = block.getSelectedBoundingBox(RenderUtil.mc.theWorld, blockPos).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).offset(-(thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * timer.renderPartialTicks), -(thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * timer.renderPartialTicks), -(thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * timer.renderPartialTicks));
        }
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        glColor(color.getRed(), color.getGreen(), color.getBlue(), (color.getAlpha() != 255) ? color.getAlpha() : (b ? 26 : 35));
        drawFilledBox(offset);
        if (b) {
            GL11.glLineWidth(1.0f);
            enableGlCap(2848);
            glColor(color);
            drawSelectionBoundingBox(offset);
        }
        GL11.glDepthMask(true);
        RenderUtil.glCapMap.forEach(RenderUtil::setGlState);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
    }
    
    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
    }
    
    public static void drawRoundedShadow(float n, float n2, float n3, float n4) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawInternalRoundedRect2(n - 2.5f, n2 - 1.6f, n3 + 2.6f, n4 + 2.5f, 810569808, 273698896);
        drawInternalRoundedRect2(n - 1.5f, n2 - 1.6f, n3 + 1.6f, n4 + 1.5f, 1347440720, 811622496);
        drawInternalRoundedRect2(n - 0.5f, n2 - 0.6f, n3 + 0.6f, n4 + 0.5f, 1615876176, 1347440720);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void init() {
        (RenderUtil.shaderGroup = new ShaderGroup(RenderUtil.mc.getTextureManager(), RenderUtil.mc.getResourceManager(), RenderUtil.mc.getFramebuffer(), new ResourceLocation("blur.json"))).createBindFramebuffers(RenderUtil.mc.displayWidth, RenderUtil.mc.displayHeight);
        RenderUtil.framebuffer = RenderUtil.shaderGroup.mainFramebuffer;
    }
    
    public static void drawInternalRoundedRect1(float n, float n2, float n3, float n4, final int n5, final int n6) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n, n2 + 4.0f, n4 - 5.0f, n5);
        drawVLine(n3 - 1.0f, n2 + 4.0f, n4 - 5.0f, n5);
        drawHLine(n + 5.0f, n3 - 6.0f, n2, n5);
        drawHLine(n3 - 4.0f, n3 - 4.0f, n2, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n2 + 3.0f, n6);
        drawHLine(n + 3.0f, n + 3.0f, n2, n6);
        drawHLine(n + 0.0f, n + 0.0f, n2 + 3.0f, n6);
        drawHLine(n + 0.0f, n + 0.0f, n4 - 4.0f, n6);
        drawHLine(n + 2.0f, n + 2.0f, n4 - 0.0f, n6);
        drawHLine(n3 - 1.0f, n3 - 1.0f, n4 - 4.0f, n6);
        drawHLine(n3 - 4.0f, n3 - 4.0f, n4 - 1.0f, n6);
        drawHLine(n + 5.0f, n3 - 6.0f, n4 - 1.0f, n5);
        Gui.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n5);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n4, n5, n6, n7);
        drawBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glLineWidth(n12);
        GL11.glColor4f(n8, n9, n10, n11);
        drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glLineWidth(n14);
        GL11.glColor4f(n10, n11, n12, n13);
        drawOutlinedBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }
    
    public static void drawRect(double n, double n2, double n3, double n4, final int n5) {
        if (n < n3) {
            final double n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n9, n10, n11, n8);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(n, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n2, 0.0).endVertex();
        worldRenderer.pos(n, n2, 0.0).endVertex();
        instance.draw();
    }
    
    public static void drawSolidBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n4, n5, n6, n7);
        drawBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static int reAlpha(final int n, final float n2) {
        final Color color = new Color(n);
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), n2).getRGB();
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawVLine(final double n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        Gui.drawRect(n, n2 + 1.0f, n + 1.0, n3, n4);
    }
    
    public static void drawOutlinedEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawOutlinedBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    private static void setValues(final int n) {
        while (true) {
            RenderUtil.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set((float)n);
            int n2 = 0;
            ++n2;
        }
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4) {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
    }
    
    public static void renderRoundedQuad(final Vec3d vec3d, final Vec3d vec3d2, final int n, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glBegin(9);
        final double[] array = (new double[][] { { vec3d2.x, vec3d2.y }, { vec3d2.x, vec3d.y }, { vec3d.x, vec3d.y }, { vec3d.x, vec3d2.y } })[0];
        final int n2;
        n2 += 90;
        while (true) {
            final double radians = Math.toRadians(-90);
            GL11.glVertex2d(array[0] + Math.sin(radians) * n, array[1] + Math.cos(radians) * n);
            int n3 = 0;
            ++n3;
        }
    }
    
    public static double interpolate(final double n, final double n2, final double n3) {
        return n2 + (n - n2) * n3;
    }
    
    public static boolean isInViewFrustrum(final Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    public static void drawBox(final BlockPos blockPos, final Color color) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, blockPos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - RenderUtil.mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - RenderUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(axisAlignedBB.minX + RenderUtil.mc.getRenderManager().viewerPosX, axisAlignedBB.minY + RenderUtil.mc.getRenderManager().viewerPosY, axisAlignedBB.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, axisAlignedBB.maxX + RenderUtil.mc.getRenderManager().viewerPosX, axisAlignedBB.maxY + RenderUtil.mc.getRenderManager().viewerPosY, axisAlignedBB.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
        }
    }
    
    public static void disableGlCap(final int... array) {
        while (0 < array.length) {
            setGlCap(array[0], false);
            int n = 0;
            ++n;
        }
    }
    
    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void post() {
        GL11.glDepthFunc((int)RenderUtil.depth.get(0));
        RenderUtil.depth.remove(0);
    }
    
    public static void setGlState(final int n, final boolean b) {
        if (b) {
            GL11.glEnable(n);
        }
        else {
            GL11.glDisable(n);
        }
    }
    
    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }
    
    public static void glColor(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(n / 255.0f, n2 / 255.0f, n3 / 255.0f, n4 / 255.0f);
    }
    
    public static void glColor(final Color color) {
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
}
