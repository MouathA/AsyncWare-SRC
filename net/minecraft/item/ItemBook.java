package net.minecraft.item;

public class ItemBook extends Item
{
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return itemStack.stackSize == 1;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
