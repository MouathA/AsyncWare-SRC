package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentKnockback extends Enchantment
{
    @Override
    public int getMaxLevel() {
        return 2;
    }
    
    protected EnchantmentKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.setName("knockback");
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 5 + 20 * (n - 1);
    }
}
