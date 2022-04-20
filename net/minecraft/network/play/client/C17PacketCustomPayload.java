package net.minecraft.network.play.client;

import net.minecraft.network.*;
import net.minecraft.network.play.*;
import java.io.*;
import io.netty.buffer.*;

public class C17PacketCustomPayload implements Packet
{
    private String channel;
    private PacketBuffer data;
    
    public PacketBuffer getBufferData() {
        return this.data;
    }
    
    public C17PacketCustomPayload(final String channel, final PacketBuffer data) {
        this.channel = channel;
        this.data = data;
        if (data.writerIndex() > 32767) {
            throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(20);
        final int readableBytes = packetBuffer.readableBytes();
        if (readableBytes >= 0 && readableBytes <= 32767) {
            this.data = new PacketBuffer(packetBuffer.readBytes(readableBytes));
            return;
        }
        throw new IOException("Payload may not be larger than 32767 bytes");
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }
    
    public String getChannelName() {
        return this.channel;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processVanilla250Packet(this);
    }
    
    public C17PacketCustomPayload() {
    }
}
