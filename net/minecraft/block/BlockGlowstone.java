package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class BlockGlowstone extends Block
{
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.glowstone_dust;
    }
    
    public BlockGlowstone(final Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(n + 1), 1, 4);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 2 + random.nextInt(3);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.sandColor;
    }
}
