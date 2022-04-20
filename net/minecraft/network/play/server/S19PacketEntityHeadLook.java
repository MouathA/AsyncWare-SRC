package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class S19PacketEntityHeadLook implements Packet
{
    private int entityId;
    private byte yaw;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.yaw = packetBuffer.readByte();
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    public S19PacketEntityHeadLook() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityHeadLook(this);
    }
    
    public S19PacketEntityHeadLook(final Entity entity, final byte yaw) {
        this.entityId = entity.getEntityId();
        this.yaw = yaw;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.yaw);
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
}
