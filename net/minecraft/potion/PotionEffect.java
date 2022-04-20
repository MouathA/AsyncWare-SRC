package net.minecraft.potion;

import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class PotionEffect
{
    private static final Logger LOGGER;
    private boolean isPotionDurationMax;
    private boolean showParticles;
    private boolean isSplashPotion;
    private int duration;
    private boolean isAmbient;
    private int amplifier;
    private int potionID;
    
    private int deincrementDuration() {
        return --this.duration;
    }
    
    @Override
    public int hashCode() {
        return this.potionID;
    }
    
    public void setSplashPotion(final boolean isSplashPotion) {
        this.isSplashPotion = isSplashPotion;
    }
    
    public void setPotionDurationMax(final boolean isPotionDurationMax) {
        this.isPotionDurationMax = isPotionDurationMax;
    }
    
    public PotionEffect(final int n, final int n2, final int n3) {
        this(n, n2, n3, false, true);
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public void combine(final PotionEffect potionEffect) {
        if (this.potionID != potionEffect.potionID) {
            PotionEffect.LOGGER.warn("This method should only be called for matching effects!");
        }
        if (potionEffect.amplifier > this.amplifier) {
            this.amplifier = potionEffect.amplifier;
            this.duration = potionEffect.duration;
        }
        else if (potionEffect.amplifier == this.amplifier && this.duration < potionEffect.duration) {
            this.duration = potionEffect.duration;
        }
        else if (!potionEffect.isAmbient && this.isAmbient) {
            this.isAmbient = potionEffect.isAmbient;
        }
        this.showParticles = potionEffect.showParticles;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public int getAmplifier() {
        return this.amplifier;
    }
    
    public int getPotionID() {
        return this.potionID;
    }
    
    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }
    
    public void performEffect(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            Potion.potionTypes[this.potionID].performEffect(entityLivingBase, this.amplifier);
        }
    }
    
    public NBTTagCompound writeCustomPotionEffectToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Id", (byte)this.getPotionID());
        nbtTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        nbtTagCompound.setInteger("Duration", this.getDuration());
        nbtTagCompound.setBoolean("Ambient", this.getIsAmbient());
        nbtTagCompound.setBoolean("ShowParticles", this.getIsShowParticles());
        return nbtTagCompound;
    }
    
    public boolean getIsAmbient() {
        return this.isAmbient;
    }
    
    public boolean onUpdate(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
                this.performEffect(entityLivingBase);
            }
            this.deincrementDuration();
        }
        return this.duration > 0;
    }
    
    public boolean getIsShowParticles() {
        return this.showParticles;
    }
    
    public PotionEffect(final int n, final int n2) {
        this(n, n2, 0);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PotionEffect)) {
            return false;
        }
        final PotionEffect potionEffect = (PotionEffect)o;
        return this.potionID == potionEffect.potionID && this.amplifier == potionEffect.amplifier && this.duration == potionEffect.duration && this.isSplashPotion == potionEffect.isSplashPotion && this.isAmbient == potionEffect.isAmbient;
    }
    
    public static PotionEffect readCustomPotionEffectFromNBT(final NBTTagCompound nbtTagCompound) {
        final byte byte1 = nbtTagCompound.getByte("Id");
        if (byte1 >= 0 && byte1 < Potion.potionTypes.length && Potion.potionTypes[byte1] != null) {
            final byte byte2 = nbtTagCompound.getByte("Amplifier");
            final int integer = nbtTagCompound.getInteger("Duration");
            final boolean boolean1 = nbtTagCompound.getBoolean("Ambient");
            if (nbtTagCompound.hasKey("ShowParticles", 1)) {
                nbtTagCompound.getBoolean("ShowParticles");
            }
            return new PotionEffect(byte1, integer, byte2, boolean1, true);
        }
        return null;
    }
    
    public PotionEffect(final PotionEffect potionEffect) {
        this.potionID = potionEffect.potionID;
        this.duration = potionEffect.duration;
        this.amplifier = potionEffect.amplifier;
        this.isAmbient = potionEffect.isAmbient;
        this.showParticles = potionEffect.showParticles;
    }
    
    public PotionEffect(final int potionID, final int duration, final int amplifier, final boolean isAmbient, final boolean showParticles) {
        this.potionID = potionID;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isAmbient = isAmbient;
        this.showParticles = showParticles;
    }
    
    public String getEffectName() {
        return Potion.potionTypes[this.potionID].getName();
    }
    
    @Override
    public String toString() {
        String s;
        if (this.getAmplifier() > 0) {
            s = this.getEffectName() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else {
            s = this.getEffectName() + ", Duration: " + this.getDuration();
        }
        if (this.isSplashPotion) {
            s += ", Splash: true";
        }
        if (!this.showParticles) {
            s += ", Particles: false";
        }
        return Potion.potionTypes[this.potionID].isUsable() ? ("(" + s + ")") : s;
    }
}
