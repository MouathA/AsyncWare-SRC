package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemBow extends Item
{
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return itemStack;
    }
    
    static {
        ItemBow.bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer, final int n) {
        final boolean b = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;
        if (b || entityPlayer.inventory.hasItem(Items.arrow)) {
            final float n2 = (this.getMaxItemUseDuration(itemStack) - n) / 20.0f;
            float n3 = (n2 * n2 + n2 * 2.0f) / 3.0f;
            if (n3 < 0.1) {
                return;
            }
            if (n3 > 1.0f) {
                n3 = 1.0f;
            }
            final EntityArrow entityArrow = new EntityArrow(world, entityPlayer, n3 * 2.0f);
            if (n3 == 1.0f) {
                entityArrow.setIsCritical(true);
            }
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            if (enchantmentLevel > 0) {
                entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
            }
            final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            if (enchantmentLevel2 > 0) {
                entityArrow.setKnockbackStrength(enchantmentLevel2);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                entityArrow.setFire(100);
            }
            itemStack.damageItem(1, entityPlayer);
            world.playSoundAtEntity(entityPlayer, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + n3 * 0.5f);
            if (b) {
                entityArrow.canBePickedUp = 2;
            }
            else {
                entityPlayer.inventory.consumeInventoryItem(Items.arrow);
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityArrow);
            }
        }
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode || entityPlayer.inventory.hasItem(Items.arrow)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BOW;
    }
}
