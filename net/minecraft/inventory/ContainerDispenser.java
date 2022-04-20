package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerDispenser extends Container
{
    private IInventory dispenserInventory;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.dispenserInventory.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < 9) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0, 9, false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    public ContainerDispenser(final IInventory inventory, final IInventory dispenserInventory) {
        this.dispenserInventory = dispenserInventory;
        while (true) {
            this.addSlotToContainer(new Slot(dispenserInventory, 0, 62, 17));
            int n = 0;
            ++n;
        }
    }
}
