package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiDisconnected extends GuiScreen
{
    private int field_175353_i;
    private IChatComponent message;
    private String reason;
    private List multilineMessage;
    private final GuiScreen parentScreen;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, GuiDisconnected.width / 2, GuiDisconnected.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int n4 = GuiDisconnected.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            final Iterator<String> iterator = this.multilineMessage.iterator();
            while (iterator.hasNext()) {
                this.drawCenteredString(this.fontRendererObj, iterator.next(), GuiDisconnected.width / 2, n4, 16777215);
                n4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), GuiDisconnected.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, GuiDisconnected.width / 2 - 100, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
    }
    
    public GuiDisconnected(final GuiScreen parentScreen, final String s, final IChatComponent message) {
        this.parentScreen = parentScreen;
        this.reason = I18n.format(s, new Object[0]);
        this.message = message;
    }
}
