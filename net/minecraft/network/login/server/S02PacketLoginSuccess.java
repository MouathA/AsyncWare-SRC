package net.minecraft.network.login.server;

import com.mojang.authlib.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;
import java.util.*;
import java.io.*;

public class S02PacketLoginSuccess implements Packet
{
    private GameProfile profile;
    
    public S02PacketLoginSuccess(final GameProfile profile) {
        this.profile = profile;
    }
    
    public S02PacketLoginSuccess() {
    }
    
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleLoginSuccess(this);
    }
    
    public GameProfile getProfile() {
        return this.profile;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        final UUID id = this.profile.getId();
        packetBuffer.writeString((id == null) ? "" : id.toString());
        packetBuffer.writeString(this.profile.getName());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile(UUID.fromString(packetBuffer.readStringFromBuffer(36)), packetBuffer.readStringFromBuffer(16));
    }
}
