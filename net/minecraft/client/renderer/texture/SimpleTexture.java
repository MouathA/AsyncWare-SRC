package net.minecraft.client.renderer.texture;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.resources.*;
import java.awt.image.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class SimpleTexture extends AbstractTexture
{
    private static final Logger logger;
    protected final ResourceLocation textureLocation;
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        final IResource resource = resourceManager.getResource(this.textureLocation);
        final InputStream inputStream = resource.getInputStream();
        final BufferedImage bufferedImage = TextureUtil.readBufferedImage(inputStream);
        if (resource.hasMetadata()) {
            final TextureMetadataSection textureMetadataSection = (TextureMetadataSection)resource.getMetadata("texture");
            if (textureMetadataSection != null) {
                textureMetadataSection.getTextureBlur();
                textureMetadataSection.getTextureClamp();
            }
        }
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedImage, false, false);
        if (inputStream != null) {
            inputStream.close();
        }
    }
    
    public SimpleTexture(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
