package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderPig extends RenderLiving
{
    private static final ResourceLocation pigTextures;
    
    protected ResourceLocation getEntityTexture(final EntityPig entityPig) {
        return RenderPig.pigTextures;
    }
    
    static {
        pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityPig)entity);
    }
    
    public RenderPig(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerSaddle(this));
    }
}
