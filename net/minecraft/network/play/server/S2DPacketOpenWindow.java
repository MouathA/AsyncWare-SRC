package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2DPacketOpenWindow implements Packet
{
    private int windowId;
    private String inventoryType;
    private int entityId;
    private IChatComponent windowTitle;
    private int slotCount;
    
    public IChatComponent getWindowTitle() {
        return this.windowTitle;
    }
    
    public boolean hasSlots() {
        return this.slotCount > 0;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleOpenWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.inventoryType = packetBuffer.readStringFromBuffer(32);
        this.windowTitle = packetBuffer.readChatComponent();
        this.slotCount = packetBuffer.readUnsignedByte();
        if (this.inventoryType.equals("EntityHorse")) {
            this.entityId = packetBuffer.readInt();
        }
    }
    
    public S2DPacketOpenWindow(final int windowId, final String inventoryType, final IChatComponent windowTitle, final int slotCount) {
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.slotCount = slotCount;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeString(this.inventoryType);
        packetBuffer.writeChatComponent(this.windowTitle);
        packetBuffer.writeByte(this.slotCount);
        if (this.inventoryType.equals("EntityHorse")) {
            packetBuffer.writeInt(this.entityId);
        }
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public S2DPacketOpenWindow() {
    }
    
    public int getSlotCount() {
        return this.slotCount;
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent, final int n2, final int entityId) {
        this(n, s, chatComponent, n2);
        this.entityId = entityId;
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent) {
        this(n, s, chatComponent, 0);
    }
    
    public String getGuiId() {
        return this.inventoryType;
    }
    
    public int getWindowId() {
        return this.windowId;
    }
}
