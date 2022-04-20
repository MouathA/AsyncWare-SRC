package net.minecraft.network.status.server;

import net.minecraft.network.status.*;
import net.minecraft.network.*;
import java.io.*;

public class S01PacketPong implements Packet
{
    private long clientTime;
    
    public void processPacket(final INetHandlerStatusClient netHandlerStatusClient) {
        netHandlerStatusClient.handlePong(this);
    }
    
    public S01PacketPong() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }
    
    public S01PacketPong(final long clientTime) {
        this.clientTime = clientTime;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }
}
