package net.minecraft.client.settings;

import com.google.gson.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import org.apache.commons.lang3.*;
import java.lang.reflect.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.io.*;
import optfine.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.stream.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;

public class GameSettings
{
    public float chatScale;
    public boolean touchscreen;
    public boolean ofAnimatedTerrain;
    public String streamPreferredServer;
    public boolean ofAnimatedFire;
    public KeyBinding keyBindScreenshot;
    public boolean showDebugProfilerChart;
    public KeyBinding keyBindDrop;
    public int ofRain;
    public boolean ofLazyChunkLoading;
    public boolean forceUnicodeFont;
    public String ofFullscreenMode;
    public int ofFogType;
    public boolean field_181657_aC;
    public static final int FANCY = 2;
    public boolean ofWeather;
    private static final Gson gson;
    public KeyBinding keyBindStreamStartStop;
    private File optionsFile;
    public boolean ofVoidParticles;
    public boolean hideGUI;
    public int renderDistanceChunks;
    public int ofTrees;
    public KeyBinding keyBindPlayerList;
    public int ofTime;
    public boolean fancyGraphics;
    public boolean ofAnimatedSmoke;
    public boolean ofFireworkParticles;
    public boolean ofFastRender;
    public boolean snooperEnabled;
    public int streamChatEnabled;
    private static final String[] AMBIENT_OCCLUSIONS;
    public int ambientOcclusion;
    public boolean ofCustomColors;
    public boolean chatLinksPrompt;
    public boolean ofSunMoon;
    protected Minecraft mc;
    public KeyBinding keyBindTogglePerspective;
    public boolean ofAnimatedTextures;
    public boolean ofChunkUpdatesDynamic;
    private static final String[] PARTICLES;
    public boolean ofStars;
    public boolean ofWaterParticles;
    public float mouseSensitivity;
    public int mipmapLevels;
    public float chatOpacity;
    public boolean hideServerAddress;
    public int ofConnectedTextures;
    public boolean ofBetterSnow;
    public float gammaSetting;
    public String lastServer;
    private static final String[] field_181149_aW;
    public boolean pauseOnLostFocus;
    private static final Logger logger;
    public int ofAutoSaveTicks;
    public boolean ofAnimatedPortal;
    public boolean ofProfiler;
    public EntityPlayer.EnumChatVisibility chatVisibility;
    public boolean ofAnimatedRedstone;
    private static final String[] STREAM_COMPRESSIONS;
    public int ofAnimatedWater;
    public boolean allowBlockAlternatives;
    public KeyBinding keyBindRight;
    public int ofChunkLoading;
    public KeyBinding keyBindBack;
    public boolean ofNaturalTextures;
    public boolean advancedItemTooltips;
    public EnumDifficulty difficulty;
    public boolean field_181151_V;
    public float ofAoLevel;
    public KeyBinding[] keyBindings;
    public static final int CL_THREADED = 2;
    public boolean ofDrippingWaterLava;
    public boolean ofLagometer;
    public float streamFps;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindStreamCommercials;
    public boolean ofRandomMobs;
    public boolean anaglyph;
    public KeyBinding keyBindFullscreen;
    public boolean ofSwampColors;
    public int limitFramerate;
    public boolean ofAnimatedExplosion;
    public int ofChunkUpdates;
    public boolean ofAnimatedFlame;
    public boolean ofFastMath;
    public int ofAfLevel;
    public KeyBinding keyBindInventory;
    public float chatWidth;
    public boolean chatLinks;
    public static final int ANIM_GENERATED = 1;
    public static final int OFF = 3;
    public static final int ANIM_OFF = 2;
    private static final ParameterizedType typeListString;
    public KeyBinding[] keyBindsHotbar;
    public int ofTranslucentBlocks;
    public static final int FAST = 1;
    public boolean ofCustomSky;
    public KeyBinding keyBindUseItem;
    public int streamChatUserFilter;
    public int ofVignette;
    public int thirdPersonView;
    public boolean field_181150_U;
    public String language;
    public int ofDroppedItems;
    public boolean smoothCamera;
    public static final int CL_DEFAULT = 0;
    public KeyBinding keyBindCommand;
    public KeyBinding keyBindSneak;
    public boolean ofSmoothBiomes;
    public boolean enableVsync;
    public boolean streamSendMetadata;
    public int ofAnimatedLava;
    public int streamCompression;
    public KeyBinding keyBindZoom;
    public KeyBinding keyBindStreamToggleMic;
    public int ofClouds;
    public boolean ofPotionParticles;
    public int ofMipmapType;
    public float saturation;
    public boolean showDebugInfo;
    public KeyBinding keyBindSprint;
    public KeyBinding keyBindSmoothCamera;
    public static final int CL_SMOOTH = 1;
    public KeyBinding keyBindAttack;
    public boolean ofSky;
    public int ofBetterGrass;
    public boolean useVbo;
    public boolean showInventoryAchievementHint;
    public float ofFogStart;
    public KeyBinding keyBindForward;
    public List resourcePacks;
    public float fovSetting;
    public int ofAaLevel;
    public KeyBinding keyBindPickBlock;
    public KeyBinding keyBindSpectatorOutlines;
    private final Set setModelParts;
    private static final String __OBFID = "CL_00000650";
    public boolean ofShowFps;
    private static final String[] STREAM_MIC_MODES;
    public float streamKbps;
    public float streamBytesPerPixel;
    public KeyBinding keyBindStreamPauseUnpause;
    public int clouds;
    public float ofCloudsHeight;
    public boolean reducedDebugInfo;
    public float chatHeightUnfocused;
    private static final String[] STREAM_CHAT_FILTER_MODES;
    public float streamMicVolume;
    public boolean invertMouse;
    public int overrideWidth;
    public KeyBinding keyBindChat;
    public boolean ofPortalParticles;
    public int overrideHeight;
    public static final int ANIM_ON = 0;
    public boolean ofOcclusionFancy;
    public boolean ofClearWater;
    public boolean debugCamEnable;
    public boolean ofCustomFonts;
    public float chatHeightFocused;
    public boolean ofShowCapes;
    public boolean ofSmoothWorld;
    public boolean heldItemTooltips;
    private File optionsFileOF;
    public boolean ofSmoothFps;
    public boolean viewBobbing;
    private static final String[] STREAM_CHAT_MODES;
    public boolean fullScreen;
    public boolean ofRainSplash;
    public int particleSetting;
    public static final String DEFAULT_STR = "Default";
    public int streamMicToggleBehavior;
    public int guiScale;
    private Map mapSoundLevels;
    public boolean fboEnable;
    public boolean chatColours;
    public float streamGameVolume;
    public static final int DEFAULT = 0;
    public List field_183018_l;
    public KeyBinding ofKeyBindZoom;
    
    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }
    
    public void setModelPartEnabled(final EnumPlayerModelParts enumPlayerModelParts, final boolean b) {
        if (b) {
            this.setModelParts.add(enumPlayerModelParts);
        }
        else {
            this.setModelParts.remove(enumPlayerModelParts);
        }
        this.sendSettingsToServer();
    }
    
    public boolean func_181148_f() {
        return this.field_181150_U;
    }
    
    public GameSettings() {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = 2;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.field_183018_l = Lists.newArrayList();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.reducedDebugInfo = false;
        this.pauseOnLostFocus = true;
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = "";
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.field_181150_U = true;
        this.field_181151_V = true;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = "";
        this.fovSetting = 70.0f;
        this.language = "en_US";
        this.forceUnicodeFont = false;
    }
    
    public GameSettings(final Minecraft mc, final File file) {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = 2;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.field_183018_l = Lists.newArrayList();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.reducedDebugInfo = false;
        this.pauseOnLostFocus = true;
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = "";
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.field_181150_U = true;
        this.field_181151_V = true;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = "";
        this.fovSetting = 70.0f;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        this.mc = mc;
        this.optionsFile = new File(file, "options.txt");
        this.optionsFileOF = new File(file, "optionsof.txt");
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("Zoom", 48, "key.categories.misc");
        this.keyBindZoom = new KeyBinding("ZoomX", 46, "key.categories.misc");
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
        Options.RENDER_DISTANCE.setValueMax(32.0f);
        this.renderDistanceChunks = 8;
        this.loadOptions();
        Config.initGameSettings(this);
    }
    
    public void resetSettings() {
        this.renderDistanceChunks = 8;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = 2;
        this.fovSetting = 70.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.forceUnicodeFont = false;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofShowFps = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        this.updateWaterOpacity();
        this.mc.refreshResources();
        this.saveOptions();
    }
    
    public void loadOptions() {
        if (!this.optionsFile.exists()) {
            return;
        }
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile));
        this.mapSoundLevels.clear();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] split = line.split(":");
            if (split[0].equals("mouseSensitivity")) {
                this.mouseSensitivity = this.parseFloat(split[1]);
            }
            if (split[0].equals("fov")) {
                this.fovSetting = this.parseFloat(split[1]) * 40.0f + 70.0f;
            }
            if (split[0].equals("gamma")) {
                this.gammaSetting = this.parseFloat(split[1]);
            }
            if (split[0].equals("saturation")) {
                this.saturation = this.parseFloat(split[1]);
            }
            if (split[0].equals("invertYMouse")) {
                this.invertMouse = split[1].equals("true");
            }
            if (split[0].equals("renderDistance")) {
                this.renderDistanceChunks = Integer.parseInt(split[1]);
            }
            if (split[0].equals("guiScale")) {
                this.guiScale = Integer.parseInt(split[1]);
            }
            if (split[0].equals("particles")) {
                this.particleSetting = Integer.parseInt(split[1]);
            }
            if (split[0].equals("bobView")) {
                this.viewBobbing = split[1].equals("true");
            }
            if (split[0].equals("anaglyph3d")) {
                this.anaglyph = split[1].equals("true");
            }
            if (split[0].equals("maxFps")) {
                this.limitFramerate = Integer.parseInt(split[1]);
                this.enableVsync = false;
                if (this.limitFramerate <= 0) {
                    this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                    this.enableVsync = true;
                }
                this.updateVSync();
            }
            if (split[0].equals("fboEnable")) {
                this.fboEnable = split[1].equals("true");
            }
            if (split[0].equals("difficulty")) {
                this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(split[1]));
            }
            if (split[0].equals("fancyGraphics")) {
                this.fancyGraphics = split[1].equals("true");
                this.updateRenderClouds();
            }
            if (split[0].equals("ao")) {
                if (split[1].equals("true")) {
                    this.ambientOcclusion = 2;
                }
                else if (split[1].equals("false")) {
                    this.ambientOcclusion = 0;
                }
                else {
                    this.ambientOcclusion = Integer.parseInt(split[1]);
                }
            }
            if (split[0].equals("renderClouds")) {
                if (split[1].equals("true")) {
                    this.clouds = 2;
                }
                else if (split[1].equals("false")) {
                    this.clouds = 0;
                }
                else if (split[1].equals("fast")) {
                    this.clouds = 1;
                }
            }
            if (split[0].equals("resourcePacks")) {
                this.resourcePacks = (List)GameSettings.gson.fromJson(line.substring(line.indexOf(58) + 1), (Type)GameSettings.typeListString);
                if (this.resourcePacks == null) {
                    this.resourcePacks = Lists.newArrayList();
                }
            }
            if (split[0].equals("incompatibleResourcePacks")) {
                this.field_183018_l = (List)GameSettings.gson.fromJson(line.substring(line.indexOf(58) + 1), (Type)GameSettings.typeListString);
                if (this.field_183018_l == null) {
                    this.field_183018_l = Lists.newArrayList();
                }
            }
            if (split[0].equals("lastServer") && split.length >= 2) {
                this.lastServer = line.substring(line.indexOf(58) + 1);
            }
            if (split[0].equals("lang") && split.length >= 2) {
                this.language = split[1];
            }
            if (split[0].equals("chatVisibility")) {
                this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(split[1]));
            }
            if (split[0].equals("chatColors")) {
                this.chatColours = split[1].equals("true");
            }
            if (split[0].equals("chatLinks")) {
                this.chatLinks = split[1].equals("true");
            }
            if (split[0].equals("chatLinksPrompt")) {
                this.chatLinksPrompt = split[1].equals("true");
            }
            if (split[0].equals("chatOpacity")) {
                this.chatOpacity = this.parseFloat(split[1]);
            }
            if (split[0].equals("snooperEnabled")) {
                this.snooperEnabled = split[1].equals("true");
            }
            if (split[0].equals("fullscreen")) {
                this.fullScreen = split[1].equals("true");
            }
            if (split[0].equals("enableVsync")) {
                this.enableVsync = split[1].equals("true");
                this.updateVSync();
            }
            if (split[0].equals("useVbo")) {
                this.useVbo = split[1].equals("true");
            }
            if (split[0].equals("hideServerAddress")) {
                this.hideServerAddress = split[1].equals("true");
            }
            if (split[0].equals("advancedItemTooltips")) {
                this.advancedItemTooltips = split[1].equals("true");
            }
            if (split[0].equals("pauseOnLostFocus")) {
                this.pauseOnLostFocus = split[1].equals("true");
            }
            if (split[0].equals("touchscreen")) {
                this.touchscreen = split[1].equals("true");
            }
            if (split[0].equals("overrideHeight")) {
                this.overrideHeight = Integer.parseInt(split[1]);
            }
            if (split[0].equals("overrideWidth")) {
                this.overrideWidth = Integer.parseInt(split[1]);
            }
            if (split[0].equals("heldItemTooltips")) {
                this.heldItemTooltips = split[1].equals("true");
            }
            if (split[0].equals("chatHeightFocused")) {
                this.chatHeightFocused = this.parseFloat(split[1]);
            }
            if (split[0].equals("chatHeightUnfocused")) {
                this.chatHeightUnfocused = this.parseFloat(split[1]);
            }
            if (split[0].equals("chatScale")) {
                this.chatScale = this.parseFloat(split[1]);
            }
            if (split[0].equals("chatWidth")) {
                this.chatWidth = this.parseFloat(split[1]);
            }
            if (split[0].equals("showInventoryAchievementHint")) {
                this.showInventoryAchievementHint = split[1].equals("true");
            }
            if (split[0].equals("mipmapLevels")) {
                this.mipmapLevels = Integer.parseInt(split[1]);
            }
            if (split[0].equals("streamBytesPerPixel")) {
                this.streamBytesPerPixel = this.parseFloat(split[1]);
            }
            if (split[0].equals("streamMicVolume")) {
                this.streamMicVolume = this.parseFloat(split[1]);
            }
            if (split[0].equals("streamSystemVolume")) {
                this.streamGameVolume = this.parseFloat(split[1]);
            }
            if (split[0].equals("streamKbps")) {
                this.streamKbps = this.parseFloat(split[1]);
            }
            if (split[0].equals("streamFps")) {
                this.streamFps = this.parseFloat(split[1]);
            }
            if (split[0].equals("streamCompression")) {
                this.streamCompression = Integer.parseInt(split[1]);
            }
            if (split[0].equals("streamSendMetadata")) {
                this.streamSendMetadata = split[1].equals("true");
            }
            if (split[0].equals("streamPreferredServer") && split.length >= 2) {
                this.streamPreferredServer = line.substring(line.indexOf(58) + 1);
            }
            if (split[0].equals("streamChatEnabled")) {
                this.streamChatEnabled = Integer.parseInt(split[1]);
            }
            if (split[0].equals("streamChatUserFilter")) {
                this.streamChatUserFilter = Integer.parseInt(split[1]);
            }
            if (split[0].equals("streamMicToggleBehavior")) {
                this.streamMicToggleBehavior = Integer.parseInt(split[1]);
            }
            if (split[0].equals("forceUnicodeFont")) {
                this.forceUnicodeFont = split[1].equals("true");
            }
            if (split[0].equals("allowBlockAlternatives")) {
                this.allowBlockAlternatives = split[1].equals("true");
            }
            if (split[0].equals("reducedDebugInfo")) {
                this.reducedDebugInfo = split[1].equals("true");
            }
            if (split[0].equals("useNativeTransport")) {
                this.field_181150_U = split[1].equals("true");
            }
            if (split[0].equals("entityShadows")) {
                this.field_181151_V = split[1].equals("true");
            }
            final KeyBinding[] keyBindings = this.keyBindings;
            int n = 0;
            while (0 < keyBindings.length) {
                final KeyBinding keyBinding = keyBindings[0];
                if (split[0].equals("key_" + keyBinding.getKeyDescription())) {
                    keyBinding.setKeyCode(Integer.parseInt(split[1]));
                }
                ++n;
            }
            final SoundCategory[] values = SoundCategory.values();
            while (0 < values.length) {
                final SoundCategory soundCategory = values[0];
                if (split[0].equals("soundCategory_" + soundCategory.getCategoryName())) {
                    this.mapSoundLevels.put(soundCategory, this.parseFloat(split[1]));
                }
                ++n;
            }
            final EnumPlayerModelParts[] values2 = EnumPlayerModelParts.values();
            while (0 < values2.length) {
                final EnumPlayerModelParts enumPlayerModelParts = values2[0];
                if (split[0].equals("modelPart_" + enumPlayerModelParts.getPartName())) {
                    this.setModelPartEnabled(enumPlayerModelParts, split[1].equals("true"));
                }
                ++n;
            }
        }
        bufferedReader.close();
        this.loadOfOptions();
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        this.mc.getSoundHandler().setSoundLevel(soundCategory, n);
        this.mapSoundLevels.put(soundCategory, n);
    }
    
    public float getSoundLevel(final SoundCategory soundCategory) {
        return this.mapSoundLevels.containsKey(soundCategory) ? this.mapSoundLevels.get(soundCategory) : 1.0f;
    }
    
    public void setAllAnimations(final boolean ofAnimatedTextures) {
        final int n = ofAnimatedTextures ? 0 : 2;
        this.ofAnimatedWater = n;
        this.ofAnimatedLava = n;
        this.ofAnimatedFire = ofAnimatedTextures;
        this.ofAnimatedPortal = ofAnimatedTextures;
        this.ofAnimatedRedstone = ofAnimatedTextures;
        this.ofAnimatedExplosion = ofAnimatedTextures;
        this.ofAnimatedFlame = ofAnimatedTextures;
        this.ofAnimatedSmoke = ofAnimatedTextures;
        this.ofVoidParticles = ofAnimatedTextures;
        this.ofWaterParticles = ofAnimatedTextures;
        this.ofRainSplash = ofAnimatedTextures;
        this.ofPortalParticles = ofAnimatedTextures;
        this.ofPotionParticles = ofAnimatedTextures;
        this.ofFireworkParticles = ofAnimatedTextures;
        this.particleSetting = (ofAnimatedTextures ? 0 : 2);
        this.ofDrippingWaterLava = ofAnimatedTextures;
        this.ofAnimatedTerrain = ofAnimatedTextures;
        this.ofAnimatedTextures = ofAnimatedTextures;
    }
    
    public void setOptionKeyBinding(final KeyBinding keyBinding, final int keyCode) {
        keyBinding.setKeyCode(keyCode);
        this.saveOptions();
    }
    
    public boolean getOptionOrdinalValue(final Options options) {
        switch (GameSettings$2.field_151477_a[options.ordinal()]) {
            case 1: {
                return this.invertMouse;
            }
            case 2: {
                return this.viewBobbing;
            }
            case 3: {
                return this.anaglyph;
            }
            case 4: {
                return this.fboEnable;
            }
            case 5: {
                return this.chatColours;
            }
            case 6: {
                return this.chatLinks;
            }
            case 7: {
                return this.chatLinksPrompt;
            }
            case 8: {
                return this.snooperEnabled;
            }
            case 9: {
                return this.fullScreen;
            }
            case 10: {
                return this.enableVsync;
            }
            case 11: {
                return this.useVbo;
            }
            case 12: {
                return this.touchscreen;
            }
            case 13: {
                return this.streamSendMetadata;
            }
            case 14: {
                return this.forceUnicodeFont;
            }
            case 15: {
                return this.allowBlockAlternatives;
            }
            case 16: {
                return this.reducedDebugInfo;
            }
            case 17: {
                return this.field_181151_V;
            }
            default: {
                return false;
            }
        }
    }
    
    private void updateRenderClouds() {
        switch (this.ofClouds) {
            case 1: {
                this.clouds = 1;
                break;
            }
            case 2: {
                this.clouds = 2;
                break;
            }
            case 3: {
                this.clouds = 0;
                break;
            }
            default: {
                if (this.fancyGraphics) {
                    this.clouds = 2;
                    break;
                }
                this.clouds = 1;
                break;
            }
        }
    }
    
    public static boolean isKeyDown(final KeyBinding keyBinding) {
        final int keyCode = keyBinding.getKeyCode();
        return keyCode >= -100 && keyCode <= 255 && keyBinding.getKeyCode() != 0 && ((keyBinding.getKeyCode() < 0) ? Mouse.isButtonDown(keyBinding.getKeyCode() + 100) : Keyboard.isKeyDown(keyBinding.getKeyCode()));
    }
    
    public float getOptionFloatValue(final Options options) {
        return (options == Options.CLOUD_HEIGHT) ? this.ofCloudsHeight : ((options == Options.AO_LEVEL) ? this.ofAoLevel : ((options == Options.AA_LEVEL) ? ((float)this.ofAaLevel) : ((options == Options.AF_LEVEL) ? ((float)this.ofAfLevel) : ((options == Options.MIPMAP_TYPE) ? ((float)this.ofMipmapType) : ((options == Options.FRAMERATE_LIMIT) ? ((this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0f : ((float)this.limitFramerate)) : ((options == Options.FOV) ? this.fovSetting : ((options == Options.GAMMA) ? this.gammaSetting : ((options == Options.SATURATION) ? this.saturation : ((options == Options.SENSITIVITY) ? this.mouseSensitivity : ((options == Options.CHAT_OPACITY) ? this.chatOpacity : ((options == Options.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((options == Options.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((options == Options.CHAT_SCALE) ? this.chatScale : ((options == Options.CHAT_WIDTH) ? this.chatWidth : ((options == Options.FRAMERATE_LIMIT) ? ((float)this.limitFramerate) : ((options == Options.MIPMAP_LEVELS) ? ((float)this.mipmapLevels) : ((options == Options.RENDER_DISTANCE) ? ((float)this.renderDistanceChunks) : ((options == Options.STREAM_BYTES_PER_PIXEL) ? this.streamBytesPerPixel : ((options == Options.STREAM_VOLUME_MIC) ? this.streamMicVolume : ((options == Options.STREAM_VOLUME_SYSTEM) ? this.streamGameVolume : ((options == Options.STREAM_KBPS) ? this.streamKbps : ((options == Options.STREAM_FPS) ? this.streamFps : 0.0f))))))))))))))))))))));
    }
    
    private String getKeyBindingOF(final Options options) {
        String s = I18n.format(options.getEnumString(), new Object[0]) + ": ";
        if (s == null) {
            s = options.getEnumString();
        }
        if (options == Options.RENDER_DISTANCE) {
            final int n = (int)this.getOptionFloatValue(options);
            String s2 = "Tiny";
            if (n >= 4) {
                s2 = "Short";
            }
            if (n >= 8) {
                s2 = "Normal";
            }
            if (n >= 16) {
                s2 = "Far";
            }
            if (n >= 32) {
                s2 = "Extreme";
            }
            final int n2 = this.renderDistanceChunks - 32;
            String string = s2;
            if (n2 > 0) {
                string = s2 + "+";
            }
            return s + n + " " + string + "";
        }
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return s + "Fast";
                }
                case 2: {
                    return s + "Fancy";
                }
                case 3: {
                    return s + "OFF";
                }
                default: {
                    return s + "OFF";
                }
            }
        }
        else {
            if (options == Options.FOG_START) {
                return s + this.ofFogStart;
            }
            if (options == Options.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0: {
                        return s + "Nearest";
                    }
                    case 1: {
                        return s + "Linear";
                    }
                    case 2: {
                        return s + "Bilinear";
                    }
                    case 3: {
                        return s + "Trilinear";
                    }
                    default: {
                        return s + "Nearest";
                    }
                }
            }
            else {
                if (options == Options.SMOOTH_FPS) {
                    return this.ofSmoothFps ? (s + "ON") : (s + "OFF");
                }
                if (options == Options.SMOOTH_WORLD) {
                    return this.ofSmoothWorld ? (s + "ON") : (s + "OFF");
                }
                if (options == Options.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1: {
                            return s + "Fast";
                        }
                        case 2: {
                            return s + "Fancy";
                        }
                        case 3: {
                            return s + "OFF";
                        }
                        default: {
                            return s + "Default";
                        }
                    }
                }
                else if (options == Options.TREES) {
                    switch (this.ofTrees) {
                        case 1: {
                            return s + "Fast";
                        }
                        case 2: {
                            return s + "Fancy";
                        }
                        default: {
                            return s + "Default";
                        }
                    }
                }
                else if (options == Options.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1: {
                            return s + "Fast";
                        }
                        case 2: {
                            return s + "Fancy";
                        }
                        default: {
                            return s + "Default";
                        }
                    }
                }
                else if (options == Options.RAIN) {
                    switch (this.ofRain) {
                        case 1: {
                            return s + "Fast";
                        }
                        case 2: {
                            return s + "Fancy";
                        }
                        case 3: {
                            return s + "OFF";
                        }
                        default: {
                            return s + "Default";
                        }
                    }
                }
                else if (options == Options.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1: {
                            return s + "Dynamic";
                        }
                        case 2: {
                            return s + "OFF";
                        }
                        default: {
                            return s + "ON";
                        }
                    }
                }
                else if (options == Options.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1: {
                            return s + "Dynamic";
                        }
                        case 2: {
                            return s + "OFF";
                        }
                        default: {
                            return s + "ON";
                        }
                    }
                }
                else {
                    if (options == Options.ANIMATED_FIRE) {
                        return this.ofAnimatedFire ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_PORTAL) {
                        return this.ofAnimatedPortal ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_REDSTONE) {
                        return this.ofAnimatedRedstone ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_EXPLOSION) {
                        return this.ofAnimatedExplosion ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_FLAME) {
                        return this.ofAnimatedFlame ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_SMOKE) {
                        return this.ofAnimatedSmoke ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.VOID_PARTICLES) {
                        return this.ofVoidParticles ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.WATER_PARTICLES) {
                        return this.ofWaterParticles ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.PORTAL_PARTICLES) {
                        return this.ofPortalParticles ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.POTION_PARTICLES) {
                        return this.ofPotionParticles ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.FIREWORK_PARTICLES) {
                        return this.ofFireworkParticles ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.DRIPPING_WATER_LAVA) {
                        return this.ofDrippingWaterLava ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_TERRAIN) {
                        return this.ofAnimatedTerrain ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.ANIMATED_TEXTURES) {
                        return this.ofAnimatedTextures ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.RAIN_SPLASH) {
                        return this.ofRainSplash ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.LAGOMETER) {
                        return this.ofLagometer ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.SHOW_FPS) {
                        return this.ofShowFps ? (s + "ON") : (s + "OFF");
                    }
                    if (options == Options.AUTOSAVE_TICKS) {
                        return (this.ofAutoSaveTicks <= 40) ? (s + "Default (2s)") : ((this.ofAutoSaveTicks <= 400) ? (s + "20s") : ((this.ofAutoSaveTicks <= 4000) ? (s + "3min") : (s + "30min")));
                    }
                    if (options == Options.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1: {
                                return s + "Fast";
                            }
                            case 2: {
                                return s + "Fancy";
                            }
                            default: {
                                return s + "OFF";
                            }
                        }
                    }
                    else if (options == Options.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1: {
                                return s + "Fast";
                            }
                            case 2: {
                                return s + "Fancy";
                            }
                            default: {
                                return s + "OFF";
                            }
                        }
                    }
                    else {
                        if (options == Options.WEATHER) {
                            return this.ofWeather ? (s + "ON") : (s + "OFF");
                        }
                        if (options == Options.SKY) {
                            return this.ofSky ? (s + "ON") : (s + "OFF");
                        }
                        if (options == Options.STARS) {
                            return this.ofStars ? (s + "ON") : (s + "OFF");
                        }
                        if (options == Options.SUN_MOON) {
                            return this.ofSunMoon ? (s + "ON") : (s + "OFF");
                        }
                        if (options == Options.VIGNETTE) {
                            switch (this.ofVignette) {
                                case 1: {
                                    return s + "Fast";
                                }
                                case 2: {
                                    return s + "Fancy";
                                }
                                default: {
                                    return s + "Default";
                                }
                            }
                        }
                        else {
                            if (options == Options.CHUNK_UPDATES) {
                                return s + this.ofChunkUpdates;
                            }
                            if (options == Options.CHUNK_LOADING) {
                                return (this.ofChunkLoading == 1) ? (s + "Smooth") : ((this.ofChunkLoading == 2) ? (s + "Multi-Core") : (s + "Default"));
                            }
                            if (options == Options.CHUNK_UPDATES_DYNAMIC) {
                                return this.ofChunkUpdatesDynamic ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.TIME) {
                                return (this.ofTime == 1) ? (s + "Day Only") : ((this.ofTime == 3) ? (s + "Night Only") : (s + "Default"));
                            }
                            if (options == Options.CLEAR_WATER) {
                                return this.ofClearWater ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.AA_LEVEL) {
                                String s3 = "";
                                if (this.ofAaLevel != Config.getAntialiasingLevel()) {
                                    s3 = " (restart)";
                                }
                                return (this.ofAaLevel == 0) ? (s + "OFF" + s3) : (s + this.ofAaLevel + s3);
                            }
                            if (options == Options.AF_LEVEL) {
                                return (this.ofAfLevel == 1) ? (s + "OFF") : (s + this.ofAfLevel);
                            }
                            if (options == Options.PROFILER) {
                                return this.ofProfiler ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.BETTER_SNOW) {
                                return this.ofBetterSnow ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.SWAMP_COLORS) {
                                return this.ofSwampColors ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.RANDOM_MOBS) {
                                return this.ofRandomMobs ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.SMOOTH_BIOMES) {
                                return this.ofSmoothBiomes ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.CUSTOM_FONTS) {
                                return this.ofCustomFonts ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.CUSTOM_COLORS) {
                                return this.ofCustomColors ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.CUSTOM_SKY) {
                                return this.ofCustomSky ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.SHOW_CAPES) {
                                return this.ofShowCapes ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.NATURAL_TEXTURES) {
                                return this.ofNaturalTextures ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.FAST_MATH) {
                                return this.ofFastMath ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.FAST_RENDER) {
                                return this.ofFastRender ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.TRANSLUCENT_BLOCKS) {
                                return (this.ofTranslucentBlocks == 1) ? (s + "Fast") : ((this.ofTranslucentBlocks == 2) ? (s + "Fancy") : (s + "Default"));
                            }
                            if (options == Options.LAZY_CHUNK_LOADING) {
                                return this.ofLazyChunkLoading ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.FULLSCREEN_MODE) {
                                return s + this.ofFullscreenMode;
                            }
                            if (options == Options.HELD_ITEM_TOOLTIPS) {
                                return this.heldItemTooltips ? (s + "ON") : (s + "OFF");
                            }
                            if (options == Options.FRAMERATE_LIMIT) {
                                final float optionFloatValue = this.getOptionFloatValue(options);
                                return (optionFloatValue == 0.0f) ? (s + "VSync") : ((optionFloatValue == Options.access$000(options)) ? (s + I18n.format("options.framerateLimit.max", new Object[0])) : (s + (int)optionFloatValue + " fps"));
                            }
                            return null;
                        }
                    }
                }
            }
        }
    }
    
    public static String getKeyDisplayString(final int n) {
        return (n < 0) ? I18n.format("key.mouseButton", n + 101) : ((n < 256) ? Keyboard.getKeyName(n) : String.format("%c", (char)(n - 256)).toUpperCase());
    }
    
    public Set getModelParts() {
        return (Set)ImmutableSet.copyOf((Collection)this.setModelParts);
    }
    
    private float parseFloat(final String s) {
        return s.equals("true") ? 1.0f : (s.equals("false") ? 0.0f : Float.parseFloat(s));
    }
    
    public int func_181147_e() {
        return (this.renderDistanceChunks >= 4) ? this.clouds : 0;
    }
    
    public void saveOptions() {
        if (Reflector.FMLClientHandler.exists()) {
            final Object call = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            if (call != null && Reflector.callBoolean(call, Reflector.FMLClientHandler_isLoading, new Object[0])) {
                return;
            }
        }
        final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFile));
        printWriter.println("invertYMouse:" + this.invertMouse);
        printWriter.println("mouseSensitivity:" + this.mouseSensitivity);
        printWriter.println("fov:" + (this.fovSetting - 70.0f) / 40.0f);
        printWriter.println("gamma:" + this.gammaSetting);
        printWriter.println("saturation:" + this.saturation);
        printWriter.println("renderDistance:" + this.renderDistanceChunks);
        printWriter.println("guiScale:" + this.guiScale);
        printWriter.println("particles:" + this.particleSetting);
        printWriter.println("bobView:" + this.viewBobbing);
        printWriter.println("anaglyph3d:" + this.anaglyph);
        printWriter.println("maxFps:" + this.limitFramerate);
        printWriter.println("fboEnable:" + this.fboEnable);
        printWriter.println("difficulty:" + this.difficulty.getDifficultyId());
        printWriter.println("fancyGraphics:" + this.fancyGraphics);
        printWriter.println("ao:" + this.ambientOcclusion);
        switch (this.clouds) {
            case 0: {
                printWriter.println("renderClouds:false");
                break;
            }
            case 1: {
                printWriter.println("renderClouds:fast");
                break;
            }
            case 2: {
                printWriter.println("renderClouds:true");
                break;
            }
        }
        printWriter.println("resourcePacks:" + GameSettings.gson.toJson((Object)this.resourcePacks));
        printWriter.println("incompatibleResourcePacks:" + GameSettings.gson.toJson((Object)this.field_183018_l));
        printWriter.println("lastServer:" + this.lastServer);
        printWriter.println("lang:" + this.language);
        printWriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
        printWriter.println("chatColors:" + this.chatColours);
        printWriter.println("chatLinks:" + this.chatLinks);
        printWriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
        printWriter.println("chatOpacity:" + this.chatOpacity);
        printWriter.println("snooperEnabled:" + this.snooperEnabled);
        printWriter.println("fullscreen:" + this.fullScreen);
        printWriter.println("enableVsync:" + this.enableVsync);
        printWriter.println("useVbo:" + this.useVbo);
        printWriter.println("hideServerAddress:" + this.hideServerAddress);
        printWriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
        printWriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
        printWriter.println("touchscreen:" + this.touchscreen);
        printWriter.println("overrideWidth:" + this.overrideWidth);
        printWriter.println("overrideHeight:" + this.overrideHeight);
        printWriter.println("heldItemTooltips:" + this.heldItemTooltips);
        printWriter.println("chatHeightFocused:" + this.chatHeightFocused);
        printWriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
        printWriter.println("chatScale:" + this.chatScale);
        printWriter.println("chatWidth:" + this.chatWidth);
        printWriter.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
        printWriter.println("mipmapLevels:" + this.mipmapLevels);
        printWriter.println("streamBytesPerPixel:" + this.streamBytesPerPixel);
        printWriter.println("streamMicVolume:" + this.streamMicVolume);
        printWriter.println("streamSystemVolume:" + this.streamGameVolume);
        printWriter.println("streamKbps:" + this.streamKbps);
        printWriter.println("streamFps:" + this.streamFps);
        printWriter.println("streamCompression:" + this.streamCompression);
        printWriter.println("streamSendMetadata:" + this.streamSendMetadata);
        printWriter.println("streamPreferredServer:" + this.streamPreferredServer);
        printWriter.println("streamChatEnabled:" + this.streamChatEnabled);
        printWriter.println("streamChatUserFilter:" + this.streamChatUserFilter);
        printWriter.println("streamMicToggleBehavior:" + this.streamMicToggleBehavior);
        printWriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
        printWriter.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
        printWriter.println("reducedDebugInfo:" + this.reducedDebugInfo);
        printWriter.println("useNativeTransport:" + this.field_181150_U);
        printWriter.println("entityShadows:" + this.field_181151_V);
        final KeyBinding[] keyBindings = this.keyBindings;
        int n = 0;
        while (0 < keyBindings.length) {
            final KeyBinding keyBinding = keyBindings[0];
            printWriter.println("key_" + keyBinding.getKeyDescription() + ":" + keyBinding.getKeyCode());
            ++n;
        }
        final SoundCategory[] values = SoundCategory.values();
        while (0 < values.length) {
            final SoundCategory soundCategory = values[0];
            printWriter.println("soundCategory_" + soundCategory.getCategoryName() + ":" + this.getSoundLevel(soundCategory));
            ++n;
        }
        final EnumPlayerModelParts[] values2 = EnumPlayerModelParts.values();
        while (0 < values2.length) {
            final EnumPlayerModelParts enumPlayerModelParts = values2[0];
            printWriter.println("modelPart_" + enumPlayerModelParts.getPartName() + ":" + this.setModelParts.contains(enumPlayerModelParts));
            ++n;
        }
        printWriter.close();
        this.saveOfOptions();
        this.sendSettingsToServer();
    }
    
    private void updateWaterOpacity() {
        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            Config.waterOpacityChanged = true;
        }
        ClearWater.updateWaterOpacity(this, this.mc.theWorld);
    }
    
    public void saveOfOptions() {
        final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFileOF));
        printWriter.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
        printWriter.println("ofFogType:" + this.ofFogType);
        printWriter.println("ofFogStart:" + this.ofFogStart);
        printWriter.println("ofMipmapType:" + this.ofMipmapType);
        printWriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
        printWriter.println("ofSmoothFps:" + this.ofSmoothFps);
        printWriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
        printWriter.println("ofAoLevel:" + this.ofAoLevel);
        printWriter.println("ofClouds:" + this.ofClouds);
        printWriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
        printWriter.println("ofTrees:" + this.ofTrees);
        printWriter.println("ofDroppedItems:" + this.ofDroppedItems);
        printWriter.println("ofRain:" + this.ofRain);
        printWriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
        printWriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
        printWriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
        printWriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
        printWriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
        printWriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
        printWriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
        printWriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
        printWriter.println("ofVoidParticles:" + this.ofVoidParticles);
        printWriter.println("ofWaterParticles:" + this.ofWaterParticles);
        printWriter.println("ofPortalParticles:" + this.ofPortalParticles);
        printWriter.println("ofPotionParticles:" + this.ofPotionParticles);
        printWriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
        printWriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
        printWriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
        printWriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
        printWriter.println("ofRainSplash:" + this.ofRainSplash);
        printWriter.println("ofLagometer:" + this.ofLagometer);
        printWriter.println("ofShowFps:" + this.ofShowFps);
        printWriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
        printWriter.println("ofBetterGrass:" + this.ofBetterGrass);
        printWriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
        printWriter.println("ofWeather:" + this.ofWeather);
        printWriter.println("ofSky:" + this.ofSky);
        printWriter.println("ofStars:" + this.ofStars);
        printWriter.println("ofSunMoon:" + this.ofSunMoon);
        printWriter.println("ofVignette:" + this.ofVignette);
        printWriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
        printWriter.println("ofChunkLoading:" + this.ofChunkLoading);
        printWriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
        printWriter.println("ofTime:" + this.ofTime);
        printWriter.println("ofClearWater:" + this.ofClearWater);
        printWriter.println("ofAaLevel:" + this.ofAaLevel);
        printWriter.println("ofAfLevel:" + this.ofAfLevel);
        printWriter.println("ofProfiler:" + this.ofProfiler);
        printWriter.println("ofBetterSnow:" + this.ofBetterSnow);
        printWriter.println("ofSwampColors:" + this.ofSwampColors);
        printWriter.println("ofRandomMobs:" + this.ofRandomMobs);
        printWriter.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
        printWriter.println("ofCustomFonts:" + this.ofCustomFonts);
        printWriter.println("ofCustomColors:" + this.ofCustomColors);
        printWriter.println("ofCustomSky:" + this.ofCustomSky);
        printWriter.println("ofShowCapes:" + this.ofShowCapes);
        printWriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
        printWriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
        printWriter.println("ofFullscreenMode:" + this.ofFullscreenMode);
        printWriter.println("ofFastMath:" + this.ofFastMath);
        printWriter.println("ofFastRender:" + this.ofFastRender);
        printWriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
        printWriter.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
        printWriter.close();
    }
    
    static {
        logger = LogManager.getLogger();
        gson = new Gson();
        typeListString = new ParameterizedType() {
            private static final String __OBFID = "CL_00000651";
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class };
            }
        };
        GameSettings.GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
        PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
        AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
        STREAM_COMPRESSIONS = new String[] { "options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high" };
        STREAM_CHAT_MODES = new String[] { "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never" };
        STREAM_CHAT_FILTER_MODES = new String[] { "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods" };
        STREAM_MIC_MODES = new String[] { "options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk" };
        field_181149_aW = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
    }
    
    private void setOptionValueOF(final Options options, final int n) {
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (!Config.isFancyFogAvailable()) {
                        this.ofFogType = 3;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.ofFogType = 3;
                    break;
                }
                case 3: {
                    this.ofFogType = 1;
                    break;
                }
                default: {
                    this.ofFogType = 1;
                    break;
                }
            }
        }
        if (options == Options.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (options == Options.SMOOTH_FPS) {
            this.ofSmoothFps = !this.ofSmoothFps;
        }
        if (options == Options.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
        }
        if (options == Options.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
            this.updateRenderClouds();
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.TREES) {
            ++this.ofTrees;
            if (this.ofTrees > 2) {
                this.ofTrees = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (options == Options.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (options == Options.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (options == Options.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (options == Options.ANIMATED_FIRE) {
            this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (options == Options.ANIMATED_PORTAL) {
            this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (options == Options.ANIMATED_REDSTONE) {
            this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (options == Options.ANIMATED_EXPLOSION) {
            this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (options == Options.ANIMATED_FLAME) {
            this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (options == Options.ANIMATED_SMOKE) {
            this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (options == Options.VOID_PARTICLES) {
            this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (options == Options.WATER_PARTICLES) {
            this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (options == Options.PORTAL_PARTICLES) {
            this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (options == Options.POTION_PARTICLES) {
            this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (options == Options.FIREWORK_PARTICLES) {
            this.ofFireworkParticles = !this.ofFireworkParticles;
        }
        if (options == Options.DRIPPING_WATER_LAVA) {
            this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (options == Options.ANIMATED_TERRAIN) {
            this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (options == Options.ANIMATED_TEXTURES) {
            this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (options == Options.RAIN_SPLASH) {
            this.ofRainSplash = !this.ofRainSplash;
        }
        if (options == Options.LAGOMETER) {
            this.ofLagometer = !this.ofLagometer;
        }
        if (options == Options.SHOW_FPS) {
            this.ofShowFps = !this.ofShowFps;
        }
        if (options == Options.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }
        if (options == Options.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            if (this.ofConnectedTextures != 2) {
                this.mc.refreshResources();
            }
        }
        if (options == Options.WEATHER) {
            this.ofWeather = !this.ofWeather;
        }
        if (options == Options.SKY) {
            this.ofSky = !this.ofSky;
        }
        if (options == Options.STARS) {
            this.ofStars = !this.ofStars;
        }
        if (options == Options.SUN_MOON) {
            this.ofSunMoon = !this.ofSunMoon;
        }
        if (options == Options.VIGNETTE) {
            ++this.ofVignette;
            if (this.ofVignette > 2) {
                this.ofVignette = 0;
            }
        }
        if (options == Options.CHUNK_UPDATES) {
            ++this.ofChunkUpdates;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }
        if (options == Options.CHUNK_LOADING) {
            ++this.ofChunkLoading;
            if (this.ofChunkLoading > 2) {
                this.ofChunkLoading = 0;
            }
            this.updateChunkLoading();
        }
        if (options == Options.CHUNK_UPDATES_DYNAMIC) {
            this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }
        if (options == Options.TIME) {
            ++this.ofTime;
            if (this.ofTime > 3) {
                this.ofTime = 0;
            }
        }
        if (options == Options.CLEAR_WATER) {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }
        if (options == Options.PROFILER) {
            this.ofProfiler = !this.ofProfiler;
        }
        if (options == Options.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RANDOM_MOBS) {
            this.ofRandomMobs = !this.ofRandomMobs;
        }
        if (options == Options.SMOOTH_BIOMES) {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
        if (options == Options.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
        }
        if (options == Options.SHOW_CAPES) {
            this.ofShowCapes = !this.ofShowCapes;
        }
        if (options == Options.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FAST_MATH) {
            this.ofFastMath = !this.ofFastMath;
            MathHelper.fastMath = this.ofFastMath;
        }
        if (options == Options.FAST_RENDER) {
            this.ofFastRender = !this.ofFastRender;
        }
        if (options == Options.TRANSLUCENT_BLOCKS) {
            if (this.ofTranslucentBlocks == 0) {
                this.ofTranslucentBlocks = 1;
            }
            else if (this.ofTranslucentBlocks == 1) {
                this.ofTranslucentBlocks = 2;
            }
            else if (this.ofTranslucentBlocks == 2) {
                this.ofTranslucentBlocks = 0;
            }
            else {
                this.ofTranslucentBlocks = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            if (!Config.isSingleProcessor()) {
                this.ofLazyChunkLoading = false;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FULLSCREEN_MODE) {
            final List<String> list = Arrays.asList(Config.getFullscreenModes());
            if (this.ofFullscreenMode.equals("Default")) {
                this.ofFullscreenMode = list.get(0);
            }
            else {
                int index = list.indexOf(this.ofFullscreenMode);
                if (index < 0) {
                    this.ofFullscreenMode = "Default";
                }
                else if (++index >= list.size()) {
                    this.ofFullscreenMode = "Default";
                }
                else {
                    this.ofFullscreenMode = list.get(index);
                }
            }
        }
        if (options == Options.HELD_ITEM_TOOLTIPS) {
            this.heldItemTooltips = !this.heldItemTooltips;
        }
    }
    
    public void loadOfOptions() {
        File file = this.optionsFileOF;
        if (!file.exists()) {
            file = this.optionsFile;
        }
        if (!file.exists()) {
            return;
        }
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] split = line.split(":");
            if (split[0].equals("ofRenderDistanceChunks") && split.length >= 2) {
                this.renderDistanceChunks = Integer.valueOf(split[1]);
                this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
            }
            if (split[0].equals("ofFogType") && split.length >= 2) {
                this.ofFogType = Integer.valueOf(split[1]);
                this.ofFogType = Config.limit(this.ofFogType, 1, 3);
            }
            if (split[0].equals("ofFogStart") && split.length >= 2) {
                this.ofFogStart = Float.valueOf(split[1]);
                if (this.ofFogStart < 0.2f) {
                    this.ofFogStart = 0.2f;
                }
                if (this.ofFogStart > 0.81f) {
                    this.ofFogStart = 0.8f;
                }
            }
            if (split[0].equals("ofMipmapType") && split.length >= 2) {
                this.ofMipmapType = Integer.valueOf(split[1]);
                this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
            }
            if (split[0].equals("ofOcclusionFancy") && split.length >= 2) {
                this.ofOcclusionFancy = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSmoothFps") && split.length >= 2) {
                this.ofSmoothFps = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSmoothWorld") && split.length >= 2) {
                this.ofSmoothWorld = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAoLevel") && split.length >= 2) {
                this.ofAoLevel = Float.valueOf(split[1]);
                this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
            }
            if (split[0].equals("ofClouds") && split.length >= 2) {
                this.ofClouds = Integer.valueOf(split[1]);
                this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                this.updateRenderClouds();
            }
            if (split[0].equals("ofCloudsHeight") && split.length >= 2) {
                this.ofCloudsHeight = Float.valueOf(split[1]);
                this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
            }
            if (split[0].equals("ofTrees") && split.length >= 2) {
                this.ofTrees = Integer.valueOf(split[1]);
                this.ofTrees = Config.limit(this.ofTrees, 0, 2);
            }
            if (split[0].equals("ofDroppedItems") && split.length >= 2) {
                this.ofDroppedItems = Integer.valueOf(split[1]);
                this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
            }
            if (split[0].equals("ofRain") && split.length >= 2) {
                this.ofRain = Integer.valueOf(split[1]);
                this.ofRain = Config.limit(this.ofRain, 0, 3);
            }
            if (split[0].equals("ofAnimatedWater") && split.length >= 2) {
                this.ofAnimatedWater = Integer.valueOf(split[1]);
                this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
            }
            if (split[0].equals("ofAnimatedLava") && split.length >= 2) {
                this.ofAnimatedLava = Integer.valueOf(split[1]);
                this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
            }
            if (split[0].equals("ofAnimatedFire") && split.length >= 2) {
                this.ofAnimatedFire = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedPortal") && split.length >= 2) {
                this.ofAnimatedPortal = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedRedstone") && split.length >= 2) {
                this.ofAnimatedRedstone = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedExplosion") && split.length >= 2) {
                this.ofAnimatedExplosion = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedFlame") && split.length >= 2) {
                this.ofAnimatedFlame = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedSmoke") && split.length >= 2) {
                this.ofAnimatedSmoke = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofVoidParticles") && split.length >= 2) {
                this.ofVoidParticles = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofWaterParticles") && split.length >= 2) {
                this.ofWaterParticles = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofPortalParticles") && split.length >= 2) {
                this.ofPortalParticles = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofPotionParticles") && split.length >= 2) {
                this.ofPotionParticles = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofFireworkParticles") && split.length >= 2) {
                this.ofFireworkParticles = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofDrippingWaterLava") && split.length >= 2) {
                this.ofDrippingWaterLava = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedTerrain") && split.length >= 2) {
                this.ofAnimatedTerrain = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAnimatedTextures") && split.length >= 2) {
                this.ofAnimatedTextures = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofRainSplash") && split.length >= 2) {
                this.ofRainSplash = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofLagometer") && split.length >= 2) {
                this.ofLagometer = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofShowFps") && split.length >= 2) {
                this.ofShowFps = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofAutoSaveTicks") && split.length >= 2) {
                this.ofAutoSaveTicks = Integer.valueOf(split[1]);
                this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
            }
            if (split[0].equals("ofBetterGrass") && split.length >= 2) {
                this.ofBetterGrass = Integer.valueOf(split[1]);
                this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
            }
            if (split[0].equals("ofConnectedTextures") && split.length >= 2) {
                this.ofConnectedTextures = Integer.valueOf(split[1]);
                this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
            }
            if (split[0].equals("ofWeather") && split.length >= 2) {
                this.ofWeather = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSky") && split.length >= 2) {
                this.ofSky = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofStars") && split.length >= 2) {
                this.ofStars = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSunMoon") && split.length >= 2) {
                this.ofSunMoon = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofVignette") && split.length >= 2) {
                this.ofVignette = Integer.valueOf(split[1]);
                this.ofVignette = Config.limit(this.ofVignette, 0, 2);
            }
            if (split[0].equals("ofChunkUpdates") && split.length >= 2) {
                this.ofChunkUpdates = Integer.valueOf(split[1]);
                this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
            }
            if (split[0].equals("ofChunkLoading") && split.length >= 2) {
                this.ofChunkLoading = Integer.valueOf(split[1]);
                this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                this.updateChunkLoading();
            }
            if (split[0].equals("ofChunkUpdatesDynamic") && split.length >= 2) {
                this.ofChunkUpdatesDynamic = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofTime") && split.length >= 2) {
                this.ofTime = Integer.valueOf(split[1]);
                this.ofTime = Config.limit(this.ofTime, 0, 3);
            }
            if (split[0].equals("ofClearWater") && split.length >= 2) {
                this.ofClearWater = Boolean.valueOf(split[1]);
                this.updateWaterOpacity();
            }
            if (split[0].equals("ofAaLevel") && split.length >= 2) {
                this.ofAaLevel = Integer.valueOf(split[1]);
                this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
            }
            if (split[0].equals("ofAfLevel") && split.length >= 2) {
                this.ofAfLevel = Integer.valueOf(split[1]);
                this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            }
            if (split[0].equals("ofProfiler") && split.length >= 2) {
                this.ofProfiler = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofBetterSnow") && split.length >= 2) {
                this.ofBetterSnow = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSwampColors") && split.length >= 2) {
                this.ofSwampColors = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofRandomMobs") && split.length >= 2) {
                this.ofRandomMobs = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofSmoothBiomes") && split.length >= 2) {
                this.ofSmoothBiomes = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofCustomFonts") && split.length >= 2) {
                this.ofCustomFonts = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofCustomColors") && split.length >= 2) {
                this.ofCustomColors = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofCustomSky") && split.length >= 2) {
                this.ofCustomSky = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofShowCapes") && split.length >= 2) {
                this.ofShowCapes = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofNaturalTextures") && split.length >= 2) {
                this.ofNaturalTextures = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofLazyChunkLoading") && split.length >= 2) {
                this.ofLazyChunkLoading = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofFullscreenMode") && split.length >= 2) {
                this.ofFullscreenMode = split[1];
            }
            if (split[0].equals("ofFastMath") && split.length >= 2) {
                this.ofFastMath = Boolean.valueOf(split[1]);
                MathHelper.fastMath = this.ofFastMath;
            }
            if (split[0].equals("ofFastRender") && split.length >= 2) {
                this.ofFastRender = Boolean.valueOf(split[1]);
            }
            if (split[0].equals("ofTranslucentBlocks") && split.length >= 2) {
                this.ofTranslucentBlocks = Integer.valueOf(split[1]);
                this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
            }
            if (split[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) {
                this.ofKeyBindZoom.setKeyCode(Integer.parseInt(split[1]));
            }
        }
        bufferedReader.close();
    }
    
    public void setOptionFloatValue(final Options options, final float streamFps) {
        this.setOptionFloatValueOF(options, streamFps);
        if (options == Options.SENSITIVITY) {
            this.mouseSensitivity = streamFps;
        }
        if (options == Options.FOV) {
            this.fovSetting = streamFps;
        }
        if (options == Options.GAMMA) {
            this.gammaSetting = streamFps;
        }
        if (options == Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)streamFps;
            this.enableVsync = false;
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = true;
            }
            this.updateVSync();
        }
        if (options == Options.CHAT_OPACITY) {
            this.chatOpacity = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_WIDTH) {
            this.chatWidth = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_SCALE) {
            this.chatScale = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.MIPMAP_LEVELS) {
            final int mipmapLevels = this.mipmapLevels;
            this.mipmapLevels = (int)streamFps;
            if (mipmapLevels != streamFps) {
                this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, this.mipmapLevels > 0);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            this.allowBlockAlternatives = !this.allowBlockAlternatives;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RENDER_DISTANCE) {
            this.renderDistanceChunks = (int)streamFps;
            this.mc.renderGlobal.setDisplayListEntitiesDirty();
        }
        if (options == Options.STREAM_BYTES_PER_PIXEL) {
            this.streamBytesPerPixel = streamFps;
        }
        if (options == Options.STREAM_VOLUME_MIC) {
            this.streamMicVolume = streamFps;
            this.mc.getTwitchStream().updateStreamVolume();
        }
        if (options == Options.STREAM_VOLUME_SYSTEM) {
            this.streamGameVolume = streamFps;
            this.mc.getTwitchStream().updateStreamVolume();
        }
        if (options == Options.STREAM_KBPS) {
            this.streamKbps = streamFps;
        }
        if (options == Options.STREAM_FPS) {
            this.streamFps = streamFps;
        }
    }
    
    private static String getTranslation(final String[] array, final int n) {
        if (0 >= array.length) {}
        return I18n.format(array[0], new Object[0]);
    }
    
    public void updateChunkLoading() {
        if (this.mc.renderGlobal != null) {
            this.mc.renderGlobal.loadRenderers();
        }
    }
    
    private void setOptionFloatValueOF(final Options options, final float n) {
        if (options == Options.CLOUD_HEIGHT) {
            this.ofCloudsHeight = n;
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.AO_LEVEL) {
            this.ofAoLevel = n;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AA_LEVEL) {
            final int[] array = { 0, 2, 4, 6, 8, 12, 16 };
            this.ofAaLevel = 0;
            final int n2 = (int)n;
            while (0 < array.length) {
                if (n2 >= array[0]) {
                    this.ofAaLevel = array[0];
                }
                int n3 = 0;
                ++n3;
            }
            this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
        }
        if (options == Options.AF_LEVEL) {
            final int n4 = (int)n;
            this.ofAfLevel = 1;
            while (this.ofAfLevel * 2 <= n4) {
                this.ofAfLevel *= 2;
            }
            this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            this.mc.refreshResources();
        }
        if (options == Options.MIPMAP_TYPE) {
            this.ofMipmapType = Config.limit((int)n, 0, 3);
            this.mc.refreshResources();
        }
    }
    
    public void switchModelPartEnabled(final EnumPlayerModelParts enumPlayerModelParts) {
        if (!this.getModelParts().contains(enumPlayerModelParts)) {
            this.setModelParts.add(enumPlayerModelParts);
        }
        else {
            this.setModelParts.remove(enumPlayerModelParts);
        }
        this.sendSettingsToServer();
    }
    
    public String getKeyBinding(final Options options) {
        final String keyBindingOF = this.getKeyBindingOF(options);
        if (keyBindingOF != null) {
            return keyBindingOF;
        }
        final String string = I18n.format(options.getEnumString(), new Object[0]) + ": ";
        if (options.getEnumFloat()) {
            final float optionFloatValue = this.getOptionFloatValue(options);
            final float normalizeValue = options.normalizeValue(optionFloatValue);
            return (options == Options.SENSITIVITY) ? ((normalizeValue == 0.0f) ? (string + I18n.format("options.sensitivity.min", new Object[0])) : ((normalizeValue == 1.0f) ? (string + I18n.format("options.sensitivity.max", new Object[0])) : (string + (int)(normalizeValue * 200.0f) + "%"))) : ((options == Options.FOV) ? ((optionFloatValue == 70.0f) ? (string + I18n.format("options.fov.min", new Object[0])) : ((optionFloatValue == 110.0f) ? (string + I18n.format("options.fov.max", new Object[0])) : (string + (int)optionFloatValue))) : ((options == Options.FRAMERATE_LIMIT) ? ((optionFloatValue == Options.access$000(options)) ? (string + I18n.format("options.framerateLimit.max", new Object[0])) : (string + (int)optionFloatValue + " fps")) : ((options == Options.RENDER_CLOUDS) ? ((optionFloatValue == Options.access$100(options)) ? (string + I18n.format("options.cloudHeight.min", new Object[0])) : (string + ((int)optionFloatValue + 128))) : ((options == Options.GAMMA) ? ((normalizeValue == 0.0f) ? (string + I18n.format("options.gamma.min", new Object[0])) : ((normalizeValue == 1.0f) ? (string + I18n.format("options.gamma.max", new Object[0])) : (string + "+" + (int)(normalizeValue * 100.0f) + "%"))) : ((options == Options.SATURATION) ? (string + (int)(normalizeValue * 400.0f) + "%") : ((options == Options.CHAT_OPACITY) ? (string + (int)(normalizeValue * 90.0f + 10.0f) + "%") : ((options == Options.CHAT_HEIGHT_UNFOCUSED) ? (string + GuiNewChat.calculateChatboxHeight(normalizeValue) + "px") : ((options == Options.CHAT_HEIGHT_FOCUSED) ? (string + GuiNewChat.calculateChatboxHeight(normalizeValue) + "px") : ((options == Options.CHAT_WIDTH) ? (string + GuiNewChat.calculateChatboxWidth(normalizeValue) + "px") : ((options == Options.RENDER_DISTANCE) ? (string + (int)optionFloatValue + " chunks") : ((options == Options.MIPMAP_LEVELS) ? ((optionFloatValue == 0.0f) ? (string + I18n.format("options.off", new Object[0])) : (string + (int)optionFloatValue)) : ((options == Options.STREAM_FPS) ? (string + TwitchStream.formatStreamFps(normalizeValue) + " fps") : ((options == Options.STREAM_KBPS) ? (string + TwitchStream.formatStreamKbps(normalizeValue) + " Kbps") : ((options == Options.STREAM_BYTES_PER_PIXEL) ? (string + String.format("%.3f bpp", TwitchStream.formatStreamBps(normalizeValue))) : ((normalizeValue == 0.0f) ? (string + I18n.format("options.off", new Object[0])) : (string + (int)(normalizeValue * 100.0f) + "%"))))))))))))))));
        }
        if (options.getEnumBoolean()) {
            return this.getOptionOrdinalValue(options) ? (string + I18n.format("options.on", new Object[0])) : (string + I18n.format("options.off", new Object[0]));
        }
        if (options == Options.GUI_SCALE) {
            return string + getTranslation(GameSettings.GUISCALES, this.guiScale);
        }
        if (options == Options.CHAT_VISIBILITY) {
            return string + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
        }
        if (options == Options.PARTICLES) {
            return string + getTranslation(GameSettings.PARTICLES, this.particleSetting);
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            return string + getTranslation(GameSettings.AMBIENT_OCCLUSIONS, this.ambientOcclusion);
        }
        if (options == Options.STREAM_COMPRESSION) {
            return string + getTranslation(GameSettings.STREAM_COMPRESSIONS, this.streamCompression);
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            return string + getTranslation(GameSettings.STREAM_CHAT_MODES, this.streamChatEnabled);
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            return string + getTranslation(GameSettings.STREAM_CHAT_FILTER_MODES, this.streamChatUserFilter);
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return string + getTranslation(GameSettings.STREAM_MIC_MODES, this.streamMicToggleBehavior);
        }
        if (options == Options.RENDER_CLOUDS) {
            return string + getTranslation(GameSettings.field_181149_aW, this.clouds);
        }
        if (options != Options.GRAPHICS) {
            return string;
        }
        if (this.fancyGraphics) {
            return string + I18n.format("options.graphics.fancy", new Object[0]);
        }
        return string + I18n.format("options.graphics.fast", new Object[0]);
    }
    
    public void sendSettingsToServer() {
        if (this.mc.thePlayer != null) {
            final Iterator<EnumPlayerModelParts> iterator = this.setModelParts.iterator();
            while (iterator.hasNext()) {
                final int n = 0x0 | iterator.next().getPartMask();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, 0));
        }
    }
    
    public void setOptionValue(final Options options, final int n) {
        this.setOptionValueOF(options, n);
        if (options == Options.INVERT_MOUSE) {
            this.invertMouse = !this.invertMouse;
        }
        if (options == Options.GUI_SCALE) {
            this.guiScale = (this.guiScale + n & 0x3);
        }
        if (options == Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + n) % 3;
        }
        if (options == Options.VIEW_BOBBING) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (options == Options.RENDER_CLOUDS) {
            this.clouds = (this.clouds + n) % 3;
        }
        if (options == Options.FORCE_UNICODE_FONT) {
            this.forceUnicodeFont = !this.forceUnicodeFont;
            this.mc.fontRendererObj.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
        }
        if (options == Options.FBO_ENABLE) {
            this.fboEnable = !this.fboEnable;
        }
        if (options == Options.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.refreshResources();
        }
        if (options == Options.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.updateRenderClouds();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + n) % 3;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CHAT_VISIBILITY) {
            this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + n) % 3);
        }
        if (options == Options.STREAM_COMPRESSION) {
            this.streamCompression = (this.streamCompression + n) % 3;
        }
        if (options == Options.STREAM_SEND_METADATA) {
            this.streamSendMetadata = !this.streamSendMetadata;
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            this.streamChatEnabled = (this.streamChatEnabled + n) % 3;
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            this.streamChatUserFilter = (this.streamChatUserFilter + n) % 3;
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            this.streamMicToggleBehavior = (this.streamMicToggleBehavior + n) % 2;
        }
        if (options == Options.CHAT_COLOR) {
            this.chatColours = !this.chatColours;
        }
        if (options == Options.CHAT_LINKS) {
            this.chatLinks = !this.chatLinks;
        }
        if (options == Options.CHAT_LINKS_PROMPT) {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }
        if (options == Options.SNOOPER_ENABLED) {
            this.snooperEnabled = !this.snooperEnabled;
        }
        if (options == Options.TOUCHSCREEN) {
            this.touchscreen = !this.touchscreen;
        }
        if (options == Options.USE_FULLSCREEN) {
            this.fullScreen = !this.fullScreen;
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (options == Options.ENABLE_VSYNC) {
            Display.setVSyncEnabled(this.enableVsync = !this.enableVsync);
        }
        if (options == Options.USE_VBO) {
            this.useVbo = !this.useVbo;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            this.allowBlockAlternatives = !this.allowBlockAlternatives;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.REDUCED_DEBUG_INFO) {
            this.reducedDebugInfo = !this.reducedDebugInfo;
        }
        if (options == Options.ENTITY_SHADOWS) {
            this.field_181151_V = !this.field_181151_V;
        }
        this.saveOptions();
    }
    
    static final class GameSettings$2
    {
        private static final String __OBFID = "CL_00000652";
        
        static {
            (GameSettings$2.field_151477_a = new int[Options.values().length])[Options.INVERT_MOUSE.ordinal()] = 1;
            GameSettings$2.field_151477_a[Options.VIEW_BOBBING.ordinal()] = 2;
            GameSettings$2.field_151477_a[Options.ANAGLYPH.ordinal()] = 3;
            GameSettings$2.field_151477_a[Options.FBO_ENABLE.ordinal()] = 4;
            GameSettings$2.field_151477_a[Options.CHAT_COLOR.ordinal()] = 5;
            GameSettings$2.field_151477_a[Options.CHAT_LINKS.ordinal()] = 6;
            GameSettings$2.field_151477_a[Options.CHAT_LINKS_PROMPT.ordinal()] = 7;
            GameSettings$2.field_151477_a[Options.SNOOPER_ENABLED.ordinal()] = 8;
            GameSettings$2.field_151477_a[Options.USE_FULLSCREEN.ordinal()] = 9;
            GameSettings$2.field_151477_a[Options.ENABLE_VSYNC.ordinal()] = 10;
            GameSettings$2.field_151477_a[Options.USE_VBO.ordinal()] = 11;
            GameSettings$2.field_151477_a[Options.TOUCHSCREEN.ordinal()] = 12;
            GameSettings$2.field_151477_a[Options.STREAM_SEND_METADATA.ordinal()] = 13;
            GameSettings$2.field_151477_a[Options.FORCE_UNICODE_FONT.ordinal()] = 14;
            GameSettings$2.field_151477_a[Options.BLOCK_ALTERNATIVES.ordinal()] = 15;
            GameSettings$2.field_151477_a[Options.REDUCED_DEBUG_INFO.ordinal()] = 16;
            GameSettings$2.field_151477_a[Options.ENTITY_SHADOWS.ordinal()] = 17;
        }
    }
    
    public enum Options
    {
        SWAMP_COLORS("SWAMP_COLORS", 86, "", 999, "Swamp Colors", false, false), 
        BETTER_GRASS("BETTER_GRASS", 60, "", 999, "Better Grass", false, false), 
        STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false), 
        ANIMATED_PORTAL("ANIMATED_PORTAL", 55, "", 999, "Portal Animated", false, false), 
        USE_VBO("USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true), 
        STREAM_SEND_METADATA("STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true), 
        FAST_RENDER("FAST_RENDER", 103, "", 999, "Fast Render", false, false), 
        VOID_PARTICLES("VOID_PARTICLES", 75, "", 999, "Void Particles", false, false), 
        SMOOTH_FPS("SMOOTH_FPS", 47, "", 999, "Smooth FPS", false, false), 
        STREAM_FPS("STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false);
        
        private final boolean enumBoolean;
        
        SUN_MOON("SUN_MOON", 68, "", 999, "Sun & Moon", false, false), 
        ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 62, "", 999, "Explosion Animated", false, false), 
        STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false), 
        CONNECTED_TEXTURES("CONNECTED_TEXTURES", 92, "", 999, "Connected Textures", false, false), 
        AA_LEVEL("AA_LEVEL", 93, "", 999, "Antialiasing", true, false, 0.0f, 16.0f, 1.0f), 
        TOUCHSCREEN("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true), 
        FOG_START("FOG_START", 45, "", 999, "Fog Start", false, false), 
        SHOW_CAPES("SHOW_CAPES", 91, "", 999, "Show Capes", false, false), 
        CLEAR_WATER("CLEAR_WATER", 73, "", 999, "Clear Water", false, false), 
        CLOUDS("CLOUDS", 48, "", 999, "Clouds", false, false), 
        SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true);
        
        private final boolean enumFloat;
        
        MIPMAP_TYPE("MIPMAP_TYPE", 46, "", 999, "Mipmap Type", true, false, 0.0f, 3.0f, 1.0f), 
        CHAT_VISIBILITY("CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false), 
        POTION_PARTICLES("POTION_PARTICLES", 79, "", 999, "Potion Particles", false, false), 
        GRAPHICS("GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false), 
        CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true), 
        WATER_PARTICLES("WATER_PARTICLES", 76, "", 999, "Water Particles", false, false), 
        LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 100, "", 999, "Lazy Chunk Loading", false, false), 
        ANIMATED_WATER("ANIMATED_WATER", 52, "", 999, "Water Animated", false, false), 
        SMOOTH_BIOMES("SMOOTH_BIOMES", 88, "", 999, "Smooth Biomes", false, false), 
        SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false);
        
        private float valueMin;
        
        CHUNK_UPDATES("CHUNK_UPDATES", 70, "", 999, "Chunk Updates", false, false), 
        CHAT_LINKS("CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true), 
        SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
        GUI_SCALE("GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false), 
        RENDER_CLOUDS("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, false), 
        FOG_FANCY("FOG_FANCY", 44, "FOG", 999, "Fog", false, false), 
        CUSTOM_SKY("CUSTOM_SKY", 101, "", 999, "Custom Sky", false, false), 
        BETTER_SNOW("BETTER_SNOW", 83, "", 999, "Better Snow", false, false);
        
        private static final Options[] $VALUES$;
        
        NATURAL_TEXTURES("NATURAL_TEXTURES", 96, "", 999, "Natural Textures", false, false), 
        STREAM_KBPS("STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false);
        
        private static final String __OBFID = "CL_00000653";
        
        ANIMATED_TEXTURES("ANIMATED_TEXTURES", 95, "", 999, "Textures Animated", false, false), 
        FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true), 
        GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
        SMOOTH_WORLD("SMOOTH_WORLD", 74, "", 999, "Smooth World", false, false), 
        CHAT_OPACITY("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false), 
        MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f), 
        CHAT_WIDTH("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false), 
        CHAT_SCALE("CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false), 
        ANIMATED_LAVA("ANIMATED_LAVA", 53, "", 999, "Lava Animated", false, false), 
        AO_LEVEL("AO_LEVEL", 56, "", 999, "Smooth Lighting Level", true, false), 
        TIME("TIME", 72, "", 999, "Time", false, false), 
        ANIMATED_TERRAIN("ANIMATED_TERRAIN", 85, "", 999, "Terrain Animated", false, false), 
        FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f), 
        CLOUD_HEIGHT("CLOUD_HEIGHT", 49, "", 999, "Cloud Height", true, false), 
        DROPPED_ITEMS("DROPPED_ITEMS", 99, "", 999, "Dropped Items", false, false), 
        STARS("STARS", 67, "", 999, "Stars", false, false), 
        VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
        BLOCK_ALTERNATIVES("BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true), 
        CHAT_COLOR("CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true), 
        DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 82, "", 999, "Dripping Water/Lava", false, false), 
        STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false), 
        TREES("TREES", 50, "", 999, "Trees", false, false), 
        ENTITY_SHADOWS("ENTITY_SHADOWS", 43, "ENTITY_SHADOWS", 43, "options.entityShadows", false, true), 
        CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 71, "", 999, "Dynamic Updates", false, false), 
        WEATHER("WEATHER", 65, "", 999, "Weather", false, false), 
        CUSTOM_COLORS("CUSTOM_COLORS", 90, "", 999, "Custom Colors", false, false), 
        ANIMATED_FLAME("ANIMATED_FLAME", 63, "", 999, "Flame Animated", false, false), 
        ANIMATED_FIRE("ANIMATED_FIRE", 54, "", 999, "Fire Animated", false, false), 
        LAGOMETER("LAGOMETER", 57, "", 999, "Lagometer", false, false), 
        AUTOSAVE_TICKS("AUTOSAVE_TICKS", 59, "", 999, "Autosave", false, false), 
        SHOW_FPS("SHOW_FPS", 58, "", 999, "Show FPS", false, false), 
        STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false), 
        USE_FULLSCREEN("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true);
        
        private static final Options[] $VALUES;
        
        SKY("SKY", 66, "", 999, "Sky", false, false), 
        PROFILER("PROFILER", 81, "", 999, "Debug Profiler", false, false), 
        ENABLE_VSYNC("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true), 
        CUSTOM_FONTS("CUSTOM_FONTS", 89, "", 999, "Custom Fonts", false, false), 
        HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 98, "", 999, "Held Item Tooltips", false, false), 
        INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
        STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false), 
        CHUNK_LOADING("CHUNK_LOADING", 97, "", 999, "Chunk Loading", false, false), 
        TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 104, "", 999, "Translucent Blocks", false, false), 
        FBO_ENABLE("FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true);
        
        private float valueMax;
        
        AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false);
        
        private final float valueStep;
        
        FAST_MATH("FAST_MATH", 102, "", 999, "Fast Math", false, false), 
        AF_LEVEL("AF_LEVEL", 94, "", 999, "Anisotropic Filtering", true, false, 1.0f, 16.0f, 1.0f), 
        PORTAL_PARTICLES("PORTAL_PARTICLES", 78, "", 999, "Portal Particles", false, false), 
        RANDOM_MOBS("RANDOM_MOBS", 87, "", 999, "Random Mobs", false, false), 
        STREAM_COMPRESSION("STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "options.stream.compression", false, false), 
        CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false), 
        RAIN_SPLASH("RAIN_SPLASH", 77, "", 999, "Rain Splash", false, false), 
        ANIMATED_SMOKE("ANIMATED_SMOKE", 64, "", 999, "Smoke Animated", false, false), 
        VIGNETTE("VIGNETTE", 69, "", 999, "Vignette", false, false), 
        FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0f, 110.0f, 1.0f), 
        STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false), 
        REDUCED_DEBUG_INFO("REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true), 
        RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f), 
        FIREWORK_PARTICLES("FIREWORK_PARTICLES", 80, "", 999, "Firework Particles", false, false), 
        CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false), 
        RAIN("RAIN", 51, "", 999, "Rain & Snow", false, false), 
        FULLSCREEN_MODE("FULLSCREEN_MODE", 84, "", 999, "Fullscreen Mode", false, false);
        
        private final String enumString;
        
        ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true), 
        ANIMATED_REDSTONE("ANIMATED_REDSTONE", 61, "", 999, "Redstone Animated", false, false), 
        PARTICLES("PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false);
        
        public float normalizeValue(final float n) {
            return MathHelper.clamp_float((this.snapToStepClamp(n) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
        }
        
        static float access$100(final Options options) {
            return options.valueMin;
        }
        
        public float getValueMax() {
            return this.valueMax;
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String s3, final boolean b, final boolean b2) {
            this(s, n, s2, n2, s3, b, b2, 0.0f, 1.0f, 0.0f);
        }
        
        public boolean getEnumFloat() {
            return this.enumFloat;
        }
        
        protected float snapToStep(float n) {
            if (this.valueStep > 0.0f) {
                n = this.valueStep * Math.round(n / this.valueStep);
            }
            return n;
        }
        
        public static Options getEnumOptions(final int n) {
            final Options[] values = values();
            while (0 < values.length) {
                final Options options = values[0];
                if (options.returnEnumOrdinal() == n) {
                    return options;
                }
                int n2 = 0;
                ++n2;
            }
            return null;
        }
        
        public void setValueMax(final float valueMax) {
            this.valueMax = valueMax;
        }
        
        public int returnEnumOrdinal() {
            return this.ordinal();
        }
        
        static {
            $VALUES$ = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO, Options.ENTITY_SHADOWS, Options.FOG_FANCY, Options.FOG_START, Options.MIPMAP_TYPE, Options.SMOOTH_FPS, Options.CLOUDS, Options.CLOUD_HEIGHT, Options.TREES, Options.RAIN, Options.ANIMATED_WATER, Options.ANIMATED_LAVA, Options.ANIMATED_FIRE, Options.ANIMATED_PORTAL, Options.AO_LEVEL, Options.LAGOMETER, Options.SHOW_FPS, Options.AUTOSAVE_TICKS, Options.BETTER_GRASS, Options.ANIMATED_REDSTONE, Options.ANIMATED_EXPLOSION, Options.ANIMATED_FLAME, Options.ANIMATED_SMOKE, Options.WEATHER, Options.SKY, Options.STARS, Options.SUN_MOON, Options.VIGNETTE, Options.CHUNK_UPDATES, Options.CHUNK_UPDATES_DYNAMIC, Options.TIME, Options.CLEAR_WATER, Options.SMOOTH_WORLD, Options.VOID_PARTICLES, Options.WATER_PARTICLES, Options.RAIN_SPLASH, Options.PORTAL_PARTICLES, Options.POTION_PARTICLES, Options.FIREWORK_PARTICLES, Options.PROFILER, Options.DRIPPING_WATER_LAVA, Options.BETTER_SNOW, Options.FULLSCREEN_MODE, Options.ANIMATED_TERRAIN, Options.SWAMP_COLORS, Options.RANDOM_MOBS, Options.SMOOTH_BIOMES, Options.CUSTOM_FONTS, Options.CUSTOM_COLORS, Options.SHOW_CAPES, Options.CONNECTED_TEXTURES, Options.AA_LEVEL, Options.AF_LEVEL, Options.ANIMATED_TEXTURES, Options.NATURAL_TEXTURES, Options.CHUNK_LOADING, Options.HELD_ITEM_TOOLTIPS, Options.DROPPED_ITEMS, Options.LAZY_CHUNK_LOADING, Options.CUSTOM_SKY, Options.FAST_MATH, Options.FAST_RENDER, Options.TRANSLUCENT_BLOCKS };
            $VALUES = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO, Options.ENTITY_SHADOWS };
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String enumString, final boolean enumFloat, final boolean enumBoolean, final float valueMin, final float valueMax, final float valueStep) {
            this.enumString = enumString;
            this.enumFloat = enumFloat;
            this.enumBoolean = enumBoolean;
            this.valueMin = valueMin;
            this.valueMax = valueMax;
            this.valueStep = valueStep;
        }
        
        public boolean getEnumBoolean() {
            return this.enumBoolean;
        }
        
        public String getEnumString() {
            return this.enumString;
        }
        
        static float access$000(final Options options) {
            return options.valueMax;
        }
        
        public float denormalizeValue(final float n) {
            return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(n, 0.0f, 1.0f));
        }
        
        public float snapToStepClamp(float snapToStep) {
            snapToStep = this.snapToStep(snapToStep);
            return MathHelper.clamp_float(snapToStep, this.valueMin, this.valueMax);
        }
    }
}
