package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class LayerWitherAura implements LayerRenderer
{
    private final ModelWither witherModel;
    private static final ResourceLocation WITHER_ARMOR;
    private final RenderWither witherRenderer;
    
    public LayerWitherAura(final RenderWither witherRenderer) {
        this.witherModel = new ModelWither(0.5f);
        this.witherRenderer = witherRenderer;
    }
    
    static {
        WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
    }
    
    public void doRenderLayer(final EntityWither entityWither, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWither.isArmored()) {
            GlStateManager.depthMask(!entityWither.isInvisible());
            this.witherRenderer.bindTexture(LayerWitherAura.WITHER_ARMOR);
            GlStateManager.matrixMode(5890);
            final float n8 = entityWither.ticksExisted + n3;
            GlStateManager.translate(MathHelper.cos(n8 * 0.02f) * 3.0f, n8 * 0.01f, 0.0f);
            GlStateManager.matrixMode(5888);
            final float n9 = 0.5f;
            GlStateManager.color(n9, n9, n9, 1.0f);
            GlStateManager.blendFunc(1, 1);
            this.witherModel.setLivingAnimations(entityWither, n, n2, n3);
            this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
            this.witherModel.render(entityWither, n, n2, n4, n5, n6, n7);
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityWither)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
