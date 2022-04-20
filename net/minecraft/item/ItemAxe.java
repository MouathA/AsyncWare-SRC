package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;

public class ItemAxe extends ItemTool
{
    private static final Set EFFECTIVE_ON;
    
    static {
        EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder });
    }
    
    protected ItemAxe(final ToolMaterial toolMaterial) {
        super(3.0f, toolMaterial, ItemAxe.EFFECTIVE_ON);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return (block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine) ? super.getStrVsBlock(itemStack, block) : this.efficiencyOnProperMaterial;
    }
}
