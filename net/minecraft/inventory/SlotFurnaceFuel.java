package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;

public class SlotFurnaceFuel extends Slot
{
    public SlotFurnaceFuel(final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
    }
    
    @Override
    public int getItemStackLimit(final ItemStack itemStack) {
        return (itemStack != null) ? 1 : super.getItemStackLimit(itemStack);
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return TileEntityFurnace.isItemFuel(itemStack) || itemStack != null;
    }
}
