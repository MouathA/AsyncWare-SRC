package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import java.util.*;
import net.minecraft.world.chunk.*;

public class S26PacketMapChunkBulk implements Packet
{
    private boolean isOverworld;
    private int[] zPositions;
    private int[] xPositions;
    private S21PacketChunkData.Extracted[] chunksData;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.isOverworld);
        packetBuffer.writeVarIntToBuffer(this.chunksData.length);
        int n = 0;
        while (0 < this.xPositions.length) {
            packetBuffer.writeInt(this.xPositions[0]);
            packetBuffer.writeInt(this.zPositions[0]);
            packetBuffer.writeShort((short)(this.chunksData[0].dataSize & 0xFFFF));
            ++n;
        }
        while (0 < this.xPositions.length) {
            packetBuffer.writeBytes(this.chunksData[0].data);
            ++n;
        }
    }
    
    public S26PacketMapChunkBulk() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.isOverworld = packetBuffer.readBoolean();
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        this.xPositions = new int[varIntFromBuffer];
        this.zPositions = new int[varIntFromBuffer];
        this.chunksData = new S21PacketChunkData.Extracted[varIntFromBuffer];
        int n = 0;
        while (0 < varIntFromBuffer) {
            this.xPositions[0] = packetBuffer.readInt();
            this.zPositions[0] = packetBuffer.readInt();
            this.chunksData[0] = new S21PacketChunkData.Extracted();
            this.chunksData[0].dataSize = (packetBuffer.readShort() & 0xFFFF);
            this.chunksData[0].data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.chunksData[0].dataSize), this.isOverworld, true)];
            ++n;
        }
        while (0 < varIntFromBuffer) {
            packetBuffer.readBytes(this.chunksData[0].data);
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMapChunkBulk(this);
    }
    
    public byte[] getChunkBytes(final int n) {
        return this.chunksData[n].data;
    }
    
    public int getChunkSize(final int n) {
        return this.chunksData[n].dataSize;
    }
    
    public int getChunkCount() {
        return this.xPositions.length;
    }
    
    public int getChunkZ(final int n) {
        return this.zPositions[n];
    }
    
    public int getChunkX(final int n) {
        return this.xPositions[n];
    }
    
    public S26PacketMapChunkBulk(final List list) {
        final int size = list.size();
        this.xPositions = new int[size];
        this.zPositions = new int[size];
        this.chunksData = new S21PacketChunkData.Extracted[size];
        this.isOverworld = !list.get(0).getWorld().provider.getHasNoSky();
        while (0 < size) {
            final Chunk chunk = list.get(0);
            final S21PacketChunkData.Extracted func_179756_a = S21PacketChunkData.func_179756_a(chunk, true, this.isOverworld, 65535);
            this.xPositions[0] = chunk.xPosition;
            this.zPositions[0] = chunk.zPosition;
            this.chunksData[0] = func_179756_a;
            int n = 0;
            ++n;
        }
    }
}
