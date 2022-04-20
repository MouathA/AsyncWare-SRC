package com.nquantum.module.player;

import com.nquantum.module.*;
import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import net.minecraft.client.entity.*;

public class AntiKB extends Module
{
    public AntiKB() {
        super("AntiKnockback", 0, Category.PLAYER);
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Cancel");
        list.add("AAC");
        Asyncware.instance.settingsManager.rSetting(new Setting("AntiKB Mode", this, "Cancel", list));
    }
    
    @Punjabi
    public void onPacket(final EventSendPacket eventSendPacket) {
        if (Asyncware.instance.settingsManager.getSettingByName("AntiKB Mode").getValString().equalsIgnoreCase("Cancel") && (eventSendPacket.getPacket() instanceof C00PacketKeepAlive || eventSendPacket.getPacket() instanceof S00PacketKeepAlive || eventSendPacket.getPacket() instanceof S12PacketEntityVelocity)) {
            System.out.println(eventSendPacket.getPacket());
        }
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("AntiKB Mode").getValString();
        this.setDisplayName("Anti Knockback §7" + valString);
        if (valString.equalsIgnoreCase("AAC")) {
            final double n = 0.01;
            final double n2 = 0.01;
            if (this.mc.thePlayer.hurtTime > 0 && this.mc.thePlayer.fallDistance < 3.0f) {
                if (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionY -= n;
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionX *= n2;
                    final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                    thePlayer3.motionZ *= n2;
                    final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                    thePlayer4.motionY += n;
                }
                else {
                    final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                    thePlayer5.motionY -= n;
                    final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                    thePlayer6.motionX *= n2 + 0.2;
                    final EntityPlayerSP thePlayer7 = this.mc.thePlayer;
                    thePlayer7.motionZ *= n2 + 0.2;
                    final EntityPlayerSP thePlayer8 = this.mc.thePlayer;
                    thePlayer8.motionY += n;
                }
            }
        }
    }
}
