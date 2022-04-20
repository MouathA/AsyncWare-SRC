package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.realms.*;
import net.minecraft.client.gui.achievement.*;
import com.nquantum.ui.*;
import java.io.*;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146444_f;
    private GuiTextField username;
    private int field_146445_a;
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }
    
    @Override
    public void initGui() {
        this.field_146445_a = 0;
        this.buttonList.clear();
        this.username = new GuiTextField(100, this.mc.fontRendererObj, GuiIngameMenu.width / 2 - 100, 60, 200, 20);
        this.buttonList.add(new GuiButton(112, 2, GuiIngameMenu.height - 30, "Set client name"));
        this.buttonList.add(new GuiButton(1, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 120 - 16, I18n.format("menu.returnToMenu", new Object[0])));
        this.buttonList.add(new GuiButton(8, GuiIngameMenu.width / 2 - 100, 12, "Cape Manager"));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add(new GuiButton(4, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 24 - 16, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 96 - 16, 98, 20, I18n.format("menu.options", new Object[0])));
        final GuiButton guiButton;
        this.buttonList.add(guiButton = new GuiButton(7, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 96 - 16, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 48 - 16, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 48 - 16, 98, 20, I18n.format("gui.stats", new Object[0])));
        guiButton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), GuiIngameMenu.width / 2, 40, 16777215);
        this.username.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                final boolean integratedServerRunning = this.mc.isIntegratedServerRunning();
                final boolean func_181540_al = this.mc.func_181540_al();
                guiButton.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                if (integratedServerRunning) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                if (func_181540_al) {
                    new RealmsBridge().switchToRealms(new GuiMainMenu());
                    break;
                }
                this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
            }
            case 8: {
                this.mc.displayGuiScreen(new GuiCapeManager());
                break;
            }
        }
    }
}
