package com.nquantum.clickgui.component;

import java.util.*;
import com.nquantum.module.*;
import com.nquantum.clickgui.component.impl.*;

public class Component
{
    private ArrayList subcomponents;
    private boolean isHovered;
    public boolean isSetting;
    public Module mod;
    public boolean open;
    public PanelComponent parent;
    private int height;
    public int offset;
    
    public int getHeight() {
        return 0;
    }
    
    public void keyTyped(final char c, final int n) {
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public void updateComponent(final int n, final int n2) {
    }
    
    public int getParentHeight() {
        return 0;
    }
    
    public void setOff(final int n) {
    }
    
    public void renderComponent() {
    }
}
