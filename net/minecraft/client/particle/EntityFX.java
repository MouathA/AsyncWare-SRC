package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityFX extends Entity
{
    protected float particleBlue;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected TextureAtlasSprite particleIcon;
    protected float particleGravity;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleAlpha;
    protected float particleRed;
    protected int particleTextureIndexY;
    protected float particleScale;
    public static double interpPosX;
    public static double interpPosZ;
    protected int particleTextureIndexX;
    protected float particleGreen;
    public static double interpPosY;
    
    public EntityFX multipleParticleScaleBy(final float n) {
        this.setSize(0.2f * n, 0.2f * n);
        this.particleScale *= n;
        return this;
    }
    
    public void setRBGColorF(final float particleRed, final float particleGreen, final float particleBlue) {
        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;
    }
    
    public void setParticleIcon(final TextureAtlasSprite particleIcon) {
        if (this.getFXLayer() == 1) {
            this.particleIcon = particleIcon;
            return;
        }
        throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float minU = this.particleTextureIndexX / 16.0f;
        float maxU = minU + 0.0624375f;
        float minV = this.particleTextureIndexY / 16.0f;
        float maxV = minV + 0.0624375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
            minV = this.particleIcon.getMinV();
            maxV = this.particleIcon.getMaxV();
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityFX.interpPosZ);
        final int brightnessForRender = this.getBrightnessForRender(n);
        final int n11 = brightnessForRender >> 16 & 0xFFFF;
        final int n12 = brightnessForRender & 0xFFFF;
        worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
    }
    
    public void setAlphaF(final float particleAlpha) {
        if (this.particleAlpha == 1.0f && particleAlpha < 1.0f) {
            Minecraft.getMinecraft().effectRenderer.moveToAlphaLayer(this);
        }
        else if (this.particleAlpha < 1.0f && particleAlpha == 1.0f) {
            Minecraft.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
        }
        this.particleAlpha = particleAlpha;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    protected EntityFX(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.particleAlpha = 1.0f;
        this.setSize(0.2f, 0.2f);
        this.setPosition(n, n2, n3);
        this.prevPosX = n;
        this.lastTickPosX = n;
        this.prevPosY = n2;
        this.lastTickPosY = n2;
        this.prevPosZ = n3;
        this.lastTickPosZ = n3;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }
    
    public void setParticleTextureIndex(final int n) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }
        this.particleTextureIndexX = n % 16;
        this.particleTextureIndexY = n / 16;
    }
    
    public float getGreenColorF() {
        return this.particleGreen;
    }
    
    public EntityFX multiplyVelocity(final float n) {
        this.motionX *= n;
        this.motionY = (this.motionY - 0.10000000149011612) * n + 0.10000000149011612;
        this.motionZ *= n;
        return this;
    }
    
    public EntityFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3);
        this.motionX = n4 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionY = n5 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionZ = n6 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        final float n7 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / sqrt_double * n7 * 0.4000000059604645;
        this.motionY = this.motionY / sqrt_double * n7 * 0.4000000059604645 + 0.10000000149011612;
        this.motionZ = this.motionZ / sqrt_double * n7 * 0.4000000059604645;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public int getFXLayer() {
        return 0;
    }
    
    public float getBlueColorF() {
        return this.particleBlue;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public float getAlpha() {
        return this.particleAlpha;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public float getRedColorF() {
        return this.particleRed;
    }
    
    public void nextTextureIndexX() {
        ++this.particleTextureIndexX;
    }
}
