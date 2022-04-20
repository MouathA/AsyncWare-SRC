package com.nquantum.util.player;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;

public class BypassUtil
{
    static Minecraft mc;
    
    public static float range(final float n, final float n2) {
        return n + new Random().nextFloat() * (n2 - n);
    }
    
    public static boolean isBlockBelowSlippery() {
        return BypassUtil.mc.theWorld.getBlockState(new BlockPos(BypassUtil.mc.thePlayer.posX, BypassUtil.mc.thePlayer.posY - 1.0, BypassUtil.mc.thePlayer.posZ)).getBlock().slipperiness == 0.98f;
    }
    
    static {
        BypassUtil.mc = Minecraft.getMinecraft();
    }
    
    public static float getMaxFallDist() {
        final PotionEffect activePotionEffect = BypassUtil.mc.thePlayer.getActivePotionEffect(Potion.jump);
        return (float)(BypassUtil.mc.thePlayer.getMaxFallHeight() + ((activePotionEffect != null) ? (activePotionEffect.getAmplifier() + 1) : 0));
    }
    
    public static void damage() {
        final double n = 0.0;
        final double n2 = 0.0;
        final double n3 = 0.0;
        while (0 < getMaxFallDist() / (n - 0.004999999888241291) + 1.0) {
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(BypassUtil.mc.thePlayer.posX, BypassUtil.mc.thePlayer.posY + n, BypassUtil.mc.thePlayer.posZ, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(BypassUtil.mc.thePlayer.posX, BypassUtil.mc.thePlayer.posY + n2, BypassUtil.mc.thePlayer.posZ, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(BypassUtil.mc.thePlayer.posX, BypassUtil.mc.thePlayer.posY + n3 + n * 1.0E-6, BypassUtil.mc.thePlayer.posZ, false));
            int n4 = 0;
            ++n4;
        }
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer(true));
    }
    
    public static double range(final double n, final double n2) {
        return n + new Random().nextDouble() * (n2 - n);
    }
    
    public static int range(final int n, final int n2) {
        return n + new Random().nextInt() * (n2 - n);
    }
}
