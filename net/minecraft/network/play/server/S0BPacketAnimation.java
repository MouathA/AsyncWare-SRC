package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;

public class S0BPacketAnimation implements Packet
{
    private int entityId;
    private int type;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
    }
    
    public int getAnimationType() {
        return this.type;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleAnimation(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S0BPacketAnimation(final Entity entity, final int type) {
        this.entityId = entity.getEntityId();
        this.type = type;
    }
    
    public S0BPacketAnimation() {
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readUnsignedByte();
    }
}
