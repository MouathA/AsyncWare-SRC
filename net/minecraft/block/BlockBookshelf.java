package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockBookshelf extends Block
{
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.book;
    }
    
    public BlockBookshelf() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 3;
    }
}
