package optfine;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class ModelSprite
{
    private int textureOffsetY;
    private int sizeX;
    private float posY;
    private float sizeAdd;
    private int sizeY;
    private float minV;
    private int sizeZ;
    private float maxV;
    private float posZ;
    private float posX;
    private int textureOffsetX;
    private float minU;
    private float maxU;
    private ModelRenderer modelRenderer;
    
    public void render(final Tessellator tessellator, final float n) {
        GlStateManager.translate(this.posX * n, this.posY * n, this.posZ * n);
        float n2 = this.minU;
        float n3 = this.maxU;
        float n4 = this.minV;
        float n5 = this.maxV;
        if (this.modelRenderer.mirror) {
            n2 = this.maxU;
            n3 = this.minU;
        }
        if (this.modelRenderer.mirrorV) {
            n4 = this.maxV;
            n5 = this.minV;
        }
        renderItemIn2D(tessellator, n2, n4, n3, n5, this.sizeX, this.sizeY, n * this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
        GlStateManager.translate(-this.posX * n, -this.posY * n, -this.posZ * n);
    }
    
    public ModelSprite(final ModelRenderer modelRenderer, final int textureOffsetX, final int textureOffsetY, final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ, final float sizeAdd) {
        this.modelRenderer = null;
        this.textureOffsetX = 0;
        this.textureOffsetY = 0;
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.posZ = 0.0f;
        this.sizeX = 0;
        this.sizeY = 0;
        this.sizeZ = 0;
        this.sizeAdd = 0.0f;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 0.0f;
        this.maxV = 0.0f;
        this.modelRenderer = modelRenderer;
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.sizeAdd = sizeAdd;
        this.minU = textureOffsetX / modelRenderer.textureWidth;
        this.minV = textureOffsetY / modelRenderer.textureHeight;
        this.maxU = (textureOffsetX + sizeX) / modelRenderer.textureWidth;
        this.maxV = (textureOffsetY + sizeY) / modelRenderer.textureHeight;
    }
    
    public static void renderItemIn2D(final Tessellator tessellator, final float n, final float n2, final float n3, final float n4, final int n5, final int n6, float n7, final float n8, final float n9) {
        if (n7 < 6.25E-4f) {
            n7 = 6.25E-4f;
        }
        final float n10 = n3 - n;
        final float n11 = n4 - n2;
        final double n12 = MathHelper.abs(n10) * (n8 / 16.0f);
        final double n13 = MathHelper.abs(n11) * (n9 / 16.0f);
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, 0.0, 0.0).tex(n, n2).endVertex();
        worldRenderer.pos(n12, 0.0, 0.0).tex(n3, n2).endVertex();
        worldRenderer.pos(n12, n13, 0.0).tex(n3, n4).endVertex();
        worldRenderer.pos(0.0, n13, 0.0).tex(n, n4).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, n13, n7).tex(n, n4).endVertex();
        worldRenderer.pos(n12, n13, n7).tex(n3, n4).endVertex();
        worldRenderer.pos(n12, 0.0, n7).tex(n3, n2).endVertex();
        worldRenderer.pos(0.0, 0.0, n7).tex(n, n2).endVertex();
        tessellator.draw();
        final float n14 = 0.5f * n10 / n5;
        final float n15 = 0.5f * n11 / n6;
        GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        int n18 = 0;
        while (0 < n5) {
            final float n16 = 0 / (float)n5;
            final float n17 = n + n10 * n16 + n14;
            worldRenderer.pos(n16 * n12, 0.0, n7).tex(n17, n2).endVertex();
            worldRenderer.pos(n16 * n12, 0.0, 0.0).tex(n17, n2).endVertex();
            worldRenderer.pos(n16 * n12, n13, 0.0).tex(n17, n4).endVertex();
            worldRenderer.pos(n16 * n12, n13, n7).tex(n17, n4).endVertex();
            ++n18;
        }
        tessellator.draw();
        GL11.glNormal3f(1.0f, 0.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        while (0 < n5) {
            final float n19 = 0 / (float)n5;
            final float n20 = n + n10 * n19 + n14;
            final float n21 = n19 + 1.0f / n5;
            worldRenderer.pos(n21 * n12, n13, n7).tex(n20, n4).endVertex();
            worldRenderer.pos(n21 * n12, n13, 0.0).tex(n20, n4).endVertex();
            worldRenderer.pos(n21 * n12, 0.0, 0.0).tex(n20, n2).endVertex();
            worldRenderer.pos(n21 * n12, 0.0, n7).tex(n20, n2).endVertex();
            ++n18;
        }
        tessellator.draw();
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        while (0 < n6) {
            final float n22 = 0 / (float)n6;
            final float n23 = n2 + n11 * n22 + n15;
            final float n24 = n22 + 1.0f / n6;
            worldRenderer.pos(0.0, n24 * n13, 0.0).tex(n, n23).endVertex();
            worldRenderer.pos(n12, n24 * n13, 0.0).tex(n3, n23).endVertex();
            worldRenderer.pos(n12, n24 * n13, n7).tex(n3, n23).endVertex();
            worldRenderer.pos(0.0, n24 * n13, n7).tex(n, n23).endVertex();
            ++n18;
        }
        tessellator.draw();
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        while (0 < n6) {
            final float n25 = 0 / (float)n6;
            final float n26 = n2 + n11 * n25 + n15;
            worldRenderer.pos(n12, n25 * n13, 0.0).tex(n3, n26).endVertex();
            worldRenderer.pos(0.0, n25 * n13, 0.0).tex(n, n26).endVertex();
            worldRenderer.pos(0.0, n25 * n13, n7).tex(n, n26).endVertex();
            worldRenderer.pos(n12, n25 * n13, n7).tex(n3, n26).endVertex();
            ++n18;
        }
        tessellator.draw();
    }
}
