package net.minecraft.client.renderer;

import net.minecraft.client.settings.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private final ChestRenderer chestRenderer;
    private final GameSettings gameSettings;
    private BlockModelShapes blockModelShapes;
    private final BlockModelRenderer blockModelRenderer;
    private final BlockFluidRenderer fluidRenderer;
    
    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }
    
    public void renderBlockDamage(IBlockState actualState, final BlockPos blockPos, final TextureAtlasSprite textureAtlasSprite, final IBlockAccess blockAccess) {
        final Block block = actualState.getBlock();
        if (block.getRenderType() == 3) {
            actualState = block.getActualState(actualState, blockAccess, blockPos);
            this.blockModelRenderer.renderModel(blockAccess, new SimpleBakedModel.Builder(this.blockModelShapes.getModelForState(actualState), textureAtlasSprite).makeBakedModel(), actualState, blockPos, Tessellator.getInstance().getWorldRenderer());
        }
    }
    
    private IBakedModel getBakedModel(final IBlockState blockState, final BlockPos blockPos) {
        IBakedModel bakedModel = this.blockModelShapes.getModelForState(blockState);
        if (blockPos != null && this.gameSettings.allowBlockAlternatives && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
        }
        return bakedModel;
    }
    
    public boolean isRenderTypeChest(final Block block, final int n) {
        if (block == null) {
            return false;
        }
        final int renderType = block.getRenderType();
        return renderType != 3 && renderType == 2;
    }
    
    public void renderBlockBrightness(final IBlockState blockState, final float n) {
        final int renderType = blockState.getBlock().getRenderType();
        if (renderType != -1) {
            switch (renderType) {
                case 2: {
                    this.chestRenderer.renderChestBrightness(blockState.getBlock(), n);
                    break;
                }
                case 3: {
                    this.blockModelRenderer.renderModelBrightness(this.getBakedModel(blockState, null), blockState, n, true);
                    break;
                }
            }
        }
    }
    
    public boolean renderBlock(final IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final WorldRenderer worldRenderer) {
        final int renderType = blockState.getBlock().getRenderType();
        if (renderType == -1) {
            return false;
        }
        switch (renderType) {
            case 1: {
                return this.fluidRenderer.renderFluid(blockAccess, blockState, blockPos, worldRenderer);
            }
            case 2: {
                return false;
            }
            case 3: {
                return this.blockModelRenderer.renderModel(blockAccess, this.getModelFromBlockState(blockState, blockAccess, blockPos), blockState, blockPos, worldRenderer);
            }
            default: {
                return false;
            }
        }
    }
    
    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }
    
    public BlockRendererDispatcher(final BlockModelShapes blockModelShapes, final GameSettings gameSettings) {
        this.blockModelRenderer = new BlockModelRenderer();
        this.chestRenderer = new ChestRenderer();
        this.fluidRenderer = new BlockFluidRenderer();
        this.blockModelShapes = blockModelShapes;
        this.gameSettings = gameSettings;
    }
    
    public IBakedModel getModelFromBlockState(IBlockState actualState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = actualState.getBlock();
        if (blockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
            actualState = block.getActualState(actualState, blockAccess, blockPos);
        }
        IBakedModel bakedModel = this.blockModelShapes.getModelForState(actualState);
        if (blockPos != null && this.gameSettings.allowBlockAlternatives && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
        }
        return bakedModel;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }
}
