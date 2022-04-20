package com.nquantum.event;

import java.lang.reflect.*;

public class Data
{
    public final Object source;
    public final byte priority;
    public final Method target;
    
    public Data(final Object source, final Method target, final byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}
