package com.nquantum.module.player;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class SpinBot extends Module
{
    static int yaw;
    
    public SpinBot() {
        super("Spin Bot", 0, Category.PLAYER);
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
        SpinBot.yaw += 30;
        eventPreMotionUpdate.setYaw((float)SpinBot.yaw);
        eventPreMotionUpdate.setPitch(180.0f);
    }
}
