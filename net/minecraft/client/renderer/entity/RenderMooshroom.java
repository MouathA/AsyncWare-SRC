package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderMooshroom extends RenderLiving
{
    private static final ResourceLocation mooshroomTextures;
    
    protected ResourceLocation getEntityTexture(final EntityMooshroom entityMooshroom) {
        return RenderMooshroom.mooshroomTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMooshroom)entity);
    }
    
    static {
        mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
    }
    
    public RenderMooshroom(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerMooshroomMushroom(this));
    }
}
