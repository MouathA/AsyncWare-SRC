package com.nquantum.clickgui;

import net.minecraft.client.gui.*;
import com.nquantum.clickgui.component.impl.*;
import com.nquantum.clickgui.component.*;
import java.util.*;
import java.io.*;
import com.nquantum.module.*;
import net.minecraft.item.*;

public class ClickguiScreen extends GuiScreen
{
    public static ArrayList frames;
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Iterator<PanelComponent> iterator = ClickguiScreen.frames.iterator();
        while (iterator.hasNext()) {
            iterator.next().setDrag(false);
        }
        for (final PanelComponent panelComponent : ClickguiScreen.frames) {
            if (panelComponent.isOpen() && !panelComponent.getComponents().isEmpty()) {
                final Iterator iterator3 = panelComponent.getComponents().iterator();
                while (iterator3.hasNext()) {
                    iterator3.next().mouseReleased(n, n2, n3);
                }
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        for (final PanelComponent panelComponent : ClickguiScreen.frames) {
            if (panelComponent.isWithinHeader(n, n2) && n3 == 0) {
                panelComponent.setDrag(true);
                panelComponent.dragX = n - panelComponent.getX();
                panelComponent.dragY = n2 - panelComponent.getY();
            }
            if (panelComponent.isWithinHeader(n, n2) && n3 == 1) {
                panelComponent.setOpen(!panelComponent.isOpen());
            }
            if (panelComponent.isOpen() && !panelComponent.getComponents().isEmpty()) {
                final Iterator iterator2 = panelComponent.getComponents().iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().mouseClicked(n, n2, n3);
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) {
        for (final PanelComponent panelComponent : ClickguiScreen.frames) {
            if (panelComponent.isOpen() && n != 1 && !panelComponent.getComponents().isEmpty()) {
                final Iterator iterator2 = panelComponent.getComponents().iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().keyTyped(c, n);
                }
            }
        }
        if (n == 1) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    public ClickguiScreen() {
        ClickguiScreen.frames = new ArrayList();
        final Category[] values = Category.values();
        while (0 < values.length) {
            final PanelComponent panelComponent = new PanelComponent(values[0]);
            panelComponent.setX(2);
            ClickguiScreen.frames.add(panelComponent);
            final int n = 2 + (panelComponent.getWidth() + 3);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    protected void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        super.renderToolTip(itemStack, n, n2);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        for (final PanelComponent panelComponent : ClickguiScreen.frames) {
            panelComponent.renderFrame(this.fontRendererObj);
            panelComponent.updatePosition(n, n2);
            final Iterator iterator2 = panelComponent.getComponents().iterator();
            while (iterator2.hasNext()) {
                iterator2.next().updateComponent(n, n2);
            }
        }
    }
}
