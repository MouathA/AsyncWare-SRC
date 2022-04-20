package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class S11PacketSpawnExperienceOrb implements Packet
{
    private int entityID;
    private int posY;
    private int posX;
    private int xpValue;
    private int posZ;
    
    public int getZ() {
        return this.posZ;
    }
    
    public int getY() {
        return this.posY;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeShort(this.xpValue);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnExperienceOrb(this);
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    public int getXPValue() {
        return this.xpValue;
    }
    
    public S11PacketSpawnExperienceOrb(final EntityXPOrb entityXPOrb) {
        this.entityID = entityXPOrb.getEntityId();
        this.posX = MathHelper.floor_double(entityXPOrb.posX * 32.0);
        this.posY = MathHelper.floor_double(entityXPOrb.posY * 32.0);
        this.posZ = MathHelper.floor_double(entityXPOrb.posZ * 32.0);
        this.xpValue = entityXPOrb.getXpValue();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.xpValue = packetBuffer.readShort();
    }
    
    public S11PacketSpawnExperienceOrb() {
    }
    
    public int getX() {
        return this.posX;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
