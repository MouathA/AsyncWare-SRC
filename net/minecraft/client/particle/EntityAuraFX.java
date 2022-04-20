package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityAuraFX extends EntityFX
{
    protected EntityAuraFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        final float particleBlue = this.rand.nextFloat() * 0.1f + 0.2f;
        this.particleRed = particleBlue;
        this.particleGreen = particleBlue;
        this.particleBlue = particleBlue;
        this.setParticleTextureIndex(0);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.5f;
        this.motionX *= 0.019999999552965164;
        this.motionY *= 0.019999999552965164;
        this.motionZ *= 0.019999999552965164;
        this.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = true;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityAuraFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
    
    public static class HappyVillagerFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntityAuraFX entityAuraFX = new EntityAuraFX(world, n2, n3, n4, n5, n6, n7);
            entityAuraFX.setParticleTextureIndex(82);
            entityAuraFX.setRBGColorF(1.0f, 1.0f, 1.0f);
            return entityAuraFX;
        }
    }
}
