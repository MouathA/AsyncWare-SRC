package com.nquantum.module.render;

import com.nquantum.module.*;
import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class SwingAnimation extends Module
{
    public SwingAnimation() {
        super("Block Animations", 0, Category.RENDER);
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Exhibition");
        list.add("Swong");
        list.add("Swang");
        list.add("Swank");
        list.add("Fan");
        list.add("Jew Stabber");
        list.add("Astolfo");
        Asyncware.instance.settingsManager.rSetting(new Setting("Animation Mode", this, "Exhibition", list));
    }
}
