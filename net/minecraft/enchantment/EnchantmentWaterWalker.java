package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentWaterWalker extends Enchantment
{
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 15;
    }
    
    public EnchantmentWaterWalker(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_FEET);
        this.setName("waterWalker");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return n * 10;
    }
}
