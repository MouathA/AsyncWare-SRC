package com.nquantum.auth;

import com.nquantum.ui.login.*;
import org.lwjgl.input.*;
import com.nquantum.util.auth.*;
import java.awt.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import com.mojang.realmsclient.gui.*;

public class GuiAuthenticate extends GuiScreen
{
    private GuiTextField username;
    private PasswordField password;
    private AuthThread thread;
    public static boolean authenticated;
    private final GuiScreen previousScreen;
    
    static {
        GuiAuthenticate.authenticated = false;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
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
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                final AuthThread thread = new AuthThread();
                final HWIDUtil hwidUtil = new HWIDUtil();
                if (thread.checkHwid(HWIDUtil.getHashedHWID())) {
                    this.thread = thread;
                    Thread.sleep(300L);
                    GuiAuthenticate.authenticated = true;
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                this.drawCenteredString(this.mc.fontRendererObj, "Can't authenticate!", GuiAuthenticate.width / 2, 120, Color.RED.getRGB());
                GuiAuthenticate.authenticated = false;
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Gui.drawRect(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 0.0, new Color(20, 20, 20, 255).getRGB());
        this.username.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Authentication", scaledResolution.getScaledWidth() / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, (this.thread == null) ? (EnumChatFormatting.RED + "Not authenticated") : (ChatFormatting.GREEN + "Authenticated!"), GuiAuthenticate.width / 2, 35, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "UID", GuiAuthenticate.width / 2 - 96, 66, -7829368);
        }
        this.drawCenteredString(this.mc.fontRendererObj, "Good Obfuscation - Literally no one", scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - 20, -1);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.username.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        final int n = GuiAuthenticate.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiAuthenticate.width / 2 - 100, n + 72 + 12, "Login"));
        (this.username = new GuiTextField(n, this.mc.fontRendererObj, GuiAuthenticate.width / 2 - 100, 60, 200, 20)).setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    public GuiAuthenticate(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (c == '\t') {
            if (!this.username.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
            }
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}
