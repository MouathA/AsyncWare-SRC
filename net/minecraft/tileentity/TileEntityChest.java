package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityChest extends TileEntityLockable implements IInventory, ITickable
{
    public TileEntityChest adjacentChestXPos;
    private int cachedChestType;
    public float lidAngle;
    public TileEntityChest adjacentChestZNeg;
    public TileEntityChest adjacentChestXNeg;
    private ItemStack[] chestContents;
    private int ticksSinceSync;
    public float prevLidAngle;
    private String customName;
    public int numPlayersUsing;
    public TileEntityChest adjacentChestZPos;
    public boolean adjacentChestChecked;
    
    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }
    
    public TileEntityChest() {
        this.chestContents = new ItemStack[27];
        this.cachedChestType = -1;
    }
    
    protected TileEntityChest getAdjacentChest(final EnumFacing enumFacing) {
        final BlockPos offset = this.pos.offset(enumFacing);
        if (offset == null) {
            final TileEntity tileEntity = this.worldObj.getTileEntity(offset);
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                tileEntityChest.func_174910_a(this, enumFacing.getOpposite());
                return tileEntityChest;
            }
        }
        return null;
    }
    
    @Override
    public void update() {
        this.checkForAdjacentChests();
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        ++this.ticksSinceSync;
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + x + y + z) % 200 == 0) {
            this.numPlayersUsing = 0;
            final float n = 5.0f;
            for (final EntityPlayer entityPlayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x - n, y - n, z - n, x + 1 + n, y + 1 + n, z + 1 + n))) {
                if (entityPlayer.openContainer instanceof ContainerChest) {
                    final IInventory lowerChestInventory = ((ContainerChest)entityPlayer.openContainer).getLowerChestInventory();
                    if (lowerChestInventory != this && (!(lowerChestInventory instanceof InventoryLargeChest) || !((InventoryLargeChest)lowerChestInventory).isPartOfLargeChest(this))) {
                        continue;
                    }
                    ++this.numPlayersUsing;
                }
            }
        }
        this.prevLidAngle = this.lidAngle;
        final float n2 = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double n3 = x + 0.5;
            double n4 = z + 0.5;
            if (this.adjacentChestZPos != null) {
                n4 += 0.5;
            }
            if (this.adjacentChestXPos != null) {
                n3 += 0.5;
            }
            this.worldObj.playSoundEffect(n3, y + 0.5, n4, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float lidAngle = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += n2;
            }
            else {
                this.lidAngle -= n2;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float n5 = 0.5f;
            if (this.lidAngle < n5 && lidAngle >= n5 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                double n6 = x + 0.5;
                double n7 = z + 0.5;
                if (this.adjacentChestZPos != null) {
                    n7 += 0.5;
                }
                if (this.adjacentChestXPos != null) {
                    n6 += 0.5;
                }
                this.worldObj.playSoundEffect(n6, y + 0.5, n7, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    @Override
    public void clear() {
        while (0 < this.chestContents.length) {
            this.chestContents[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int numPlayersUsing) {
        if (n == 1) {
            this.numPlayersUsing = numPlayersUsing;
            return true;
        }
        return super.receiveClientEvent(n, numPlayersUsing);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.isSpectator() && this.getBlockType() instanceof BlockChest) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.chestContents[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
            this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
            this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
            this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
        }
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.chestContents.length) {
            if (this.chestContents[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.chestContents[0].writeToNBT(nbtTagCompound2);
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
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.chestContents.length) {
                this.chestContents[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    public TileEntityChest(final int cachedChestType) {
        this.chestContents = new ItemStack[27];
        this.cachedChestType = cachedChestType;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    public int getChestType() {
        if (this.cachedChestType == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }
            this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
        }
        return this.cachedChestType;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.chestContents[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.chestContents[n] == null) {
            return null;
        }
        if (this.chestContents[n].stackSize <= n2) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.chestContents[n].splitStack(n2);
        if (this.chestContents[n].stackSize == 0) {
            this.chestContents[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:chest";
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.chestContents[n] != null) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.chest";
    }
    
    private void func_174910_a(final TileEntityChest tileEntityChest, final EnumFacing enumFacing) {
        if (tileEntityChest.isInvalid()) {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked) {
            switch (TileEntityChest$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    if (this.adjacentChestZNeg != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.adjacentChestZPos != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.adjacentChestXPos != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.adjacentChestXNeg != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
}
