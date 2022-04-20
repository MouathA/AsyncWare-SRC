package net.minecraft.entity.item;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EntityItemFrame extends EntityHanging
{
    private float itemDropChance;
    
    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(9);
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(8, 5);
        this.getDataWatcher().addObject(9, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.getDisplayedItem() != null) {
            nbtTagCompound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            nbtTagCompound.setByte("ItemRotation", (byte)this.getRotation());
            nbtTagCompound.setFloat("ItemDropChance", this.itemDropChance);
        }
        super.writeEntityToNBT(nbtTagCompound);
    }
    
    @Override
    public int getWidthPixels() {
        return 12;
    }
    
    public EntityItemFrame(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        super(world, blockPos);
        this.itemDropChance = 1.0f;
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    @Override
    public void onBroken(final Entity entity) {
        this.dropItemOrSelf(entity, true);
    }
    
    public void setDisplayedItem(final ItemStack itemStack) {
        this.setDisplayedItemWithUpdate(itemStack, true);
    }
    
    public EntityItemFrame(final World world) {
        super(world);
        this.itemDropChance = 1.0f;
    }
    
    @Override
    public int getHeightPixels() {
        return 12;
    }
    
    private void setDisplayedItemWithUpdate(ItemStack copy, final boolean b) {
        if (copy != null) {
            copy = copy.copy();
            copy.stackSize = 1;
            copy.setItemFrame(this);
        }
        this.getDataWatcher().updateObject(8, copy);
        this.getDataWatcher().setObjectWatched(8);
        if (b && this.hangingPosition != null) {
            this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.getDisplayedItem() == null) {
            final ItemStack heldItem = entityPlayer.getHeldItem();
            if (heldItem != null && !this.worldObj.isRemote) {
                this.setDisplayedItem(heldItem);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = heldItem;
                    if (--itemStack.stackSize <= 0) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                    }
                }
            }
        }
        else if (!this.worldObj.isRemote) {
            this.setItemRotation(this.getRotation() + 1);
        }
        return true;
    }
    
    private void removeFrameFromMap(final ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getItem() == Items.filled_map) {
                ((ItemMap)itemStack.getItem()).getMapData(itemStack, this.worldObj).mapDecorations.remove("frame-" + this.getEntityId());
            }
            itemStack.setItemFrame(null);
        }
    }
    
    public void dropItemOrSelf(final Entity entity, final boolean b) {
        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            final ItemStack displayedItem = this.getDisplayedItem();
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                this.removeFrameFromMap(displayedItem);
                return;
            }
            if (b) {
                this.entityDropItem(new ItemStack(Items.item_frame), 0.0f);
            }
            if (displayedItem != null && this.rand.nextFloat() < this.itemDropChance) {
                final ItemStack copy = displayedItem.copy();
                this.removeFrameFromMap(copy);
                this.entityDropItem(copy, 0.0f);
            }
        }
    }
    
    private void func_174865_a(final int n, final boolean b) {
        this.getDataWatcher().updateObject(9, (byte)(n % 8));
        if (b && this.hangingPosition != null) {
            this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = 16.0 * 64.0 * this.renderDistanceWeight;
        return n < n2 * n2;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (!damageSource.isExplosion() && this.getDisplayedItem() != null) {
            if (!this.worldObj.isRemote) {
                this.dropItemOrSelf(damageSource.getEntity(), false);
                this.setDisplayedItem(null);
            }
            return true;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public void setItemRotation(final int n) {
        this.func_174865_a(n, true);
    }
    
    public int func_174866_q() {
        return (this.getDisplayedItem() == null) ? 0 : (this.getRotation() % 8 + 1);
    }
    
    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(8);
    }
    
    @Override
    public float getCollisionBorderSize() {
        return 0.0f;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("Item");
        if (compoundTag != null && !compoundTag.hasNoTags()) {
            this.setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(compoundTag), false);
            this.func_174865_a(nbtTagCompound.getByte("ItemRotation"), false);
            if (nbtTagCompound.hasKey("ItemDropChance", 99)) {
                this.itemDropChance = nbtTagCompound.getFloat("ItemDropChance");
            }
            if (nbtTagCompound.hasKey("Direction")) {
                this.func_174865_a(this.getRotation() * 2, false);
            }
        }
        super.readEntityFromNBT(nbtTagCompound);
    }
}
