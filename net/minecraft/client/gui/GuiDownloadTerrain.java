package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetHandlerPlayClient netHandlerPlayClient;
    private int progress;
    
    @Override
    public void initGui() {
        this.buttonList.clear();
    }
    
    public GuiDownloadTerrain(final NetHandlerPlayClient netHandlerPlayClient) {
        this.netHandlerPlayClient = netHandlerPlayClient;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), GuiDownloadTerrain.width / 2, GuiDownloadTerrain.height / 2 - 50, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        ++this.progress;
        if (this.progress % 20 == 0) {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
}
