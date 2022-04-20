package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.*;

public class ContainerWorkbench extends Container
{
    private World worldObj;
    private BlockPos pos;
    public IInventory craftResult;
    public InventoryCrafting craftMatrix;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.worldObj.getBlockState(this.pos).getBlock() == Blocks.crafting_table && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (this.worldObj.isRemote) {
            return;
        }
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
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
    }
    
    public ContainerWorkbench(final InventoryPlayer inventoryPlayer, final World worldObj, final BlockPos pos) {
        this.craftMatrix = new InventoryCrafting(this, 3, 3);
        this.craftResult = new InventoryCraftResult();
        this.worldObj = worldObj;
        this.pos = pos;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        while (true) {
            this.addSlotToContainer(new Slot(this.craftMatrix, 0, 30, 17));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 10, 46, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n >= 10 && n < 37) {
                if (!this.mergeItemStack(stack, 37, 46, false)) {
                    return null;
                }
            }
            else if (n >= 37 && n < 46) {
                if (!this.mergeItemStack(stack, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 10, 46, false)) {
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
