package com.nquantum.module.movement;

import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class Sprint extends Module
{
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.setSprinting(false);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Keep Sprint");
        if (!this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.moveForward > 0.0f) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
    
    public Sprint() {
        super("Sprint", 50, Category.MOVEMENT);
    }
}
