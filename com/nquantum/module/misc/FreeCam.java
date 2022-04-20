package com.nquantum.module.misc;

import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.nquantum.event.impl.*;

public class FreeCam extends Module
{
    double y;
    private double startX;
    private double startZ;
    private float startPitch;
    public static boolean enabled;
    private double startY;
    private EntityOtherPlayerMP playerCopy;
    double x;
    private float startYaw;
    double z;
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.theWorld.removeEntityFromWorld(-123);
        this.mc.thePlayer.setPosition(this.x, this.y, this.z);
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.thePlayer.noClip = false;
    }
    
    @Punjabi
    public void onCollide(final EventCollide eventCollide) {
        eventCollide.setBoundingBox(null);
        eventCollide.cancel();
    }
    
    @Punjabi
    public void onPacket(final EventSendPacket eventSendPacket) {
        if (eventSendPacket.getPacket() instanceof C03PacketPlayer) {
            eventSendPacket.cancel();
        }
    }
    
    public FreeCam() {
        super("Free Cam", 0, Category.MISC);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(this.mc.theWorld, new GameProfile(new UUID(2L, 2L), "Nigger"));
        entityOtherPlayerMP.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        entityOtherPlayerMP.setCustomNameTag("Nigger");
        entityOtherPlayerMP.rotationYawHead = this.mc.thePlayer.rotationYawHead;
        entityOtherPlayerMP.clonePlayer(this.mc.thePlayer, true);
        entityOtherPlayerMP.copyLocationAndAnglesFrom(this.mc.thePlayer);
        entityOtherPlayerMP.rotationYaw = this.mc.thePlayer.rotationYaw;
        this.mc.theWorld.addEntityToWorld(-123, entityOtherPlayerMP);
        this.x = this.mc.thePlayer.posX;
        this.y = this.mc.thePlayer.posY;
        this.z = this.mc.thePlayer.posZ;
        this.mc.thePlayer.noClip = true;
        this.mc.thePlayer.capabilities.isFlying = true;
    }
    
    @Punjabi
    public void onPreMotion(final EventPreMotionUpdate eventPreMotionUpdate) {
        this.mc.thePlayer.capabilities.isFlying = true;
        this.mc.thePlayer.noClip = true;
        this.mc.thePlayer.capabilities.setFlySpeed(0.1f);
        eventPreMotionUpdate.setCancelled(true);
    }
    
    @Punjabi
    public void onBB(final EventBoundingBox eventBoundingBox) {
        eventBoundingBox.setBoundingBox(null);
        eventBoundingBox.cancel();
    }
}
