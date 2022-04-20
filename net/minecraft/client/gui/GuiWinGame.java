package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.renderer.vertex.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import java.io.*;

public class GuiWinGame extends GuiScreen
{
    private int field_146581_h;
    private int field_146579_r;
    private static final ResourceLocation MINECRAFT_LOGO;
    private static final Logger logger;
    private float field_146578_s;
    private List field_146582_i;
    private static final ResourceLocation VIGNETTE_TEXTURE;
    
    @Override
    public void updateScreen() {
        final MusicTicker func_181535_r = this.mc.func_181535_r();
        final SoundHandler soundHandler = this.mc.getSoundHandler();
        if (this.field_146581_h == 0) {
            func_181535_r.func_181557_a();
            func_181535_r.func_181558_a(MusicTicker.MusicType.CREDITS);
            soundHandler.resumeSounds();
        }
        soundHandler.update();
        ++this.field_146581_h;
        if (this.field_146581_h > (this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s) {
            this.sendRespawnPacket();
        }
    }
    
    private void sendRespawnPacket() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    private void drawWinGameScreen(final int n, final int n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final int width = GuiWinGame.width;
        final float n4 = 0.0f - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n5 = GuiWinGame.height - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n6 = 0.015625f;
        float n7 = (this.field_146581_h + n3 - 0.0f) * 0.02f;
        final float n8 = ((this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s - 20.0f - (this.field_146581_h + n3)) * 0.005f;
        if (n8 < n7) {
            n7 = n8;
        }
        if (n7 > 1.0f) {
            n7 = 1.0f;
        }
        final float n9 = n7 * n7 * 96.0f / 255.0f;
        worldRenderer.pos(0.0, GuiWinGame.height, this.zLevel).tex(0.0, n4 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(width, GuiWinGame.height, this.zLevel).tex(width * n6, n4 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(width, 0.0, this.zLevel).tex(width * n6, n5 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.0, n5 * n6).color(n9, n9, n9, 1.0f).endVertex();
        instance.draw();
    }
    
    static {
        logger = LogManager.getLogger();
        MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
        VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawWinGameScreen(n, n2, n3);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final int n4 = GuiWinGame.width / 2 - 2;
        final int n5 = GuiWinGame.height + 50;
        final float n6 = -(this.field_146581_h + n3) * this.field_146578_s;
        GlStateManager.translate(0.0f, n6, 0.0f);
        this.mc.getTextureManager().bindTexture(GuiWinGame.MINECRAFT_LOGO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(n4, n5, 0, 0, 155, 44);
        this.drawTexturedModalRect(n4 + 155, n5, 0, 45, 155, 44);
        int n7 = n5 + 200;
        while (0 < this.field_146582_i.size()) {
            if (0 == this.field_146582_i.size() - 1) {
                final float n8 = n7 + n6 - (GuiWinGame.height / 2 - 6);
                if (n8 < 0.0f) {
                    GlStateManager.translate(0.0f, -n8, 0.0f);
                }
            }
            if (n7 + n6 + 12.0f + 8.0f > 0.0f && n7 + n6 < GuiWinGame.height) {
                final String s = this.field_146582_i.get(0);
                if (s.startsWith("[C]")) {
                    this.fontRendererObj.drawStringWithShadow(s.substring(3), (float)(n4 + (274 - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), (float)n7, 16777215);
                }
                else {
                    this.fontRendererObj.fontRandom.setSeed(0 * 4238972211L + this.field_146581_h / 4);
                    this.fontRendererObj.drawStringWithShadow(s, (float)n4, (float)n7, 16777215);
                }
            }
            n7 += 12;
            int width = 0;
            ++width;
        }
        this.mc.getTextureManager().bindTexture(GuiWinGame.VIGNETTE_TEXTURE);
        GlStateManager.blendFunc(0, 769);
        int width = GuiWinGame.width;
        final int height = GuiWinGame.height;
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, height, this.zLevel).tex(0.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0, height, this.zLevel).tex(1.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0, 0.0, this.zLevel).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        instance.draw();
        super.drawScreen(n, n2, n3);
    }
    
    public GuiWinGame() {
        this.field_146578_s = 0.5f;
    }
    
    @Override
    public void initGui() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //     4: ifnonnull       470
        //     7: aload_0        
        //     8: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    11: putfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //    14: ldc_w           ""
        //    17: astore_1       
        //    18: new             Ljava/lang/StringBuilder;
        //    21: dup            
        //    22: invokespecial   java/lang/StringBuilder.<init>:()V
        //    25: ldc_w           ""
        //    28: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    31: getstatic       net/minecraft/util/EnumChatFormatting.WHITE:Lnet/minecraft/util/EnumChatFormatting;
        //    34: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    37: getstatic       net/minecraft/util/EnumChatFormatting.OBFUSCATED:Lnet/minecraft/util/EnumChatFormatting;
        //    40: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    43: getstatic       net/minecraft/util/EnumChatFormatting.GREEN:Lnet/minecraft/util/EnumChatFormatting;
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    49: getstatic       net/minecraft/util/EnumChatFormatting.AQUA:Lnet/minecraft/util/EnumChatFormatting;
        //    52: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    55: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    58: astore_2       
        //    59: aload_0        
        //    60: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //    63: invokevirtual   net/minecraft/client/Minecraft.getResourceManager:()Lnet/minecraft/client/resources/IResourceManager;
        //    66: new             Lnet/minecraft/util/ResourceLocation;
        //    69: dup            
        //    70: ldc_w           "texts/end.txt"
        //    73: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    76: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //    81: invokeinterface net/minecraft/client/resources/IResource.getInputStream:()Ljava/io/InputStream;
        //    86: astore          4
        //    88: new             Ljava/io/BufferedReader;
        //    91: dup            
        //    92: new             Ljava/io/InputStreamReader;
        //    95: dup            
        //    96: aload           4
        //    98: getstatic       org/apache/commons/io/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //   101: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
        //   104: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //   107: astore          5
        //   109: new             Ljava/util/Random;
        //   112: dup            
        //   113: ldc2_w          8124371
        //   116: invokespecial   java/util/Random.<init>:(J)V
        //   119: astore          6
        //   121: aload           5
        //   123: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //   126: dup            
        //   127: astore_1       
        //   128: ifnull          280
        //   131: aload_1        
        //   132: ldc_w           "PLAYERNAME"
        //   135: aload_0        
        //   136: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //   139: invokevirtual   net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //   142: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //   145: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   148: astore_1       
        //   149: aload_1        
        //   150: aload_2        
        //   151: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   154: ifeq            240
        //   157: aload_1        
        //   158: aload_2        
        //   159: invokevirtual   java/lang/String.indexOf:(Ljava/lang/String;)I
        //   162: istore          9
        //   164: aload_1        
        //   165: iconst_0       
        //   166: iload           9
        //   168: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   171: astore          7
        //   173: aload_1        
        //   174: iload           9
        //   176: aload_2        
        //   177: invokevirtual   java/lang/String.length:()I
        //   180: iadd           
        //   181: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   184: astore          8
        //   186: new             Ljava/lang/StringBuilder;
        //   189: dup            
        //   190: invokespecial   java/lang/StringBuilder.<init>:()V
        //   193: aload           7
        //   195: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   198: getstatic       net/minecraft/util/EnumChatFormatting.WHITE:Lnet/minecraft/util/EnumChatFormatting;
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   204: getstatic       net/minecraft/util/EnumChatFormatting.OBFUSCATED:Lnet/minecraft/util/EnumChatFormatting;
        //   207: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   210: ldc_w           "XXXXXXXX"
        //   213: iconst_0       
        //   214: aload           6
        //   216: iconst_4       
        //   217: invokevirtual   java/util/Random.nextInt:(I)I
        //   220: iconst_3       
        //   221: iadd           
        //   222: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   228: aload           8
        //   230: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   233: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   236: astore_1       
        //   237: goto            149
        //   240: aload_0        
        //   241: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   244: aload_0        
        //   245: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //   248: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   251: aload_1        
        //   252: sipush          274
        //   255: invokevirtual   net/minecraft/client/gui/FontRenderer.listFormattedStringToWidth:(Ljava/lang/String;I)Ljava/util/List;
        //   258: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   263: pop            
        //   264: aload_0        
        //   265: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   268: ldc_w           ""
        //   271: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   276: pop            
        //   277: goto            121
        //   280: aload           4
        //   282: invokevirtual   java/io/InputStream.close:()V
        //   285: aload_0        
        //   286: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   289: ldc_w           ""
        //   292: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   297: pop            
        //   298: iinc            7, 1
        //   301: goto            285
        //   304: aload_0        
        //   305: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //   308: invokevirtual   net/minecraft/client/Minecraft.getResourceManager:()Lnet/minecraft/client/resources/IResourceManager;
        //   311: new             Lnet/minecraft/util/ResourceLocation;
        //   314: dup            
        //   315: ldc_w           "texts/credits.txt"
        //   318: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   321: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //   326: invokeinterface net/minecraft/client/resources/IResource.getInputStream:()Ljava/io/InputStream;
        //   331: astore          4
        //   333: new             Ljava/io/BufferedReader;
        //   336: dup            
        //   337: new             Ljava/io/InputStreamReader;
        //   340: dup            
        //   341: aload           4
        //   343: getstatic       org/apache/commons/io/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //   346: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
        //   349: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //   352: astore          5
        //   354: aload           5
        //   356: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //   359: dup            
        //   360: astore_1       
        //   361: ifnull          433
        //   364: aload_1        
        //   365: ldc_w           "PLAYERNAME"
        //   368: aload_0        
        //   369: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //   372: invokevirtual   net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //   375: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //   378: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   381: astore_1       
        //   382: aload_1        
        //   383: ldc_w           "\t"
        //   386: ldc_w           "    "
        //   389: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   392: astore_1       
        //   393: aload_0        
        //   394: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   397: aload_0        
        //   398: getfield        net/minecraft/client/gui/GuiWinGame.mc:Lnet/minecraft/client/Minecraft;
        //   401: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   404: aload_1        
        //   405: sipush          274
        //   408: invokevirtual   net/minecraft/client/gui/FontRenderer.listFormattedStringToWidth:(Ljava/lang/String;I)Ljava/util/List;
        //   411: invokeinterface java/util/List.addAll:(Ljava/util/Collection;)Z
        //   416: pop            
        //   417: aload_0        
        //   418: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   421: ldc_w           ""
        //   424: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   429: pop            
        //   430: goto            354
        //   433: aload           4
        //   435: invokevirtual   java/io/InputStream.close:()V
        //   438: aload_0        
        //   439: aload_0        
        //   440: getfield        net/minecraft/client/gui/GuiWinGame.field_146582_i:Ljava/util/List;
        //   443: invokeinterface java/util/List.size:()I
        //   448: bipush          12
        //   450: imul           
        //   451: putfield        net/minecraft/client/gui/GuiWinGame.field_146579_r:I
        //   454: goto            470
        //   457: astore_1       
        //   458: getstatic       net/minecraft/client/gui/GuiWinGame.logger:Lorg/apache/logging/log4j/Logger;
        //   461: ldc_w           "Couldn't load credits"
        //   464: aload_1        
        //   465: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   470: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.sendRespawnPacket();
        }
    }
}
