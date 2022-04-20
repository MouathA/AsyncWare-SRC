package net.minecraft.item;

import net.minecraft.block.*;

public class ItemLeaves extends ItemBlock
{
    private final BlockLeaves leaves;
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.leaves.getRenderColor(this.leaves.getStateFromMeta(itemStack.getMetadata()));
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + this.leaves.getWoodType(itemStack.getMetadata()).getUnlocalizedName();
    }
    
    @Override
    public int getMetadata(final int n) {
        return n | 0x4;
    }
    
    public ItemLeaves(final BlockLeaves leaves) {
        super(leaves);
        this.leaves = leaves;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
}
