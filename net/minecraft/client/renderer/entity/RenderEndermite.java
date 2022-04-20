package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderEndermite extends RenderLiving
{
    private static final ResourceLocation ENDERMITE_TEXTURES;
    
    static {
        ENDERMITE_TEXTURES = new ResourceLocation("textures/entity/endermite.png");
    }
    
    public RenderEndermite(final RenderManager renderManager) {
        super(renderManager, new ModelEnderMite(), 0.3f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityEndermite)entity);
    }
    
    protected float getDeathMaxRotation(final EntityEndermite entityEndermite) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntityEndermite entityEndermite) {
        return RenderEndermite.ENDERMITE_TEXTURES;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntityEndermite)entityLivingBase);
    }
}
