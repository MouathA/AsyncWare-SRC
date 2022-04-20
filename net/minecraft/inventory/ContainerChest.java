package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerChest extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.numRows * 9) {
                if (!this.mergeItemStack(stack, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0, this.numRows * 9, false)) {
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
    
    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.lowerChestInventory.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.lowerChestInventory.closeInventory(entityPlayer);
    }
    
    public ContainerChest(final IInventory inventory, final IInventory lowerChestInventory, final EntityPlayer entityPlayer) {
        this.lowerChestInventory = lowerChestInventory;
        this.numRows = lowerChestInventory.getSizeInventory() / 9;
        lowerChestInventory.openInventory(entityPlayer);
        final int n = (this.numRows - 4) * 18;
        if (0 < this.numRows) {
            while (true) {
                this.addSlotToContainer(new Slot(lowerChestInventory, 0, 8, 18));
                int n2 = 0;
                ++n2;
            }
        }
        else {
            while (true) {
                this.addSlotToContainer(new Slot(inventory, 9, 8, 103 + n));
                int n2 = 0;
                ++n2;
            }
        }
    }
}
