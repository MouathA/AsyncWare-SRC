package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockGlass extends BlockBreakable
{
    public BlockGlass(final Material material, final boolean b) {
        super(material, b);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
