package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;

public class LayerHeldBlock implements LayerRenderer
{
    private final RenderEnderman endermanRenderer;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public LayerHeldBlock(final RenderEnderman endermanRenderer) {
        this.endermanRenderer = endermanRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    public void doRenderLayer(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final IBlockState heldBlockState = entityEnderman.getHeldBlockState();
        if (heldBlockState.getBlock().getMaterial() != Material.air) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.translate(0.0f, 0.6875f, -0.75f);
            GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.25f, 0.1875f, 0.25f);
            final float n8 = 0.5f;
            GlStateManager.scale(-n8, -n8, n8);
            final int brightnessForRender = entityEnderman.getBrightnessForRender(n3);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.endermanRenderer.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.renderBlockBrightness(heldBlockState, 1.0f);
        }
    }
}
