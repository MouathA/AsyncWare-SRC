package optfine;

import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.client.resources.*;
import javax.imageio.*;
import java.io.*;
import java.util.zip.*;
import java.util.*;

public class TextureAnimations
{
    private static TextureAnimation[] textureAnimations;
    
    public static String[] getAnimationPropertiesDir(final File file) {
        final File file2 = new File(file, "anim");
        if (!file2.exists()) {
            return null;
        }
        if (!file2.isDirectory()) {
            return null;
        }
        final File[] listFiles = file2.listFiles();
        if (listFiles == null) {
            return null;
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < listFiles.length) {
            final File file3 = listFiles[0];
            final String name = file3.getName();
            if (!name.startsWith("custom_") && name.endsWith(".properties") && file3.isFile() && file3.canRead()) {
                Config.dbg("TextureAnimation: anim/" + file3.getName());
                list.add("/anim/" + name);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static byte[] getCustomTextureData(final String s, final int n) {
        byte[] array = loadImage(s, n);
        if (array == null) {
            array = loadImage("/anim" + s, n);
        }
        return array;
    }
    
    static {
        TextureAnimations.textureAnimations = null;
    }
    
    private static byte[] loadImage(final String s, final int n) {
        final GameSettings gameSettings = Config.getGameSettings();
        final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
        if (resourceStream == null) {
            return null;
        }
        BufferedImage bufferedImage = readTextureImage(resourceStream);
        if (bufferedImage == null) {
            return null;
        }
        if (n > 0 && bufferedImage.getWidth() != n) {
            bufferedImage = scaleBufferedImage(bufferedImage, n, (int)(n * (double)(bufferedImage.getHeight() / bufferedImage.getWidth())));
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] array = new int[width * height];
        final byte[] array2 = new byte[width * height * 4];
        bufferedImage.getRGB(0, 0, width, height, array, 0, width);
        while (0 < array.length) {
            final int n2 = array[0] >> 24 & 0xFF;
            int n3 = array[0] >> 16 & 0xFF;
            int n4 = array[0] >> 8 & 0xFF;
            int n5 = array[0] & 0xFF;
            if (gameSettings != null && gameSettings.anaglyph) {
                final int n6 = (n3 * 30 + n4 * 59 + n5 * 11) / 100;
                final int n7 = (n3 * 30 + n4 * 70) / 100;
                final int n8 = (n3 * 30 + n5 * 70) / 100;
                n3 = n6;
                n4 = n7;
                n5 = n8;
            }
            array2[0] = (byte)n3;
            array2[1] = (byte)n4;
            array2[2] = (byte)n5;
            array2[3] = (byte)n2;
            int n9 = 0;
            ++n9;
        }
        return array2;
    }
    
    public static void updateCustomAnimations() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnull          9
        //     6: invokestatic    optfine/Config.isAnimatedTextures:()Z
        //     9: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0009 (coming from #0006).
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
    
    public static TextureAnimation makeTextureAnimation(final Properties properties, final ResourceLocation resourceLocation) {
        final String property = properties.getProperty("from");
        final String property2 = properties.getProperty("to");
        final int int1 = Config.parseInt(properties.getProperty("x"), -1);
        final int int2 = Config.parseInt(properties.getProperty("y"), -1);
        final int int3 = Config.parseInt(properties.getProperty("w"), -1);
        final int int4 = Config.parseInt(properties.getProperty("h"), -1);
        if (property == null || property2 == null) {
            Config.warn("TextureAnimation: Source or target texture not specified");
            return null;
        }
        if (int1 < 0 || int2 < 0 || int3 < 0 || int4 < 0) {
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        final String basePath = TextureUtils.getBasePath(resourceLocation.getResourcePath());
        final String fixResourcePath = TextureUtils.fixResourcePath(property, basePath);
        final String fixResourcePath2 = TextureUtils.fixResourcePath(property2, basePath);
        final byte[] customTextureData = getCustomTextureData(fixResourcePath, int3);
        if (customTextureData == null) {
            Config.warn("TextureAnimation: Source texture not found: " + fixResourcePath2);
            return null;
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(fixResourcePath2);
        if (!Config.hasResource(resourceLocation2)) {
            Config.warn("TextureAnimation: Target texture not found: " + fixResourcePath2);
            return null;
        }
        return new TextureAnimation(fixResourcePath, customTextureData, fixResourcePath2, resourceLocation2, int1, int2, int3, int4, properties, 1);
    }
    
    public static TextureAnimation[] getTextureAnimations(final IResourcePack[] array) {
        final ArrayList list = new ArrayList();
        while (0 < array.length) {
            final TextureAnimation[] textureAnimations = getTextureAnimations(array[0]);
            if (textureAnimations != null) {
                list.addAll(Arrays.asList(textureAnimations));
            }
            int n = 0;
            ++n;
        }
        return (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
    }
    
    public static void updateAnimations() {
        if (TextureAnimations.textureAnimations != null) {
            while (0 < TextureAnimations.textureAnimations.length) {
                TextureAnimations.textureAnimations[0].updateTexture();
                int n = 0;
                ++n;
            }
        }
    }
    
    public static void update() {
        TextureAnimations.textureAnimations = null;
        TextureAnimations.textureAnimations = getTextureAnimations(Config.getResourcePacks());
        Config.isAnimatedTextures();
    }
    
    public static BufferedImage scaleBufferedImage(final BufferedImage bufferedImage, final int n, final int n2) {
        final BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(bufferedImage, 0, 0, n, n2, null);
        return bufferedImage2;
    }
    
    public static TextureAnimation[] getTextureAnimations(final IResourcePack resourcePack) {
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return null;
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        if (resourcePackFile == null) {
            return null;
        }
        if (!resourcePackFile.exists()) {
            return null;
        }
        String[] array;
        if (resourcePackFile.isFile()) {
            array = getAnimationPropertiesZip(resourcePackFile);
        }
        else {
            array = getAnimationPropertiesDir(resourcePackFile);
        }
        if (array == null) {
            return null;
        }
        final ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        while (0 < array.length) {
            final String s = array[0];
            Config.dbg("Texture animation: " + s);
            final ResourceLocation resourceLocation = new ResourceLocation(s);
            final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
            final Properties properties = new Properties();
            properties.load(inputStream);
            final TextureAnimation textureAnimation = makeTextureAnimation(properties, resourceLocation);
            if (textureAnimation != null) {
                if (Config.getDefiningResourcePack(new ResourceLocation(textureAnimation.getDstTex())) != resourcePack) {
                    Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
                }
                else {
                    list.add(textureAnimation);
                }
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new TextureAnimation[list.size()]);
    }
    
    public static void reset() {
        TextureAnimations.textureAnimations = null;
    }
    
    private static BufferedImage readTextureImage(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        inputStream.close();
        return read;
    }
    
    public static String[] getAnimationPropertiesZip(final File file) {
        final Enumeration<? extends ZipEntry> entries = new ZipFile(file).entries();
        final ArrayList<String> list = new ArrayList<String>();
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith("assets/minecraft/mcpatcher/anim/") && !name.startsWith("assets/minecraft/mcpatcher/anim/custom_") && name.endsWith(".properties")) {
                list.add(name.substring("assets/minecraft/".length()));
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
