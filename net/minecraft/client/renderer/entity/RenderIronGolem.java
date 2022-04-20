package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class RenderIronGolem extends RenderLiving
{
    private static final ResourceLocation ironGolemTextures;
    
    public RenderIronGolem(final RenderManager renderManager) {
        super(renderManager, new ModelIronGolem(), 0.5f);
        this.addLayer(new LayerIronGolemFlower(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityIronGolem)entity);
    }
    
    protected void rotateCorpse(final EntityIronGolem entityIronGolem, final float n, final float n2, final float n3) {
        super.rotateCorpse(entityIronGolem, n, n2, n3);
        if (entityIronGolem.limbSwingAmount >= 0.01) {
            final float n4 = 13.0f;
            GlStateManager.rotate(6.5f * ((Math.abs((entityIronGolem.limbSwing - entityIronGolem.limbSwingAmount * (1.0f - n3) + 6.0f) % n4 - n4 * 0.5f) - n4 * 0.25f) / (n4 * 0.25f)), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityIronGolem entityIronGolem) {
        return RenderIronGolem.ironGolemTextures;
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityIronGolem)entityLivingBase, n, n2, n3);
    }
    
    static {
        ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
    }
}
