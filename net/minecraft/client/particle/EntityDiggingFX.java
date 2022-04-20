package net.minecraft.client.particle;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityDiggingFX extends EntityFX
{
    private IBlockState field_174847_a;
    private BlockPos field_181019_az;
    
    protected EntityDiggingFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final IBlockState field_174847_a) {
        super(world, n, n2, n3, n4, n5, n6);
        this.field_174847_a = field_174847_a;
        this.setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(field_174847_a));
        this.particleGravity = field_174847_a.getBlock().blockParticleGravity;
        final float particleRed = 0.6f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale /= 2.0f;
    }
    
    public EntityDiggingFX func_174845_l() {
        this.field_181019_az = new BlockPos(this.posX, this.posY, this.posZ);
        final Block block = this.field_174847_a.getBlock();
        if (block == Blocks.grass) {
            return this;
        }
        final int renderColor = block.getRenderColor(this.field_174847_a);
        this.particleRed *= (renderColor >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (renderColor >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (renderColor & 0xFF) / 255.0f;
        return this;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        if (this.worldObj.isBlockLoaded(this.field_181019_az)) {
            this.worldObj.getCombinedLight(this.field_181019_az, 0);
        }
        return (brightnessForRender == 0) ? 0 : brightnessForRender;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float interpolatedU = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float interpolatedU2 = interpolatedU + 0.015609375f;
        float interpolatedV = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float interpolatedV2 = interpolatedV + 0.015609375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            interpolatedU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            interpolatedU2 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            interpolatedV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            interpolatedV2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityDiggingFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityDiggingFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityDiggingFX.interpPosZ);
        final int brightnessForRender = this.getBrightnessForRender(n);
        final int n11 = brightnessForRender >> 16 & 0xFFFF;
        final int n12 = brightnessForRender & 0xFFFF;
        worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(interpolatedU, interpolatedV2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(interpolatedU, interpolatedV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(interpolatedU2, interpolatedV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(interpolatedU2, interpolatedV2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    public EntityDiggingFX func_174846_a(final BlockPos field_181019_az) {
        this.field_181019_az = field_181019_az;
        if (this.field_174847_a.getBlock() == Blocks.grass) {
            return this;
        }
        final int colorMultiplier = this.field_174847_a.getBlock().colorMultiplier(this.worldObj, field_181019_az);
        this.particleRed *= (colorMultiplier >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (colorMultiplier >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (colorMultiplier & 0xFF) / 255.0f;
        return this;
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDiggingFX(world, n2, n3, n4, n5, n6, n7, Block.getStateById(array[0])).func_174845_l();
        }
    }
}
