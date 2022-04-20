package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderCaveSpider extends RenderSpider
{
    private static final ResourceLocation caveSpiderTextures;
    
    protected void preRenderCallback(final EntityCaveSpider entityCaveSpider, final float n) {
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySpider entitySpider) {
        return this.getEntityTexture((EntityCaveSpider)entitySpider);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityCaveSpider)entityLivingBase, n);
    }
    
    public RenderCaveSpider(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize *= 0.7f;
    }
    
    protected ResourceLocation getEntityTexture(final EntityCaveSpider entityCaveSpider) {
        return RenderCaveSpider.caveSpiderTextures;
    }
    
    static {
        caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCaveSpider)entity);
    }
}
