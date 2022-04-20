package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSkeleton extends RenderBiped
{
    private static final ResourceLocation skeletonTextures;
    private static final ResourceLocation witherSkeletonTextures;
    
    protected ResourceLocation getEntityTexture(final EntitySkeleton entitySkeleton) {
        return (entitySkeleton.getSkeletonType() == 1) ? RenderSkeleton.witherSkeletonTextures : RenderSkeleton.skeletonTextures;
    }
    
    public RenderSkeleton(final RenderManager renderManager) {
        super(renderManager, new ModelSkeleton(), 0.5f);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this, this) {
            final RenderSkeleton this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = new ModelSkeleton(0.5f, true);
                this.field_177186_d = new ModelSkeleton(1.0f, true);
            }
        });
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySkeleton)entity);
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntitySkeleton)entityLivingBase, n);
    }
    
    static {
        skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }
    
    protected void preRenderCallback(final EntitySkeleton entitySkeleton, final float n) {
        if (entitySkeleton.getSkeletonType() == 1) {
            GlStateManager.scale(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntitySkeleton)entityLiving);
    }
}
