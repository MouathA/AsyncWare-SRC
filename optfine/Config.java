package optfine;

import net.minecraft.client.settings.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.integrated.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import java.lang.reflect.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.awt.*;
import org.lwjgl.*;
import net.minecraft.client.resources.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import java.util.*;

public class Config
{
    public static final Boolean DEF_OCCLUSION_ENABLED;
    public static final Boolean DEF_LOAD_CHUNKS_FAR;
    public static final String OF_NAME = "OptiFine";
    private static long lastActionTime;
    public static final Integer DEF_MIPMAP_LEVEL;
    private static PrintStream systemOut;
    private static boolean fullscreenModeChecked;
    public static String openGlVersion;
    private static Thread minecraftThread;
    public static String openGlRenderer;
    public static boolean zoomMode;
    private static boolean notify64BitJava;
    public static boolean occlusionAvailable;
    public static String openGlVendor;
    public static final Float DEF_FOG_START;
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "E1";
    private static DisplayMode desktopDisplayMode;
    public static boolean fancyFogAvailable;
    private static Minecraft minecraft;
    public static final Boolean DEF_DYNAMIC_UPDATES;
    public static final Integer DEF_CHUNKS_LIMIT;
    private static boolean desktopModeChecked;
    private static String newRelease;
    public static final Float DEF_ALPHA_FUNC_LEVEL;
    public static boolean waterOpacityChanged;
    public static final String VERSION = "OptiFine_1.8.8_HD_U_E1";
    public static final Integer DEF_MIPMAP_TYPE;
    private static boolean initialized;
    public static final Integer DEF_PRELOADED_CHUNKS;
    public static final String MC_VERSION = "1.8.8";
    private static GameSettings gameSettings;
    public static final Integer DEF_UPDATES_PER_FRAME;
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE;
    public static final Boolean DEF_FOG_FANCY;
    
    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }
    
    public static boolean isTimeNightOnly() {
        return Config.gameSettings.ofTime == 3;
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }
    
    public static boolean isTimeDefault() {
        return Config.gameSettings.ofTime == 0 || Config.gameSettings.ofTime == 2;
    }
    
    public static int limit(final int n, final int n2, final int n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static boolean isFogFast() {
        return Config.gameSettings.ofFogType == 1;
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void checkDisplaySettings() {
        if (getAntialiasingLevel() > 0) {
            final int antialiasingLevel = getAntialiasingLevel();
            final DisplayMode displayMode = Display.getDisplayMode();
            dbg("FSAA Samples: " + antialiasingLevel);
            Display.destroy();
            Display.setDisplayMode(displayMode);
            Display.create(new PixelFormat().withDepthBits(24).withSamples(antialiasingLevel));
            Display.setResizable(false);
            Display.setResizable(true);
        }
    }
    
    public static boolean isCloudsFancy() {
        return (Config.gameSettings.ofClouds != 0) ? (Config.gameSettings.ofClouds == 2) : Config.gameSettings.fancyGraphics;
    }
    
    public static void drawFps() {
        final Minecraft minecraft = Config.minecraft;
        Config.minecraft.fontRendererObj.drawString("" + Minecraft.getDebugFPS() + " fps, C: " + Config.minecraft.renderGlobal.getCountActiveRenderers() + ", E: " + Config.minecraft.renderGlobal.getCountEntitiesRendered() + "+" + Config.minecraft.renderGlobal.getCountTileEntitiesRendered() + ", U: " + getUpdates(Config.minecraft.debug), 2.0, 2.0, -2039584);
    }
    
    public static boolean isFogOff() {
        return Config.gameSettings.ofFogType == 3;
    }
    
    public static float parseFloat(final String s, final float n) {
        return (s == null) ? n : Float.parseFloat(s);
    }
    
    public static String readInputStream(final InputStream inputStream) throws IOException {
        return readInputStream(inputStream, "ASCII");
    }
    
    private static void setThreadPriority(final String s, final int priority) {
        final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (threadGroup == null) {
            return;
        }
        final Thread[] array = new Thread[(threadGroup.activeCount() + 10) * 2];
        threadGroup.enumerate(array, false);
        while (0 < array.length) {
            final Thread thread = array[0];
            if (thread != null && thread.getName().startsWith(s)) {
                thread.setPriority(priority);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static boolean isTimeDayOnly() {
        return Config.gameSettings.ofTime == 1;
    }
    
    public static String getResourcePackNames() {
        if (Config.minecraft == null) {
            return "";
        }
        if (Config.minecraft.getResourcePackRepository() == null) {
            return "";
        }
        final IResourcePack[] resourcePacks = getResourcePacks();
        if (resourcePacks.length <= 0) {
            return getDefaultResourcePack().getPackName();
        }
        final String[] array = new String[resourcePacks.length];
        while (0 < resourcePacks.length) {
            array[0] = resourcePacks[0].getPackName();
            int n = 0;
            ++n;
        }
        return arrayToString(array);
    }
    
    public static boolean isVignetteEnabled() {
        return (Config.gameSettings.ofVignette == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofVignette == 2);
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }
    
    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }
    
    public static String fillRight(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    private static void checkOpenGlCaps() {
        log("");
        log(getVersion());
        log("" + new Date());
        log("OS: " + "\u88d9\u88c5\u8898\u88d8\u88d7\u88db\u88d3" + " (" + "\u88d9\u88c5\u8898\u88d7\u88c4\u88d5\u88de" + ") version " + "\u88d9\u88c5\u8898\u88c0\u88d3\u88c4\u88c5\u88df\u88d9\u88d8");
        log("Java: " + "\u88dc\u88d7\u88c0\u88d7\u8898\u88c0\u88d3\u88c4\u88c5\u88df\u88d9\u88d8" + ", " + "\u88dc\u88d7\u88c0\u88d7\u8898\u88c0\u88d3\u88d8\u88d2\u88d9\u88c4");
        log("VM: " + "\u88dc\u88d7\u88c0\u88d7\u8898\u88c0\u88db\u8898\u88d8\u88d7\u88db\u88d3" + " (" + "\u88dc\u88d7\u88c0\u88d7\u8898\u88c0\u88db\u8898\u88df\u88d8\u88d0\u88d9" + "), " + "\u88dc\u88d7\u88c0\u88d7\u8898\u88c0\u88db\u8898\u88c0\u88d3\u88d8\u88d2\u88d9\u88c4");
        log("LWJGL: " + Sys.getVersion());
        Config.openGlVersion = GL11.glGetString(7938);
        Config.openGlRenderer = GL11.glGetString(7937);
        Config.openGlVendor = GL11.glGetString(7936);
        log("OpenGL: " + Config.openGlRenderer + ", version " + Config.openGlVersion + ", " + Config.openGlVendor);
        log("OpenGL Version: " + getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!(Config.fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance)) {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!(Config.occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query)) {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + glMaximumTextureSize + "x" + glMaximumTextureSize);
    }
    
    public static void dbg(final String s) {
        Config.systemOut.print("[OptiFine] ");
        Config.systemOut.println(s);
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }
    
    public static TextureManager getTextureManager() {
        return Config.minecraft.getTextureManager();
    }
    
    private static void startVersionCheckThread() {
        new VersionCheckThread().start();
    }
    
    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings.ofConnectedTextures == 2;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }
    
    public static void error(final String s) {
        Config.systemOut.print("[OptiFine] [ERROR] ");
        Config.systemOut.println(s);
    }
    
    public static boolean isAnimatedWater() {
        return Config.gameSettings.ofAnimatedWater != 2;
    }
    
    public static void initGameSettings(final GameSettings gameSettings) {
        Config.gameSettings = gameSettings;
        Config.minecraft = Minecraft.getMinecraft();
        Config.desktopDisplayMode = Display.getDesktopDisplayMode();
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static String arrayToString(final int[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length * 5);
        while (0 < array.length) {
            sb.append(String.valueOf(array[0]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static boolean isFancyFogAvailable() {
        return Config.fancyFogAvailable;
    }
    
    public static boolean isActing() {
        final boolean actingNow = isActingNow();
        final long currentTimeMillis = System.currentTimeMillis();
        if (actingNow) {
            Config.lastActionTime = currentTimeMillis;
            return true;
        }
        return currentTimeMillis - Config.lastActionTime < 100L;
    }
    
    public static String arrayToString(final Object[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length * 5);
        while (0 < array.length) {
            sb.append(String.valueOf(array[0]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static boolean isConnectedModels() {
        return false;
    }
    
    public static float limitTo1(final float n) {
        return (n < 0.0f) ? 0.0f : ((n > 1.0f) ? 1.0f : n);
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean hasResource(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return resourceManager.getResource(resourceLocation) != null;
    }
    
    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }
    
    private static ByteBuffer readIconImage(final File file) throws IOException {
        final BufferedImage read = ImageIO.read(file);
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
    
    public static boolean isLazyChunkLoading() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_0       
        //     4: goto            13
        //     7: getstatic       optfine/Config.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    10: getfield        net/minecraft/client/settings/GameSettings.ofLazyChunkLoading:Z
        //    13: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }
    
    private static Method getMethod(final Class clazz, final String s, final Object[] array) {
        final Method[] methods = clazz.getMethods();
        while (0 < methods.length) {
            final Method method = methods[0];
            if (method.getName().equals(s) && method.getParameterTypes().length == array.length) {
                return method;
            }
            int n = 0;
            ++n;
        }
        warn("No method found for: " + clazz.getName() + "." + s + "(" + arrayToString(array) + ")");
        return null;
    }
    
    public static boolean isUseAlphaFunc() {
        return getAlphaFuncLevel() > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f;
    }
    
    public static int parseInt(final String s, final int n) {
        return (s == null) ? n : Integer.parseInt(s);
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }
    
    public static WorldServer getWorldServer() {
        if (Config.minecraft == null) {
            return null;
        }
        final WorldClient theWorld = Config.minecraft.theWorld;
        if (theWorld == null) {
            return null;
        }
        if (!Config.minecraft.isIntegratedServerRunning()) {
            return null;
        }
        final IntegratedServer integratedServer = Config.minecraft.getIntegratedServer();
        if (integratedServer == null) {
            return null;
        }
        final WorldProvider provider = theWorld.provider;
        if (provider == null) {
            return null;
        }
        return integratedServer.worldServerForDimension(provider.getDimensionId());
    }
    
    public static void checkInitialized() {
        if (!Config.initialized && Display.isCreated()) {
            Config.initialized = true;
        }
    }
    
    public static void updateTexturePackClouds() {
        Config.texturePackClouds = 0;
        final IResourceManager resourceManager = getResourceManager();
        if (resourceManager != null) {
            final InputStream inputStream = resourceManager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (inputStream == null) {
                return;
            }
            final Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            final String property = properties.getProperty("clouds");
            if (property == null) {
                return;
            }
            dbg("Texture pack clouds: " + property);
            final String lowerCase = property.toLowerCase();
            if (lowerCase.equals("fast")) {
                Config.texturePackClouds = 1;
            }
            if (lowerCase.equals("fancy")) {
                Config.texturePackClouds = 2;
            }
        }
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }
    
    public static String getVersion() {
        return "OptiFine_1.8.8_HD_U_E1";
    }
    
    public static void updateAvailableProcessors() {
        Config.availableProcessors = Runtime.getRuntime().availableProcessors();
    }
    
    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }
    
    public static int intHash(int n) {
        n = (n ^ 0x3D ^ n >> 16);
        n += n << 3;
        n ^= n >> 4;
        n *= 668265261;
        n ^= n >> 15;
        return n;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static boolean isCloudsOff() {
        return Config.gameSettings.ofClouds == 3;
    }
    
    public static void setNewRelease(final String newRelease) {
        Config.newRelease = newRelease;
    }
    
    public static InputStream getResourceStream(final ResourceLocation resourceLocation) throws IOException {
        return getResourceStream(Config.minecraft.getResourceManager(), resourceLocation);
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }
    
    public static boolean isConnectedTextures() {
        return Config.gameSettings.ofConnectedTextures != 3;
    }
    
    public static int getAntialiasingLevel() {
        return 0;
    }
    
    public static void sleep(final long n) {
        Thread.currentThread();
        Thread.sleep(n);
    }
    
    public static boolean isAnimatedLava() {
        return Config.gameSettings.ofAnimatedLava != 2;
    }
    
    public static int getRandom(final BlockPos blockPos, final int n) {
        return intHash(intHash(intHash(intHash(n + 37) + blockPos.getX()) + blockPos.getZ()) + blockPos.getY());
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 10;
        }
        return Config.gameSettings.renderDistanceChunks;
    }
    
    public static int getAvailableProcessors() {
        return 0;
    }
    
    public static boolean isTranslucentBlocksFancy() {
        return (Config.gameSettings.ofTranslucentBlocks == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTranslucentBlocks == 2);
    }
    
    public static IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        return Config.minecraft.getResourceManager().getResource(resourceLocation);
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }
    
    private static boolean isActingNow() {
        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
    }
    
    public static Object[] addObjectsToArray(final Object[] array, final Object[] array2) {
        if (array == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (array2.length == 0) {
            return array;
        }
        final int length = array.length;
        final Object[] array3 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + array2.length);
        System.arraycopy(array, 0, array3, 0, length);
        System.arraycopy(array2, 0, array3, length, array2.length);
        return array3;
    }
    
    public static boolean isDroppedItemsFancy() {
        return (Config.gameSettings.ofDroppedItems == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofDroppedItems == 2);
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isBetterGrassFancy() {
        return Config.gameSettings.ofBetterGrass == 2;
    }
    
    public static void checkDisplayMode() {
        if (Config.minecraft.isFullScreen()) {
            if (Config.fullscreenModeChecked) {
                return;
            }
            Config.fullscreenModeChecked = true;
            Config.desktopModeChecked = false;
            final DisplayMode displayMode = Display.getDisplayMode();
            final Dimension fullscreenDimension = getFullscreenDimension();
            if (fullscreenDimension == null) {
                return;
            }
            if (displayMode.getWidth() == fullscreenDimension.width && displayMode.getHeight() == fullscreenDimension.height) {
                return;
            }
            final DisplayMode displayMode2 = getDisplayMode(fullscreenDimension);
            if (displayMode2 == null) {
                return;
            }
            Display.setDisplayMode(displayMode2);
            Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
            Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
            if (Config.minecraft.displayWidth <= 0) {
                Config.minecraft.displayWidth = 1;
            }
            if (Config.minecraft.displayHeight <= 0) {
                Config.minecraft.displayHeight = 1;
            }
            if (Config.minecraft.currentScreen != null) {
                final ScaledResolution scaledResolution = new ScaledResolution(Config.minecraft);
                Config.minecraft.currentScreen.setWorldAndResolution(Config.minecraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
            Config.minecraft.loadingScreen = new LoadingScreenRenderer(Config.minecraft);
            Display.setFullscreen(true);
            Config.minecraft.gameSettings.updateVSync();
        }
        else {
            if (Config.desktopModeChecked) {
                return;
            }
            Config.desktopModeChecked = true;
            Config.fullscreenModeChecked = false;
            Config.minecraft.gameSettings.updateVSync();
            Display.update();
            Display.setResizable(false);
            Display.setResizable(true);
        }
    }
    
    public static int compareRelease(final String s, final String s2) {
        final String[] splitRelease = splitRelease(s);
        final String[] splitRelease2 = splitRelease(s2);
        final String s3 = splitRelease[0];
        final String s4 = splitRelease2[0];
        if (!s3.equals(s4)) {
            return s3.compareTo(s4);
        }
        final int int1 = parseInt(splitRelease[1], -1);
        final int int2 = parseInt(splitRelease2[1], -1);
        if (int1 != int2) {
            return int1 - int2;
        }
        return splitRelease[2].compareTo(splitRelease2[2]);
    }
    
    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }
    
    public static String getOpenGlVersionString() {
        final int openGlVersion = getOpenGlVersion();
        return "" + openGlVersion / 10 + "." + openGlVersion % 10;
    }
    
    public static boolean isFogFancy() {
        return isFancyFogAvailable() && Config.gameSettings.ofFogType == 2;
    }
    
    public static int getMaxDynamicTileWidth() {
        return 64;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isGeneratedWater() {
        return Config.gameSettings.ofAnimatedWater == 1;
    }
    
    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }
    
    private static String getUpdates(final String s) {
        final int index = s.indexOf(40);
        if (index < 0) {
            return "";
        }
        final int index2 = s.indexOf(32, index);
        return (index2 < 0) ? "" : s.substring(index + 1, index2);
    }
    
    public static IResourcePack getDefiningResourcePack(final ResourceLocation resourceLocation) {
        final IResourcePack[] resourcePacks = getResourcePacks();
        for (int i = resourcePacks.length - 1; i >= 0; --i) {
            final IResourcePack resourcePack = resourcePacks[i];
            if (resourcePack.resourceExists(resourceLocation)) {
                return resourcePack;
            }
        }
        if (getDefaultResourcePack().resourceExists(resourceLocation)) {
            return getDefaultResourcePack();
        }
        return null;
    }
    
    public static DisplayMode[] getFullscreenDisplayModes() {
        final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        final ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
        while (0 < availableDisplayModes.length) {
            final DisplayMode displayMode = availableDisplayModes[0];
            if (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency())) {
                list.add(displayMode);
            }
            int n = 0;
            ++n;
        }
        final DisplayMode[] array = list.toArray(new DisplayMode[list.size()]);
        Arrays.sort(array, new Comparator() {
            @Override
            public int compare(final Object o, final Object o2) {
                final DisplayMode displayMode = (DisplayMode)o;
                final DisplayMode displayMode2 = (DisplayMode)o2;
                return (displayMode.getWidth() != displayMode2.getWidth()) ? (displayMode2.getWidth() - displayMode.getWidth()) : ((displayMode.getHeight() != displayMode2.getHeight()) ? (displayMode2.getHeight() - displayMode.getHeight()) : 0);
            }
        });
        return array;
    }
    
    public static void warn(final String s) {
        Config.systemOut.print("[OptiFine] [WARN] ");
        Config.systemOut.println(s);
    }
    
    public static String readInputStream(final InputStream inputStream, final String s) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, s));
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static boolean between(final int n, final int n2, final int n3) {
        return n >= n2 && n <= n3;
    }
    
    public static boolean isSmoothBiomes() {
        return Config.gameSettings.ofSmoothBiomes;
    }
    
    public static boolean isOcclusionAvailable() {
        return Config.occlusionAvailable;
    }
    
    public static float limit(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }
    
    public static Minecraft getMinecraft() {
        return Config.minecraft;
    }
    
    public static int getBitsJre() {
        final String[] array = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        while (0 < array.length) {
            final String property = System.getProperty(array[0]);
            if (property != null && property.contains("64")) {
                return 64;
            }
            int n = 0;
            ++n;
        }
        return 32;
    }
    
    public static DisplayMode getDisplayMode(final Dimension dimension) throws LWJGLException {
        final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        while (0 < availableDisplayModes.length) {
            final DisplayMode displayMode = availableDisplayModes[0];
            if (displayMode.getWidth() == dimension.width && displayMode.getHeight() == dimension.height && (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency()))) {
                return displayMode;
            }
            int n = 0;
            ++n;
        }
        return Config.desktopDisplayMode;
    }
    
    public static boolean isMinecraftThread() {
        return Thread.currentThread() == Config.minecraftThread;
    }
    
    public static int getBitsOs() {
        return ("\u88e6\u88c4\u88d9\u88d1\u88c4\u88d7\u88db\u88f0\u88df\u88da\u88d3\u88c5\u889e\u88ee\u888e\u8880\u889f" != null) ? 64 : 32;
    }
    
    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }
    
    public static String[] readLines(final File file) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ASCII"));
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static Dimension getFullscreenDimension() {
        if (Config.desktopDisplayMode == null) {
            return null;
        }
        if (Config.gameSettings == null) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String ofFullscreenMode = Config.gameSettings.ofFullscreenMode;
        if (ofFullscreenMode.equals("Default")) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String[] tokenize = tokenize(ofFullscreenMode, " x");
        return (tokenize.length < 2) ? new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight()) : new Dimension(parseInt(tokenize[0], -1), parseInt(tokenize[1], -1));
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Config.desktopDisplayMode;
    }
    
    public static void log(final String s) {
        dbg(s);
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }
    
    private static int getOpenGlVersion() {
        return GLContext.getCapabilities().OpenGL11 ? (GLContext.getCapabilities().OpenGL12 ? (GLContext.getCapabilities().OpenGL13 ? (GLContext.getCapabilities().OpenGL14 ? (GLContext.getCapabilities().OpenGL15 ? (GLContext.getCapabilities().OpenGL20 ? (GLContext.getCapabilities().OpenGL21 ? (GLContext.getCapabilities().OpenGL30 ? (GLContext.getCapabilities().OpenGL31 ? (GLContext.getCapabilities().OpenGL32 ? (GLContext.getCapabilities().OpenGL33 ? (GLContext.getCapabilities().OpenGL40 ? 40 : 33) : 32) : 31) : 30) : 21) : 20) : 15) : 14) : 13) : 12) : 11) : 10;
    }
    
    public static IResourcePack[] getResourcePacks() {
        final List repositoryEntries = Config.minecraft.getResourcePackRepository().getRepositoryEntries();
        final ArrayList<IResourcePack> list = new ArrayList<IResourcePack>();
        final Iterator<ResourcePackRepository.Entry> iterator = repositoryEntries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getResourcePack());
        }
        return list.toArray(new IResourcePack[list.size()]);
    }
    
    public static boolean hasResource(final ResourceLocation resourceLocation) {
        return getResource(resourceLocation) != null;
    }
    
    public static void initDisplay() {
        Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
        Config.minecraftThread = Thread.currentThread();
    }
    
    public static boolean isFromDefaultResourcePack(final ResourceLocation resourceLocation) {
        return getDefiningResourcePack(resourceLocation) == getDefaultResourcePack();
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static void checkGlError(final String s) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            error("OpenGlError: " + glGetError + " (" + GLU.gluErrorString(glGetError) + "), at: " + s);
        }
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static String fillLeft(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n - s.length()) {
            sb.append(c);
        }
        return sb.toString() + s;
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static GameSettings getGameSettings() {
        return Config.gameSettings;
    }
    
    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }
    
    public static RenderGlobal getRenderGlobal() {
        return (Config.minecraft == null) ? null : Config.minecraft.renderGlobal;
    }
    
    public static void setNotify64BitJava(final boolean notify64BitJava) {
        Config.notify64BitJava = notify64BitJava;
    }
    
    public static boolean isRainFancy() {
        return (Config.gameSettings.ofRain == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofRain == 2);
    }
    
    private static String[] splitRelease(final String s) {
        if (s == null || s.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final String substring = s.substring(0, 1);
        if (s.length() <= 1) {
            return new String[] { substring, "", "" };
        }
        while (1 < s.length() && Character.isDigit(s.charAt(1))) {
            int n = 0;
            ++n;
        }
        final String substring2 = s.substring(1, 1);
        if (1 >= s.length()) {
            return new String[] { substring, substring2, "" };
        }
        return new String[] { substring, substring2, s.substring(1) };
    }
    
    public static boolean isCustomItems() {
        return false;
    }
    
    public static boolean isRandomMobs() {
        return Config.gameSettings.ofRandomMobs;
    }
    
    public static InputStream getResourceStream(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final IResource resource = resourceManager.getResource(resourceLocation);
        return (resource == null) ? null : resource.getInputStream();
    }
    
    public static void updateFramebufferSize() {
        Config.minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }
    
    public static boolean isTreesFancy() {
        return (Config.gameSettings.ofTrees == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTrees == 2);
    }
    
    public static boolean isNotify64BitJava() {
        return Config.notify64BitJava;
    }
    
    public static String[] getFullscreenModes() {
        final DisplayMode[] fullscreenDisplayModes = getFullscreenDisplayModes();
        final String[] array = new String[fullscreenDisplayModes.length];
        while (0 < fullscreenDisplayModes.length) {
            final DisplayMode displayMode = fullscreenDisplayModes[0];
            array[0] = "" + displayMode.getWidth() + "x" + displayMode.getHeight();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }
    
    public static Object[] addObjectToArray(final Object[] array, final Object o) {
        if (array == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int length = array.length;
        final Object[] array2 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + 1);
        System.arraycopy(array, 0, array2, 0, length);
        array2[length] = o;
        return array2;
    }
    
    public static IResourceManager getResourceManager() {
        return Config.minecraft.getResourceManager();
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }
    
    public static boolean isBetterGrass() {
        return Config.gameSettings.ofBetterGrass != 3;
    }
    
    static {
        Config.newRelease = null;
        Config.notify64BitJava = false;
        Config.openGlVersion = null;
        Config.openGlRenderer = null;
        Config.openGlVendor = null;
        Config.fancyFogAvailable = false;
        Config.occlusionAvailable = false;
        Config.gameSettings = null;
        Config.minecraft = null;
        Config.initialized = false;
        Config.minecraftThread = null;
        Config.desktopDisplayMode = null;
        Config.zoomMode = false;
        Config.waterOpacityChanged = false;
        Config.fullscreenModeChecked = false;
        Config.desktopModeChecked = false;
        Config.systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
        DEF_FOG_FANCY = true;
        DEF_FOG_START = 0.2f;
        DEF_OPTIMIZE_RENDER_DISTANCE = false;
        DEF_OCCLUSION_ENABLED = false;
        DEF_MIPMAP_LEVEL = 0;
        DEF_MIPMAP_TYPE = 9984;
        DEF_ALPHA_FUNC_LEVEL = 0.1f;
        DEF_LOAD_CHUNKS_FAR = false;
        DEF_PRELOADED_CHUNKS = 0;
        DEF_CHUNKS_LIMIT = 25;
        DEF_UPDATES_PER_FRAME = 3;
        DEF_DYNAMIC_UPDATES = false;
        Config.lastActionTime = System.currentTimeMillis();
    }
    
    public static boolean isGeneratedLava() {
        return Config.gameSettings.ofAnimatedLava == 1;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }
    
    public static String readFile(final File file) throws IOException {
        return readInputStream(new FileInputStream(file), "ASCII");
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }
    
    public static void updateThreadPriorities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokestatic    optfine/Config.isSmoothWorld:()Z
        //     6: ifeq            27
        //     9: getstatic       optfine/Config.minecraftThread:Ljava/lang/Thread;
        //    12: bipush          10
        //    14: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    17: ldc_w           "Server thread"
        //    20: iconst_1       
        //    21: invokestatic    optfine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    24: goto            59
        //    27: getstatic       optfine/Config.minecraftThread:Ljava/lang/Thread;
        //    30: iconst_5       
        //    31: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    34: ldc_w           "Server thread"
        //    37: iconst_5       
        //    38: invokestatic    optfine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    41: goto            59
        //    44: getstatic       optfine/Config.minecraftThread:Ljava/lang/Thread;
        //    47: bipush          10
        //    49: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    52: ldc_w           "Server thread"
        //    55: iconst_5       
        //    56: invokestatic    optfine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    59: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean equals(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    public static int getMipmapType() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnonnull       13
        //     6: getstatic       optfine/Config.DEF_MIPMAP_TYPE:Ljava/lang/Integer;
        //     9: invokevirtual   java/lang/Integer.intValue:()I
        //    12: ireturn        
        //    13: getstatic       optfine/Config.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    16: getfield        net/minecraft/client/settings/GameSettings.ofMipmapType:I
        //    19: tableswitch {
        //                0: 48
        //                1: 52
        //                2: 56
        //                3: 67
        //          default: 78
        //        }
        //    48: sipush          9986
        //    51: ireturn        
        //    52: sipush          9986
        //    55: ireturn        
        //    56: if_icmple       63
        //    59: sipush          9985
        //    62: ireturn        
        //    63: sipush          9986
        //    66: ireturn        
        //    67: if_icmple       74
        //    70: sipush          9987
        //    73: ireturn        
        //    74: sipush          9986
        //    77: ireturn        
        //    78: sipush          9986
        //    81: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static String[] tokenize(final String s, final String s2) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, s2);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
}
