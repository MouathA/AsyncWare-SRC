package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S02PacketChat implements Packet
{
    private IChatComponent chatComponent;
    private byte type;
    
    public S02PacketChat(final IChatComponent chatComponent) {
        this(chatComponent, (byte)1);
    }
    
    public byte getType() {
        return this.type;
    }
    
    public S02PacketChat(final IChatComponent chatComponent, final byte type) {
        this.chatComponent = chatComponent;
        this.type = type;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChat(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.chatComponent);
        packetBuffer.writeByte(this.type);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chatComponent = packetBuffer.readChatComponent();
        this.type = packetBuffer.readByte();
    }
    
    public boolean isChat() {
        return this.type == 1 || this.type == 2;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S02PacketChat() {
    }
    
    public IChatComponent getChatComponent() {
        return this.chatComponent;
    }
}
