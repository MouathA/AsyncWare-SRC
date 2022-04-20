package com.nquantum.module.misc;

import com.nquantum.event.impl.*;
import net.minecraft.network.play.client.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class AntiTabComplete extends Module
{
    @Punjabi
    private synchronized void listen(final EventSendPacket eventSendPacket) {
        if (eventSendPacket.getPacket() instanceof C14PacketTabComplete) {
            eventSendPacket.cancel();
        }
    }
    
    public AntiTabComplete() {
        super("Anti Tab Complete", 0, Category.MISC);
    }
}
