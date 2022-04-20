package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;

public class ItemFood extends Item
{
    private float potionEffectProbability;
    private final float saturationModifier;
    public final int itemUseDuration;
    private final boolean isWolfsFavoriteMeat;
    private int potionId;
    private boolean alwaysEdible;
    private final int healAmount;
    private int potionAmplifier;
    private int potionDuration;
    
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        --itemStack.stackSize;
        entityPlayer.getFoodStats().addStats(this, itemStack);
        world.playSoundAtEntity(entityPlayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(itemStack, world, entityPlayer);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public ItemFood(final int n, final boolean b) {
        this(n, 0.6f, b);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.canEat(this.alwaysEdible)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
    
    public ItemFood setPotionEffect(final int potionId, final int potionDuration, final int potionAmplifier, final float potionEffectProbability) {
        this.potionId = potionId;
        this.potionDuration = potionDuration;
        this.potionAmplifier = potionAmplifier;
        this.potionEffectProbability = potionEffectProbability;
        return this;
    }
    
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability) {
            entityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 32;
    }
    
    public ItemFood(final int healAmount, final float saturationModifier, final boolean isWolfsFavoriteMeat) {
        this.itemUseDuration = 32;
        this.healAmount = healAmount;
        this.isWolfsFavoriteMeat = isWolfsFavoriteMeat;
        this.saturationModifier = saturationModifier;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public int getHealAmount(final ItemStack itemStack) {
        return this.healAmount;
    }
    
    public float getSaturationModifier(final ItemStack itemStack) {
        return this.saturationModifier;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.EAT;
    }
}
