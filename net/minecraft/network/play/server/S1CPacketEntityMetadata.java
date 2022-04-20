package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S1CPacketEntityMetadata implements Packet
{
    private int entityId;
    private List field_149378_b;
    
    public int getEntityId() {
        return this.entityId;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, packetBuffer);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    public S1CPacketEntityMetadata() {
    }
    
    public List func_149376_c() {
        return this.field_149378_b;
    }
    
    public S1CPacketEntityMetadata(final int entityId, final DataWatcher dataWatcher, final boolean b) {
        this.entityId = entityId;
        if (b) {
            this.field_149378_b = dataWatcher.getAllWatched();
        }
        else {
            this.field_149378_b = dataWatcher.getChanged();
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMetadata(this);
    }
}
