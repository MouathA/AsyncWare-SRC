package net.minecraft.client;

import net.minecraft.profiler.*;
import net.minecraft.client.audio.*;
import net.minecraft.server.integrated.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.shader.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.crash.*;
import com.mojang.authlib.properties.*;
import net.minecraft.client.stream.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import com.nquantum.event.impl.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.*;
import com.nquantum.*;
import org.lwjgl.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.world.storage.*;
import java.net.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.server.*;
import java.nio.*;
import net.minecraft.client.resources.data.*;
import java.text.*;
import net.minecraft.init.*;
import org.apache.commons.io.*;
import net.minecraft.client.main.*;
import java.util.*;
import com.mojang.authlib.yggdrasil.*;
import com.google.common.collect.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.client.resources.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.texture.*;
import com.nquantum.ui.ingame.*;
import net.minecraft.client.multiplayer.*;
import com.nquantum.auth.*;
import net.minecraft.nbt.*;
import org.lwjgl.util.glu.*;
import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.util.*;
import com.nquantum.util.*;

public class Minecraft implements IThreadListener, IPlayerUsage
{
    private LanguageManager mcLanguageManager;
    public final FrameTimer field_181542_y;
    public Timer timer;
    private ItemRenderer itemRenderer;
    private long debugCrashKeyPressTime;
    private MusicTicker mcMusicTicker;
    public static byte[] memoryReserve;
    private PlayerUsageSnooper usageSnooper;
    private boolean field_181541_X;
    private NetworkManager myNetworkManager;
    public EntityRenderer entityRenderer;
    private RenderItem renderItem;
    public boolean renderChunksMany;
    private static final ResourceLocation locationMojangPng;
    public int rightClickDelayTimer;
    public final Profiler mcProfiler;
    public FontRenderer standardGalacticFontRenderer;
    private final boolean jvm64bit;
    private SoundHandler mcSoundHandler;
    private TextureMap textureMapBlocks;
    private String serverName;
    private IntegratedServer theIntegratedServer;
    public boolean field_175611_D;
    private final PropertyMap twitchDetails;
    private final Proxy proxy;
    private RenderManager renderManager;
    private ServerData currentServerData;
    public EntityPlayerSP thePlayer;
    public int displayHeight;
    private BlockRendererDispatcher blockRenderDispatcher;
    long debugUpdateTime;
    public MouseHelper mouseHelper;
    public boolean inGameHasFocus;
    public Session session;
    public final File mcDataDir;
    private final Queue scheduledTasks;
    public GuiAchievement guiAchievement;
    private Framebuffer framebufferMc;
    private int tempDisplayWidth;
    public boolean field_175613_B;
    private final PropertyMap field_181038_N;
    private Entity renderViewEntity;
    public boolean field_175614_C;
    private final MinecraftSessionService sessionService;
    private IStream stream;
    private int joinPlayerCounter;
    private ResourcePackRepository mcResourcePackRepository;
    private boolean isGamePaused;
    private final String launchedVersion;
    public PlayerControllerMP playerController;
    private boolean fullscreen;
    private SkinManager skinManager;
    private final Thread mcThread;
    public EffectRenderer effectRenderer;
    public FontRenderer fontRendererObj;
    public boolean skipRenderWorld;
    private final IMetadataSerializer metadataSerializer_;
    private final DefaultResourcePack mcDefaultResourcePack;
    private static int debugFPS;
    public TextureManager renderEngine;
    public RenderGlobal renderGlobal;
    public String debug;
    private boolean hasCrashed;
    public Entity pointedEntity;
    private static final Logger logger;
    private final List defaultResourcePacks;
    int fpsCounter;
    public WorldClient theWorld;
    public GuiIngame ingameGUI;
    public static final boolean isRunningOnMac;
    public LoadingScreenRenderer loadingScreen;
    private boolean integratedServerIsRunning;
    private final File fileResourcepacks;
    long prevFrameTime;
    private boolean enableGLErrorChecking;
    private IReloadableResourceManager mcResourceManager;
    public GameSettings gameSettings;
    private int leftClickCounter;
    private int tempDisplayHeight;
    private String debugProfilerName;
    volatile boolean running;
    public int displayWidth;
    private ResourceLocation mojangLogo;
    private ModelManager modelManager;
    private CrashReport crashReporter;
    long field_181543_z;
    long systemTime;
    private final File fileAssets;
    private long field_175615_aJ;
    public MovingObjectPosition objectMouseOver;
    private final boolean isDemo;
    private static final List macDisplayModes;
    private static Minecraft theMinecraft;
    private ISaveFormat saveLoader;
    private int serverPort;
    public GuiScreen currentScreen;
    
    private void initStream() {
        this.stream = new TwitchStream(this, (Property)Iterables.getFirst((Iterable)this.twitchDetails.get((Object)"twitch_access_token"), (Object)null));
    }
    
    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this != 0 && !this.theIntegratedServer.getPublic()) {
                this.mcSoundHandler.pauseSounds();
            }
        }
    }
    
    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }
    
    private void displayDebugInfo(final long n) {
        if (this.mcProfiler.profilingEnabled) {
            final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
            final Profiler.Result result = profilingData.remove(0);
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n2 = this.displayWidth - 160 - 10;
            final int n3 = this.displayHeight - 320;
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(n2 - 160 * 1.1f, n3 - 160 * 0.6f - 16.0f, 0.0).color(200, 0, 0, 0).endVertex();
            worldRenderer.pos(n2 - 160 * 1.1f, n3 + 320, 0.0).color(200, 0, 0, 0).endVertex();
            worldRenderer.pos(n2 + 160 * 1.1f, n3 + 320, 0.0).color(200, 0, 0, 0).endVertex();
            worldRenderer.pos(n2 + 160 * 1.1f, n3 - 160 * 0.6f - 16.0f, 0.0).color(200, 0, 0, 0).endVertex();
            instance.draw();
            final double n4 = 0.0;
            if (0 < profilingData.size()) {
                final Profiler.Result result2 = profilingData.get(0);
                final int n5 = MathHelper.floor_double(result2.field_76332_a / 4.0) + 1;
                worldRenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
                final int func_76329_a = result2.func_76329_a();
                worldRenderer.pos(n2, n3, 0.0).color(0, 0, 0, 255).endVertex();
                while (true) {
                    final float n6 = (float)((n4 + result2.field_76332_a * 16777215 / 16777215) * 3.141592653589793 * 2.0 / 100.0);
                    worldRenderer.pos(n2 + MathHelper.sin(n6) * 160, n3 - MathHelper.cos(n6) * 160 * 0.5f, 0.0).color(0, 0, 0, 255).endVertex();
                    int n7 = 0;
                    --n7;
                }
            }
            else {
                final DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                String string = "";
                if (!result.field_76331_c.equals("unspecified")) {
                    string += "[0] ";
                }
                String s;
                if (result.field_76331_c.length() == 0) {
                    s = string + "ROOT ";
                }
                else {
                    s = string + result.field_76331_c + " ";
                }
                this.fontRendererObj.drawStringWithShadow(s, (float)(n2 - 160), (float)(n3 - 1 - 16), 16777215);
                final FontRenderer fontRendererObj = this.fontRendererObj;
                final String string2 = decimalFormat.format(result.field_76330_b) + "%";
                fontRendererObj.drawStringWithShadow(string2, (float)(n2 + 160 - this.fontRendererObj.getStringWidth(string2)), (float)(n3 - 1 - 16), 16777215);
                while (0 < profilingData.size()) {
                    final Profiler.Result result3 = profilingData.get(0);
                    final String s2 = "";
                    String s3;
                    if (result3.field_76331_c.equals("unspecified")) {
                        s3 = s2 + "[?] ";
                    }
                    else {
                        s3 = s2 + "[" + 1 + "] ";
                    }
                    this.fontRendererObj.drawStringWithShadow(s3 + result3.field_76331_c, (float)(n2 - 160), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                    final FontRenderer fontRendererObj2 = this.fontRendererObj;
                    final String string3 = decimalFormat.format(result3.field_76332_a) + "%";
                    fontRendererObj2.drawStringWithShadow(string3, (float)(n2 + 160 - 50 - this.fontRendererObj.getStringWidth(string3)), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                    final FontRenderer fontRendererObj3 = this.fontRendererObj;
                    final String string4 = decimalFormat.format(result3.field_76330_b) + "%";
                    fontRendererObj3.drawStringWithShadow(string4, (float)(n2 + 160 - this.fontRendererObj.getStringWidth(string4)), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                    int func_76329_a = 0;
                    ++func_76329_a;
                }
            }
        }
    }
    
    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }
    
    private void updateDebugProfilerName(int n) {
        final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (profilingData != null && !profilingData.isEmpty()) {
            final Profiler.Result result = profilingData.remove(0);
            if (n == 0) {
                if (result.field_76331_c.length() > 0) {
                    final int lastIndex = this.debugProfilerName.lastIndexOf(".");
                    if (lastIndex >= 0) {
                        this.debugProfilerName = this.debugProfilerName.substring(0, lastIndex);
                    }
                }
            }
            else if (--n < profilingData.size() && !profilingData.get(n).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName += ".";
                }
                this.debugProfilerName += profilingData.get(n).field_76331_c;
            }
        }
    }
    
    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
        this.gameSettings.fullScreen = this.fullscreen;
        if (this.fullscreen) {
            this.updateDisplayMode();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        }
        if (this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
        }
        else {
            this.updateFramebufferSize();
        }
        Display.setFullscreen(this.fullscreen);
        Display.setVSyncEnabled(this.gameSettings.enableVsync);
        this.updateDisplay();
    }
    
    public void runTick() throws IOException {
        if (this.rightClickDelayTimer > 0) {
            --this.rightClickDelayTimer;
        }
        this.mcProfiler.startSection("gui");
        if (!this.isGamePaused) {
            this.ingameGUI.updateTick();
        }
        this.mcProfiler.endSection();
        this.entityRenderer.getMouseOver(1.0f);
        this.mcProfiler.startSection("gameMode");
        if (!this.isGamePaused && this.theWorld != null) {
            this.playerController.updateController();
        }
        this.mcProfiler.endStartSection("textures");
        if (!this.isGamePaused) {
            this.renderEngine.tick();
        }
        if (this.currentScreen == null && this.thePlayer != null) {
            if (this.thePlayer.getHealth() <= 0.0f) {
                this.displayGuiScreen(null);
            }
            else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
                this.displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.leftClickCounter = 10000;
        }
        if (this.currentScreen != null) {
            this.currentScreen.handleInput();
            if (this.currentScreen != null) {
                this.currentScreen.updateScreen();
            }
        }
        if (this.currentScreen != null && !this.currentScreen.allowUserInput) {
            if (this.theWorld != null) {
                if (this.thePlayer != null) {
                    ++this.joinPlayerCounter;
                    if (this.joinPlayerCounter == 30) {
                        this.joinPlayerCounter = 0;
                        this.theWorld.joinEntityInSurroundings(this.thePlayer);
                    }
                }
                this.mcProfiler.endStartSection("gameRenderer");
                if (!this.isGamePaused) {
                    this.entityRenderer.updateRenderer();
                }
                this.mcProfiler.endStartSection("levelRenderer");
                if (!this.isGamePaused) {
                    this.renderGlobal.updateClouds();
                }
                this.mcProfiler.endStartSection("level");
                if (!this.isGamePaused) {
                    if (this.theWorld.getLastLightningBolt() > 0) {
                        this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
                    }
                    this.theWorld.updateEntities();
                }
            }
            else if (this.entityRenderer.isShaderActive()) {
                this.entityRenderer.func_181022_b();
            }
            if (!this.isGamePaused) {
                this.mcMusicTicker.update();
                this.mcSoundHandler.update();
            }
            if (this.theWorld != null) {
                if (!this.isGamePaused) {
                    this.theWorld.setAllowedSpawnTypes(this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                    this.theWorld.tick();
                }
                this.mcProfiler.endStartSection("animateTick");
                if (!this.isGamePaused && this.theWorld != null) {
                    this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
                }
                this.mcProfiler.endStartSection("particles");
                if (!this.isGamePaused) {
                    this.effectRenderer.updateEffects();
                }
            }
            else if (this.myNetworkManager != null) {
                this.mcProfiler.endStartSection("pendingConnection");
                this.myNetworkManager.processReceivedPackets();
            }
            this.mcProfiler.endSection();
            this.systemTime = getSystemTime();
            return;
        }
        this.mcProfiler.endStartSection("mouse");
        int eventButton = 0;
        while (Mouse.next()) {
            eventButton = Mouse.getEventButton();
            KeyBinding.setKeyBindState(-100, Mouse.getEventButtonState());
            if (Mouse.getEventButtonState()) {
                if (this.thePlayer.isSpectator()) {}
                KeyBinding.onTick(-100);
            }
            if (getSystemTime() - this.systemTime <= 200L) {
                final int eventDWheel = Mouse.getEventDWheel();
                if (eventDWheel != 0) {
                    if (this.thePlayer.isSpectator()) {
                        final int n = (eventDWheel < 0) ? -1 : 1;
                        if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
                            this.ingameGUI.getSpectatorGui().func_175259_b(-n);
                        }
                        else {
                            this.thePlayer.capabilities.setFlySpeed(MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + n * 0.005f, 0.0f, 0.2f));
                        }
                    }
                    else {
                        this.thePlayer.inventory.changeCurrentItem(eventDWheel);
                    }
                }
                if (this.currentScreen == null) {
                    if (this.inGameHasFocus || !Mouse.getEventButtonState()) {
                        continue;
                    }
                    this.setIngameFocus();
                }
                else {
                    if (this.currentScreen == null) {
                        continue;
                    }
                    this.currentScreen.handleMouseInput();
                }
            }
        }
        if (this.leftClickCounter > 0) {
            --this.leftClickCounter;
        }
        this.mcProfiler.endStartSection("keyboard");
        while (Keyboard.next()) {
            eventButton = ((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey());
            KeyBinding.setKeyBindState(0, Keyboard.getEventKeyState());
            if (Keyboard.getEventKeyState()) {
                KeyBinding.onTick(0);
            }
            if (this.debugCrashKeyPressTime > 0L) {
                if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                    throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                }
                if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                    this.debugCrashKeyPressTime = -1L;
                }
            }
            else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                this.debugCrashKeyPressTime = getSystemTime();
            }
            this.dispatchKeypresses();
            if (Keyboard.getEventKeyState()) {
                if (this.currentScreen != null) {
                    this.currentScreen.handleKeyboardInput();
                }
                else {
                    new EventKey(0).call();
                    if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                        final GameSettings gameSettings = this.gameSettings;
                        ++gameSettings.thirdPersonView;
                        if (this.gameSettings.thirdPersonView > 2) {
                            this.gameSettings.thirdPersonView = 0;
                        }
                        if (this.gameSettings.thirdPersonView == 0) {
                            this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                        }
                        else if (this.gameSettings.thirdPersonView == 1) {
                            this.entityRenderer.loadEntityShader(null);
                        }
                        this.renderGlobal.setDisplayListEntitiesDirty();
                    }
                    if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                        this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                    }
                }
                if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) {
                    continue;
                }
                while (true) {
                    int n2 = 0;
                    ++n2;
                }
            }
        }
        while (true) {
            if (this.gameSettings.keyBindsHotbar[0].isPressed()) {
                if (this.thePlayer.isSpectator()) {
                    this.ingameGUI.getSpectatorGui().func_175260_a(0);
                }
                else {
                    this.thePlayer.inventory.currentItem = 0;
                }
            }
            ++eventButton;
        }
    }
    
    public static Map getSessionInfo() {
        final HashMap hashMap = Maps.newHashMap();
        hashMap.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
        hashMap.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
        hashMap.put("X-Minecraft-Version", "1.8.8");
        return hashMap;
    }
    
    public NetHandlerPlayClient getNetHandler() {
        return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
    }
    
    public boolean isGamePaused() {
        return this.isGamePaused;
    }
    
    private ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        final int[] rgb = read.getRGB(0, 0, read.getWidth(), read.getHeight(), null, 0, read.getWidth());
        final ByteBuffer allocate = ByteBuffer.allocate(4 * rgb.length);
        final int[] array = rgb;
        while (0 < array.length) {
            final int n = array[0];
            allocate.putInt(n << 8 | (n >> 24 & 0xFF));
            int n2 = 0;
            ++n2;
        }
        allocate.flip();
        return allocate;
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.addScheduledTask(Executors.callable(runnable));
    }
    
    public SoundHandler getSoundHandler() {
        return this.mcSoundHandler;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.theMinecraft;
    }
    
    public boolean isUnicode() {
        return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
    }
    
    public void func_181537_a(final boolean field_181541_X) {
        this.field_181541_X = field_181541_X;
    }
    
    public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Launched Version", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() throws Exception {
                return Minecraft.access$000(this.this$0);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("LWJGL", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return Sys.getVersion();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("OpenGL", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("GL Caps", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return OpenGlHelper.getLogText();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Using VBOs", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return this.this$0.gameSettings.useVbo ? "Yes" : "No";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                final String clientModName = ClientBrandRetriever.getClientModName();
                return clientModName.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + clientModName + "'");
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Type", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return "Client (map_client.txt)";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Resource Packs", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() throws Exception {
                final StringBuilder sb = new StringBuilder();
                for (final Object next : this.this$0.gameSettings.resourcePacks) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(next);
                    if (this.this$0.gameSettings.field_183018_l.contains(next)) {
                        sb.append(" (incompatible)");
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Current Language", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return Minecraft.access$100(this.this$0).getCurrentLanguage().toString();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() throws Exception {
                return this.this$0.mcProfiler.profilingEnabled ? this.this$0.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable("CPU", new Callable(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return OpenGlHelper.func_183029_j();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        if (this.theWorld != null) {
            this.theWorld.addWorldInfoToCrashReport(crashReport);
        }
        return crashReport;
    }
    
    private String func_181538_aA() {
        return (this.theIntegratedServer != null) ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : ((this.currentServerData != null) ? (this.currentServerData.func_181041_d() ? "playing_lan" : "multiplayer") : "out_of_game");
    }
    
    public SkinManager getSkinManager() {
        return this.skinManager;
    }
    
    static LanguageManager access$100(final Minecraft minecraft) {
        return minecraft.mcLanguageManager;
    }
    
    private void createDisplay() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("Loading " + Asyncware.instance.name);
        Display.create(new PixelFormat().withDepthBits(24));
    }
    
    public void launchIntegratedServer(final String s, final String s2, WorldSettings worldSettings) {
        this.loadWorld(null);
        System.gc();
        final ISaveHandler saveLoader = this.saveLoader.getSaveLoader(s, false);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (loadWorldInfo == null && worldSettings != null) {
            loadWorldInfo = new WorldInfo(worldSettings, s);
            saveLoader.saveWorldInfo(loadWorldInfo);
        }
        if (worldSettings == null) {
            worldSettings = new WorldSettings(loadWorldInfo);
        }
        (this.theIntegratedServer = new IntegratedServer(this, s, s2, worldSettings)).startServerThread();
        this.integratedServerIsRunning = true;
        this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            final String userMessage = this.theIntegratedServer.getUserMessage();
            if (userMessage != null) {
                this.loadingScreen.displayLoadingString(I18n.format(userMessage, new Object[0]));
            }
            else {
                this.loadingScreen.displayLoadingString("");
            }
            Thread.sleep(200L);
        }
        this.displayGuiScreen(null);
        final SocketAddress addLocalEndpoint = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        final NetworkManager provideLocalClient = NetworkManager.provideLocalClient(addLocalEndpoint);
        provideLocalClient.setNetHandler(new NetHandlerLoginClient(provideLocalClient, this, null));
        provideLocalClient.sendPacket(new C00Handshake(47, addLocalEndpoint.toString(), 0, EnumConnectionState.LOGIN));
        provideLocalClient.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
        this.myNetworkManager = provideLocalClient;
    }
    
    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }
    
    private void middleClickMouse() {
        if (this.objectMouseOver != null) {
            final boolean isCreativeMode = this.thePlayer.capabilities.isCreativeMode;
            TileEntity tileEntity = null;
            Item item;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                final Block block = this.theWorld.getBlockState(blockPos).getBlock();
                if (block.getMaterial() == Material.air) {
                    return;
                }
                item = block.getItem(this.theWorld, blockPos);
                if (item == null) {
                    return;
                }
                if (isCreativeMode && GuiScreen.isCtrlKeyDown()) {
                    tileEntity = this.theWorld.getTileEntity(blockPos);
                }
                ((item instanceof ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block).getDamageValue(this.theWorld, blockPos);
                item.getHasSubtypes();
            }
            else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !isCreativeMode) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    item = Items.painting;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    item = Items.lead;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    final ItemStack displayedItem = ((EntityItemFrame)this.objectMouseOver.entityHit).getDisplayedItem();
                    if (displayedItem == null) {
                        item = Items.item_frame;
                    }
                    else {
                        item = displayedItem.getItem();
                        displayedItem.getMetadata();
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    switch (Minecraft$18.$SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[((EntityMinecart)this.objectMouseOver.entityHit).getMinecartType().ordinal()]) {
                        case 1: {
                            item = Items.furnace_minecart;
                            break;
                        }
                        case 2: {
                            item = Items.chest_minecart;
                            break;
                        }
                        case 3: {
                            item = Items.tnt_minecart;
                            break;
                        }
                        case 4: {
                            item = Items.hopper_minecart;
                            break;
                        }
                        case 5: {
                            item = Items.command_block_minecart;
                            break;
                        }
                        default: {
                            item = Items.minecart;
                            break;
                        }
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    item = Items.boat;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                    item = Items.armor_stand;
                }
                else {
                    item = Items.spawn_egg;
                    EntityList.getEntityID(this.objectMouseOver.entityHit);
                    if (!EntityList.entityEggs.containsKey(0)) {
                        return;
                    }
                }
            }
            final InventoryPlayer inventory = this.thePlayer.inventory;
            if (tileEntity == null) {
                inventory.setCurrentItem(item, 0, true, isCreativeMode);
            }
            else {
                inventory.setInventorySlotContents(inventory.currentItem, this.func_181036_a(item, 0, tileEntity));
            }
            if (isCreativeMode) {
                this.playerController.sendSlotPacket(inventory.getStackInSlot(inventory.currentItem), this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventory.currentItem);
            }
        }
    }
    
    public void func_181536_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final float n11 = 0.00390625f;
        final float n12 = 0.00390625f;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(n, n2 + n6, 0.0).tex(n3 * n11, (n4 + n6) * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, 0.0).tex((n3 + n5) * n11, (n4 + n6) * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n + n5, n2, 0.0).tex((n3 + n5) * n11, n4 * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n11, n4 * n12).color(n7, n8, n9, n10).endVertex();
        Tessellator.getInstance().draw();
    }
    
    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }
    
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }
    
    public void updateDisplay() {
        this.mcProfiler.startSection("display_update");
        Display.update();
        this.mcProfiler.endSection();
        this.checkWindowResize();
    }
    
    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }
    
    public void shutdown() {
        this.running = false;
    }
    
    public RenderItem getRenderItem() {
        return this.renderItem;
    }
    
    public void run() {
        this.running = true;
        this.startGame();
        while (this.running) {
            if (!this.hasCrashed || this.crashReporter == null) {
                this.runGameLoop();
            }
            else {
                this.displayCrashReport(this.crashReporter);
            }
        }
        this.shutdownMinecraftApplet();
    }
    
    private void sendClickBlockToController(final boolean b) {
        if (!b) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0 && (!this.thePlayer.isUsingItem() || Asyncware.instance.moduleManager.getModuleByName("Multi Action").isToggled())) {
            if (b && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockPos, this.objectMouseOver.sideHit)) {
                    this.effectRenderer.addBlockHitEffects(blockPos, this.objectMouseOver.sideHit);
                    this.thePlayer.swingItem();
                }
            }
            else if (!Asyncware.instance.moduleManager.getModuleByName("Abort Breaking").isToggled()) {
                this.playerController.resetBlockRemoving();
            }
        }
    }
    
    public IStream getTwitchStream() {
        return this.stream;
    }
    
    private void resize(final int n, final int n2) {
        this.displayWidth = Math.max(1, n);
        this.displayHeight = Math.max(1, n2);
        if (this.currentScreen != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(this);
            this.currentScreen.onResize(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }
    
    private void setInitialDisplayMode() throws LWJGLException {
        if (this.fullscreen) {
            Display.setFullscreen(true);
            final DisplayMode displayMode = Display.getDisplayMode();
            this.displayWidth = Math.max(1, displayMode.getWidth());
            this.displayHeight = Math.max(1, displayMode.getHeight());
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }
    
    private static boolean isJvm64bit() {
        final String[] array = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        while (0 < array.length) {
            final String property = System.getProperty(array[0]);
            if (property != null && property.contains("64")) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public String getVersion() {
        return this.launchedVersion;
    }
    
    public MusicTicker func_181535_r() {
        return this.mcMusicTicker;
    }
    
    static String access$000(final Minecraft minecraft) {
        return minecraft.launchedVersion;
    }
    
    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.blockRenderDispatcher;
    }
    
    static {
        logger = LogManager.getLogger();
        locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
        isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
        Minecraft.memoryReserve = new byte[10485760];
        macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("fps", Minecraft.debugFPS);
        playerUsageSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
        playerUsageSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
        playerUsageSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
        playerUsageSnooper.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat("current_action", this.func_181538_aA());
        playerUsageSnooper.addClientStat("endianness", (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big");
        playerUsageSnooper.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        for (final ResourcePackRepository.Entry entry : this.mcResourcePackRepository.getRepositoryEntries()) {
            final StringBuilder append = new StringBuilder().append("resource_pack[");
            final int n = 0;
            int n2 = 0;
            ++n2;
            playerUsageSnooper.addClientStat(append.append(n).append("]").toString(), entry.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerUsageSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }
    
    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }
    
    private void updateDisplayMode() throws LWJGLException {
        final HashSet hashSet = Sets.newHashSet();
        Collections.addAll(hashSet, Display.getAvailableDisplayModes());
        DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
        if (!hashSet.contains(desktopDisplayMode) && Util.getOSType() == Util.EnumOS.OSX) {
            for (final DisplayMode displayMode : Minecraft.macDisplayModes) {
                for (final DisplayMode displayMode2 : hashSet) {
                    if (displayMode2.getBitsPerPixel() == 32 && displayMode2.getWidth() == displayMode.getWidth() && displayMode2.getHeight() == displayMode.getHeight()) {
                        break;
                    }
                }
                for (final DisplayMode displayMode3 : hashSet) {
                    if (displayMode3.getBitsPerPixel() == 32 && displayMode3.getWidth() == displayMode.getWidth() / 2 && displayMode3.getHeight() == displayMode.getHeight() / 2) {
                        desktopDisplayMode = displayMode3;
                        break;
                    }
                }
            }
        }
        Display.setDisplayMode(desktopDisplayMode);
        this.displayWidth = desktopDisplayMode.getWidth();
        this.displayHeight = desktopDisplayMode.getHeight();
    }
    
    private void registerMetadataSerializers() {
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }
    
    public void freeMemory() {
        Minecraft.memoryReserve = new byte[0];
        this.renderGlobal.deleteAllDisplayLists();
        System.gc();
        this.loadWorld(null);
        System.gc();
    }
    
    public void refreshResources() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        final Iterator<ResourcePackRepository.Entry> iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getResourcePack());
        }
        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add(this.mcResourcePackRepository.getResourcePackInstance());
        }
        this.mcResourceManager.reloadResources(arrayList);
        this.mcLanguageManager.parseLanguageMetadata(arrayList);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }
    
    public void crashed(final CrashReport crashReporter) {
        this.hasCrashed = true;
        this.crashReporter = crashReporter;
    }
    
    public void displayCrashReport(final CrashReport crashReport) {
        final File file = new File(new File(getMinecraft().mcDataDir, "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.printToSYSOUT(crashReport.getCompleteReport());
        if (crashReport.getFile() != null) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
            System.exit(-1);
        }
        else if (crashReport.saveToFile(file)) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file.getAbsolutePath());
            System.exit(-1);
        }
        else {
            Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
    
    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }
    
    private void drawSplashScreen(final TextureManager textureManager) throws LWJGLException {
        final ScaledResolution scaledResolution = new ScaledResolution(this);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        final InputStream inputStream = this.mcDefaultResourcePack.getInputStream(Minecraft.locationMojangPng);
        textureManager.bindTexture(this.mojangLogo = textureManager.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputStream))));
        IOUtils.closeQuietly(inputStream);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, this.displayHeight, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(this.displayWidth, this.displayHeight, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(this.displayWidth, 0.0, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_181536_a((scaledResolution.getScaledWidth() - 256) / 2, (scaledResolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256, 255, 255, 255, 255);
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
        GlStateManager.alphaFunc(516, 0.1f);
        this.updateDisplay();
    }
    
    public Minecraft(final GameConfiguration gameConfiguration) {
        this.enableGLErrorChecking = true;
        this.field_181541_X = false;
        this.timer = new Timer(20.0f);
        this.usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
        this.systemTime = getSystemTime();
        this.field_181542_y = new FrameTimer();
        this.field_181543_z = System.nanoTime();
        this.mcProfiler = new Profiler();
        this.debugCrashKeyPressTime = -1L;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = Lists.newArrayList();
        this.scheduledTasks = Queues.newArrayDeque();
        this.field_175615_aJ = 0L;
        this.mcThread = Thread.currentThread();
        this.running = true;
        this.debug = "";
        this.field_175613_B = false;
        this.field_175614_C = false;
        this.field_175611_D = false;
        this.renderChunksMany = true;
        this.debugUpdateTime = getSystemTime();
        this.prevFrameTime = -1L;
        this.debugProfilerName = "root";
        Minecraft.theMinecraft = this;
        this.mcDataDir = gameConfiguration.folderInfo.mcDataDir;
        this.fileAssets = gameConfiguration.folderInfo.assetsDir;
        this.fileResourcepacks = gameConfiguration.folderInfo.resourcePacksDir;
        this.launchedVersion = gameConfiguration.gameInfo.version;
        this.twitchDetails = gameConfiguration.userInfo.userProperties;
        this.field_181038_N = gameConfiguration.userInfo.field_181172_c;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(gameConfiguration.folderInfo.assetsDir, gameConfiguration.folderInfo.assetIndex).getResourceMap());
        this.proxy = ((gameConfiguration.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfiguration.userInfo.proxy);
        this.sessionService = new YggdrasilAuthenticationService(gameConfiguration.userInfo.proxy, UUID.randomUUID().toString()).createMinecraftSessionService();
        this.session = gameConfiguration.userInfo.session;
        Minecraft.logger.info("Setting user: " + this.session.getUsername());
        Minecraft.logger.info("(Session ID is " + this.session.getSessionID() + ")");
        this.isDemo = gameConfiguration.gameInfo.isDemo;
        this.displayWidth = ((gameConfiguration.displayInfo.width > 0) ? gameConfiguration.displayInfo.width : 1);
        this.displayHeight = ((gameConfiguration.displayInfo.height > 0) ? gameConfiguration.displayInfo.height : 1);
        this.tempDisplayWidth = gameConfiguration.displayInfo.width;
        this.tempDisplayHeight = gameConfiguration.displayInfo.height;
        this.fullscreen = gameConfiguration.displayInfo.fullscreen;
        this.jvm64bit = isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (gameConfiguration.serverInfo.serverName != null) {
            this.serverName = gameConfiguration.serverInfo.serverName;
            this.serverPort = gameConfiguration.serverInfo.serverPort;
        }
        ImageIO.setUseCache(false);
    }
    
    public ListenableFuture scheduleResourcesRefresh() {
        return this.addScheduledTask(new Runnable(this) {
            final Minecraft this$0;
            
            @Override
            public void run() {
                this.this$0.refreshResources();
            }
        });
    }
    
    public PropertyMap func_181037_M() {
        if (this.field_181038_N.isEmpty()) {
            this.field_181038_N.putAll((Multimap)this.getSessionService().fillProfileProperties(this.session.getProfile(), false).getProperties());
        }
        return this.field_181038_N;
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
        playerUsageSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
        playerUsageSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
        playerUsageSnooper.addStatToSnooper("launched_version", this.launchedVersion);
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", capabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", capabilities.GL_ARB_base_instance);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", capabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", capabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", capabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", capabilities.GL_ARB_compatibility);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", capabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", capabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", capabilities.GL_ARB_depth_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", capabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", capabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", capabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", capabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", capabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", capabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", capabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", capabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", capabilities.GL_ARB_fragment_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", capabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", capabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", capabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", capabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", capabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", capabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", capabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", capabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", capabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", capabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", capabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multisample]", capabilities.GL_ARB_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", capabilities.GL_ARB_multitexture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", capabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", capabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", capabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", capabilities.GL_ARB_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", capabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", capabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow]", capabilities.GL_ARB_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", capabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", capabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_sync]", capabilities.GL_ARB_sync);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", capabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", capabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", capabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", capabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", capabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", capabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", capabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", capabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", capabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", capabilities.GL_ARB_vertex_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", capabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", capabilities.GL_EXT_bindable_uniform);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", capabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", capabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", capabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", capabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", capabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", capabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", capabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", capabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", capabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", capabilities.GL_EXT_gpu_program_parameters);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", capabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", capabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", capabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", capabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", capabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", capabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", capabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", capabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", capabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", capabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", capabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", capabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", capabilities.GL_EXT_texture_3d);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", capabilities.GL_EXT_texture_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", capabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", capabilities.GL_EXT_texture_integer);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", capabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", capabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", capabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", capabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
    }
    
    public int getLimitFramerate() {
        return (this.theWorld == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
    }
    
    public void setDimensionAndSpawnPlayer(final int dimension) {
        this.theWorld.setInitialSpawnLocation();
        this.theWorld.removeAllEntities();
        String clientBrand = null;
        if (this.thePlayer != null) {
            this.thePlayer.getEntityId();
            this.theWorld.removeEntity(this.thePlayer);
            clientBrand = this.thePlayer.getClientBrand();
        }
        this.renderViewEntity = null;
        final EntityPlayerSP thePlayer = this.thePlayer;
        this.thePlayer = this.playerController.func_178892_a(this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
        this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(thePlayer.getDataWatcher().getAllWatched());
        this.thePlayer.dimension = dimension;
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        this.thePlayer.setClientBrand(clientBrand);
        this.theWorld.spawnEntityInWorld(this.thePlayer);
        this.playerController.flipPlayer(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.setEntityId(0);
        this.playerController.setPlayerCapabilities(this.thePlayer);
        this.thePlayer.setReducedDebug(thePlayer.hasReducedDebug());
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }
    
    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }
    
    public void loadWorld(final WorldClient worldClient, final String s) {
        if (worldClient == null) {
            final NetHandlerPlayClient netHandler = this.getNetHandler();
            if (netHandler != null) {
                netHandler.cleanup();
            }
            if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
                this.theIntegratedServer.initiateShutdown();
                this.theIntegratedServer.setStaticInstance();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.clearAchievements();
            this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
        }
        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(s);
            this.loadingScreen.displayLoadingString("");
        }
        if (worldClient == null && this.theWorld != null) {
            this.mcResourcePackRepository.func_148529_f();
            this.ingameGUI.func_181029_i();
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        this.mcSoundHandler.stopSounds();
        if ((this.theWorld = worldClient) != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(worldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(worldClient);
            }
            if (this.thePlayer == null) {
                this.thePlayer = this.playerController.func_178892_a(worldClient, new StatFileWriter());
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.preparePlayerToSpawn();
            worldClient.spawnEntityInWorld(this.thePlayer);
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(this.thePlayer);
            this.renderViewEntity = this.thePlayer;
        }
        else {
            this.saveLoader.flushCache();
            this.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }
    
    private void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            final InputStream inputStreamAssets = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
            final InputStream inputStreamAssets2 = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
            if (inputStreamAssets != null && inputStreamAssets2 != null) {
                Display.setIcon(new ByteBuffer[] { this.readImageToBuffer(inputStreamAssets), this.readImageToBuffer(inputStreamAssets2) });
            }
            IOUtils.closeQuietly(inputStreamAssets);
            IOUtils.closeQuietly(inputStreamAssets2);
        }
    }
    
    private void updateFramebufferSize() {
        this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }
    
    public static int getDebugFPS() {
        return Minecraft.debugFPS;
    }
    
    private void startGame() throws LWJGLException, IOException {
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
        this.startTimerHackThread();
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        Minecraft.logger.info("LWJGL Version: " + Sys.getVersion());
        this.setWindowIcon();
        this.setInitialDisplayMode();
        this.createDisplay();
        (this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true)).setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.registerMetadataSerializers();
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.drawSplashScreen(this.renderEngine);
        this.initStream();
        this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(this);
        this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
        if (this.gameSettings.language != null) {
            this.fontRendererObj.setUnicodeFlag(this.isUnicode());
            this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener(this.fontRendererObj);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat(this) {
            final Minecraft this$0;
            
            @Override
            public String formatString(final String s) {
                return String.format(s, GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindInventory.getKeyCode()));
            }
        });
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GlStateManager.shadeModel(7425);
        GlStateManager.clearDepth(1.0);
        GlStateManager.depthFunc(515);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.cullFace(1029);
        GlStateManager.matrixMode(5889);
        GlStateManager.matrixMode(5888);
        this.checkGLError("Startup");
        (this.textureMapBlocks = new TextureMap("textures")).setMipmapLevels(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.textureMapBlocks.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
        this.modelManager = new ModelManager(this.textureMapBlocks);
        this.mcResourceManager.registerReloadListener(this.modelManager);
        this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
        this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
        this.itemRenderer = new ItemRenderer(this);
        this.mcResourceManager.registerReloadListener(this.renderItem);
        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
        this.renderGlobal = new RenderGlobal(this);
        this.mcResourceManager.registerReloadListener(this.renderGlobal);
        this.guiAchievement = new GuiAchievement(this);
        GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        this.checkGLError("Post startup");
        this.ingameGUI = new HUD(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
        }
        else {
            this.displayGuiScreen(new GuiAuthenticate(new GuiMainMenu()));
        }
        this.renderEngine.deleteTexture(this.mojangLogo);
        this.mojangLogo = null;
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        Display.setVSyncEnabled(this.gameSettings.enableVsync);
        Asyncware.instance.startClient();
        this.renderGlobal.makeEntityOutlineShader();
    }
    
    public boolean isFullScreen() {
        return this.fullscreen;
    }
    
    public Framebuffer getFramebuffer() {
        return this.framebufferMc;
    }
    
    public static int getGLMaximumTextureSize() {
        do {
            GL11.glTexImage2D(32868, 0, 6408, 16384, 16384, 0, 6408, 5121, (ByteBuffer)null);
        } while (GL11.glGetTexLevelParameteri(32868, 0, 4096) == 0);
        return 16384;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }
    
    private ItemStack func_181036_a(final Item item, final int n, final TileEntity tileEntity) {
        final ItemStack itemStack = new ItemStack(item, 1, n);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        if (item == Items.skull && nbtTagCompound.hasKey("Owner")) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("Owner");
            final NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setTag("SkullOwner", compoundTag);
            itemStack.setTagCompound(tagCompound);
            return itemStack;
        }
        itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagString("(+NBT)"));
        nbtTagCompound2.setTag("Lore", list);
        itemStack.setTagInfo("display", nbtTagCompound2);
        return itemStack;
    }
    
    public void displayGuiScreen(GuiScreen currentScreen) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (currentScreen == null && this.theWorld == null) {
            currentScreen = new GuiMainMenu();
        }
        else if (currentScreen == null && this.thePlayer.getHealth() <= 0.0f) {
            currentScreen = new GuiGameOver();
        }
        if (currentScreen instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages();
        }
        if ((this.currentScreen = currentScreen) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(this);
            currentScreen.setWorldAndResolution(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            this.skipRenderWorld = false;
        }
        else {
            this.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }
    
    public void loadWorld(final WorldClient worldClient) {
        this.loadWorld(worldClient, "");
    }
    
    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }
    
    public void rightClickMouse() {
        if (!this.playerController.func_181040_m()) {
            this.rightClickDelayTimer = 4;
            final ItemStack currentItem = this.thePlayer.inventory.getCurrentItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.warn("Null returned as 'hitResult', this shouldn't happen!");
            }
            else {
                switch (Minecraft$18.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        if (this.playerController.func_178894_a(this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                            break;
                        }
                        if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
                            break;
                        }
                        break;
                    }
                    case 2: {
                        final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                        if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
                            break;
                        }
                        final int n = (currentItem != null) ? currentItem.stackSize : 0;
                        if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, currentItem, blockPos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                            this.thePlayer.swingItem();
                        }
                        if (currentItem == null) {
                            return;
                        }
                        if (currentItem.stackSize == 0) {
                            this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                            break;
                        }
                        if (currentItem.stackSize != n || this.playerController.isInCreativeMode()) {
                            this.entityRenderer.itemRenderer.resetEquippedProgress();
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    public static void stopIntegratedServer() {
        if (Minecraft.theMinecraft != null) {
            final IntegratedServer integratedServer = Minecraft.theMinecraft.getIntegratedServer();
            if (integratedServer != null) {
                integratedServer.stopServer();
            }
        }
    }
    
    private void clickMouse() {
        if (this.leftClickCounter <= 0) {
            this.thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
            else {
                switch (Minecraft$18.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
                        return;
                    }
                    case 2: {
                        final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                        if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air) {
                            this.playerController.clickBlock(blockPos, this.objectMouseOver.sideHit);
                            return;
                        }
                        break;
                    }
                }
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
        }
    }
    
    private void checkGLError(final String s) {
        if (this.enableGLErrorChecking) {
            final int glGetError = GL11.glGetError();
            if (glGetError != 0) {
                final String gluErrorString = GLU.gluErrorString(glGetError);
                Minecraft.logger.error("########## GL ERROR ##########");
                Minecraft.logger.error("@ " + s);
                Minecraft.logger.error(glGetError + ": " + gluErrorString);
            }
        }
    }
    
    public MusicTicker.MusicType getAmbientMusicType() {
        return (this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
    }
    
    public void setRenderViewEntity(final Entity renderViewEntity) {
        this.renderViewEntity = renderViewEntity;
        this.entityRenderer.loadEntityShader(renderViewEntity);
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    private void runGameLoop() throws IOException {
        final long nanoTime = System.nanoTime();
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && this.theWorld != null) {
            final float renderPartialTicks = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = renderPartialTicks;
        }
        else {
            this.timer.updateTimer();
        }
        this.mcProfiler.startSection("scheduledExecutables");
        // monitorenter(scheduledTasks = this.scheduledTasks)
        while (!this.scheduledTasks.isEmpty()) {
            Util.func_181617_a(this.scheduledTasks.poll(), Minecraft.logger);
        }
        // monitorexit(scheduledTasks)
        this.mcProfiler.endSection();
        final long nanoTime2 = System.nanoTime();
        this.mcProfiler.startSection("tick");
        while (0 < this.timer.elapsedTicks) {
            this.runTick();
            int n = 0;
            ++n;
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        final long n2 = System.nanoTime() - nanoTime2;
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.setListener(this.thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GlStateManager.clear(16640);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.func_181560_a(this.timer.renderPartialTicks, nanoTime);
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(n2);
        }
        else {
            this.mcProfiler.profilingEnabled = false;
            this.prevFrameTime = System.nanoTime();
        }
        this.guiAchievement.updateAchievementWindow();
        this.framebufferMc.unbindFramebuffer();
        this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
        this.entityRenderer.renderStreamIndicator(this.timer.renderPartialTicks);
        this.mcProfiler.startSection("root");
        this.updateDisplay();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.stream.func_152935_j();
        this.mcProfiler.endStartSection("submit");
        this.stream.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = (this != 0 && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
        final long nanoTime3 = System.nanoTime();
        this.field_181542_y.func_181747_a(nanoTime3 - this.field_181543_z);
        this.field_181543_z = nanoTime3;
        while (getSystemTime() >= this.debugUpdateTime + 1000L) {
            Minecraft.debugFPS = this.fpsCounter;
            this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", Minecraft.debugFPS, RenderChunk.renderChunksUpdated, (RenderChunk.renderChunksUpdated != 1) ? "s" : "", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "");
            RenderChunk.renderChunksUpdated = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.startSnooper();
            }
        }
        if (this < 0) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync(this.getLimitFramerate());
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
    }
    
    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }
    
    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }
    
    public boolean func_181540_al() {
        return this.field_181541_X;
    }
    
    public Session getSession() {
        return this.session;
    }
    
    public void setServerData(final ServerData currentServerData) {
        this.currentServerData = currentServerData;
    }
    
    public void dispatchKeypresses() {
        final int n = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (n != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (n == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        this.getTwitchStream().stopBroadcasting();
                    }
                    else if (this.getTwitchStream().isReadyToBroadcast()) {
                        this.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                            final Minecraft this$0;
                            
                            @Override
                            public void confirmClicked(final boolean b, final int n) {
                                if (b) {
                                    this.this$0.getTwitchStream().func_152930_t();
                                }
                                this.this$0.displayGuiScreen(null);
                            }
                        }, I18n.format("stream.confirm_start", new Object[0]), "", 0));
                    }
                    else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                        if (this.theWorld != null) {
                            this.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
                        }
                    }
                    else {
                        GuiStreamUnavailable.func_152321_a(this.currentScreen);
                    }
                }
                else if (n == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        if (this.getTwitchStream().isPaused()) {
                            this.getTwitchStream().unpause();
                        }
                        else {
                            this.getTwitchStream().pause();
                        }
                    }
                }
                else if (n == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        this.getTwitchStream().requestCommercial();
                    }
                }
                else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.muteMicrophone(true);
                }
                else if (n == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                }
                else if (n == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
                }
            }
            else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                this.stream.muteMicrophone(false);
            }
        }
    }
    
    public FrameTimer func_181539_aj() {
        return this.field_181542_y;
    }
    
    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }
    
    public boolean isJava64bit() {
        return this.jvm64bit;
    }
    
    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }
    
    private void startTimerHackThread() {
        final Thread thread = new Thread(this, "Timer hack thread") {
            final Minecraft this$0;
            
            @Override
            public void run() {
                while (this.this$0.running) {
                    Thread.sleep(2147483647L);
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    protected void checkWindowResize() {
        if (!this.fullscreen && Display.wasResized()) {
            final int displayWidth = this.displayWidth;
            final int displayHeight = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != displayWidth || this.displayHeight != displayHeight) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    public ListenableFuture addScheduledTask(final Callable p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/apache/commons/lang3/Validate.notNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: aload_0        
        //     6: if_acmpne       43
        //     9: aload_1        
        //    10: invokestatic    com/google/common/util/concurrent/ListenableFutureTask.create:(Ljava/util/concurrent/Callable;)Lcom/google/common/util/concurrent/ListenableFutureTask;
        //    13: astore_2       
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/Minecraft.scheduledTasks:Ljava/util/Queue;
        //    18: dup            
        //    19: astore_3       
        //    20: monitorenter   
        //    21: aload_0        
        //    22: getfield        net/minecraft/client/Minecraft.scheduledTasks:Ljava/util/Queue;
        //    25: aload_2        
        //    26: invokeinterface java/util/Queue.add:(Ljava/lang/Object;)Z
        //    31: pop            
        //    32: aload_2        
        //    33: aload_3        
        //    34: monitorexit    
        //    35: areturn        
        //    36: astore          4
        //    38: aload_3        
        //    39: monitorexit    
        //    40: aload           4
        //    42: athrow         
        //    43: aload_1        
        //    44: invokeinterface java/util/concurrent/Callable.call:()Ljava/lang/Object;
        //    49: invokestatic    com/google/common/util/concurrent/Futures.immediateFuture:(Ljava/lang/Object;)Lcom/google/common/util/concurrent/ListenableFuture;
        //    52: areturn        
        //    53: astore_2       
        //    54: aload_2        
        //    55: invokestatic    com/google/common/util/concurrent/Futures.immediateFailedCheckedFuture:(Ljava/lang/Exception;)Lcom/google/common/util/concurrent/CheckedFuture;
        //    58: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }
    
    public RenderManager getRenderManager() {
        return this.renderManager;
    }
    
    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public void shutdownMinecraftApplet() {
        ConfigSystem.save(Asyncware.instance.moduleManager.getModules(), "default");
        System.out.println("Config saved.");
        Asyncware.instance.stopClient();
        this.stream.shutdownStream();
        Minecraft.logger.info("Stopping!");
        this.loadWorld(null);
        this.mcSoundHandler.unloadSounds();
        Display.destroy();
        if (!this.hasCrashed) {
            System.exit(0);
        }
        System.gc();
    }
    
    public int getDebugFps() {
        return Minecraft.debugFPS;
    }
    
    public final boolean isDemo() {
        return this.isDemo;
    }
    
    public PropertyMap getTwitchDetails() {
        return this.twitchDetails;
    }
    
    public static boolean isGuiEnabled() {
        return Minecraft.theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }
    
    public TextureManager getTextureManager() {
        return this.renderEngine;
    }
}
