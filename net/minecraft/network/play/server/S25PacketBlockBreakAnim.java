package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S25PacketBlockBreakAnim implements Packet
{
    private int progress;
    private BlockPos position;
    private int breakerId;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.breakerId = packetBuffer.readVarIntFromBuffer();
        this.position = packetBuffer.readBlockPos();
        this.progress = packetBuffer.readUnsignedByte();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockBreakAnim(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getProgress() {
        return this.progress;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public S25PacketBlockBreakAnim() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.breakerId);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.progress);
    }
    
    public int getBreakerId() {
        return this.breakerId;
    }
    
    public S25PacketBlockBreakAnim(final int breakerId, final BlockPos position, final int progress) {
        this.breakerId = breakerId;
        this.position = position;
        this.progress = progress;
    }
}
