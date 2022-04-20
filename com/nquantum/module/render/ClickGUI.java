package com.nquantum.module.render;

import com.nquantum.module.*;
import com.nquantum.*;
import net.minecraft.client.gui.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class ClickGUI extends Module
{
    public ClickGUI() {
        super("ClickGUI", 54, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.displayGuiScreen(Asyncware.instance.clickGui);
        this.toggle();
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("New");
        list.add("JellyLike");
        Asyncware.instance.settingsManager.rSetting(new Setting("Design", this, "New", list));
        Asyncware.instance.settingsManager.rSetting(new Setting("Sound", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiRed", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 26.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 42.0, 0.0, 255.0, true));
    }
}
