package com.nquantum.event;

import com.nquantum.*;
import java.util.*;

public abstract class Event
{
    private boolean cancelled;
    
    public void cancel() {
        this.cancelled = true;
    }
    
    public Event call() {
        this.cancelled = false;
        call(this);
        return this;
    }
    
    private static void call(final Event event) {
        final ArrayHelper value = Asyncware.instance.eventManager.get(event.getClass());
        if (value != null) {
            for (final Data data : value) {
                data.target.invoke(data.source, event);
            }
        }
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public enum State
    {
        PRE("PRE", 0, "PRE", 0);
        
        private static final State[] $VALUES;
        
        POST("POST", 1, "POST", 1);
        
        static {
            $VALUES = new State[] { State.PRE, State.POST };
        }
        
        private State(final String s, final int n, final String s2, final int n2) {
        }
    }
}
