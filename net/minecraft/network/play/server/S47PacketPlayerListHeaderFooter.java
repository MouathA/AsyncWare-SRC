package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S47PacketPlayerListHeaderFooter implements Packet
{
    private IChatComponent footer;
    private IChatComponent header;
    
    public S47PacketPlayerListHeaderFooter(final IChatComponent header) {
        this.header = header;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.header = packetBuffer.readChatComponent();
        this.footer = packetBuffer.readChatComponent();
    }
    
    public IChatComponent getFooter() {
        return this.footer;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerListHeaderFooter(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public IChatComponent getHeader() {
        return this.header;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.header);
        packetBuffer.writeChatComponent(this.footer);
    }
    
    public S47PacketPlayerListHeaderFooter() {
    }
}
