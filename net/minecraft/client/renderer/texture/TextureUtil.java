package net.minecraft.client.renderer.texture;

import java.nio.*;
import org.apache.logging.log4j.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import javax.imageio.*;
import org.apache.commons.io.*;
import java.io.*;
import org.lwjgl.*;
import java.awt.image.*;
import net.minecraft.client.*;
import optfine.*;

public class TextureUtil
{
    private static final Logger logger;
    private static final IntBuffer dataBuffer;
    private static final int[] mipmapBuffer;
    private static final String __OBFID = "CL_00001067";
    public static final DynamicTexture missingTexture;
    
    public static void uploadTexture(final int n, final int[] array, final int n2, final int n3) {
        bindTexture(n);
        uploadTextureSub(0, array, n2, n3, 0, 0, false, false, false);
    }
    
    private static void setTextureBlurred(final boolean b) {
        setTextureBlurMipmap(b, false);
    }
    
    static {
        logger = LogManager.getLogger();
        dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
        missingTexture = new DynamicTexture(16, 16);
        TextureUtil.missingTextureData = TextureUtil.missingTexture.getTextureData();
        final int[] array = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
        final int[] array2 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
        final int length = array.length;
        while (true) {
            System.arraycopy((0 < length) ? array : array2, 0, TextureUtil.missingTextureData, 0, length);
            System.arraycopy((0 < length) ? array2 : array, 0, TextureUtil.missingTextureData, 0 + length, length);
            int n = 0;
            ++n;
        }
    }
    
    public static void uploadTextureMipmap(final int[][] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        while (0 < array.length) {
            uploadTextureSub(0, array[0], n >> 0, n2 >> 0, n3 >> 0, n4 >> 0, b, b2, array.length > 1);
            int n5 = 0;
            ++n5;
        }
    }
    
    public static void allocateTextureImpl(final int n, final int n2, final int n3, final int n4) {
        deleteTexture(n);
        bindTexture(n);
        if (n2 >= 0) {
            GL11.glTexParameteri(3553, 33085, n2);
            GL11.glTexParameterf(3553, 33082, 0.0f);
            GL11.glTexParameterf(3553, 33083, (float)n2);
            GL11.glTexParameterf(3553, 34049, 0.0f);
        }
        while (0 <= n2) {
            GL11.glTexImage2D(3553, 0, 6408, n3 >> 0, n4 >> 0, 0, 32993, 33639, (IntBuffer)null);
            int n5 = 0;
            ++n5;
        }
    }
    
    private static void uploadTextureImageSubImpl(final BufferedImage bufferedImage, final int n, final int n2, final boolean textureBlurred, final boolean textureClamped) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n3 = 4194304 / width;
        final int[] array = new int[n3 * width];
        setTextureBlurred(textureBlurred);
        setTextureClamped(textureClamped);
        while (0 < width * height) {
            final int n4 = 0 / width;
            final int min = Math.min(n3, height - n4);
            final int n5 = width * min;
            bufferedImage.getRGB(0, n4, width, min, array, 0, width);
            copyToBuffer(array, n5);
            GL11.glTexSubImage2D(3553, 0, n, n2 + n4, width, min, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    private static void setTextureClamped(final boolean b) {
        if (b) {
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
        }
        else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
    }
    
    private static void setTextureBlurMipmap(final boolean b, final boolean b2) {
        if (b) {
            GL11.glTexParameteri(3553, 10241, b2 ? 9987 : 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        else {
            final int mipmapType = Config.getMipmapType();
            GL11.glTexParameteri(3553, 10241, b2 ? mipmapType : 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
    }
    
    static void bindTexture(final int n) {
        GlStateManager.bindTexture(n);
    }
    
    public static int[][] generateMipmapData(final int n, final int n2, final int[][] array) {
        final int[][] array2 = new int[n + 1][];
        array2[0] = array[0];
        if (n > 0) {
            int n3 = 0;
            while (1 < array.length) {
                if (array[0][1] >> 24 == 0) {
                    break;
                }
                ++n3;
            }
            while (1 <= n) {
                if (array[1] != null) {
                    array2[1] = array[1];
                }
                else {
                    final int[] array3 = array2[0];
                    final int[] array4 = new int[array3.length >> 2];
                    final int n4 = n2 >> 1;
                    final int n5 = array4.length / n4;
                    final int n6 = n4 << 1;
                    while (0 < n4) {
                        while (0 < n5) {
                            final int n7 = 2 * (0 + 0 * n6);
                            array4[0 + 0 * n4] = blendColors(array3[n7 + 0], array3[n7 + 1], array3[n7 + 0 + n6], array3[n7 + 1 + n6], true);
                            int n8 = 0;
                            ++n8;
                        }
                        int n9 = 0;
                        ++n9;
                    }
                    array2[1] = array4;
                }
                ++n3;
            }
        }
        return array2;
    }
    
    public static int[] readImageData(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final BufferedImage bufferedImage = readBufferedImage(resourceManager.getResource(resourceLocation).getInputStream());
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] array = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, array, 0, width);
        return array;
    }
    
    public static BufferedImage readBufferedImage(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        IOUtils.closeQuietly(inputStream);
        return read;
    }
    
    public static int glGenTextures() {
        return GlStateManager.generateTexture();
    }
    
    public static void deleteTexture(final int n) {
        GlStateManager.deleteTexture(n);
    }
    
    private static void uploadTextureSub(final int n, final int[] array, final int n2, final int n3, final int n4, final int n5, final boolean b, final boolean textureClamped, final boolean b2) {
        final int n6 = 4194304 / n2;
        setTextureBlurMipmap(b, b2);
        setTextureClamped(textureClamped);
        while (0 < n2 * n3) {
            final int n7 = 0 / n2;
            final int min = Math.min(n6, n3 - n7);
            copyToBufferPos(array, 0, n2 * min);
            GL11.glTexSubImage2D(3553, n, n4, n5 + n7, n2, min, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    public static int[] updateAnaglyph(final int[] array) {
        final int[] array2 = new int[array.length];
        while (0 < array.length) {
            array2[0] = anaglyphColor(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    private static int blendColorComponent(final int n, final int n2, final int n3, final int n4, final int n5) {
        return (int)((float)Math.pow(((float)Math.pow((n >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n2 >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n3 >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n4 >> n5 & 0xFF) / 255.0f, 2.2)) * 0.25, 0.45454545454545453) * 255.0);
    }
    
    public static int uploadTextureImageAllocate(final int n, final BufferedImage bufferedImage, final boolean b, final boolean b2) {
        allocateTexture(n, bufferedImage.getWidth(), bufferedImage.getHeight());
        return uploadTextureImageSub(n, bufferedImage, 0, 0, b, b2);
    }
    
    public static void processPixelValues(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n];
        while (0 < n2 / 2) {
            System.arraycopy(array, 0 * n, array2, 0, n);
            System.arraycopy(array, (n2 - 1 - 0) * n, array, 0 * n, n);
            System.arraycopy(array2, 0, array, (n2 - 1 - 0) * n, n);
            int n3 = 0;
            ++n3;
        }
    }
    
    public static int uploadTextureImageSub(final int n, final BufferedImage bufferedImage, final int n2, final int n3, final boolean b, final boolean b2) {
        bindTexture(n);
        uploadTextureImageSubImpl(bufferedImage, n2, n3, b, b2);
        return n;
    }
    
    public static void saveGlTexture(final String s, final int n, final int n2, final int n3, final int n4) {
        bindTexture(n);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        while (0 <= n2) {
            final File file = new File(s + "_" + 0 + ".png");
            final int n5 = n3 >> 0;
            final int n6 = n4 >> 0;
            final int n7 = n5 * n6;
            final IntBuffer intBuffer = BufferUtils.createIntBuffer(n7);
            final int[] array = new int[n7];
            GL11.glGetTexImage(3553, 0, 32993, 33639, intBuffer);
            intBuffer.get(array);
            final BufferedImage bufferedImage = new BufferedImage(n5, n6, 2);
            bufferedImage.setRGB(0, 0, n5, n6, array, 0, n5);
            ImageIO.write(bufferedImage, "png", file);
            TextureUtil.logger.debug("Exported png to: {}", new Object[] { file.getAbsolutePath() });
            int n8 = 0;
            ++n8;
        }
    }
    
    private static void copyToBufferPos(final int[] array, final int n, final int n2) {
        int[] updateAnaglyph = array;
        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            updateAnaglyph = updateAnaglyph(array);
        }
        TextureUtil.dataBuffer.clear();
        TextureUtil.dataBuffer.put(updateAnaglyph, n, n2);
        TextureUtil.dataBuffer.position(0).limit(n2);
    }
    
    private static void copyToBuffer(final int[] array, final int n) {
        copyToBufferPos(array, 0, n);
    }
    
    private static int blendColors(final int n, final int n2, final int n3, final int n4, final boolean b) {
        return Mipmaps.alphaBlend(n, n2, n3, n4);
    }
    
    public static int anaglyphColor(final int n) {
        final int n2 = n >> 24 & 0xFF;
        final int n3 = n >> 16 & 0xFF;
        final int n4 = n >> 8 & 0xFF;
        final int n5 = n & 0xFF;
        return n2 << 24 | (n3 * 30 + n4 * 59 + n5 * 11) / 100 << 16 | (n3 * 30 + n4 * 70) / 100 << 8 | (n3 * 30 + n5 * 70) / 100;
    }
    
    public static void allocateTexture(final int n, final int n2, final int n3) {
        allocateTextureImpl(n, 0, n2, n3);
    }
    
    public static int uploadTextureImage(final int n, final BufferedImage bufferedImage) {
        return uploadTextureImageAllocate(n, bufferedImage, false, false);
    }
}
