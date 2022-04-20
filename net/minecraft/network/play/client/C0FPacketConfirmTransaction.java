package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C0FPacketConfirmTransaction implements Packet
{
    public int windowId;
    public boolean accepted;
    public short uid;
    
    public short getUid() {
        return this.uid;
    }
    
    public C0FPacketConfirmTransaction(final int windowId, final short uid, final boolean accepted) {
        this.windowId = windowId;
        this.uid = uid;
        this.accepted = accepted;
    }
    
    public C0FPacketConfirmTransaction() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.uid = packetBuffer.readShort();
        this.accepted = (packetBuffer.readByte() != 0);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.uid);
        packetBuffer.writeByte(this.accepted ? 1 : 0);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processConfirmTransaction(this);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
}
