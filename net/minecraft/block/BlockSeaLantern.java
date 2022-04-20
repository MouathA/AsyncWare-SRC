package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockSeaLantern extends Block
{
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.prismarine_crystals;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.quartzColor;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 2 + random.nextInt(2);
    }
    
    public BlockSeaLantern(final Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(n + 1), 1, 5);
    }
}
