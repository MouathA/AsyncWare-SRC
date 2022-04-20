package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class RegionRenderCacheBuilder
{
    private final WorldRenderer[] worldRenderers;
    
    public WorldRenderer getWorldRendererByLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.worldRenderers[enumWorldBlockLayer.ordinal()];
    }
    
    public WorldRenderer getWorldRendererByLayerId(final int n) {
        return this.worldRenderers[n];
    }
    
    public RegionRenderCacheBuilder() {
        (this.worldRenderers = new WorldRenderer[EnumWorldBlockLayer.values().length])[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(2097152);
        this.worldRenderers[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(131072);
        this.worldRenderers[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(131072);
        this.worldRenderers[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(262144);
    }
}
