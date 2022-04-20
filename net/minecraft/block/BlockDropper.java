package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem dropBehavior;
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDropper();
    }
    
    public BlockDropper() {
        this.dropBehavior = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    protected void dispense(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int dispenseSlot = tileEntityDispenser.getDispenseSlot();
            if (dispenseSlot < 0) {
                world.playAuxSFX(1001, blockPos, 0);
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(dispenseSlot);
                if (stackInSlot != null) {
                    final EnumFacing enumFacing = (EnumFacing)world.getBlockState(blockPos).getValue(BlockDropper.FACING);
                    final BlockPos offset = blockPos.offset(enumFacing);
                    final IInventory inventoryAtPosition = TileEntityHopper.getInventoryAtPosition(world, offset.getX(), offset.getY(), offset.getZ());
                    ItemStack itemStack;
                    if (inventoryAtPosition == null) {
                        itemStack = this.dropBehavior.dispense(blockSourceImpl, stackInSlot);
                        if (itemStack != null && itemStack.stackSize <= 0) {
                            itemStack = null;
                        }
                    }
                    else if (TileEntityHopper.putStackInInventoryAllSlots(inventoryAtPosition, stackInSlot.copy().splitStack(1), enumFacing.getOpposite()) == null) {
                        final ItemStack copy;
                        itemStack = (copy = stackInSlot.copy());
                        if (--copy.stackSize <= 0) {
                            itemStack = null;
                        }
                    }
                    else {
                        itemStack = stackInSlot.copy();
                    }
                    tileEntityDispenser.setInventorySlotContents(dispenseSlot, itemStack);
                }
            }
        }
    }
    
    @Override
    protected IBehaviorDispenseItem getBehavior(final ItemStack itemStack) {
        return this.dropBehavior;
    }
}
