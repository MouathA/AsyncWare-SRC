package com.nquantum.module.render;

import com.nquantum.event.impl.*;
import com.nquantum.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.entity.*;
import com.nquantum.module.customize.*;
import net.minecraft.item.*;
import net.minecraft.client.*;

public class NameTags extends Module
{
    public boolean armor;
    
    @Punjabi
    private void onNigger(final Event3D event3D) {
        this.setDisplayName("Name Tags");
        if (Asyncware.instance.moduleManager.getModuleByName("ESP2D").isToggled()) {
            return;
        }
        for (final EntityPlayerSP next : this.mc.theWorld.loadedEntityList) {
            if (next instanceof EntityPlayer) {
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                if (next == thePlayer || thePlayer.isInvisible()) {
                    continue;
                }
                this.racisz(next, next.lastTickPosX + (next.posX - next.lastTickPosX) * event3D.partialTicks - this.mc.getRenderManager().renderPosX, next.lastTickPosY + (next.posY - next.lastTickPosY) * event3D.partialTicks - this.mc.getRenderManager().renderPosY, next.lastTickPosZ + (next.posZ - next.lastTickPosZ) * event3D.partialTicks - this.mc.getRenderManager().renderPosZ);
            }
        }
    }
    
    public NameTags() {
        super("NameTags", 0, Category.RENDER);
        this.armor = true;
    }
    
    private void racisz(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        final float n4 = (float)(0.25 - this.mc.thePlayer.getDistance(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ) / 1000.0);
        GlStateManager.translate(n, n2 + 0.4, n3);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        this.mc.thePlayer.getCurrentArmor(1);
        GlStateManager.scale(-n4, -n4, 0.2f);
        GlStateManager.translate(0.0f, 12.0f, 0.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        final String name = entityLivingBase.getName();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        Gui.drawRect(-this.mc.fontRendererObj.getStringWidth(name) / 8, -23.399999618530273, this.mc.fontRendererObj.getStringWidth(name) / 8, -21.0, new Color(24, 24, 24, 149).getRGB());
        GlStateManager.scale(0.2, 0.2, 0.2);
        while (true) {
            if (entityLivingBase.getEquipmentInSlot(0) != null) {
                GL11.glPushMatrix();
                GL11.glScalef(0.6f, 0.6f, 0.6f);
                GL11.glPopMatrix();
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    private void rat(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        for (final EntityPlayer entityPlayer : this.mc.theWorld.playerEntities) {
            if (!entityPlayer.isInvisible()) {
                if (entityPlayer == this.mc.thePlayer) {
                    continue;
                }
                GL11.glPushMatrix();
                GL11.glTranslated(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosX, entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosY + entityPlayer.getEyeHeight() + 1.7, entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosZ);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                }
                else {
                    GlStateManager.rotate(-this.mc.thePlayer.rotationYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.mc.thePlayer.rotationPitch, 1.0f, 0.0f, 0.0f);
                }
                final float distanceToEntity = this.mc.thePlayer.getDistanceToEntity(entityPlayer);
                final float n4 = 0.02672f;
                final float n5 = 0.1f;
                final double n6 = 7.0;
                final float min = Math.min(0.1f, n4 * (float)((distanceToEntity <= n6) ? (n6 * n5) : (distanceToEntity * n5)));
                GL11.glScalef(-min, -min, 0.2f);
                GlStateManager.depthMask(false);
                GL11.glDisable(2929);
                final String s = (entityPlayer.getHealth() > 15.0f) ? "§a" : ((entityPlayer.getHealth() > 10.0f) ? "§e" : ((entityPlayer.getHealth() > 7.0f) ? "§6" : "§c"));
                final int n7 = (entityPlayer.getHealth() > 15.0f) ? -11667621 : ((entityPlayer.getHealth() > 10.0f) ? -919731 : ((entityPlayer.getHealth() > 7.0f) ? -555699 : -568755));
                final float n8 = (float)this.mc.fontRendererObj.getStringWidth(entityPlayer.getName() + " " + s + (int)entityPlayer.getHealth());
                Gui.drawRect(-n8 / 2.0f - 2.0f, 42.0, n8 / 2.0f + 2.0f, 40.0, new Color(0, 0, 0, 20).getRGB());
                Gui.drawRect(-n8 / 2.0f - 15.0f, 20.0, n8 / 2.0f + 15.0f, 40.0, new Color(0, 0, 0, 10).getRGB());
                this.mc.fontRendererObj.drawStringWithShadow(entityPlayer.getName(), -20.0f, 23.0f, HUD.hudColor);
                GlStateManager.depthMask(true);
                GL11.glEnable(2929);
                double n9 = 1.2;
                if (n8 <= 65.0f) {
                    n9 = 2.0;
                }
                if (n8 <= 85.0f) {
                    n9 = 1.2;
                }
                if (n8 <= 100.0f) {
                    n9 = 1.1;
                }
                if (this.armor) {
                    while (true) {
                        if (entityPlayer.getEquipmentInSlot(0) != null) {
                            renderItem(entityPlayer.getEquipmentInSlot(0), (int)(0 / n9) - 30, -10);
                        }
                        int n10 = 0;
                        ++n10;
                    }
                }
                else {
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    public static void renderItem(final ItemStack itemStack, final int n, final int n2) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + 8);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
}
