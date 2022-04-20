package net.minecraft.util;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.enchantment.*;

public class WeightedRandomFishable extends WeightedRandom.Item
{
    private boolean enchantable;
    private float maxDamagePercent;
    private final ItemStack returnStack;
    
    public WeightedRandomFishable setMaxDamagePercent(final float maxDamagePercent) {
        this.maxDamagePercent = maxDamagePercent;
        return this;
    }
    
    public WeightedRandomFishable setEnchantable() {
        this.enchantable = true;
        return this;
    }
    
    public WeightedRandomFishable(final ItemStack returnStack, final int n) {
        super(n);
        this.returnStack = returnStack;
    }
    
    public ItemStack getItemStack(final Random random) {
        final ItemStack copy = this.returnStack.copy();
        if (this.maxDamagePercent > 0.0f) {
            final int n = (int)(this.maxDamagePercent * this.returnStack.getMaxDamage());
            final int n2 = copy.getMaxDamage() - random.nextInt(random.nextInt(n) + 1);
            if (1 > n) {}
            copy.setItemDamage(1);
        }
        if (this.enchantable) {
            EnchantmentHelper.addRandomEnchantment(random, copy, 30);
        }
        return copy;
    }
}
