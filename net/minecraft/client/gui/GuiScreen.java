package net.minecraft.client.gui;

import net.minecraft.client.*;
import com.google.common.base.*;
import java.net.*;
import net.minecraft.client.renderer.entity.*;
import org.apache.commons.lang3.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import org.lwjgl.input.*;
import java.awt.datatransfer.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.event.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.stats.*;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback
{
    protected Minecraft mc;
    private int touchValue;
    public static int height;
    private GuiButton selectedButton;
    private static final Splitter NEWLINE_SPLITTER;
    private static final Set PROTOCOLS;
    private static final Logger LOGGER;
    protected List labelList;
    private URI clickedLinkURI;
    private int eventButton;
    public static int width;
    protected RenderItem itemRender;
    protected FontRenderer fontRendererObj;
    protected List buttonList;
    private long lastMouseEvent;
    public boolean allowUserInput;
    
    public static void setClipboardString(final String s) {
        if (!StringUtils.isEmpty((CharSequence)s)) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
        }
    }
    
    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }
    
    public GuiScreen() {
        this.buttonList = Lists.newArrayList();
        this.labelList = Lists.newArrayList();
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        int n4 = 0;
        while (0 < this.buttonList.size()) {
            this.buttonList.get(0).drawButton(this.mc, n, n2);
            ++n4;
        }
        while (0 < this.labelList.size()) {
            this.labelList.get(0).drawLabel(this.mc, n, n2);
            ++n4;
        }
    }
    
    public void drawBackground(final int n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(GuiScreen.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, GuiScreen.height, 0.0).tex(0.0, GuiScreen.height / 32.0f + n).color(64, 64, 64, 255).endVertex();
        worldRenderer.pos(GuiScreen.width, GuiScreen.height, 0.0).tex(GuiScreen.width / 32.0f, GuiScreen.height / 32.0f + n).color(64, 64, 64, 255).endVertex();
        worldRenderer.pos(GuiScreen.width, 0.0, 0.0).tex(GuiScreen.width / 32.0f, n).color(64, 64, 64, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, n).color(64, 64, 64, 255).endVertex();
        instance.draw();
    }
    
    public void onResize(final Minecraft minecraft, final int n, final int n2) {
        this.setWorldAndResolution(minecraft, n, n2);
    }
    
    public void initGui() {
    }
    
    protected void setText(final String s, final boolean b) {
    }
    
    protected void drawCreativeTabHoveringText(final String s, final int n, final int n2) {
        this.drawHoveringText(Arrays.asList(s), n, n2);
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (n == 31102009) {
            if (b) {
                this.openWebLink(this.clickedLinkURI);
            }
            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }
    
    public void sendChatMessage(final String s, final boolean b) {
        if (b) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(s);
        }
        this.mc.thePlayer.sendChatMessage(s);
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            while (0 < this.buttonList.size()) {
                final GuiButton selectedButton = this.buttonList.get(0);
                if (selectedButton.mousePressed(this.mc, n, n2)) {
                    (this.selectedButton = selectedButton).playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(selectedButton);
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public void onGuiClosed() {
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.selectedButton != null && n3 == 0) {
            this.selectedButton.mouseReleased(n, n2);
            this.selectedButton = null;
        }
    }
    
    public static boolean isKeyComboCtrlA(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: bipush          30
        //     3: if_icmpne       19
        //     6: ifeq            19
        //     9: ifne            19
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.mc.dispatchKeypresses();
    }
    
    protected boolean handleComponentClick(final IChatComponent p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_1        
        //     7: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //    12: invokevirtual   net/minecraft/util/ChatStyle.getChatClickEvent:()Lnet/minecraft/event/ClickEvent;
        //    15: astore_2       
        //    16: ifne            48
        //    19: aload_1        
        //    20: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //    25: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //    28: ifnull          428
        //    31: aload_0        
        //    32: aload_1        
        //    33: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //    38: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //    41: iconst_0       
        //    42: invokevirtual   net/minecraft/client/gui/GuiScreen.setText:(Ljava/lang/String;Z)V
        //    45: goto            428
        //    48: aload_2        
        //    49: ifnull          428
        //    52: aload_2        
        //    53: invokevirtual   net/minecraft/event/ClickEvent.getAction:()Lnet/minecraft/event/ClickEvent$Action;
        //    56: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //    59: if_acmpne       251
        //    62: aload_0        
        //    63: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //    66: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    69: getfield        net/minecraft/client/settings/GameSettings.chatLinks:Z
        //    72: ifne            77
        //    75: iconst_0       
        //    76: ireturn        
        //    77: new             Ljava/net/URI;
        //    80: dup            
        //    81: aload_2        
        //    82: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //    85: invokespecial   java/net/URI.<init>:(Ljava/lang/String;)V
        //    88: astore_3       
        //    89: aload_3        
        //    90: invokevirtual   java/net/URI.getScheme:()Ljava/lang/String;
        //    93: astore          4
        //    95: aload           4
        //    97: ifnonnull       115
        //   100: new             Ljava/net/URISyntaxException;
        //   103: dup            
        //   104: aload_2        
        //   105: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   108: ldc_w           "Missing protocol"
        //   111: invokespecial   java/net/URISyntaxException.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   114: athrow         
        //   115: getstatic       net/minecraft/client/gui/GuiScreen.PROTOCOLS:Ljava/util/Set;
        //   118: aload           4
        //   120: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   123: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   128: ifne            167
        //   131: new             Ljava/net/URISyntaxException;
        //   134: dup            
        //   135: aload_2        
        //   136: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   139: new             Ljava/lang/StringBuilder;
        //   142: dup            
        //   143: invokespecial   java/lang/StringBuilder.<init>:()V
        //   146: ldc_w           "Unsupported protocol: "
        //   149: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   152: aload           4
        //   154: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   157: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   160: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   163: invokespecial   java/net/URISyntaxException.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   166: athrow         
        //   167: aload_0        
        //   168: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //   171: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   174: getfield        net/minecraft/client/settings/GameSettings.chatLinksPrompt:Z
        //   177: ifeq            210
        //   180: aload_0        
        //   181: aload_3        
        //   182: putfield        net/minecraft/client/gui/GuiScreen.clickedLinkURI:Ljava/net/URI;
        //   185: aload_0        
        //   186: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //   189: new             Lnet/minecraft/client/gui/GuiConfirmOpenLink;
        //   192: dup            
        //   193: aload_0        
        //   194: aload_2        
        //   195: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   198: ldc             31102009
        //   200: iconst_0       
        //   201: invokespecial   net/minecraft/client/gui/GuiConfirmOpenLink.<init>:(Lnet/minecraft/client/gui/GuiYesNoCallback;Ljava/lang/String;IZ)V
        //   204: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   207: goto            426
        //   210: aload_0        
        //   211: aload_3        
        //   212: invokespecial   net/minecraft/client/gui/GuiScreen.openWebLink:(Ljava/net/URI;)V
        //   215: goto            426
        //   218: astore_3       
        //   219: getstatic       net/minecraft/client/gui/GuiScreen.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   222: new             Ljava/lang/StringBuilder;
        //   225: dup            
        //   226: invokespecial   java/lang/StringBuilder.<init>:()V
        //   229: ldc_w           "Can't open url for "
        //   232: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   235: aload_2        
        //   236: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   239: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   242: aload_3        
        //   243: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   248: goto            426
        //   251: aload_2        
        //   252: invokevirtual   net/minecraft/event/ClickEvent.getAction:()Lnet/minecraft/event/ClickEvent$Action;
        //   255: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_FILE:Lnet/minecraft/event/ClickEvent$Action;
        //   258: if_acmpne       284
        //   261: new             Ljava/io/File;
        //   264: dup            
        //   265: aload_2        
        //   266: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   269: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   272: invokevirtual   java/io/File.toURI:()Ljava/net/URI;
        //   275: astore_3       
        //   276: aload_0        
        //   277: aload_3        
        //   278: invokespecial   net/minecraft/client/gui/GuiScreen.openWebLink:(Ljava/net/URI;)V
        //   281: goto            426
        //   284: aload_2        
        //   285: invokevirtual   net/minecraft/event/ClickEvent.getAction:()Lnet/minecraft/event/ClickEvent$Action;
        //   288: getstatic       net/minecraft/event/ClickEvent$Action.SUGGEST_COMMAND:Lnet/minecraft/event/ClickEvent$Action;
        //   291: if_acmpne       306
        //   294: aload_0        
        //   295: aload_2        
        //   296: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   299: iconst_1       
        //   300: invokevirtual   net/minecraft/client/gui/GuiScreen.setText:(Ljava/lang/String;Z)V
        //   303: goto            426
        //   306: aload_2        
        //   307: invokevirtual   net/minecraft/event/ClickEvent.getAction:()Lnet/minecraft/event/ClickEvent$Action;
        //   310: getstatic       net/minecraft/event/ClickEvent$Action.RUN_COMMAND:Lnet/minecraft/event/ClickEvent$Action;
        //   313: if_acmpne       328
        //   316: aload_0        
        //   317: aload_2        
        //   318: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   321: iconst_0       
        //   322: invokevirtual   net/minecraft/client/gui/GuiScreen.sendChatMessage:(Ljava/lang/String;Z)V
        //   325: goto            426
        //   328: aload_2        
        //   329: invokevirtual   net/minecraft/event/ClickEvent.getAction:()Lnet/minecraft/event/ClickEvent$Action;
        //   332: getstatic       net/minecraft/event/ClickEvent$Action.TWITCH_USER_INFO:Lnet/minecraft/event/ClickEvent$Action;
        //   335: if_acmpne       398
        //   338: aload_0        
        //   339: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //   342: invokevirtual   net/minecraft/client/Minecraft.getTwitchStream:()Lnet/minecraft/client/stream/IStream;
        //   345: aload_2        
        //   346: invokevirtual   net/minecraft/event/ClickEvent.getValue:()Ljava/lang/String;
        //   349: invokeinterface net/minecraft/client/stream/IStream.func_152926_a:(Ljava/lang/String;)Ltv/twitch/chat/ChatUserInfo;
        //   354: astore_3       
        //   355: aload_3        
        //   356: ifnull          384
        //   359: aload_0        
        //   360: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //   363: new             Lnet/minecraft/client/gui/stream/GuiTwitchUserMode;
        //   366: dup            
        //   367: aload_0        
        //   368: getfield        net/minecraft/client/gui/GuiScreen.mc:Lnet/minecraft/client/Minecraft;
        //   371: invokevirtual   net/minecraft/client/Minecraft.getTwitchStream:()Lnet/minecraft/client/stream/IStream;
        //   374: aload_3        
        //   375: invokespecial   net/minecraft/client/gui/stream/GuiTwitchUserMode.<init>:(Lnet/minecraft/client/stream/IStream;Ltv/twitch/chat/ChatUserInfo;)V
        //   378: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   381: goto            426
        //   384: getstatic       net/minecraft/client/gui/GuiScreen.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   387: ldc_w           "Tried to handle twitch user but couldn't find them!"
        //   390: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //   395: goto            426
        //   398: getstatic       net/minecraft/client/gui/GuiScreen.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   401: new             Ljava/lang/StringBuilder;
        //   404: dup            
        //   405: invokespecial   java/lang/StringBuilder.<init>:()V
        //   408: ldc_w           "Don't know how to handle "
        //   411: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   414: aload_2        
        //   415: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   418: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   421: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //   426: iconst_1       
        //   427: ireturn        
        //   428: iconst_0       
        //   429: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean isKeyComboCtrlX(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: bipush          45
        //     3: if_icmpne       19
        //     6: ifeq            19
        //     9: ifne            19
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void openWebLink(final URI uri) {
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), uri);
    }
    
    public static boolean isKeyComboCtrlC(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: bipush          46
        //     3: if_icmpne       19
        //     6: ifeq            19
        //     9: ifne            19
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void handleInput() throws IOException {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }
    
    public static String getClipboardString() {
        final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        return "";
    }
    
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        GuiScreen.width = width;
        GuiScreen.height = height;
        this.buttonList.clear();
        this.initGui();
    }
    
    public void handleMouseInput() throws IOException {
        final int n = Mouse.getEventX() * GuiScreen.width / this.mc.displayWidth;
        final int n2 = GuiScreen.height - Mouse.getEventY() * GuiScreen.height / this.mc.displayHeight - 1;
        final int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
                return;
            }
            this.eventButton = eventButton;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(n, n2, this.eventButton);
        }
        else if (eventButton != -1) {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseReleased(n, n2, eventButton);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            this.mouseClickMove(n, n2, this.eventButton, Minecraft.getSystemTime() - this.lastMouseEvent);
        }
    }
    
    public void updateScreen() {
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    public static boolean isKeyComboCtrlV(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: bipush          47
        //     3: if_icmpne       19
        //     6: ifeq            19
        //     9: ifne            19
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        LOGGER = LogManager.getLogger();
        PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
        NEWLINE_SPLITTER = Splitter.on('\n');
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
    }
    
    protected void drawHoveringText(final List list, final int n, final int n2) {
        if (!list.isEmpty()) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.fontRendererObj.getStringWidth(iterator.next());
            }
            int n3 = n + 12;
            int n4 = n2 - 12;
            if (list.size() > 1) {
                final int n5 = 8 + (2 + (list.size() - 1) * 10);
            }
            if (n3 + 0 > GuiScreen.width) {
                n3 -= 28;
            }
            if (n4 + 8 + 6 > GuiScreen.height) {
                n4 = GuiScreen.height - 8 - 6;
            }
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            this.drawGradientRect(n3 - 3, n4 - 4, n3 + 0 + 3, n4 - 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 + 8 + 3, n3 + 0 + 3, n4 + 8 + 4, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 - 3, n3 + 0 + 3, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 4, n4 - 3, n3 - 3, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 + 0 + 3, n4 - 3, n3 + 0 + 4, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 - 3 + 1, n3 - 3 + 1, n4 + 8 + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(n3 + 0 + 2, n4 - 3 + 1, n3 + 0 + 3, n4 + 8 + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(n3 - 3, n4 - 3, n3 + 0 + 3, n4 - 3 + 1, 1347420415, 1347420415);
            this.drawGradientRect(n3 - 3, n4 + 8 + 2, n3 + 0 + 3, n4 + 8 + 3, 1344798847, 1344798847);
            while (0 < list.size()) {
                this.fontRendererObj.drawStringWithShadow(list.get(0), (float)n3, (float)n4, -1);
                n4 += 2;
                n4 += 10;
                int n6 = 0;
                ++n6;
            }
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
        }
    }
    
    public void sendChatMessage(final String s) {
        this.sendChatMessage(s, true);
    }
    
    protected void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        final List tooltip = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        while (0 < tooltip.size()) {
            tooltip.set(0, itemStack.getRarity().rarityColor + tooltip.get(0));
            int n3 = 0;
            ++n3;
        }
        this.drawHoveringText(tooltip, n, n2);
    }
    
    protected void handleComponentHover(final IChatComponent chatComponent, final int n, final int n2) {
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            final HoverEvent chatHoverEvent = chatComponent.getChatStyle().getChatHoverEvent();
            if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ITEM) {
                ItemStack loadItemStackFromNBT = null;
                final NBTTagCompound tagFromJson = JsonToNBT.getTagFromJson(chatHoverEvent.getValue().getUnformattedText());
                if (tagFromJson instanceof NBTTagCompound) {
                    loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(tagFromJson);
                }
                if (loadItemStackFromNBT != null) {
                    this.renderToolTip(loadItemStackFromNBT, n, n2);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", n, n2);
                }
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                if (this.mc.gameSettings.advancedItemTooltips) {
                    final NBTTagCompound tagFromJson2 = JsonToNBT.getTagFromJson(chatHoverEvent.getValue().getUnformattedText());
                    if (tagFromJson2 instanceof NBTTagCompound) {
                        final ArrayList arrayList = Lists.newArrayList();
                        final NBTTagCompound nbtTagCompound = tagFromJson2;
                        arrayList.add(nbtTagCompound.getString("name"));
                        if (nbtTagCompound.hasKey("type", 8)) {
                            final String string = nbtTagCompound.getString("type");
                            arrayList.add("Type: " + string + " (" + EntityList.getIDFromString(string) + ")");
                        }
                        arrayList.add(nbtTagCompound.getString("id"));
                        this.drawHoveringText(arrayList, n, n2);
                    }
                    else {
                        this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", n, n2);
                    }
                }
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                this.drawHoveringText(GuiScreen.NEWLINE_SPLITTER.splitToList((CharSequence)chatHoverEvent.getValue().getFormattedText()), n, n2);
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
                final StatBase oneShotStat = StatList.getOneShotStat(chatHoverEvent.getValue().getUnformattedText());
                if (oneShotStat != null) {
                    final IChatComponent statName = oneShotStat.getStatName();
                    final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (oneShotStat.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                    chatComponentTranslation.getChatStyle().setItalic(true);
                    final String s = (oneShotStat instanceof Achievement) ? ((Achievement)oneShotStat).getDescription() : null;
                    final ArrayList arrayList2 = Lists.newArrayList((Object[])new String[] { statName.getFormattedText(), chatComponentTranslation.getFormattedText() });
                    if (s != null) {
                        arrayList2.addAll(this.fontRendererObj.listFormattedStringToWidth(s, 150));
                    }
                    this.drawHoveringText(arrayList2, n, n2);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", n, n2);
                }
            }
        }
    }
    
    public void drawWorldBackground(final int n) {
        if (this.mc.theWorld != null) {
            this.drawGradientRect(0, 0, GuiScreen.width, GuiScreen.height, -1072689136, -804253680);
        }
        else {
            this.drawBackground(n);
        }
    }
}
