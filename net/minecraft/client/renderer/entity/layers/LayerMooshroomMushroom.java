package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerMooshroomMushroom implements LayerRenderer
{
    private final RenderMooshroom mooshroomRenderer;
    
    public void doRenderLayer(final EntityMooshroom entityMooshroom, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entityMooshroom.isChild() && !entityMooshroom.isInvisible()) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.cullFace(1028);
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.2f, 0.35f, 0.5f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.translate(0.1f, 0.0f, -0.6f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            ((ModelQuadruped)this.mooshroomRenderer.getMainModel()).head.postRender(0.0625f);
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.0f, 0.7f, -0.2f);
            GlStateManager.rotate(12.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.cullFace(1029);
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityMooshroom)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public LayerMooshroomMushroom(final RenderMooshroom mooshroomRenderer) {
        this.mooshroomRenderer = mooshroomRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
