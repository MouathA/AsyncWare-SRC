package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;

public abstract class TileEntitySpecialRenderer
{
    protected static final ResourceLocation[] DESTROY_STAGES;
    protected TileEntityRendererDispatcher rendererDispatcher;
    
    public void setRendererDispatcher(final TileEntityRendererDispatcher rendererDispatcher) {
        this.rendererDispatcher = rendererDispatcher;
    }
    
    public FontRenderer getFontRenderer() {
        return this.rendererDispatcher.getFontRenderer();
    }
    
    public boolean func_181055_a() {
        return false;
    }
    
    static {
        DESTROY_STAGES = new ResourceLocation[] { new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png") };
    }
    
    protected World getWorld() {
        return this.rendererDispatcher.worldObj;
    }
    
    protected void bindTexture(final ResourceLocation resourceLocation) {
        final TextureManager renderEngine = this.rendererDispatcher.renderEngine;
        if (renderEngine != null) {
            renderEngine.bindTexture(resourceLocation);
        }
    }
    
    public abstract void renderTileEntityAt(final TileEntity p0, final double p1, final double p2, final double p3, final float p4, final int p5);
}
