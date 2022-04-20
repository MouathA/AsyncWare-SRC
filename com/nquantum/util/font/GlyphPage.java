package com.nquantum.util.font;

import java.util.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class GlyphPage
{
    private int imgSize;
    private int maxFontHeight;
    private HashMap glyphCharacterMap;
    private boolean fractionalMetrics;
    private BufferedImage bufferedImage;
    private DynamicTexture loadedTexture;
    private boolean antiAliasing;
    private Font font;
    
    public float drawChar(final char c, final float n, final float n2) {
        final Glyph glyph = this.glyphCharacterMap.get(c);
        if (glyph == null) {
            throw new IllegalArgumentException("'" + c + "' wasn't found");
        }
        final float n3 = Glyph.access$200(glyph) / (float)this.imgSize;
        final float n4 = Glyph.access$300(glyph) / (float)this.imgSize;
        final float n5 = Glyph.access$000(glyph) / (float)this.imgSize;
        final float n6 = Glyph.access$100(glyph) / (float)this.imgSize;
        final float n7 = (float)Glyph.access$000(glyph);
        final float n8 = (float)Glyph.access$100(glyph);
        GL11.glBegin(4);
        GL11.glTexCoord2f(n3 + n5, n4);
        GL11.glVertex2f(n + n7, n2);
        GL11.glTexCoord2f(n3, n4);
        GL11.glVertex2f(n, n2);
        GL11.glTexCoord2f(n3, n4 + n6);
        GL11.glVertex2f(n, n2 + n8);
        GL11.glTexCoord2f(n3, n4 + n6);
        GL11.glVertex2f(n, n2 + n8);
        GL11.glTexCoord2f(n3 + n5, n4 + n6);
        GL11.glVertex2f(n + n7, n2 + n8);
        GL11.glTexCoord2f(n3 + n5, n4);
        GL11.glVertex2f(n + n7, n2);
        GL11.glEnd();
        return n7 - 8.0f;
    }
    
    public void setupTexture() {
        this.loadedTexture = new DynamicTexture(this.bufferedImage);
    }
    
    public void generateGlyphPage(final char[] array) {
        double width = -1.0;
        double height = -1.0;
        final FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), this.antiAliasing, this.fractionalMetrics);
        while (0 < array.length) {
            final char c = array[0];
            final Rectangle2D stringBounds = this.font.getStringBounds(Character.toString('\0'), fontRenderContext);
            if (width < stringBounds.getWidth()) {
                width = stringBounds.getWidth();
            }
            if (height < stringBounds.getHeight()) {
                height = stringBounds.getHeight();
            }
            int access$100 = 0;
            ++access$100;
        }
        final double n = width + 2.0;
        final double n2 = height + 2.0;
        this.imgSize = (int)Math.ceil(Math.max(Math.ceil(Math.sqrt(n * n * array.length) / n), Math.ceil(Math.sqrt(n2 * n2 * array.length) / n2)) * Math.max(n, n2)) + 1;
        this.bufferedImage = new BufferedImage(this.imgSize, this.imgSize, 2);
        final Graphics2D graphics2D = (Graphics2D)this.bufferedImage.getGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.imgSize, this.imgSize);
        graphics2D.setColor(Color.white);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        while (0 < array.length) {
            final char c2 = array[0];
            final Glyph glyph = new Glyph();
            final Rectangle2D stringBounds2 = fontMetrics.getStringBounds(Character.toString(c2), graphics2D);
            Glyph.access$002(glyph, stringBounds2.getBounds().width + 8);
            Glyph.access$102(glyph, stringBounds2.getBounds().height);
            if (1 + Glyph.access$100(glyph) >= this.imgSize) {
                throw new IllegalStateException("Not all characters will fit");
            }
            if (0 + Glyph.access$000(glyph) >= this.imgSize) {}
            Glyph.access$202(glyph, 0);
            Glyph.access$302(glyph, 1);
            if (Glyph.access$100(glyph) > this.maxFontHeight) {
                this.maxFontHeight = Glyph.access$100(glyph);
            }
            if (Glyph.access$100(glyph) > 0) {
                final int access$100 = Glyph.access$100(glyph);
            }
            graphics2D.drawString(Character.toString(c2), 2, 1 + fontMetrics.getAscent());
            final int n3 = 0 + Glyph.access$000(glyph);
            this.glyphCharacterMap.put(c2, glyph);
            int n4 = 0;
            ++n4;
        }
    }
    
    public boolean isAntiAliasingEnabled() {
        return this.antiAliasing;
    }
    
    public void unbindTexture() {
        GlStateManager.bindTexture(0);
    }
    
    public GlyphPage(final Font font, final boolean antiAliasing, final boolean fractionalMetrics) {
        this.maxFontHeight = -1;
        this.glyphCharacterMap = new HashMap();
        this.font = font;
        this.antiAliasing = antiAliasing;
        this.fractionalMetrics = fractionalMetrics;
    }
    
    public int getMaxFontHeight() {
        return this.maxFontHeight;
    }
    
    public float getWidth(final char c) {
        return (float)Glyph.access$000(this.glyphCharacterMap.get(c));
    }
    
    public void bindTexture() {
        GlStateManager.bindTexture(this.loadedTexture.getGlTextureId());
    }
    
    public boolean isFractionalMetricsEnabled() {
        return this.fractionalMetrics;
    }
    
    static class Glyph
    {
        private int x;
        private int width;
        private int y;
        private int height;
        
        public int getWidth() {
            return this.width;
        }
        
        static int access$300(final Glyph glyph) {
            return glyph.y;
        }
        
        static int access$202(final Glyph glyph, final int x) {
            return glyph.x = x;
        }
        
        static int access$100(final Glyph glyph) {
            return glyph.height;
        }
        
        static int access$302(final Glyph glyph, final int y) {
            return glyph.y = y;
        }
        
        static int access$000(final Glyph glyph) {
            return glyph.width;
        }
        
        static int access$200(final Glyph glyph) {
            return glyph.x;
        }
        
        Glyph(final int x, final int y, final int width, final int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        static int access$102(final Glyph glyph, final int height) {
            return glyph.height = height;
        }
        
        static int access$002(final Glyph glyph, final int width) {
            return glyph.width = width;
        }
        
        Glyph() {
        }
        
        public int getY() {
            return this.y;
        }
    }
}
