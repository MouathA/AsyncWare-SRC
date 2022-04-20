package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation beaconBeam;
    
    @Override
    public boolean func_181055_a() {
        return true;
    }
    
    static {
        beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
    }
    
    public void renderTileEntityAt(final TileEntityBeacon tileEntityBeacon, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float shouldBeamRender = tileEntityBeacon.shouldBeamRender();
        GlStateManager.alphaFunc(516, 0.1f);
        if (shouldBeamRender > 0.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final List beamSegments = tileEntityBeacon.getBeamSegments();
            while (0 < beamSegments.size()) {
                final TileEntityBeacon.BeamSegment beamSegment = beamSegments.get(0);
                final int n6 = 0 + beamSegment.getHeight();
                this.bindTexture(TileEntityBeaconRenderer.beaconBeam);
                GL11.glTexParameterf(3553, 10242, 10497.0f);
                GL11.glTexParameterf(3553, 10243, 10497.0f);
                GlStateManager.depthMask(true);
                GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
                final double n7 = tileEntityBeacon.getWorld().getTotalWorldTime() + (double)n4;
                final double func_181162_h = MathHelper.func_181162_h(-n7 * 0.2 - MathHelper.floor_double(-n7 * 0.1));
                final float n8 = beamSegment.getColors()[0];
                final float n9 = beamSegment.getColors()[1];
                final float n10 = beamSegment.getColors()[2];
                final double n11 = n7 * 0.025 * -1.5;
                final double n12 = 0.5 + Math.cos(n11 + 2.356194490192345) * 0.2;
                final double n13 = 0.5 + Math.sin(n11 + 2.356194490192345) * 0.2;
                final double n14 = 0.5 + Math.cos(n11 + 0.7853981633974483) * 0.2;
                final double n15 = 0.5 + Math.sin(n11 + 0.7853981633974483) * 0.2;
                final double n16 = 0.5 + Math.cos(n11 + 3.9269908169872414) * 0.2;
                final double n17 = 0.5 + Math.sin(n11 + 3.9269908169872414) * 0.2;
                final double n18 = 0.5 + Math.cos(n11 + 5.497787143782138) * 0.2;
                final double n19 = 0.5 + Math.sin(n11 + 5.497787143782138) * 0.2;
                final double n20 = -1.0 + func_181162_h;
                final double n21 = beamSegment.getHeight() * shouldBeamRender * 2.5 + n20;
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n + n12, n2 + n6, n3 + n13).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + 0, n3 + n13).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + 0, n3 + n15).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + n6, n3 + n15).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + n6, n3 + n19).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + 0, n3 + n19).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + 0, n3 + n17).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + n6, n3 + n17).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + n6, n3 + n15).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + 0, n3 + n15).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + 0, n3 + n19).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + n6, n3 + n19).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + n6, n3 + n17).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + 0, n3 + n17).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + 0, n3 + n13).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + n6, n3 + n13).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                instance.draw();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.depthMask(false);
                final double n22 = -1.0 + func_181162_h;
                final double n23 = beamSegment.getHeight() * shouldBeamRender + n22;
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.2).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + 0, n3 + 0.2).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + 0, n3 + 0.2).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.2).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.8).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + 0, n3 + 0.8).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + 0, n3 + 0.8).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.8).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.2).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + 0, n3 + 0.2).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + 0, n3 + 0.8).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.8).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.8).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + 0, n3 + 0.8).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + 0, n3 + 0.2).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.2).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                instance.draw();
                GlStateManager.depthMask(true);
                int n24 = 0;
                ++n24;
            }
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityBeacon)tileEntity, n, n2, n3, n4, n5);
    }
}
