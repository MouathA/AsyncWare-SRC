package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public class RecipeFireworks implements IRecipe
{
    private ItemStack field_92102_a;
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        while (0 < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public int getRecipeSize() {
        return 10;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.field_92102_a;
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        this.field_92102_a = null;
        while (0 < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.gunpowder) {
                    int n = 0;
                    ++n;
                }
                else if (stackInSlot.getItem() == Items.firework_charge) {
                    int n2 = 0;
                    ++n2;
                }
                else if (stackInSlot.getItem() == Items.dye) {
                    int n3 = 0;
                    ++n3;
                }
                else if (stackInSlot.getItem() == Items.paper) {
                    int n4 = 0;
                    ++n4;
                }
                else if (stackInSlot.getItem() == Items.glowstone_dust) {
                    int n5 = 0;
                    ++n5;
                }
                else if (stackInSlot.getItem() == Items.diamond) {
                    int n5 = 0;
                    ++n5;
                }
                else if (stackInSlot.getItem() == Items.fire_charge) {
                    int n6 = 0;
                    ++n6;
                }
                else if (stackInSlot.getItem() == Items.feather) {
                    int n6 = 0;
                    ++n6;
                }
                else if (stackInSlot.getItem() == Items.gold_nugget) {
                    int n6 = 0;
                    ++n6;
                }
                else {
                    if (stackInSlot.getItem() != Items.skull) {
                        return false;
                    }
                    int n6 = 0;
                    ++n6;
                }
            }
            int n7 = 0;
            ++n7;
        }
        return false;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        return this.field_92102_a.copy();
    }
}
