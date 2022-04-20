package com.nquantum.module.combat;

import com.nquantum.util.time.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import com.nquantum.event.*;

public class Regen extends Module
{
    Timer timer;
    
    public Regen() {
        super("Regen", 0, Category.COMBAT);
        this.timer = new Timer();
    }
    
    @Punjabi
    private void onEvent(final EventUpdate eventUpdate) {
        if (this.mc.thePlayer.getHealth() < 20.0f) {
            PacketUtil.sendPacket(new C03PacketPlayer(true));
            System.out.println("sex");
        }
    }
}
