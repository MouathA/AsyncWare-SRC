package net.minecraft.inventory;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class ContainerHorseInventory extends Container
{
    private IInventory horseInventory;
    private EntityHorse theHorse;
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.horseInventory.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (this.getSlot(1).isItemValid(stack) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(stack, 1, 2, false)) {
                    return null;
                }
            }
            else if (this.getSlot(0).isItemValid(stack)) {
                if (!this.mergeItemStack(stack, 0, 1, false)) {
                    return null;
                }
            }
            else if (this.horseInventory.getSizeInventory() <= 2 || !this.mergeItemStack(stack, 2, this.horseInventory.getSizeInventory(), false)) {
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
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.horseInventory.closeInventory(entityPlayer);
    }
    
    public ContainerHorseInventory(final IInventory inventory, final IInventory horseInventory, final EntityHorse theHorse, final EntityPlayer entityPlayer) {
        this.horseInventory = horseInventory;
        this.theHorse = theHorse;
        horseInventory.openInventory(entityPlayer);
        this.addSlotToContainer(new Slot(this, horseInventory, 0, 8, 18) {
            final ContainerHorseInventory this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return super.isItemValid(itemStack) && itemStack.getItem() == Items.saddle && !this.getHasStack();
            }
        });
        this.addSlotToContainer(new Slot(this, horseInventory, 1, 8, 36, theHorse) {
            final EntityHorse val$horse;
            final ContainerHorseInventory this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return super.isItemValid(itemStack) && this.val$horse.canWearArmor() && EntityHorse.isArmorItem(itemStack.getItem());
            }
            
            @Override
            public boolean canBeHovered() {
                return this.val$horse.canWearArmor();
            }
        });
        if (theHorse.isChested()) {
            while (true) {
                this.addSlotToContainer(new Slot(horseInventory, 2, 80, 18));
                int n = 0;
                ++n;
            }
        }
        else {
            while (true) {
                this.addSlotToContainer(new Slot(inventory, 9, 8, 84));
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.horseInventory.isUseableByPlayer(entityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(entityPlayer) < 8.0f;
    }
}
