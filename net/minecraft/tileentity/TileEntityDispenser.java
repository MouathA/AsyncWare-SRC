package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;

public class TileEntityDispenser extends TileEntityLockable implements IInventory
{
    private static final Random RNG;
    private ItemStack[] stacks;
    protected String customName;
    
    public int getDispenseSlot() {
        while (0 < this.stacks.length) {
            if (this.stacks[0] != null) {
                final Random rng = TileEntityDispenser.RNG;
                final int n = 1;
                int n2 = 0;
                ++n2;
                if (rng.nextInt(n) == 0) {}
            }
            int n3 = 0;
            ++n3;
        }
        return -1;
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stacks[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stacks[n] == null) {
            return null;
        }
        if (this.stacks[n].stackSize <= n2) {
            final ItemStack itemStack = this.stacks[n];
            this.stacks[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.stacks[n].splitStack(n2);
        if (this.stacks[n].stackSize == 0) {
            this.stacks[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    public int addItemStack(final ItemStack itemStack) {
        while (0 < this.stacks.length) {
            if (this.stacks[0] == null || this.stacks[0].getItem() == null) {
                this.setInventorySlotContents(0, itemStack);
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @Override
    public void clear() {
        while (0 < this.stacks.length) {
            this.stacks[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:dispenser";
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.stacks[n];
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.stacks = new ItemStack[this.getSizeInventory()];
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.stacks.length) {
                this.stacks[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n2 = 0;
            ++n2;
        }
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    static {
        RNG = new Random();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.dispenser";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerDispenser(inventoryPlayer, this);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.stacks.length) {
            if (this.stacks[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.stacks[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
        if (this != null) {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stacks[n] != null) {
            final ItemStack itemStack = this.stacks[n];
            this.stacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    public TileEntityDispenser() {
        this.stacks = new ItemStack[9];
    }
}
