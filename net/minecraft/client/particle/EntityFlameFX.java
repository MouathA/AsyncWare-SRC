package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityFlameFX extends EntityFX
{
    private float flameScale;
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        final int n2 = super.getBrightnessForRender(n) >> 16 & 0xFF;
        final int n3 = 240 + (int)(clamp_float * 15.0f * 16.0f);
        return 0xF0 | n2 << 16;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - n7 * n7 * 0.5f);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    protected EntityFlameFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.motionX = this.motionX * 0.009999999776482582 + n4;
        this.motionY = this.motionY * 0.009999999776482582 + n5;
        this.motionZ = this.motionZ * 0.009999999776482582 + n6;
        this.posX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.posY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.posZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.flameScale = this.particleScale;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }
    
    @Override
    public float getBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        return super.getBrightness(n) * clamp_float + (1.0f - clamp_float);
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFlameFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
