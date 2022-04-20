package net.minecraft.client.renderer.texture;

import java.util.*;
import net.minecraft.client.resources.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class LayeredColorMaskTexture extends AbstractTexture
{
    private final List field_174950_i;
    private static final Logger LOG;
    private final ResourceLocation textureLocation;
    private final List field_174949_h;
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        final BufferedImage bufferedImage = TextureUtil.readBufferedImage(resourceManager.getResource(this.textureLocation).getInputStream());
        bufferedImage.getType();
        final BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 6);
        bufferedImage2.getGraphics().drawImage(bufferedImage, 0, 0, null);
        while (0 < this.field_174949_h.size() && 0 < this.field_174950_i.size()) {
            final String s = this.field_174949_h.get(0);
            final MapColor mapColor = this.field_174950_i.get(0).getMapColor();
            if (s != null) {
                final BufferedImage bufferedImage3 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                if (bufferedImage3.getWidth() == bufferedImage2.getWidth() && bufferedImage3.getHeight() == bufferedImage2.getHeight() && bufferedImage3.getType() == 6) {
                    while (0 < bufferedImage3.getHeight()) {
                        while (0 < bufferedImage3.getWidth()) {
                            final int rgb = bufferedImage3.getRGB(0, 0);
                            if ((rgb & 0xFF000000) != 0x0) {
                                bufferedImage3.setRGB(0, 0, ((rgb & 0xFF0000) << 8 & 0xFF000000) | (MathHelper.func_180188_d(bufferedImage.getRGB(0, 0), mapColor.colorValue) & 0xFFFFFF));
                            }
                            int n = 0;
                            ++n;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    bufferedImage2.getGraphics().drawImage(bufferedImage3, 0, 0, null);
                }
            }
            int n3 = 0;
            ++n3;
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage2);
    }
    
    static {
        LOG = LogManager.getLogger();
    }
    
    public LayeredColorMaskTexture(final ResourceLocation textureLocation, final List field_174949_h, final List field_174950_i) {
        this.textureLocation = textureLocation;
        this.field_174949_h = field_174949_h;
        this.field_174950_i = field_174950_i;
    }
}
