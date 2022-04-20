package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import optfine.*;
import java.io.*;

public class GuiVideoSettings extends GuiScreen
{
    private int lastMouseX;
    private GuiScreen parentGuiScreen;
    private boolean is64bit;
    protected String screenTitle;
    private long mouseStillTime;
    private int lastMouseY;
    private GameSettings guiGameSettings;
    private static final String __OBFID = "CL_00000718";
    private static GameSettings.Options[] videoOptions;
    
    public static int getButtonWidth(final GuiButton guiButton) {
        return guiButton.width;
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(58);
        return (index < 0) ? s : s.substring(0, index);
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        while (0 < this.buttonList.size()) {
            final GuiButton guiButton = this.buttonList.get(0);
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + guiButton.width && n2 < guiButton.yPosition + guiButton.height) {
                return guiButton;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    public static int getButtonHeight(final GuiButton guiButton) {
        return guiButton.height;
    }
    
    @Override
    public void drawScreen(final int p0, final int p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/gui/GuiVideoSettings.drawDefaultBackground:()V
        //     4: aload_0        
        //     5: aload_0        
        //     6: getfield        net/minecraft/client/gui/GuiVideoSettings.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/gui/GuiVideoSettings.screenTitle:Ljava/lang/String;
        //    13: aload_0        
        //    14: pop            
        //    15: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //    18: iconst_2       
        //    19: idiv           
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/gui/GuiVideoSettings.is64bit:Z
        //    24: ifeq            32
        //    27: bipush          20
        //    29: goto            33
        //    32: iconst_5       
        //    33: ldc             16777215
        //    35: invokevirtual   net/minecraft/client/gui/GuiVideoSettings.drawCenteredString:(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V
        //    38: aload_0        
        //    39: getfield        net/minecraft/client/gui/GuiVideoSettings.is64bit:Z
        //    42: ifne            54
        //    45: aload_0        
        //    46: getfield        net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    49: getfield        net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //    52: bipush          8
        //    54: aload_0        
        //    55: iload_1        
        //    56: iload_2        
        //    57: fload_3        
        //    58: invokespecial   net/minecraft/client/gui/GuiScreen.drawScreen:(IIF)V
        //    61: iload_1        
        //    62: aload_0        
        //    63: getfield        net/minecraft/client/gui/GuiVideoSettings.lastMouseX:I
        //    66: isub           
        //    67: invokestatic    java/lang/Math.abs:(I)I
        //    70: iconst_5       
        //    71: if_icmpgt       262
        //    74: iload_2        
        //    75: aload_0        
        //    76: getfield        net/minecraft/client/gui/GuiVideoSettings.lastMouseY:I
        //    79: isub           
        //    80: invokestatic    java/lang/Math.abs:(I)I
        //    83: iconst_5       
        //    84: if_icmpgt       262
        //    87: invokestatic    java/lang/System.currentTimeMillis:()J
        //    90: aload_0        
        //    91: getfield        net/minecraft/client/gui/GuiVideoSettings.mouseStillTime:J
        //    94: sipush          700
        //    97: i2l            
        //    98: ladd           
        //    99: lcmp           
        //   100: iflt            259
        //   103: aload_0        
        //   104: pop            
        //   105: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   108: iconst_2       
        //   109: idiv           
        //   110: sipush          150
        //   113: isub           
        //   114: istore          5
        //   116: aload_0        
        //   117: pop            
        //   118: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   121: bipush          6
        //   123: idiv           
        //   124: iconst_5       
        //   125: isub           
        //   126: istore          6
        //   128: iload_2        
        //   129: iload           6
        //   131: bipush          98
        //   133: iadd           
        //   134: if_icmpgt       140
        //   137: iinc            6, 105
        //   140: iload           5
        //   142: sipush          150
        //   145: iadd           
        //   146: sipush          150
        //   149: iadd           
        //   150: istore          7
        //   152: iload           6
        //   154: bipush          84
        //   156: iadd           
        //   157: bipush          10
        //   159: iadd           
        //   160: istore          8
        //   162: aload_0        
        //   163: iload_1        
        //   164: iload_2        
        //   165: invokespecial   net/minecraft/client/gui/GuiVideoSettings.getSelectedButton:(II)Lnet/minecraft/client/gui/GuiButton;
        //   168: astore          9
        //   170: aload           9
        //   172: ifnull          259
        //   175: aload_0        
        //   176: aload           9
        //   178: getfield        net/minecraft/client/gui/GuiButton.displayString:Ljava/lang/String;
        //   181: invokespecial   net/minecraft/client/gui/GuiVideoSettings.getButtonName:(Ljava/lang/String;)Ljava/lang/String;
        //   184: astore          10
        //   186: aload_0        
        //   187: aload           10
        //   189: invokespecial   net/minecraft/client/gui/GuiVideoSettings.getTooltipLines:(Ljava/lang/String;)[Ljava/lang/String;
        //   192: astore          11
        //   194: aload           11
        //   196: ifnonnull       200
        //   199: return         
        //   200: aload_0        
        //   201: iload           5
        //   203: iload           6
        //   205: iload           7
        //   207: iload           8
        //   209: ldc             -536870912
        //   211: ldc             -536870912
        //   213: invokevirtual   net/minecraft/client/gui/GuiVideoSettings.drawGradientRect:(IIIIII)V
        //   216: iconst_0       
        //   217: aload           11
        //   219: arraylength    
        //   220: if_icmpge       259
        //   223: aload           11
        //   225: iconst_0       
        //   226: aaload         
        //   227: astore          13
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/gui/GuiVideoSettings.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   233: aload           13
        //   235: iload           5
        //   237: iconst_5       
        //   238: iadd           
        //   239: i2f            
        //   240: iload           6
        //   242: iconst_5       
        //   243: iadd           
        //   244: iconst_0       
        //   245: iadd           
        //   246: i2f            
        //   247: ldc             14540253
        //   249: invokevirtual   net/minecraft/client/gui/FontRenderer.drawStringWithShadow:(Ljava/lang/String;FFI)I
        //   252: pop            
        //   253: iinc            12, 1
        //   256: goto            216
        //   259: goto            279
        //   262: aload_0        
        //   263: iload_1        
        //   264: putfield        net/minecraft/client/gui/GuiVideoSettings.lastMouseX:I
        //   267: aload_0        
        //   268: iload_2        
        //   269: putfield        net/minecraft/client/gui/GuiVideoSettings.lastMouseY:I
        //   272: aload_0        
        //   273: invokestatic    java/lang/System.currentTimeMillis:()J
        //   276: putfield        net/minecraft/client/gui/GuiVideoSettings.mouseStillTime:J
        //   279: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0054 (coming from #0052).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
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
    
    private String[] getTooltipLines(final String s) {
        return (String[])(s.equals("Graphics") ? new String[] { "Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides." } : (s.equals("Render Distance") ? new String[] { "Visible distance", "  2 Tiny - 32m (fastest)", "  4 Short - 64m (faster)", "  8 Normal - 128m", "  16 Far - 256m (slower)", "  32 Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!", "Values over 16 Far are only effective in local worlds." } : (s.equals("Smooth Lighting") ? new String[] { "Smooth lighting", "  OFF - no smooth lighting (faster)", "  Minimum - simple smooth lighting (slower)", "  Maximum - complex smooth lighting (slowest)" } : (s.equals("Smooth Lighting Level") ? new String[] { "Smooth lighting level", "  OFF - no shadows", "  50% - light shadows", "  100% - dark shadows" } : (s.equals("Max Framerate") ? new String[] { "Max framerate", "  VSync - limit to monitor framerate (60, 30, 20)", "  5-255 - variable", "  Unlimited - no limit (fastest)", "The framerate limit decreases the FPS even if", "the limit value is not reached." } : (s.equals("View Bobbing") ? new String[] { "More realistic movement.", "When using mipmaps set it to OFF for best results." } : (s.equals("GUI Scale") ? new String[] { "GUI Scale", "Smaller GUI might be faster" } : (s.equals("Server Textures") ? new String[] { "Server textures", "Use the resource pack recommended by the server" } : (s.equals("Advanced OpenGL") ? new String[] { "Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card." } : (s.equals("Fog") ? new String[] { "Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card." } : (s.equals("Fog Start") ? new String[] { "Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance." } : (s.equals("Brightness") ? new String[] { "Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects" } : (s.equals("Chunk Loading") ? new String[] { "Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and ", "freezes caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core." } : (s.equals("Alternate Blocks") ? new String[] { "Alternate Blocks", "Uses alternative block models for some blocks.", "Depends on the selected resource pack." } : (s.equals("Use VBOs") ? new String[] { "Vertex Buffer Objects", "Uses an alternative rendering model which is usually", "faster (5-10%) than the default rendering." } : (s.equals("3D Anaglyph") ? new String[] { "3D Anaglyph", "Enables a stereoscopic 3D effect using different colors", "for each eye.", "Requires red-cyan glasses for proper viewing." } : null))))))))))))))));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            final int guiScale = this.guiGameSettings.guiScale;
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != guiScale) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
            if (guiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiDetailSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 202) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiQualitySettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 211) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiAnimationSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 212) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiPerformanceSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == 222) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiOtherSettingsOF(this, this.guiGameSettings));
            }
            if (guiButton.id == GameSettings.Options.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }
    
    public GuiVideoSettings(final GuiScreen parentGuiScreen, final GameSettings guiGameSettings) {
        this.screenTitle = "Video Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.parentGuiScreen = parentGuiScreen;
        this.guiGameSettings = guiGameSettings;
    }
    
    static {
        GuiVideoSettings.videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START, GameSettings.Options.ANAGLYPH };
    }
    
    @Override
    public void initGui() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc_w           "options.videoTitle"
        //     4: iconst_0       
        //     5: anewarray       Ljava/lang/Object;
        //     8: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    11: putfield        net/minecraft/client/gui/GuiVideoSettings.screenTitle:Ljava/lang/String;
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //    18: invokeinterface java/util/List.clear:()V
        //    23: aload_0        
        //    24: iconst_0       
        //    25: putfield        net/minecraft/client/gui/GuiVideoSettings.is64bit:Z
        //    28: iconst_3       
        //    29: anewarray       Ljava/lang/String;
        //    32: dup            
        //    33: iconst_0       
        //    34: ldc_w           "sun.arch.data.model"
        //    37: aastore        
        //    38: dup            
        //    39: iconst_1       
        //    40: ldc_w           "com.ibm.vm.bitmode"
        //    43: aastore        
        //    44: dup            
        //    45: iconst_2       
        //    46: ldc_w           "os.arch"
        //    49: aastore        
        //    50: astore_1       
        //    51: aload_1        
        //    52: astore_2       
        //    53: aload_2        
        //    54: arraylength    
        //    55: istore_3       
        //    56: iconst_0       
        //    57: iload_3        
        //    58: if_icmpge       103
        //    61: aload_2        
        //    62: iconst_0       
        //    63: aaload         
        //    64: astore          5
        //    66: aload           5
        //    68: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //    71: astore          6
        //    73: aload           6
        //    75: ifnull          97
        //    78: aload           6
        //    80: ldc_w           "64"
        //    83: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    86: ifeq            97
        //    89: aload_0        
        //    90: iconst_1       
        //    91: putfield        net/minecraft/client/gui/GuiVideoSettings.is64bit:Z
        //    94: goto            103
        //    97: iinc            4, 1
        //   100: goto            56
        //   103: aload_0        
        //   104: getfield        net/minecraft/client/gui/GuiVideoSettings.is64bit:Z
        //   107: ifne            114
        //   110: iconst_1       
        //   111: goto            115
        //   114: iconst_0       
        //   115: istore_3       
        //   116: getstatic       net/minecraft/client/gui/GuiVideoSettings.videoOptions:[Lnet/minecraft/client/settings/GameSettings$Options;
        //   119: astore          4
        //   121: aload           4
        //   123: arraylength    
        //   124: istore          5
        //   126: iconst_0       
        //   127: iload           5
        //   129: if_icmpge       253
        //   132: aload           4
        //   134: iconst_0       
        //   135: aaload         
        //   136: astore          7
        //   138: aload           7
        //   140: ifnull          247
        //   143: aload_0        
        //   144: pop            
        //   145: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   148: iconst_2       
        //   149: idiv           
        //   150: sipush          155
        //   153: isub           
        //   154: iconst_0       
        //   155: iadd           
        //   156: istore          8
        //   158: aload_0        
        //   159: pop            
        //   160: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   163: bipush          6
        //   165: idiv           
        //   166: iconst_0       
        //   167: iadd           
        //   168: bipush          10
        //   170: isub           
        //   171: istore          9
        //   173: aload           7
        //   175: invokevirtual   net/minecraft/client/settings/GameSettings$Options.getEnumFloat:()Z
        //   178: ifeq            211
        //   181: aload_0        
        //   182: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   185: new             Lnet/minecraft/client/gui/GuiOptionSlider;
        //   188: dup            
        //   189: aload           7
        //   191: invokevirtual   net/minecraft/client/settings/GameSettings$Options.returnEnumOrdinal:()I
        //   194: iconst_0       
        //   195: iload           9
        //   197: aload           7
        //   199: invokespecial   net/minecraft/client/gui/GuiOptionSlider.<init>:(IIILnet/minecraft/client/settings/GameSettings$Options;)V
        //   202: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   207: pop            
        //   208: goto            247
        //   211: aload_0        
        //   212: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   215: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   218: dup            
        //   219: aload           7
        //   221: invokevirtual   net/minecraft/client/settings/GameSettings$Options.returnEnumOrdinal:()I
        //   224: iconst_0       
        //   225: iload           9
        //   227: aload           7
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   233: aload           7
        //   235: invokevirtual   net/minecraft/client/settings/GameSettings.getKeyBinding:(Lnet/minecraft/client/settings/GameSettings$Options;)Ljava/lang/String;
        //   238: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILnet/minecraft/client/settings/GameSettings$Options;Ljava/lang/String;)V
        //   241: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   246: pop            
        //   247: iinc            6, 1
        //   250: goto            126
        //   253: aload_0        
        //   254: pop            
        //   255: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   258: bipush          6
        //   260: idiv           
        //   261: iconst_0       
        //   262: iadd           
        //   263: bipush          10
        //   265: isub           
        //   266: istore          7
        //   268: aload_0        
        //   269: pop            
        //   270: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   273: iconst_2       
        //   274: idiv           
        //   275: sipush          155
        //   278: isub           
        //   279: sipush          160
        //   282: iadd           
        //   283: istore          8
        //   285: aload_0        
        //   286: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   289: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   292: dup            
        //   293: sipush          202
        //   296: iconst_0       
        //   297: iload           7
        //   299: ldc_w           "Quality..."
        //   302: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   305: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   310: pop            
        //   311: iload           7
        //   313: bipush          21
        //   315: iadd           
        //   316: istore          7
        //   318: aload_0        
        //   319: pop            
        //   320: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   323: iconst_2       
        //   324: idiv           
        //   325: sipush          155
        //   328: isub           
        //   329: iconst_0       
        //   330: iadd           
        //   331: istore          8
        //   333: aload_0        
        //   334: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   337: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   340: dup            
        //   341: sipush          201
        //   344: iconst_0       
        //   345: iload           7
        //   347: ldc_w           "Details..."
        //   350: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   353: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   358: pop            
        //   359: aload_0        
        //   360: pop            
        //   361: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   364: iconst_2       
        //   365: idiv           
        //   366: sipush          155
        //   369: isub           
        //   370: sipush          160
        //   373: iadd           
        //   374: istore          8
        //   376: aload_0        
        //   377: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   380: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   383: dup            
        //   384: sipush          212
        //   387: iconst_0       
        //   388: iload           7
        //   390: ldc_w           "Performance..."
        //   393: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   396: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   401: pop            
        //   402: iload           7
        //   404: bipush          21
        //   406: iadd           
        //   407: istore          7
        //   409: aload_0        
        //   410: pop            
        //   411: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   414: iconst_2       
        //   415: idiv           
        //   416: sipush          155
        //   419: isub           
        //   420: iconst_0       
        //   421: iadd           
        //   422: istore          8
        //   424: aload_0        
        //   425: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   428: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   431: dup            
        //   432: sipush          211
        //   435: iconst_0       
        //   436: iload           7
        //   438: ldc_w           "Animations..."
        //   441: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   444: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   449: pop            
        //   450: aload_0        
        //   451: pop            
        //   452: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   455: iconst_2       
        //   456: idiv           
        //   457: sipush          155
        //   460: isub           
        //   461: sipush          160
        //   464: iadd           
        //   465: istore          8
        //   467: aload_0        
        //   468: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   471: new             Lnet/minecraft/client/gui/GuiOptionButton;
        //   474: dup            
        //   475: sipush          222
        //   478: iconst_0       
        //   479: iload           7
        //   481: ldc_w           "Other..."
        //   484: invokespecial   net/minecraft/client/gui/GuiOptionButton.<init>:(IIILjava/lang/String;)V
        //   487: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   492: pop            
        //   493: aload_0        
        //   494: getfield        net/minecraft/client/gui/GuiVideoSettings.buttonList:Ljava/util/List;
        //   497: new             Lnet/minecraft/client/gui/GuiButton;
        //   500: dup            
        //   501: sipush          200
        //   504: aload_0        
        //   505: pop            
        //   506: getstatic       net/minecraft/client/gui/GuiVideoSettings.width:I
        //   509: iconst_2       
        //   510: idiv           
        //   511: bipush          100
        //   513: isub           
        //   514: aload_0        
        //   515: pop            
        //   516: getstatic       net/minecraft/client/gui/GuiVideoSettings.height:I
        //   519: bipush          6
        //   521: idiv           
        //   522: sipush          168
        //   525: iadd           
        //   526: bipush          11
        //   528: iadd           
        //   529: ldc_w           "gui.done"
        //   532: iconst_0       
        //   533: anewarray       Ljava/lang/Object;
        //   536: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   539: invokespecial   net/minecraft/client/gui/GuiButton.<init>:(IIILjava/lang/String;)V
        //   542: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   547: pop            
        //   548: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
