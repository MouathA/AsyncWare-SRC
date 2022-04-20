package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S1FPacketSetExperience implements Packet
{
    private int totalExperience;
    private int level;
    private float field_149401_a;
    
    public S1FPacketSetExperience(final float field_149401_a, final int totalExperience, final int level) {
        this.field_149401_a = field_149401_a;
        this.totalExperience = totalExperience;
        this.level = level;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetExperience(this);
    }
    
    public float func_149397_c() {
        return this.field_149401_a;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S1FPacketSetExperience() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.field_149401_a);
        packetBuffer.writeVarIntToBuffer(this.level);
        packetBuffer.writeVarIntToBuffer(this.totalExperience);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149401_a = packetBuffer.readFloat();
        this.level = packetBuffer.readVarIntFromBuffer();
        this.totalExperience = packetBuffer.readVarIntFromBuffer();
    }
    
    public int getTotalExperience() {
        return this.totalExperience;
    }
}
