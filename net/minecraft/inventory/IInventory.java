package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public interface IInventory extends IWorldNameable
{
    int getSizeInventory();
    
    int getFieldCount();
    
    void markDirty();
    
    void openInventory(final EntityPlayer p0);
    
    int getField(final int p0);
    
    void setField(final int p0, final int p1);
    
    ItemStack getStackInSlot(final int p0);
    
    void clear();
    
    boolean isUseableByPlayer(final EntityPlayer p0);
    
    void closeInventory(final EntityPlayer p0);
    
    ItemStack decrStackSize(final int p0, final int p1);
    
    void setInventorySlotContents(final int p0, final ItemStack p1);
    
    int getInventoryStackLimit();
    
    ItemStack removeStackFromSlot(final int p0);
    
    boolean isItemValidForSlot(final int p0, final ItemStack p1);
}
