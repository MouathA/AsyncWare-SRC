package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderSpider extends RenderLiving
{
    private static final ResourceLocation spiderTextures;
    
    protected ResourceLocation getEntityTexture(final EntitySpider entitySpider) {
        return RenderSpider.spiderTextures;
    }
    
    protected float getDeathMaxRotation(final EntitySpider entitySpider) {
        return 180.0f;
    }
    
    static {
        spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntitySpider)entityLivingBase);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySpider)entity);
    }
    
    public RenderSpider(final RenderManager renderManager) {
        super(renderManager, new ModelSpider(), 1.0f);
        this.addLayer(new LayerSpiderEyes(this));
    }
}
