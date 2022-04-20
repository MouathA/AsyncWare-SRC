package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderFallingBlock extends Render
{
    public void doRender(final EntityFallingBlock entityFallingBlock, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (entityFallingBlock.getBlock() != null) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            final IBlockState block = entityFallingBlock.getBlock();
            final Block block2 = block.getBlock();
            final BlockPos blockPos = new BlockPos(entityFallingBlock);
            final World worldObj = entityFallingBlock.getWorldObj();
            if (block != worldObj.getBlockState(blockPos) && block2.getRenderType() != -1 && block2.getRenderType() == 3) {
                GlStateManager.translate((float)n, (float)n2, (float)n3);
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
                worldRenderer.setTranslation(-blockPos.getX() - 0.5f, -blockPos.getY(), -blockPos.getZ() - 0.5f);
                final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                blockRendererDispatcher.getBlockModelRenderer().renderModel(worldObj, blockRendererDispatcher.getModelFromBlockState(block, worldObj, null), block, blockPos, worldRenderer, false);
                worldRenderer.setTranslation(0.0, 0.0, 0.0);
                instance.draw();
                super.doRender(entityFallingBlock, n, n2, n3, n4, n5);
            }
        }
    }
    
    public RenderFallingBlock(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFallingBlock)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFallingBlock)entity, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityFallingBlock entityFallingBlock) {
        return TextureMap.locationBlocksTexture;
    }
}
