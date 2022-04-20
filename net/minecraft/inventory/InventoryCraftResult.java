package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] stackResult;
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackResult[0] != null) {
            final ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.stackResult[0];
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void clear() {
        while (0 < this.stackResult.length) {
            this.stackResult[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stackResult[0] != null) {
            final ItemStack itemStack = this.stackResult[0];
            this.stackResult[0] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public String getName() {
        return "Result";
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackResult[0] = itemStack;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return true;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    public InventoryCraftResult() {
        this.stackResult = new ItemStack[1];
    }
}
