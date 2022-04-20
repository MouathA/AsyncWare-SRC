package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowInfinite extends Enchantment
{
    public EnchantmentArrowInfinite(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName("arrowInfinite");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 20;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
