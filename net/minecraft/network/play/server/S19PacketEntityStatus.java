package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class S19PacketEntityStatus implements Packet
{
    private byte logicOpcode;
    private int entityId;
    
    public S19PacketEntityStatus() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.logicOpcode = packetBuffer.readByte();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityStatus(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeByte(this.logicOpcode);
    }
    
    public byte getOpCode() {
        return this.logicOpcode;
    }
    
    public S19PacketEntityStatus(final Entity entity, final byte logicOpcode) {
        this.entityId = entity.getEntityId();
        this.logicOpcode = logicOpcode;
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
}
