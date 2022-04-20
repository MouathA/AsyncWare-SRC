package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class Slot
{
    public int xDisplayPosition;
    public int yDisplayPosition;
    private final int slotIndex;
    public final IInventory inventory;
    public int slotNumber;
    
    public int getItemStackLimit(final ItemStack itemStack) {
        return this.getSlotStackLimit();
    }
    
    public void onSlotChange(final ItemStack itemStack, final ItemStack itemStack2) {
        if (itemStack != null && itemStack2 != null && itemStack.getItem() == itemStack2.getItem()) {
            final int n = itemStack2.stackSize - itemStack.stackSize;
            if (n > 0) {
                this.onCrafting(itemStack, n);
            }
        }
    }
    
    public Slot(final IInventory inventory, final int slotIndex, final int xDisplayPosition, final int yDisplayPosition) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.xDisplayPosition = xDisplayPosition;
        this.yDisplayPosition = yDisplayPosition;
    }
    
    public boolean canTakeStack(final EntityPlayer entityPlayer) {
        return true;
    }
    
    public String getSlotTexture() {
        return null;
    }
    
    public void onSlotChanged() {
        this.inventory.markDirty();
    }
    
    public boolean isItemValid(final ItemStack itemStack) {
        return true;
    }
    
    public boolean canBeHovered() {
        return true;
    }
    
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
    
    protected void onCrafting(final ItemStack itemStack) {
    }
    
    protected void onCrafting(final ItemStack itemStack, final int n) {
    }
    
    public void putStack(final ItemStack itemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
        this.onSlotChanged();
    }
    
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public ItemStack decrStackSize(final int n) {
        return this.inventory.decrStackSize(this.slotIndex, n);
    }
    
    public boolean isHere(final IInventory inventory, final int n) {
        return inventory == this.inventory && n == this.slotIndex;
    }
    
    public boolean getHasStack() {
        return this.getStack() != null;
    }
    
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onSlotChanged();
    }
}
