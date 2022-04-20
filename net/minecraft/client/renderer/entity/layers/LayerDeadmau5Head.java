package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerDeadmau5Head implements LayerRenderer
{
    private final RenderPlayer playerRenderer;
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!abstractClientPlayer.getName().equals("deadmau5") || !abstractClientPlayer.hasSkin() || abstractClientPlayer.isInvisible()) {
            return;
        }
        this.playerRenderer.bindTexture(abstractClientPlayer.getLocationSkin());
        while (true) {
            final float n8 = abstractClientPlayer.prevRotationYaw + (abstractClientPlayer.rotationYaw - abstractClientPlayer.prevRotationYaw) * n3 - (abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * n3);
            final float n9 = abstractClientPlayer.prevRotationPitch + (abstractClientPlayer.rotationPitch - abstractClientPlayer.prevRotationPitch) * n3;
            GlStateManager.rotate(n8, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(n9, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.375f * -1, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.375f, 0.0f);
            GlStateManager.rotate(-n9, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-n8, 0.0f, 1.0f, 0.0f);
            final float n10 = 1.3333334f;
            GlStateManager.scale(n10, n10, n10);
            this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625f);
            int n11 = 0;
            ++n11;
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public LayerDeadmau5Head(final RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
}
