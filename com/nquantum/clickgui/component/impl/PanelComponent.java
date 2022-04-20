package com.nquantum.clickgui.component.impl;

import com.nquantum.*;
import com.nquantum.module.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import java.util.*;
import com.nquantum.clickgui.component.*;

public class PanelComponent
{
    private int barHeight;
    private boolean open;
    private boolean isDragging;
    private int width;
    public int dragY;
    public ArrayList components;
    private int y;
    private int x;
    public int dragX;
    public Category category;
    
    public boolean isDragging() {
        return this.isDragging;
    }
    
    public void setDragging(final boolean isDragging) {
        this.isDragging = isDragging;
    }
    
    public PanelComponent(final Category category) {
        this.components = new ArrayList();
        this.category = category;
        this.width = 100;
        this.x = 20;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int barHeight = this.barHeight;
        final Iterator<Module> iterator = Asyncware.instance.moduleManager.getModules(this.category).iterator();
        while (iterator.hasNext()) {
            this.components.add(new ButtonComponent(iterator.next(), this, barHeight));
            barHeight += 12;
        }
    }
    
    public boolean isWithinHeader(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.barHeight;
    }
    
    public int getDragY() {
        return this.dragY;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void renderFrame(final FontRenderer fontRenderer) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(9, 9, 9, 255).getRGB());
        Asyncware.s.drawString(this.category.name().toLowerCase(), (float)(this.x + 3), this.y + 2.9f, -1);
        this.components.sort(PanelComponent::lambda$renderFrame$0);
        if (this.open && !this.components.isEmpty()) {
            final Iterator<Component> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().renderComponent();
            }
        }
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setDrag(final boolean isDragging) {
        this.isDragging = isDragging;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public ArrayList getComponents() {
        return this.components;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void updatePosition(final int n, final int n2) {
        if (this.isDragging) {
            this.setX(n - this.dragX);
            this.setY(n2 - this.dragY);
        }
    }
    
    public void setBarHeight(final int barHeight) {
        this.barHeight = barHeight;
    }
    
    private static int lambda$renderFrame$0(final Component component, final Component component2) {
        return (int)(Asyncware.sfboldsmall.getStringWidth(component2.toString()) - Asyncware.s.getStringWidth(component.toString()));
    }
    
    public void refresh() {
        int barHeight = this.barHeight;
        for (final Component component : this.components) {
            component.setOff(barHeight);
            barHeight += component.getHeight();
        }
    }
    
    public void setDragX(final int dragX) {
        this.dragX = dragX;
    }
    
    public int getDragX() {
        return this.dragX;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public int getBarHeight() {
        return this.barHeight;
    }
    
    public void setDragY(final int dragY) {
        this.dragY = dragY;
    }
    
    public boolean isOpen() {
        return this.open;
    }
}
