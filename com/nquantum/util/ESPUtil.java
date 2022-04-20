package com.nquantum.util;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.*;

public class ESPUtil
{
    public static Minecraft mc;
    
    public static void glColor(final int n) {
        GlStateManager.color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void blockESPBox(final BlockPos blockPos) {
        final double n = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        final double n2 = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        final double n3 = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glColor4d(86.0, 66.0, 243.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(1.0, 1.0, 1.0, 0.5);
        RenderGlobal.func_181561_a(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 0.9));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    static {
        ESPUtil.mc = Minecraft.getMinecraft();
    }
    
    public static void drawCircleOnEntityFade(final EntityLivingBase entityLivingBase, final float n) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2896);
        final double n2 = interpolate(entityLivingBase.posX, entityLivingBase.lastTickPosX, n) - ESPUtil.mc.getRenderManager().renderPosX;
        final double n3 = interpolate(entityLivingBase.posY, entityLivingBase.lastTickPosY, n) - ESPUtil.mc.getRenderManager().renderPosY;
        final double n4 = interpolate(entityLivingBase.posZ, entityLivingBase.lastTickPosZ, n) - ESPUtil.mc.getRenderManager().renderPosZ;
        GL11.glLineWidth(1.0f);
        final ArrayList<Vec3> list = new ArrayList<Vec3>();
        final float n5 = entityLivingBase.getEyeHeight() / 1.5f + MathHelper.sin((entityLivingBase.ticksExisted + n) / 15.0f) * 0.8f + 0.1f;
        for (float n6 = 0.0f; n6 < 6.346017160251382; n6 += (float)0.06283185307179587) {
            list.add(new Vec3(entityLivingBase.width * Math.cos(n6) + n2, n3 + n5, entityLivingBase.width * Math.sin(n6) + n4));
        }
        GL11.glEnable(2852);
        GL11.glLineStipple(4, (short)(-1));
        GL11.glBegin(3);
        for (final Vec3 vec3 : list) {
            final int n7 = (int)(0 + 6.283185307179586);
            glColor(-1);
            GL11.glVertex3d(vec3.xCoord, vec3.yCoord, vec3.zCoord);
        }
        GL11.glEnd();
        GL11.glDisable(2852);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }
    
    public static double interpolate(final double n, final double n2, final double n3) {
        return n2 + (n - n2) * n3;
    }
    
    public static int getRainbowText(final int n, final int n2, final float n3) {
        return Color.HSBtoRGB((System.currentTimeMillis() - (n * 10L - n2 * 10L)) % 6000L / 6000.0f, n3, 1.0f);
    }
    
    public static void drawTracerLine(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0, 0.0 + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(n, n2, n3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
