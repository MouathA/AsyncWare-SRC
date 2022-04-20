package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class RenderFish extends Render
{
    private static final ResourceLocation FISH_PARTICLES;
    
    public RenderFish(final RenderManager renderManager) {
        super(renderManager);
    }
    
    static {
        FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
    }
    
    protected ResourceLocation getEntityTexture(final EntityFishHook entityFishHook) {
        return RenderFish.FISH_PARTICLES;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFishHook)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFishHook)entity, n, n2, n3, n4, n5);
    }
    
    public void doRender(final EntityFishHook entityFishHook, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        this.bindEntityTexture(entityFishHook);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        worldRenderer.pos(-0.5, -0.5, 0.0).tex(0.0625, 0.1875).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, -0.5, 0.0).tex(0.125, 0.1875).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.5, 0.5, 0.0).tex(0.125, 0.125).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(-0.5, 0.5, 0.0).tex(0.0625, 0.125).normal(0.0f, 1.0f, 0.0f).endVertex();
        instance.draw();
        if (entityFishHook.angler == null) {
            return;
        }
        final float sin = MathHelper.sin(MathHelper.sqrt_float(entityFishHook.angler.getSwingProgress(n5)) * 3.1415927f);
        final Vec3 rotatePitch = new Vec3(-0.36, 0.03, 0.35).rotatePitch(-(entityFishHook.angler.prevRotationPitch + (entityFishHook.angler.rotationPitch - entityFishHook.angler.prevRotationPitch) * n5) * 3.1415927f / 180.0f).rotateYaw(-(entityFishHook.angler.prevRotationYaw + (entityFishHook.angler.rotationYaw - entityFishHook.angler.prevRotationYaw) * n5) * 3.1415927f / 180.0f).rotateYaw(sin * 0.5f).rotatePitch(-sin * 0.7f);
        double n6 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 + rotatePitch.xCoord;
        double n7 = entityFishHook.angler.prevPosY + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 + rotatePitch.yCoord;
        double n8 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 + rotatePitch.zCoord;
        double n9 = entityFishHook.angler.getEyeHeight();
        if ((this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0) || entityFishHook.angler != Minecraft.getMinecraft().thePlayer) {
            final float n10 = (entityFishHook.angler.prevRenderYawOffset + (entityFishHook.angler.renderYawOffset - entityFishHook.angler.prevRenderYawOffset) * n5) * 3.1415927f / 180.0f;
            final double n11 = MathHelper.sin(n10);
            final double n12 = MathHelper.cos(n10);
            n6 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 - n12 * 0.35 - n11 * 0.8;
            n7 = entityFishHook.angler.prevPosY + n9 + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 - 0.45;
            n8 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 - n11 * 0.35 + n12 * 0.8;
            n9 = (entityFishHook.angler.isSneaking() ? -0.1875 : 0.0);
        }
        final double n13 = entityFishHook.prevPosX + (entityFishHook.posX - entityFishHook.prevPosX) * n5;
        final double n14 = entityFishHook.prevPosY + (entityFishHook.posY - entityFishHook.prevPosY) * n5 + 0.25;
        final double n15 = entityFishHook.prevPosZ + (entityFishHook.posZ - entityFishHook.prevPosZ) * n5;
        final double n16 = (float)(n6 - n13);
        final double n17 = (float)(n7 - n14) + n9;
        final double n18 = (float)(n8 - n15);
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        while (true) {
            final float n19 = 0 / 16.0f;
            worldRenderer.pos(n + n16 * n19, n2 + n17 * (n19 * n19 + n19) * 0.5 + 0.25, n3 + n18 * n19).color(0, 0, 0, 255).endVertex();
            int n20 = 0;
            ++n20;
        }
    }
}
