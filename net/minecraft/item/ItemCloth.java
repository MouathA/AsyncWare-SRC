package net.minecraft.item;

import net.minecraft.block.*;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
    }
}
