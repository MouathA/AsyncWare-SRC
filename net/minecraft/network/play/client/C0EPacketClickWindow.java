package net.minecraft.network.play.client;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C0EPacketClickWindow implements Packet
{
    private int mode;
    private int slotId;
    private int usedButton;
    private short actionNumber;
    private int windowId;
    private ItemStack clickedItem;
    
    public int getMode() {
        return this.mode;
    }
    
    public C0EPacketClickWindow(final int windowId, final int slotId, final int usedButton, final int mode, final ItemStack itemStack, final short actionNumber) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.usedButton = usedButton;
        this.clickedItem = ((itemStack != null) ? itemStack.copy() : null);
        this.actionNumber = actionNumber;
        this.mode = mode;
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getUsedButton() {
        return this.usedButton;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeByte(this.usedButton);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeByte(this.mode);
        packetBuffer.writeItemStackToBuffer(this.clickedItem);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public short getActionNumber() {
        return this.actionNumber;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClickWindow(this);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public ItemStack getClickedItem() {
        return this.clickedItem;
    }
    
    public C0EPacketClickWindow() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slotId = packetBuffer.readShort();
        this.usedButton = packetBuffer.readByte();
        this.actionNumber = packetBuffer.readShort();
        this.mode = packetBuffer.readByte();
        this.clickedItem = packetBuffer.readItemStackFromBuffer();
    }
}
