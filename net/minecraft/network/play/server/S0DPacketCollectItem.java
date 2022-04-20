package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S0DPacketCollectItem implements Packet
{
    private int entityId;
    private int collectedItemEntityId;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCollectItem(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.collectedItemEntityId = packetBuffer.readVarIntFromBuffer();
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.collectedItemEntityId);
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S0DPacketCollectItem() {
    }
    
    public int getCollectedItemEntityID() {
        return this.collectedItemEntityId;
    }
    
    public S0DPacketCollectItem(final int collectedItemEntityId, final int entityId) {
        this.collectedItemEntityId = collectedItemEntityId;
        this.entityId = entityId;
    }
}
