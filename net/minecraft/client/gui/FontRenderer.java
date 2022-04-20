package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import java.io.*;
import com.ibm.icu.text.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import optfine.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class FontRenderer implements IResourceManagerReloadListener
{
    private boolean italicStyle;
    public float green;
    public int FONT_HEIGHT;
    private int[] colorCode;
    public Random fontRandom;
    private int textColor;
    private final TextureManager renderEngine;
    public float scaleFactor;
    private boolean boldStyle;
    private boolean randomStyle;
    public ResourceLocation locationFontTextureBase;
    public float alpha;
    private static final String __OBFID = "CL_00000660";
    private float[] charWidth;
    private byte[] glyphWidth;
    public float posX;
    public GameSettings gameSettings;
    public boolean bidiFlag;
    private static final ResourceLocation[] unicodePageLocations;
    public float blue;
    public boolean enabled;
    private boolean underlineStyle;
    public float red;
    private boolean unicodeFlag;
    public float posY;
    private ResourceLocation locationFontTexture;
    private boolean strikethroughStyle;
    
    private int sizeStringToWidth(final String p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/String.length:()I
        //     4: istore_3       
        //     5: fconst_0       
        //     6: fstore          4
        //     8: iconst_0       
        //     9: iload_3        
        //    10: if_icmpge       165
        //    13: aload_1        
        //    14: iconst_0       
        //    15: invokevirtual   java/lang/String.charAt:(I)C
        //    18: istore          8
        //    20: iload           8
        //    22: lookupswitch {
        //               10: 56
        //               32: 62
        //              167: 82
        //          default: 62
        //        }
        //    56: iinc            5, -1
        //    59: goto            135
        //    62: fload           4
        //    64: aload_0        
        //    65: iload           8
        //    67: invokespecial   net/minecraft/client/gui/FontRenderer.getCharWidthFloat:(C)F
        //    70: fadd           
        //    71: fstore          4
        //    73: fload           4
        //    75: fconst_1       
        //    76: fadd           
        //    77: fstore          4
        //    79: goto            135
        //    82: iconst_0       
        //    83: iload_3        
        //    84: iconst_1       
        //    85: isub           
        //    86: if_icmpge       135
        //    89: iinc            5, 1
        //    92: aload_1        
        //    93: iconst_0       
        //    94: invokevirtual   java/lang/String.charAt:(I)C
        //    97: istore          9
        //    99: iload           9
        //   101: bipush          108
        //   103: if_icmpeq       135
        //   106: iload           9
        //   108: bipush          76
        //   110: if_icmpeq       135
        //   113: iload           9
        //   115: bipush          114
        //   117: if_icmpeq       132
        //   120: iload           9
        //   122: bipush          82
        //   124: if_icmpeq       132
        //   127: iload           9
        //   129: if_icmplt       135
        //   132: goto            135
        //   135: iload           8
        //   137: bipush          10
        //   139: if_icmpne       148
        //   142: iinc            5, 1
        //   145: goto            165
        //   148: fload           4
        //   150: iload_2        
        //   151: i2f            
        //   152: fcmpl          
        //   153: ifle            159
        //   156: goto            165
        //   159: iinc            5, 1
        //   162: goto            8
        //   165: iconst_0       
        //   166: iload_3        
        //   167: if_icmpeq       177
        //   170: goto            177
        //   173: iconst_m1      
        //   174: goto            178
        //   177: iconst_0       
        //   178: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int renderString(String bidiReorder, final float posX, final float posY, int n, final boolean b) {
        if (bidiReorder == null) {
            return 0;
        }
        if (this.bidiFlag) {
            bidiReorder = this.bidiReorder(bidiReorder);
        }
        if ((n & 0xFC000000) == 0x0) {
            n |= 0xFF000000;
        }
        if (b) {
            n = ((n & 0xFCFCFC) >> 2 | (n & 0xFF000000));
        }
        this.red = (n >> 16 & 0xFF) / 255.0f;
        this.blue = (n >> 8 & 0xFF) / 255.0f;
        this.green = (n & 0xFF) / 255.0f;
        this.alpha = (n >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(this.red, this.blue, this.green, this.alpha);
        this.posX = posX;
        this.posY = posY;
        this.renderStringAtPos(bidiReorder, b);
        return (int)this.posX;
    }
    
    private void readGlyphSizes() {
        final InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
        inputStream.read(this.glyphWidth);
        IOUtils.closeQuietly(inputStream);
    }
    
    public void setUnicodeFlag(final boolean unicodeFlag) {
        this.unicodeFlag = unicodeFlag;
    }
    
    public List listFormattedStringToWidth(final String s, final int n) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, n).split("\n"));
    }
    
    public int getCharWidth(final char c) {
        return Math.round(this.getCharWidthFloat(c));
    }
    
    public String bidiReorder(final String s) {
        final Bidi bidi = new Bidi(new ArabicShaping(8).shape(s), 127);
        bidi.setReorderingMode(0);
        return bidi.writeReordered(2);
    }
    
    public String trimStringToWidth(final String s, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        final float n2 = 0.0f;
        final int n3 = b ? (s.length() - 1) : 0;
        for (int n4 = b ? -1 : 1, n5 = n3; n5 >= 0 && n5 < s.length() && n2 < n; n5 += n4) {
            final char char1 = s.charAt(n5);
            this.getCharWidthFloat(char1);
            if (char1 != 'l' && char1 != 'L') {
                if (char1 == 'r' || char1 == 'R') {}
            }
            if (n2 > n) {
                break;
            }
            if (b) {
                sb.insert(0, char1);
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public void setBidiFlag(final boolean bidiFlag) {
        this.bidiFlag = bidiFlag;
    }
    
    private float func_181559_a(final char c, final boolean b) {
        if (c == ' ') {
            return this.charWidth[c];
        }
        final int index = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(c);
        return (index != -1 && !this.unicodeFlag) ? this.renderDefaultChar(index, b) : this.renderUnicodeChar(c, b);
    }
    
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    public void drawSplitString(String trimStringNewline, final int n, final int n2, final int n3, final int textColor) {
        this.resetStyles();
        this.textColor = textColor;
        trimStringNewline = this.trimStringNewline(trimStringNewline);
        this.renderSplitString(trimStringNewline, n, n2, n3, false);
    }
    
    public int drawString(final String s, final float n, final float n2, final int n3, final boolean b) {
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.renderString(s, n + 1.0f, n2 + 1.0f, n3, true), this.renderString(s, n, n2, n3, false));
        }
        else {
            n4 = this.renderString(s, n, n2, n3, false);
        }
        return n4;
    }
    
    public float drawCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        return (float)this.drawStringWithShadow(s, n - this.getStringWidth(s) / 2, n2, n3);
    }
    
    private float renderDefaultChar(final int n, final boolean b) {
        final int n2 = n % 16 * 8;
        final int n3 = n / 16 * 8;
        final boolean b2 = b;
        this.renderEngine.bindTexture(this.locationFontTexture);
        final float n4 = this.charWidth[n];
        final float n5 = 7.99f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(n2 / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + (float)(b2 ? 1 : 0), this.posY, 0.0f);
        GL11.glTexCoord2f(n2 / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX - (float)(b2 ? 1 : 0), this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n2 + n5 - 1.0f) / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + n5 - 1.0f + (float)(b2 ? 1 : 0), this.posY, 0.0f);
        GL11.glTexCoord2f((n2 + n5 - 1.0f) / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX + n5 - 1.0f - (float)(b2 ? 1 : 0), this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return n4;
    }
    
    public FontRenderer(final GameSettings gameSettings, final ResourceLocation resourceLocation, final TextureManager renderEngine, final boolean unicodeFlag) {
        this.charWidth = new float[256];
        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.enabled = true;
        this.scaleFactor = 1.0f;
        this.gameSettings = gameSettings;
        this.locationFontTextureBase = resourceLocation;
        this.locationFontTexture = resourceLocation;
        this.renderEngine = renderEngine;
        this.unicodeFlag = unicodeFlag;
        renderEngine.bindTexture(this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase));
        while (true) {
            if (gameSettings.anaglyph) {}
            this.colorCode[0] = 0;
            int n = 0;
            ++n;
        }
    }
    
    String wrapFormattedStringToWidth(final String s, final int n) {
        final int sizeStringToWidth = this.sizeStringToWidth(s, n);
        if (s.length() <= sizeStringToWidth) {
            return s;
        }
        final String substring = s.substring(0, sizeStringToWidth);
        final char char1 = s.charAt(sizeStringToWidth);
        return substring + "\n" + this.wrapFormattedStringToWidth(getFormatFromString(substring) + s.substring(sizeStringToWidth + ((char1 == ' ' || char1 == '\n') ? 1 : 0)), n);
    }
    
    public int drawString(final String s, final double n, final double n2, final int n3) {
        return this.enabled ? this.drawString(s, (float)n, (float)n2, n3, false) : 0;
    }
    
    private String trimStringNewline(String substring) {
        while (substring != null && substring.endsWith("\n")) {
            substring = substring.substring(0, substring.length() - 1);
        }
        return substring;
    }
    
    private void readFontTexture() {
        final BufferedImage bufferedImage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n = height / 16;
        final float scaleFactor = width / 128.0f;
        this.scaleFactor = scaleFactor;
        bufferedImage.getRGB(0, 0, width, height, new int[width * height], 0, width);
        while (true) {
            if (0 < n) {}
            this.charWidth[0] = 1 / scaleFactor + 1.0f;
            int n2 = 0;
            ++n2;
        }
    }
    
    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }
    
    private int renderStringAligned(final String s, int n, final int n2, final int n3, final int n4, final boolean b) {
        if (this.bidiFlag) {
            n = n + n3 - this.getStringWidth(this.bidiReorder(s));
        }
        return this.renderString(s, (float)n, (float)n2, n4, b);
    }
    
    public int splitStringWidth(final String s, final int n) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(s, n).size();
    }
    
    public int getColorCode(final char c) {
        final int index = "0123456789abcdef".indexOf(c);
        return (index >= 0 && index < this.colorCode.length) ? this.colorCode[index] : 16777215;
    }
    
    private static ResourceLocation getHdFontLocation(final ResourceLocation resourceLocation) {
        if (!Config.isCustomFonts()) {
            return resourceLocation;
        }
        if (resourceLocation == null) {
            return resourceLocation;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final String s = "textures/";
        final String s2 = "mcpatcher/";
        if (!resourcePath.startsWith(s)) {
            return resourceLocation;
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getResourceDomain(), s2 + resourcePath.substring(s.length()));
        return Config.hasResource(Config.getResourceManager(), resourceLocation2) ? resourceLocation2 : resourceLocation;
    }
    
    public static String getFormatFromString(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_1       
        //     4: aload_0        
        //     5: invokevirtual   java/lang/String.length:()I
        //     8: istore_3       
        //     9: aload_0        
        //    10: sipush          167
        //    13: iconst_0       
        //    14: invokevirtual   java/lang/String.indexOf:(II)I
        //    17: dup            
        //    18: istore_2       
        //    19: iconst_m1      
        //    20: if_icmpeq       101
        //    23: iconst_m1      
        //    24: iload_3        
        //    25: iconst_1       
        //    26: isub           
        //    27: if_icmpge       9
        //    30: aload_0        
        //    31: iconst_0       
        //    32: invokevirtual   java/lang/String.charAt:(I)C
        //    35: istore          4
        //    37: iload           4
        //    39: if_icmplt       67
        //    42: new             Ljava/lang/StringBuilder;
        //    45: dup            
        //    46: invokespecial   java/lang/StringBuilder.<init>:()V
        //    49: ldc_w           "§"
        //    52: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    55: iload           4
        //    57: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    60: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    63: astore_1       
        //    64: goto            9
        //    67: iload           4
        //    69: if_icmplt       98
        //    72: new             Ljava/lang/StringBuilder;
        //    75: dup            
        //    76: invokespecial   java/lang/StringBuilder.<init>:()V
        //    79: aload_1        
        //    80: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    83: ldc_w           "§"
        //    86: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    89: iload           4
        //    91: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    94: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    97: astore_1       
        //    98: goto            9
        //   101: aload_1        
        //   102: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String trimStringToWidth(final String s, final int n) {
        return this.trimStringToWidth(s, n, false);
    }
    
    private void renderStringAtPos(final String s, final boolean b) {
        while (0 < s.length()) {
            char char1 = s.charAt(0);
            int n = 0;
            if (char1 == '§' && 1 < s.length()) {
                int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase().charAt(1));
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
                "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(char1);
                if (this.randomStyle) {
                    char char2;
                    do {
                        this.fontRandom.nextInt(256);
                        char2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".charAt(15);
                    } while (this.getCharWidth(char1) != this.getCharWidth(char2));
                    char1 = char2;
                }
                final float n2 = this.unicodeFlag ? 0.5f : (1.0f / this.scaleFactor);
                final boolean b2 = (char1 == '\0' || this.unicodeFlag) && b;
                if (b2) {
                    this.posX -= n2;
                    this.posY -= n2;
                }
                float func_181559_a = this.func_181559_a(char1, this.italicStyle);
                if (b2) {
                    this.posX += n2;
                    this.posY += n2;
                }
                if (this.boldStyle) {
                    this.posX += n2;
                    if (b2) {
                        this.posX -= n2;
                        this.posY -= n2;
                    }
                    this.func_181559_a(char1, this.italicStyle);
                    this.posX -= n2;
                    if (b2) {
                        this.posX += n2;
                        this.posY += n2;
                    }
                    func_181559_a += n2;
                }
                if (this.strikethroughStyle) {
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION);
                    worldRenderer.pos(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0).endVertex();
                    worldRenderer.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT / 2, 0.0).endVertex();
                    worldRenderer.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0).endVertex();
                    worldRenderer.pos(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0).endVertex();
                    instance.draw();
                }
                if (this.underlineStyle) {
                    final Tessellator instance2 = Tessellator.getInstance();
                    final WorldRenderer worldRenderer2 = instance2.getWorldRenderer();
                    worldRenderer2.begin(7, DefaultVertexFormats.POSITION);
                    final int n3 = this.underlineStyle ? -1 : 0;
                    worldRenderer2.pos(this.posX + n3, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + func_181559_a, this.posY + this.FONT_HEIGHT - 1.0f, 0.0).endVertex();
                    worldRenderer2.pos(this.posX + n3, this.posY + this.FONT_HEIGHT - 1.0f, 0.0).endVertex();
                    instance2.draw();
                }
                this.posX += func_181559_a;
            }
            ++n;
        }
    }
    
    static {
        unicodePageLocations = new ResourceLocation[256];
    }
    
    private float getCharWidthFloat(final char c) {
        if (c == '§') {
            return -1.0f;
        }
        if (c == ' ') {
            return this.charWidth[32];
        }
        final int index = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(c);
        if (c > '\0' && index != -1 && !this.unicodeFlag) {
            return this.charWidth[index];
        }
        if (this.glyphWidth[c] != 0) {
            final int n = this.glyphWidth[c] >>> 4;
            int n2 = this.glyphWidth[c] & 0xF;
            ++n2;
            return 1;
        }
        return 0.0f;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
        while (0 < FontRenderer.unicodePageLocations.length) {
            FontRenderer.unicodePageLocations[0] = null;
            int n = 0;
            ++n;
        }
        this.readFontTexture();
    }
    
    private float renderUnicodeChar(final char c, final boolean b) {
        if (this.glyphWidth[c] == 0) {
            return 0.0f;
        }
        this.loadGlyphTexture(c / '\u0100');
        final int n = this.glyphWidth[c] >>> 4;
        final int n2 = this.glyphWidth[c] & 0xF;
        final float n3 = (float)n;
        final float n4 = (float)(n2 + 1);
        final float n5 = c % '\u0010' * 16 + n3;
        final float n6 = (float)((c & '\u00ff') / 16 * 16);
        final float n7 = n4 - n3 - 0.02f;
        final float n8 = b ? 1.0f : 0.0f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(n5 / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n8, this.posY, 0.0f);
        GL11.glTexCoord2f(n5 / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX - n8, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f + n8, this.posY, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f - n8, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (n4 - n3) / 2.0f + 1.0f;
    }
    
    public int getStringWidth(final String s) {
        if (s == null) {
            return 0;
        }
        float n = 0.0f;
        while (0 < s.length()) {
            float charWidthFloat = this.getCharWidthFloat(s.charAt(0));
            int n2 = 0;
            if (charWidthFloat < 0.0f && 0 < s.length() - 1) {
                ++n2;
                final char char1 = s.charAt(0);
                if (char1 != 'l' && char1 != 'L' && (char1 == 'r' || char1 == 'R')) {}
                charWidthFloat = 0.0f;
            }
            n += charWidthFloat;
            if (charWidthFloat > 0.0f) {
                ++n;
            }
            ++n2;
        }
        return (int)n;
    }
    
    public int drawStringWithShadow(final String s, final float n, final float n2, final int n3) {
        return this.drawString(s, n, n2, n3, true);
    }
    
    private ResourceLocation getUnicodePageLocation(final int n) {
        if (FontRenderer.unicodePageLocations[n] == null) {
            FontRenderer.unicodePageLocations[n] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", n));
            FontRenderer.unicodePageLocations[n] = getHdFontLocation(FontRenderer.unicodePageLocations[n]);
        }
        return FontRenderer.unicodePageLocations[n];
    }
    
    private void readCustomCharWidths() {
        final String resourcePath = this.locationFontTexture.getResourcePath();
        final String s = ".png";
        if (resourcePath.endsWith(s)) {
            final String string = resourcePath.substring(0, resourcePath.length() - s.length()) + ".properties";
            final InputStream resourceStream = Config.getResourceStream(Config.getResourceManager(), new ResourceLocation(this.locationFontTexture.getResourceDomain(), string));
            if (resourceStream == null) {
                return;
            }
            Config.log("Loading " + string);
            final Properties properties = new Properties();
            properties.load(resourceStream);
            for (final String s2 : ((Hashtable<String, V>)properties).keySet()) {
                final String s3 = "width.";
                if (s2.startsWith(s3)) {
                    final int int1 = Config.parseInt(s2.substring(s3.length()), -1);
                    if (int1 < 0 || int1 >= this.charWidth.length) {
                        continue;
                    }
                    final float float1 = Config.parseFloat(properties.getProperty(s2), -1.0f);
                    if (float1 < 0.0f) {
                        continue;
                    }
                    this.charWidth[int1] = float1;
                }
            }
        }
    }
    
    private void loadGlyphTexture(final int n) {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(n));
    }
    
    public boolean getBidiFlag() {
        return this.bidiFlag;
    }
    
    private void renderSplitString(final String s, final int n, int n2, final int n3, final boolean b) {
        final Iterator<String> iterator = this.listFormattedStringToWidth(s, n3).iterator();
        while (iterator.hasNext()) {
            this.renderStringAligned(iterator.next(), n, n2, n3, this.textColor, b);
            n2 += this.FONT_HEIGHT;
        }
    }
}
