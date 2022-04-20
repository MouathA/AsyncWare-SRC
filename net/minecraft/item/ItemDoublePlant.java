package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.world.*;

public class ItemDoublePlant extends ItemMultiTexture
{
    public ItemDoublePlant(final Block block, final Block block2, final Function function) {
        super(block, block2, function);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final BlockDoublePlant.EnumPlantType byMetadata = BlockDoublePlant.EnumPlantType.byMetadata(itemStack.getMetadata());
        return (byMetadata != BlockDoublePlant.EnumPlantType.GRASS && byMetadata != BlockDoublePlant.EnumPlantType.FERN) ? super.getColorFromItemStack(itemStack, n) : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
}
