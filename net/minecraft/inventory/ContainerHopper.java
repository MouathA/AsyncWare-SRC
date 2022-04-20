package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ContainerHopper extends Container
{
    private final IInventory hopperInventory;
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.hopperInventory.closeInventory(entityPlayer);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.hopperInventory.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.hopperInventory.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0, this.hopperInventory.getSizeInventory(), false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }
    
    public ContainerHopper(final InventoryPlayer inventoryPlayer, final IInventory hopperInventory, final EntityPlayer entityPlayer) {
        (this.hopperInventory = hopperInventory).openInventory(entityPlayer);
        while (0 < hopperInventory.getSizeInventory()) {
            this.addSlotToContainer(new Slot(hopperInventory, 0, 44, 20));
            int n = 0;
            ++n;
        }
        while (true) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 51));
            int n2 = 0;
            ++n2;
        }
    }
}
