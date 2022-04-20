package com.nquantum.module.misc;

import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import com.nquantum.module.*;
import java.util.concurrent.*;

public class AntiVanish extends Module
{
    private final Queue toLookUp;
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
    }
    
    @Punjabi
    public void onPacket(final EventReceivePacket eventReceivePacket) {
        if (eventReceivePacket.getPacket() instanceof S38PacketPlayerListItem && ((S38PacketPlayerListItem)eventReceivePacket.getPacket()).func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
            for (final S38PacketPlayerListItem.AddPlayerData addPlayerData : ((S38PacketPlayerListItem)eventReceivePacket.getPacket()).func_179767_a()) {
                if (this.mc.getNetHandler().getPlayerInfo(addPlayerData.getProfile().getId()) != null) {
                    continue;
                }
                this.toLookUp.add(addPlayerData.getProfile().getId());
            }
        }
    }
    
    public AntiVanish() {
        super("Anti Vanish", 0, Category.MISC);
        this.toLookUp = new ConcurrentLinkedQueue();
    }
}
