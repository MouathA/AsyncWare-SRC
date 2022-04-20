package com.nquantum.module.combat;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class FollowAura extends Module
{
    public FollowAura() {
        super("FollowAura", 0, Category.COMBAT);
    }
    
    @Punjabi
    private void onFart(final EventPreMotionUpdate eventPreMotionUpdate) {
        this.setDisplayName("Follow Aura");
        eventPreMotionUpdate.setCancelled(true);
    }
}
