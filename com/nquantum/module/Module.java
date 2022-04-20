package com.nquantum.module;

import net.minecraft.client.*;
import com.nquantum.event.*;
import com.nquantum.*;
import com.nquantum.event.impl.*;

public class Module
{
    protected Minecraft mc;
    private int key;
    private Category category;
    private String name;
    private boolean toggled;
    private String displayName;
    
    public void setup() {
    }
    
    @Punjabi
    public synchronized void onUpdate(final EventUpdate eventUpdate) {
    }
    
    public void onDisable() {
        Asyncware.instance.eventManager.unregister(this);
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        this.onToggle();
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public String getDisplayName() {
        return (this.displayName == null) ? this.name : this.displayName;
    }
    
    @Punjabi
    public synchronized void on3D(final Event3D event3D) {
    }
    
    public int getKey() {
        return this.key;
    }
    
    @Punjabi
    public synchronized void on2D(final Event2D event2D) {
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    @Punjabi
    public synchronized void onSend(final EventSendPacket eventSendPacket) {
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    @Punjabi
    public synchronized void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
    }
    
    public Module(final String name, final int key, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = false;
        this.setup();
    }
    
    @Punjabi
    public synchronized void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void onEnable() {
        Asyncware.instance.eventManager.register(this);
    }
    
    @Punjabi
    public synchronized void onReceive(final EventReceivePacket eventReceivePacket) {
    }
    
    public void onToggle() {
    }
}
