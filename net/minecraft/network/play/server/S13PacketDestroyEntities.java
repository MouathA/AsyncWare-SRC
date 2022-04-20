package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S13PacketDestroyEntities implements Packet
{
    private int[] entityIDs;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S13PacketDestroyEntities(final int... entityIDs) {
        this.entityIDs = entityIDs;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityIDs = new int[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.entityIDs.length) {
            this.entityIDs[0] = packetBuffer.readVarIntFromBuffer();
            int n = 0;
            ++n;
        }
    }
    
    public S13PacketDestroyEntities() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityIDs.length);
        while (0 < this.entityIDs.length) {
            packetBuffer.writeVarIntToBuffer(this.entityIDs[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDestroyEntities(this);
    }
    
    public int[] getEntityIDs() {
        return this.entityIDs;
    }
}
