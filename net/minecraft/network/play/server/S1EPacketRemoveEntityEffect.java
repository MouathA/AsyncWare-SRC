package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;

public class S1EPacketRemoveEntityEffect implements Packet
{
    private int effectId;
    private int entityId;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRemoveEntityEffect(this);
    }
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public int getEffectId() {
        return this.effectId;
    }
    
    public S1EPacketRemoveEntityEffect(final int entityId, final PotionEffect potionEffect) {
        this.entityId = entityId;
        this.effectId = potionEffect.getPotionID();
    }
}
