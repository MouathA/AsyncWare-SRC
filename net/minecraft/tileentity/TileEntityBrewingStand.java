package net.minecraft.tileentity;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityBrewingStand extends TileEntityLockable implements ISidedInventory, ITickable
{
    private ItemStack[] brewingItemStacks;
    private String customName;
    private boolean[] filledSlots;
    private Item ingredientID;
    private int brewTime;
    private static final int[] outputSlots;
    
    private void brewPotions() {
        if (this == null) {
            return;
        }
        final ItemStack itemStack = this.brewingItemStacks[3];
        while (true) {
            if (this.brewingItemStacks[0] != null && this.brewingItemStacks[0].getItem() == Items.potionitem) {
                final int metadata = this.brewingItemStacks[0].getMetadata();
                final int potionResult = this.getPotionResult(metadata, itemStack);
                final List effects = Items.potionitem.getEffects(metadata);
                final List effects2 = Items.potionitem.getEffects(potionResult);
                if ((metadata > 0 && effects == effects2) || (effects != null && (effects.equals(effects2) || effects2 == null))) {
                    if (!ItemPotion.isSplash(metadata) && ItemPotion.isSplash(potionResult)) {
                        this.brewingItemStacks[0].setItemDamage(potionResult);
                    }
                }
                else if (metadata != potionResult) {
                    this.brewingItemStacks[0].setItemDamage(potionResult);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP) ? TileEntityBrewingStand.inputSlots : TileEntityBrewingStand.outputSlots;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            this.brewingItemStacks[n] = itemStack;
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final byte byte1 = compoundTag.getByte("Slot");
            if (byte1 >= 0 && byte1 < this.brewingItemStacks.length) {
                this.brewingItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n = 0;
            ++n;
        }
        this.brewTime = nbtTagCompound.getShort("BrewTime");
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }
    
    private int getPotionResult(final int n, final ItemStack itemStack) {
        return (itemStack == null) ? n : (itemStack.getItem().isPotionIngredient(itemStack) ? PotionHelper.applyIngredient(n, itemStack.getItem().getPotionEffect(itemStack)) : n);
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.brewing";
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= 0 && n < this.brewingItemStacks.length) ? this.brewingItemStacks[n] : null;
    }
    
    @Override
    public void update() {
        if (this.brewTime > 0) {
            --this.brewTime;
            if (this.brewTime == 0) {
                this.brewPotions();
                this.markDirty();
            }
            else if (this != null) {
                this.brewTime = 0;
                this.markDirty();
            }
            else if (this.ingredientID != this.brewingItemStacks[3].getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        }
        else if (this != null) {
            this.brewTime = 400;
            this.ingredientID = this.brewingItemStacks[3].getItem();
        }
        if (!this.worldObj.isRemote) {
            final boolean[] func_174902_m = this.func_174902_m();
            if (!Arrays.equals(func_174902_m, this.filledSlots)) {
                this.filledSlots = func_174902_m;
                IBlockState blockState = this.worldObj.getBlockState(this.getPos());
                if (!(blockState.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }
                while (0 < BlockBrewingStand.HAS_BOTTLE.length) {
                    blockState = blockState.withProperty(BlockBrewingStand.HAS_BOTTLE[0], func_174902_m[0]);
                    int n = 0;
                    ++n;
                }
                this.worldObj.setBlockState(this.pos, blockState, 2);
            }
        }
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    public TileEntityBrewingStand() {
        this.brewingItemStacks = new ItemStack[4];
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("BrewTime", (short)this.brewTime);
        final NBTTagList list = new NBTTagList();
        while (0 < this.brewingItemStacks.length) {
            if (this.brewingItemStacks[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.brewingItemStacks[0].writeToNBT(nbtTagCompound2);
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
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    public boolean[] func_174902_m() {
        final boolean[] array = new boolean[3];
        while (true) {
            if (this.brewingItemStacks[0] != null) {
                array[0] = true;
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return (n == 3) ? itemStack.getItem().isPotionIngredient(itemStack) : (itemStack.getItem() == Items.potionitem || itemStack.getItem() == Items.glass_bottle);
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:brewing_stand";
    }
    
    @Override
    public void clear() {
        while (0 < this.brewingItemStacks.length) {
            this.brewingItemStacks[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }
    
    @Override
    public void setField(final int n, final int brewTime) {
        switch (n) {
            case 0: {
                this.brewTime = brewTime;
                break;
            }
        }
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBrewingStand(inventoryPlayer, this);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    static {
        TileEntityBrewingStand.inputSlots = new int[] { 3 };
        outputSlots = new int[] { 0, 1, 2 };
    }
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.brewTime;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
    
    @Override
    public int getFieldCount() {
        return 1;
    }
}
