package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerWolfCollar implements LayerRenderer
{
    private static final ResourceLocation WOLF_COLLAR;
    private final RenderWolf wolfRenderer;
    
    static {
        WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    public LayerWolfCollar(final RenderWolf wolfRenderer) {
        this.wolfRenderer = wolfRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityWolf)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final EntityWolf entityWolf, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWolf.isTamed() && !entityWolf.isInvisible()) {
            this.wolfRenderer.bindTexture(LayerWolfCollar.WOLF_COLLAR);
            final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(entityWolf.getCollarColor().getMetadata()));
            GlStateManager.color(func_175513_a[0], func_175513_a[1], func_175513_a[2]);
            this.wolfRenderer.getMainModel().render(entityWolf, n, n2, n4, n5, n6, n7);
        }
    }
}
