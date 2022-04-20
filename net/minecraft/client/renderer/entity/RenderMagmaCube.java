package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderMagmaCube extends RenderLiving
{
    private static final ResourceLocation magmaCubeTextures;
    
    protected void preRenderCallback(final EntityMagmaCube entityMagmaCube, final float n) {
        final int slimeSize = entityMagmaCube.getSlimeSize();
        final float n2 = 1.0f / ((entityMagmaCube.prevSquishFactor + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * n) / (slimeSize * 0.5f + 1.0f) + 1.0f);
        final float n3 = (float)slimeSize;
        GlStateManager.scale(n2 * n3, 1.0f / n2 * n3, n2 * n3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityMagmaCube)entityLivingBase, n);
    }
    
    static {
        magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
    }
    
    public RenderMagmaCube(final RenderManager renderManager) {
        super(renderManager, new ModelMagmaCube(), 0.25f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMagmaCube)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntityMagmaCube entityMagmaCube) {
        return RenderMagmaCube.magmaCubeTextures;
    }
}
