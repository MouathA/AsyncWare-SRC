package com.nquantum.ui;

import net.minecraft.client.*;
import java.io.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.nquantum.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;

public class GuiCapeManager extends GuiScreen
{
    public Minecraft mc;
    
    public GuiCapeManager() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Gui.drawRect(scaledResolution.getScaledWidth(), 100.0, 0.0, 0.0, new Color(0, 0, 0, 150).getRGB());
        Gui.drawRect(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 0.0, new Color(0, 0, 0, 2).getRGB());
        Asyncware.roboto.drawString("Cape Manager", 2.0f, 4.0f, -1);
        GL11.glPushMatrix();
        GuiInventory.drawEntityOnScreen(40, scaledResolution.getScaledHeight() / 2, 50, (float)(-n), 1.0f, this.mc.thePlayer);
        GL11.glPopMatrix();
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.buttonList.add(new GuiButton(1, scaledResolution.getScaledWidth() - 220, scaledResolution.getScaledHeight() - 40, "Load cape from PC"));
    }
}
