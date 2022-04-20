package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.server.management.*;
import net.minecraft.world.*;
import java.util.*;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable
{
    protected EntityAISit aiSit;
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("showDeathMessages") && this.hasCustomName() && this.getOwner() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.getOwner()).addChatMessage(this.getCombatTracker().getDeathMessage());
        }
        super.onDeath(damageSource);
    }
    
    @Override
    public Team getTeam() {
        if (this != 0) {
            final EntityLivingBase owner = this.getOwner();
            if (owner != null) {
                return owner.getTeam();
            }
        }
        return super.getTeam();
    }
    
    protected void playTameEffect(final boolean b) {
        EnumParticleTypes enumParticleTypes = EnumParticleTypes.HEART;
        if (!b) {
            enumParticleTypes = EnumParticleTypes.SMOKE_NORMAL;
        }
        while (true) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    public EntityAISit getAISit() {
        return this.aiSit;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, "");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.getOwnerId() == null) {
            nbtTagCompound.setString("OwnerUUID", "");
        }
        else {
            nbtTagCompound.setString("OwnerUUID", this.getOwnerId());
        }
        nbtTagCompound.setBoolean("Sitting", this.isSitting());
    }
    
    @Override
    public Entity getOwner() {
        return this.getOwner();
    }
    
    @Override
    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(17);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        String ownerId;
        if (nbtTagCompound.hasKey("OwnerUUID", 8)) {
            ownerId = nbtTagCompound.getString("OwnerUUID");
        }
        else {
            ownerId = PreYggdrasilConverter.getStringUUIDFromName(nbtTagCompound.getString("Owner"));
        }
        if (ownerId.length() > 0) {
            this.setOwnerId(ownerId);
            this.setTamed(true);
        }
        this.aiSit.setSitting(nbtTagCompound.getBoolean("Sitting"));
        this.setSitting(nbtTagCompound.getBoolean("Sitting"));
    }
    
    protected void setupTamedAI() {
    }
    
    public boolean shouldAttackEntity(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return true;
    }
    
    public EntityTameable(final World world) {
        super(world);
        this.aiSit = new EntityAISit(this);
        this.setupTamedAI();
    }
    
    public boolean isSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    @Override
    public EntityLivingBase getOwner() {
        final UUID fromString = UUID.fromString(this.getOwnerId());
        return (fromString == null) ? null : this.worldObj.getPlayerEntityByUUID(fromString);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 7) {
            this.playTameEffect(true);
        }
        else if (b == 6) {
            this.playTameEffect(false);
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public boolean isOwner(final EntityLivingBase entityLivingBase) {
        return entityLivingBase == this.getOwner();
    }
    
    public void setSitting(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFE));
        }
    }
    
    public void setTamed(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x4));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFB));
        }
        this.setupTamedAI();
    }
    
    @Override
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        if (this != 0) {
            final EntityLivingBase owner = this.getOwner();
            if (entityLivingBase == owner) {
                return true;
            }
            if (owner != null) {
                return owner.isOnSameTeam(entityLivingBase);
            }
        }
        return super.isOnSameTeam(entityLivingBase);
    }
    
    public void setOwnerId(final String s) {
        this.dataWatcher.updateObject(17, s);
    }
}
