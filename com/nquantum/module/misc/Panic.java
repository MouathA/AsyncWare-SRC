package com.nquantum.module.misc;

import com.nquantum.module.*;
import com.nquantum.*;
import java.util.*;

public class Panic extends Module
{
    public Panic() {
        super("Panic", 0, Category.MISC);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final ArrayList<Module> list = new ArrayList<Module>();
        for (final Module module : Asyncware.instance.moduleManager.getModules()) {
            if (module.isToggled()) {
                list.add(module);
            }
        }
        final Iterator<Module> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().toggle();
        }
    }
}
