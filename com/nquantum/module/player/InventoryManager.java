package com.nquantum.module.player;

import java.util.*;
import com.nquantum.util.time.*;
import net.minecraft.item.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import net.minecraft.enchantment.*;
import com.nquantum.module.*;

public class InventoryManager extends Module
{
    private boolean someboolean;
    public Random rand;
    private double numberIdkWillfigureout;
    public Timer timer;
    private int slots;
    
    public static boolean isItemBad(final ItemStack itemStack) {
        return itemStack != null && (itemStack.getItem().getUnlocalizedName().contains("TNT") || itemStack.getItem().getUnlocalizedName().contains("stick") || itemStack.getItem().getUnlocalizedName().contains("egg") || itemStack.getItem().getUnlocalizedName().contains("string") || itemStack.getItem().getUnlocalizedName().contains("flint") || itemStack.getItem().getUnlocalizedName().contains("compass") || itemStack.getItem().getUnlocalizedName().contains("feather") || itemStack.getItem().getUnlocalizedName().contains("bucket") || itemStack.getItem().getUnlocalizedName().contains("chest") || itemStack.getItem().getUnlocalizedName().contains("snowball") || itemStack.getItem().getUnlocalizedName().contains("fish") || itemStack.getItem().getUnlocalizedName().contains("enchant") || itemStack.getItem().getUnlocalizedName().contains("exp") || itemStack.getItem() instanceof ItemPickaxe || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || (itemStack.getItem().getUnlocalizedName().contains("potion") && itemStack == null));
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Inventory Manager");
    }
    
    private static double getEnchantmentOnSword(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0;
        }
        if (!(itemStack.getItem() instanceof ItemSword)) {
            return 0.0;
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) + ((ItemSword)itemStack.getItem()).getItemEnchantability();
    }
    
    public InventoryManager() {
        super("InventoryManager", 0, Category.PLAYER);
        this.rand = new Random();
        this.timer = new Timer();
    }
}
