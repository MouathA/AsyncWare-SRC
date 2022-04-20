package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class LayerSaddle implements LayerRenderer
{
    private final ModelPig pigModel;
    private static final ResourceLocation TEXTURE;
    private final RenderPig pigRenderer;
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityPig)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final EntityPig entityPig, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityPig.getSaddled()) {
            this.pigRenderer.bindTexture(LayerSaddle.TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entityPig, n, n2, n4, n5, n6, n7);
        }
    }
    
    public LayerSaddle(final RenderPig pigRenderer) {
        this.pigModel = new ModelPig(0.5f);
        this.pigRenderer = pigRenderer;
    }
}
