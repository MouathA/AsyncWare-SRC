package com.nquantum.util;

import org.newdawn.slick.*;
import net.minecraft.client.*;
import java.awt.*;
import org.newdawn.slick.font.effects.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class FontUtil
{
    private final int[] colorCodes;
    private final UnicodeFont unicodeFont;
    private float kerning;
    private int fontType;
    private int size;
    private String fontName;
    private Minecraft mc;
    
    public FontUtil(final String fontName, final int fontType, final int size, final float kerning) {
        this.mc = Minecraft.getMinecraft();
        this.colorCodes = new int[32];
        this.fontName = fontName;
        this.fontType = fontType;
        this.size = size;
        this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        this.unicodeFont.loadGlyphs();
        while (true) {
            this.colorCodes[0] = 0;
            int n = 0;
            ++n;
        }
    }
    
    public float getWidth(final String s) {
        float n = 0.0f;
        final char[] charArray = StringUtils.stripControlCodes(s).toCharArray();
        while (0 < charArray.length) {
            n += this.unicodeFont.getWidth(Character.toString(charArray[0])) + this.kerning;
            int n2 = 0;
            ++n2;
        }
        return n / 2.0f;
    }
    
    public int drawStringWithShadow(final String s, final float n, final float n2, final int n3) {
        this.drawString(StringUtils.stripControlCodes(s), n + 0.5f, n2 + 0.5f, 0);
        return this.drawString(s, n, n2, n3);
    }
    
    public FontUtil(final String s, final int n, final int n2) {
        this(s, n, n2, 0.0f);
    }
    
    public void drawCenteredString(final String s, final float n, final float n2, final int n3) {
        this.drawString(s, n - (int)this.getWidth(s) / 2, n2, n3);
    }
    
    public void drawCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        this.drawCenteredString(StringUtils.stripControlCodes(s), n + 0.5f, n2 + 0.5f, n3);
        this.drawCenteredString(s, n, n2, n3);
    }
    
    public String trimStringToWidth(final String s, final int n) {
        final StringBuilder sb = new StringBuilder();
        final float n2 = 0.0f;
        while (0 < s.length() && n2 < n) {
            final char char1 = s.charAt(0);
            this.getCharWidth(char1);
            if (char1 != 'l' && char1 != 'L') {
                if (char1 == 'r' || char1 == 'R') {}
            }
            if (n2 > n) {
                break;
            }
            sb.append(char1);
        }
        return sb.toString();
    }
    
    public float getHeight(final String s) {
        return this.unicodeFont.getHeight(s) / 2.0f;
    }
    
    public int drawString(final String s, float n, float n2, final int n3) {
        n *= 2.0f;
        n2 *= 2.0f;
        final float n4 = n;
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        final boolean glIsEnabled = GL11.glIsEnabled(3042);
        final boolean glIsEnabled2 = GL11.glIsEnabled(2896);
        final boolean glIsEnabled3 = GL11.glIsEnabled(3553);
        if (!glIsEnabled) {
            GL11.glEnable(3042);
        }
        if (glIsEnabled2) {
            GL11.glDisable(2896);
        }
        if (glIsEnabled3) {
            GL11.glDisable(3553);
        }
        int n5 = n3;
        final char[] charArray;
        final char[] array = charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (c == '\r') {
                n = n4;
            }
            if (c == '\n') {
                n2 += this.getHeight(Character.toString(c)) * 2.0f;
            }
            Label_0289: {
                if (c != '§') {
                    this.unicodeFont.drawString(n, n2, Character.toString(c), new org.newdawn.slick.Color(n5));
                    n += this.getWidth(Character.toString(c)) * 2.0f;
                }
                else if (c == ' ') {
                    n += this.unicodeFont.getSpaceWidth();
                }
                else if (c == '§' && 0 != array.length - 1) {
                    final int index = "0123456789abcdefg".indexOf(s.charAt(1));
                    if (index < 0) {
                        break Label_0289;
                    }
                    n5 = this.colorCodes[index];
                }
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        if (glIsEnabled3) {
            GL11.glEnable(3553);
        }
        if (glIsEnabled2) {
            GL11.glEnable(2896);
        }
        if (!glIsEnabled) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
        return (int)n;
    }
    
    public float getCharWidth(final char c) {
        return (float)this.unicodeFont.getWidth(String.valueOf(c));
    }
    
    public UnicodeFont getFont() {
        return this.unicodeFont;
    }
}
