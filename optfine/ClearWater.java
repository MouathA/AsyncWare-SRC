package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;

public class ClearWater
{
    public static void updateWaterOpacity(final GameSettings gameSettings, final World world) {
        if (gameSettings != null) {
            if (gameSettings.ofClearWater) {}
            BlockUtils.setLightOpacity(Blocks.water, 1);
            BlockUtils.setLightOpacity(Blocks.flowing_water, 1);
        }
        if (world != null) {
            final IChunkProvider chunkProvider = world.getChunkProvider();
            if (chunkProvider != null) {
                final Entity renderViewEntity = Config.getMinecraft().getRenderViewEntity();
                if (renderViewEntity != null) {
                    final int n = (int)renderViewEntity.posX / 16;
                    final int n2 = (int)renderViewEntity.posZ / 16;
                    final int n3 = n - 512;
                    final int n4 = n + 512;
                    final int n5 = n2 - 512;
                    final int n6 = n2 + 512;
                    for (int i = n3; i < n4; ++i) {
                        for (int j = n5; j < n6; ++j) {
                            if (chunkProvider.chunkExists(i, j)) {
                                final Chunk provideChunk = chunkProvider.provideChunk(i, j);
                                if (provideChunk != null && !(provideChunk instanceof EmptyChunk)) {
                                    final int n7 = i << 4;
                                    final int n8 = j << 4;
                                    final int n9 = n7 + 16;
                                    final int n10 = n8 + 16;
                                    final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
                                    final BlockPosM blockPosM2 = new BlockPosM(0, 0, 0);
                                    for (int k = n7; k < n9; ++k) {
                                        for (int l = n8; l < n10; ++l) {
                                            blockPosM.setXyz(k, 0, l);
                                            final BlockPos precipitationHeight = world.getPrecipitationHeight(blockPosM);
                                            while (0 < precipitationHeight.getY()) {
                                                blockPosM2.setXyz(k, 0, l);
                                                if (world.getBlockState(blockPosM2).getBlock().getMaterial() == Material.water) {
                                                    world.markBlocksDirtyVertical(k, l, blockPosM2.getY(), precipitationHeight.getY());
                                                    int n11 = 0;
                                                    ++n11;
                                                    break;
                                                }
                                                int n12 = 0;
                                                ++n12;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
