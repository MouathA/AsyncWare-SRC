package net.minecraft.block;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockPackedIce extends Block
{
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    public BlockPackedIce() {
        super(Material.packedIce);
        this.slipperiness = 0.98f;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
