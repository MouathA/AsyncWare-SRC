package com.nquantum.module.player;

import net.minecraft.entity.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.notification.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import com.nquantum.event.*;

public class EntityDesync extends Module
{
    private Entity ridingEntity;
    
    public EntityDesync() {
        super("Entity Desync", 0, Category.PLAYER);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.ridingEntity == null) {
            NotificationManager.show(new Notification(NotificationType.ERROR, "Error", "You need to ride an entity!", 3));
            this.toggle();
        }
        if (this.ridingEntity != null) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            this.ridingEntity.posX = thePlayer.posX;
            this.ridingEntity.posY = thePlayer.posY;
            this.ridingEntity.posZ = thePlayer.posZ;
            PacketUtil.sendPacket(new CPacketVehicleMove(this.ridingEntity));
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.ridingEntity = null;
        this.mc.thePlayer.setEntityBoundingBox(this.mc.thePlayer.getEntityBoundingBox().offset(23.0, 0.0, 23.0));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final Entity ridingEntity = this.mc.thePlayer.ridingEntity;
        if (ridingEntity != null) {
            this.ridingEntity = ridingEntity;
            this.mc.thePlayer.dismountEntity(ridingEntity);
            this.mc.theWorld.removeEntity(ridingEntity);
        }
    }
}
