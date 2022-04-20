package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;

public class RenderWitherSkull extends Render
{
    private static final ResourceLocation witherTextures;
    private static final ResourceLocation invulnerableWitherTextures;
    private final ModelSkeletonHead skeletonHeadModel;
    
    private float func_82400_a(final float n, final float n2, final float n3) {
        float n4;
        for (n4 = n2 - n; n4 < -180.0f; n4 += 360.0f) {}
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitherSkull)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWitherSkull)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntityWitherSkull entityWitherSkull) {
        return entityWitherSkull.isInvulnerable() ? RenderWitherSkull.invulnerableWitherTextures : RenderWitherSkull.witherTextures;
    }
    
    static {
        invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    }
    
    public void doRender(final EntityWitherSkull entityWitherSkull, final double n, final double n2, final double n3, final float n4, final float n5) {
        final float func_82400_a = this.func_82400_a(entityWitherSkull.prevRotationYaw, entityWitherSkull.rotationYaw, n5);
        final float n6 = entityWitherSkull.prevRotationPitch + (entityWitherSkull.rotationPitch - entityWitherSkull.prevRotationPitch) * n5;
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n7 = 0.0625f;
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.bindEntityTexture(entityWitherSkull);
        this.skeletonHeadModel.render(entityWitherSkull, 0.0f, 0.0f, 0.0f, func_82400_a, n6, n7);
        super.doRender(entityWitherSkull, n, n2, n3, n4, n5);
    }
    
    public RenderWitherSkull(final RenderManager renderManager) {
        super(renderManager);
        this.skeletonHeadModel = new ModelSkeletonHead();
    }
}
