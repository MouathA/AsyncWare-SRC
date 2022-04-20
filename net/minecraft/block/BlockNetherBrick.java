package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockNetherBrick extends Block
{
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.netherrackColor;
    }
    
    public BlockNetherBrick() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
