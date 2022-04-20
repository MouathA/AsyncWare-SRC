package com.nquantum.module.render;

import com.nquantum.event.impl.*;
import com.nquantum.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class Zoom extends Module
{
    @Punjabi
    public void onNigger(final EventUpdate eventUpdate) {
        final int n = (int)Asyncware.instance.settingsManager.getSettingByName("Zoom Fov").getValDouble();
        this.mc.gameSettings.keyBindZoom.isKeyDown();
    }
    
    public Zoom() {
        super("Zoom", 0, Category.RENDER);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(new Setting("Zoom Fov", this, 30.0, 5.0, 50.0, true));
    }
}
