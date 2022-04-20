package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack recipeOutput;
    private final List recipeItems;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.recipeItems);
        while (0 < inventoryCrafting.getHeight()) {
            while (0 < inventoryCrafting.getWidth()) {
                final ItemStack stackInRowAndColumn = inventoryCrafting.getStackInRowAndColumn(0, 0);
                if (stackInRowAndColumn != null) {
                    for (final ItemStack itemStack : arrayList) {
                        if (stackInRowAndColumn.getItem() == itemStack.getItem() && (itemStack.getMetadata() == 32767 || stackInRowAndColumn.getMetadata() == itemStack.getMetadata())) {
                            arrayList.remove(itemStack);
                            break;
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return arrayList.isEmpty();
    }
    
    public ShapelessRecipes(final ItemStack recipeOutput, final List recipeItems) {
        this.recipeOutput = recipeOutput;
        this.recipeItems = recipeItems;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        return this.recipeOutput.copy();
    }
    
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
}
