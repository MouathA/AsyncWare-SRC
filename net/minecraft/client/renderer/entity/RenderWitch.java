package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderWitch extends RenderLiving
{
    private static final ResourceLocation witchTextures;
    
    protected ResourceLocation getEntityTexture(final EntityWitch entityWitch) {
        return RenderWitch.witchTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWitch)entity);
    }
    
    protected void preRenderCallback(final EntityWitch entityWitch, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
    }
    
    public RenderWitch(final RenderManager renderManager) {
        super(renderManager, new ModelWitch(0.0f), 0.5f);
        this.addLayer(new LayerHeldItemWitch(this));
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityWitch)entityLivingBase, n);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitch)entityLiving, n, n2, n3, n4, n5);
    }
    
    public void doRender(final EntityWitch entityWitch, final double n, final double n2, final double n3, final float n4, final float n5) {
        ((ModelWitch)this.mainModel).field_82900_g = (entityWitch.getHeldItem() != null);
        super.doRender(entityWitch, n, n2, n3, n4, n5);
    }
    
    static {
        witchTextures = new ResourceLocation("textures/entity/witch.png");
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitch)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitch)entityLivingBase, n, n2, n3, n4, n5);
    }
}
