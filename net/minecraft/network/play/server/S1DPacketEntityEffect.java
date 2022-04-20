package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import net.minecraft.potion.*;

public class S1DPacketEntityEffect implements Packet
{
    private int duration;
    private byte hideParticles;
    private int entityId;
    private byte amplifier;
    private byte effectId;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
        packetBuffer.writeByte(this.amplifier);
        packetBuffer.writeVarIntToBuffer(this.duration);
        packetBuffer.writeByte(this.hideParticles);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readByte();
        this.amplifier = packetBuffer.readByte();
        this.duration = packetBuffer.readVarIntFromBuffer();
        this.hideParticles = packetBuffer.readByte();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public boolean func_149429_c() {
        return this.duration == 32767;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEffect(this);
    }
    
    public boolean func_179707_f() {
        return this.hideParticles != 0;
    }
    
    public byte getEffectId() {
        return this.effectId;
    }
    
    public S1DPacketEntityEffect(final int entityId, final PotionEffect potionEffect) {
        this.entityId = entityId;
        this.effectId = (byte)(potionEffect.getPotionID() & 0xFF);
        this.amplifier = (byte)(potionEffect.getAmplifier() & 0xFF);
        if (potionEffect.getDuration() > 32767) {
            this.duration = 32767;
        }
        else {
            this.duration = potionEffect.getDuration();
        }
        this.hideParticles = (byte)(potionEffect.getIsShowParticles() ? 1 : 0);
    }
    
    public S1DPacketEntityEffect() {
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public byte getAmplifier() {
        return this.amplifier;
    }
}
