package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;

public class ContainerPlayer extends Container
{
    public boolean isLocalWorld;
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private final EntityPlayer thePlayer;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return true;
    }
    
    public ContainerPlayer(final InventoryPlayer inventoryPlayer, final boolean isLocalWorld, final EntityPlayer thePlayer) {
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = isLocalWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        while (true) {
            this.addSlotToContainer(new Slot(this.craftMatrix, 0, 88, 26));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        while (true) {
            final ItemStack removeStackFromSlot = this.craftMatrix.removeStackFromSlot(0);
            if (removeStackFromSlot != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, false);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n >= 1 && n < 5) {
                if (!this.mergeItemStack(stack, 9, 45, false)) {
                    return null;
                }
            }
            else if (n >= 5 && n < 9) {
                if (!this.mergeItemStack(stack, 9, 45, false)) {
                    return null;
                }
            }
            else if (copy.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)copy.getItem()).armorType)).getHasStack()) {
                final int n2 = 5 + ((ItemArmor)copy.getItem()).armorType;
                if (!this.mergeItemStack(stack, n2, n2 + 1, false)) {
                    return null;
                }
            }
            else if (n >= 9 && n < 36) {
                if (!this.mergeItemStack(stack, 36, 45, false)) {
                    return null;
                }
            }
            else if (n >= 36 && n < 45) {
                if (!this.mergeItemStack(stack, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 9, 45, false)) {
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
}
