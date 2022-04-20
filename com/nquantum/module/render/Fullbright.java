package com.nquantum.module.render;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class Fullbright extends Module
{
    private float oldBrightness;
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.gammaSetting = this.oldBrightness;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.oldBrightness = this.mc.gameSettings.gammaSetting;
    }
    
    public Fullbright() {
        super("Fullbright", 0, Category.RENDER);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Full Bright");
        this.mc.gameSettings.gammaSetting = 10.0f;
    }
}
