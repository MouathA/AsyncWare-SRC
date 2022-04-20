package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEnderDragonEyes implements LayerRenderer
{
    private static final ResourceLocation TEXTURE;
    private final RenderDragon dragonRenderer;
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.dragonRenderer.bindTexture(LayerEnderDragonEyes.TEXTURE);
        GlStateManager.blendFunc(1, 1);
        GlStateManager.depthFunc(514);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.dragonRenderer.getMainModel().render(entityDragon, n, n2, n4, n5, n6, n7);
        this.dragonRenderer.func_177105_a(entityDragon, n3);
        GlStateManager.depthFunc(515);
    }
    
    public LayerEnderDragonEyes(final RenderDragon dragonRenderer) {
        this.dragonRenderer = dragonRenderer;
    }
}
