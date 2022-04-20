package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import java.awt.image.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import java.io.*;
import net.minecraft.client.*;
import optfine.*;
import net.minecraft.client.resources.data.*;
import org.apache.logging.log4j.*;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
    private final String basePath;
    private int mipmapLevels;
    private final Map mapUploadedSprites;
    private final List listAnimatedSprites;
    private final IIconCreator iconCreator;
    private final TextureAtlasSprite missingImage;
    private static final Logger logger;
    public static final ResourceLocation locationBlocksTexture;
    public static final ResourceLocation LOCATION_MISSING_TEXTURE;
    private final Map mapRegisteredSprites;
    private static final String __OBFID = "CL_00001058";
    
    private boolean isAbsoluteLocation(final ResourceLocation resourceLocation) {
        return this.isAbsoluteLocationPath(resourceLocation.getResourcePath());
    }
    
    public void updateAnimations() {
        TextureUtil.bindTexture(this.getGlTextureId());
        for (final TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
            if (this != textureAtlasSprite) {
                textureAtlasSprite.updateAnimation();
            }
        }
        if (Config.isMultiTexture()) {
            for (final TextureAtlasSprite textureAtlasSprite2 : this.listAnimatedSprites) {
                if (this != textureAtlasSprite2) {
                    final TextureAtlasSprite spriteSingle = textureAtlasSprite2.spriteSingle;
                    if (spriteSingle == null) {
                        continue;
                    }
                    textureAtlasSprite2.bindSpriteTexture();
                    spriteSingle.updateAnimation();
                }
            }
            TextureUtil.bindTexture(this.getGlTextureId());
        }
    }
    
    public void setMipmapLevels(final int mipmapLevels) {
        this.mipmapLevels = mipmapLevels;
    }
    
    public TextureMap(final String basePath, final IIconCreator iconCreator) {
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new TextureAtlasSprite("missingno");
        this.basePath = basePath;
        this.iconCreator = iconCreator;
    }
    
    public TextureAtlasSprite getSpriteSafe(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    private int[] getMissingImageData(final int n) {
        final BufferedImage bufferedImage = new BufferedImage(16, 16, 2);
        bufferedImage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
        final BufferedImage scaleToPowerOfTwo = TextureUtils.scaleToPowerOfTwo(bufferedImage, n);
        final int[] array = new int[n * n];
        scaleToPowerOfTwo.getRGB(0, 0, n, n, array, 0, n);
        return array;
    }
    
    private int detectMaxMipmapLevel(final Map map, final IResourceManager resourceManager) {
        this.detectMinimumSpriteSize(map, resourceManager, 20);
        MathHelper.roundUpToPowerOfTwo(16);
        MathHelper.calculateLogBaseTwo(16);
        return 4;
    }
    
    private int detectMinimumSpriteSize(final Map map, final IResourceManager resourceManager, final int n) {
        final HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>();
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, 0);
            if (!textureAtlasSprite.hasCustomLoader(resourceManager, resourceLocation)) {
                final IResource resource = resourceManager.getResource(completeResourceLocation);
                if (resource == null) {
                    continue;
                }
                final InputStream inputStream = resource.getInputStream();
                if (inputStream == null) {
                    continue;
                }
                final Dimension imageSize = TextureUtils.getImageSize(inputStream, "png");
                if (imageSize == null) {
                    continue;
                }
                final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(imageSize.width);
                if (!hashMap.containsKey(roundUpToPowerOfTwo)) {
                    hashMap.put(roundUpToPowerOfTwo, 1);
                }
                else {
                    hashMap.put(roundUpToPowerOfTwo, hashMap.get(roundUpToPowerOfTwo) + 1);
                }
            }
        }
        final TreeSet set = new TreeSet<Integer>(hashMap.keySet());
        final Iterator<Integer> iterator2 = (Iterator<Integer>)set.iterator();
        while (iterator2.hasNext()) {
            iterator2.next();
            final int n2 = 0 + hashMap.get(0);
        }
        final int n3 = 0 * n / 100;
        for (final int intValue : set) {
            final int n4 = 0 + hashMap.get(intValue);
            if (intValue > 16) {}
            if (0 > n3) {
                return 16;
            }
        }
        return 16;
    }
    
    public boolean setTextureEntry(final String s, final TextureAtlasSprite textureAtlasSprite) {
        if (!this.mapRegisteredSprites.containsKey(s)) {
            this.mapRegisteredSprites.put(s, textureAtlasSprite);
            if (textureAtlasSprite.getIndexInMap() < 0) {
                textureAtlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
            return true;
        }
        return false;
    }
    
    private boolean isAbsoluteLocationPath(final String s) {
        final String lowerCase = s.toLowerCase();
        return lowerCase.startsWith("mcpatcher/") || lowerCase.startsWith("optifine/");
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.iconCreator != null) {
            this.loadSprites(resourceManager, this.iconCreator);
        }
    }
    
    private void initMissingImage() {
        final int minSpriteSize = this.getMinSpriteSize();
        final int[] missingImageData = this.getMissingImageData(minSpriteSize);
        this.missingImage.setIconWidth(minSpriteSize);
        this.missingImage.setIconHeight(minSpriteSize);
        final int[][] array = new int[this.mipmapLevels + 1][];
        array[0] = missingImageData;
        this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { array }));
        this.missingImage.setIndexInMap(0);
    }
    
    public void loadTextureAtlas(final IResourceManager resourceManager) {
        Config.dbg("Multitexture: " + Config.isMultiTexture());
        if (Config.isMultiTexture()) {
            final Iterator<TextureAtlasSprite> iterator = this.mapUploadedSprites.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().deleteSpriteTexture();
            }
        }
        ConnectedTextures.updateIcons(this);
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        final Stitcher stitcher = new Stitcher(glMaximumTextureSize, glMaximumTextureSize, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this);
        final int minSpriteSize = this.getMinSpriteSize();
        int n = 1 << this.mipmapLevels;
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator2 = this.mapRegisteredSprites.entrySet().iterator();
        while (iterator2.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator2.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, 0);
            if (!textureAtlasSprite.hasCustomLoader(resourceManager, resourceLocation)) {
                final IResource resource = resourceManager.getResource(completeResourceLocation);
                final BufferedImage[] array = new BufferedImage[1 + this.mipmapLevels];
                array[0] = TextureUtil.readBufferedImage(resource.getInputStream());
                if (this.mipmapLevels > 0 && array != null) {
                    final int width = array[0].getWidth();
                    array[0] = TextureUtils.scaleToPowerOfTwo(array[0], minSpriteSize);
                    final int width2 = array[0].getWidth();
                    if (!TextureUtils.isPowerOfTwo(width)) {
                        Config.log("Scaled non power of 2: " + textureAtlasSprite.getIconName() + ", " + width + " -> " + width2);
                    }
                }
                final TextureMetadataSection textureMetadataSection = (TextureMetadataSection)resource.getMetadata("texture");
                if (textureMetadataSection != null) {
                    final List listMipmaps = textureMetadataSection.getListMipmaps();
                    if (!listMipmaps.isEmpty()) {
                        array[0].getWidth();
                        array[0].getHeight();
                        if (MathHelper.roundUpToPowerOfTwo(0) != 0 || MathHelper.roundUpToPowerOfTwo(1) != 1) {
                            throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                        }
                    }
                    final Iterator<Integer> iterator3 = listMipmaps.iterator();
                    while (iterator3.hasNext()) {
                        iterator3.next();
                        if (1 < array.length - 1 && array[1] == null) {
                            array[1] = TextureUtil.readBufferedImage(resourceManager.getResource(this.completeResourceLocation(resourceLocation, 1)).getInputStream());
                        }
                    }
                }
                textureAtlasSprite.loadSprite(array, (AnimationMetadataSection)resource.getMetadata("animation"));
                Math.min(Integer.MAX_VALUE, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                final int min = Math.min(Integer.lowestOneBit(textureAtlasSprite.getIconWidth()), Integer.lowestOneBit(textureAtlasSprite.getIconHeight()));
                if (min < n) {
                    TextureMap.logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { completeResourceLocation, textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight(), MathHelper.calculateLogBaseTwo(n), MathHelper.calculateLogBaseTwo(min) });
                    n = min;
                }
                stitcher.addSprite(textureAtlasSprite);
            }
            else {
                if (textureAtlasSprite.load(resourceManager, resourceLocation)) {
                    continue;
                }
                Math.min(Integer.MAX_VALUE, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                stitcher.addSprite(textureAtlasSprite);
            }
        }
        final int min2 = Math.min(Integer.MAX_VALUE, n);
        MathHelper.calculateLogBaseTwo(min2);
        if (0 < this.mipmapLevels) {
            TextureMap.logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.basePath, this.mipmapLevels, 0, min2 });
            this.mipmapLevels = 0;
        }
        final Iterator<TextureAtlasSprite> iterator4 = this.mapRegisteredSprites.values().iterator();
        while (iterator4.hasNext()) {
            iterator4.next().generateMipmaps(this.mipmapLevels);
        }
        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);
        stitcher.doStitch();
        TextureMap.logger.info("Created: {}x{} {}-atlas", new Object[] { stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), this.basePath });
        TextureUtil.allocateTextureImpl(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        final HashMap hashMap = Maps.newHashMap(this.mapRegisteredSprites);
        for (final TextureAtlasSprite textureAtlasSprite2 : stitcher.getStichSlots()) {
            final String iconName = textureAtlasSprite2.getIconName();
            hashMap.remove(iconName);
            this.mapUploadedSprites.put(iconName, textureAtlasSprite2);
            TextureUtil.uploadTextureMipmap(textureAtlasSprite2.getFrameTextureData(0), textureAtlasSprite2.getIconWidth(), textureAtlasSprite2.getIconHeight(), textureAtlasSprite2.getOriginX(), textureAtlasSprite2.getOriginY(), false, false);
            if (textureAtlasSprite2.hasAnimationMetadata()) {
                this.listAnimatedSprites.add(textureAtlasSprite2);
            }
        }
        final Iterator<TextureAtlasSprite> iterator6 = hashMap.values().iterator();
        while (iterator6.hasNext()) {
            iterator6.next().copyFrom(this.missingImage);
        }
        if (Config.isMultiTexture()) {
            final int currentWidth = stitcher.getCurrentWidth();
            final int currentHeight = stitcher.getCurrentHeight();
            for (final TextureAtlasSprite textureAtlasSprite3 : stitcher.getStichSlots()) {
                textureAtlasSprite3.sheetWidth = currentWidth;
                textureAtlasSprite3.sheetHeight = currentHeight;
                textureAtlasSprite3.mipmapLevels = this.mipmapLevels;
                final TextureAtlasSprite spriteSingle = textureAtlasSprite3.spriteSingle;
                if (spriteSingle != null) {
                    spriteSingle.sheetWidth = currentWidth;
                    spriteSingle.sheetHeight = currentHeight;
                    spriteSingle.mipmapLevels = this.mipmapLevels;
                    textureAtlasSprite3.bindSpriteTexture();
                    TextureUtil.uploadTextureMipmap(spriteSingle.getFrameTextureData(0), spriteSingle.getIconWidth(), spriteSingle.getIconHeight(), spriteSingle.getOriginX(), spriteSingle.getOriginY(), false, true);
                }
            }
            Config.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        }
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
        if (Config.equals("\u7dd8\u7dca\u7ddd\u7dce\u7dff\u7dce\u7dd3\u7ddf\u7dde\u7dd9\u7dce\u7de6\u7dca\u7ddb", "true")) {
            TextureUtil.saveGlTexture(this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
    }
    
    private int getMinSpriteSize() {
        final int n = 1 << this.mipmapLevels;
        return 16;
    }
    
    @Override
    public void tick() {
        this.updateAnimations();
    }
    
    public int getCountRegisteredSprites() {
        return this.mapRegisteredSprites.size();
    }
    
    public void loadSprites(final IResourceManager resourceManager, final IIconCreator iconCreator) {
        this.mapRegisteredSprites.clear();
        iconCreator.registerSprites(this);
        if (this.mipmapLevels >= 4) {
            this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
            Config.log("Mipmap levels: " + this.mipmapLevels);
        }
        this.initMissingImage();
        this.deleteGlTexture();
        this.loadTextureAtlas(resourceManager);
    }
    
    public TextureAtlasSprite getTextureExtry(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    private ResourceLocation completeResourceLocation(final ResourceLocation resourceLocation, final int n) {
        return this.isAbsoluteLocation(resourceLocation) ? ((n == 0) ? new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + ".png") : new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + "mipmap" + n + ".png")) : ((n == 0) ? new ResourceLocation(resourceLocation.getResourceDomain(), String.format("%s/%s%s", this.basePath, resourceLocation.getResourcePath(), ".png")) : new ResourceLocation(resourceLocation.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", this.basePath, resourceLocation.getResourcePath(), n, ".png")));
    }
    
    public TextureAtlasSprite getAtlasSprite(final String s) {
        TextureAtlasSprite missingImage = this.mapUploadedSprites.get(s);
        if (missingImage == null) {
            missingImage = this.missingImage;
        }
        return missingImage;
    }
    
    public TextureAtlasSprite registerSprite(final ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        TextureAtlasSprite atlasSprite = this.mapRegisteredSprites.get(resourceLocation.toString());
        if (atlasSprite == null && Reflector.ModLoader_getCustomAnimationLogic.exists()) {
            atlasSprite = (TextureAtlasSprite)Reflector.call(Reflector.ModLoader_getCustomAnimationLogic, resourceLocation);
        }
        if (atlasSprite == null) {
            atlasSprite = TextureAtlasSprite.makeAtlasSprite(resourceLocation);
            this.mapRegisteredSprites.put(resourceLocation.toString(), atlasSprite);
            if (atlasSprite instanceof TextureAtlasSprite && atlasSprite.getIndexInMap() < 0) {
                atlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
        }
        return atlasSprite;
    }
    
    static {
        logger = LogManager.getLogger();
        LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
        locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
    }
    
    public TextureMap(final String s) {
        this(s, null);
    }
    
    public TextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }
}
