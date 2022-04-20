package net.minecraft.client.renderer.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer
{
    private final BlockRendererDispatcher blockRenderer;
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityPiston)tileEntity, n, n2, n3, n4, n5);
    }
    
    public void renderTileEntityAt(final TileEntityPiston tileEntityPiston, final double n, final double n2, final double n3, final float n4, final int n5) {
        final BlockPos pos = tileEntityPiston.getPos();
        final IBlockState pistonState = tileEntityPiston.getPistonState();
        final Block block = pistonState.getBlock();
        if (block.getMaterial() != Material.air && tileEntityPiston.getProgress(n4) < 1.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.blendFunc(770, 771);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(7425);
            }
            else {
                GlStateManager.shadeModel(7424);
            }
            worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
            worldRenderer.setTranslation((float)n - pos.getX() + tileEntityPiston.getOffsetX(n4), (float)n2 - pos.getY() + tileEntityPiston.getOffsetY(n4), (float)n3 - pos.getZ() + tileEntityPiston.getOffsetZ(n4));
            final World world = this.getWorld();
            if (block == Blocks.piston_head && tileEntityPiston.getProgress(n4) < 0.5f) {
                final IBlockState withProperty = pistonState.withProperty(BlockPistonExtension.SHORT, true);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(withProperty, world, pos), withProperty, pos, worldRenderer, true);
            }
            else if (tileEntityPiston.shouldPistonHeadBeRendered() && !tileEntityPiston.isExtending()) {
                final IBlockState withProperty2 = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.TYPE, (block == Blocks.sticky_piston) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT).withProperty(BlockPistonExtension.FACING, pistonState.getValue(BlockPistonBase.FACING)).withProperty(BlockPistonExtension.SHORT, tileEntityPiston.getProgress(n4) >= 0.5f);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(withProperty2, world, pos), withProperty2, pos, worldRenderer, true);
                worldRenderer.setTranslation((float)n - pos.getX(), (float)n2 - pos.getY(), (float)n3 - pos.getZ());
                pistonState.withProperty(BlockPistonBase.EXTENDED, true);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(pistonState, world, pos), pistonState, pos, worldRenderer, true);
            }
            else {
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(pistonState, world, pos), pistonState, pos, worldRenderer, false);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            instance.draw();
        }
    }
    
    public TileEntityPistonRenderer() {
        this.blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
    }
}
