package com.nquantum.ui.login;

import net.minecraft.client.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;

public final class AltLoginThread extends Thread
{
    private final String username;
    private final String password;
    private String status;
    private Minecraft mc;
    
    private Session createSession(final String username, final String password) {
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        yggdrasilUserAuthentication.logIn();
        return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
    }
    
    public String getStatus() {
        return this.status;
    }
    
    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = EnumChatFormatting.YELLOW + "Logging in...";
        final Session session = this.createSession(this.username, this.password);
        if (session == null) {
            this.status = EnumChatFormatting.RED + "Login failed!";
        }
        else {
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + session.getUsername() + ")";
            this.mc.session = session;
        }
    }
    
    public AltLoginThread(final String username, final String password) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = username;
        this.password = password;
        this.status = EnumChatFormatting.GRAY + "Waiting...";
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
}
