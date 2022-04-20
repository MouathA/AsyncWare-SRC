package net.minecraft.enchantment;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class EnchantmentThorns extends Enchantment
{
    public EnchantmentThorns(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_TORSO);
        this.setName("thorns");
    }
    
    public static int func_92095_b(final int n, final Random random) {
        return (n > 10) ? (n - 10) : (1 + random.nextInt(4));
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 10 + 20 * (n - 1);
    }
    
    @Override
    public void onUserHurt(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        final Random rng = entityLivingBase.getRNG();
        final ItemStack enchantedItem = EnchantmentHelper.getEnchantedItem(Enchantment.thorns, entityLivingBase);
        if (rng <= 0) {
            if (entity != null) {
                entity.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), (float)func_92095_b(n, rng));
                entity.playSound("damage.thorns", 0.5f, 1.0f);
            }
            if (enchantedItem != null) {
                enchantedItem.damageItem(3, entityLivingBase);
            }
        }
        else if (enchantedItem != null) {
            enchantedItem.damageItem(1, entityLivingBase);
        }
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || super.canApply(itemStack);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
}
