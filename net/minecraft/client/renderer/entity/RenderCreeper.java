package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class RenderCreeper extends RenderLiving
{
    private static final ResourceLocation creeperTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCreeper)entity);
    }
    
    public RenderCreeper(final RenderManager renderManager) {
        super(renderManager, new ModelCreeper(), 0.5f);
        this.addLayer(new LayerCreeperCharge(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityCreeper entityCreeper) {
        return RenderCreeper.creeperTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityCreeper)entityLivingBase, n);
    }
    
    protected void preRenderCallback(final EntityCreeper entityCreeper, final float n) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n);
        final float n2 = 1.0f + MathHelper.sin(creeperFlashIntensity * 100.0f) * creeperFlashIntensity * 0.01f;
        final float clamp_float = MathHelper.clamp_float(creeperFlashIntensity, 0.0f, 1.0f);
        final float n3 = clamp_float * clamp_float;
        final float n4 = n3 * n3;
        final float n5 = (1.0f + n4 * 0.4f) * n2;
        GlStateManager.scale(n5, (1.0f + n4 * 0.1f) / n2, n5);
    }
    
    static {
        creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
    
    @Override
    protected int getColorMultiplier(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        return this.getColorMultiplier((EntityCreeper)entityLivingBase, n, n2);
    }
    
    protected int getColorMultiplier(final EntityCreeper entityCreeper, final float n, final float n2) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n2);
        if ((int)(creeperFlashIntensity * 10.0f) % 2 == 0) {
            return 0;
        }
        return MathHelper.clamp_int((int)(creeperFlashIntensity * 0.2f * 255.0f), 0, 255) << 24 | 0xFFFFFF;
    }
}
