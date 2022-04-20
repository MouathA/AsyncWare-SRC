package com.nquantum.module.movement;

import com.nquantum.module.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import com.nquantum.event.impl.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import com.nquantum.event.*;

public class AntiVoid extends Module
{
    BlockPos under;
    
    public AntiVoid() {
        super("Anti Void", 0, Category.MOVEMENT);
    }
    
    private boolean isAirUnderneath() {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final WorldClient theWorld = this.mc.theWorld;
        final AxisAlignedBB entityBoundingBox = thePlayer.getEntityBoundingBox();
        while (0 < thePlayer.posY + thePlayer.getEyeHeight()) {
            if (!theWorld.getCollidingBoundingBoxes(thePlayer, entityBoundingBox.offset(0.0, 0, 0.0)).isEmpty()) {
                return true;
            }
            final int n;
            n += 2;
        }
        return false;
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.mc.thePlayer.fallDistance > 6.0f) {
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 7.0, this.mc.thePlayer.posZ, true));
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 2.2, this.mc.thePlayer.posZ);
        }
    }
}
