package net.minecraft.network.play.client;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C15PacketClientSettings implements Packet
{
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private int view;
    private boolean enableColors;
    private int modelPartFlags;
    private String lang;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.lang);
        packetBuffer.writeByte(this.view);
        packetBuffer.writeByte(this.chatVisibility.getChatVisibility());
        packetBuffer.writeBoolean(this.enableColors);
        packetBuffer.writeByte(this.modelPartFlags);
    }
    
    public EntityPlayer.EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.lang = packetBuffer.readStringFromBuffer(7);
        this.view = packetBuffer.readByte();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(packetBuffer.readByte());
        this.enableColors = packetBuffer.readBoolean();
        this.modelPartFlags = packetBuffer.readUnsignedByte();
    }
    
    public String getLang() {
        return this.lang;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C15PacketClientSettings() {
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientSettings(this);
    }
    
    public int getModelPartFlags() {
        return this.modelPartFlags;
    }
    
    public boolean isColorsEnabled() {
        return this.enableColors;
    }
    
    public C15PacketClientSettings(final String lang, final int view, final EntityPlayer.EnumChatVisibility chatVisibility, final boolean enableColors, final int modelPartFlags) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = chatVisibility;
        this.enableColors = enableColors;
        this.modelPartFlags = modelPartFlags;
    }
}
