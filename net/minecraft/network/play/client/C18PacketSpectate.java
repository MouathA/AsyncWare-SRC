package net.minecraft.network.play.client;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C18PacketSpectate implements Packet
{
    private UUID id;
    
    public Entity getEntity(final WorldServer worldServer) {
        return worldServer.getEntityFromUuid(this.id);
    }
    
    public C18PacketSpectate(final UUID id) {
        this.id = id;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUuid(this.id);
    }
    
    public C18PacketSpectate() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.handleSpectate(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readUuid();
    }
}
