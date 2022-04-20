package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S31PacketWindowProperty implements Packet
{
    private int windowId;
    private int varValue;
    private int varIndex;
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getVarValue() {
        return this.varValue;
    }
    
    public S31PacketWindowProperty(final int windowId, final int varIndex, final int varValue) {
        this.windowId = windowId;
        this.varIndex = varIndex;
        this.varValue = varValue;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.varIndex);
        packetBuffer.writeShort(this.varValue);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowProperty(this);
    }
    
    public S31PacketWindowProperty() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.varIndex = packetBuffer.readShort();
        this.varValue = packetBuffer.readShort();
    }
    
    public int getVarIndex() {
        return this.varIndex;
    }
}
