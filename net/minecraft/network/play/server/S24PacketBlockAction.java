package net.minecraft.network.play.server;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S24PacketBlockAction implements Packet
{
    private Block block;
    private int pitch;
    private BlockPos blockPosition;
    private int instrument;
    
    public S24PacketBlockAction() {
    }
    
    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
    
    public int getData2() {
        return this.pitch;
    }
    
    public int getData1() {
        return this.instrument;
    }
    
    public Block getBlockType() {
        return this.block;
    }
    
    public S24PacketBlockAction(final BlockPos blockPosition, final Block block, final int instrument, final int pitch) {
        this.blockPosition = blockPosition;
        this.instrument = instrument;
        this.pitch = pitch;
        this.block = block;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockAction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.instrument = packetBuffer.readUnsignedByte();
        this.pitch = packetBuffer.readUnsignedByte();
        this.block = Block.getBlockById(packetBuffer.readVarIntFromBuffer() & 0xFFF);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeByte(this.instrument);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 0xFFF);
    }
}
