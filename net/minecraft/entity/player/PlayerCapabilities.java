package net.minecraft.entity.player;

import net.minecraft.nbt.*;

public class PlayerCapabilities
{
    public boolean allowFlying;
    public boolean disableDamage;
    public boolean isCreativeMode;
    public boolean allowEdit;
    private float flySpeed;
    public boolean isFlying;
    private float walkSpeed;
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
    
    public void writeCapabilitiesToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setBoolean("invulnerable", this.disableDamage);
        nbtTagCompound2.setBoolean("flying", this.isFlying);
        nbtTagCompound2.setBoolean("mayfly", this.allowFlying);
        nbtTagCompound2.setBoolean("instabuild", this.isCreativeMode);
        nbtTagCompound2.setBoolean("mayBuild", this.allowEdit);
        nbtTagCompound2.setFloat("flySpeed", this.flySpeed);
        nbtTagCompound2.setFloat("walkSpeed", this.walkSpeed);
        nbtTagCompound.setTag("abilities", nbtTagCompound2);
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public PlayerCapabilities() {
        this.allowEdit = true;
        this.flySpeed = 0.05f;
        this.walkSpeed = 0.1f;
    }
    
    public void setPlayerWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public void readCapabilitiesFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("abilities", 10)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("abilities");
            this.disableDamage = compoundTag.getBoolean("invulnerable");
            this.isFlying = compoundTag.getBoolean("flying");
            this.allowFlying = compoundTag.getBoolean("mayfly");
            this.isCreativeMode = compoundTag.getBoolean("instabuild");
            if (compoundTag.hasKey("flySpeed", 99)) {
                this.flySpeed = compoundTag.getFloat("flySpeed");
                this.walkSpeed = compoundTag.getFloat("walkSpeed");
            }
            if (compoundTag.hasKey("mayBuild", 1)) {
                this.allowEdit = compoundTag.getBoolean("mayBuild");
            }
        }
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
}
