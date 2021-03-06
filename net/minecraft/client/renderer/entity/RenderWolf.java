package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderWolf extends RenderLiving
{
    private static final ResourceLocation anrgyWolfTextures;
    private static final ResourceLocation tamedWolfTextures;
    private static final ResourceLocation wolfTextures;
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWolf)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntityWolf)entityLivingBase, n);
    }
    
    static {
        wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
        tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
        anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
    }
    
    protected ResourceLocation getEntityTexture(final EntityWolf entityWolf) {
        return entityWolf.isTamed() ? RenderWolf.tamedWolfTextures : (entityWolf.isAngry() ? RenderWolf.anrgyWolfTextures : RenderWolf.wolfTextures);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWolf)entity);
    }
    
    public void doRender(final EntityWolf entityWolf, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (entityWolf.isWolfWet()) {
            final float n6 = entityWolf.getBrightness(n5) * entityWolf.getShadingWhileWet(n5);
            GlStateManager.color(n6, n6, n6);
        }
        super.doRender(entityWolf, n, n2, n3, n4, n5);
    }
    
    public RenderWolf(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerWolfCollar(this));
    }
    
    protected float handleRotationFloat(final EntityWolf entityWolf, final float n) {
        return entityWolf.getTailRotation();
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWolf)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWolf)entityLivingBase, n, n2, n3, n4, n5);
    }
}
