package com.nquantum.util.font;

import java.awt.image.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import java.util.*;
import net.minecraft.util.*;
import java.awt.*;
import java.awt.geom.*;
import org.lwjgl.util.vector.*;

public class TTFFontRenderer
{
    private final CharacterData[] italicsData;
    private final int[] colorCodes;
    private boolean fractionalMetrics;
    private final Font font;
    private final CharacterData[] boldData;
    private final boolean antiAlias;
    private final CharacterData[] regularData;
    
    private void createTexture(final int n, final BufferedImage bufferedImage) {
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), array, 0, bufferedImage.getWidth());
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);
        while (0 < bufferedImage.getHeight()) {
            while (0 < bufferedImage.getWidth()) {
                final int n2 = array[0 * bufferedImage.getWidth() + 0];
                byteBuffer.put((byte)(n2 >> 16 & 0xFF));
                byteBuffer.put((byte)(n2 >> 8 & 0xFF));
                byteBuffer.put((byte)(n2 & 0xFF));
                byteBuffer.put((byte)(n2 >> 24 & 0xFF));
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        byteBuffer.flip();
        GlStateManager.bindTexture(n);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexImage2D(3553, 0, 6408, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 6408, 5121, byteBuffer);
    }
    
    public void drawCenteredString(final String s, final float n, final float n2, final int n3) {
        final float n4 = this.getWidth(s) / 2.0f;
        GL11.glTranslated(0.5, 0.5, 0.0);
        this.renderString(s, n - n4, n2, n3, true);
        GL11.glTranslated(-0.5, -0.5, 0.0);
        this.renderString(s, n - n4, n2, n3, false);
    }
    
    public float getHeight(final String s) {
        float max = 0.0f;
        CharacterData[] array = this.regularData;
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (46 != 167) {
                if (char1 == '§' && 0 < length) {
                    final int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(1));
                    if (index == 17) {
                        array = this.boldData;
                    }
                    else if (index == 20) {
                        array = this.italicsData;
                    }
                    else if (index == 21) {
                        array = this.regularData;
                    }
                }
                else if (char1 <= '\u00ff') {
                    max = Math.max(max, array[char1].height);
                }
            }
            int n = 0;
            ++n;
        }
        return max / 2.0f - 2.0f;
    }
    
    private CharacterData[] setup(final CharacterData[] array, final int n) {
        this.generateColors();
        final Font deriveFont = this.font.deriveFont(n);
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        graphics2D.setFont(deriveFont);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        while (0 < array.length) {
            final char c = 0;
            final Rectangle2D stringBounds = fontMetrics.getStringBounds(String.valueOf(c), graphics2D);
            final BufferedImage bufferedImage = new BufferedImage(MathHelper.ceiling_double_int((float)stringBounds.getWidth() + 8.0f), MathHelper.ceiling_double_int((float)stringBounds.getHeight()), 2);
            final Graphics2D graphics2D2 = (Graphics2D)bufferedImage.getGraphics();
            graphics2D2.setFont(deriveFont);
            graphics2D2.setColor(new Color(255, 255, 255, 0));
            graphics2D2.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            graphics2D2.setColor(Color.WHITE);
            if (this.antiAlias) {
                graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics2D2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics2D2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }
            graphics2D2.drawString(String.valueOf(c), 4, fontMetrics.getAscent());
            final int glGenTextures = GL11.glGenTextures();
            this.createTexture(glGenTextures, bufferedImage);
            array[0] = new CharacterData(c, (float)bufferedImage.getWidth(), (float)bufferedImage.getHeight(), glGenTextures);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    public void drawString(final String s, final float n, final float n2, final int n3) {
        this.renderString(s, n, n2, n3, false);
    }
    
    public TTFFontRenderer(final Font font, final int n) {
        this(font, n, true);
    }
    
    public float getStringWidth(final String s) {
        float n = 0.0f;
        CharacterData[] array = this.regularData;
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (46 != 167) {
                if (char1 == '§' && 0 < length) {
                    final int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(1));
                    if (index == 17) {
                        array = this.boldData;
                    }
                    else if (index == 20) {
                        array = this.italicsData;
                    }
                    else if (index == 21) {
                        array = this.regularData;
                    }
                }
                else if (char1 <= '\u00ff') {
                    n += (array[char1].width - 8.0f) / 2.0f;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return n + 2.0f;
    }
    
    public void drawStringWithShadow(final String s, final float n, final float n2, final int n3) {
        GL11.glTranslated(0.5, 0.5, 0.0);
        this.renderString(s, n, n2, n3, true);
        GL11.glTranslated(-0.5, -0.5, 0.0);
        this.renderString(s, n, n2, n3, false);
    }
    
    private void generateColors() {
        while (true) {
            this.colorCodes[0] = 0;
            int n = 0;
            ++n;
        }
    }
    
    public TTFFontRenderer(final Font font) {
        this(font, 256);
    }
    
    public TTFFontRenderer(final Font font, final int n, final boolean antiAlias) {
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        this.regularData = this.setup(new CharacterData[n], 0);
        this.boldData = this.setup(new CharacterData[n], 1);
        this.italicsData = this.setup(new CharacterData[n], 2);
    }
    
    private void drawChar(final char c, final CharacterData[] array, final float n, final float n2) {
        final CharacterData characterData = array[c];
        characterData.bind();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d((double)n, (double)(n2 + characterData.height));
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d((double)(n + characterData.width), (double)(n2 + characterData.height));
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d((double)(n + characterData.width), (double)n2);
        GL11.glEnd();
    }
    
    public float getWidth(final String s) {
        float n = 0.0f;
        CharacterData[] array = this.regularData;
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (46 != 167) {
                if (char1 == '§' && 0 < length) {
                    final int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(1));
                    if (index == 17) {
                        array = this.boldData;
                    }
                    else if (index == 20) {
                        array = this.italicsData;
                    }
                    else if (index == 21) {
                        array = this.regularData;
                    }
                }
                else if (char1 <= '\u00ff') {
                    n += (array[char1].width - 8.0f) / 2.0f;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return n + 2.0f;
    }
    
    private void drawLine(final Vector2f vector2f, final Vector2f vector2f2, final float n) {
        GL11.glDisable(3553);
        GL11.glLineWidth(n);
        GL11.glBegin(1);
        GL11.glVertex2f(vector2f.x, vector2f.y);
        GL11.glVertex2f(vector2f2.x, vector2f2.y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    private void renderString(final String s, float n, float n2, final int n3, final boolean b) {
        if (s == "" || s.length() == 0) {
            return;
        }
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.blendFunc(770, 771);
        n -= 2.0f;
        n2 -= 2.0f;
        n += 0.5f;
        n2 += 0.5f;
        n *= 2.0f;
        n2 *= 2.0f;
        CharacterData[] array = this.regularData;
        final int length = s.length();
        final double n4 = 255.0 * (b ? 4 : 1);
        final Color color = new Color(n3);
        GL11.glColor4d(color.getRed() / n4, color.getGreen() / n4, color.getBlue() / n4, (n3 >> 24 & 0xFF) / 255.0);
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (46 != 167) {
                if (char1 == '§' && 0 < length) {
                    int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(1));
                    array = this.regularData;
                    if (b) {
                        index += 16;
                    }
                    final int n5 = this.colorCodes[15];
                    GL11.glColor4d((n5 >> 16) / 255.0, (n5 >> 8 & 0xFF) / 255.0, (n5 & 0xFF) / 255.0, (n3 >> 24 & 0xFF) / 255.0);
                }
                else if (char1 <= '\u00ff') {
                    this.drawChar(char1, array, n, n2);
                    n += array[char1].width - 8.0f;
                }
            }
            int n6 = 0;
            ++n6;
        }
        GL11.glPopMatrix();
        GlStateManager.bindTexture(0);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }
    
    class CharacterData
    {
        public float height;
        public char character;
        private final int textureId;
        final TTFFontRenderer this$0;
        public float width;
        
        public void bind() {
            GL11.glBindTexture(3553, this.textureId);
        }
        
        public CharacterData(final TTFFontRenderer this$0, final char character, final float width, final float height, final int textureId) {
            this.this$0 = this$0;
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }
    }
}
