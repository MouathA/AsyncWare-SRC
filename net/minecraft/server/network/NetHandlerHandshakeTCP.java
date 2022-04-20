package net.minecraft.server.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.login.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final NetworkManager networkManager;
    private final MinecraftServer server;
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        switch (NetHandlerHandshakeTCP$1.$SwitchMap$net$minecraft$network$EnumConnectionState[c00Handshake.getRequestedState().ordinal()]) {
            case 1: {
                this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
                if (c00Handshake.getProtocolVersion() > 47) {
                    final ChatComponentText chatComponentText = new ChatComponentText("Outdated server! I'm still on 1.8.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
                    this.networkManager.closeChannel(chatComponentText);
                    break;
                }
                if (c00Handshake.getProtocolVersion() < 47) {
                    final ChatComponentText chatComponentText2 = new ChatComponentText("Outdated client! Please use 1.8.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText2));
                    this.networkManager.closeChannel(chatComponentText2);
                    break;
                }
                this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
                break;
            }
            case 2: {
                this.networkManager.setConnectionState(EnumConnectionState.STATUS);
                this.networkManager.setNetHandler(new NetHandlerStatusServer(this.server, this.networkManager));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + c00Handshake.getRequestedState());
            }
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    public NetHandlerHandshakeTCP(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
}
