package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFireball extends Render
{
    private float scale;
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFireball)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFireball)entity);
    }
    
    public RenderFireball(final RenderManager renderManager, final float scale) {
        super(renderManager);
        this.scale = scale;
    }
    
    public void doRender(final EntityFireball entityFireball, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.bindEntityTexture(entityFireball);
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.scale(this.scale, this.scale, this.scale);
        final TextureAtlasSprite particleIcon = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float minU = particleIcon.getMinU();
        final float maxU = particleIcon.getMaxU();
        final float minV = particleIcon.getMinV();
        final float maxV = particleIcon.getMaxV();
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        worldRenderer.pos(-0.5, -0.25, 0.0).tex(minU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, -0.25, 0.0).tex(maxU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, 0.75, 0.0).tex(maxU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(-0.5, 0.75, 0.0).tex(minU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();
        instance.draw();
        super.doRender(entityFireball, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityFireball entityFireball) {
        return TextureMap.locationBlocksTexture;
    }
}
