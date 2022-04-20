package net.minecraft.network.play.server;

import net.minecraft.nbt.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class S49PacketUpdateEntityNBT implements Packet
{
    private int entityId;
    private NBTTagCompound tagCompound;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityNBT(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeNBTTagCompoundToBuffer(this.tagCompound);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.tagCompound = packetBuffer.readNBTTagCompoundFromBuffer();
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    public S49PacketUpdateEntityNBT() {
    }
    
    public S49PacketUpdateEntityNBT(final int entityId, final NBTTagCompound tagCompound) {
        this.entityId = entityId;
        this.tagCompound = tagCompound;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.tagCompound;
    }
}
