package net.minecraft.item;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;

public class ItemAppleGold extends ItemFood
{
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return itemStack.getMetadata() > 0;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        return (itemStack.getMetadata() == 0) ? EnumRarity.RARE : EnumRarity.EPIC;
    }
    
    public ItemAppleGold(final int n, final float n2, final boolean b) {
        super(n, n2, b);
        this.setHasSubtypes(true);
    }
    
    @Override
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.absorption.id, 2400, 0));
        }
        if (itemStack.getMetadata() > 0) {
            if (!world.isRemote) {
                entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
            }
        }
        else {
            super.onFoodEaten(itemStack, world, entityPlayer);
        }
    }
}
