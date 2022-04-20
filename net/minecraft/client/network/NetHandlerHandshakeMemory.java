package net.minecraft.client.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.server.network.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final NetworkManager networkManager;
    private final MinecraftServer mcServer;
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    public NetHandlerHandshakeMemory(final MinecraftServer mcServer, final NetworkManager networkManager) {
        this.mcServer = mcServer;
        this.networkManager = networkManager;
    }
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        this.networkManager.setConnectionState(c00Handshake.getRequestedState());
        this.networkManager.setNetHandler(new NetHandlerLoginServer(this.mcServer, this.networkManager));
    }
}
