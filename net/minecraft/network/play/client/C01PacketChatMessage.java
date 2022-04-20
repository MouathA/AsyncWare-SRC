package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C01PacketChatMessage implements Packet
{
    private String message;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(100);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.message);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C01PacketChatMessage() {
    }
    
    public C01PacketChatMessage(String substring) {
        if (substring.length() > 100) {
            substring = substring.substring(0, 100);
        }
        this.message = substring;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processChatMessage(this);
    }
}
