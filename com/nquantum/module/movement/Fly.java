package com.nquantum.module.movement;

import com.nquantum.util.time.*;
import com.nquantum.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.nquantum.event.*;
import com.nquantum.notification.*;
import com.nquantum.util.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;

public class Fly extends Module
{
    private Timer timer;
    private Timer bypassTimer;
    
    @Override
    public void onDisable() {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        super.onDisable();
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.capabilities.isFlying = false;
        if (valString.equalsIgnoreCase("Vanilla")) {
            this.mc.thePlayer.capabilities.isFlying = false;
        }
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Vanilla");
        list.add("Watchdog");
        list.add("Bow Fly");
        list.add("Verus");
        list.add("Verus Infinite");
        list.add("Redesky");
        list.add("AAC");
        list.add(ChatFormatting.YELLOW + "Bridger Land");
        list.add("AAC 5.0.14 Packet");
        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", list));
        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Speed", this, 2.0, 0.1, 10.0, false));
    }
    
    @Punjabi
    public void onNigger(final EventPreMotionUpdate eventPreMotionUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        if (valString.equalsIgnoreCase("VerusInfinite")) {
            eventPreMotionUpdate.setGround(true);
        }
        if (valString.equalsIgnoreCase("AAC 5.0.14 Packet")) {
            final MathUtil mathUtil = new MathUtil();
            if (this.timer.hasTimeElapsed(100L, true)) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY + MathUtil.Xorshift32((int)(System.nanoTime() / 1L)), this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - MathUtil.Xorshift32((int)(System.nanoTime() / 1L)), this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY + MathUtil.Xorshift32((int)(System.nanoTime() / 1L)), this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - MathUtil.Xorshift32((int)(System.nanoTime() / 1L)), this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
            }
            System.out.println(MathUtil.Xorshift32((int)(System.nanoTime() / 100L)));
        }
        if (valString.equalsIgnoreCase("Bow Fly")) {
            this.mc.thePlayer.motionY = 0.0;
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
            if (this.mc.thePlayer.hurtTime > 0) {
                eventPreMotionUpdate.setPitch(0.0f);
                this.mc.thePlayer.motionY = 0.001;
                MovementUtil.setMotion(9.0);
            }
            else {
                eventPreMotionUpdate.setPitch(-90.0f);
            }
        }
    }
    
    @Override
    public void onEnable() {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        this.timer = new Timer();
        if (valString.equalsIgnoreCase("Verus") || valString.equalsIgnoreCase("AAC 5.0.14 Packet")) {}
        if (valString.equalsIgnoreCase("Watchdog")) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.45, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
            this.mc.thePlayer.jump();
        }
        if (valString.equalsIgnoreCase("Verus Infinite")) {
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.5, this.mc.thePlayer.posZ);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.3, this.mc.thePlayer.posZ);
            NotificationManager.show(new Notification(NotificationType.WARNING, "Verus Fly", "Please wait!", 3));
            NotificationManager.show(new Notification(NotificationType.INFO, "Verus Fly", "You can fly infinite now!", 4));
            this.mc.thePlayer.motionY = 0.42;
        }
        if (valString.equalsIgnoreCase(ChatFormatting.YELLOW + "Bridger Land")) {
            PacketUtil.sendPacketPlayer(new C03PacketPlayer());
        }
        super.onEnable();
    }
    
    public Fly() {
        super("Fly", 33, Category.MOVEMENT);
        this.bypassTimer = new Timer();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();
        this.setDisplayName("Flight §7" + valString);
        if (valString.equalsIgnoreCase("Watchdog")) {
            MovementUtil.setMotion(8.0);
            if (this.timer.hasTimeElapsed(250L, true)) {
                this.mc.timer.timerSpeed = 1.5f;
                this.mc.thePlayer.motionY = -0.5;
            }
            else {
                this.mc.timer.timerSpeed = 0.1f;
            }
        }
        if (valString.equalsIgnoreCase("Vanilla")) {
            this.mc.thePlayer.motionY = 1.0E-4;
            MovementUtil.setMotion(valDouble);
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionY = 2.0;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.motionY = -Math.abs(2);
            }
        }
        if (valString.equalsIgnoreCase("AAC 5.0.14 Packet")) {
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = 0.0;
            MovementUtil.setMotion(4.0);
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionY = 2.0;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.motionY = -2.0;
            }
            if (this.timer.hasTimeElapsed(3000L, true)) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 5997.7235, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 5997.7235, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
            }
        }
        if (valString.equalsIgnoreCase("AAC")) {
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.onGround = true;
            if (MovementUtil.isMoving()) {
                MovementUtil.strafe(1.72f);
            }
            this.mc.thePlayer.motionY = 0.5;
            MovementUtil.setMotion(3.0);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.10000000149011612, this.mc.thePlayer.posZ);
            if (this.timer.delay(1200.0f)) {
                this.mc.thePlayer.setSpeed(0.2f);
            }
        }
        if (valString.equalsIgnoreCase("Redesky") && this.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.mc.thePlayer.motionY = 0.42;
            this.mc.thePlayer.speedInAir = 0.19f;
            this.mc.thePlayer.isAirBorne = false;
        }
        if (valString.equalsIgnoreCase("Verus Infinite")) {
            final Timer timer = new Timer();
            final double posY = this.mc.thePlayer.posY;
            this.mc.thePlayer.motionY = 0.5;
            final boolean b = this.mc.thePlayer.posY + 0.1 >= posY && this.mc.gameSettings.keyBindJump.isKeyDown();
            if (this.mc.thePlayer.isSneaking()) {
                this.mc.thePlayer.motionY = -0.4000000059604645;
            }
            else if (this.mc.gameSettings.keyBindJump.isKeyDown() && !b) {
                this.mc.thePlayer.motionY = 0.41999998688697815;
            }
            else {
                this.mc.thePlayer.motionY = -0.004;
            }
            MovementUtil.setMotion(0.5);
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionY = 0.42;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.motionY = -0.42;
            }
        }
        if (valString.equalsIgnoreCase(ChatFormatting.YELLOW + "Bridger Land")) {
            MovementUtil.setMotion(1.0);
            this.mc.thePlayer.motionY = 0.0;
            if (this.timer.hasTimeElapsed(200L, true)) {
                PacketUtil.sendPacketPlayer(new C03PacketPlayer());
                MovementUtil.setMotion(0.0);
            }
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionY = 0.42;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.motionY = -0.42;
            }
        }
        if (valString.equalsIgnoreCase("Verus")) {
            this.bypassTimer = new Timer();
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.motionY = 1.0E-4;
            MovementUtil.setMotion(valDouble);
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionY = 1.0;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.thePlayer.motionY = -1.0;
            }
        }
    }
    
    @Punjabi
    public void onPacket(final EventSendPacket eventSendPacket) {
        Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Watchdog");
    }
}
