package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class InventoryCrafting implements IInventory
{
    private final ItemStack[] stackList;
    private final int inventoryHeight;
    private final int inventoryWidth;
    private final Container eventHandler;
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackList[n] == null) {
            return null;
        }
        if (this.stackList[n].stackSize <= n2) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return itemStack;
        }
        final ItemStack splitStack = this.stackList[n].splitStack(n2);
        if (this.stackList[n].stackSize == 0) {
            this.stackList[n] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return splitStack;
    }
    
    @Override
    public String getName() {
        return "container.crafting";
    }
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stackList[n] != null) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            return itemStack;
        }
        return null;
    }
    
    public int getWidth() {
        return this.inventoryWidth;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return true;
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    public ItemStack getStackInRowAndColumn(final int n, final int n2) {
        return (n >= 0 && n < this.inventoryWidth && n2 >= 0 && n2 <= this.inventoryHeight) ? this.getStackInSlot(n + n2 * this.inventoryWidth) : null;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    public InventoryCrafting(final Container eventHandler, final int inventoryWidth, final int inventoryHeight) {
        this.stackList = new ItemStack[inventoryWidth * inventoryHeight];
        this.eventHandler = eventHandler;
        this.inventoryWidth = inventoryWidth;
        this.inventoryHeight = inventoryHeight;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= this.getSizeInventory()) ? null : this.stackList[n];
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }
    
    public int getHeight() {
        return this.inventoryHeight;
    }
    
    @Override
    public void clear() {
        while (0 < this.stackList.length) {
            this.stackList[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackList[n] = itemStack;
        this.eventHandler.onCraftMatrixChanged(this);
    }
}
