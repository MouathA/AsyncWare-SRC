package com.nquantum.clickgui.component.impl;

import com.nquantum.clickgui.component.*;
import com.nquantum.module.*;
import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.nquantum.util.color.*;

public class ButtonComponent extends Component
{
    public boolean open;
    private int height;
    public boolean isSetting;
    public PanelComponent parent;
    public int offset;
    private boolean isHovered;
    private ArrayList subcomponents;
    public Module mod;
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0) {
            this.mod.toggle();
        }
        if (n > n2 && n3 == 1) {
            this.isSetting = true;
        }
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseReleased(n, n2, n3);
        }
    }
    
    public ButtonComponent(final Module mod, final PanelComponent parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 12;
        if (Asyncware.instance.settingsManager.getSettingsByMod(mod) != null) {
            for (Setting setting : Asyncware.instance.settingsManager.getSettingsByMod(mod)) {}
        }
    }
    
    @Override
    public void keyTyped(final char c, final int n) {
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().keyTyped(c, n);
        }
    }
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.isHovered = this.isMouseOnButton(n, n2);
        if (!this.subcomponents.isEmpty()) {
            final Iterator<Component> iterator = this.subcomponents.iterator();
            while (iterator.hasNext()) {
                iterator.next().updateComponent(n, n2);
            }
        }
    }
    
    @Override
    public void renderComponent() {
        super.renderComponent();
        final int rgb = new Color(255, 23, 23, 255).getRGB();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 2 + this.offset, this.isHovered ? (this.mod.isToggled() ? Colors.darker(rgb, 0.8f) : Colors.darker(rgb, 0.8f)) : (this.mod.isToggled() ? rgb : new Color(24, 24, 24, 255).getRGB()));
        Asyncware.sfboldsmall.drawStringWithShadow(this.mod.getName().toLowerCase(), this.parent.getX() + 2 - Asyncware.sfboldsmall.getStringWidth(this.mod.getName().toLowerCase()) + 96.0f, (float)(this.parent.getY() + this.offset + 3), this.mod.isToggled() ? new Color(255, 255, 255, 255).getRGB() : -1);
        if (this.open && !this.subcomponents.isEmpty()) {
            final Iterator<Component> iterator = this.subcomponents.iterator();
            while (iterator.hasNext()) {
                iterator.next().renderComponent();
            }
        }
        this.open;
    }
}
