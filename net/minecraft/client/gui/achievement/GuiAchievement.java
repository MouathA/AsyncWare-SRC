package net.minecraft.client.gui.achievement;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiAchievement extends Gui
{
    private int height;
    private Minecraft mc;
    private boolean permanentNotification;
    private RenderItem renderItem;
    private int width;
    private Achievement theAchievement;
    private static final ResourceLocation achievementBg;
    private String achievementDescription;
    private long notificationTime;
    private String achievementTitle;
    
    static {
        achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }
    
    public void displayUnformattedAchievement(final Achievement theAchievement) {
        this.achievementTitle = theAchievement.getStatName().getUnformattedText();
        this.achievementDescription = theAchievement.getDescription();
        this.notificationTime = Minecraft.getSystemTime() + 2500L;
        this.theAchievement = theAchievement;
        this.permanentNotification = true;
    }
    
    private void updateAchievementWindowScale() {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.matrixMode(5888);
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.width = scaledResolution.getScaledWidth();
        this.height = scaledResolution.getScaledHeight();
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, this.width, this.height, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    public GuiAchievement(final Minecraft mc) {
        this.mc = mc;
        this.renderItem = mc.getRenderItem();
    }
    
    public void displayAchievement(final Achievement theAchievement) {
        this.achievementTitle = I18n.format("achievement.get", new Object[0]);
        this.achievementDescription = theAchievement.getStatName().getUnformattedText();
        this.notificationTime = Minecraft.getSystemTime();
        this.theAchievement = theAchievement;
        this.permanentNotification = false;
    }
    
    public void clearAchievements() {
        this.theAchievement = null;
        this.notificationTime = 0L;
    }
    
    public void updateAchievementWindow() {
        if (this.theAchievement != null && this.notificationTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
            double n = (Minecraft.getSystemTime() - this.notificationTime) / 3000.0;
            if (!this.permanentNotification) {
                if (n < 0.0 || n > 1.0) {
                    this.notificationTime = 0L;
                    return;
                }
            }
            else if (n > 0.5) {
                n = 0.5;
            }
            this.updateAchievementWindowScale();
            GlStateManager.depthMask(false);
            double n2 = n * 2.0;
            if (n2 > 1.0) {
                n2 = 2.0 - n2;
            }
            double n3 = 1.0 - n2 * 4.0;
            if (n3 < 0.0) {
                n3 = 0.0;
            }
            final double n4 = n3 * n3;
            final double n5 = n4 * n4;
            final int n6 = this.width - 160;
            final int n7 = 0 - (int)(n5 * 36.0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiAchievement.achievementBg);
            this.drawTexturedModalRect(n6, n7, 96, 202, 160, 32);
            if (this.permanentNotification) {
                this.mc.fontRendererObj.drawSplitString(this.achievementDescription, n6 + 30, n7 + 7, 120, -1);
            }
            else {
                this.mc.fontRendererObj.drawString(this.achievementTitle, n6 + 30, n7 + 7, -256);
                this.mc.fontRendererObj.drawString(this.achievementDescription, n6 + 30, n7 + 18, -1);
            }
            this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, n6 + 8, n7 + 8);
            GlStateManager.depthMask(true);
        }
    }
}
