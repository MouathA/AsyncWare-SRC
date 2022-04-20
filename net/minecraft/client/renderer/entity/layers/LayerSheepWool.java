package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSheepWool implements LayerRenderer
{
    private final RenderSheep sheepRenderer;
    private final ModelSheep1 sheepModel;
    private static final ResourceLocation TEXTURE;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySheep)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public LayerSheepWool(final RenderSheep sheepRenderer) {
        this.sheepModel = new ModelSheep1();
        this.sheepRenderer = sheepRenderer;
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    public void doRenderLayer(final EntitySheep entitySheep, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySheep.getSheared() && !entitySheep.isInvisible()) {
            this.sheepRenderer.bindTexture(LayerSheepWool.TEXTURE);
            if (entitySheep.hasCustomName() && "jeb_".equals(entitySheep.getCustomNameTag())) {
                final int n8 = entitySheep.ticksExisted / 25 + entitySheep.getEntityId();
                final int length = EnumDyeColor.values().length;
                final int n9 = n8 % length;
                final int n10 = (n8 + 1) % length;
                final float n11 = (entitySheep.ticksExisted % 25 + n3) / 25.0f;
                final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(n9));
                final float[] func_175513_a2 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(n10));
                GlStateManager.color(func_175513_a[0] * (1.0f - n11) + func_175513_a2[0] * n11, func_175513_a[1] * (1.0f - n11) + func_175513_a2[1] * n11, func_175513_a[2] * (1.0f - n11) + func_175513_a2[2] * n11);
            }
            else {
                final float[] func_175513_a3 = EntitySheep.func_175513_a(entitySheep.getFleeceColor());
                GlStateManager.color(func_175513_a3[0], func_175513_a3[1], func_175513_a3[2]);
            }
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitySheep, n, n2, n3);
            this.sheepModel.render(entitySheep, n, n2, n4, n5, n6, n7);
        }
    }
}
