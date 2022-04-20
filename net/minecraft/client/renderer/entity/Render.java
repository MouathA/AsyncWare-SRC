package net.minecraft.client.renderer.entity;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.*;
import com.nquantum.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

public abstract class Render
{
    protected final RenderManager renderManager;
    protected float shadowSize;
    protected float shadowOpaque;
    private static final ResourceLocation shadowTextures;
    
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.renderName(entity, n, n2, n3);
    }
    
    public RenderManager getRenderManager() {
        return this.renderManager;
    }
    
    private void func_180549_a(final Block block, final double n, final double n2, final double n3, final BlockPos blockPos, final float n4, final float n5, final double n6, final double n7, final double n8) {
        if (block.isFullCube()) {
            final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            double n9 = (n4 - (n2 - (blockPos.getY() + n7)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(blockPos);
            if (n9 >= 0.0) {
                if (n9 > 1.0) {
                    n9 = 1.0;
                }
                final double n10 = blockPos.getX() + block.getBlockBoundsMinX() + n6;
                final double n11 = blockPos.getX() + block.getBlockBoundsMaxX() + n6;
                final double n12 = blockPos.getY() + block.getBlockBoundsMinY() + n7 + 0.015625;
                final double n13 = blockPos.getZ() + block.getBlockBoundsMinZ() + n8;
                final double n14 = blockPos.getZ() + block.getBlockBoundsMaxZ() + n8;
                final float n15 = (float)((n - n10) / 2.0 / n5 + 0.5);
                final float n16 = (float)((n - n11) / 2.0 / n5 + 0.5);
                final float n17 = (float)((n3 - n13) / 2.0 / n5 + 0.5);
                final float n18 = (float)((n3 - n14) / 2.0 / n5 + 0.5);
                worldRenderer.pos(n10, n12, n13).tex(n15, n17).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n10, n12, n14).tex(n15, n18).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n11, n12, n14).tex(n16, n18).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n11, n12, n13).tex(n16, n17).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
            }
        }
    }
    
    protected Render(final RenderManager renderManager) {
        this.shadowOpaque = 1.0f;
        this.renderManager = renderManager;
    }
    
    public void bindTexture(final ResourceLocation resourceLocation) {
        this.renderManager.renderEngine.bindTexture(resourceLocation);
    }
    
    protected void renderOffsetLivingLabel(final Entity entity, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderLivingLabel(entity, s, n, n2, n3, 64);
    }
    
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }
    
    protected boolean bindEntityTexture(final Entity entity) {
        final ResourceLocation entityTexture = this.getEntityTexture(entity);
        if (entityTexture == null) {
            return false;
        }
        this.bindTexture(entityTexture);
        return true;
    }
    
    protected abstract ResourceLocation getEntityTexture(final Entity p0);
    
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
    
    private void renderShadow(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.blendFunc(770, 771);
        this.renderManager.renderEngine.bindTexture(Render.shadowTextures);
        final World worldFromRenderManager = this.getWorldFromRenderManager();
        GlStateManager.depthMask(false);
        float shadowSize = this.shadowSize;
        if (entity instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entity;
            shadowSize *= entityLiving.getRenderSizeModifier();
            if (entityLiving.isChild()) {
                shadowSize *= 0.5f;
            }
        }
        final double n6 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n5;
        final double n7 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n5;
        final double n8 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n5;
        final int floor_double = MathHelper.floor_double(n6 - shadowSize);
        final int floor_double2 = MathHelper.floor_double(n6 + shadowSize);
        final int floor_double3 = MathHelper.floor_double(n7 - shadowSize);
        final int floor_double4 = MathHelper.floor_double(n7);
        final int floor_double5 = MathHelper.floor_double(n8 - shadowSize);
        final int floor_double6 = MathHelper.floor_double(n8 + shadowSize);
        final double n9 = n - n6;
        final double n10 = n2 - n7;
        final double n11 = n3 - n8;
        final Tessellator instance = Tessellator.getInstance();
        instance.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        for (final BlockPos blockPos : BlockPos.getAllInBoxMutable(new BlockPos(floor_double, floor_double3, floor_double5), new BlockPos(floor_double2, floor_double4, floor_double6))) {
            final Block block = worldFromRenderManager.getBlockState(blockPos.down()).getBlock();
            if (block.getRenderType() != -1 && worldFromRenderManager.getLightFromNeighbors(blockPos) > 3) {
                this.func_180549_a(block, n, n2, n3, blockPos, n4, shadowSize, n9, n10, n11);
            }
        }
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.depthMask(true);
    }
    
    protected void renderName(final Entity entity, final double n, final double n2, final double n3) {
        if (entity != 0) {
            this.renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), n, n2, n3, 64);
        }
    }
    
    protected void renderLivingLabel(final Entity entity, final String s, final double n, final double n2, final double n3, final int n4) {
        if (Asyncware.instance.moduleManager.getModuleByName("NameTags").isToggled()) {
            return;
        }
        GL11.glPushMatrix();
        final float n5 = 1.1f - entity.getDistanceToEntity(this.renderManager.livingPlayer);
        GL11.glScalef(1.1f, 1.1f, 1.1f);
        GL11.glTranslatef(0.0f, 0.1f, 0.0f);
        if (-(-entity.getDistanceSqToEntity(this.renderManager.livingPlayer)) < n4 * n4) {
            final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
            final float n6 = 0.016666668f * 1.6f;
            GlStateManager.translate((float)n + 0.0f, (float)n2 + entity.height + 0.5f, (float)n3);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-n6, -n6, n6);
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            if (s.equals("deadmau5")) {}
            final int n7 = fontRendererFromRenderManager.getStringWidth(s) / 2;
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(-n7 - 1, -11, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(-n7 - 1, -2, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(n7 + 1, -2, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(n7 + 1, -11, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            instance.draw();
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / 2, -10, -1);
            GlStateManager.depthMask(true);
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / 2, -10, -1);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
    }
    
    public static void renderOffsetAABB(final AxisAlignedBB axisAlignedBB, final double n, final double n2, final double n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.setTranslation(n, n2, n3);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_NORMAL);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        instance.draw();
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
    }
    
    public boolean shouldRender(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
        if (entityBoundingBox.func_181656_b() || entityBoundingBox.getAverageEdgeLength() == 0.0) {
            entityBoundingBox = new AxisAlignedBB(entity.posX - 2.0, entity.posY - 2.0, entity.posZ - 2.0, entity.posX + 2.0, entity.posY + 2.0, entity.posZ + 2.0);
        }
        return entity.isInRangeToRender3d(n, n2, n3) && (entity.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entityBoundingBox));
    }
    
    static {
        shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    }
    
    public void doRenderShadowAndFire(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.renderManager.options != null) {
            if (this.renderManager.options.field_181151_V && this.shadowSize > 0.0f && !entity.isInvisible() && this.renderManager.isRenderShadow()) {
                final float n6 = (float)((1.0 - this.renderManager.getDistanceToCamera(entity.posX, entity.posY, entity.posZ) / 256.0) * this.shadowOpaque);
                if (n6 > 0.0f) {
                    this.renderShadow(entity, n, n2, n3, n6, n5);
                }
            }
            if (entity.canRenderOnFire() && (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())) {
                this.renderEntityOnFire(entity, n, n2, n3, n5);
            }
        }
    }
    
    private void renderEntityOnFire(final Entity entity, final double n, final double n2, final double n3, final float n4) {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite atlasSprite = textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_0");
        textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_1");
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n5 = entity.width * 1.4f;
        GlStateManager.scale(n5, n5, n5);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        float n6 = 0.5f;
        final float n7 = 0.0f;
        float n8 = entity.height / n5;
        float n9 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, -0.3f + (int)n8 * 0.02f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float n10 = 0.0f;
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        while (n8 > 0.0f) {
            final TextureAtlasSprite textureAtlasSprite = atlasSprite;
            this.bindTexture(TextureMap.locationBlocksTexture);
            final float minU = textureAtlasSprite.getMinU();
            final float minV = textureAtlasSprite.getMinV();
            final float maxU = textureAtlasSprite.getMaxU();
            final float maxV = textureAtlasSprite.getMaxV();
            final float n11 = maxU;
            final float n12 = minU;
            final float n13 = n11;
            worldRenderer.pos(n6 - n7, 0.0f - n9, n10).tex(n12, maxV).endVertex();
            worldRenderer.pos(-n6 - n7, 0.0f - n9, n10).tex(n13, maxV).endVertex();
            worldRenderer.pos(-n6 - n7, 1.4f - n9, n10).tex(n13, minV).endVertex();
            worldRenderer.pos(n6 - n7, 1.4f - n9, n10).tex(n12, minV).endVertex();
            n8 -= 0.45f;
            n9 -= 0.45f;
            n6 *= 0.9f;
            n10 += 0.03f;
            int n14 = 0;
            ++n14;
        }
        instance.draw();
    }
}
