package com.nquantum.module.combat;

import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class Reach extends Module
{
    @Punjabi
    public void onReach(final EventGetBlockReach eventGetBlockReach) {
    }
    
    public Reach() {
        super("Reach", 0, Category.COMBAT);
    }
}
