package net.minecraft.block;

import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockMelon extends Block
{
    @Override
    public int quantityDropped(final Random random) {
        return 3 + random.nextInt(5);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.melon;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return Math.min(9, this.quantityDropped(random) + random.nextInt(1 + n));
    }
    
    protected BlockMelon() {
        super(Material.gourd, MapColor.limeColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
