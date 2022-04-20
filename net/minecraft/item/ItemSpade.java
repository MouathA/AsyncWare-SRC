package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemSpade extends ItemTool
{
    private static final Set EFFECTIVE_ON;
    
    static {
        EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand });
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.snow_layer || block == Blocks.snow;
    }
    
    public ItemSpade(final ToolMaterial toolMaterial) {
        super(1.0f, toolMaterial, ItemSpade.EFFECTIVE_ON);
    }
}
