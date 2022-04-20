package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;

public class RecipesBanners
{
    void addRecipes(final CraftingManager craftingManager) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            final EnumDyeColor enumDyeColor = values[0];
            craftingManager.addRecipe(new ItemStack(Items.banner, 1, enumDyeColor.getDyeDamage()), "###", "###", " | ", '#', new ItemStack(Blocks.wool, 1, enumDyeColor.getMetadata()), '|', Items.stick);
            int n = 0;
            ++n;
        }
        craftingManager.addRecipe(new RecipeDuplicatePattern(null));
        craftingManager.addRecipe(new RecipeAddPattern(null));
    }
    
    static class RecipeAddPattern implements IRecipe
    {
        @Override
        public int getRecipeSize() {
            return 10;
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return this.func_179533_c(inventoryCrafting) != null;
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            ItemStack copy = null;
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && stackInSlot.getItem() == Items.banner) {
                    copy = stackInSlot.copy();
                    copy.stackSize = 1;
                    break;
                }
                int n = 0;
                ++n;
            }
            final TileEntityBanner.EnumBannerPattern func_179533_c = this.func_179533_c(inventoryCrafting);
            if (func_179533_c != null) {
                while (0 < inventoryCrafting.getSizeInventory()) {
                    final ItemStack stackInSlot2 = inventoryCrafting.getStackInSlot(0);
                    if (stackInSlot2 != null && stackInSlot2.getItem() == Items.dye) {
                        stackInSlot2.getMetadata();
                        break;
                    }
                    int n2 = 0;
                    ++n2;
                }
                final NBTTagCompound subCompound = copy.getSubCompound("BlockEntityTag", true);
                NBTTagList tagList;
                if (subCompound.hasKey("Patterns", 9)) {
                    tagList = subCompound.getTagList("Patterns", 10);
                }
                else {
                    tagList = new NBTTagList();
                    subCompound.setTag("Patterns", tagList);
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Pattern", func_179533_c.getPatternID());
                nbtTagCompound.setInteger("Color", 0);
                tagList.appendTag(nbtTagCompound);
            }
            return copy;
        }
        
        private TileEntityBanner.EnumBannerPattern func_179533_c(final InventoryCrafting inventoryCrafting) {
            final TileEntityBanner.EnumBannerPattern[] values = TileEntityBanner.EnumBannerPattern.values();
            while (0 < values.length) {
                final TileEntityBanner.EnumBannerPattern enumBannerPattern = values[0];
                if (enumBannerPattern.hasValidCrafting()) {
                    if (enumBannerPattern.hasCraftingStack()) {
                        if (0 < inventoryCrafting.getSizeInventory()) {}
                    }
                    else if (inventoryCrafting.getSizeInventory() == enumBannerPattern.getCraftingLayers().length * enumBannerPattern.getCraftingLayers()[0].length()) {
                        if (0 < inventoryCrafting.getSizeInventory()) {}
                    }
                }
                int n = 0;
                ++n;
            }
            return null;
        }
        
        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
        
        RecipeAddPattern(final RecipesBanners$1 object) {
            this();
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
        
        private RecipeAddPattern() {
        }
    }
    
    static class RecipeDuplicatePattern implements IRecipe
    {
        @Override
        public int getRecipeSize() {
            return 2;
        }
        
        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
        
        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null && TileEntityBanner.getPatterns(stackInSlot) > 0) {
                    final ItemStack copy = stackInSlot.copy();
                    copy.stackSize = 1;
                    return copy;
                }
                int n = 0;
                ++n;
            }
            return null;
        }
        
        @Override
        public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
            final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
            while (0 < array.length) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem().hasContainerItem()) {
                        array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
                    }
                    else if (stackInSlot.hasTagCompound() && TileEntityBanner.getPatterns(stackInSlot) > 0) {
                        array[0] = stackInSlot.copy();
                        array[0].stackSize = 1;
                    }
                }
                int n = 0;
                ++n;
            }
            return array;
        }
        
        RecipeDuplicatePattern(final RecipesBanners$1 object) {
            this();
        }
        
        private RecipeDuplicatePattern() {
        }
        
        @Override
        public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
            ItemStack itemStack = null;
            ItemStack itemStack2 = null;
            while (0 < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem() != Items.banner) {
                        return false;
                    }
                    if (itemStack != null && itemStack2 != null) {
                        return false;
                    }
                    final int baseColor = TileEntityBanner.getBaseColor(stackInSlot);
                    final boolean b = TileEntityBanner.getPatterns(stackInSlot) > 0;
                    if (itemStack != null) {
                        if (b) {
                            return false;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack)) {
                            return false;
                        }
                        itemStack2 = stackInSlot;
                    }
                    else if (itemStack2 != null) {
                        if (!b) {
                            return false;
                        }
                        if (baseColor != TileEntityBanner.getBaseColor(itemStack2)) {
                            return false;
                        }
                        itemStack = stackInSlot;
                    }
                    else if (b) {
                        itemStack = stackInSlot;
                    }
                    else {
                        itemStack2 = stackInSlot;
                    }
                }
                int n = 0;
                ++n;
            }
            return itemStack != null && itemStack2 != null;
        }
    }
}
