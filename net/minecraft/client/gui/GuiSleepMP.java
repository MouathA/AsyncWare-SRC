package net.minecraft.client.gui;

import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.client.resources.*;

public class GuiSleepMP extends GuiChat
{
    private void wakeFromSleep() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.wakeFromSleep();
        }
        else if (n != 28 && n != 156) {
            super.keyTyped(c, n);
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (!trim.isEmpty()) {
                this.mc.thePlayer.sendChatMessage(trim);
            }
            this.inputField.setText("");
            this.mc.ingameGUI.getChatGUI().resetScroll();
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiSleepMP.width / 2 - 100, GuiSleepMP.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.wakeFromSleep();
        }
        else {
            super.actionPerformed(guiButton);
        }
    }
}
