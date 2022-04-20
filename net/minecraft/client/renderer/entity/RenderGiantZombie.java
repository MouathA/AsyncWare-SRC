package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;

public class RenderGiantZombie extends RenderLiving
{
    private float scale;
    private static final ResourceLocation zombieTextures;
    
    protected ResourceLocation getEntityTexture(final EntityGiantZombie entityGiantZombie) {
        return RenderGiantZombie.zombieTextures;
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    static {
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGiantZombie)entity);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGiantZombie)entityLivingBase, n);
    }
    
    public RenderGiantZombie(final RenderManager renderManager, final ModelBase modelBase, final float n, final float scale) {
        super(renderManager, modelBase, n * scale);
        this.scale = scale;
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this, this) {
            final RenderGiantZombie this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
        });
    }
    
    protected void preRenderCallback(final EntityGiantZombie entityGiantZombie, final float n) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
}
