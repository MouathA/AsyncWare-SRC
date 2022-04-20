package com.nquantum.module.render;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.nquantum.event.impl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class Trajectories extends Module
{
    public static Minecraft mc;
    
    public ArrayList getEntities() {
        final ArrayList<EntityPlayerSP> list = new ArrayList<EntityPlayerSP>();
        for (final EntityPlayerSP next : Trajectories.mc.theWorld.loadedEntityList) {
            if (next != Trajectories.mc.thePlayer && next instanceof EntityLivingBase) {
                list.add(next);
            }
        }
        return list;
    }
    
    @Punjabi
    public void onRender(final Event3D event3D) {
        if (this.isToggled()) {
            if (Trajectories.mc.thePlayer.getHeldItem() == null) {
                return;
            }
            if ((!(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) || !Trajectories.mc.thePlayer.isUsingItem()) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg) && (!(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) || !ItemPotion.isSplash(Trajectories.mc.thePlayer.getHeldItem().getItemDamage()))) {
                return;
            }
            final boolean b = Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow;
            final boolean b2 = Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion;
            final float rotationYaw = Trajectories.mc.thePlayer.rotationYaw;
            final float rotationPitch = Trajectories.mc.thePlayer.rotationPitch;
            Trajectories.mc.getRenderManager();
            final double n = Minecraft.getMinecraft().getRenderManager().renderPosX - MathHelper.cos(rotationYaw / 180.0f * 3.141593f) * 0.16f;
            Trajectories.mc.getRenderManager();
            final double n2 = Minecraft.getMinecraft().getRenderManager().renderPosY + Trajectories.mc.thePlayer.getEyeHeight() - 0.1000000014901161;
            Trajectories.mc.getRenderManager();
            final double n3 = Minecraft.getMinecraft().getRenderManager().renderPosZ - MathHelper.sin(rotationYaw / 180.0f * 3.141593f) * 0.16f;
            final double n4 = -MathHelper.sin(rotationYaw / 180.0f * 3.141593f) * MathHelper.cos(rotationPitch / 180.0f * 3.141593f) * 0.4;
            final double n5 = -MathHelper.sin((rotationPitch - 0) / 180.0f * 3.141593f) * 0.4;
            final double n6 = MathHelper.cos(rotationYaw / 180.0f * 3.141593f) * MathHelper.cos(rotationPitch / 180.0f * 3.141593f) * 0.4;
            final float n7 = (72000 - Trajectories.mc.thePlayer.getItemInUseCount()) / 20.0f;
            final float n8 = (n7 * n7 + n7 * 2.0f) / 3.0f;
            if (n8 < 0.1) {
                return;
            }
            if (n8 > 1.0f) {}
            final float sqrt_double = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
            final double n9 = n4 / sqrt_double;
            final double n10 = n5 / sqrt_double;
            final double n11 = n6 / sqrt_double;
            final double n12 = n9 * (1.0f * 1.5);
            final double n13 = n10 * (1.0f * 1.5);
            final double n14 = n11 * (1.0f * 1.5);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0f, 0.0f);
            GL11.glDisable(2896);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            final MovingObjectPosition movingObjectPosition = null;
            GL11.glEnd();
            GL11.glPushMatrix();
            GL11.glTranslated(n - Minecraft.getMinecraft().getRenderManager().renderPosX, n2 - Minecraft.getMinecraft().getRenderManager().renderPosY, n3 - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int index = movingObjectPosition.sideHit.getIndex();
                if (index == 1) {
                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (index == 2) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (index == 3) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (index == 4) {
                    GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                else if (index == 5) {
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(0.05f, 0.05f, 0.05f);
                Gui.drawRect(-8.25, -8.25, 8.25, 8.25, -1711276033);
            }
            GL11.glPopMatrix();
            if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().renderPosX, -Minecraft.getMinecraft().getRenderManager().renderPosY, -Minecraft.getMinecraft().getRenderManager().renderPosZ);
                GL11.glTranslated(Minecraft.getMinecraft().getRenderManager().renderPosX, Minecraft.getMinecraft().getRenderManager().renderPosY, Minecraft.getMinecraft().getRenderManager().renderPosZ);
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        }
    }
    
    static {
        Trajectories.mc = Minecraft.getMinecraft();
    }
    
    public Trajectories() {
        super("Trajectories", 0, Category.RENDER);
    }
    
    public Entity getEntityHit(final boolean b, final Vec3 vec3, final Vec3 vec4) {
        for (final EntityLivingBase entityLivingBase : this.getEntities()) {
            if (entityLivingBase != Trajectories.mc.thePlayer) {
                if (entityLivingBase.getEntityBoundingBox().expand(0.20000000298023224, 0.20000000298023224, 0.20000000298023224).calculateIntercept(vec3, vec4) != null) {
                    return entityLivingBase;
                }
                continue;
            }
        }
        return null;
    }
}
