package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.entity.*;

public class S1BPacketEntityAttach implements Packet
{
    private int vehicleEntityId;
    private int leash;
    private int entityId;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityAttach(this);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public int getLeash() {
        return this.leash;
    }
    
    public S1BPacketEntityAttach() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeInt(this.vehicleEntityId);
        packetBuffer.writeByte(this.leash);
    }
    
    public S1BPacketEntityAttach(final int leash, final Entity entity, final Entity entity2) {
        this.leash = leash;
        this.entityId = entity.getEntityId();
        this.vehicleEntityId = ((entity2 != null) ? entity2.getEntityId() : -1);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.vehicleEntityId = packetBuffer.readInt();
        this.leash = packetBuffer.readUnsignedByte();
    }
    
    public int getVehicleEntityId() {
        return this.vehicleEntityId;
    }
}
