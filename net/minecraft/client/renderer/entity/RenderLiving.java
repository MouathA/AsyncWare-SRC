package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;

public abstract class RenderLiving extends RendererLivingEntity
{
    protected void renderLeash(final EntityLiving entityLiving, double n, double n2, double n3, final float n4, final float n5) {
        final Entity leashedToEntity = entityLiving.getLeashedToEntity();
        if (leashedToEntity == null) {
            return;
        }
        n2 -= (1.6 - entityLiving.height) * 0.5;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        final double n6 = this.interpolateValue(leashedToEntity.prevRotationYaw, leashedToEntity.rotationYaw, n5 * 0.5f) * 0.01745329238474369;
        final double n7 = this.interpolateValue(leashedToEntity.prevRotationPitch, leashedToEntity.rotationPitch, n5 * 0.5f) * 0.01745329238474369;
        double cos = Math.cos(n6);
        double sin = Math.sin(n6);
        double sin2 = Math.sin(n7);
        if (leashedToEntity instanceof EntityHanging) {
            cos = 0.0;
            sin = 0.0;
            sin2 = -1.0;
        }
        final double cos2 = Math.cos(n7);
        final double n8 = this.interpolateValue(leashedToEntity.prevPosX, leashedToEntity.posX, n5) - cos * 0.7 - sin * 0.5 * cos2;
        final double n9 = this.interpolateValue(leashedToEntity.prevPosY + leashedToEntity.getEyeHeight() * 0.7, leashedToEntity.posY + leashedToEntity.getEyeHeight() * 0.7, n5) - sin2 * 0.5 - 0.25;
        final double n10 = this.interpolateValue(leashedToEntity.prevPosZ, leashedToEntity.posZ, n5) - sin * 0.7 + cos * 0.5 * cos2;
        final double n11 = this.interpolateValue(entityLiving.prevRenderYawOffset, entityLiving.renderYawOffset, n5) * 0.01745329238474369 + 1.5707963267948966;
        final double n12 = Math.cos(n11) * entityLiving.width * 0.4;
        final double n13 = Math.sin(n11) * entityLiving.width * 0.4;
        final double n14 = this.interpolateValue(entityLiving.prevPosX, entityLiving.posX, n5) + n12;
        final double interpolateValue = this.interpolateValue(entityLiving.prevPosY, entityLiving.posY, n5);
        final double n15 = this.interpolateValue(entityLiving.prevPosZ, entityLiving.posZ, n5) + n13;
        n += n12;
        n3 += n13;
        final double n16 = (float)(n8 - n14);
        final double n17 = (float)(n9 - interpolateValue);
        final double n18 = (float)(n10 - n15);
        worldRenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
        while (true) {
            final float n19 = 0.5f;
            final float n20 = 0.4f;
            final float n21 = 0.3f;
            final float n22 = n19 * 0.7f;
            final float n23 = n20 * 0.7f;
            final float n24 = n21 * 0.7f;
            final float n25 = 0 / 24.0f;
            worldRenderer.pos(n + n16 * n25 + 0.0, n2 + n17 * (n25 * n25 + n25) * 0.5 + ((24.0f - 0) / 18.0f + 0.125f), n3 + n18 * n25).color(n22, n23, n24, 1.0f).endVertex();
            worldRenderer.pos(n + n16 * n25 + 0.025, n2 + n17 * (n25 * n25 + n25) * 0.5 + ((24.0f - 0) / 18.0f + 0.125f) + 0.025, n3 + n18 * n25).color(n22, n23, n24, 1.0f).endVertex();
            int n26 = 0;
            ++n26;
        }
    }
    
    protected boolean canRenderName(final EntityLiving entityLiving) {
        return super.canRenderName((EntityLivingBase)entityLiving) && (entityLiving.getAlwaysRenderNameTagForRender() || (entityLiving.hasCustomName() && entityLiving == this.renderManager.pointedEntity));
    }
    
    public RenderLiving(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    public void func_177105_a(final EntityLiving entityLiving, final float n) {
        final int brightnessForRender = entityLiving.getBrightnessForRender(n);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
    }
    
    private double interpolateValue(final double n, final double n2, final double n3) {
        return n + (n2 - n) * n3;
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLiving)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        super.doRender(entityLiving, n, n2, n3, n4, n5);
        this.renderLeash(entityLiving, n, n2, n3, n4, n5);
    }
    
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.canRenderName((EntityLiving)entityLivingBase);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLiving)entity, n, n2, n3, n4, n5);
    }
    
    public boolean shouldRender(final EntityLiving entityLiving, final ICamera camera, final double n, final double n2, final double n3) {
        return super.shouldRender(entityLiving, camera, n, n2, n3) || (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() != null && camera.isBoundingBoxInFrustum(entityLiving.getLeashedToEntity().getEntityBoundingBox()));
    }
    
    @Override
    public boolean shouldRender(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return this.shouldRender((EntityLiving)entity, camera, n, n2, n3);
    }
    
    @Override
    protected boolean canRenderName(final Entity entity) {
        return this.canRenderName((EntityLiving)entity);
    }
}
