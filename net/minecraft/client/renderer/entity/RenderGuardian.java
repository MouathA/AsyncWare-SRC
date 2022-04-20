package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;

public class RenderGuardian extends RenderLiving
{
    private static final ResourceLocation GUARDIAN_TEXTURE;
    private static final ResourceLocation GUARDIAN_ELDER_TEXTURE;
    int field_177115_a;
    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGuardian)entity);
    }
    
    public RenderGuardian(final RenderManager renderManager) {
        super(renderManager, new ModelGuardian(), 0.5f);
        this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
    }
    
    private Vec3 func_177110_a(final EntityLivingBase entityLivingBase, final double n, final float n2) {
        return new Vec3(entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * n2, n + entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * n2, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * n2);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityGuardian)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityGuardian entityGuardian) {
        return entityGuardian.isElder() ? RenderGuardian.GUARDIAN_ELDER_TEXTURE : RenderGuardian.GUARDIAN_TEXTURE;
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityGuardian)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityGuardian)entity, n, n2, n3, n4, n5);
    }
    
    public void doRender(final EntityGuardian entityGuardian, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
            this.mainModel = new ModelGuardian();
            this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
        }
        super.doRender(entityGuardian, n, n2, n3, n4, n5);
        final EntityLivingBase targetedEntity = entityGuardian.getTargetedEntity();
        if (targetedEntity != null) {
            final float func_175477_p = entityGuardian.func_175477_p(n5);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(RenderGuardian.GUARDIAN_BEAM_TEXTURE);
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GlStateManager.depthMask(true);
            final float n6 = 240.0f;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n6, n6);
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            final float n7 = entityGuardian.worldObj.getTotalWorldTime() + n5;
            final float n8 = n7 * 0.5f % 1.0f;
            final float eyeHeight = entityGuardian.getEyeHeight();
            GlStateManager.translate((float)n, (float)n2 + eyeHeight, (float)n3);
            final Vec3 subtract = this.func_177110_a(targetedEntity, targetedEntity.height * 0.5, n5).subtract(this.func_177110_a(entityGuardian, eyeHeight, n5));
            final double n9 = subtract.lengthVector() + 1.0;
            final Vec3 normalize = subtract.normalize();
            final float n10 = (float)Math.acos(normalize.yCoord);
            GlStateManager.rotate((1.5707964f + -(float)Math.atan2(normalize.zCoord, normalize.xCoord)) * 57.295776f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(n10 * 57.295776f, 1.0f, 0.0f, 0.0f);
            final double n11 = n7 * 0.05 * (1.0 - 1 * 2.5);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            final float n12 = func_175477_p * func_175477_p;
            final int n13 = 64 + (int)(n12 * 240.0f);
            final int n14 = 32 + (int)(n12 * 192.0f);
            final int n15 = 128 - (int)(n12 * 64.0f);
            final double n16 = 1 * 0.2;
            final double n17 = n16 * 1.41;
            final double n18 = 0.0 + Math.cos(n11 + 2.356194490192345) * n17;
            final double n19 = 0.0 + Math.sin(n11 + 2.356194490192345) * n17;
            final double n20 = 0.0 + Math.cos(n11 + 0.7853981633974483) * n17;
            final double n21 = 0.0 + Math.sin(n11 + 0.7853981633974483) * n17;
            final double n22 = 0.0 + Math.cos(n11 + 3.9269908169872414) * n17;
            final double n23 = 0.0 + Math.sin(n11 + 3.9269908169872414) * n17;
            final double n24 = 0.0 + Math.cos(n11 + 5.497787143782138) * n17;
            final double n25 = 0.0 + Math.sin(n11 + 5.497787143782138) * n17;
            final double n26 = 0.0 + Math.cos(n11 + 3.141592653589793) * n16;
            final double n27 = 0.0 + Math.sin(n11 + 3.141592653589793) * n16;
            final double n28 = 0.0 + Math.cos(n11 + 0.0) * n16;
            final double n29 = 0.0 + Math.sin(n11 + 0.0) * n16;
            final double n30 = 0.0 + Math.cos(n11 + 1.5707963267948966) * n16;
            final double n31 = 0.0 + Math.sin(n11 + 1.5707963267948966) * n16;
            final double n32 = 0.0 + Math.cos(n11 + 4.71238898038469) * n16;
            final double n33 = 0.0 + Math.sin(n11 + 4.71238898038469) * n16;
            final double n34 = -1.0f + n8;
            final double n35 = n9 * (0.5 / n16) + n34;
            worldRenderer.pos(n26, n9, n27).tex(0.4999, n35).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n26, 0.0, n27).tex(0.4999, n34).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n28, 0.0, n29).tex(0.0, n34).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n28, n9, n29).tex(0.0, n35).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n30, n9, n31).tex(0.4999, n35).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n30, 0.0, n31).tex(0.4999, n34).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n32, 0.0, n33).tex(0.0, n34).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n32, n9, n33).tex(0.0, n35).color(n13, n14, n15, 255).endVertex();
            double n36 = 0.0;
            if (entityGuardian.ticksExisted % 2 == 0) {
                n36 = 0.5;
            }
            worldRenderer.pos(n18, n9, n19).tex(0.5, n36 + 0.5).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n20, n9, n21).tex(1.0, n36 + 0.5).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n24, n9, n25).tex(1.0, n36).color(n13, n14, n15, 255).endVertex();
            worldRenderer.pos(n22, n9, n23).tex(0.5, n36).color(n13, n14, n15, 255).endVertex();
            instance.draw();
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGuardian)entityLivingBase, n);
    }
    
    public boolean shouldRender(final EntityGuardian entityGuardian, final ICamera camera, final double n, final double n2, final double n3) {
        if (super.shouldRender(entityGuardian, camera, n, n2, n3)) {
            return true;
        }
        if (entityGuardian.hasTargetedEntity()) {
            final EntityLivingBase targetedEntity = entityGuardian.getTargetedEntity();
            if (targetedEntity != null) {
                final Vec3 func_177110_a = this.func_177110_a(targetedEntity, targetedEntity.height * 0.5, 1.0f);
                final Vec3 func_177110_a2 = this.func_177110_a(entityGuardian, entityGuardian.getEyeHeight(), 1.0f);
                if (camera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(func_177110_a2.xCoord, func_177110_a2.yCoord, func_177110_a2.zCoord, func_177110_a.xCoord, func_177110_a.yCoord, func_177110_a.zCoord))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean shouldRender(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return this.shouldRender((EntityGuardian)entity, camera, n, n2, n3);
    }
    
    protected void preRenderCallback(final EntityGuardian entityGuardian, final float n) {
        if (entityGuardian.isElder()) {
            GlStateManager.scale(2.35f, 2.35f, 2.35f);
        }
    }
    
    static {
        GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
        GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");
        GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
    }
    
    @Override
    public boolean shouldRender(final EntityLiving entityLiving, final ICamera camera, final double n, final double n2, final double n3) {
        return this.shouldRender((EntityGuardian)entityLiving, camera, n, n2, n3);
    }
}
