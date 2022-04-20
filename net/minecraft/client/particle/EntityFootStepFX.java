package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class EntityFootStepFX extends EntityFX
{
    private int footstepAge;
    private int footstepMaxAge;
    private static final ResourceLocation FOOTPRINT_TEXTURE;
    private TextureManager currentFootSteps;
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    static {
        FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
    }
    
    protected EntityFootStepFX(final TextureManager currentFootSteps, final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.currentFootSteps = currentFootSteps;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.footstepMaxAge = 200;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.footstepAge + n) / this.footstepMaxAge;
        float n8 = 2.0f - n7 * n7 * 2.0f;
        if (n8 > 1.0f) {
            n8 = 1.0f;
        }
        final float n9 = n8 * 0.2f;
        final float n10 = (float)(this.posX - EntityFootStepFX.interpPosX);
        final float n11 = (float)(this.posY - EntityFootStepFX.interpPosY);
        final float n12 = (float)(this.posZ - EntityFootStepFX.interpPosZ);
        final float lightBrightness = this.worldObj.getLightBrightness(new BlockPos(this));
        this.currentFootSteps.bindTexture(EntityFootStepFX.FOOTPRINT_TEXTURE);
        GlStateManager.blendFunc(770, 771);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(n10 - 0.125f, n11, n12 + 0.125f).tex(0.0, 1.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 + 0.125f, n11, n12 + 0.125f).tex(1.0, 1.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 + 0.125f, n11, n12 - 0.125f).tex(1.0, 0.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 - 0.125f, n11, n12 - 0.125f).tex(0.0, 0.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        Tessellator.getInstance().draw();
    }
    
    @Override
    public void onUpdate() {
        ++this.footstepAge;
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), world, n2, n3, n4);
        }
    }
}
