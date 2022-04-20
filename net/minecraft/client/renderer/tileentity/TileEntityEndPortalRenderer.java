package net.minecraft.client.renderer.tileentity;

import java.util.*;
import net.minecraft.util.*;
import java.nio.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer
{
    private static final Random field_147527_e;
    private static final ResourceLocation END_PORTAL_TEXTURE;
    FloatBuffer field_147528_b;
    private static final ResourceLocation END_SKY_TEXTURE;
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityEndPortal)tileEntity, n, n2, n3, n4, n5);
    }
    
    public TileEntityEndPortalRenderer() {
        this.field_147528_b = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public void renderTileEntityAt(final TileEntityEndPortal tileEntityEndPortal, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float n6 = (float)this.rendererDispatcher.entityX;
        final float n7 = (float)this.rendererDispatcher.entityY;
        final float n8 = (float)this.rendererDispatcher.entityZ;
        TileEntityEndPortalRenderer.field_147527_e.setSeed(31100L);
        final float n9 = 0.75f;
        while (true) {
            final float n10 = 1.0f / (16 + 1.0f);
            this.bindTexture(TileEntityEndPortalRenderer.END_SKY_TEXTURE);
            final float n11 = 0.1f;
            final float n12 = 65.0f;
            final float n13 = 0.125f;
            GlStateManager.blendFunc(770, 771);
            final float n14 = (float)(-(n2 + n9));
            GlStateManager.translate(n6, (float)(n2 + n9) + (n14 + (float)ActiveRenderInfo.getPosition().yCoord) / (n14 + n12 + (float)ActiveRenderInfo.getPosition().yCoord), n8);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9473, this.func_147525_a(1.0f, 0.0f, 0.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9473, this.func_147525_a(0.0f, 0.0f, 1.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9473, this.func_147525_a(0.0f, 0.0f, 0.0f, 1.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, this.func_147525_a(0.0f, 1.0f, 0.0f, 0.0f));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.matrixMode(5890);
            GlStateManager.translate(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GlStateManager.scale(n13, n13, n13);
            GlStateManager.translate(0.5f, 0.5f, 0.0f);
            GlStateManager.rotate(0 * 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.0f);
            GlStateManager.translate(-n6, -n8, -n7);
            final float n15 = n14 + (float)ActiveRenderInfo.getPosition().yCoord;
            GlStateManager.translate((float)ActiveRenderInfo.getPosition().xCoord * n12 / n15, (float)ActiveRenderInfo.getPosition().zCoord * n12 / n15, -n7);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            final float n16 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.1f) * n11;
            final float n17 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.4f) * n11;
            final float n18 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.5f) * n11;
            final float n21;
            final float n20;
            final float n19 = n20 = (n21 = 1.0f * n11);
            worldRenderer.pos(n, n2 + n9, n3).color(n20, n19, n21, 1.0f).endVertex();
            worldRenderer.pos(n, n2 + n9, n3 + 1.0).color(n20, n19, n21, 1.0f).endVertex();
            worldRenderer.pos(n + 1.0, n2 + n9, n3 + 1.0).color(n20, n19, n21, 1.0f).endVertex();
            worldRenderer.pos(n + 1.0, n2 + n9, n3).color(n20, n19, n21, 1.0f).endVertex();
            instance.draw();
            GlStateManager.matrixMode(5888);
            this.bindTexture(TileEntityEndPortalRenderer.END_SKY_TEXTURE);
            int n22 = 0;
            ++n22;
        }
    }
    
    static {
        END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
        END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
        field_147527_e = new Random(31100L);
    }
    
    private FloatBuffer func_147525_a(final float n, final float n2, final float n3, final float n4) {
        this.field_147528_b.clear();
        this.field_147528_b.put(n).put(n2).put(n3).put(n4);
        this.field_147528_b.flip();
        return this.field_147528_b;
    }
}
