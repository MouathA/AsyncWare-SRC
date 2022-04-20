package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockSnowBlock extends Block
{
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > 11) {
            this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.snowball;
    }
    
    protected BlockSnowBlock() {
        super(Material.craftedSnow);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 4;
    }
}
