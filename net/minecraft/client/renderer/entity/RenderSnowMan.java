package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderSnowMan extends RenderLiving
{
    private static final ResourceLocation snowManTextures;
    
    @Override
    public ModelSnowMan getMainModel() {
        return (ModelSnowMan)super.getMainModel();
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    protected ResourceLocation getEntityTexture(final EntitySnowman entitySnowman) {
        return RenderSnowMan.snowManTextures;
    }
    
    static {
        snowManTextures = new ResourceLocation("textures/entity/snowman.png");
    }
    
    public RenderSnowMan(final RenderManager renderManager) {
        super(renderManager, new ModelSnowMan(), 0.5f);
        this.addLayer(new LayerSnowmanHead(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySnowman)entity);
    }
}
