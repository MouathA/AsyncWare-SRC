package com.nquantum.util.font;

import java.awt.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GlyphPageFontRenderer
{
    private float blue;
    private GlyphPage italicGlyphPage;
    private float alpha;
    private GlyphPage regularGlyphPage;
    private GlyphPage boldItalicGlyphPage;
    private int textColor;
    private boolean underlineStyle;
    private GlyphPage boldGlyphPage;
    private boolean strikethroughStyle;
    private int[] colorCode;
    private float red;
    public Random fontRandom;
    private float posX;
    private boolean italicStyle;
    private boolean boldStyle;
    private boolean randomStyle;
    private float green;
    private float posY;
    
    public GlyphPageFontRenderer(final GlyphPage regularGlyphPage, final GlyphPage boldGlyphPage, final GlyphPage italicGlyphPage, final GlyphPage boldItalicGlyphPage) {
        this.fontRandom = new Random();
        this.colorCode = new int[32];
        this.regularGlyphPage = regularGlyphPage;
        this.boldGlyphPage = boldGlyphPage;
        this.italicGlyphPage = italicGlyphPage;
        this.boldItalicGlyphPage = boldItalicGlyphPage;
        while (true) {
            this.colorCode[0] = 0;
            int n = 0;
            ++n;
        }
    }
    
    public String trimStringToWidth(final String s, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        final int n2 = b ? (s.length() - 1) : 0;
        for (int n3 = b ? -1 : 1, n4 = n2; n4 >= 0 && n4 < s.length() && n4 < n; n4 += n3) {
            char c = s.charAt(n4);
            if (c != '§') {
                c = s.charAt(n4);
                final int n5 = (int)(0 + (this.getCurrentGlyphPage().getWidth(c) - 8.0f) / 2.0f);
            }
            if (n4 > 0) {
                break;
            }
            if (b) {
                sb.insert(0, c);
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public int getStringWidth(final String s) {
        if (s == null) {
            return 0;
        }
        while (0 < s.length()) {
            int n = 0;
            if (s.charAt(0) == '§') {
                ++n;
            }
            else {
                final int n2 = (int)(0 + (this.getCurrentGlyphPage().getWidth(s.charAt(0)) - 8.0f));
            }
            ++n;
        }
        return 0;
    }
    
    public int drawString(final String s, final float n, final float n2, final int n3, final boolean b) {
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.renderString(s, n + 0.56f, n2 + 0.56f, n3, true), this.renderString(s, n, n2, n3, false));
        }
        else {
            n4 = this.renderString(s, n, n2, n3, false);
        }
        return n4;
    }
    
    public static GlyphPageFontRenderer create(final String s, final int n, final boolean b, final boolean b2, final boolean b3) {
        final char[] array = new char[256];
        while (0 < array.length) {
            array[0] = 0;
            int n2 = 0;
            ++n2;
        }
        final GlyphPage glyphPage = new GlyphPage(new Font(s, 0, n), true, true);
        glyphPage.generateGlyphPage(array);
        glyphPage.setupTexture();
        GlyphPage glyphPage2 = glyphPage;
        GlyphPage glyphPage3 = glyphPage;
        GlyphPage glyphPage4 = glyphPage;
        if (b) {
            glyphPage2 = new GlyphPage(new Font(s, 1, n), true, true);
            glyphPage2.generateGlyphPage(array);
            glyphPage2.setupTexture();
        }
        if (b2) {
            glyphPage3 = new GlyphPage(new Font(s, 2, n), true, true);
            glyphPage3.generateGlyphPage(array);
            glyphPage3.setupTexture();
        }
        if (b3) {
            glyphPage4 = new GlyphPage(new Font(s, 3, n), true, true);
            glyphPage4.generateGlyphPage(array);
            glyphPage4.setupTexture();
        }
        return new GlyphPageFontRenderer(glyphPage, glyphPage2, glyphPage3, glyphPage4);
    }
    
    private void renderStringAtPos(final String s, final boolean b) {
        GlyphPage glyphPage = this.getCurrentGlyphPage();
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        GlStateManager.blendFunc(770, 771);
        glyphPage.bindTexture();
        GL11.glTexParameteri(3553, 10240, 9729);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            int n = 0;
            if (char1 == '§' && 1 < s.length()) {
                int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(1));
                this.randomStyle = false;
                this.boldStyle = false;
                this.strikethroughStyle = false;
                this.underlineStyle = false;
                this.italicStyle = false;
                if (b) {
                    index += 16;
                }
                final int textColor = this.colorCode[15];
                this.textColor = textColor;
                GlStateManager.color((textColor >> 16) / 255.0f, (textColor >> 8 & 0xFF) / 255.0f, (textColor & 0xFF) / 255.0f, this.alpha);
                ++n;
            }
            else {
                glyphPage = this.getCurrentGlyphPage();
                glyphPage.bindTexture();
                this.doDraw(glyphPage.drawChar(char1, this.posX, this.posY), glyphPage);
            }
            ++n;
        }
        glyphPage.unbindTexture();
        GL11.glPopMatrix();
    }
    
    private int renderString(final String s, final float n, final float n2, int n3, final boolean b) {
        if (s == null) {
            return 0;
        }
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 0xFCFCFC) >> 1 | (n3 & 0xFF000000));
        }
        this.red = (n3 >> 16 & 0xFF) / 255.0f;
        this.blue = (n3 >> 8 & 0xFF) / 255.0f;
        this.green = (n3 & 0xFF) / 255.0f;
        this.alpha = (n3 >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(this.red, this.blue, this.green, this.alpha);
        this.posX = n * 2.0f;
        this.posY = n2 * 2.0f;
        this.renderStringAtPos(s, b);
        return (int)(this.posX / 4.0f);
    }
    
    private GlyphPage getCurrentGlyphPage() {
        if (this.boldStyle && this.italicStyle) {
            return this.boldItalicGlyphPage;
        }
        if (this.boldStyle) {
            return this.boldGlyphPage;
        }
        if (this.italicStyle) {
            return this.italicGlyphPage;
        }
        return this.regularGlyphPage;
    }
    
    public int drawString(final String s, final double n, final double n2, final int n3, final boolean b) {
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.renderString(s, (int)n + 0.56f, (int)n2 + 0.56f, n3, true), this.renderString(s, (float)(int)n, (float)(int)n2, n3, false));
        }
        else {
            n4 = this.renderString(s, (float)(int)n, (float)(int)n2, n3, false);
        }
        return n4;
    }
    
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    private void doDraw(final float n, final GlyphPage glyphPage) {
        if (this.strikethroughStyle) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION);
            worldRenderer.pos(this.posX, this.posY + glyphPage.getMaxFontHeight() / 2, 0.0).endVertex();
            worldRenderer.pos(this.posX + n, this.posY + glyphPage.getMaxFontHeight() / 2, 0.0).endVertex();
            worldRenderer.pos(this.posX + n, this.posY + glyphPage.getMaxFontHeight() / 2 - 1.0f, 0.0).endVertex();
            worldRenderer.pos(this.posX, this.posY + glyphPage.getMaxFontHeight() / 2 - 1.0f, 0.0).endVertex();
            instance.draw();
        }
        if (this.underlineStyle) {
            final Tessellator instance2 = Tessellator.getInstance();
            final WorldRenderer worldRenderer2 = instance2.getWorldRenderer();
            worldRenderer2.begin(7, DefaultVertexFormats.POSITION);
            final int n2 = this.underlineStyle ? -1 : 0;
            worldRenderer2.pos(this.posX + n2, this.posY + glyphPage.getMaxFontHeight(), 0.0).endVertex();
            worldRenderer2.pos(this.posX + n, this.posY + glyphPage.getMaxFontHeight(), 0.0).endVertex();
            worldRenderer2.pos(this.posX + n, this.posY + glyphPage.getMaxFontHeight() - 1.0f, 0.0).endVertex();
            worldRenderer2.pos(this.posX + n2, this.posY + glyphPage.getMaxFontHeight() - 1.0f, 0.0).endVertex();
            instance2.draw();
        }
        this.posX += n;
    }
    
    public int drawScaledString(final String s, final int n, final int n2, final int n3, final boolean b, final float n4) {
        GlStateManager.scale(n4, n4, 1.0f);
        final int drawString = this.drawString(s, (float)(int)(n / n4), (float)(int)(n2 / n4), n3, b);
        GlStateManager.scale(Math.pow(n4, -1.0), Math.pow(n4, -1.0), 1.0);
        return drawString;
    }
    
    public String trimStringToWidth(final String s, final int n) {
        return this.trimStringToWidth(s, n, false);
    }
    
    public int getFontHeight() {
        return this.regularGlyphPage.getMaxFontHeight() / 2;
    }
    
    public int drawScaledString(final String s, final float n, final float n2, final int n3, final boolean b, final float n4) {
        GlStateManager.scale(n4, n4, 1.0f);
        final int drawString = this.drawString(s, (float)(int)(n / n4), (float)(int)(n2 / n4), n3, b);
        GlStateManager.scale(Math.pow(n4, -1.0), Math.pow(n4, -1.0), 1.0);
        return drawString;
    }
}
