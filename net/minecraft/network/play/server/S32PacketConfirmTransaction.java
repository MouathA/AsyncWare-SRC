package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S32PacketConfirmTransaction implements Packet
{
    private boolean field_148893_c;
    private short actionNumber;
    private int windowId;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S32PacketConfirmTransaction() {
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public boolean func_148888_e() {
        return this.field_148893_c;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleConfirmTransaction(this);
    }
    
    public short getActionNumber() {
        return this.actionNumber;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeBoolean(this.field_148893_c);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.actionNumber = packetBuffer.readShort();
        this.field_148893_c = packetBuffer.readBoolean();
    }
    
    public S32PacketConfirmTransaction(final int windowId, final short actionNumber, final boolean field_148893_c) {
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.field_148893_c = field_148893_c;
    }
}
