package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.texture.*;

public class RenderItemFrame extends Render
{
    private final ModelResourceLocation mapModel;
    private RenderItem itemRenderer;
    private final Minecraft mc;
    private final ModelResourceLocation itemFrameModel;
    private static final ResourceLocation mapBackgroundTextures;
    
    public RenderItemFrame(final RenderManager renderManager, final RenderItem itemRenderer) {
        super(renderManager);
        this.mc = Minecraft.getMinecraft();
        this.itemFrameModel = new ModelResourceLocation("item_frame", "normal");
        this.mapModel = new ModelResourceLocation("item_frame", "map");
        this.itemRenderer = itemRenderer;
    }
    
    protected void renderName(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3) {
        if (Minecraft.isGuiEnabled() && entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entityItemFrame) {
            final float n4 = 0.016666668f * 1.6f;
            final double distanceSqToEntity = entityItemFrame.getDistanceSqToEntity(this.renderManager.livingPlayer);
            final float n5 = entityItemFrame.isSneaking() ? 32.0f : 64.0f;
            if (distanceSqToEntity < n5 * n5) {
                final String displayName = entityItemFrame.getDisplayedItem().getDisplayName();
                if (entityItemFrame.isSneaking()) {
                    final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
                    GlStateManager.translate((float)n + 0.0f, (float)n2 + entityItemFrame.height + 0.5f, (float)n3);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-n4, -n4, n4);
                    GlStateManager.translate(0.0f, 0.25f / n4, 0.0f);
                    GlStateManager.depthMask(false);
                    GlStateManager.blendFunc(770, 771);
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    final int n6 = fontRendererFromRenderManager.getStringWidth(displayName) / 2;
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(-n6 - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(-n6 - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n6 + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n6 + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    instance.draw();
                    GlStateManager.depthMask(true);
                    fontRendererFromRenderManager.drawString(displayName, -fontRendererFromRenderManager.getStringWidth(displayName) / 2, 0.0, 553648127);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.renderLivingLabel(entityItemFrame, displayName, n, n2, n3, 64);
                }
            }
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityItemFrame entityItemFrame) {
        return null;
    }
    
    public void doRender(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockPos hangingPosition = entityItemFrame.getHangingPosition();
        GlStateManager.translate(hangingPosition.getX() - entityItemFrame.posX + n + 0.5, hangingPosition.getY() - entityItemFrame.posY + n2 + 0.5, hangingPosition.getZ() - entityItemFrame.posZ + n3 + 0.5);
        GlStateManager.rotate(180.0f - entityItemFrame.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final BlockRendererDispatcher blockRendererDispatcher = this.mc.getBlockRendererDispatcher();
        final ModelManager modelManager = blockRendererDispatcher.getBlockModelShapes().getModelManager();
        IBakedModel bakedModel;
        if (entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
            bakedModel = modelManager.getModel(this.mapModel);
        }
        else {
            bakedModel = modelManager.getModel(this.itemFrameModel);
        }
        GlStateManager.translate(-0.5f, -0.5f, -0.5f);
        blockRendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(bakedModel, 1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0f, 0.0f, 0.4375f);
        this.renderItem(entityItemFrame);
        this.renderName(entityItemFrame, n + entityItemFrame.facingDirection.getFrontOffsetX() * 0.3f, n2 - 0.25, n3 + entityItemFrame.facingDirection.getFrontOffsetZ() * 0.3f);
    }
    
    static {
        mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    }
    
    private void renderItem(final EntityItemFrame entityItemFrame) {
        final ItemStack displayedItem = entityItemFrame.getDisplayedItem();
        if (displayedItem != null) {
            final EntityItem entityItem = new EntityItem(entityItemFrame.worldObj, 0.0, 0.0, 0.0, displayedItem);
            final Item item = entityItem.getEntityItem().getItem();
            entityItem.getEntityItem().stackSize = 1;
            entityItem.hoverStart = 0.0f;
            int rotation = entityItemFrame.getRotation();
            if (item == Items.filled_map) {
                rotation = rotation % 4 * 2;
            }
            GlStateManager.rotate(rotation * 360.0f / 8.0f, 0.0f, 0.0f, 1.0f);
            if (item == Items.filled_map) {
                this.renderManager.renderEngine.bindTexture(RenderItemFrame.mapBackgroundTextures);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                final float n = 0.0078125f;
                GlStateManager.scale(n, n, n);
                GlStateManager.translate(-64.0f, -64.0f, 0.0f);
                final MapData mapData = Items.filled_map.getMapData(entityItem.getEntityItem(), entityItemFrame.worldObj);
                GlStateManager.translate(0.0f, 0.0f, -1.0f);
                if (mapData != null) {
                    this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, true);
                }
            }
            else {
                TextureAtlasSprite atlasSprite = null;
                if (item == Items.compass) {
                    atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
                    this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                    if (atlasSprite instanceof TextureCompass) {
                        final TextureCompass textureCompass = (TextureCompass)atlasSprite;
                        final double currentAngle = textureCompass.currentAngle;
                        final double angleDelta = textureCompass.angleDelta;
                        textureCompass.currentAngle = 0.0;
                        textureCompass.angleDelta = 0.0;
                        textureCompass.updateCompass(entityItemFrame.worldObj, entityItemFrame.posX, entityItemFrame.posZ, MathHelper.wrapAngleTo180_float((float)(180 + entityItemFrame.facingDirection.getHorizontalIndex() * 90)), false, true);
                        textureCompass.currentAngle = currentAngle;
                        textureCompass.angleDelta = angleDelta;
                    }
                    else {
                        atlasSprite = null;
                    }
                }
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                if (!this.itemRenderer.shouldRenderItemIn3D(entityItem.getEntityItem()) || item instanceof ItemSkull) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                this.itemRenderer.func_181564_a(entityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
                if (atlasSprite != null && atlasSprite.getFrameCount() > 0) {
                    atlasSprite.updateAnimation();
                }
            }
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityItemFrame)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityItemFrame)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void renderName(final Entity entity, final double n, final double n2, final double n3) {
        this.renderName((EntityItemFrame)entity, n, n2, n3);
    }
}
