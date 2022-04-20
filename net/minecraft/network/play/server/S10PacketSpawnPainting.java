package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S10PacketSpawnPainting implements Packet
{
    private String title;
    private EnumFacing facing;
    private BlockPos position;
    private int entityID;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.title = packetBuffer.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
        this.position = packetBuffer.readBlockPos();
        this.facing = EnumFacing.getHorizontal(packetBuffer.readUnsignedByte());
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPainting(this);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    public S10PacketSpawnPainting() {
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public S10PacketSpawnPainting(final EntityPainting entityPainting) {
        this.entityID = entityPainting.getEntityId();
        this.position = entityPainting.getHangingPosition();
        this.facing = entityPainting.facingDirection;
        this.title = entityPainting.art.title;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeString(this.title);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getHorizontalIndex());
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
}
