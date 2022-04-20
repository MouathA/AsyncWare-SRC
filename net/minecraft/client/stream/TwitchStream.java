package net.minecraft.client.stream;

import net.minecraft.client.shader.*;
import java.util.*;
import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.settings.*;
import net.minecraft.event.*;
import tv.twitch.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.net.*;
import com.google.gson.*;
import tv.twitch.chat.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import tv.twitch.broadcast.*;
import net.minecraft.client.renderer.*;

public class TwitchStream implements ChatController.ChatListener, BroadcastController.BroadcastListener, IStream, IngestServerTester.IngestTestListener
{
    private boolean loggedIn;
    private Framebuffer framebuffer;
    private boolean field_152960_l;
    private AuthFailureReason authFailureReason;
    private final Map field_152955_g;
    public static final Marker STREAM_MARKER;
    private static boolean field_152965_q;
    private boolean field_152962_n;
    private String field_176029_e;
    private boolean field_152963_o;
    private final ChatController chatController;
    private final Minecraft mc;
    private long field_152959_k;
    private final BroadcastController broadcastController;
    private int targetFPS;
    private final IChatComponent twitchComponent;
    private static final Logger LOGGER;
    private boolean field_152957_i;
    
    @Override
    public void func_152895_a() {
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, "Logged out of twitch");
    }
    
    @Override
    public void pause() {
        this.broadcastController.func_152847_F();
        this.field_152962_n = true;
        this.updateStreamVolume();
    }
    
    @Override
    public boolean func_152913_F() {
        return this.loggedIn;
    }
    
    @Override
    public void func_176023_d(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Chat failed to initialize");
        }
    }
    
    @Override
    public boolean func_152927_B() {
        return this.field_176029_e != null && this.field_176029_e.equals(this.broadcastController.getChannelInfo().name);
    }
    
    @Override
    public boolean isBroadcasting() {
        return this.broadcastController.isBroadcasting();
    }
    
    @Override
    public void func_176026_a(final Metadata metadata, final long n, final long n2) {
        if (this.isBroadcasting() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            final String func_152809_a = metadata.func_152809_a();
            final String func_152806_b = metadata.func_152806_b();
            final long func_177946_b = this.broadcastController.func_177946_b(metadata.func_152810_c(), func_152844_x + n, func_152809_a, func_152806_b);
            if (func_177946_b < 0L) {
                TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Could not send stream metadata sequence from {} to {}: {}", new Object[] { func_152844_x + n, func_152844_x + n2, metadata });
            }
            else if (this.broadcastController.func_177947_a(metadata.func_152810_c(), func_152844_x + n2, func_177946_b, func_152809_a, func_152806_b)) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Sent stream metadata sequence from {} to {}: {}", new Object[] { func_152844_x + n, func_152844_x + n2, metadata });
            }
            else {
                TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Half-sent stream metadata sequence from {} to {}: {}", new Object[] { func_152844_x + n, func_152844_x + n2, metadata });
            }
        }
    }
    
    @Override
    public void func_152909_x() {
        final IngestServerTester func_152838_J = this.broadcastController.func_152838_J();
        if (func_152838_J != null) {
            func_152838_J.func_153042_a(this);
        }
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return this.authFailureReason;
    }
    
    @Override
    public void func_152907_a(final IngestServerTester ingestServerTester, final IngestServerTester.IngestTestState ingestTestState) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Ingest test state changed to {}", new Object[] { ingestTestState });
        if (ingestTestState == IngestServerTester.IngestTestState.Finished) {
            this.field_152960_l = true;
        }
    }
    
    public static float formatStreamBps(final float n) {
        return 0.1f + n * 0.1f;
    }
    
    static AuthFailureReason access$302(final TwitchStream twitchStream, final AuthFailureReason authFailureReason) {
        return twitchStream.authFailureReason = authFailureReason;
    }
    
    @Override
    public IngestServerTester func_152932_y() {
        return this.broadcastController.isReady();
    }
    
    @Override
    public void func_176017_a(final ChatController.ChatState chatState) {
    }
    
    @Override
    public void func_152901_c() {
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, "Broadcast to Twitch has stopped");
    }
    
    @Override
    public boolean func_152908_z() {
        return this.broadcastController.isIngestTesting();
    }
    
    static Logger access$000() {
        return TwitchStream.LOGGER;
    }
    
    @Override
    public void func_152935_j() {
        final int streamChatEnabled = this.mc.gameSettings.streamChatEnabled;
        final boolean b = this.field_176029_e != null && this.chatController.func_175990_d(this.field_176029_e);
        final boolean b2 = this.chatController.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected);
        if (streamChatEnabled == 2) {
            if (b) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Disconnecting from twitch chat per user options");
                this.chatController.func_175991_l(this.field_176029_e);
            }
        }
        else if (streamChatEnabled == 1) {
            if (b2 && this.broadcastController.func_152849_q()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Connecting to twitch chat per user options");
                this.func_152942_I();
            }
        }
        else if (streamChatEnabled == 0) {
            if (b && !this.isBroadcasting()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Disconnecting from twitch chat as user is no longer streaming");
                this.chatController.func_175991_l(this.field_176029_e);
            }
            else if (b2 && this.isBroadcasting()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Connecting to twitch chat as user is streaming");
                this.func_152942_I();
            }
        }
        this.broadcastController.func_152821_H();
        this.chatController.func_152997_n();
    }
    
    @Override
    public void func_176022_e(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Chat failed to shutdown");
        }
    }
    
    @Override
    public void muteMicrophone(final boolean field_152963_o) {
        this.field_152963_o = field_152963_o;
        this.updateStreamVolume();
    }
    
    @Override
    public boolean func_152936_l() {
        return this.broadcastController.func_152849_q();
    }
    
    @Override
    public void func_152899_b() {
        this.updateStreamVolume();
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, "Broadcast to Twitch has started");
    }
    
    static ChatController access$200(final TwitchStream twitchStream) {
        return twitchStream.chatController;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        STREAM_MARKER = MarkerManager.getMarker("STREAM");
        if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            System.loadLibrary("avutil-ttv-51");
            System.loadLibrary("swresample-ttv-0");
            System.loadLibrary("libmp3lame-ttv");
            if ("\uec9d\uec81\uecdc\uec93\uec80\uec91\uec9a".contains("64")) {
                System.loadLibrary("libmfxsw64");
            }
            else {
                System.loadLibrary("libmfxsw32");
            }
        }
        TwitchStream.field_152965_q = true;
    }
    
    @Override
    public void func_176024_e() {
    }
    
    @Override
    public boolean func_152928_D() {
        return TwitchStream.field_152965_q && this.broadcastController.func_152858_b();
    }
    
    @Override
    public void func_152897_a(final ErrorCode errorCode) {
        if (ErrorCode.succeeded(errorCode)) {
            TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Login attempt successful");
            this.loggedIn = true;
        }
        else {
            TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Login attempt unsuccessful: {} (error code {})", new Object[] { ErrorCode.getString(errorCode), errorCode.getValue() });
            this.loggedIn = false;
        }
    }
    
    @Override
    public void func_180606_a(final String s) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Chat connected");
    }
    
    @Override
    public boolean isPaused() {
        return this.broadcastController.isBroadcastPaused();
    }
    
    @Override
    public void func_152917_b(final String s) {
        this.chatController.func_175986_a(this.field_176029_e, s);
    }
    
    @Override
    public ErrorCode func_152912_E() {
        return TwitchStream.field_152965_q ? this.broadcastController.getErrorCode() : ErrorCode.TTV_EC_OS_TOO_OLD;
    }
    
    @Override
    public void func_152930_t() {
        final GameSettings gameSettings = this.mc.gameSettings;
        final VideoParams func_152834_a = this.broadcastController.func_152834_a(formatStreamKbps(gameSettings.streamKbps), formatStreamFps(gameSettings.streamFps), formatStreamBps(gameSettings.streamBytesPerPixel), this.mc.displayWidth / (float)this.mc.displayHeight);
        switch (gameSettings.streamCompression) {
            case 0: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                break;
            }
            case 1: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                break;
            }
            case 2: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
                break;
            }
        }
        if (this.framebuffer == null) {
            this.framebuffer = new Framebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight, false);
        }
        else {
            this.framebuffer.createBindFramebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight);
        }
        if (gameSettings.streamPreferredServer != null && gameSettings.streamPreferredServer.length() > 0) {
            final IngestServer[] func_152925_v = this.func_152925_v();
            while (0 < func_152925_v.length) {
                final IngestServer ingestServer = func_152925_v[0];
                if (ingestServer.serverUrl.equals(gameSettings.streamPreferredServer)) {
                    this.broadcastController.func_152824_a(ingestServer);
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        this.targetFPS = func_152834_a.targetFps;
        this.field_152957_i = gameSettings.streamSendMetadata;
        this.broadcastController.func_152836_a(func_152834_a);
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, "Streaming at {}/{} at {} kbps to {}", new Object[] { func_152834_a.outputWidth, func_152834_a.outputHeight, func_152834_a.maxKbps, this.broadcastController.func_152833_s().serverUrl });
        this.broadcastController.func_152828_a(null, "Minecraft", null);
    }
    
    @Override
    public void func_152892_c(final ErrorCode errorCode) {
        if (errorCode == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
            chatComponentTranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            chatComponentTranslation.getChatStyle().setUnderlined(true);
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] { chatComponentTranslation });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation2);
        }
        else {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] { ErrorCode.getString(errorCode) });
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation3);
        }
    }
    
    @Override
    public void unpause() {
        this.broadcastController.func_152854_G();
        this.field_152962_n = false;
        this.updateStreamVolume();
    }
    
    static BroadcastController access$100(final TwitchStream twitchStream) {
        return twitchStream.broadcastController;
    }
    
    @Override
    public void func_152911_a(final Metadata metadata, final long n) {
        if (this.isBroadcasting() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            if (!this.broadcastController.func_152840_a(metadata.func_152810_c(), func_152844_x + n, metadata.func_152809_a(), metadata.func_152806_b())) {
                TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Couldn't send stream metadata action at {}: {}", new Object[] { func_152844_x + n, metadata });
            }
            else {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Sent stream metadata action at {}: {}", new Object[] { func_152844_x + n, metadata });
            }
        }
    }
    
    @Override
    public void func_180607_b(final String s) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Chat disconnected");
        this.field_152955_g.clear();
    }
    
    @Override
    public void func_180605_a(final String p0, final ChatRawMessage[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_3       
        //     2: aload_3        
        //     3: arraylength    
        //     4: istore          4
        //     6: iconst_0       
        //     7: iload           4
        //     9: if_icmpge       335
        //    12: aload_3        
        //    13: iconst_0       
        //    14: aaload         
        //    15: astore          6
        //    17: aload_0        
        //    18: aload           6
        //    20: getfield        tv/twitch/chat/ChatRawMessage.userName:Ljava/lang/String;
        //    23: aload           6
        //    25: invokespecial   net/minecraft/client/stream/TwitchStream.func_176027_a:(Ljava/lang/String;Ltv/twitch/chat/ChatRawMessage;)V
        //    28: aload_0        
        //    29: aload           6
        //    31: getfield        tv/twitch/chat/ChatRawMessage.modes:Ljava/util/HashSet;
        //    34: aload           6
        //    36: getfield        tv/twitch/chat/ChatRawMessage.subscriptions:Ljava/util/HashSet;
        //    39: aload_0        
        //    40: getfield        net/minecraft/client/stream/TwitchStream.mc:Lnet/minecraft/client/Minecraft;
        //    43: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    46: getfield        net/minecraft/client/settings/GameSettings.streamChatUserFilter:I
        //    49: ifeq            329
        //    52: new             Lnet/minecraft/util/ChatComponentText;
        //    55: dup            
        //    56: aload           6
        //    58: getfield        tv/twitch/chat/ChatRawMessage.userName:Ljava/lang/String;
        //    61: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
        //    64: astore          7
        //    66: new             Lnet/minecraft/util/ChatComponentTranslation;
        //    69: dup            
        //    70: new             Ljava/lang/StringBuilder;
        //    73: dup            
        //    74: invokespecial   java/lang/StringBuilder.<init>:()V
        //    77: ldc_w           "chat.stream."
        //    80: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    83: aload           6
        //    85: getfield        tv/twitch/chat/ChatRawMessage.action:Z
        //    88: ifeq            97
        //    91: ldc_w           "emote"
        //    94: goto            100
        //    97: ldc_w           "text"
        //   100: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   103: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   106: iconst_3       
        //   107: anewarray       Ljava/lang/Object;
        //   110: dup            
        //   111: iconst_0       
        //   112: aload_0        
        //   113: getfield        net/minecraft/client/stream/TwitchStream.twitchComponent:Lnet/minecraft/util/IChatComponent;
        //   116: aastore        
        //   117: dup            
        //   118: iconst_1       
        //   119: aload           7
        //   121: aastore        
        //   122: dup            
        //   123: iconst_2       
        //   124: aload           6
        //   126: getfield        tv/twitch/chat/ChatRawMessage.message:Ljava/lang/String;
        //   129: invokestatic    net/minecraft/util/EnumChatFormatting.getTextWithoutFormattingCodes:(Ljava/lang/String;)Ljava/lang/String;
        //   132: aastore        
        //   133: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   136: astore          8
        //   138: aload           6
        //   140: getfield        tv/twitch/chat/ChatRawMessage.action:Z
        //   143: ifeq            161
        //   146: aload           8
        //   148: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   153: iconst_1       
        //   154: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   157: invokevirtual   net/minecraft/util/ChatStyle.setItalic:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   160: pop            
        //   161: new             Lnet/minecraft/util/ChatComponentText;
        //   164: dup            
        //   165: ldc_w           ""
        //   168: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
        //   171: astore          9
        //   173: aload           9
        //   175: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   178: dup            
        //   179: ldc_w           "stream.userinfo.chatTooltip"
        //   182: iconst_0       
        //   183: anewarray       Ljava/lang/Object;
        //   186: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   189: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
        //   194: pop            
        //   195: aload           6
        //   197: getfield        tv/twitch/chat/ChatRawMessage.modes:Ljava/util/HashSet;
        //   200: aload           6
        //   202: getfield        tv/twitch/chat/ChatRawMessage.subscriptions:Ljava/util/HashSet;
        //   205: aconst_null    
        //   206: checkcast       Lnet/minecraft/client/stream/IStream;
        //   209: invokestatic    net/minecraft/client/gui/stream/GuiTwitchUserMode.func_152328_a:(Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/client/stream/IStream;)Ljava/util/List;
        //   212: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   217: astore          10
        //   219: aload           10
        //   221: invokeinterface java/util/Iterator.hasNext:()Z
        //   226: ifeq            265
        //   229: aload           10
        //   231: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   236: checkcast       Lnet/minecraft/util/IChatComponent;
        //   239: astore          11
        //   241: aload           9
        //   243: ldc_w           "\n"
        //   246: invokeinterface net/minecraft/util/IChatComponent.appendText:(Ljava/lang/String;)Lnet/minecraft/util/IChatComponent;
        //   251: pop            
        //   252: aload           9
        //   254: aload           11
        //   256: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
        //   261: pop            
        //   262: goto            219
        //   265: aload           7
        //   267: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   272: new             Lnet/minecraft/event/HoverEvent;
        //   275: dup            
        //   276: getstatic       net/minecraft/event/HoverEvent$Action.SHOW_TEXT:Lnet/minecraft/event/HoverEvent$Action;
        //   279: aload           9
        //   281: invokespecial   net/minecraft/event/HoverEvent.<init>:(Lnet/minecraft/event/HoverEvent$Action;Lnet/minecraft/util/IChatComponent;)V
        //   284: invokevirtual   net/minecraft/util/ChatStyle.setChatHoverEvent:(Lnet/minecraft/event/HoverEvent;)Lnet/minecraft/util/ChatStyle;
        //   287: pop            
        //   288: aload           7
        //   290: invokeinterface net/minecraft/util/IChatComponent.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   295: new             Lnet/minecraft/event/ClickEvent;
        //   298: dup            
        //   299: getstatic       net/minecraft/event/ClickEvent$Action.TWITCH_USER_INFO:Lnet/minecraft/event/ClickEvent$Action;
        //   302: aload           6
        //   304: getfield        tv/twitch/chat/ChatRawMessage.userName:Ljava/lang/String;
        //   307: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   310: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   313: pop            
        //   314: aload_0        
        //   315: getfield        net/minecraft/client/stream/TwitchStream.mc:Lnet/minecraft/client/Minecraft;
        //   318: getfield        net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   321: invokevirtual   net/minecraft/client/gui/GuiIngame.getChatGUI:()Lnet/minecraft/client/gui/GuiNewChat;
        //   324: aload           8
        //   326: invokevirtual   net/minecraft/client/gui/GuiNewChat.printChatMessage:(Lnet/minecraft/util/IChatComponent;)V
        //   329: iinc            5, 1
        //   332: goto            6
        //   335: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0006 (coming from #0332).
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
    
    @Override
    public void func_152900_a(final ErrorCode errorCode, final AuthToken authToken) {
    }
    
    @Override
    public void func_152896_a(final IngestList list) {
    }
    
    @Override
    public String func_152921_C() {
        return this.field_176029_e;
    }
    
    @Override
    public void func_152891_a(final BroadcastController.BroadcastState broadcastState) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Broadcast state changed to {}", new Object[] { broadcastState });
        if (broadcastState == BroadcastController.BroadcastState.Initialized) {
            this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
    }
    
    private void func_176027_a(final String displayName, final ChatRawMessage chatRawMessage) {
        ChatUserInfo chatUserInfo = this.field_152955_g.get(displayName);
        if (chatUserInfo == null) {
            chatUserInfo = new ChatUserInfo();
            chatUserInfo.displayName = displayName;
            this.field_152955_g.put(displayName, chatUserInfo);
        }
        chatUserInfo.subscriptions = chatRawMessage.subscriptions;
        chatUserInfo.modes = chatRawMessage.modes;
        chatUserInfo.nameColorARGB = chatRawMessage.nameColorARGB;
    }
    
    @Override
    public void func_152898_a(final ErrorCode errorCode, final GameInfo[] array) {
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String s) {
        return this.field_152955_g.get(s);
    }
    
    @Override
    public void stopBroadcasting() {
        if (this.broadcastController.stopBroadcasting()) {
            TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, "Stopped streaming to Twitch");
        }
        else {
            TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Could not stop streaming to Twitch");
        }
    }
    
    public TwitchStream(final Minecraft mc, final Property property) {
        this.twitchComponent = new ChatComponentText("Twitch");
        this.field_152955_g = Maps.newHashMap();
        this.targetFPS = 30;
        this.field_152959_k = 0L;
        this.field_152960_l = false;
        this.authFailureReason = AuthFailureReason.ERROR;
        this.mc = mc;
        this.broadcastController = new BroadcastController();
        this.chatController = new ChatController();
        this.broadcastController.func_152841_a(this);
        this.chatController.func_152990_a(this);
        this.broadcastController.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.chatController.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.twitchComponent.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        if (property != null && !Strings.isNullOrEmpty(property.getValue()) && OpenGlHelper.framebufferSupported) {
            final Thread thread = new Thread(this, "Twitch authenticator", property) {
                final Property val$streamProperty;
                final TwitchStream this$0;
                
                @Override
                public void run() {
                    final JsonObject jsonObject = JsonUtils.getJsonObject(JsonUtils.getJsonObject(new JsonParser().parse(HttpUtil.get(new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(this.val$streamProperty.getValue(), "UTF-8")))), "Response"), "token");
                    if (JsonUtils.getBoolean(jsonObject, "valid")) {
                        final String string = JsonUtils.getString(jsonObject, "user_name");
                        TwitchStream.access$000().debug(TwitchStream.STREAM_MARKER, "Authenticated with twitch; username is {}", new Object[] { string });
                        final AuthToken authToken = new AuthToken();
                        authToken.data = this.val$streamProperty.getValue();
                        TwitchStream.access$100(this.this$0).func_152818_a(string, authToken);
                        TwitchStream.access$200(this.this$0).func_152998_c(string);
                        TwitchStream.access$200(this.this$0).func_152994_a(authToken);
                        Runtime.getRuntime().addShutdownHook(new Thread(this, "Twitch shutdown hook") {
                            final TwitchStream$1 this$1;
                            
                            @Override
                            public void run() {
                                this.this$1.this$0.shutdownStream();
                            }
                        });
                        TwitchStream.access$100(this.this$0).func_152817_A();
                        TwitchStream.access$200(this.this$0).func_175984_n();
                    }
                    else {
                        TwitchStream.access$302(this.this$0, AuthFailureReason.INVALID_TOKEN);
                        TwitchStream.access$000().error(TwitchStream.STREAM_MARKER, "Given twitch access token is invalid");
                    }
                }
            };
            thread.setDaemon(true);
            thread.start();
        }
    }
    
    @Override
    public void func_176019_a(final String s, final String s2) {
    }
    
    @Override
    public void func_152894_a(final StreamInfo streamInfo) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Stream info updated; {} viewers on stream ID {}", new Object[] { streamInfo.viewers, streamInfo.streamId });
    }
    
    @Override
    public void func_176025_a(final String s, final ChatTokenizedMessage[] array) {
    }
    
    @Override
    public int func_152920_A() {
        return this.isBroadcasting() ? this.broadcastController.getStreamInfo().viewers : 0;
    }
    
    @Override
    public void func_176021_d() {
    }
    
    @Override
    public void func_176020_d(final String s) {
    }
    
    @Override
    public void func_176016_c(final String s) {
    }
    
    @Override
    public void shutdownStream() {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Shutdown streaming");
        this.broadcastController.statCallback();
        this.chatController.func_175988_p();
    }
    
    public static int formatStreamFps(final float n) {
        return MathHelper.floor_float(10.0f + n * 50.0f);
    }
    
    @Override
    public void func_152922_k() {
        if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused()) {
            final long nanoTime = System.nanoTime();
            if (nanoTime - this.field_152959_k >= 1000000000 / this.targetFPS) {
                final FrameBuffer func_152822_N = this.broadcastController.func_152822_N();
                final Framebuffer framebuffer = this.mc.getFramebuffer();
                this.framebuffer.bindFramebuffer(true);
                GlStateManager.matrixMode(5889);
                GlStateManager.ortho(0.0, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 0.0, 1000.0, 3000.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.translate(0.0f, 0.0f, -2000.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.viewport(0, 0, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight);
                final float n = (float)this.framebuffer.framebufferWidth;
                final float n2 = (float)this.framebuffer.framebufferHeight;
                final float n3 = framebuffer.framebufferWidth / (float)framebuffer.framebufferTextureWidth;
                final float n4 = framebuffer.framebufferHeight / (float)framebuffer.framebufferTextureHeight;
                framebuffer.bindFramebufferTexture();
                GL11.glTexParameterf(3553, 10241, 9729.0f);
                GL11.glTexParameterf(3553, 10240, 9729.0f);
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(0.0, n2, 0.0).tex(0.0, n4).endVertex();
                worldRenderer.pos(n, n2, 0.0).tex(n3, n4).endVertex();
                worldRenderer.pos(n, 0.0, 0.0).tex(n3, 0.0).endVertex();
                worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex();
                instance.draw();
                framebuffer.unbindFramebufferTexture();
                GlStateManager.matrixMode(5889);
                GlStateManager.matrixMode(5888);
                this.broadcastController.captureFramebuffer(func_152822_N);
                this.framebuffer.unbindFramebuffer();
                this.broadcastController.submitStreamFrame(func_152822_N);
                this.field_152959_k = nanoTime;
            }
        }
    }
    
    public static int formatStreamKbps(final float n) {
        return MathHelper.floor_float(230.0f + n * 3270.0f);
    }
    
    @Override
    public boolean isReadyToBroadcast() {
        return this.broadcastController.isReadyToBroadcast();
    }
    
    protected void func_152942_I() {
        final ChatController.ChatState func_153000_j = this.chatController.func_153000_j();
        final String name = this.broadcastController.getChannelInfo().name;
        this.field_176029_e = name;
        if (func_153000_j != ChatController.ChatState.Initialized) {
            TwitchStream.LOGGER.warn("Invalid twitch chat state {}", new Object[] { func_153000_j });
        }
        else if (this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected) {
            this.chatController.func_152986_d(name);
        }
        else {
            TwitchStream.LOGGER.warn("Invalid twitch chat state {}", new Object[] { func_153000_j });
        }
    }
    
    @Override
    public void updateStreamVolume() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/stream/TwitchStream.isBroadcasting:()Z
        //     4: ifeq            78
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/stream/TwitchStream.mc:Lnet/minecraft/client/Minecraft;
        //    11: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    14: getfield        net/minecraft/client/settings/GameSettings.streamGameVolume:F
        //    17: fstore_1       
        //    18: aload_0        
        //    19: getfield        net/minecraft/client/stream/TwitchStream.field_152962_n:Z
        //    22: ifne            31
        //    25: fload_1        
        //    26: fconst_0       
        //    27: fcmpg          
        //    28: ifgt            35
        //    31: iconst_1       
        //    32: goto            36
        //    35: iconst_0       
        //    36: istore_2       
        //    37: aload_0        
        //    38: getfield        net/minecraft/client/stream/TwitchStream.broadcastController:Lnet/minecraft/client/stream/BroadcastController;
        //    41: iload_2        
        //    42: ifeq            49
        //    45: fconst_0       
        //    46: goto            50
        //    49: fload_1        
        //    50: invokevirtual   net/minecraft/client/stream/BroadcastController.setPlaybackDeviceVolume:(F)V
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/stream/TwitchStream.broadcastController:Lnet/minecraft/client/stream/BroadcastController;
        //    57: aload_0        
        //    58: if_icmpne       65
        //    61: fconst_0       
        //    62: goto            75
        //    65: aload_0        
        //    66: getfield        net/minecraft/client/stream/TwitchStream.mc:Lnet/minecraft/client/Minecraft;
        //    69: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    72: getfield        net/minecraft/client/settings/GameSettings.streamMicVolume:F
        //    75: invokevirtual   net/minecraft/client/stream/BroadcastController.setRecordingDeviceVolume:(F)V
        //    78: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void func_152893_b(final ErrorCode errorCode) {
        TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Issue submitting frame: {} (Error code {})", new Object[] { ErrorCode.getString(errorCode), errorCode.getValue() });
        this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("Issue streaming frame: " + errorCode + " (" + ErrorCode.getString(errorCode) + ")"), 2);
    }
    
    @Override
    public void func_176018_a(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
        int n = 0;
        while (0 < array2.length) {
            this.field_152955_g.remove(array2[0].displayName);
            ++n;
        }
        while (0 < array3.length) {
            final ChatUserInfo chatUserInfo = array3[0];
            this.field_152955_g.put(chatUserInfo.displayName, chatUserInfo);
            ++n;
        }
        while (0 < array.length) {
            final ChatUserInfo chatUserInfo2 = array[0];
            this.field_152955_g.put(chatUserInfo2.displayName, chatUserInfo2);
            ++n;
        }
    }
    
    @Override
    public void requestCommercial() {
        if (this.broadcastController.requestCommercial()) {
            TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Requested commercial from Twitch");
        }
        else {
            TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, "Could not request commercial from Twitch");
        }
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return this.broadcastController.func_152855_t().getServers();
    }
}
