package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentOxygen extends Enchantment
{
    @Override
    public int getMinEnchantability(final int n) {
        return 10 * n;
    }
    
    public EnchantmentOxygen(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_HEAD);
        this.setName("oxygen");
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 30;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
}
