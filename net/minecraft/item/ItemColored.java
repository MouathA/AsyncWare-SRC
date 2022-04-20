package net.minecraft.item;

import net.minecraft.block.*;

public class ItemColored extends ItemBlock
{
    private String[] subtypeNames;
    private final Block coloredBlock;
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        if (this.subtypeNames == null) {
            return super.getUnlocalizedName(itemStack);
        }
        final int metadata = itemStack.getMetadata();
        return (metadata >= 0 && metadata < this.subtypeNames.length) ? (super.getUnlocalizedName(itemStack) + "." + this.subtypeNames[metadata]) : super.getUnlocalizedName(itemStack);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.coloredBlock.getRenderColor(this.coloredBlock.getStateFromMeta(itemStack.getMetadata()));
    }
    
    public ItemColored setSubtypeNames(final String[] subtypeNames) {
        this.subtypeNames = subtypeNames;
        return this;
    }
    
    public ItemColored(final Block coloredBlock, final boolean b) {
        super(coloredBlock);
        this.coloredBlock = coloredBlock;
        if (b) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }
}
