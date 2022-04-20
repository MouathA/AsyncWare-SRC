package com.nquantum.module.player;

import net.minecraft.client.settings.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import com.nquantum.event.*;

public class InvWalk extends Module
{
    private final KeyBinding[] moveKeys;
    
    public InvWalk() {
        super("InvWalk", 0, Category.PLAYER);
        this.moveKeys = new KeyBinding[] { this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint };
    }
    
    @Override
    public void setup() {
        super.setup();
        this.setDisplayName("Inventory Walk");
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (!(this.mc.currentScreen instanceof GuiChat)) {
            while (0 < this.moveKeys.length) {
                this.moveKeys[0].pressed = Keyboard.isKeyDown(this.moveKeys[0].getKeyCode());
                int n = 0;
                ++n;
            }
        }
    }
}
