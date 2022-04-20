package optfine;

import net.minecraft.client.resources.model.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer;
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
    }
    
    private static boolean hasSnowNeighbours(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block snow_layer = Blocks.snow_layer;
        return (blockAccess.getBlockState(blockPos.north()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.south()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.west()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.east()).getBlock() == snow_layer) && blockAccess.getBlockState(blockPos.down()).getBlock().isOpaqueCube();
    }
    
    public static boolean shouldRender(final IBlockAccess blockAccess, final Block block, final IBlockState blockState, final BlockPos blockPos) {
        return blockState == 0 && hasSnowNeighbours(blockAccess, blockPos);
    }
    
    public static IBakedModel getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static IBlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
}
