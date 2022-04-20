package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation LOC_GRASS_PNG;
    
    static {
        LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, GrassColorReloadListener.LOC_GRASS_PNG));
    }
}
