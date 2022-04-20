package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class ShapedRecipes implements IRecipe
{
    private final ItemStack recipeOutput;
    private final int recipeHeight;
    private final ItemStack[] recipeItems;
    private final int recipeWidth;
    private boolean copyIngredientNBT;
    
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
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        final ItemStack copy = this.getRecipeOutput().copy();
        if (this.copyIngredientNBT) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.hasTagCompound()) {
                    copy.setTagCompound((NBTTagCompound)stackInSlot.getTagCompound().copy());
                }
                int n = 0;
                ++n;
            }
        }
        return copy;
    }
    
    public ShapedRecipes(final int recipeWidth, final int recipeHeight, final ItemStack[] recipeItems, final ItemStack recipeOutput) {
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        while (0 <= 3 - this.recipeWidth) {
            if (0 <= 3 - this.recipeHeight) {
                false;
                false;
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
}
