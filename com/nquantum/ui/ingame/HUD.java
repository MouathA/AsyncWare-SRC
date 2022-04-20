package com.nquantum.ui.ingame;

import net.minecraft.client.*;
import com.nquantum.module.*;
import com.nquantum.*;
import net.minecraft.client.gui.*;
import com.nquantum.util.color.*;
import com.mojang.realmsclient.gui.*;
import java.awt.*;
import java.io.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;

public class HUD extends GuiIngame
{
    private FontRenderer font;
    private Minecraft mc;
    
    private void renderArrayListRemix() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final ArrayList<Module> list = new ArrayList<Module>();
        for (final Module module : Asyncware.instance.moduleManager.getModules()) {
            if (module.isToggled()) {
                list.add(module);
            }
        }
        list.sort(HUD::lambda$renderArrayListRemix$3);
        float n = 0.0f;
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("ModuleList Y Spacing").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD R").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD G").getValDouble();
        final int n4 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD B").getValDouble();
        new Color(n2, n3, n4, 255).getRGB();
        final int n5 = (int)Asyncware.instance.settingsManager.getSettingByName("ModuleList Rect Opacity").getValDouble();
        new Color(93, 62, 255, 255).getRGB();
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Color Mode").getValString();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        for (final Module module2 : list) {
            final int hudColor = com.nquantum.module.customize.HUD.hudColor;
            if (valString.equalsIgnoreCase("Static")) {
                new Color(n2, n3, n4, 255).getRGB();
            }
            if (valString.equalsIgnoreCase("Fade")) {
                final int rgb = new Color(n2, n3, n4, 255).getRGB();
                Colors.fadeBetween(rgb, Colors.darker(rgb, 0.52f), (System.currentTimeMillis() + 0) % 1000L / 500.0f);
            }
            if (valString.equalsIgnoreCase("Astolfo")) {
                Colors.Astolfo(0, 0.5f, 1.0f);
            }
            if (valString.equalsIgnoreCase("Rainbow")) {
                Colors.RGBX(1.0f, 0.5f, 1.0f, 0);
            }
            Gui.drawRect(0.0, 0.0, 0.0, 0.0, new Color(24, 24, 24, 0).getRGB());
            Gui.drawRect(scaledResolution.getScaledWidth() - Asyncware.roboto.getStringWidth(module2.getDisplayName()) - 4.0f, n, scaledResolution.getScaledWidth(), Asyncware.roboto.getHeight(module2.getDisplayName()) + n, new Color(0, 0, 0, n5).getRGB());
            Asyncware.roboto.drawStringWithShadow(module2.getDisplayName(), scaledResolution.getScaledWidth() - Asyncware.roboto.getStringWidth(module2.getDisplayName()) - 2.0f, n, hudColor);
            Gui.drawRect(scaledResolution.getScaledWidth() - 1.2f, n, scaledResolution.getScaledWidth(), Asyncware.roboto.getHeight(module2.getDisplayName()) + n, hudColor);
            n += (float)valDouble;
            int n6 = 0;
            ++n6;
        }
    }
    
    private int lambda$renderArrayListWithoutFont$2(final Module module, final Module module2) {
        return this.mc.fontRendererObj.getStringWidth(module2.getDisplayName()) - this.mc.fontRendererObj.getStringWidth(module.getDisplayName());
    }
    
    private void renderInfo() throws FontFormatException, IOException {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Asyncware.sftitle.drawStringWithShadow(ChatFormatting.DARK_RED + "Speed: " + ChatFormatting.WHITE + Math.round((float)(this.mc.thePlayer.getDistance(this.mc.thePlayer.lastTickPosX, this.mc.thePlayer.posY, this.mc.thePlayer.lastTickPosZ) * (Minecraft.getMinecraft().timer.ticksPerSecond * Minecraft.getMinecraft().timer.timerSpeed))) + " b/ps", 1.0f, (float)(scaledResolution.getScaledHeight() - 12), -1);
        Asyncware.sftitle.drawStringWithShadow(ChatFormatting.DARK_RED + "XYZ: " + ChatFormatting.WHITE + Math.round(this.mc.thePlayer.posX) + ", " + Math.round(this.mc.thePlayer.posY) + ", " + Math.round(this.mc.thePlayer.posZ), 1.0f, (float)(scaledResolution.getScaledHeight() - 22), -1);
    }
    
    private static int lambda$renderArrayListRemix$3(final Module module, final Module module2) {
        return (int)(Asyncware.roboto.getStringWidth(module2.getDisplayName()) - Asyncware.roboto.getStringWidth(module.getDisplayName()));
    }
    
    private void renderKeyStrokes() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int n = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode()) ? 125 : 50;
        final int n2 = Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode()) ? 125 : 50;
        final int n3 = Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode()) ? 125 : 50;
        final int n4 = Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode()) ? 125 : 50;
        Gui.drawRect(scaledResolution.getScaledWidth() - 29 - 29, scaledResolution.getScaledHeight() - 4 - 25 - 29, scaledResolution.getScaledWidth() - 4 - 29, scaledResolution.getScaledHeight() - 4 - 29, new Color(0, 0, 0, n).getRGB());
        Gui.drawRect(scaledResolution.getScaledWidth() - 29 - 29 - 29, scaledResolution.getScaledHeight() - 4 - 25, scaledResolution.getScaledWidth() - 4 - 29 - 29, scaledResolution.getScaledHeight() - 4, new Color(0, 0, 0, n2).getRGB());
        Gui.drawRect(scaledResolution.getScaledWidth() - 29 - 29, scaledResolution.getScaledHeight() - 4 - 25, scaledResolution.getScaledWidth() - 4 - 29, scaledResolution.getScaledHeight() - 4, new Color(0, 0, 0, n3).getRGB());
        Gui.drawRect(scaledResolution.getScaledWidth() - 29, scaledResolution.getScaledHeight() - 4 - 25, scaledResolution.getScaledWidth() - 4, scaledResolution.getScaledHeight() - 4, new Color(0, 0, 0, n4).getRGB());
        this.font.drawString("W", scaledResolution.getScaledWidth() - 48, scaledResolution.getScaledHeight() - 49, -1);
        this.font.drawString("A", scaledResolution.getScaledWidth() - 77, scaledResolution.getScaledHeight() - 20, -1);
        this.font.drawString("S", scaledResolution.getScaledWidth() - 48.5, scaledResolution.getScaledHeight() - 20, -1);
        this.font.drawString("D", scaledResolution.getScaledWidth() - 19, scaledResolution.getScaledHeight() - 20, -1);
    }
    
    private static int lambda$renderArrayList$0(final Module module, final Module module2) {
        return (int)(Asyncware.astofolo.getStringWidth(module2.getDisplayName()) - Asyncware.astofolo.getStringWidth(module.getDisplayName()));
    }
    
    private void renderArrayList() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final ArrayList<Object> list = new ArrayList<Object>();
        for (final Module module : Asyncware.instance.moduleManager.getModules()) {
            if (module.isToggled()) {
                list.add(module);
            }
        }
        Collections.sort(list, new ModuleComparator());
        list.sort((Comparator<? super Object>)HUD::lambda$renderArrayList$0);
        float n = 0.0f;
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("ModuleList Y Spacing").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD R").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD G").getValDouble();
        final int n4 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD B").getValDouble();
        final int n5 = (int)Asyncware.instance.settingsManager.getSettingByName("ModuleList Rect Opacity").getValDouble();
        new Color(93, 62, 255, 255).getRGB();
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Color Mode").getValString();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        for (final Module module2 : list) {
            final int hudColor = com.nquantum.module.customize.HUD.hudColor;
            if (valString.equalsIgnoreCase("Static")) {
                com.nquantum.module.customize.HUD.hudColor = new Color(n2, n3, n4, 255).getRGB();
            }
            if (valString.equalsIgnoreCase("Fade")) {
                final int rgb = new Color(n2, n3, n4, 255).getRGB();
                com.nquantum.module.customize.HUD.hudColor = Colors.fadeBetween(rgb, Colors.darker(rgb, 0.52f), (System.currentTimeMillis() + 0) % 1000L / 500.0f);
            }
            if (valString.equalsIgnoreCase("Astolfo")) {
                com.nquantum.module.customize.HUD.hudColor = Colors.Astolfo(10, 0.5f, 1.0f);
            }
            if (valString.equalsIgnoreCase("Rainbow")) {
                com.nquantum.module.customize.HUD.hudColor = Colors.RGBX(1.0f, 0.5f, 1.0f, 0);
            }
            Gui.drawRect(0.0, 0.0, 0.0, 0.0, new Color(24, 24, 24, 0).getRGB());
            Gui.drawRect(scaledResolution.getScaledWidth() - Asyncware.astofolo.getStringWidth(module2.getDisplayName()) - 4.0f, n, scaledResolution.getScaledWidth(), Asyncware.astofolo.getHeight(module2.getDisplayName()) + n, new Color(0, 0, 0, n5).getRGB());
            Asyncware.astofolo.drawStringWithShadow(module2.getDisplayName(), scaledResolution.getScaledWidth() - Asyncware.astofolo.getStringWidth(module2.getDisplayName()) - 1.5f, n, hudColor);
            Gui.drawRect(scaledResolution.getScaledWidth() - Asyncware.astofolo.getStringWidth(module2.getDisplayName()) - 3.2, n, scaledResolution.getScaledWidth() - Asyncware.astofolo.getStringWidth(module2.getDisplayName()) - 4.0f, Asyncware.astofolo.getHeight(module2.getDisplayName()) + n, hudColor);
            Gui.drawRect(scaledResolution.getScaledWidth() - 1.2, n, scaledResolution.getScaledWidth(), Asyncware.astofolo.getHeight(module2.getDisplayName()) + n, hudColor);
            n += (float)valDouble;
            int n6 = 0;
            ++n6;
        }
    }
    
    private static int lambda$renderArrayListMoon$1(final Module module, final Module module2) {
        return (int)(Asyncware.moon.getStringWidth(module2.getDisplayName()) - Asyncware.moon.getStringWidth(module.getDisplayName()));
    }
    
    private void renderArrayListW() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: getfield        com/nquantum/ui/ingame/HUD.mc:Lnet/minecraft/client/Minecraft;
        //     8: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    11: astore_1       
        //    12: new             Ljava/util/ArrayList;
        //    15: dup            
        //    16: invokespecial   java/util/ArrayList.<init>:()V
        //    19: astore_2       
        //    20: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //    23: getfield        com/nquantum/Asyncware.moduleManager:Lcom/nquantum/module/ModuleManager;
        //    26: invokevirtual   com/nquantum/module/ModuleManager.getModules:()Ljava/util/ArrayList;
        //    29: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    32: astore_3       
        //    33: aload_3        
        //    34: invokeinterface java/util/Iterator.hasNext:()Z
        //    39: ifeq            71
        //    42: aload_3        
        //    43: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    48: checkcast       Lcom/nquantum/module/Module;
        //    51: astore          4
        //    53: aload           4
        //    55: invokevirtual   com/nquantum/module/Module.isToggled:()Z
        //    58: ifeq            68
        //    61: aload_2        
        //    62: aload           4
        //    64: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    67: pop            
        //    68: goto            33
        //    71: aload_2        
        //    72: new             Lcom/nquantum/ui/ingame/HUD$ModuleComparator;
        //    75: dup            
        //    76: invokespecial   com/nquantum/ui/ingame/HUD$ModuleComparator.<init>:()V
        //    79: invokestatic    java/util/Collections.sort:(Ljava/util/List;Ljava/util/Comparator;)V
        //    82: fconst_0       
        //    83: fstore_3       
        //    84: new             Ljava/awt/Color;
        //    87: dup            
        //    88: bipush          62
        //    90: sipush          255
        //    93: sipush          210
        //    96: sipush          255
        //    99: invokespecial   java/awt/Color.<init>:(IIII)V
        //   102: invokevirtual   java/awt/Color.getRGB:()I
        //   105: istore          5
        //   107: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   110: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   113: ldc             "ModuleList Y Spacing"
        //   115: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   118: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   121: dstore          7
        //   123: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   126: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   129: ldc             "HUD R"
        //   131: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   134: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   137: d2i            
        //   138: istore          9
        //   140: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   143: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   146: ldc             "HUD G"
        //   148: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   151: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   154: d2i            
        //   155: istore          10
        //   157: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   160: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   163: ldc             "HUD B"
        //   165: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   168: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   171: d2i            
        //   172: istore          11
        //   174: new             Ljava/awt/Color;
        //   177: dup            
        //   178: iload           9
        //   180: iload           10
        //   182: iload           11
        //   184: sipush          255
        //   187: invokespecial   java/awt/Color.<init>:(IIII)V
        //   190: invokevirtual   java/awt/Color.getRGB:()I
        //   193: istore          12
        //   195: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   198: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   201: ldc             "ModuleList Rect Opacity"
        //   203: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   206: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   209: d2i            
        //   210: istore          13
        //   212: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   215: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   218: ldc             "Color Mode"
        //   220: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   223: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValString:()Ljava/lang/String;
        //   226: astore          14
        //   228: aload           14
        //   230: ldc             "Static"
        //   232: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   235: ifeq            259
        //   238: new             Ljava/awt/Color;
        //   241: dup            
        //   242: iload           9
        //   244: iload           10
        //   246: iload           11
        //   248: sipush          255
        //   251: invokespecial   java/awt/Color.<init>:(IIII)V
        //   254: invokevirtual   java/awt/Color.getRGB:()I
        //   257: istore          12
        //   259: aload           14
        //   261: ldc             "Fade"
        //   263: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   266: ifeq            318
        //   269: new             Ljava/awt/Color;
        //   272: dup            
        //   273: iload           9
        //   275: iload           10
        //   277: iload           11
        //   279: sipush          255
        //   282: invokespecial   java/awt/Color.<init>:(IIII)V
        //   285: invokevirtual   java/awt/Color.getRGB:()I
        //   288: istore          15
        //   290: iload           15
        //   292: iload           15
        //   294: ldc             0.52
        //   296: invokestatic    com/nquantum/util/color/Colors.darker:(IF)I
        //   299: invokestatic    java/lang/System.currentTimeMillis:()J
        //   302: iconst_0       
        //   303: i2l            
        //   304: ladd           
        //   305: ldc2_w          1000
        //   308: lrem           
        //   309: l2f            
        //   310: ldc             500.0
        //   312: fdiv           
        //   313: invokestatic    com/nquantum/util/color/Colors.fadeBetween:(IIF)I
        //   316: istore          12
        //   318: aload           14
        //   320: ldc             "Astolfo"
        //   322: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   325: ifeq            338
        //   328: bipush          10
        //   330: ldc             0.5
        //   332: fconst_1       
        //   333: invokestatic    com/nquantum/util/color/Colors.Astolfo:(IFF)I
        //   336: istore          12
        //   338: aload           14
        //   340: ldc             "Rainbow"
        //   342: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   345: ifeq            359
        //   348: fconst_1       
        //   349: ldc             0.5
        //   351: fconst_1       
        //   352: iconst_0       
        //   353: i2l            
        //   354: invokestatic    com/nquantum/util/color/Colors.RGBX:(FFFJ)I
        //   357: istore          12
        //   359: dconst_0       
        //   360: dconst_0       
        //   361: dconst_0       
        //   362: dconst_0       
        //   363: iconst_0       
        //   364: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   367: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   370: aload_2        
        //   371: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   374: astore          15
        //   376: aload           15
        //   378: invokeinterface java/util/Iterator.hasNext:()Z
        //   383: ifeq            703
        //   386: aload           15
        //   388: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   393: checkcast       Lcom/nquantum/module/Module;
        //   396: astore          16
        //   398: aload           14
        //   400: ldc             "Static"
        //   402: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   405: ifeq            429
        //   408: new             Ljava/awt/Color;
        //   411: dup            
        //   412: iload           9
        //   414: iload           10
        //   416: iload           11
        //   418: sipush          255
        //   421: invokespecial   java/awt/Color.<init>:(IIII)V
        //   424: invokevirtual   java/awt/Color.getRGB:()I
        //   427: istore          12
        //   429: aload           14
        //   431: ldc             "Fade"
        //   433: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   436: ifeq            488
        //   439: new             Ljava/awt/Color;
        //   442: dup            
        //   443: iload           9
        //   445: iload           10
        //   447: iload           11
        //   449: sipush          255
        //   452: invokespecial   java/awt/Color.<init>:(IIII)V
        //   455: invokevirtual   java/awt/Color.getRGB:()I
        //   458: istore          17
        //   460: iload           17
        //   462: iload           17
        //   464: ldc             0.52
        //   466: invokestatic    com/nquantum/util/color/Colors.darker:(IF)I
        //   469: invokestatic    java/lang/System.currentTimeMillis:()J
        //   472: iconst_0       
        //   473: i2l            
        //   474: ladd           
        //   475: ldc2_w          1000
        //   478: lrem           
        //   479: l2f            
        //   480: ldc             500.0
        //   482: fdiv           
        //   483: invokestatic    com/nquantum/util/color/Colors.fadeBetween:(IIF)I
        //   486: istore          12
        //   488: aload           14
        //   490: ldc             "Astolfo"
        //   492: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   495: ifeq            509
        //   498: bipush          80
        //   500: fconst_1       
        //   501: ldc_w           0.55
        //   504: invokestatic    com/nquantum/util/color/Colors.Astolfo:(IFF)I
        //   507: istore          12
        //   509: aload           14
        //   511: ldc             "Rainbow"
        //   513: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   516: ifeq            530
        //   519: fconst_1       
        //   520: ldc             0.5
        //   522: fconst_1       
        //   523: iconst_0       
        //   524: i2l            
        //   525: invokestatic    com/nquantum/util/color/Colors.RGBX:(FFFJ)I
        //   528: istore          12
        //   530: iload           12
        //   532: istore          6
        //   534: dconst_0       
        //   535: dconst_0       
        //   536: dconst_0       
        //   537: dconst_0       
        //   538: new             Ljava/awt/Color;
        //   541: dup            
        //   542: bipush          24
        //   544: bipush          24
        //   546: bipush          24
        //   548: iconst_0       
        //   549: invokespecial   java/awt/Color.<init>:(IIII)V
        //   552: invokevirtual   java/awt/Color.getRGB:()I
        //   555: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   558: aload_1        
        //   559: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   562: i2f            
        //   563: getstatic       com/nquantum/Asyncware.s:Lcom/nquantum/util/font/TTFFontRenderer;
        //   566: aload           16
        //   568: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   571: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getStringWidth:(Ljava/lang/String;)F
        //   574: fsub           
        //   575: ldc             4.0
        //   577: fsub           
        //   578: f2d            
        //   579: fload_3        
        //   580: f2d            
        //   581: aload_1        
        //   582: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   585: i2d            
        //   586: getstatic       com/nquantum/Asyncware.s:Lcom/nquantum/util/font/TTFFontRenderer;
        //   589: aload           16
        //   591: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   594: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getHeight:(Ljava/lang/String;)F
        //   597: fload_3        
        //   598: fadd           
        //   599: fconst_1       
        //   600: fadd           
        //   601: f2d            
        //   602: new             Ljava/awt/Color;
        //   605: dup            
        //   606: iconst_0       
        //   607: iconst_0       
        //   608: iconst_0       
        //   609: iload           13
        //   611: invokespecial   java/awt/Color.<init>:(IIII)V
        //   614: invokevirtual   java/awt/Color.getRGB:()I
        //   617: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   620: getstatic       com/nquantum/Asyncware.s:Lcom/nquantum/util/font/TTFFontRenderer;
        //   623: aload           16
        //   625: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   628: aload_1        
        //   629: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   632: i2f            
        //   633: getstatic       com/nquantum/Asyncware.s:Lcom/nquantum/util/font/TTFFontRenderer;
        //   636: aload           16
        //   638: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   641: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getStringWidth:(Ljava/lang/String;)F
        //   644: fsub           
        //   645: fconst_2       
        //   646: fsub           
        //   647: fload_3        
        //   648: iload           6
        //   650: invokevirtual   com/nquantum/util/font/TTFFontRenderer.drawStringWithShadow:(Ljava/lang/String;FFI)V
        //   653: aload_1        
        //   654: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   657: i2f            
        //   658: ldc             1.2
        //   660: fsub           
        //   661: f2d            
        //   662: fload_3        
        //   663: f2d            
        //   664: aload_1        
        //   665: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   668: i2d            
        //   669: getstatic       com/nquantum/Asyncware.s:Lcom/nquantum/util/font/TTFFontRenderer;
        //   672: aload           16
        //   674: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   677: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getHeight:(Ljava/lang/String;)F
        //   680: fload_3        
        //   681: fadd           
        //   682: fconst_1       
        //   683: fadd           
        //   684: f2d            
        //   685: iload           6
        //   687: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   690: fload_3        
        //   691: f2d            
        //   692: dload           7
        //   694: dadd           
        //   695: d2f            
        //   696: fstore_3       
        //   697: iinc            4, 1
        //   700: goto            376
        //   703: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   706: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void renderPlayer() {
        GuiInventory.drawEntityOnScreen(80, 55, 25, 0.0f, 0.0f, this.mc.thePlayer);
    }
    
    private void renderArrayListMoon() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: getfield        com/nquantum/ui/ingame/HUD.mc:Lnet/minecraft/client/Minecraft;
        //     8: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    11: astore_1       
        //    12: new             Ljava/util/ArrayList;
        //    15: dup            
        //    16: invokespecial   java/util/ArrayList.<init>:()V
        //    19: astore_2       
        //    20: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //    23: getfield        com/nquantum/Asyncware.moduleManager:Lcom/nquantum/module/ModuleManager;
        //    26: invokevirtual   com/nquantum/module/ModuleManager.getModules:()Ljava/util/ArrayList;
        //    29: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    32: astore_3       
        //    33: aload_3        
        //    34: invokeinterface java/util/Iterator.hasNext:()Z
        //    39: ifeq            71
        //    42: aload_3        
        //    43: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    48: checkcast       Lcom/nquantum/module/Module;
        //    51: astore          4
        //    53: aload           4
        //    55: invokevirtual   com/nquantum/module/Module.isToggled:()Z
        //    58: ifeq            68
        //    61: aload_2        
        //    62: aload           4
        //    64: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    67: pop            
        //    68: goto            33
        //    71: aload_2        
        //    72: invokedynamic   BootstrapMethod #2, compare:()Ljava/util/Comparator;
        //    77: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //    80: fconst_0       
        //    81: fstore_3       
        //    82: new             Ljava/awt/Color;
        //    85: dup            
        //    86: bipush          62
        //    88: sipush          255
        //    91: sipush          210
        //    94: sipush          255
        //    97: invokespecial   java/awt/Color.<init>:(IIII)V
        //   100: invokevirtual   java/awt/Color.getRGB:()I
        //   103: istore          5
        //   105: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   108: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   111: ldc             "ModuleList Y Spacing"
        //   113: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   116: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   119: dstore          7
        //   121: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   124: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   127: ldc             "HUD R"
        //   129: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   132: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   135: d2i            
        //   136: istore          9
        //   138: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   141: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   144: ldc             "HUD G"
        //   146: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   149: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   152: d2i            
        //   153: istore          10
        //   155: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   158: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   161: ldc             "HUD B"
        //   163: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   166: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   169: d2i            
        //   170: istore          11
        //   172: new             Ljava/awt/Color;
        //   175: dup            
        //   176: iload           9
        //   178: iload           10
        //   180: iload           11
        //   182: sipush          255
        //   185: invokespecial   java/awt/Color.<init>:(IIII)V
        //   188: invokevirtual   java/awt/Color.getRGB:()I
        //   191: istore          12
        //   193: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   196: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   199: ldc             "Color Mode"
        //   201: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   204: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValString:()Ljava/lang/String;
        //   207: astore          13
        //   209: aload           13
        //   211: ldc             "Static"
        //   213: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   216: ifeq            240
        //   219: new             Ljava/awt/Color;
        //   222: dup            
        //   223: iload           9
        //   225: iload           10
        //   227: iload           11
        //   229: sipush          255
        //   232: invokespecial   java/awt/Color.<init>:(IIII)V
        //   235: invokevirtual   java/awt/Color.getRGB:()I
        //   238: istore          12
        //   240: aload           13
        //   242: ldc             "Fade"
        //   244: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   247: ifeq            299
        //   250: new             Ljava/awt/Color;
        //   253: dup            
        //   254: iload           9
        //   256: iload           10
        //   258: iload           11
        //   260: sipush          255
        //   263: invokespecial   java/awt/Color.<init>:(IIII)V
        //   266: invokevirtual   java/awt/Color.getRGB:()I
        //   269: istore          14
        //   271: iload           14
        //   273: iload           14
        //   275: ldc             0.52
        //   277: invokestatic    com/nquantum/util/color/Colors.darker:(IF)I
        //   280: invokestatic    java/lang/System.currentTimeMillis:()J
        //   283: iconst_0       
        //   284: i2l            
        //   285: ladd           
        //   286: ldc2_w          1000
        //   289: lrem           
        //   290: l2f            
        //   291: ldc             500.0
        //   293: fdiv           
        //   294: invokestatic    com/nquantum/util/color/Colors.fadeBetween:(IIF)I
        //   297: istore          12
        //   299: aload           13
        //   301: ldc             "Astolfo"
        //   303: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   306: ifeq            319
        //   309: bipush          10
        //   311: ldc             0.5
        //   313: fconst_1       
        //   314: invokestatic    com/nquantum/util/color/Colors.Astolfo:(IFF)I
        //   317: istore          12
        //   319: aload           13
        //   321: ldc             "Rainbow"
        //   323: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   326: ifeq            340
        //   329: fconst_1       
        //   330: ldc             0.5
        //   332: fconst_1       
        //   333: iconst_0       
        //   334: i2l            
        //   335: invokestatic    com/nquantum/util/color/Colors.RGBX:(FFFJ)I
        //   338: istore          12
        //   340: dconst_0       
        //   341: dconst_0       
        //   342: dconst_0       
        //   343: dconst_0       
        //   344: iconst_0       
        //   345: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   348: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   351: aload_2        
        //   352: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   355: astore          14
        //   357: aload           14
        //   359: invokeinterface java/util/Iterator.hasNext:()Z
        //   364: ifeq            686
        //   367: aload           14
        //   369: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   374: checkcast       Lcom/nquantum/module/Module;
        //   377: astore          15
        //   379: aload           13
        //   381: ldc             "Static"
        //   383: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   386: ifeq            410
        //   389: new             Ljava/awt/Color;
        //   392: dup            
        //   393: iload           9
        //   395: iload           10
        //   397: iload           11
        //   399: sipush          255
        //   402: invokespecial   java/awt/Color.<init>:(IIII)V
        //   405: invokevirtual   java/awt/Color.getRGB:()I
        //   408: istore          12
        //   410: aload           13
        //   412: ldc             "Fade"
        //   414: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   417: ifeq            469
        //   420: new             Ljava/awt/Color;
        //   423: dup            
        //   424: iload           9
        //   426: iload           10
        //   428: iload           11
        //   430: sipush          255
        //   433: invokespecial   java/awt/Color.<init>:(IIII)V
        //   436: invokevirtual   java/awt/Color.getRGB:()I
        //   439: istore          16
        //   441: iload           16
        //   443: iload           16
        //   445: ldc             0.52
        //   447: invokestatic    com/nquantum/util/color/Colors.darker:(IF)I
        //   450: invokestatic    java/lang/System.currentTimeMillis:()J
        //   453: iconst_0       
        //   454: i2l            
        //   455: ladd           
        //   456: ldc2_w          1000
        //   459: lrem           
        //   460: l2f            
        //   461: ldc             500.0
        //   463: fdiv           
        //   464: invokestatic    com/nquantum/util/color/Colors.fadeBetween:(IIF)I
        //   467: istore          12
        //   469: aload           13
        //   471: ldc             "Astolfo"
        //   473: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   476: ifeq            490
        //   479: bipush          80
        //   481: fconst_1       
        //   482: ldc_w           0.55
        //   485: invokestatic    com/nquantum/util/color/Colors.Astolfo:(IFF)I
        //   488: istore          12
        //   490: aload           13
        //   492: ldc             "Rainbow"
        //   494: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   497: ifeq            511
        //   500: fconst_1       
        //   501: ldc             0.5
        //   503: fconst_1       
        //   504: iconst_0       
        //   505: i2l            
        //   506: invokestatic    com/nquantum/util/color/Colors.RGBX:(FFFJ)I
        //   509: istore          12
        //   511: iload           12
        //   513: istore          6
        //   515: dconst_0       
        //   516: dconst_0       
        //   517: dconst_0       
        //   518: dconst_0       
        //   519: new             Ljava/awt/Color;
        //   522: dup            
        //   523: bipush          24
        //   525: bipush          24
        //   527: bipush          24
        //   529: iconst_0       
        //   530: invokespecial   java/awt/Color.<init>:(IIII)V
        //   533: invokevirtual   java/awt/Color.getRGB:()I
        //   536: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   539: aload_1        
        //   540: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   543: i2f            
        //   544: getstatic       com/nquantum/Asyncware.moon:Lcom/nquantum/util/font/TTFFontRenderer;
        //   547: aload           15
        //   549: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   552: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getStringWidth:(Ljava/lang/String;)F
        //   555: fsub           
        //   556: ldc             4.0
        //   558: fsub           
        //   559: f2d            
        //   560: fload_3        
        //   561: f2d            
        //   562: aload_1        
        //   563: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   566: i2d            
        //   567: getstatic       com/nquantum/Asyncware.moon:Lcom/nquantum/util/font/TTFFontRenderer;
        //   570: aload           15
        //   572: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   575: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getHeight:(Ljava/lang/String;)F
        //   578: fload_3        
        //   579: fadd           
        //   580: fconst_1       
        //   581: fadd           
        //   582: f2d            
        //   583: new             Ljava/awt/Color;
        //   586: dup            
        //   587: bipush          9
        //   589: bipush          9
        //   591: bipush          9
        //   593: iconst_0       
        //   594: invokespecial   java/awt/Color.<init>:(IIII)V
        //   597: invokevirtual   java/awt/Color.getRGB:()I
        //   600: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   603: getstatic       com/nquantum/Asyncware.moon:Lcom/nquantum/util/font/TTFFontRenderer;
        //   606: aload           15
        //   608: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   611: aload_1        
        //   612: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   615: i2f            
        //   616: getstatic       com/nquantum/Asyncware.moon:Lcom/nquantum/util/font/TTFFontRenderer;
        //   619: aload           15
        //   621: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   624: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getStringWidth:(Ljava/lang/String;)F
        //   627: fsub           
        //   628: fconst_2       
        //   629: fsub           
        //   630: fload_3        
        //   631: iload           6
        //   633: invokevirtual   com/nquantum/util/font/TTFFontRenderer.drawStringWithShadow:(Ljava/lang/String;FFI)V
        //   636: aload_1        
        //   637: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   640: i2f            
        //   641: ldc             1.2
        //   643: fsub           
        //   644: f2d            
        //   645: fload_3        
        //   646: f2d            
        //   647: aload_1        
        //   648: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   651: i2d            
        //   652: getstatic       com/nquantum/Asyncware.moon:Lcom/nquantum/util/font/TTFFontRenderer;
        //   655: aload           15
        //   657: invokevirtual   com/nquantum/module/Module.getDisplayName:()Ljava/lang/String;
        //   660: invokevirtual   com/nquantum/util/font/TTFFontRenderer.getHeight:(Ljava/lang/String;)F
        //   663: fload_3        
        //   664: fadd           
        //   665: fconst_1       
        //   666: fadd           
        //   667: f2d            
        //   668: iload           6
        //   670: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   673: fload_3        
        //   674: f2d            
        //   675: dload           7
        //   677: dadd           
        //   678: d2f            
        //   679: fstore_3       
        //   680: iinc            4, 1
        //   683: goto            357
        //   686: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   689: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void renderGameOverlay(final float n) {
        super.renderGameOverlay(n);
        if (!this.mc.gameSettings.showDebugInfo) {
            this.renderInfo();
            final String valString = Asyncware.instance.settingsManager.getSettingByName("ModuleList Mode").getValString();
            if (valString.equalsIgnoreCase("Astolfo")) {
                this.renderArrayListW();
            }
            if (valString.equalsIgnoreCase("Moon")) {
                this.renderArrayListMoon();
            }
            if (valString.equalsIgnoreCase("Asyncware")) {
                this.renderArrayListWithoutFont();
            }
            if (valString.equalsIgnoreCase("Custom")) {
                this.renderArrayList();
            }
            if (valString.equalsIgnoreCase("Remix")) {
                this.renderArrayListRemix();
            }
        }
    }
    
    private void renderArrayListWithoutFont() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final ArrayList<Module> list = new ArrayList<Module>();
        for (final Module module : Asyncware.instance.moduleManager.getModules()) {
            if (module.isToggled()) {
                list.add(module);
            }
        }
        list.sort(this::lambda$renderArrayListWithoutFont$2);
        float n = 0.0f;
        Asyncware.instance.settingsManager.getSettingByName("ModuleList Y Spacing").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD R").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD G").getValDouble();
        final int n4 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD B").getValDouble();
        final int n5 = (int)Asyncware.instance.settingsManager.getSettingByName("ModuleList Rect Opacity").getValDouble();
        new Color(93, 62, 255, 255).getRGB();
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Color Mode").getValString();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        for (final Module module2 : list) {
            final int hudColor = com.nquantum.module.customize.HUD.hudColor;
            if (valString.equalsIgnoreCase("Static")) {
                com.nquantum.module.customize.HUD.hudColor = new Color(n2, n3, n4, 255).getRGB();
            }
            if (valString.equalsIgnoreCase("Fade")) {
                final int rgb = new Color(n2, n3, n4, 255).getRGB();
                com.nquantum.module.customize.HUD.hudColor = Colors.fadeBetween(rgb, Colors.darker(rgb, 0.52f), (System.currentTimeMillis() + 0) % 1000L / 500.0f);
            }
            if (valString.equalsIgnoreCase("Astolfo")) {
                com.nquantum.module.customize.HUD.hudColor = Colors.Astolfo(10, 0.5f, 1.0f);
            }
            if (valString.equalsIgnoreCase("Rainbow")) {
                com.nquantum.module.customize.HUD.hudColor = Colors.RGBX(1.0f, 0.5f, 1.0f, 0);
            }
            Gui.drawRect(0.0, 0.0, 0.0, 0.0, new Color(24, 24, 24, 0).getRGB());
            Gui.drawRect(scaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(module2.getDisplayName()) - 4.0f, n - 2.0f, scaledResolution.getScaledWidth(), this.mc.fontRendererObj.FONT_HEIGHT + n, new Color(0, 0, 0, n5).getRGB());
            this.mc.fontRendererObj.drawStringWithShadow(module2.getDisplayName(), (float)(scaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(module2.getDisplayName()) - 2), n, hudColor);
            Gui.drawRect(scaledResolution.getScaledWidth() - 1.2f, n - 2.0f, scaledResolution.getScaledWidth(), this.mc.fontRendererObj.FONT_HEIGHT + n, hudColor);
            n += 11.0f;
            int n6 = 0;
            ++n6;
        }
    }
    
    public HUD(final Minecraft minecraft) {
        super(minecraft);
        this.mc = Minecraft.getMinecraft();
        this.font = this.mc.fontRendererObj;
    }
    
    public static class ModuleComparator implements Comparator
    {
        public int compare(final Module module, final Module module2) {
            if (Asyncware.astofolo.getStringWidth(module.getDisplayName()) > Asyncware.astofolo.getStringWidth(module2.getDisplayName())) {
                return -1;
            }
            if (Asyncware.astofolo.getStringWidth(module.getDisplayName()) < Asyncware.astofolo.getStringWidth(module2.getDisplayName())) {
                return 1;
            }
            return 0;
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Module)o, (Module)o2);
        }
    }
}
