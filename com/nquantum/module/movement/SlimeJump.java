package com.nquantum.module.movement;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import com.nquantum.event.*;

public class SlimeJump extends Module
{
    public SlimeJump() {
        super("Slime Jump", 0, Category.MOVEMENT);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ).getBlock() instanceof BlockSlime && this.mc.gameSettings.keyBindJump.isPressed()) {
            this.mc.thePlayer.motionY = 1.0;
        }
    }
}
