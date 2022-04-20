package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;

public class ItemSoup extends ItemFood
{
    public ItemSoup(final int n) {
        super(n, false);
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        super.onItemUseFinish(itemStack, world, entityPlayer);
        return new ItemStack(Items.bowl);
    }
}
