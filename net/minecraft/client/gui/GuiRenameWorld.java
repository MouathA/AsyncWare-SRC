package net.minecraft.client.gui;

import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;

public class GuiRenameWorld extends GuiScreen
{
    private final String saveName;
    private GuiTextField field_146583_f;
    private GuiScreen parentScreen;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 0) {
                this.mc.getSaveLoader().renameWorld(this.saveName, this.field_146583_f.getText().trim());
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146583_f.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.field_146583_f.textboxKeyTyped(c, n);
        this.buttonList.get(0).enabled = (this.field_146583_f.getText().trim().length() > 0);
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public GuiRenameWorld(final GuiScreen parentScreen, final String saveName) {
        this.parentScreen = parentScreen;
        this.saveName = saveName;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        final String worldName = this.mc.getSaveLoader().getWorldInfo(this.saveName).getWorldName();
        (this.field_146583_f = new GuiTextField(2, this.fontRendererObj, GuiRenameWorld.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146583_f.setText(worldName);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), GuiRenameWorld.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), GuiRenameWorld.width / 2 - 100, 47, 10526880);
        this.field_146583_f.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }
}
