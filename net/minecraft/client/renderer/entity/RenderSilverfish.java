package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderSilverfish extends RenderLiving
{
    private static final ResourceLocation silverfishTextures;
    
    static {
        silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntitySilverfish)entityLivingBase);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySilverfish)entity);
    }
    
    public RenderSilverfish(final RenderManager renderManager) {
        super(renderManager, new ModelSilverfish(), 0.3f);
    }
    
    protected float getDeathMaxRotation(final EntitySilverfish entitySilverfish) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntitySilverfish entitySilverfish) {
        return RenderSilverfish.silverfishTextures;
    }
}
