package net.minecraft.network.login.server;

import net.minecraft.network.login.*;
import net.minecraft.network.*;
import java.io.*;

public class S03PacketEnableCompression implements Packet
{
    private int compressionTreshold;
    
    public S03PacketEnableCompression() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.compressionTreshold);
    }
    
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleEnableCompression(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.compressionTreshold = packetBuffer.readVarIntFromBuffer();
    }
    
    public int getCompressionTreshold() {
        return this.compressionTreshold;
    }
    
    public S03PacketEnableCompression(final int compressionTreshold) {
        this.compressionTreshold = compressionTreshold;
    }
}
