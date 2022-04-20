package net.minecraft.network.play.server;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import java.util.*;

public class S30PacketWindowItems implements Packet
{
    private ItemStack[] itemStacks;
    private int windowId;
    
    public int func_148911_c() {
        return this.windowId;
    }
    
    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.itemStacks.length);
        final ItemStack[] itemStacks = this.itemStacks;
        while (0 < itemStacks.length) {
            packetBuffer.writeItemStackToBuffer(itemStacks[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S30PacketWindowItems(final int windowId, final List list) {
        this.windowId = windowId;
        this.itemStacks = new ItemStack[list.size()];
        while (0 < this.itemStacks.length) {
            final ItemStack itemStack = list.get(0);
            this.itemStacks[0] = ((itemStack == null) ? null : itemStack.copy());
            int n = 0;
            ++n;
        }
    }
    
    public S30PacketWindowItems() {
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowItems(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        final short short1 = packetBuffer.readShort();
        this.itemStacks = new ItemStack[short1];
        while (0 < short1) {
            this.itemStacks[0] = packetBuffer.readItemStackFromBuffer();
            int n = 0;
            ++n;
        }
    }
}
