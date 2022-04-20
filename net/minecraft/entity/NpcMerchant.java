package net.minecraft.entity;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.village.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    private IChatComponent field_175548_d;
    private MerchantRecipeList recipeList;
    
    @Override
    public void setCustomer(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList recipeList) {
        this.recipeList = recipeList;
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }
    
    public NpcMerchant(final EntityPlayer customer, final IChatComponent field_175548_d) {
        this.customer = customer;
        this.field_175548_d = field_175548_d;
        this.theMerchantInventory = new InventoryMerchant(customer, this);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return (this.field_175548_d != null) ? this.field_175548_d : new ChatComponentTranslation("entity.Villager.name", new Object[0]);
    }
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        return this.recipeList;
    }
}
