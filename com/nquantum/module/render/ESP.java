package com.nquantum.module.render;

import com.nquantum.module.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.event.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.nquantum.event.*;

public class ESP extends Module
{
    public ESP() {
        super("ESP", 0, Category.RENDER);
    }
    
    public void drawRacisz(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        GlStateManager.translate(n, n2, n3);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        Gui.drawRect(-6.230000019073486, -21.0, -6.010000228881836, 2.1500000953674316, 0);
        Gui.drawRect(6.230000019073486, -21.0, 6.010000228881836, 2.1500000953674316, 0);
        Gui.drawRect(-6.230000019073486, 2.0999999046325684, 6.010000228881836, 2.009999990463257, 0);
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, -1.0, 0.0);
        Gui.drawRect(-6.230000019073486, -20.010000228881836, 6.010000228881836, -20.110000610351562, 0);
        GL11.glPopMatrix();
        Gui.drawRect(-6.199999809265137, -21.0, -6.010000228881836, 2.1500000953674316, -1);
        Gui.drawRect(6.199999809265137, -21.0, 6.010000228881836, 2.1500000953674316, -1);
        Gui.drawRect(-6.199999809265137, 2.0999999046325684, 6.010000228881836, 2.009999990463257, -1);
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, -1.0, 0.0);
        Gui.drawRect(-6.199999809265137, -20.010000228881836, 6.010000228881836, -20.110000610351562, -1);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(-2.4, 0.0, 0.0);
        Gui.drawRect(9.359999656677246, -entityLivingBase.getHealth(), 9.109999656677246, 2.0999999046325684, new Color(0, 0, 0, 255).getRGB());
        Gui.drawRect(9.32, -entityLivingBase.getHealth(), 9.149999618530273, 2.0, new Color(162, 99, 231, 255).getRGB());
        GL11.glPopMatrix();
        GlStateManager.scale(0.1, 0.1, 0.1);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
    }
    
    public void drawNigga(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        final Vec3 vec3 = new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
        final double n4 = vec3.xCoord - this.mc.getRenderManager().renderPosX;
        final double n5 = vec3.yCoord - this.mc.getRenderManager().renderPosY;
        final double n6 = vec3.zCoord - this.mc.getRenderManager().renderPosZ;
        final double n7 = 0.3;
        final double n8 = this.mc.thePlayer.getEyeHeight();
        GL11.glLoadIdentity();
        this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 2);
        GL11.glColor4f(200.0f, 20.0f, 20.0f, 255.0f);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(3);
        GL11.glVertex3d(n4 - n7, n5, n6 - n7);
        GL11.glVertex3d(n4 - n7, n5, n6 - n7);
        GL11.glVertex3d(n4 - n7, n5 + n8, n6 - n7);
        GL11.glVertex3d(n4 + n7, n5 + n8, n6 - n7);
        GL11.glVertex3d(n4 + n7, n5, n6 - n7);
        GL11.glVertex3d(n4 - n7, n5, n6 - n7);
        GL11.glVertex3d(n4 - n7, n5, n6 + n7);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(n4 + n7, n5, n6 + n7);
        GL11.glVertex3d(n4 + n7, n5 + n8, n6 + n7);
        GL11.glVertex3d(n4 - n7, n5 + n8, n6 + n7);
        GL11.glVertex3d(n4 - n7, n5, n6 + n7);
        GL11.glVertex3d(n4 + n7, n5, n6 + n7);
        GL11.glVertex3d(n4 + n7, n5, n6 - n7);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(n4 + n7, n5 + n8, n6 + n7);
        GL11.glVertex3d(n4 + n7, n5 + n8, n6 - n7);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(n4 - n7, n5 + n8, n6 + n7);
        GL11.glVertex3d(n4 - n7, n5 + n8, n6 - n7);
        GL11.glEnd();
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("CSGO");
        list.add("CSGO2");
        list.add("Box");
        Asyncware.instance.settingsManager.rSetting(new Setting("ESP Mode", this, "CSGO", list));
        super.setup();
    }
    
    @Punjabi
    @Override
    public void on3D(final Event3D event3D) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("ESP Mode").getValString();
        this.setDisplayName("ESP §7" + valString);
        for (final EntityPlayerSP next : this.mc.theWorld.loadedEntityList) {
            if (next instanceof EntityPlayer) {
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                if ((next != thePlayer || this.mc.gameSettings.thirdPersonView == 0) && (next == thePlayer || thePlayer.isSpectator())) {
                    continue;
                }
                final EntityPlayerSP entityPlayerSP = next;
                final double n = entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * event3D.partialTicks;
                final double n2 = entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * event3D.partialTicks;
                final double n3 = entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * event3D.partialTicks;
                final String s = valString;
                switch (s.hashCode()) {
                    case 2078040: {
                        if (s.equals("CSGO")) {
                            break;
                        }
                        break;
                    }
                    case 64419290: {
                        if (s.equals("CSGO2")) {
                            break;
                        }
                        break;
                    }
                    case 66987: {
                        if (s.equals("Box")) {}
                        break;
                    }
                }
                switch (3) {
                    default: {
                        this.drawRat(entityPlayerSP, n - this.mc.getRenderManager().renderPosX, n2 - this.mc.getRenderManager().renderPosY, n3 - this.mc.getRenderManager().renderPosZ);
                        continue;
                    }
                    case 2:
                    case 3: {
                        continue;
                    }
                }
            }
        }
    }
    
    public void drawRat(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        GlStateManager.translate(n, n2 + 0.10000000149011612, n3);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        Gui.drawRect(2.0, 2.0, 2.0, 2.0, 1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glTranslatef(1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 0.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 0.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.translate(n, n2 - 0.004999999888241291, n3 - 0.004999999888241291);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glColor4f(0.003921569f, 0.007843138f, 0.003921569f, 1.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.translate(n, n2 + 0.10000000149011612, n3);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        Gui.drawRect(2.0, 2.0, 2.0, 2.0, 1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glTranslatef(1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 0.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 0.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.translate(n, n2 + 0.004999999888241291, n3 + 0.004999999888241291);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glColor4f(0.003921569f, 0.007843138f, 0.003921569f, 1.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.translate(n, n2, n3);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        Gui.drawRect(2.0, 2.0, 2.0, 2.0, 1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glTranslatef(1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 1.0f);
        GL11.glColor4f(0.63529414f, 0.3882353f, 0.90588236f, 1.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.translate(n, n2, n3);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        GL11.glColor4f(0.7921569f, 0.7921569f, 0.7921569f, 1.0f);
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(-5.75f, -20.0f);
        GL11.glVertex2f(-5.75f, 2.15f);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glVertex2f(5.75f, -20.0f);
        GL11.glVertex2f(5.75f, 2.15f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
    }
}
