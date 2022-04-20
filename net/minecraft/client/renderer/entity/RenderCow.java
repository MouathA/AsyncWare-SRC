package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderCow extends RenderLiving
{
    private static final ResourceLocation cowTextures;
    
    protected ResourceLocation getEntityTexture(final EntityCow entityCow) {
        return RenderCow.cowTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCow)entity);
    }
    
    public RenderCow(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    static {
        cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    }
}
