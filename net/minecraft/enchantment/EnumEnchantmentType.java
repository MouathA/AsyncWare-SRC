package net.minecraft.enchantment;

import net.minecraft.item.*;

public enum EnumEnchantmentType
{
    WEAPON("WEAPON", 6);
    
    private static final EnumEnchantmentType[] $VALUES;
    
    ARMOR_LEGS("ARMOR_LEGS", 3), 
    ARMOR_TORSO("ARMOR_TORSO", 4), 
    ALL("ALL", 0), 
    FISHING_ROD("FISHING_ROD", 8), 
    ARMOR_FEET("ARMOR_FEET", 2), 
    ARMOR_HEAD("ARMOR_HEAD", 5), 
    BREAKABLE("BREAKABLE", 9), 
    DIGGER("DIGGER", 7), 
    ARMOR("ARMOR", 1), 
    BOW("BOW", 10);
    
    static {
        $VALUES = new EnumEnchantmentType[] { EnumEnchantmentType.ALL, EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE, EnumEnchantmentType.BOW };
    }
    
    public boolean canEnchantItem(final Item item) {
        if (this == EnumEnchantmentType.ALL) {
            return true;
        }
        if (this == EnumEnchantmentType.BREAKABLE && item.isDamageable()) {
            return true;
        }
        if (!(item instanceof ItemArmor)) {
            return (item instanceof ItemSword) ? (this == EnumEnchantmentType.WEAPON) : ((item instanceof ItemTool) ? (this == EnumEnchantmentType.DIGGER) : ((item instanceof ItemBow) ? (this == EnumEnchantmentType.BOW) : (item instanceof ItemFishingRod && this == EnumEnchantmentType.FISHING_ROD)));
        }
        if (this == EnumEnchantmentType.ARMOR) {
            return true;
        }
        final ItemArmor itemArmor = (ItemArmor)item;
        return (itemArmor.armorType == 0) ? (this == EnumEnchantmentType.ARMOR_HEAD) : ((itemArmor.armorType == 2) ? (this == EnumEnchantmentType.ARMOR_LEGS) : ((itemArmor.armorType == 1) ? (this == EnumEnchantmentType.ARMOR_TORSO) : (itemArmor.armorType == 3 && this == EnumEnchantmentType.ARMOR_FEET)));
    }
    
    private EnumEnchantmentType(final String s, final int n) {
    }
}
