package net.minecraft.network.login.client;

import com.mojang.authlib.*;
import net.minecraft.network.login.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class C00PacketLoginStart implements Packet
{
    private GameProfile profile;
    
    public void processPacket(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processLoginStart(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.profile.getName());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile((UUID)null, packetBuffer.readStringFromBuffer(16));
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginServer)netHandler);
    }
    
    public GameProfile getProfile() {
        return this.profile;
    }
    
    public C00PacketLoginStart(final GameProfile profile) {
        this.profile = profile;
    }
    
    public C00PacketLoginStart() {
    }
}
