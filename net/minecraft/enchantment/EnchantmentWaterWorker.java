package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentWaterWorker extends Enchantment
{
    @Override
    public int getMinEnchantability(final int n) {
        return 1;
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 40;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    public EnchantmentWaterWorker(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_HEAD);
        this.setName("waterWorker");
    }
}
