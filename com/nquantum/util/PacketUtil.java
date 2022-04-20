package com.nquantum.util;

import net.minecraft.client.*;
import net.minecraft.network.*;

public class PacketUtil
{
    public static Minecraft mc;
    
    static {
        PacketUtil.mc = Minecraft.getMinecraft();
    }
    
    public static void sendPacketPlayerNoEvent(final Packet packet) {
        PacketUtil.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(packet);
    }
    
    public static void sendPacket(final Packet packet) {
        PacketUtil.mc.getNetHandler().addToSendQueue(packet);
    }
    
    public static void sendPacketNoEvent(final Packet packet) {
        PacketUtil.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static void sendPacketPlayer(final Packet packet) {
        PacketUtil.mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
