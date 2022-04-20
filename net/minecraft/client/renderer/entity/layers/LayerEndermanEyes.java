package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEndermanEyes implements LayerRenderer
{
    private static final ResourceLocation field_177203_a;
    private final RenderEnderman endermanRenderer;
    
    static {
        field_177203_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    public LayerEndermanEyes(final RenderEnderman endermanRenderer) {
        this.endermanRenderer = endermanRenderer;
    }
    
    public void doRenderLayer(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.endermanRenderer.bindTexture(LayerEndermanEyes.field_177203_a);
        GlStateManager.blendFunc(1, 1);
        GlStateManager.depthMask(!entityEnderman.isInvisible());
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.endermanRenderer.getMainModel().render(entityEnderman, n, n2, n4, n5, n6, n7);
        this.endermanRenderer.func_177105_a(entityEnderman, n3);
        GlStateManager.depthMask(true);
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
