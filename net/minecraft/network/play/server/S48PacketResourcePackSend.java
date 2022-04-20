package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S48PacketResourcePackSend implements Packet
{
    private String url;
    private String hash;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.url = packetBuffer.readStringFromBuffer(32767);
        this.hash = packetBuffer.readStringFromBuffer(40);
    }
    
    public String getHash() {
        return this.hash;
    }
    
    public String getURL() {
        return this.url;
    }
    
    public S48PacketResourcePackSend(final String url, final String hash) {
        this.url = url;
        this.hash = hash;
        if (hash.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleResourcePack(this);
    }
    
    public S48PacketResourcePackSend() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.url);
        packetBuffer.writeString(this.hash);
    }
}
