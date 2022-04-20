package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockGravel extends BlockFalling
{
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (random.nextInt(1) == 0) ? Items.flint : Item.getItemFromBlock(this);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.stoneColor;
    }
}
