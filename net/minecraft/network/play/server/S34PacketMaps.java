package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import net.minecraft.world.storage.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S34PacketMaps implements Packet
{
    private byte[] mapDataBytes;
    private byte mapScale;
    private int mapMaxY;
    private int mapMaxX;
    private int mapMinY;
    private Vec4b[] mapVisiblePlayersVec4b;
    private int mapMinX;
    private int mapId;
    
    public int getMapId() {
        return this.mapId;
    }
    
    public S34PacketMaps(final int mapId, final byte mapScale, final Collection collection, final byte[] array, final int mapMinX, final int mapMinY, final int mapMaxX, final int mapMaxY) {
        this.mapId = mapId;
        this.mapScale = mapScale;
        this.mapVisiblePlayersVec4b = collection.toArray(new Vec4b[collection.size()]);
        this.mapMinX = mapMinX;
        this.mapMinY = mapMinY;
        this.mapMaxX = mapMaxX;
        this.mapMaxY = mapMaxY;
        this.mapDataBytes = new byte[mapMaxX * mapMaxY];
        while (0 < mapMaxX) {
            while (0 < mapMaxY) {
                this.mapDataBytes[0 + 0 * mapMaxX] = array[mapMinX + 0 + (mapMinY + 0) * 128];
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.mapId = packetBuffer.readVarIntFromBuffer();
        this.mapScale = packetBuffer.readByte();
        this.mapVisiblePlayersVec4b = new Vec4b[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.mapVisiblePlayersVec4b.length) {
            final short n = packetBuffer.readByte();
            this.mapVisiblePlayersVec4b[0] = new Vec4b((byte)(n >> 4 & 0xF), packetBuffer.readByte(), packetBuffer.readByte(), (byte)(n & 0xF));
            int n2 = 0;
            ++n2;
        }
        this.mapMaxX = packetBuffer.readUnsignedByte();
        if (this.mapMaxX > 0) {
            this.mapMaxY = packetBuffer.readUnsignedByte();
            this.mapMinX = packetBuffer.readUnsignedByte();
            this.mapMinY = packetBuffer.readUnsignedByte();
            this.mapDataBytes = packetBuffer.readByteArray();
        }
    }
    
    public void setMapdataTo(final MapData p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/network/play/server/S34PacketMaps.mapScale:B
        //     5: putfield        net/minecraft/world/storage/MapData.scale:B
        //     8: aload_1        
        //     9: getfield        net/minecraft/world/storage/MapData.mapDecorations:Ljava/util/Map;
        //    12: invokeinterface java/util/Map.clear:()V
        //    17: iconst_0       
        //    18: aload_0        
        //    19: getfield        net/minecraft/network/play/server/S34PacketMaps.mapVisiblePlayersVec4b:[Lnet/minecraft/util/Vec4b;
        //    22: arraylength    
        //    23: if_icmpge       69
        //    26: aload_0        
        //    27: getfield        net/minecraft/network/play/server/S34PacketMaps.mapVisiblePlayersVec4b:[Lnet/minecraft/util/Vec4b;
        //    30: iconst_0       
        //    31: aaload         
        //    32: astore_3       
        //    33: aload_1        
        //    34: getfield        net/minecraft/world/storage/MapData.mapDecorations:Ljava/util/Map;
        //    37: new             Ljava/lang/StringBuilder;
        //    40: dup            
        //    41: invokespecial   java/lang/StringBuilder.<init>:()V
        //    44: ldc             "icon-"
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    49: iconst_0       
        //    50: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    53: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    56: aload_3        
        //    57: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    62: pop            
        //    63: iinc            2, 1
        //    66: goto            17
        //    69: iconst_0       
        //    70: aload_0        
        //    71: getfield        net/minecraft/network/play/server/S34PacketMaps.mapMaxX:I
        //    74: if_icmpge       132
        //    77: iconst_0       
        //    78: aload_0        
        //    79: getfield        net/minecraft/network/play/server/S34PacketMaps.mapMaxY:I
        //    82: if_icmpge       126
        //    85: aload_1        
        //    86: getfield        net/minecraft/world/storage/MapData.colors:[B
        //    89: aload_0        
        //    90: getfield        net/minecraft/network/play/server/S34PacketMaps.mapMinX:I
        //    93: iconst_0       
        //    94: iadd           
        //    95: aload_0        
        //    96: getfield        net/minecraft/network/play/server/S34PacketMaps.mapMinY:I
        //    99: iconst_0       
        //   100: iadd           
        //   101: sipush          128
        //   104: imul           
        //   105: iadd           
        //   106: aload_0        
        //   107: getfield        net/minecraft/network/play/server/S34PacketMaps.mapDataBytes:[B
        //   110: iconst_0       
        //   111: iconst_0       
        //   112: aload_0        
        //   113: getfield        net/minecraft/network/play/server/S34PacketMaps.mapMaxX:I
        //   116: imul           
        //   117: iadd           
        //   118: baload         
        //   119: bastore        
        //   120: iinc            3, 1
        //   123: goto            77
        //   126: iinc            2, 1
        //   129: goto            69
        //   132: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.mapId);
        packetBuffer.writeByte(this.mapScale);
        packetBuffer.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);
        final Vec4b[] mapVisiblePlayersVec4b = this.mapVisiblePlayersVec4b;
        while (0 < mapVisiblePlayersVec4b.length) {
            final Vec4b vec4b = mapVisiblePlayersVec4b[0];
            packetBuffer.writeByte((vec4b.func_176110_a() & 0xF) << 4 | (vec4b.func_176111_d() & 0xF));
            packetBuffer.writeByte(vec4b.func_176112_b());
            packetBuffer.writeByte(vec4b.func_176113_c());
            int n = 0;
            ++n;
        }
        packetBuffer.writeByte(this.mapMaxX);
        if (this.mapMaxX > 0) {
            packetBuffer.writeByte(this.mapMaxY);
            packetBuffer.writeByte(this.mapMinX);
            packetBuffer.writeByte(this.mapMinY);
            packetBuffer.writeByteArray(this.mapDataBytes);
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMaps(this);
    }
    
    public S34PacketMaps() {
    }
}
