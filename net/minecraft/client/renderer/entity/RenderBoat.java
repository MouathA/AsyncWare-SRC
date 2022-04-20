package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;

public class RenderBoat extends Render
{
    private static final ResourceLocation boatTextures;
    protected ModelBase modelBoat;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityBoat)entity);
    }
    
    public void doRender(final EntityBoat entityBoat, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2 + 0.25f, (float)n3);
        GlStateManager.rotate(180.0f - n4, 0.0f, 1.0f, 0.0f);
        final float n6 = entityBoat.getTimeSinceHit() - n5;
        float n7 = entityBoat.getDamageTaken() - n5;
        if (n7 < 0.0f) {
            n7 = 0.0f;
        }
        if (n6 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(n6) * n6 * n7 / 10.0f * entityBoat.getForwardDirection(), 1.0f, 0.0f, 0.0f);
        }
        final float n8 = 0.75f;
        GlStateManager.scale(n8, n8, n8);
        GlStateManager.scale(1.0f / n8, 1.0f / n8, 1.0f / n8);
        this.bindEntityTexture(entityBoat);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.modelBoat.render(entityBoat, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        super.doRender(entityBoat, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityBoat)entity, n, n2, n3, n4, n5);
    }
    
    static {
        boatTextures = new ResourceLocation("textures/entity/boat.png");
    }
    
    protected ResourceLocation getEntityTexture(final EntityBoat entityBoat) {
        return RenderBoat.boatTextures;
    }
    
    public RenderBoat(final RenderManager renderManager) {
        super(renderManager);
        this.modelBoat = new ModelBoat();
        this.shadowSize = 0.5f;
    }
}
