package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest associatedChest;
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return (this.associatedChest == null || this.associatedChest.canBeUsed(entityPlayer)) && super.isUseableByPlayer(entityPlayer);
    }
    
    public void loadInventoryFromNBT(final NBTTagList list) {
        int n = 0;
        while (0 < this.getSizeInventory()) {
            this.setInventorySlotContents(0, null);
            ++n;
        }
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final int n2 = compoundTag.getByte("Slot") & 0xFF;
            if (n2 >= 0 && n2 < this.getSizeInventory()) {
                this.setInventorySlotContents(n2, ItemStack.loadItemStackFromNBT(compoundTag));
            }
            ++n;
        }
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.closeChest();
        }
        super.closeInventory(entityPlayer);
        this.associatedChest = null;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.openChest();
        }
        super.openInventory(entityPlayer);
    }
    
    public NBTTagList saveInventoryToNBT() {
        final NBTTagList list = new NBTTagList();
        while (0 < this.getSizeInventory()) {
            final ItemStack stackInSlot = this.getStackInSlot(0);
            if (stackInSlot != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte)0);
                stackInSlot.writeToNBT(nbtTagCompound);
                list.appendTag(nbtTagCompound);
            }
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public void setChestTileEntity(final TileEntityEnderChest associatedChest) {
        this.associatedChest = associatedChest;
    }
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
}
