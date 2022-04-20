package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S05PacketSpawnPosition implements Packet
{
    private BlockPos spawnBlockPos;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPosition(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.spawnBlockPos = packetBuffer.readBlockPos();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.spawnBlockPos);
    }
    
    public S05PacketSpawnPosition() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S05PacketSpawnPosition(final BlockPos spawnBlockPos) {
        this.spawnBlockPos = spawnBlockPos;
    }
    
    public BlockPos getSpawnPos() {
        return this.spawnBlockPos;
    }
}
