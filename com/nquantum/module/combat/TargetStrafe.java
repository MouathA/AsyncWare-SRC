package com.nquantum.module.combat;

import com.nquantum.*;
import net.minecraft.client.*;
import com.nquantum.util.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import net.minecraft.util.*;

public class TargetStrafe extends Module
{
    boolean decreasing;
    public double yLevel;
    
    public static void strafe(final Entity entity, final float[] array, final double n, final double n2) {
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Strafe Speed").getValDouble();
        if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= n2) {
            MovementUtil.setSpeed(valDouble, array[0], n, 0.0);
        }
        else {
            MovementUtil.setSpeed(valDouble, array[0], n, 1.0);
        }
    }
    
    public TargetStrafe() {
        super("TargetStrafe", 0, Category.COMBAT);
    }
    
    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(new Setting("Rainbow Circle", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Space", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Strafe Radius", this, 2.5, 0.1, 6.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Strafe Speed", this, 2.5, 0.01, 3.0, false));
    }
    
    public void drawCircleAroundEntity(final Entity entity, final float n, final double n2) {
        final Color hsbColor = Color.getHSBColor(System.currentTimeMillis() % 2000L / 2000.0f, 1.0f, 1.0f);
        final float n3 = hsbColor.getRed() / 255.0f;
        final float n4 = hsbColor.getGreen() / 255.0f;
        final float n5 = hsbColor.getBlue() / 255.0f;
        final float n6 = 90.0f;
        final double n7 = 0.0;
        if (n7 >= 0.02) {
            return;
        }
        int n8 = 0;
        ++n8;
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDisable(2929);
        GL11.glLineWidth(1.3f);
        GL11.glBegin(3);
        final double n9 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n - this.mc.getRenderManager().viewerPosX;
        final double n10 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n - this.mc.getRenderManager().viewerPosY;
        final double n11 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n - this.mc.getRenderManager().viewerPosZ;
        float n12;
        float n13;
        for (n12 = 5000.0f, n13 = (float)(System.currentTimeMillis() % (int)n12); n13 > n12; n13 -= n12) {}
        final float n14 = n13 / n12;
        while (true) {
            for (float n15 = (0 + (float)(n7 * 8.0)) / n6 + n14; n15 > 1.0f; --n15) {}
            GlStateManager.color(n3, n4, n5);
            GL11.glVertex3d(n9 + n2 * Math.cos(0 * 6.283185307179586 / n6), n10 + n7, n11 + n2 * Math.sin(0 * 6.283185307179586 / n6));
            int n16 = 0;
            ++n16;
        }
    }
    
    @Punjabi
    @Override
    public void on3D(final Event3D event3D) {
        final KillAura killAura = new KillAura();
        Asyncware.instance.settingsManager.getSettingByName("Strafe Radius").getValDouble();
        final EntityLivingBase target = killAura.target;
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Target Strafe " + EnumChatFormatting.GRAY + Math.round(Asyncware.instance.settingsManager.getSettingByName("Strafe Radius").getValDouble()));
    }
}
