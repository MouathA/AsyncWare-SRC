package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C00PacketKeepAlive implements Packet
{
    public int key;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.key);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C00PacketKeepAlive() {
    }
    
    public int getKey() {
        return this.key;
    }
    
    public C00PacketKeepAlive(final int key) {
        this.key = key;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.key = packetBuffer.readVarIntFromBuffer();
    }
}
