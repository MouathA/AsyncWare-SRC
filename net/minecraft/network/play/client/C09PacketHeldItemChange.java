package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C09PacketHeldItemChange implements Packet
{
    private int slotId;
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processHeldItemChange(this);
    }
    
    public C09PacketHeldItemChange() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
    }
    
    public C09PacketHeldItemChange(final int slotId) {
        this.slotId = slotId;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
    }
}
