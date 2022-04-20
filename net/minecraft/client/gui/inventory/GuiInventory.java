package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import java.io.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float oldMouseX;
    private float oldMouseY;
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        else {
            super.initGui();
        }
    }
    
    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        this.updateActivePotionEffects();
    }
    
    public static void drawEntityOnScreen(final int n, final int n2, final int n3, final float n4, final float n5, final EntityLivingBase entityLivingBase) {
        GlStateManager.translate((float)n, (float)n2, 50.0f);
        GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float renderYawOffset = entityLivingBase.renderYawOffset;
        final float rotationYaw = entityLivingBase.rotationYaw;
        final float rotationPitch = entityLivingBase.rotationPitch;
        final float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        final float rotationYawHead = entityLivingBase.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-(float)Math.atan(n5 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        entityLivingBase.renderYawOffset = (float)Math.atan(n4 / 40.0f) * 20.0f;
        entityLivingBase.rotationYaw = (float)Math.atan(n4 / 40.0f) * 40.0f;
        entityLivingBase.rotationPitch = -(float)Math.atan(n5 / 40.0f) * 20.0f;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.setRenderShadow(true);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86.0, 16.0, 4210752);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        this.oldMouseX = (float)n;
        this.oldMouseY = (float)n2;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiInventory.inventoryBackground);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(guiLeft + 51, guiTop + 75, 30, guiLeft + 51 - this.oldMouseX, guiTop + 75 - 50 - this.oldMouseY, this.mc.thePlayer);
    }
    
    public GuiInventory(final EntityPlayer entityPlayer) {
        super(entityPlayer.inventoryContainer);
        this.allowUserInput = true;
    }
}
