package net.minecraft.enchantment;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EnchantmentProtection extends Enchantment
{
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    public final int protectionType;
    
    public static int getFireTimeForEntity(final Entity entity, int n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_float(n * (float)maxEnchantmentLevel * 0.15f);
        }
        return n;
    }
    
    public EnchantmentProtection(final int n, final ResourceLocation resourceLocation, final int n2, final int protectionType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR);
        this.protectionType = protectionType;
        if (protectionType == 2) {
            this.type = EnumEnchantmentType.ARMOR_FEET;
        }
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentProtection.baseEnchantability[this.protectionType] + (n - 1) * EnchantmentProtection.levelEnchantability[this.protectionType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            final EnchantmentProtection enchantmentProtection = (EnchantmentProtection)enchantment;
            return enchantmentProtection.protectionType != this.protectionType && (this.protectionType == 2 || enchantmentProtection.protectionType == 2);
        }
        return super.canApplyTogether(enchantment);
    }
    
    public static double func_92092_a(final Entity entity, double n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_double(n * (maxEnchantmentLevel * 0.15f));
        }
        return n;
    }
    
    @Override
    public int getMaxLevel() {
        return 4;
    }
    
    static {
        EnchantmentProtection.protectionName = new String[] { "all", "fire", "fall", "explosion", "projectile" };
        EnchantmentProtection.baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
        levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
        thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
    }
    
    @Override
    public String getName() {
        return "enchantment.protect." + EnchantmentProtection.protectionName[this.protectionType];
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentProtection.thresholdEnchantability[this.protectionType];
    }
    
    @Override
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return 0;
        }
        final float n2 = (6 + n * n) / 3.0f;
        return (this.protectionType == 0) ? MathHelper.floor_float(n2 * 0.75f) : ((this.protectionType == 1 && damageSource.isFireDamage()) ? MathHelper.floor_float(n2 * 1.25f) : ((this.protectionType == 2 && damageSource == DamageSource.fall) ? MathHelper.floor_float(n2 * 2.5f) : ((this.protectionType == 3 && damageSource.isExplosion()) ? MathHelper.floor_float(n2 * 1.5f) : ((this.protectionType == 4 && damageSource.isProjectile()) ? MathHelper.floor_float(n2 * 1.5f) : 0))));
    }
}
