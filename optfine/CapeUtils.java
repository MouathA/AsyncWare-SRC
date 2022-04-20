package optfine;

import net.minecraft.client.entity.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import java.awt.*;

public class CapeUtils
{
    public static void downloadCape(final AbstractClientPlayer abstractClientPlayer) {
        final String name = abstractClientPlayer.getName();
        if (name != null && !name.isEmpty()) {
            final String string = "http://s.optifine.net/capes/" + name + ".png";
            FilenameUtils.getBaseName(string);
            final ResourceLocation resourceLocation = new ResourceLocation("asyncware/cape.png");
            final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject texture = textureManager.getTexture(resourceLocation);
            if (texture != null && texture instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texture;
                if (threadDownloadImageData.imageFound != null) {
                    threadDownloadImageData.imageFound;
                    return;
                }
            }
            textureManager.loadTexture(resourceLocation, new ThreadDownloadImageData(null, string, null, new IImageBuffer() {
                ImageBufferDownload ibd = new ImageBufferDownload();
                
                @Override
                public void skinAvailable() {
                }
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
                    return CapeUtils.parseCape(bufferedImage);
                }
            }));
        }
    }
    
    public static BufferedImage parseCape(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        while (64 < width || 32 < height) {}
        final BufferedImage bufferedImage2 = new BufferedImage(64, 32, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }
}
