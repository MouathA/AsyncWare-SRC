package com.nquantum.module.render;

import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class ItemPhysics extends Module
{
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Item Physics");
    }
    
    public ItemPhysics() {
        super("ItemPhysics", 0, Category.RENDER);
    }
}
