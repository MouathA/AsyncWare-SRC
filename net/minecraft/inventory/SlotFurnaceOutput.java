package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class SlotFurnaceOutput extends Slot
{
    private int field_75228_b;
    private EntityPlayer thePlayer;
    
    public SlotFurnaceOutput(final EntityPlayer thePlayer, final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
        this.thePlayer = thePlayer;
    }
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.field_75228_b += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
        if (!this.thePlayer.worldObj.isRemote) {
            final int field_75228_b = this.field_75228_b;
            final float smeltingExperience = FurnaceRecipes.instance().getSmeltingExperience(itemStack);
            if (smeltingExperience != 0.0f) {
                if (smeltingExperience < 1.0f) {
                    int floor_float = MathHelper.floor_float(0 * smeltingExperience);
                    if (floor_float < MathHelper.ceiling_float_int(0 * smeltingExperience) && Math.random() < 0 * smeltingExperience - floor_float) {
                        ++floor_float;
                    }
                }
            }
        }
        this.field_75228_b = 0;
        if (itemStack.getItem() == Items.iron_ingot) {
            this.thePlayer.triggerAchievement(AchievementList.acquireIron);
        }
        if (itemStack.getItem() == Items.cooked_fish) {
            this.thePlayer.triggerAchievement(AchievementList.cookFish);
        }
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onCrafting(itemStack);
        super.onPickupFromSlot(entityPlayer, itemStack);
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return false;
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.field_75228_b += n;
        this.onCrafting(itemStack);
    }
}
