package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class LayerEnderDragonDeath implements LayerRenderer
{
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityDragon.deathTicks > 0) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final float n8 = (entityDragon.deathTicks + n3) / 200.0f;
            float n9 = 0.0f;
            if (n8 > 0.8f) {
                n9 = (n8 - 0.8f) / 0.2f;
            }
            final Random random = new Random(432L);
            GlStateManager.shadeModel(7425);
            GlStateManager.blendFunc(770, 1);
            GlStateManager.depthMask(false);
            GlStateManager.translate(0.0f, -1.0f, -2.0f);
            while (0 < (n8 + n8 * n8) / 2.0f * 60.0f) {
                GlStateManager.rotate(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f + n8 * 90.0f, 0.0f, 0.0f, 1.0f);
                final float n10 = random.nextFloat() * 20.0f + 5.0f + n9 * 10.0f;
                final float n11 = random.nextFloat() * 2.0f + 1.0f + n9 * 2.0f;
                worldRenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos(0.0, 0.0, 0.0).color(255, 255, 255, (int)(255.0f * (1.0f - n9))).endVertex();
                worldRenderer.pos(-0.866 * n11, n10, -0.5f * n11).color(255, 0, 255, 0).endVertex();
                worldRenderer.pos(0.866 * n11, n10, -0.5f * n11).color(255, 0, 255, 0).endVertex();
                worldRenderer.pos(0.0, n10, 1.0f * n11).color(255, 0, 255, 0).endVertex();
                worldRenderer.pos(-0.866 * n11, n10, -0.5f * n11).color(255, 0, 255, 0).endVertex();
                instance.draw();
                int n12 = 0;
                ++n12;
            }
            GlStateManager.depthMask(true);
            GlStateManager.shadeModel(7424);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
