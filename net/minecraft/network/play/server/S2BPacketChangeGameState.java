package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S2BPacketChangeGameState implements Packet
{
    private int state;
    private float field_149141_c;
    
    public S2BPacketChangeGameState() {
    }
    
    public int getGameState() {
        return this.state;
    }
    
    static {
        S2BPacketChangeGameState.MESSAGE_NAMES = new String[] { "tile.bed.notValid" };
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S2BPacketChangeGameState(final int state, final float field_149141_c) {
        this.state = state;
        this.field_149141_c = field_149141_c;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChangeGameState(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.state);
        packetBuffer.writeFloat(this.field_149141_c);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.state = packetBuffer.readUnsignedByte();
        this.field_149141_c = packetBuffer.readFloat();
    }
    
    public float func_149137_d() {
        return this.field_149141_c;
    }
}
