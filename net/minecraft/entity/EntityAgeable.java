package net.minecraft.entity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class EntityAgeable extends EntityCreature
{
    private float ageHeight;
    private float ageWidth;
    protected int field_175503_c;
    protected int field_175502_b;
    protected int growingAge;
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setGrowingAge(nbtTagCompound.getInteger("Age"));
        this.field_175502_b = nbtTagCompound.getInteger("ForcedAge");
    }
    
    public void setScaleForAge(final boolean b) {
        this.setScale(b ? 0.5f : 1.0f);
    }
    
    public int getGrowingAge() {
        return this.worldObj.isRemote ? this.dataWatcher.getWatchableObjectByte(12) : this.growingAge;
    }
    
    public EntityAgeable(final World world) {
        super(world);
        this.ageWidth = -1.0f;
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.spawn_egg) {
            if (!this.worldObj.isRemote) {
                final Class classFromID = EntityList.getClassFromID(currentItem.getMetadata());
                if (classFromID != null && this.getClass() == classFromID) {
                    final EntityAgeable child = this.createChild(this);
                    if (child != null) {
                        child.setGrowingAge(-24000);
                        child.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                        this.worldObj.spawnEntityInWorld(child);
                        if (currentItem.hasDisplayName()) {
                            child.setCustomNameTag(currentItem.getDisplayName());
                        }
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = currentItem;
                            --itemStack.stackSize;
                            if (currentItem.stackSize <= 0) {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    protected void onGrowingAdult() {
    }
    
    public void addGrowth(final int n) {
        this.func_175501_a(n, false);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Age", this.getGrowingAge());
        nbtTagCompound.setInteger("ForcedAge", this.field_175502_b);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            if (this.field_175503_c > 0) {
                if (this.field_175503_c % 4 == 0) {
                    this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0, new int[0]);
                }
                --this.field_175503_c;
            }
            this.setScaleForAge(this.isChild());
        }
        else {
            int growingAge = this.getGrowingAge();
            if (growingAge < 0) {
                ++growingAge;
                this.setGrowingAge(growingAge);
                if (growingAge == 0) {
                    this.onGrowingAdult();
                }
            }
            else if (growingAge > 0) {
                --growingAge;
                this.setGrowingAge(growingAge);
            }
        }
    }
    
    public abstract EntityAgeable createChild(final EntityAgeable p0);
    
    @Override
    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }
    
    public void setGrowingAge(final int growingAge) {
        this.dataWatcher.updateObject(12, (byte)MathHelper.clamp_int(growingAge, -1, 1));
        this.growingAge = growingAge;
        this.setScaleForAge(this.isChild());
    }
    
    protected final void setScale(final float n) {
        super.setSize(this.ageWidth * n, this.ageHeight * n);
    }
    
    public void func_175501_a(final int n, final boolean b) {
        this.getGrowingAge();
        this.setGrowingAge(0);
        if (b) {
            this.field_175502_b += 0;
            if (this.field_175503_c == 0) {
                this.field_175503_c = 40;
            }
        }
        if (this.getGrowingAge() == 0) {
            this.setGrowingAge(this.field_175502_b);
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, 0);
    }
    
    @Override
    protected final void setSize(final float ageWidth, final float ageHeight) {
        final boolean b = this.ageWidth > 0.0f;
        this.ageWidth = ageWidth;
        this.ageHeight = ageHeight;
        if (!b) {
            this.setScale(1.0f);
        }
    }
}
