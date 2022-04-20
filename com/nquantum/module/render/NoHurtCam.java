package com.nquantum.module.render;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class NoHurtCam extends Module
{
    public NoHurtCam() {
        super("NoHurtCam", 0, Category.RENDER);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("No Hurt Cam");
    }
}
