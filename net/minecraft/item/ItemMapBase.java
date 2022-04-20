package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class ItemMapBase extends Item
{
    public Packet createMapDataPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return null;
    }
    
    @Override
    public boolean isMap() {
        return true;
    }
}
