package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public class ItemEnchantedBook extends Item
{
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return false;
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return true;
    }
    
    public WeightedRandomChestContent getRandom(final Random random, final int n, final int n2, final int n3) {
        final ItemStack itemStack = new ItemStack(Items.book, 1, 0);
        EnchantmentHelper.addRandomEnchantment(random, itemStack, 30);
        return new WeightedRandomChestContent(itemStack, n, n2, n3);
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        super.addInformation(itemStack, entityPlayer, list, b);
        final NBTTagList enchantments = this.getEnchantments(itemStack);
        if (enchantments != null) {
            while (0 < enchantments.tagCount()) {
                final short short1 = enchantments.getCompoundTagAt(0).getShort("id");
                final short short2 = enchantments.getCompoundTagAt(0).getShort("lvl");
                if (Enchantment.getEnchantmentById(short1) != null) {
                    list.add(Enchantment.getEnchantmentById(short1).getTranslatedName(short2));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public NBTTagList getEnchantments(final ItemStack itemStack) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        return (NBTTagList)((tagCompound != null && tagCompound.hasKey("StoredEnchantments", 9)) ? tagCompound.getTag("StoredEnchantments") : new NBTTagList());
    }
    
    public WeightedRandomChestContent getRandom(final Random random) {
        return this.getRandom(random, 1, 1, 1);
    }
    
    public void addEnchantment(final ItemStack itemStack, final EnchantmentData enchantmentData) {
        final NBTTagList enchantments = this.getEnchantments(itemStack);
        while (true) {
            while (0 < enchantments.tagCount()) {
                final NBTTagCompound compoundTag = enchantments.getCompoundTagAt(0);
                if (compoundTag.getShort("id") == enchantmentData.enchantmentobj.effectId) {
                    if (compoundTag.getShort("lvl") < enchantmentData.enchantmentLevel) {
                        compoundTag.setShort("lvl", (short)enchantmentData.enchantmentLevel);
                    }
                    if (!itemStack.hasTagCompound()) {
                        itemStack.setTagCompound(new NBTTagCompound());
                    }
                    itemStack.getTagCompound().setTag("StoredEnchantments", enchantments);
                    return;
                }
                int n = 0;
                ++n;
            }
            continue;
        }
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        return (this.getEnchantments(itemStack).tagCount() > 0) ? EnumRarity.UNCOMMON : super.getRarity(itemStack);
    }
    
    public void getAll(final Enchantment enchantment, final List list) {
        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
            list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
        }
    }
    
    public ItemStack getEnchantedItemStack(final EnchantmentData enchantmentData) {
        final ItemStack itemStack = new ItemStack(this);
        this.addEnchantment(itemStack, enchantmentData);
        return itemStack;
    }
}
