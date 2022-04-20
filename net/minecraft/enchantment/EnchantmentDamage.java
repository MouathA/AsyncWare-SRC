package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class EnchantmentDamage extends Enchantment
{
    public final int damageType;
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemAxe || super.canApply(itemStack);
    }
    
    @Override
    public void onEntityDamaged(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase2 = (EntityLivingBase)entity;
            if (this.damageType == 2 && entityLivingBase2.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                entityLivingBase2.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 + entityLivingBase.getRNG().nextInt(10 * n), 3));
            }
        }
    }
    
    @Override
    public float calcDamageByCreature(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        return (this.damageType == 0) ? (n * 1.25f) : ((this.damageType == 1 && enumCreatureAttribute == EnumCreatureAttribute.UNDEAD) ? (n * 2.5f) : ((this.damageType == 2 && enumCreatureAttribute == EnumCreatureAttribute.ARTHROPOD) ? (n * 2.5f) : 0.0f));
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentDamage.thresholdEnchantability[this.damageType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentDamage);
    }
    
    static {
        EnchantmentDamage.protectionName = new String[] { "all", "undead", "arthropods" };
        EnchantmentDamage.baseEnchantability = new int[] { 1, 5, 5 };
        levelEnchantability = new int[] { 11, 8, 8 };
        thresholdEnchantability = new int[] { 20, 20, 20 };
    }
    
    @Override
    public String getName() {
        return "enchantment.damage." + EnchantmentDamage.protectionName[this.damageType];
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentDamage.baseEnchantability[this.damageType] + (n - 1) * EnchantmentDamage.levelEnchantability[this.damageType];
    }
    
    public EnchantmentDamage(final int n, final ResourceLocation resourceLocation, final int n2, final int damageType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.damageType = damageType;
    }
}
