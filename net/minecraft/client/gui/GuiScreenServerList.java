package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;

public class GuiScreenServerList extends GuiScreen
{
    private final GuiScreen field_146303_a;
    private final ServerData field_146301_f;
    private GuiTextField field_146302_g;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.field_146303_a.confirmClicked(false, 0);
            }
            else if (guiButton.id == 0) {
                this.field_146301_f.serverIP = this.field_146302_g.getText();
                this.field_146303_a.confirmClicked(true, 0);
            }
        }
    }
    
    public GuiScreenServerList(final GuiScreen field_146303_a, final ServerData field_146301_f) {
        this.field_146303_a = field_146303_a;
        this.field_146301_f = field_146301_f;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.field_146302_g.textboxKeyTyped(c, n)) {
            this.buttonList.get(0).enabled = (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0);
        }
        else if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiScreenServerList.width / 2 - 100, GuiScreenServerList.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiScreenServerList.width / 2 - 100, GuiScreenServerList.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        (this.field_146302_g = new GuiTextField(2, this.fontRendererObj, GuiScreenServerList.width / 2 - 100, 116, 200, 20)).setMaxStringLength(128);
        this.field_146302_g.setFocused(true);
        this.field_146302_g.setText(this.mc.gameSettings.lastServer);
        this.buttonList.get(0).enabled = (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146302_g.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), GuiScreenServerList.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), GuiScreenServerList.width / 2 - 100, 100, 10526880);
        this.field_146302_g.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        this.field_146302_g.updateCursorCounter();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.field_146302_g.getText();
        this.mc.gameSettings.saveOptions();
    }
}
