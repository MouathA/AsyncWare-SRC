package com.nquantum.ui.login;

import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;

public final class GuiAltLogin extends GuiScreen
{
    private AltLoginThread thread;
    private GuiTextField username;
    private final GuiScreen previousScreen;
    private PasswordField password;
    
    public GuiAltLogin(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    public static String generate(final int n) {
        final String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        final StringBuilder sb = new StringBuilder(n);
        while (0 < n) {
            sb.append(s.charAt((int)(s.length() * Math.random())));
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final int n = GuiAltLogin.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiAltLogin.width / 2 - 100, n + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(2, GuiAltLogin.width / 2 - 100, n + 72 + 12 + 24, "Generate"));
        this.buttonList.add(new GuiButton(1, GuiAltLogin.width / 2 - 100, n + 72 + 12 + 48, "Back"));
        this.username = new GuiTextField(n, this.mc.fontRendererObj, GuiAltLogin.width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, GuiAltLogin.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        switch (guiButton.id) {
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                (this.thread = new AltLoginThread(this.username.getText(), this.password.getText())).start();
                break;
            }
            case 2: {
                (this.thread = new AltLoginThread("Racisz_" + generate(5), "")).start();
                break;
            }
            case 4: {
                this.thread.start();
                break;
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) {
        super.keyTyped(c, n);
        if (c == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(c, n);
        this.password.textboxKeyTyped(c, n);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.username.mouseClicked(n, n2, n3);
        this.password.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Alt Login", GuiAltLogin.width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, (this.thread == null) ? (EnumChatFormatting.GRAY + "Idle...") : this.thread.getStatus(), GuiAltLogin.width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", GuiAltLogin.width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", GuiAltLogin.width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(n, n2, n3);
    }
}
