package com.nquantum.module.render;

import com.nquantum.module.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import net.minecraft.network.play.server.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;

public class WorldTime extends Module
{
    public WorldTime() {
        super("World Time", 0, Category.RENDER);
        Asyncware.instance.settingsManager.rSetting(new Setting("World Time", this, 10000.0, 0.0, 24000.0, true));
    }
    
    @Punjabi
    private void onPacket(final EventReceivePacket eventReceivePacket) {
        if (eventReceivePacket.getPacket() instanceof S03PacketTimeUpdate) {
            eventReceivePacket.setCancelled(true);
        }
    }
    
    @Punjabi
    public void onEvent(final EventUpdate eventUpdate) {
        this.mc.theWorld.setWorldTime((long)Asyncware.instance.settingsManager.getSettingByName("World Time").getValDouble());
    }
}
