package com.nquantum.util.player;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;

public class PlayerUtil
{
    private static Minecraft mc;
    
    public static synchronized boolean faceEntity(final EntityPlayer entityPlayer, final boolean b) {
        final float[] rotationsNeeded = getRotationsNeeded(entityPlayer);
        if (rotationsNeeded != null) {
            PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotationsNeeded[0], rotationsNeeded[1], true));
            if (b) {
                PlayerUtil.mc.thePlayer.rotationYaw = rotationsNeeded[0];
                PlayerUtil.mc.thePlayer.rotationPitch = rotationsNeeded[1];
            }
        }
        return false;
    }
    
    public static void sendMessage(final String s) {
        sendMessage(s, true);
    }
    
    public static EntityPlayer getClosest(final double n) {
        double n2 = n;
        EntityPlayer entityPlayer = null;
        for (final EntityPlayer entityPlayer2 : PlayerUtil.mc.theWorld.playerEntities) {
            if (entityPlayer2 != PlayerUtil.mc.thePlayer && entityPlayer2.getDistanceToEntity(PlayerUtil.mc.thePlayer) < n2) {
                if (entityPlayer2.isOnSameTeam(PlayerUtil.mc.thePlayer)) {
                    continue;
                }
                n2 = entityPlayer2.getDistanceToEntity(PlayerUtil.mc.thePlayer);
                entityPlayer = entityPlayer2;
            }
        }
        return entityPlayer;
    }
    
    public static float rotationNormalToMc(float n) {
        if (n < 0.0f) {
            n += 360.0f;
        }
        else if (n >= 360.0f) {
            n -= 360.0f;
        }
        final float n2 = (n >= 180.0f) ? (n - 360.0f) : n;
        if (n2 < -180.0f) {
            return n2 + 360.0f;
        }
        if (n2 >= 180.0f) {
            return n2 - 360.0f;
        }
        return n2;
    }
    
    public static float rotationMcToNormal(float n) {
        if (n < -180.0f) {
            n += 360.0f;
        }
        else if (n >= 180.0f) {
            n -= 360.0f;
        }
        final float n2 = (n < 0.0f) ? (n + 360.0f) : n;
        if (n2 < 0.0f) {
            return n2 + 360.0f;
        }
        if (n2 >= 360.0f) {
            return n2 - 360.0f;
        }
        return n2;
    }
    
    public void attack(final EntityPlayer entityPlayer) {
        PlayerUtil.mc.thePlayer.swingItem();
        PlayerUtil.mc.playerController.attackEntity(PlayerUtil.mc.thePlayer, entityPlayer);
    }
    
    public static HashSet getClosests(final double n, final int n2) {
        final HashSet<EntityPlayerSP> set = new HashSet<EntityPlayerSP>();
        for (final EntityPlayer entityPlayer : PlayerUtil.mc.theWorld.playerEntities) {
            if (0 < n2 && entityPlayer != PlayerUtil.mc.thePlayer && entityPlayer.getDistanceToEntity(PlayerUtil.mc.thePlayer) < n) {
                if (entityPlayer.isOnSameTeam(PlayerUtil.mc.thePlayer)) {
                    continue;
                }
                set.add((EntityPlayerSP)entityPlayer);
                int n3 = 0;
                ++n3;
            }
        }
        return set;
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("Decimals must be almost 0");
        }
        return Math.round(Math.pow(10.0, n2) * n) / Math.pow(10.0, n2);
    }
    
    public static void sendMessage(final String s, final boolean b) {
        PlayerUtil.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(s));
    }
    
    public static float[] getRotationsNeeded(final EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return null;
        }
        final double n = entityPlayer.posX - PlayerUtil.mc.thePlayer.posX;
        final double n2 = entityPlayer.posZ - PlayerUtil.mc.thePlayer.posZ;
        return new float[] { PlayerUtil.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f - PlayerUtil.mc.thePlayer.rotationYaw), PlayerUtil.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(entityPlayer.posY + entityPlayer.getEyeHeight() - (PlayerUtil.mc.thePlayer.posY + PlayerUtil.mc.thePlayer.getEyeHeight()), MathHelper.sqrt_double(n * n + n2 * n2)) * 180.0 / 3.141592653589793)) - PlayerUtil.mc.thePlayer.rotationPitch) };
    }
    
    public static HashSet getClosests(final double n) {
        final HashSet<EntityPlayerSP> set = new HashSet<EntityPlayerSP>();
        for (final EntityPlayer entityPlayer : PlayerUtil.mc.theWorld.playerEntities) {
            if (entityPlayer != PlayerUtil.mc.thePlayer && entityPlayer.getDistanceToEntity(PlayerUtil.mc.thePlayer) < n) {
                if (entityPlayer.isOnSameTeam(PlayerUtil.mc.thePlayer)) {
                    continue;
                }
                set.add((EntityPlayerSP)entityPlayer);
            }
        }
        return set;
    }
    
    static {
        PlayerUtil.mc = Minecraft.getMinecraft();
    }
}
