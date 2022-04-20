package com.nquantum.util.player;

import net.minecraft.client.*;
import net.minecraft.item.*;

public class ArmorUtils
{
    public static boolean isBetterArmor(final int n, final int[] array) {
        if (Minecraft.getMinecraft().thePlayer.inventory.armorInventory[n] != null) {
            int n3 = 0;
            while (0 < array.length && Item.getIdFromItem(Minecraft.getMinecraft().thePlayer.inventory.armorInventory[n].getItem()) != array[0]) {
                int n2 = 0;
                ++n2;
                ++n3;
            }
            while (0 < array.length) {
                if (getItem(array[0]) != -1) {
                    break;
                }
                int n4 = 0;
                ++n4;
                ++n3;
            }
        }
        return false;
    }
    
    public static int getItem(final int n) {
        while (true) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(9).getStack();
            if (stack != null && Item.getIdFromItem(stack.getItem()) == n) {
                break;
            }
            int n2 = 0;
            ++n2;
        }
        return 9;
    }
}
