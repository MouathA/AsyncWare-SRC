package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSpiderEyes implements LayerRenderer
{
    private static final ResourceLocation SPIDER_EYES;
    private final RenderSpider spiderRenderer;
    
    public LayerSpiderEyes(final RenderSpider spiderRenderer) {
        this.spiderRenderer = spiderRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
    }
    
    public void doRenderLayer(final EntitySpider entitySpider, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.spiderRenderer.bindTexture(LayerSpiderEyes.SPIDER_EYES);
        GlStateManager.blendFunc(1, 1);
        if (entitySpider.isInvisible()) {
            GlStateManager.depthMask(false);
        }
        else {
            GlStateManager.depthMask(true);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.spiderRenderer.getMainModel().render(entitySpider, n, n2, n4, n5, n6, n7);
        entitySpider.getBrightnessForRender(n3);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        this.spiderRenderer.func_177105_a(entitySpider, n3);
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySpider)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
