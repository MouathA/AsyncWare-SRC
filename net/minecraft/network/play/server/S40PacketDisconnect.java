package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S40PacketDisconnect implements Packet
{
    private IChatComponent reason;
    
    public IChatComponent getReason() {
        return this.reason;
    }
    
    public S40PacketDisconnect(final IChatComponent reason) {
        this.reason = reason;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDisconnect(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readChatComponent();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.reason);
    }
    
    public S40PacketDisconnect() {
    }
}
