package com.nquantum.event.impl;

import com.nquantum.event.*;

public class EventChat extends Event
{
    public String message;
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
