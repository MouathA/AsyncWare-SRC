package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component;

import com.nquantum.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class Frame
{
    public int dragY;
    public ArrayList components;
    private int barHeight;
    private boolean isDragging;
    private boolean open;
    private int width;
    public Category category;
    private int x;
    public int dragX;
    private int y;
    
    public int getWidth() {
        return this.width;
    }
    
    public Frame(final Category category) {
        this.components = new ArrayList();
        this.category = category;
        this.width = 100;
        this.x = 2;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int barHeight = this.barHeight;
        final Iterator<Module> iterator = Asyncware.instance.moduleManager.getModules(this.category).iterator();
        while (iterator.hasNext()) {
            this.components.add(new Button(iterator.next(), this, barHeight));
            barHeight += 12;
        }
    }
    
    public boolean isWithinHeader(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.barHeight;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setDrag(final boolean isDragging) {
        this.isDragging = isDragging;
    }
    
    public int getX() {
        return this.x;
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
    
    public void renderFrame(final FontRenderer fontRenderer) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(9, 9, 9, 255).getRGB());
        Asyncware.s.drawString(this.category.name().toLowerCase(), (float)(this.x + 3), this.y + 2.9f, -1);
        if (this.open && !this.components.isEmpty()) {
            final Iterator<Component> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().renderComponent();
            }
        }
    }
    
    public ArrayList getComponents() {
        return this.components;
    }
    
    public void refresh() {
        int barHeight = this.barHeight;
        for (final Component component : this.components) {
            component.setOff(barHeight);
            barHeight += component.getHeight();
        }
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
}
