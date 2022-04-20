package com.nquantum.module.misc;

import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class NoMouseInteract extends Module
{
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("No Mouse Intersect");
    }
    
    public NoMouseInteract() {
        super("No Mouse Intersect", 0, Category.MISC);
    }
}
