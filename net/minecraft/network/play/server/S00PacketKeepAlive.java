package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S00PacketKeepAlive implements Packet
{
    private int id;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S00PacketKeepAlive() {
    }
    
    public int func_149134_c() {
        return this.id;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readVarIntFromBuffer();
    }
    
    public S00PacketKeepAlive(final int id) {
        this.id = id;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleKeepAlive(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.id);
    }
}
