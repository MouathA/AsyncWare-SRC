package net.minecraft.client.particle;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntityCrit2FX extends EntityFX
{
    float field_174839_a;
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.particleScale = this.field_174839_a * MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
        super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    protected EntityCrit2FX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3, n4, n5, n6, 1.0f);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.particleGreen *= (float)0.96;
        this.particleBlue *= (float)0.9;
        this.motionX *= 0.699999988079071;
        this.motionY *= 0.699999988079071;
        this.motionZ *= 0.699999988079071;
        this.motionY -= 0.019999999552965164;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    protected EntityCrit2FX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += n4 * 0.4;
        this.motionY += n5 * 0.4;
        this.motionZ += n6 * 0.4;
        final float particleRed = (float)(Math.random() * 0.30000001192092896 + 0.6000000238418579);
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= 0.75f;
        this.particleScale *= n7;
        this.field_174839_a = this.particleScale;
        this.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
        this.particleMaxAge *= (int)n7;
        this.noClip = false;
        this.setParticleTextureIndex(65);
        this.onUpdate();
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityCrit2FX(world, n2, n3, n4, n5, n6, n7);
        }
    }
    
    public static class MagicFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntityCrit2FX entityCrit2FX = new EntityCrit2FX(world, n2, n3, n4, n5, n6, n7);
            entityCrit2FX.setRBGColorF(entityCrit2FX.getRedColorF() * 0.3f, entityCrit2FX.getGreenColorF() * 0.8f, entityCrit2FX.getBlueColorF());
            entityCrit2FX.nextTextureIndexX();
            return entityCrit2FX;
        }
    }
}
