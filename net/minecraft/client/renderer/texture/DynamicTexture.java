package net.minecraft.client.renderer.texture;

import java.awt.image.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class DynamicTexture extends AbstractTexture
{
    private final int height;
    private final int[] dynamicTextureData;
    private final int width;
    
    public DynamicTexture(final BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
        this.updateDynamicTexture();
    }
    
    public void updateDynamicTexture() {
        TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
    }
    
    public int[] getTextureData() {
        return this.dynamicTextureData;
    }
    
    public DynamicTexture(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.dynamicTextureData = new int[width * height];
        TextureUtil.allocateTexture(this.getGlTextureId(), width, height);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
    }
}
