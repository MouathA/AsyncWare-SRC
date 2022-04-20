package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentDigging extends Enchantment
{
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 1 + 10 * (n - 1);
    }
    
    protected EnchantmentDigging(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.DIGGER);
        this.setName("digging");
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() == Items.shears || super.canApply(itemStack);
    }
}
