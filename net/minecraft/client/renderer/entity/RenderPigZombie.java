package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderPigZombie extends RenderBiped
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntityPigZombie)entityLiving);
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");
    }
    
    protected ResourceLocation getEntityTexture(final EntityPigZombie entityPigZombie) {
        return RenderPigZombie.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityPigZombie)entity);
    }
    
    public RenderPigZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this, this) {
            final RenderPigZombie this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
        });
    }
}
