package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import com.nquantum.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;

public class RenderPlayer extends RendererLivingEntity
{
    private boolean smallArms;
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((AbstractClientPlayer)entityLivingBase, n);
    }
    
    public void renderLeftArm(final AbstractClientPlayer modelVisibilities) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer mainModel = this.getMainModel();
        this.setModelVisibilities(modelVisibilities);
        mainModel.isSneak = false;
        mainModel.setRotationAngles(mainModel.swingProgress = 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, modelVisibilities);
        mainModel.renderLeftArm();
    }
    
    public void renderRightArm(final AbstractClientPlayer modelVisibilities) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer mainModel = this.getMainModel();
        this.setModelVisibilities(modelVisibilities);
        mainModel.swingProgress = 0.0f;
        mainModel.isSneak = false;
        mainModel.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, modelVisibilities);
        mainModel.renderRightArm();
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    public void doRender(final AbstractClientPlayer modelVisibilities, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (Asyncware.instance.moduleManager.getModuleByName("Wallhack").isToggled()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
        if (!modelVisibilities.isUser() || this.renderManager.livingPlayer == modelVisibilities) {
            double n6 = n2;
            if (modelVisibilities.isSneaking() && !(modelVisibilities instanceof EntityPlayerSP)) {
                n6 = n2 - 0.125;
            }
            this.setModelVisibilities(modelVisibilities);
            super.doRender(modelVisibilities, n, n6, n3, n4, n5);
        }
        if (Asyncware.instance.moduleManager.getModuleByName("Wallhack").isToggled()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }
    
    public RenderPlayer(final RenderManager renderManager, final boolean smallArms) {
        super(renderManager, new ModelPlayer(0.0f, smallArms), 0.5f);
        this.smallArms = smallArms;
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerDeadmau5Head(this));
        this.addLayer(new LayerCape(this));
        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    @Override
    public ModelPlayer getMainModel() {
        return (ModelPlayer)super.getMainModel();
    }
    
    @Override
    protected void renderLivingAt(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        this.renderLivingAt((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    private void setModelVisibilities(final AbstractClientPlayer abstractClientPlayer) {
        final ModelPlayer mainModel = this.getMainModel();
        if (abstractClientPlayer.isSpectator()) {
            mainModel.setInvisible(false);
            mainModel.bipedHead.showModel = true;
            mainModel.bipedHeadwear.showModel = true;
        }
        else {
            final ItemStack currentItem = abstractClientPlayer.inventory.getCurrentItem();
            mainModel.setInvisible(true);
            mainModel.bipedHeadwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.HAT);
            mainModel.bipedBodyWear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.JACKET);
            mainModel.bipedLeftLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            mainModel.bipedRightLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            mainModel.bipedLeftArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            mainModel.bipedRightArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            mainModel.heldItemLeft = 0;
            mainModel.aimedBow = false;
            mainModel.isSneak = abstractClientPlayer.isSneaking();
            if (currentItem == null) {
                mainModel.heldItemRight = 0;
            }
            else {
                mainModel.heldItemRight = 1;
                if (abstractClientPlayer.getItemInUseCount() > 0) {
                    final EnumAction itemUseAction = currentItem.getItemUseAction();
                    if (itemUseAction == EnumAction.BLOCK) {
                        mainModel.heldItemRight = 3;
                    }
                    else if (itemUseAction == EnumAction.BOW) {
                        mainModel.aimedBow = true;
                    }
                }
            }
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((AbstractClientPlayer)entity);
    }
    
    protected void preRenderCallback(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
    }
    
    protected void renderLivingAt(final AbstractClientPlayer abstractClientPlayer, final double n, final double n2, final double n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            super.renderLivingAt(abstractClientPlayer, n + abstractClientPlayer.renderOffsetX, n2 + abstractClientPlayer.renderOffsetY, n3 + abstractClientPlayer.renderOffsetZ);
        }
        else {
            super.renderLivingAt(abstractClientPlayer, n, n2, n3);
        }
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final AbstractClientPlayer abstractClientPlayer) {
        return abstractClientPlayer.getLocationSkin();
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((AbstractClientPlayer)entity, n, n2, n3, n4, n5);
    }
    
    protected void renderOffsetLivingLabel(final AbstractClientPlayer abstractClientPlayer, final double n, double n2, final double n3, final String s, final float n4, final double n5) {
        if (n5 < 100.0) {
            final Scoreboard worldScoreboard = abstractClientPlayer.getWorldScoreboard();
            final ScoreObjective objectiveInDisplaySlot = worldScoreboard.getObjectiveInDisplaySlot(2);
            if (objectiveInDisplaySlot != null) {
                this.renderLivingLabel(abstractClientPlayer, worldScoreboard.getValueFromObjective(abstractClientPlayer.getName(), objectiveInDisplaySlot).getScorePoints() + " " + objectiveInDisplaySlot.getDisplayName(), n, n2, n3, 64);
                n2 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * n4;
            }
        }
        super.renderOffsetLivingLabel(abstractClientPlayer, n, n2, n3, s, n4, n5);
    }
    
    @Override
    protected void renderOffsetLivingLabel(final Entity entity, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderOffsetLivingLabel((AbstractClientPlayer)entity, n, n2, n3, s, n4, n5);
    }
    
    public RenderPlayer(final RenderManager renderManager) {
        this(renderManager, false);
    }
    
    protected void rotateCorpse(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            GlStateManager.rotate(abstractClientPlayer.getBedOrientationInDegrees(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.getDeathMaxRotation(abstractClientPlayer), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            super.rotateCorpse(abstractClientPlayer, n, n2, n3);
        }
    }
}
