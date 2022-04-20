package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C01PacketPing implements Packet
{
    private long clientTime;
    
    public long getClientTime() {
        return this.clientTime;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }
    
    public void processPacket(final INetHandlerStatusServer netHandlerStatusServer) {
        netHandlerStatusServer.processPing(this);
    }
    
    public C01PacketPing(final long clientTime) {
        this.clientTime = clientTime;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusServer)netHandler);
    }
    
    public C01PacketPing() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }
}
