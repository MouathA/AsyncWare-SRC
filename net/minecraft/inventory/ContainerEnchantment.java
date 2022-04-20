package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class ContainerEnchantment extends Container
{
    public int[] enchantLevels;
    private World worldPointer;
    private BlockPos position;
    public IInventory tableInventory;
    public int[] field_178151_h;
    private Random rand;
    public int xpSeed;
    
    private List func_178148_a(final ItemStack itemStack, final int n, final int n2) {
        this.rand.setSeed(this.xpSeed + n);
        final List buildEnchantmentList = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, n2);
        if (itemStack.getItem() == Items.book && buildEnchantmentList != null && buildEnchantmentList.size() > 1) {
            buildEnchantmentList.remove(this.rand.nextInt(buildEnchantmentList.size()));
        }
        return buildEnchantmentList;
    }
    
    @Override
    public void updateProgressBar(final int n, final int xpSeed) {
        if (n >= 0 && n <= 2) {
            this.enchantLevels[n] = xpSeed;
        }
        else if (n == 3) {
            this.xpSeed = xpSeed;
        }
        else if (n >= 4 && n <= 6) {
            this.field_178151_h[n - 4] = xpSeed;
        }
        else {
            super.updateProgressBar(n, xpSeed);
        }
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.worldPointer.isRemote) {
            while (0 < this.tableInventory.getSizeInventory()) {
                final ItemStack removeStackFromSlot = this.tableInventory.removeStackFromSlot(0);
                if (removeStackFromSlot != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, false);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot(0);
        final ItemStack stackInSlot2 = this.tableInventory.getStackInSlot(1);
        final int n2 = n + 1;
        if ((stackInSlot2 == null || stackInSlot2.stackSize < n2) && !entityPlayer.capabilities.isCreativeMode) {
            return false;
        }
        if (this.enchantLevels[n] > 0 && stackInSlot != null && ((entityPlayer.experienceLevel >= n2 && entityPlayer.experienceLevel >= this.enchantLevels[n]) || entityPlayer.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isRemote) {
                final List func_178148_a = this.func_178148_a(stackInSlot, n, this.enchantLevels[n]);
                final boolean b = stackInSlot.getItem() == Items.book;
                if (func_178148_a != null) {
                    entityPlayer.removeExperienceLevel(n2);
                    if (b) {
                        stackInSlot.setItem(Items.enchanted_book);
                    }
                    while (0 < func_178148_a.size()) {
                        final EnchantmentData enchantmentData = func_178148_a.get(0);
                        if (b) {
                            Items.enchanted_book.addEnchantment(stackInSlot, enchantmentData);
                        }
                        else {
                            stackInSlot.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                        }
                        int n3 = 0;
                        ++n3;
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = stackInSlot2;
                        itemStack.stackSize -= n2;
                        if (stackInSlot2.stackSize <= 0) {
                            this.tableInventory.setInventorySlotContents(1, null);
                        }
                    }
                    entityPlayer.triggerAchievement(StatList.field_181739_W);
                    this.tableInventory.markDirty();
                    this.xpSeed = entityPlayer.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return null;
                }
            }
            else if (n == 1) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return null;
                }
            }
            else if (stack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE) {
                if (!this.mergeItemStack(stack, 1, 2, true)) {
                    return null;
                }
            }
            else {
                if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(stack)) {
                    return null;
                }
                if (stack.hasTagCompound() && stack.stackSize == 1) {
                    this.inventorySlots.get(0).putStack(stack.copy());
                    stack.stackSize = 0;
                }
                else if (stack.stackSize >= 1) {
                    this.inventorySlots.get(0).putStack(new ItemStack(stack.getItem(), 1, stack.getMetadata()));
                    final ItemStack itemStack = stack;
                    --itemStack.stackSize;
                }
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
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.worldPointer.getBlockState(this.position).getBlock() == Blocks.enchanting_table && entityPlayer.getDistanceSq(this.position.getX() + 0.5, this.position.getY() + 0.5, this.position.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        crafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
        crafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
        crafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
        crafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
    }
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World worldPointer, final BlockPos position) {
        this.tableInventory = new InventoryBasic(this, "Enchant", true, 2) {
            final ContainerEnchantment this$0;
            
            @Override
            public int getInventoryStackLimit() {
                return 64;
            }
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
        };
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.field_178151_h = new int[] { -1, -1, -1 };
        this.worldPointer = worldPointer;
        this.position = position;
        this.xpSeed = inventoryPlayer.player.getXPSeed();
        this.addSlotToContainer(new Slot(this, this.tableInventory, 0, 15, 47) {
            final ContainerEnchantment this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return true;
            }
            
            @Override
            public int getSlotStackLimit() {
                return 1;
            }
        });
        this.addSlotToContainer(new Slot(this, this.tableInventory, 1, 35, 47) {
            final ContainerEnchantment this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return itemStack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(itemStack.getMetadata()) == EnumDyeColor.BLUE;
            }
        });
        while (true) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
            int n = 0;
            ++n;
        }
    }
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        if (inventory == this.tableInventory) {
            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.isItemEnchantable()) {
                if (!this.worldPointer.isRemote) {
                    while (true) {
                        int n = 0;
                        ++n;
                    }
                }
            }
            else {
                while (true) {
                    this.enchantLevels[0] = 0;
                    this.field_178151_h[0] = -1;
                    int n2 = 0;
                    ++n2;
                }
            }
        }
    }
    
    public int getLapisAmount() {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot(1);
        return (stackInSlot == null) ? 0 : stackInSlot.stackSize;
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        while (0 < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(0);
            crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
            crafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
            crafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
            crafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
            crafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
            int n = 0;
            ++n;
        }
    }
}
