package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C11PacketEnchantItem implements Packet
{
    private int button;
    private int windowId;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public C11PacketEnchantItem() {
    }
    
    public int getButton() {
        return this.button;
    }
    
    public C11PacketEnchantItem(final int windowId, final int button) {
        this.windowId = windowId;
        this.button = button;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeByte(this.button);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.button = packetBuffer.readByte();
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEnchantItem(this);
    }
}
