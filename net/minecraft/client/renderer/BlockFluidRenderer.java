package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import optfine.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

public class BlockFluidRenderer
{
    private TextureAtlasSprite[] atlasSpritesLava;
    private TextureAtlasSprite[] atlasSpritesWater;
    private static final String __OBFID = "CL_00002519";
    
    public boolean renderFluid(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer) {
        final BlockLiquid blockLiquid = (BlockLiquid)blockState.getBlock();
        blockLiquid.setBlockBoundsBasedOnState(blockAccess, blockPos);
        final TextureAtlasSprite[] array = (blockLiquid.getMaterial() == Material.lava) ? this.atlasSpritesLava : this.atlasSpritesWater;
        final int fluidColor = CustomColorizer.getFluidColor(blockLiquid, blockAccess, blockPos);
        final float n = (fluidColor >> 16 & 0xFF) / 255.0f;
        final float n2 = (fluidColor >> 8 & 0xFF) / 255.0f;
        final float n3 = (fluidColor & 0xFF) / 255.0f;
        final boolean shouldSideBeRendered = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.up(), EnumFacing.UP);
        final boolean shouldSideBeRendered2 = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.down(), EnumFacing.DOWN);
        final boolean[] borderFlags = RenderEnv.getInstance(blockAccess, blockState, blockPos).getBorderFlags();
        borderFlags[0] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.north(), EnumFacing.NORTH);
        borderFlags[1] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.south(), EnumFacing.SOUTH);
        borderFlags[2] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.west(), EnumFacing.WEST);
        borderFlags[3] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.east(), EnumFacing.EAST);
        if (!shouldSideBeRendered && !shouldSideBeRendered2 && !borderFlags[0] && !borderFlags[1] && !borderFlags[2] && !borderFlags[3]) {
            return false;
        }
        final float n4 = 0.5f;
        final float n5 = 1.0f;
        final float n6 = 0.8f;
        final Material material = blockLiquid.getMaterial();
        float fluidHeight = this.getFluidHeight(blockAccess, blockPos, material);
        final float fluidHeight2 = this.getFluidHeight(blockAccess, blockPos.south(), material);
        final float fluidHeight3 = this.getFluidHeight(blockAccess, blockPos.east().south(), material);
        float fluidHeight4 = this.getFluidHeight(blockAccess, blockPos.east(), material);
        final double n7 = blockPos.getX();
        final double n8 = blockPos.getY();
        final double n9 = blockPos.getZ();
        final float n10 = 0.001f;
        if (shouldSideBeRendered) {
            TextureAtlasSprite sprite = array[0];
            final float n11 = (float)BlockLiquid.getFlowDirection(blockAccess, blockPos, material);
            if (n11 > -999.0f) {
                sprite = array[1];
            }
            worldRenderer.setSprite(sprite);
            fluidHeight -= n10;
            final float n12 = fluidHeight2 - n10;
            final float n13 = fluidHeight3 - n10;
            fluidHeight4 -= n10;
            float n14;
            float n15;
            float interpolatedU;
            float n16;
            float n17;
            float interpolatedV;
            float interpolatedU2;
            float interpolatedV2;
            if (n11 < -999.0f) {
                n14 = sprite.getInterpolatedU(0.0);
                n15 = sprite.getInterpolatedV(0.0);
                interpolatedU = n14;
                n16 = sprite.getInterpolatedV(16.0);
                n17 = sprite.getInterpolatedU(16.0);
                interpolatedV = n16;
                interpolatedU2 = n17;
                interpolatedV2 = n15;
            }
            else {
                final float n18 = MathHelper.sin(n11) * 0.25f;
                final float n19 = MathHelper.cos(n11) * 0.25f;
                n14 = sprite.getInterpolatedU(8.0f + (-n19 - n18) * 16.0f);
                n15 = sprite.getInterpolatedV(8.0f + (-n19 + n18) * 16.0f);
                interpolatedU = sprite.getInterpolatedU(8.0f + (-n19 + n18) * 16.0f);
                n16 = sprite.getInterpolatedV(8.0f + (n19 + n18) * 16.0f);
                n17 = sprite.getInterpolatedU(8.0f + (n19 + n18) * 16.0f);
                interpolatedV = sprite.getInterpolatedV(8.0f + (n19 - n18) * 16.0f);
                interpolatedU2 = sprite.getInterpolatedU(8.0f + (n19 - n18) * 16.0f);
                interpolatedV2 = sprite.getInterpolatedV(8.0f + (-n19 - n18) * 16.0f);
            }
            final int mixedBrightnessForBlock = blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos);
            final int n20 = mixedBrightnessForBlock >> 16 & 0xFFFF;
            final int n21 = mixedBrightnessForBlock & 0xFFFF;
            final float n22 = n5 * n;
            final float n23 = n5 * n2;
            final float n24 = n5 * n3;
            worldRenderer.pos(n7 + 0.0, n8 + fluidHeight, n9 + 0.0).color(n22, n23, n24, 1.0f).tex(n14, n15).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n7 + 0.0, n8 + n12, n9 + 1.0).color(n22, n23, n24, 1.0f).tex(interpolatedU, n16).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n7 + 1.0, n8 + n13, n9 + 1.0).color(n22, n23, n24, 1.0f).tex(n17, interpolatedV).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n7 + 1.0, n8 + fluidHeight4, n9 + 0.0).color(n22, n23, n24, 1.0f).tex(interpolatedU2, interpolatedV2).lightmap(n20, n21).endVertex();
            if (blockLiquid.func_176364_g(blockAccess, blockPos.up())) {
                worldRenderer.pos(n7 + 0.0, n8 + fluidHeight, n9 + 0.0).color(n22, n23, n24, 1.0f).tex(n14, n15).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n7 + 1.0, n8 + fluidHeight4, n9 + 0.0).color(n22, n23, n24, 1.0f).tex(interpolatedU2, interpolatedV2).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n7 + 1.0, n8 + n13, n9 + 1.0).color(n22, n23, n24, 1.0f).tex(n17, interpolatedV).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n7 + 0.0, n8 + n12, n9 + 1.0).color(n22, n23, n24, 1.0f).tex(interpolatedU, n16).lightmap(n20, n21).endVertex();
            }
        }
        if (shouldSideBeRendered2) {
            final float minU = array[0].getMinU();
            final float maxU = array[0].getMaxU();
            final float minV = array[0].getMinV();
            final float maxV = array[0].getMaxV();
            final int mixedBrightnessForBlock2 = blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos.down());
            final int n25 = mixedBrightnessForBlock2 >> 16 & 0xFFFF;
            final int n26 = mixedBrightnessForBlock2 & 0xFFFF;
            worldRenderer.pos(n7, n8, n9 + 1.0).color(n4, n4, n4, 1.0f).tex(minU, maxV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n7, n8, n9).color(n4, n4, n4, 1.0f).tex(minU, minV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n7 + 1.0, n8, n9).color(n4, n4, n4, 1.0f).tex(maxU, minV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n7 + 1.0, n8, n9 + 1.0).color(n4, n4, n4, 1.0f).tex(maxU, maxV).lightmap(n25, n26).endVertex();
        }
        while (true) {
            int n27 = 0;
            --n27;
            final BlockPos add = blockPos.add(0, 0, 0);
            final TextureAtlasSprite sprite2 = array[1];
            worldRenderer.setSprite(sprite2);
            if (borderFlags[0]) {
                final float n28 = fluidHeight;
                final float n29 = fluidHeight4;
                final double n30 = n7;
                final double n31 = n7 + 1.0;
                final double n32 = n9 + n10;
                final double n33 = n9 + n10;
                final float interpolatedU3 = sprite2.getInterpolatedU(0.0);
                final float interpolatedU4 = sprite2.getInterpolatedU(8.0);
                final float interpolatedV3 = sprite2.getInterpolatedV((1.0f - n28) * 16.0f * 0.5f);
                final float interpolatedV4 = sprite2.getInterpolatedV((1.0f - n29) * 16.0f * 0.5f);
                final float interpolatedV5 = sprite2.getInterpolatedV(8.0);
                final int mixedBrightnessForBlock3 = blockLiquid.getMixedBrightnessForBlock(blockAccess, add);
                final int n34 = mixedBrightnessForBlock3 >> 16 & 0xFFFF;
                final int n35 = mixedBrightnessForBlock3 & 0xFFFF;
                final float n36 = n6;
                final float n37 = n5 * n36 * n;
                final float n38 = n5 * n36 * n2;
                final float n39 = n5 * n36 * n3;
                worldRenderer.pos(n30, n8 + n28, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV3).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n31, n8 + n29, n33).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV4).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n31, n8 + 0.0, n33).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV5).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n30, n8 + 0.0, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV5).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n30, n8 + 0.0, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV5).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n31, n8 + 0.0, n33).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV5).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n31, n8 + n29, n33).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV4).lightmap(n34, n35).endVertex();
                worldRenderer.pos(n30, n8 + n28, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV3).lightmap(n34, n35).endVertex();
            }
            int n40 = 0;
            ++n40;
        }
    }
    
    private float getFluidHeight(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        float n = 0.0f;
        while (true) {
            final BlockPos add = blockPos.add(0, 0, 0);
            if (blockAccess.getBlockState(add.up()).getBlock().getMaterial() == material) {
                break;
            }
            final IBlockState blockState = blockAccess.getBlockState(add);
            final Material material2 = blockState.getBlock().getMaterial();
            if (material2 != material) {
                if (!material2.isSolid()) {
                    ++n;
                    int n2 = 0;
                    ++n2;
                }
            }
            else {
                final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
                int n2 = 0;
                if (intValue >= 8 || intValue == 0) {
                    n += BlockLiquid.getLiquidHeightPercent(intValue) * 10.0f;
                    n2 += 10;
                }
                n += BlockLiquid.getLiquidHeightPercent(intValue);
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return 1.0f;
    }
    
    public BlockFluidRenderer() {
        this.atlasSpritesLava = new TextureAtlasSprite[2];
        this.atlasSpritesWater = new TextureAtlasSprite[2];
        this.initAtlasSprites();
    }
    
    protected void initAtlasSprites() {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        this.atlasSpritesLava[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_still");
        this.atlasSpritesLava[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_flow");
        this.atlasSpritesWater[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_still");
        this.atlasSpritesWater[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_flow");
    }
}
