package net.minecraft.client.gui;

import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.io.*;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private boolean field_146346_f;
    private int enableButtonsTimer;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawGradientRect(0, 0, GuiGameOver.width, GuiGameOver.height, 1615855616, -1602211792);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        final boolean hardcoreModeEnabled = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
        this.drawCenteredString(this.fontRendererObj, hardcoreModeEnabled ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]), GuiGameOver.width / 2 / 2, 30, 16777215);
        if (hardcoreModeEnabled) {
            this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), GuiGameOver.width / 2, 144, 16777215);
        }
        this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), GuiGameOver.width / 2, 100, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        else {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public GuiGameOver() {
        this.field_146346_f = false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.enableButtonsTimer;
        if (this.enableButtonsTimer == 20) {
            final Iterator<GuiButton> iterator = this.buttonList.iterator();
            while (iterator.hasNext()) {
                iterator.next().enabled = true;
            }
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            if (this.mc.isIntegratedServerRunning()) {
                this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
            }
            else {
                this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
            }
        }
        else {
            this.buttonList.add(new GuiButton(0, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
            this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
            if (this.mc.getSession() == null) {
                this.buttonList.get(1).enabled = false;
            }
        }
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        while (iterator.hasNext()) {
            iterator.next().enabled = false;
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.mc.thePlayer.respawnPlayer();
                this.mc.displayGuiScreen(null);
                break;
            }
            case 1: {
                if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                final GuiYesNo guiYesNo = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
                this.mc.displayGuiScreen(guiYesNo);
                guiYesNo.setButtonDelay(20);
                break;
            }
        }
    }
}
