package com.nquantum.module.combat;

import com.nquantum.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.event.impl.*;
import org.lwjgl.opengl.*;

public class Criticals extends Module
{
    @Punjabi
    public void onSendPacket(final EventSendPacket eventSendPacket) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Criticals Mode").getValString();
        eventSendPacket.getPacket() instanceof C03PacketPlayer;
        if (this.mc.gameSettings.keyBindSneak.isPressed()) {
            System.out.println("Sent packet: " + eventSendPacket.getPacket().toString());
        }
        if (this == 0) {
            if (eventSendPacket.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)eventSendPacket.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && valString.equalsIgnoreCase("Packet")) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1625, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0E-6, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-6, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
            if (valString.equalsIgnoreCase("MiniJump")) {
                this.mc.thePlayer.jump();
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionY -= 0.3000000119209288;
            }
        }
    }
    
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Packet");
        list.add("MiniJump");
        Asyncware.instance.settingsManager.rSetting(new Setting("Criticals Mode", this, "Packet", list));
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Criticals §7" + Asyncware.instance.settingsManager.getSettingByName("Criticals Mode").getValString() + " 10");
        Display.setTitle("Badlion Client 1.8.9");
    }
}
