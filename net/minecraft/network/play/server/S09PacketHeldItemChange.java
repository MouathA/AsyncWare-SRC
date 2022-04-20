package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S09PacketHeldItemChange implements Packet
{
    private int heldItemHotbarIndex;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleHeldItemChange(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.heldItemHotbarIndex = packetBuffer.readByte();
    }
    
    public S09PacketHeldItemChange() {
    }
    
    public int getHeldItemHotbarIndex() {
        return this.heldItemHotbarIndex;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.heldItemHotbarIndex);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S09PacketHeldItemChange(final int heldItemHotbarIndex) {
        this.heldItemHotbarIndex = heldItemHotbarIndex;
    }
}
