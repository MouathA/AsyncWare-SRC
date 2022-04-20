package net.minecraft.network.play.client;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C10PacketCreativeInventoryAction implements Packet
{
    private ItemStack stack;
    private int slotId;
    
    public C10PacketCreativeInventoryAction(final int slotId, final ItemStack itemStack) {
        this.slotId = slotId;
        this.stack = ((itemStack != null) ? itemStack.copy() : null);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processCreativeInventoryAction(this);
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeItemStackToBuffer(this.stack);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
        this.stack = packetBuffer.readItemStackFromBuffer();
    }
    
    public C10PacketCreativeInventoryAction() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
}
