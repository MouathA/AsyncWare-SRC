package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class S43PacketCamera implements Packet
{
    public int entityId;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
    
    public S43PacketCamera() {
    }
    
    public S43PacketCamera(final Entity entity) {
        this.entityId = entity.getEntityId();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCamera(this);
    }
}
