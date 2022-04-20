package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class EntityFirework
{
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final SparkFX sparkFX = new SparkFX(world, n2, n3, n4, n5, n6, n7, Minecraft.getMinecraft().effectRenderer);
            sparkFX.setAlphaF(0.99f);
            return sparkFX;
        }
    }
    
    public static class SparkFX extends EntityFX
    {
        private float fadeColourBlue;
        private float fadeColourRed;
        private boolean trail;
        private float fadeColourGreen;
        private int baseTextureIndex;
        private boolean hasFadeColour;
        private final EffectRenderer field_92047_az;
        private boolean twinkle;
        
        public void setTrail(final boolean trail) {
            this.trail = trail;
        }
        
        public SparkFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer field_92047_az) {
            super(world, n, n2, n3);
            this.baseTextureIndex = 160;
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.field_92047_az = field_92047_az;
            this.particleScale *= 0.75f;
            this.particleMaxAge = 48 + this.rand.nextInt(12);
            this.noClip = false;
        }
        
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
                super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
            }
        }
        
        @Override
        public boolean canBePushed() {
            return false;
        }
        
        @Override
        public int getBrightnessForRender(final float n) {
            return 15728880;
        }
        
        @Override
        public void onUpdate() {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (this.particleAge++ >= this.particleMaxAge) {
                this.setDead();
            }
            if (this.particleAge > this.particleMaxAge / 2) {
                this.setAlphaF(1.0f - (this.particleAge - (float)(this.particleMaxAge / 2)) / this.particleMaxAge);
                if (this.hasFadeColour) {
                    this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2f;
                    this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2f;
                    this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2f;
                }
            }
            this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
            this.motionY -= 0.004;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9100000262260437;
            this.motionY *= 0.9100000262260437;
            this.motionZ *= 0.9100000262260437;
            if (this.onGround) {
                this.motionX *= 0.699999988079071;
                this.motionZ *= 0.699999988079071;
            }
            if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
                final SparkFX sparkFX = new SparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
                sparkFX.setAlphaF(0.99f);
                sparkFX.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
                sparkFX.particleAge = sparkFX.particleMaxAge / 2;
                if (this.hasFadeColour) {
                    sparkFX.hasFadeColour = true;
                    sparkFX.fadeColourRed = this.fadeColourRed;
                    sparkFX.fadeColourGreen = this.fadeColourGreen;
                    sparkFX.fadeColourBlue = this.fadeColourBlue;
                }
                sparkFX.twinkle = this.twinkle;
                this.field_92047_az.addEffect(sparkFX);
            }
        }
        
        public void setFadeColour(final int n) {
            this.fadeColourRed = ((n & 0xFF0000) >> 16) / 255.0f;
            this.fadeColourGreen = ((n & 0xFF00) >> 8) / 255.0f;
            this.fadeColourBlue = ((n & 0xFF) >> 0) / 255.0f;
            this.hasFadeColour = true;
        }
        
        public void setColour(final int n) {
            final float n2 = ((n & 0xFF0000) >> 16) / 255.0f;
            final float n3 = ((n & 0xFF00) >> 8) / 255.0f;
            final float n4 = ((n & 0xFF) >> 0) / 255.0f;
            final float n5 = 1.0f;
            this.setRBGColorF(n2 * n5, n3 * n5, n4 * n5);
        }
        
        @Override
        public float getBrightness(final float n) {
            return 1.0f;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox() {
            return null;
        }
        
        public void setTwinkle(final boolean twinkle) {
            this.twinkle = twinkle;
        }
    }
    
    public static class OverlayFX extends EntityFX
    {
        protected OverlayFX(final World world, final double n, final double n2, final double n3) {
            super(world, n, n2, n3);
            this.particleMaxAge = 4;
        }
        
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            final float n7 = 7.1f * MathHelper.sin((this.particleAge + n - 1.0f) * 0.25f * 3.1415927f);
            this.particleAlpha = 0.6f - (this.particleAge + n - 1.0f) * 0.25f * 0.5f;
            final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - OverlayFX.interpPosX);
            final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - OverlayFX.interpPosY);
            final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - OverlayFX.interpPosZ);
            final int brightnessForRender = this.getBrightnessForRender(n);
            final int n11 = brightnessForRender >> 16 & 0xFFFF;
            final int n12 = brightnessForRender & 0xFFFF;
            worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(0.5, 0.375).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(0.5, 0.125).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(0.25, 0.125).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(0.25, 0.375).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        }
    }
    
    public static class StarterFX extends EntityFX
    {
        private final EffectRenderer theEffectRenderer;
        private NBTTagList fireworkExplosions;
        boolean twinkle;
        private int fireworkAge;
        
        @Override
        public int getFXLayer() {
            return 0;
        }
        
        private void createBall(final double n, final int n2, final int[] array, final int[] array2, final boolean b, final boolean b2) {
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            for (int i = -n2; i <= n2; ++i) {
                for (int j = -n2; j <= n2; ++j) {
                    for (int k = -n2; k <= n2; ++k) {
                        final double n3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4 + n5 * n5) / n + this.rand.nextGaussian() * 0.05;
                        this.createParticle(posX, posY, posZ, n3 / n6, n4 / n6, n5 / n6, array, array2, b, b2);
                        if (i != -n2 && i != n2 && j != -n2 && j != n2) {
                            k += n2 * 2 - 1;
                        }
                    }
                }
            }
        }
        
        private void createShaped(final double n, final double[][] array, final int[] array2, final int[] array3, final boolean b, final boolean b2, final boolean b3) {
            final double n2 = array[0][0];
            final double n3 = array[0][1];
            this.createParticle(this.posX, this.posY, this.posZ, n2 * n, n3 * n, 0.0, array2, array3, b, b2);
            final float n4 = this.rand.nextFloat() * 3.1415927f;
            final double n5 = b3 ? 0.034 : 0.34;
            while (true) {
                final double n6 = n4 + 0 * 3.1415927f * n5;
                double n7 = n2;
                double n8 = n3;
                while (1 < array.length) {
                    final double n9 = array[1][0];
                    final double n10 = array[1][1];
                    for (double n11 = 0.25; n11 <= 1.0; n11 += 0.25) {
                        final double n12 = (n7 + (n9 - n7) * n11) * n;
                        final double n13 = (n8 + (n10 - n8) * n11) * n;
                        final double n14 = n12 * Math.sin(n6);
                        final double n15 = n12 * Math.cos(n6);
                        for (double n16 = -1.0; n16 <= 1.0; n16 += 2.0) {
                            this.createParticle(this.posX, this.posY, this.posZ, n15 * n16, n13, n14 * n16, array2, array3, b, b2);
                        }
                    }
                    n7 = n9;
                    n8 = n10;
                    int n17 = 0;
                    ++n17;
                }
                int n18 = 0;
                ++n18;
            }
        }
        
        private boolean func_92037_i() {
            final Minecraft minecraft = Minecraft.getMinecraft();
            return minecraft == null || minecraft.getRenderViewEntity() == null || minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0;
        }
        
        public StarterFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer theEffectRenderer, final NBTTagCompound nbtTagCompound) {
            super(world, n, n2, n3, 0.0, 0.0, 0.0);
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.theEffectRenderer = theEffectRenderer;
            this.particleMaxAge = 8;
            if (nbtTagCompound != null) {
                this.fireworkExplosions = nbtTagCompound.getTagList("Explosions", 10);
                if (this.fireworkExplosions.tagCount() == 0) {
                    this.fireworkExplosions = null;
                }
                else {
                    this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
                    while (0 < this.fireworkExplosions.tagCount()) {
                        if (this.fireworkExplosions.getCompoundTagAt(0).getBoolean("Flicker")) {
                            this.twinkle = true;
                            this.particleMaxAge += 15;
                            break;
                        }
                        int n4 = 0;
                        ++n4;
                    }
                }
            }
        }
        
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        }
        
        private void createBurst(final int[] array, final int[] array2, final boolean b, final boolean b2) {
            final double n = this.rand.nextGaussian() * 0.05;
            final double n2 = this.rand.nextGaussian() * 0.05;
            while (true) {
                this.createParticle(this.posX, this.posY, this.posZ, this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + n, this.motionY * 0.5 + this.rand.nextDouble() * 0.5, this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + n2, array, array2, b, b2);
                int n3 = 0;
                ++n3;
            }
        }
        
        private void createParticle(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int[] array, final int[] array2, final boolean trail, final boolean twinkle) {
            final SparkFX sparkFX = new SparkFX(this.worldObj, n, n2, n3, n4, n5, n6, this.theEffectRenderer);
            sparkFX.setAlphaF(0.99f);
            sparkFX.setTrail(trail);
            sparkFX.setTwinkle(twinkle);
            sparkFX.setColour(array[this.rand.nextInt(array.length)]);
            if (array2 != null && array2.length > 0) {
                sparkFX.setFadeColour(array2[this.rand.nextInt(array2.length)]);
            }
            this.theEffectRenderer.addEffect(sparkFX);
        }
        
        @Override
        public void onUpdate() {
            if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
                final boolean func_92037_i = this.func_92037_i();
                if (this.fireworkExplosions.tagCount() < 3) {
                    while (0 < this.fireworkExplosions.tagCount()) {
                        if (this.fireworkExplosions.getCompoundTagAt(0).getByte("Type") == 1) {
                            break;
                        }
                        int byte1 = 0;
                        ++byte1;
                    }
                }
                this.worldObj.playSound(this.posX, this.posY, this.posZ, "fireworks." + "largeBlast" + (func_92037_i ? "_far" : ""), 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, true);
            }
            if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
                final NBTTagCompound compoundTag = this.fireworkExplosions.getCompoundTagAt(this.fireworkAge / 2);
                final int byte1 = compoundTag.getByte("Type");
                final boolean boolean1 = compoundTag.getBoolean("Trail");
                final boolean boolean2 = compoundTag.getBoolean("Flicker");
                int[] intArray = compoundTag.getIntArray("Colors");
                final int[] intArray2 = compoundTag.getIntArray("FadeColors");
                if (intArray.length == 0) {
                    intArray = new int[] { ItemDye.dyeColors[0] };
                }
                this.createBall(0.25, 2, intArray, intArray2, boolean1, boolean2);
                final int n = intArray[0];
                final float n2 = ((n & 0xFF0000) >> 16) / 255.0f;
                final float n3 = ((n & 0xFF00) >> 8) / 255.0f;
                final float n4 = ((n & 0xFF) >> 0) / 255.0f;
                final OverlayFX overlayFX = new OverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
                overlayFX.setRBGColorF(n2, n3, n4);
                this.theEffectRenderer.addEffect(overlayFX);
            }
            ++this.fireworkAge;
            if (this.fireworkAge > this.particleMaxAge) {
                if (this.twinkle) {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, "fireworks." + (this.func_92037_i() ? "twinkle_far" : "twinkle"), 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, true);
                }
                this.setDead();
            }
        }
    }
}
