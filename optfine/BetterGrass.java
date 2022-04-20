package optfine;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class BetterGrass
{
    private static IBakedModel modelCubeGrassSnowy;
    private static IBakedModel modelCubeGrass;
    private static IBakedModel modelCubeMycelium;
    private static IBakedModel modelEmpty;
    
    static {
        BetterGrass.modelEmpty = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false, null, null);
        BetterGrass.modelCubeMycelium = null;
        BetterGrass.modelCubeGrassSnowy = null;
        BetterGrass.modelCubeGrass = null;
    }
    
    public static void update() {
        BetterGrass.modelCubeGrass = BlockModelUtils.makeModelCube("minecraft:blocks/grass_top", 0);
        BetterGrass.modelCubeGrassSnowy = BlockModelUtils.makeModelCube("minecraft:blocks/snow", -1);
        BetterGrass.modelCubeMycelium = BlockModelUtils.makeModelCube("minecraft:blocks/mycelium_top", -1);
    }
    
    public static List getFaceQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, final List list) {
        if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN) {
            return list;
        }
        if (block instanceof BlockMycelium) {
            return Config.isBetterGrassFancy() ? ((getBlockAt(blockPos.down(), enumFacing, blockAccess) == Blocks.mycelium) ? BetterGrass.modelCubeMycelium.getFaceQuads(enumFacing) : list) : BetterGrass.modelCubeMycelium.getFaceQuads(enumFacing);
        }
        if (block instanceof BlockGrass) {
            final Block block2 = blockAccess.getBlockState(blockPos.up()).getBlock();
            final boolean b = block2 == Blocks.snow || block2 == Blocks.snow_layer;
            if (!Config.isBetterGrassFancy()) {
                if (b) {
                    return BetterGrass.modelCubeGrassSnowy.getFaceQuads(enumFacing);
                }
                return BetterGrass.modelCubeGrass.getFaceQuads(enumFacing);
            }
            else if (b) {
                if (getBlockAt(blockPos, enumFacing, blockAccess) == Blocks.snow_layer) {
                    return BetterGrass.modelCubeGrassSnowy.getFaceQuads(enumFacing);
                }
            }
            else if (getBlockAt(blockPos.down(), enumFacing, blockAccess) == Blocks.grass) {
                return BetterGrass.modelCubeGrass.getFaceQuads(enumFacing);
            }
        }
        return list;
    }
    
    private static Block getBlockAt(final BlockPos blockPos, final EnumFacing enumFacing, final IBlockAccess blockAccess) {
        return blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
    }
}
