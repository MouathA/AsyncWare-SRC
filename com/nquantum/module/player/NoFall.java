package com.nquantum.module.player;

import com.nquantum.util.time.*;
import com.nquantum.module.*;
import com.nquantum.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import com.nquantum.util.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class NoFall extends Module
{
    double fall;
    TimerHelper timer;
    
    public NoFall() {
        super("NoFall", 0, Category.PLAYER);
        this.timer = new TimerHelper();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("No Fall Mode").getValString();
        this.setDisplayName("No Fall " + ChatFormatting.GRAY + valString);
        if (valString.equalsIgnoreCase("Spoof") && this.mc.thePlayer.fallDistance > 3.0f) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
        if (valString.equalsIgnoreCase("AAC") && this.mc.thePlayer.ticksExisted == 1) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, true));
        }
        if (valString.equalsIgnoreCase("Packet Spam")) {
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
        }
    }
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        if (Asyncware.instance.settingsManager.getSettingByName("No Fall Mode").getValString().equalsIgnoreCase("Watchdog")) {
            if (!MovementUtil.isOnGround(0.001)) {
                if (this.mc.thePlayer.motionY < -0.08) {
                    this.fall -= this.mc.thePlayer.motionY;
                }
                if (this.fall > 2.0) {
                    this.fall = 0.0;
                    eventPostMotionUpdate.setGround(true);
                }
            }
            else {
                this.fall = 0.0;
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final String valString = Asyncware.instance.settingsManager.getSettingByName("No Fall Mode").getValString();
        if (valString.equalsIgnoreCase("AAC") && this.mc.theWorld != null && valString.equalsIgnoreCase("AAC")) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, true));
        }
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Spoof");
        list.add("AAC");
        list.add("Watchdog");
        list.add("Packet Spam");
        Asyncware.instance.settingsManager.rSetting(new Setting("No Fall Mode", this, "Spoof", list));
    }
}
