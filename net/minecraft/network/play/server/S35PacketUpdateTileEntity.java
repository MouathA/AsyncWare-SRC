package net.minecraft.network.play.server;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S35PacketUpdateTileEntity implements Packet
{
    private NBTTagCompound nbt;
    private int metadata;
    private BlockPos blockPos;
    
    public NBTTagCompound getNbtCompound() {
        return this.nbt;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        packetBuffer.writeByte((byte)this.metadata);
        packetBuffer.writeNBTTagCompoundToBuffer(this.nbt);
    }
    
    public S35PacketUpdateTileEntity() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.metadata = packetBuffer.readUnsignedByte();
        this.nbt = packetBuffer.readNBTTagCompoundFromBuffer();
    }
    
    public int getTileEntityType() {
        return this.metadata;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateTileEntity(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S35PacketUpdateTileEntity(final BlockPos blockPos, final int metadata, final NBTTagCompound nbt) {
        this.blockPos = blockPos;
        this.metadata = metadata;
        this.nbt = nbt;
    }
    
    public BlockPos getPos() {
        return this.blockPos;
    }
}
