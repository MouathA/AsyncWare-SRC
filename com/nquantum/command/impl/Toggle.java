package com.nquantum.command.impl;

import com.nquantum.command.*;
import com.nquantum.*;
import com.nquantum.module.*;
import com.nquantum.notification.*;
import java.util.*;

public class Toggle extends Command
{
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length > 0) {
            final String s2 = array[0];
            for (final Module module : Asyncware.instance.moduleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(s2)) {
                    module.toggle();
                    NotificationManager.show(new Notification(NotificationType.INFO, "Success", "Toggled " + module.getName(), 3));
                    break;
                }
                NotificationManager.show(new Notification(NotificationType.ERROR, "Error", "Module not found. ", 3));
            }
        }
    }
    
    public Toggle() {
        super("Toggle", "Toggle specified module", "toggle <name>", Collections.singletonList("t"));
    }
}
