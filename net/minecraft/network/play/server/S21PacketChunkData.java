package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.chunk.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S21PacketChunkData implements Packet
{
    private int chunkX;
    private Extracted extractedData;
    private int chunkZ;
    private boolean field_149279_g;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChunkData(this);
    }
    
    private static int func_179757_a(final byte[] array, final byte[] array2, final int n) {
        System.arraycopy(array, 0, array2, n, array.length);
        return n + array.length;
    }
    
    public int getChunkX() {
        return this.chunkX;
    }
    
    public int getChunkZ() {
        return this.chunkZ;
    }
    
    protected static int func_180737_a(final int n, final boolean b, final boolean b2) {
        return n * 2 * 16 * 16 * 16 + n * 16 * 16 * 16 / 2 + (b ? (n * 16 * 16 * 16 / 2) : 0) + (b2 ? 256 : 0);
    }
    
    public byte[] func_149272_d() {
        return this.extractedData.data;
    }
    
    public S21PacketChunkData(final Chunk chunk, final boolean field_149279_g, final int n) {
        this.chunkX = chunk.xPosition;
        this.chunkZ = chunk.zPosition;
        this.field_149279_g = field_149279_g;
        this.extractedData = func_179756_a(chunk, field_149279_g, !chunk.getWorld().provider.getHasNoSky(), n);
    }
    
    public boolean func_149274_i() {
        return this.field_149279_g;
    }
    
    public static Extracted func_179756_a(final Chunk chunk, final boolean b, final boolean b2, final int n) {
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final Extracted extracted = new Extracted();
        final ArrayList arrayList = Lists.newArrayList();
        int n2 = 0;
        while (0 < blockStorageArray.length) {
            final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[0];
            if (extendedBlockStorage != null && (!b || !extendedBlockStorage.isEmpty()) && (n & 0x1) != 0x0) {
                final Extracted extracted2 = extracted;
                extracted2.dataSize |= 0x1;
                arrayList.add(extendedBlockStorage);
            }
            ++n2;
        }
        extracted.data = new byte[func_180737_a(Integer.bitCount(extracted.dataSize), b2, b)];
        final Iterator<ExtendedBlockStorage> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            final char[] data = iterator.next().getData();
            while (0 < data.length) {
                final char c = data[0];
                final byte[] data2 = extracted.data;
                final int n3 = 0;
                ++n2;
                data2[n3] = (byte)(c & '\u00ff');
                final byte[] data3 = extracted.data;
                final int n4 = 0;
                ++n2;
                data3[n4] = (byte)(c >> 8 & 0xFF);
                int n5 = 0;
                ++n5;
            }
        }
        final Iterator<ExtendedBlockStorage> iterator2 = arrayList.iterator();
        while (iterator2.hasNext()) {
            n2 = func_179757_a(iterator2.next().getBlocklightArray().getData(), extracted.data, 0);
        }
        if (b2) {
            final Iterator<ExtendedBlockStorage> iterator3 = arrayList.iterator();
            while (iterator3.hasNext()) {
                n2 = func_179757_a(iterator3.next().getSkylightArray().getData(), extracted.data, 0);
            }
        }
        if (b) {
            func_179757_a(chunk.getBiomeArray(), extracted.data, 0);
        }
        return extracted;
    }
    
    public S21PacketChunkData() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkX);
        packetBuffer.writeInt(this.chunkZ);
        packetBuffer.writeBoolean(this.field_149279_g);
        packetBuffer.writeShort((short)(this.extractedData.dataSize & 0xFFFF));
        packetBuffer.writeByteArray(this.extractedData.data);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getExtractedSize() {
        return this.extractedData.dataSize;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chunkX = packetBuffer.readInt();
        this.chunkZ = packetBuffer.readInt();
        this.field_149279_g = packetBuffer.readBoolean();
        this.extractedData = new Extracted();
        this.extractedData.dataSize = packetBuffer.readShort();
        this.extractedData.data = packetBuffer.readByteArray();
    }
    
    public static class Extracted
    {
        public int dataSize;
        public byte[] data;
    }
}
