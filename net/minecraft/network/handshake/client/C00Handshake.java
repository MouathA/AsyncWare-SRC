package net.minecraft.network.handshake.client;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.*;

public class C00Handshake implements Packet
{
    private int protocolVersion;
    private EnumConnectionState requestedState;
    private String ip;
    private int port;
    
    public C00Handshake(final int protocolVersion, final String ip, final int port, final EnumConnectionState requestedState) {
        this.protocolVersion = protocolVersion;
        this.ip = ip;
        this.port = port;
        this.requestedState = requestedState;
    }
    
    public EnumConnectionState getRequestedState() {
        return this.requestedState;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.protocolVersion);
        packetBuffer.writeString(this.ip);
        packetBuffer.writeShort(this.port);
        packetBuffer.writeVarIntToBuffer(this.requestedState.getId());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.protocolVersion = packetBuffer.readVarIntFromBuffer();
        this.ip = packetBuffer.readStringFromBuffer(255);
        this.port = packetBuffer.readUnsignedShort();
        this.requestedState = EnumConnectionState.getById(packetBuffer.readVarIntFromBuffer());
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerHandshakeServer)netHandler);
    }
    
    public C00Handshake() {
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public void processPacket(final INetHandlerHandshakeServer netHandlerHandshakeServer) {
        netHandlerHandshakeServer.processHandshake(this);
    }
}
