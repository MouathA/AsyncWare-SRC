package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderChicken extends RenderLiving
{
    private static final ResourceLocation chickenTextures;
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntityChicken)entityLivingBase, n);
    }
    
    protected float handleRotationFloat(final EntityChicken entityChicken, final float n) {
        return (MathHelper.sin(entityChicken.field_70888_h + (entityChicken.wingRotation - entityChicken.field_70888_h) * n) + 1.0f) * (entityChicken.field_70884_g + (entityChicken.destPos - entityChicken.field_70884_g) * n);
    }
    
    protected ResourceLocation getEntityTexture(final EntityChicken entityChicken) {
        return RenderChicken.chickenTextures;
    }
    
    public RenderChicken(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityChicken)entity);
    }
    
    static {
        chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    }
}
