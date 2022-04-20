package net.minecraft.client.renderer.entity;

import java.nio.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public abstract class RendererLivingEntity extends Render
{
    protected boolean renderOutlines;
    protected FloatBuffer brightnessBuffer;
    protected List layerRenderers;
    private static final Logger logger;
    private static final DynamicTexture field_177096_e;
    protected ModelBase mainModel;
    
    protected void unsetBrightness() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.bindTexture(0);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    protected void renderLivingAt(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
    }
    
    protected float getSwingProgress(final EntityLivingBase entityLivingBase, final float n) {
        return entityLivingBase.getSwingProgress(n);
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return 90.0f;
    }
    
    public void doRender(final EntityLivingBase scoreTeamColor, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.mainModel.swingProgress = this.getSwingProgress(scoreTeamColor, n5);
        this.mainModel.isRiding = scoreTeamColor.isRiding();
        this.mainModel.isChild = scoreTeamColor.isChild();
        float interpolateRotation = this.interpolateRotation(scoreTeamColor.prevRenderYawOffset, scoreTeamColor.renderYawOffset, n5);
        final float interpolateRotation2 = this.interpolateRotation(scoreTeamColor.prevRotationYawHead, scoreTeamColor.rotationYawHead, n5);
        float n6 = interpolateRotation2 - interpolateRotation;
        if (scoreTeamColor.isRiding() && scoreTeamColor.ridingEntity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)scoreTeamColor.ridingEntity;
            n6 = interpolateRotation2 - this.interpolateRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, n5);
            float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n6);
            if (wrapAngleTo180_float < -85.0f) {
                wrapAngleTo180_float = -85.0f;
            }
            if (wrapAngleTo180_float >= 85.0f) {
                wrapAngleTo180_float = 85.0f;
            }
            interpolateRotation = interpolateRotation2 - wrapAngleTo180_float;
            if (wrapAngleTo180_float * wrapAngleTo180_float > 2500.0f) {
                interpolateRotation += wrapAngleTo180_float * 0.2f;
            }
        }
        final float n7 = scoreTeamColor.prevRotationPitch + (scoreTeamColor.rotationPitch - scoreTeamColor.prevRotationPitch) * n5;
        this.renderLivingAt(scoreTeamColor, n, n2, n3);
        final float handleRotationFloat = this.handleRotationFloat(scoreTeamColor, n5);
        this.rotateCorpse(scoreTeamColor, handleRotationFloat, interpolateRotation, n5);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.preRenderCallback(scoreTeamColor, n5);
        GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
        float n8 = scoreTeamColor.prevLimbSwingAmount + (scoreTeamColor.limbSwingAmount - scoreTeamColor.prevLimbSwingAmount) * n5;
        float n9 = scoreTeamColor.limbSwing - scoreTeamColor.limbSwingAmount * (1.0f - n5);
        if (scoreTeamColor.isChild()) {
            n9 *= 3.0f;
        }
        if (n8 > 1.0f) {
            n8 = 1.0f;
        }
        this.mainModel.setLivingAnimations(scoreTeamColor, n9, n8, n5);
        this.mainModel.setRotationAngles(n9, n8, handleRotationFloat, n6, n7, 0.0625f, scoreTeamColor);
        if (this.renderOutlines) {
            final boolean setScoreTeamColor = this.setScoreTeamColor(scoreTeamColor);
            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, 0.0625f);
            if (setScoreTeamColor) {
                this.unsetScoreTeamColor();
            }
        }
        else {
            final boolean setDoRenderBrightness = this.setDoRenderBrightness(scoreTeamColor, n5);
            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, 0.0625f);
            if (setDoRenderBrightness) {
                this.unsetBrightness();
            }
            GlStateManager.depthMask(true);
            if (!(scoreTeamColor instanceof EntityPlayer) || !((EntityPlayer)scoreTeamColor).isSpectator()) {
                this.renderLayers(scoreTeamColor, n9, n8, n5, handleRotationFloat, n6, n7, 0.0625f);
            }
        }
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (!this.renderOutlines) {
            super.doRender(scoreTeamColor, n, n2, n3, n4, n5);
        }
    }
    
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
        if (entityLivingBase.deathTime > 0) {
            float sqrt_float = MathHelper.sqrt_float((entityLivingBase.deathTime + n3 - 1.0f) / 20.0f * 1.6f);
            if (sqrt_float > 1.0f) {
                sqrt_float = 1.0f;
            }
            GlStateManager.rotate(sqrt_float * this.getDeathMaxRotation(entityLivingBase), 0.0f, 0.0f, 1.0f);
        }
        else {
            final String textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(entityLivingBase.getName());
            if (textWithoutFormattingCodes != null && (textWithoutFormattingCodes.equals("Dinnerbone") || textWithoutFormattingCodes.equals("Grumm")) && (!(entityLivingBase instanceof EntityPlayer) || ((EntityPlayer)entityLivingBase).isWearing(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0f, entityLivingBase.height + 0.1f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    public void renderName(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        if (entityLivingBase != 0) {
            final double distanceSqToEntity = entityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);
            final float n4 = entityLivingBase.isSneaking() ? 32.0f : 64.0f;
            if (distanceSqToEntity < n4 * n4) {
                final String formattedText = entityLivingBase.getDisplayName().getFormattedText();
                GlStateManager.alphaFunc(516, 0.1f);
                if (entityLivingBase.isSneaking()) {
                    final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
                    GlStateManager.translate((float)n, (float)n2 + entityLivingBase.height + 0.5f - (entityLivingBase.isChild() ? (entityLivingBase.height / 2.0f) : 0.0f), (float)n3);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
                    GlStateManager.translate(0.0f, 9.374999f, 0.0f);
                    GlStateManager.depthMask(false);
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    final int n5 = fontRendererFromRenderManager.getStringWidth(formattedText) / 2;
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(-n5 - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(-n5 - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n5 + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n5 + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    instance.draw();
                    GlStateManager.depthMask(true);
                    fontRendererFromRenderManager.drawString(formattedText, -fontRendererFromRenderManager.getStringWidth(formattedText) / 2, 0.0, 553648127);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.renderOffsetLivingLabel(entityLivingBase, n, n2 - (entityLivingBase.isChild() ? (entityLivingBase.height / 2.0f) : 0.0), n3, formattedText, 0.02666667f, distanceSqToEntity);
                }
            }
        }
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLivingBase)entity, n, n2, n3, n4, n5);
    }
    
    protected float interpolateRotation(final float n, final float n2, final float n3) {
        float n4;
        for (n4 = n2 - n; n4 < -180.0f; n4 += 360.0f) {}
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    public void renderName(final Entity entity, final double n, final double n2, final double n3) {
        this.renderName((EntityLivingBase)entity, n, n2, n3);
    }
    
    protected void renderModel(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final boolean b = !entityLivingBase.isInvisible();
        final boolean b2 = !b && !entityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
        if (b || b2) {
            if (!this.bindEntityTexture(entityLivingBase)) {
                return;
            }
            if (b2) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.depthMask(false);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569f);
            }
            this.mainModel.render(entityLivingBase, n, n2, n3, n4, n5, n6);
            if (b2) {
                GlStateManager.alphaFunc(516, 0.1f);
                GlStateManager.depthMask(true);
            }
        }
    }
    
    protected boolean setDoRenderBrightness(final EntityLivingBase entityLivingBase, final float n) {
        return this.setBrightness(entityLivingBase, n, true);
    }
    
    protected boolean removeLayer(final LayerRenderer layerRenderer) {
        return this.layerRenderers.remove(layerRenderer);
    }
    
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
    }
    
    protected boolean setScoreTeamColor(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPlayer) {
            final ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam)entityLivingBase.getTeam();
            if (scorePlayerTeam != null) {
                final String formatFromString = FontRenderer.getFormatFromString(scorePlayerTeam.getColorPrefix());
                if (formatFromString.length() >= 2) {
                    this.getFontRendererFromRenderManager().getColorCode(formatFromString.charAt(1));
                }
            }
        }
        final float n = 255 / 255.0f;
        final float n2 = 255 / 255.0f;
        final float n3 = 255 / 255.0f;
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(n, n2, n3, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }
    
    protected boolean canRenderName(final Entity entity) {
        return this.canRenderName((EntityLivingBase)entity);
    }
    
    protected boolean addLayer(final LayerRenderer layerRenderer) {
        return this.layerRenderers.add(layerRenderer);
    }
    
    public RendererLivingEntity(final RenderManager renderManager, final ModelBase mainModel, final float shadowSize) {
        super(renderManager);
        this.brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
        this.layerRenderers = Lists.newArrayList();
        this.renderOutlines = false;
        this.mainModel = mainModel;
        this.shadowSize = shadowSize;
    }
    
    public void setRenderOutlines(final boolean renderOutlines) {
        this.renderOutlines = renderOutlines;
    }
    
    protected void renderLayers(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        for (final LayerRenderer layerRenderer : this.layerRenderers) {
            final boolean setBrightness = this.setBrightness(entityLivingBase, n3, layerRenderer.shouldCombineTextures());
            layerRenderer.doRenderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7);
            if (setBrightness) {
                this.unsetBrightness();
            }
        }
    }
    
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return entityLivingBase.ticksExisted + n;
    }
    
    protected boolean setBrightness(final EntityLivingBase entityLivingBase, final float n, final boolean b) {
        final int colorMultiplier = this.getColorMultiplier(entityLivingBase, entityLivingBase.getBrightness(n), n);
        final boolean b2 = (colorMultiplier >> 24 & 0xFF) > 0;
        final boolean b3 = entityLivingBase.hurtTime > 0 || entityLivingBase.deathTime > 0;
        if (!b2 && !b3) {
            return false;
        }
        if (!b2 && !b) {
            return false;
        }
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        this.brightnessBuffer.position(0);
        if (b3) {
            this.brightnessBuffer.put(1.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.3f);
        }
        else {
            final float n2 = (colorMultiplier >> 24 & 0xFF) / 255.0f;
            final float n3 = (colorMultiplier >> 16 & 0xFF) / 255.0f;
            final float n4 = (colorMultiplier >> 8 & 0xFF) / 255.0f;
            final float n5 = (colorMultiplier & 0xFF) / 255.0f;
            this.brightnessBuffer.put(n3);
            this.brightnessBuffer.put(n4);
            this.brightnessBuffer.put(n5);
            this.brightnessBuffer.put(1.0f - n2);
        }
        this.brightnessBuffer.flip();
        GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.bindTexture(RendererLivingEntity.field_177096_e.getGlTextureId());
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }
    
    static {
        logger = LogManager.getLogger();
        field_177096_e = new DynamicTexture(16, 16);
        final int[] textureData = RendererLivingEntity.field_177096_e.getTextureData();
        while (true) {
            textureData[0] = -1;
            int n = 0;
            ++n;
        }
    }
    
    protected int getColorMultiplier(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        return 0;
    }
    
    public ModelBase getMainModel() {
        return this.mainModel;
    }
    
    protected void unsetScoreTeamColor() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void transformHeldFull3DItemLayer() {
    }
}
