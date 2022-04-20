package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderBat extends RenderLiving
{
    private static final ResourceLocation batTextures;
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityBat)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityBat)entity);
    }
    
    public RenderBat(final RenderManager renderManager) {
        super(renderManager, new ModelBat(), 0.25f);
    }
    
    static {
        batTextures = new ResourceLocation("textures/entity/bat.png");
    }
    
    protected void preRenderCallback(final EntityBat entityBat, final float n) {
        GlStateManager.scale(0.35f, 0.35f, 0.35f);
    }
    
    protected void rotateCorpse(final EntityBat entityBat, final float n, final float n2, final float n3) {
        if (!entityBat.getIsBatHanging()) {
            GlStateManager.translate(0.0f, MathHelper.cos(n * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GlStateManager.translate(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(entityBat, n, n2, n3);
    }
    
    protected ResourceLocation getEntityTexture(final EntityBat entityBat) {
        return RenderBat.batTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityBat)entityLivingBase, n);
    }
}
