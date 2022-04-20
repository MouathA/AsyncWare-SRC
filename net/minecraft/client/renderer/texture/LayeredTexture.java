package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class LayeredTexture extends AbstractTexture
{
    public final List layeredTextureNames;
    private static final Logger logger;
    
    public LayeredTexture(final String... array) {
        this.layeredTextureNames = Lists.newArrayList((Object[])array);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        BufferedImage bufferedImage = null;
        for (final String s : this.layeredTextureNames) {
            if (s != null) {
                final BufferedImage bufferedImage2 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                if (bufferedImage == null) {
                    bufferedImage = new BufferedImage(bufferedImage2.getWidth(), bufferedImage2.getHeight(), 2);
                }
                bufferedImage.getGraphics().drawImage(bufferedImage2, 0, 0, null);
            }
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
