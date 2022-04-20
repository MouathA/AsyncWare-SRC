package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class RenderXPOrb extends Render
{
    private static final ResourceLocation experienceOrbTextures;
    
    static {
        experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
    }
    
    public RenderXPOrb(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityXPOrb)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityXPOrb)entity);
    }
    
    public void doRender(final EntityXPOrb entityXPOrb, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindEntityTexture(entityXPOrb);
        final int textureByXP = entityXPOrb.getTextureByXP();
        final float n6 = (textureByXP % 4 * 16 + 0) / 64.0f;
        final float n7 = (textureByXP % 4 * 16 + 16) / 64.0f;
        final float n8 = (textureByXP / 4 * 16 + 0) / 64.0f;
        final float n9 = (textureByXP / 4 * 16 + 16) / 64.0f;
        final float n10 = 1.0f;
        final float n11 = 0.5f;
        final float n12 = 0.25f;
        final int brightnessForRender = entityXPOrb.getBrightnessForRender(n5);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n13 = (entityXPOrb.xpColor + n5) / 2.0f;
        final int n14 = (int)((MathHelper.sin(n13 + 0.0f) + 1.0f) * 0.5f * 255.0f);
        final int n15 = (int)((MathHelper.sin(n13 + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        worldRenderer.pos(0.0f - n11, 0.0f - n12, 0.0).tex(n6, n9).color(n14, 255, n15, 128).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(n10 - n11, 0.0f - n12, 0.0).tex(n7, n9).color(n14, 255, n15, 128).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(n10 - n11, 1.0f - n12, 0.0).tex(n7, n8).color(n14, 255, n15, 128).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.0f - n11, 1.0f - n12, 0.0).tex(n6, n8).color(n14, 255, n15, 128).normal(0.0f, 1.0f, 0.0f).endVertex();
        instance.draw();
        super.doRender(entityXPOrb, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityXPOrb entityXPOrb) {
        return RenderXPOrb.experienceOrbTextures;
    }
}
