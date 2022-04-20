package net.minecraft.item;

import net.minecraft.block.*;

public class ItemPiston extends ItemBlock
{
    @Override
    public int getMetadata(final int n) {
        return 7;
    }
    
    public ItemPiston(final Block block) {
        super(block);
    }
}
