package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class Gui
{
    public static final ResourceLocation optionsBackground;
    public static final ResourceLocation icons;
    protected float zLevel;
    public static final ResourceLocation statIcons;
    
    public static void drawScaledCustomSizeModalRect(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        final float n11 = 1.0f / n9;
        final float n12 = 1.0f / n10;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n, n2 + n8, 0.0).tex(n3 * n11, (n4 + n6) * n12).endVertex();
        worldRenderer.pos(n + n7, n2 + n8, 0.0).tex((n3 + n5) * n11, (n4 + n6) * n12).endVertex();
        worldRenderer.pos(n + n7, n2, 0.0).tex((n3 + n5) * n11, n4 * n12).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n11, n4 * n12).endVertex();
        instance.draw();
    }
    
    public static void drawModalRectWithCustomSizedTexture(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        final float n9 = 1.0f / n7;
        final float n10 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n, n2 + n6, 0.0).tex(n3 * n9, (n4 + n6) * n10).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, 0.0).tex((n3 + n5) * n9, (n4 + n6) * n10).endVertex();
        worldRenderer.pos(n + n5, n2, 0.0).tex((n3 + n5) * n9, n4 * n10).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n9, n4 * n10).endVertex();
        instance.draw();
    }
    
    public void drawCenteredString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3, final boolean b) {
        fontRenderer.drawString(s, (float)(n - fontRenderer.getStringWidth(s) / 2), (float)n2, n3);
    }
    
    static {
        optionsBackground = new ResourceLocation("textures/gui/options_background.png");
        statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
        icons = new ResourceLocation("textures/gui/icons.png");
    }
    
    protected void drawGradientRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
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
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(n3, n2, this.zLevel).color(n8, n9, n10, n7).endVertex();
        worldRenderer.pos(n, n2, this.zLevel).color(n8, n9, n10, n7).endVertex();
        worldRenderer.pos(n, n4, this.zLevel).color(n12, n13, n14, n11).endVertex();
        worldRenderer.pos(n3, n4, this.zLevel).color(n12, n13, n14, n11).endVertex();
        instance.draw();
        GlStateManager.shadeModel(7424);
    }
    
    protected void drawVerticalLine(final int n, int n2, int n3, final int n4) {
        if (n3 < n2) {
            final int n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        drawRect(n, n2 + 1, n + 1, n3, n4);
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
    
    public void drawTexturedModalRect(final float n, final float n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + 0.0f, n2 + n6, this.zLevel).tex((n3 + 0) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, this.zLevel).tex((n3 + n5) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + 0.0f, this.zLevel).tex((n3 + n5) * n7, (n4 + 0) * n8).endVertex();
        worldRenderer.pos(n + 0.0f, n2 + 0.0f, this.zLevel).tex((n3 + 0) * n7, (n4 + 0) * n8).endVertex();
        instance.draw();
    }
    
    public void drawTexturedModalRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + 0, n2 + n6, this.zLevel).tex((n3 + 0) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, this.zLevel).tex((n3 + n5) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + 0, this.zLevel).tex((n3 + n5) * n7, (n4 + 0) * n8).endVertex();
        worldRenderer.pos(n + 0, n2 + 0, this.zLevel).tex((n3 + 0) * n7, (n4 + 0) * n8).endVertex();
        instance.draw();
    }
    
    public void drawCenteredString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, (float)(n - fontRenderer.getStringWidth(s) / 2), (float)n2, n3);
    }
    
    protected void drawHorizontalLine(int n, int n2, final int n3, final int n4) {
        if (n2 < n) {
            final int n5 = n;
            n = n2;
            n2 = n5;
        }
        drawRect(n, n3, n2 + 1, n3 + 1, n4);
    }
    
    public void drawString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, (float)n, (float)n2, n3);
    }
    
    public void drawTexturedModalRect(final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + 0, n2 + n4, this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos(n + n3, n2 + n4, this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos(n + n3, n2 + 0, this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV()).endVertex();
        worldRenderer.pos(n + 0, n2 + 0, this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV()).endVertex();
        instance.draw();
    }
}
