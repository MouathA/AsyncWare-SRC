package com.nquantum.module.render;

import com.nquantum.module.*;
import net.minecraft.client.gui.*;
import com.nquantum.event.impl.*;

public class PlayerList extends Module
{
    public PlayerList() {
        super("Player List", 0, Category.MISC);
    }
    
    @Override
    public synchronized void on2D(final Event2D event2D) {
        super.on2D(event2D);
        Gui.drawRect(400.0, 100.0, 0.0, 0.0, -1);
    }
    
    @Override
    public synchronized void onUpdate(final EventUpdate eventUpdate) {
        super.onUpdate(eventUpdate);
    }
}
