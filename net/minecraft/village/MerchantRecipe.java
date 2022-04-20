package net.minecraft.village;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class MerchantRecipe
{
    private boolean rewardsExp;
    private ItemStack itemToSell;
    private ItemStack secondItemToBuy;
    private int toolUses;
    private ItemStack itemToBuy;
    private int maxTradeUses;
    
    public MerchantRecipe(final NBTTagCompound nbtTagCompound) {
        this.readFromTags(nbtTagCompound);
    }
    
    public int getMaxTradeUses() {
        return this.maxTradeUses;
    }
    
    public boolean getRewardsExp() {
        return this.rewardsExp;
    }
    
    public MerchantRecipe(final ItemStack itemToBuy, final ItemStack secondItemToBuy, final ItemStack itemToSell, final int toolUses, final int maxTradeUses) {
        this.itemToBuy = itemToBuy;
        this.secondItemToBuy = secondItemToBuy;
        this.itemToSell = itemToSell;
        this.toolUses = toolUses;
        this.maxTradeUses = maxTradeUses;
        this.rewardsExp = true;
    }
    
    public ItemStack getItemToSell() {
        return this.itemToSell;
    }
    
    public boolean hasSecondItemToBuy() {
        return this.secondItemToBuy != null;
    }
    
    public void compensateToolUses() {
        this.toolUses = this.maxTradeUses;
    }
    
    public void increaseMaxTradeUses(final int n) {
        this.maxTradeUses += n;
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2, final ItemStack itemStack3) {
        this(itemStack, itemStack2, itemStack3, 0, 7);
    }
    
    public MerchantRecipe(final ItemStack itemStack, final Item item) {
        this(itemStack, new ItemStack(item));
    }
    
    public void readFromTags(final NBTTagCompound nbtTagCompound) {
        this.itemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("buy"));
        this.itemToSell = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("sell"));
        if (nbtTagCompound.hasKey("buyB", 10)) {
            this.secondItemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("buyB"));
        }
        if (nbtTagCompound.hasKey("uses", 99)) {
            this.toolUses = nbtTagCompound.getInteger("uses");
        }
        if (nbtTagCompound.hasKey("maxUses", 99)) {
            this.maxTradeUses = nbtTagCompound.getInteger("maxUses");
        }
        else {
            this.maxTradeUses = 7;
        }
        if (nbtTagCompound.hasKey("rewardExp", 1)) {
            this.rewardsExp = nbtTagCompound.getBoolean("rewardExp");
        }
        else {
            this.rewardsExp = true;
        }
    }
    
    public ItemStack getSecondItemToBuy() {
        return this.secondItemToBuy;
    }
    
    public NBTTagCompound writeToTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
        nbtTagCompound.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));
        if (this.secondItemToBuy != null) {
            nbtTagCompound.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
        }
        nbtTagCompound.setInteger("uses", this.toolUses);
        nbtTagCompound.setInteger("maxUses", this.maxTradeUses);
        nbtTagCompound.setBoolean("rewardExp", this.rewardsExp);
        return nbtTagCompound;
    }
    
    public ItemStack getItemToBuy() {
        return this.itemToBuy;
    }
    
    public void incrementToolUses() {
        ++this.toolUses;
    }
    
    public boolean isRecipeDisabled() {
        return this.toolUses >= this.maxTradeUses;
    }
    
    public int getToolUses() {
        return this.toolUses;
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2) {
        this(itemStack, null, itemStack2);
    }
}
