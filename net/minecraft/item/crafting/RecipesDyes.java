package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class RecipesDyes
{
    public void addRecipes(final CraftingManager craftingManager) {
        while (true) {
            craftingManager.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0), new ItemStack(Items.dye, 1, 15), new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, 15), "###", "#X#", "###", '#', new ItemStack(Blocks.hardened_clay), 'X', new ItemStack(Items.dye, 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_glass, 8, 15), "###", "#X#", "###", '#', new ItemStack(Blocks.glass), 'X', new ItemStack(Items.dye, 1, 0));
            craftingManager.addRecipe(new ItemStack(Blocks.stained_glass_pane, 16, 0), "###", "###", '#', new ItemStack(Blocks.stained_glass, 1, 0));
            int n = 0;
            ++n;
        }
    }
}
