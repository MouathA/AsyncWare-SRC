package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowKnockback extends Enchantment
{
    @Override
    public int getMinEnchantability(final int n) {
        return 12 + (n - 1) * 20;
    }
    
    public EnchantmentArrowKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName("arrowKnockback");
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 25;
    }
}
