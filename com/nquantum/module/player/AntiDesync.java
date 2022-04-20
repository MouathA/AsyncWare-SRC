package com.nquantum.module.player;

import com.nquantum.event.impl.*;
import net.minecraft.network.play.server.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class AntiDesync extends Module
{
    @Punjabi
    public void onPacket(final EventReceivePacket eventReceivePacket) {
        this.setDisplayName("Anti Desync");
        if (eventReceivePacket.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook)eventReceivePacket.getPacket();
            s08PacketPlayerPosLook.setYaw(this.mc.thePlayer.rotationYaw);
            s08PacketPlayerPosLook.setPitch(this.mc.thePlayer.rotationPitch);
        }
    }
    
    public AntiDesync() {
        super("AntiDesync", 0, Category.PLAYER);
    }
}
