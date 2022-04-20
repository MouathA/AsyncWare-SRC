package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S06PacketUpdateHealth implements Packet
{
    private float saturationLevel;
    private int foodLevel;
    private float health;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateHealth(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.health);
        packetBuffer.writeVarIntToBuffer(this.foodLevel);
        packetBuffer.writeFloat(this.saturationLevel);
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public float getHealth() {
        return this.health;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.health = packetBuffer.readFloat();
        this.foodLevel = packetBuffer.readVarIntFromBuffer();
        this.saturationLevel = packetBuffer.readFloat();
    }
    
    public S06PacketUpdateHealth(final float health, final int foodLevel, final float saturationLevel) {
        this.health = health;
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
    }
    
    public S06PacketUpdateHealth() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public float getSaturationLevel() {
        return this.saturationLevel;
    }
}
