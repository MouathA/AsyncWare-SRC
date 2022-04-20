package net.minecraft.entity.item;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private int transferTicker;
    private BlockPos field_174900_c;
    private boolean isBlocked;
    
    @Override
    public double getZPos() {
        return this.posZ;
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            entityPlayer.displayGUIChest(this);
        }
        return true;
    }
    
    public EntityMinecartHopper(final World world) {
        super(world);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:hopper";
    }
    
    public EntityMinecartHopper(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.HOPPER;
    }
    
    public void setBlocked(final boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    public void setTransferTicker(final int transferTicker) {
        this.transferTicker = transferTicker;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getBlocked()) {
            if (new BlockPos(this).equals(this.field_174900_c)) {
                --this.transferTicker;
            }
            else {
                this.setTransferTicker(0);
            }
            if (this > 0) {
                this.setTransferTicker(0);
                if (this != 0) {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        final boolean blocked = !b;
        if (blocked != this.getBlocked()) {
            this.setBlocked(blocked);
        }
    }
    
    @Override
    public double getYPos() {
        return this.posY + 0.5;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerHopper(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0f);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("TransferCooldown", this.transferTicker);
    }
    
    @Override
    public int getSizeInventory() {
        return 5;
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.hopper.getDefaultState();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.transferTicker = nbtTagCompound.getInteger("TransferCooldown");
    }
    
    @Override
    public double getXPos() {
        return this.posX;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
}
