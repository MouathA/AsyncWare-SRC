package net.minecraft.network.play.server;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2FPacketSetSlot implements Packet
{
    private int slot;
    private ItemStack item;
    private int windowId;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetSlot(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slot);
        packetBuffer.writeItemStackToBuffer(this.item);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slot = packetBuffer.readShort();
        this.item = packetBuffer.readItemStackFromBuffer();
    }
    
    public S2FPacketSetSlot(final int windowId, final int slot, final ItemStack itemStack) {
        this.windowId = windowId;
        this.slot = slot;
        this.item = ((itemStack == null) ? null : itemStack.copy());
    }
    
    public S2FPacketSetSlot() {
    }
    
    public int func_149173_d() {
        return this.slot;
    }
    
    public ItemStack func_149174_e() {
        return this.item;
    }
    
    public int func_149175_c() {
        return this.windowId;
    }
}
