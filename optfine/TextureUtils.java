package optfine;

import java.nio.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class TextureUtils
{
    public static final String texCoarseDirt = "coarse_dirt";
    public static TextureAtlasSprite iconMyceliumTop;
    public static final String texLeavesSpruce = "leaves_spruce";
    public static final String texSnow = "snow";
    public static final String texSand = "sand";
    public static TextureAtlasSprite iconWaterStill;
    public static final String texLeavesJungle = "leaves_jungle";
    public static final String texObsidian = "obsidian";
    public static final String texGravel = "gravel";
    public static final String texBedrock = "bedrock";
    public static final String texLeavesBigOak = "leaves_big_oak";
    public static final String texWaterStill = "water_still";
    public static final String texSoulSand = "soul_sand";
    public static final String texClay = "clay";
    public static final String texStone = "stone";
    public static TextureAtlasSprite iconLavaFlow;
    public static final String texLogOakTop = "log_oak_top";
    public static final String texCoalOre = "coal_ore";
    public static final String texLogBigOakTop = "log_big_oak_top";
    public static final String texGrassSideOverlay = "grass_side_overlay";
    public static TextureAtlasSprite iconMyceliumSide;
    public static final String texPortal = "portal";
    public static final String texLeavesBirch = "leaves_birch";
    public static final String texSandstoneTop = "sandstone_top";
    public static TextureAtlasSprite iconWaterFlow;
    public static final String texMyceliumTop = "mycelium_top";
    public static TextureAtlasSprite iconSnow;
    public static final String texLogAcaciaTop = "log_acacia_top";
    public static final String texLogSpruce = "log_spruce";
    public static final String texFarmlandWet = "farmland_wet";
    public static TextureAtlasSprite iconPortal;
    public static final String texCactusSide = "cactus_side";
    public static TextureAtlasSprite iconGrassTop;
    public static final String texRedstoneOre = "redstone_ore";
    private static IntBuffer staticBuffer;
    public static final String texGlass = "glass";
    public static final String texRedstoneLampOff = "redstone_lamp_off";
    public static final String texStoneslabTop = "stone_slab_top";
    public static final String texGrassSide = "grass_side";
    public static TextureAtlasSprite iconGrassSide;
    public static final String texLogJungle = "log_jungle";
    public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
    public static final String texLogBirchTop = "log_birch_top";
    public static final String SPRITE_LOCATION_PREFIX = "minecraft:blocks/";
    public static final String texMyceliumSide = "mycelium_side";
    public static final String texWaterFlow = "water_flow";
    public static final String texNetherrack = "netherrack";
    public static TextureAtlasSprite iconFireLayer0;
    public static final String texLogBigOak = "log_big_oak";
    public static final String texLogOak = "log_oak";
    public static TextureAtlasSprite iconFireLayer1;
    public static final String texFarmlandDry = "farmland_dry";
    public static final String texDiamondOre = "diamond_ore";
    public static final String texSandstoneBottom = "sandstone_bottom";
    public static final String texLogJungleTop = "log_jungle_top";
    public static final String texLavaStill = "lava_still";
    public static final String texFireLayer0 = "fire_layer_0";
    public static final String texLogAcacia = "log_acacia";
    public static final String texLeavesAcacia = "leaves_acacia";
    public static final String texGrassTop = "grass_top";
    public static final String texGlowstone = "glowstone";
    public static final String texEndStone = "end_stone";
    public static final String texStoneslabSide = "stone_slab_side";
    public static TextureAtlasSprite iconGrassSideOverlay;
    public static final String texGoldOre = "gold_ore";
    public static final String texLeavesSpuce = "leaves_spruce";
    public static final String texGrassSideSnowed = "grass_side_snowed";
    public static final String texGlassPaneTop = "glass_pane_top";
    public static final String texLavaFlow = "lava_flow";
    public static TextureAtlasSprite iconLavaStill;
    public static final String texFireLayer1 = "fire_layer_1";
    public static final String texDirt = "dirt";
    public static TextureAtlasSprite iconGrassSideSnowed;
    public static TextureAtlasSprite iconGlass;
    public static final String texIronOre = "iron_ore";
    public static final String texLogBirch = "log_birch";
    public static final String texLapisOre = "lapis_ore";
    public static final String texLeavesOak = "leaves_oak";
    public static final String texLogSpruceTop = "log_spruce_top";
    public static TextureAtlasSprite iconGlassPaneTop;
    public static final String texRedstoneLampOn = "redstone_lamp_on";
    
    public static int ceilPowerOfTwo(final int n) {
        while (1 < n) {}
        return 1;
    }
    
    public static void resourcesReloaded(final IResourceManager resourceManager) {
        if (getTextureMapBlocks() != null) {
            Config.dbg("*** Reloading custom textures ***");
            Config.getTextureManager().tick();
        }
    }
    
    public static void bindTexture(final int n) {
        GlStateManager.bindTexture(n);
    }
    
    public static boolean isPowerOfTwo(final int n) {
        return MathHelper.roundUpToPowerOfTwo(n) == n;
    }
    
    public static String getBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public static BufferedImage fixTextureDimensions(final String s, final BufferedImage bufferedImage) {
        if (s.startsWith("/mob/zombie") || s.startsWith("/mob/pigzombie")) {
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            if (width == height * 2) {
                final BufferedImage bufferedImage2 = new BufferedImage(width, height * 2, 2);
                final Graphics2D graphics = bufferedImage2.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics.drawImage(bufferedImage, 0, 0, width, height, null);
                return bufferedImage2;
            }
        }
        return bufferedImage;
    }
    
    public static BufferedImage scaleToPowerOfTwo(final BufferedImage bufferedImage, final int n) {
        if (bufferedImage == null) {
            return bufferedImage;
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(Math.max(width, n));
        if (roundUpToPowerOfTwo == width) {
            return bufferedImage;
        }
        final int n2 = height * roundUpToPowerOfTwo / width;
        final BufferedImage bufferedImage2 = new BufferedImage(roundUpToPowerOfTwo, n2, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        Object o = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (roundUpToPowerOfTwo % width != 0) {
            o = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, o);
        graphics.drawImage(bufferedImage, 0, 0, roundUpToPowerOfTwo, n2, null);
        return bufferedImage2;
    }
    
    public static int twoToPower(final int n) {
        while (0 < n) {
            int n2 = 0;
            ++n2;
        }
        return 1;
    }
    
    public static ITextureObject getTexture(final String s) {
        return getTexture(new ResourceLocation(s));
    }
    
    public static ITextureObject getTexture(final ResourceLocation resourceLocation) {
        final ITextureObject texture = Config.getTextureManager().getTexture(resourceLocation);
        if (texture != null) {
            return texture;
        }
        if (!Config.hasResource(resourceLocation)) {
            return null;
        }
        final SimpleTexture simpleTexture = new SimpleTexture(resourceLocation);
        Config.getTextureManager().loadTexture(resourceLocation, simpleTexture);
        return simpleTexture;
    }
    
    public static void registerResourceListener() {
        final IResourceManager resourceManager = Config.getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager)resourceManager).registerReloadListener(new IResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(final IResourceManager resourceManager) {
                    TextureUtils.resourcesReloaded(resourceManager);
                }
            });
        }
        Config.getTextureManager().loadTickableTexture(new ResourceLocation("optifine/TickableTextures"), new ITickableTextureObject() {
            @Override
            public void tick() {
            }
            
            @Override
            public int getGlTextureId() {
                return 0;
            }
            
            @Override
            public void loadTexture(final IResourceManager resourceManager) throws IOException {
            }
            
            @Override
            public void setBlurMipmap(final boolean b, final boolean b2) {
            }
            
            @Override
            public void restoreLastBlurMipmap() {
            }
        });
    }
    
    public static TextureMap getTextureMapBlocks() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }
    
    public static void refreshBlockTextures() {
    }
    
    public static Dimension getImageSize(final InputStream inputStream, final String s) {
        final Iterator<ImageReader> imageReadersBySuffix = ImageIO.getImageReadersBySuffix(s);
        if (imageReadersBySuffix.hasNext()) {
            final ImageReader imageReader = imageReadersBySuffix.next();
            imageReader.setInput(ImageIO.createImageInputStream(inputStream));
            final Dimension dimension = new Dimension(imageReader.getWidth(imageReader.getMinIndex()), imageReader.getHeight(imageReader.getMinIndex()));
            imageReader.dispose();
            return dimension;
        }
        return null;
    }
    
    public static String fixResourcePath(String s, String string) {
        final String s2 = "assets/minecraft/";
        if (s.startsWith(s2)) {
            s = s.substring(s2.length());
            return s;
        }
        if (s.startsWith("./")) {
            s = s.substring(2);
            if (!string.endsWith("/")) {
                string += "/";
            }
            s = string + s;
            return s;
        }
        final String s3 = "mcpatcher/";
        if (s.startsWith("~/")) {
            s = s.substring(2);
            s = s3 + s;
            return s;
        }
        if (s.startsWith("/")) {
            s = s3 + s.substring(1);
            return s;
        }
        return s;
    }
    
    public static int getPowerOfTwo(final int n) {
        while (1 < n) {
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static {
        TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }
    
    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            GL11.glTexParameterf(3553, 34046, Math.min((float)Config.getAnisotropicFilterLevel(), GL11.glGetFloat(34047)));
        }
    }
    
    public static void update() {
        final TextureMap textureMapBlocks = getTextureMapBlocks();
        if (textureMapBlocks != null) {
            final String s = "minecraft:blocks/";
            TextureUtils.iconGrassTop = textureMapBlocks.getSpriteSafe(s + "grass_top");
            TextureUtils.iconGrassSide = textureMapBlocks.getSpriteSafe(s + "grass_side");
            TextureUtils.iconGrassSideOverlay = textureMapBlocks.getSpriteSafe(s + "grass_side_overlay");
            TextureUtils.iconSnow = textureMapBlocks.getSpriteSafe(s + "snow");
            TextureUtils.iconGrassSideSnowed = textureMapBlocks.getSpriteSafe(s + "grass_side_snowed");
            TextureUtils.iconMyceliumSide = textureMapBlocks.getSpriteSafe(s + "mycelium_side");
            TextureUtils.iconMyceliumTop = textureMapBlocks.getSpriteSafe(s + "mycelium_top");
            TextureUtils.iconWaterStill = textureMapBlocks.getSpriteSafe(s + "water_still");
            TextureUtils.iconWaterFlow = textureMapBlocks.getSpriteSafe(s + "water_flow");
            TextureUtils.iconLavaStill = textureMapBlocks.getSpriteSafe(s + "lava_still");
            TextureUtils.iconLavaFlow = textureMapBlocks.getSpriteSafe(s + "lava_flow");
            TextureUtils.iconFireLayer0 = textureMapBlocks.getSpriteSafe(s + "fire_layer_0");
            TextureUtils.iconFireLayer1 = textureMapBlocks.getSpriteSafe(s + "fire_layer_1");
            TextureUtils.iconPortal = textureMapBlocks.getSpriteSafe(s + "portal");
            TextureUtils.iconGlass = textureMapBlocks.getSpriteSafe(s + "glass");
            TextureUtils.iconGlassPaneTop = textureMapBlocks.getSpriteSafe(s + "glass_pane_top");
        }
    }
}
