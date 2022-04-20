package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float field_70565_a;
    private double coordX;
    private double coordY;
    private double coordZ;
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        final int n5 = brightnessForRender & 0xFF;
        final int n6 = 240 + (int)(n4 * 15.0f * 16.0f);
        return n5 | 0xF00000;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float n = 1.0f - this.particleAge / (float)this.particleMaxAge;
        final float n2 = 1.0f - n;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        this.posX = this.coordX + this.motionX * n;
        this.posY = this.coordY + this.motionY * n - n4 * 1.2f;
        this.posZ = this.coordZ + this.motionZ * n;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    protected EntityEnchantmentTableParticleFX(final World world, final double coordX, final double coordY, final double coordZ, final double motionX, final double motionY, final double motionZ) {
        super(world, coordX, coordY, coordZ, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = coordZ;
        final double n = coordX + motionX;
        this.prevPosX = n;
        this.posX = n;
        final double n2 = coordY + motionY;
        this.prevPosY = n2;
        this.posY = n2;
        final double n3 = coordZ + motionZ;
        this.prevPosZ = n3;
        this.posZ = n3;
        final float n4 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n5 = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleScale = n5;
        this.field_70565_a = n5;
        final float particleRed = 1.0f * n4;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
    }
    
    @Override
    public float getBrightness(final float n) {
        final float brightness = super.getBrightness(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        return brightness * (1.0f - n4) + n4;
    }
    
    public static class EnchantmentTable implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityEnchantmentTableParticleFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
