package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3APacketTabComplete implements Packet
{
    private String[] matches;
    
    public String[] func_149630_c() {
        return this.matches;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.matches = new String[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.matches.length) {
            this.matches[0] = packetBuffer.readStringFromBuffer(32767);
            int n = 0;
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTabComplete(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.matches.length);
        final String[] matches = this.matches;
        while (0 < matches.length) {
            packetBuffer.writeString(matches[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S3APacketTabComplete(final String[] matches) {
        this.matches = matches;
    }
    
    public S3APacketTabComplete() {
    }
}
