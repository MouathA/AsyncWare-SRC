package net.minecraft.server.network;

import net.minecraft.network.status.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private static final IChatComponent field_183007_a;
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    private boolean field_183008_d;
    
    static {
        field_183007_a = new ChatComponentText("Status request has been handled.");
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    public NetHandlerStatusServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
    
    @Override
    public void processPing(final C01PacketPing c01PacketPing) {
        this.networkManager.sendPacket(new S01PacketPong(c01PacketPing.getClientTime()));
        this.networkManager.closeChannel(NetHandlerStatusServer.field_183007_a);
    }
    
    @Override
    public void processServerQuery(final C00PacketServerQuery c00PacketServerQuery) {
        if (this.field_183008_d) {
            this.networkManager.closeChannel(NetHandlerStatusServer.field_183007_a);
        }
        else {
            this.field_183008_d = true;
            this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
        }
    }
}
