package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderSheep extends RenderLiving
{
    private static final ResourceLocation shearedSheepTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySheep)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySheep entitySheep) {
        return RenderSheep.shearedSheepTextures;
    }
    
    static {
        shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    }
    
    public RenderSheep(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerSheepWool(this));
    }
}
