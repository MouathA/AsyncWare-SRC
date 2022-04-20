package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.effect.*;

public class S2CPacketSpawnGlobalEntity implements Packet
{
    private int type;
    private int z;
    private int y;
    private int entityId;
    private int x;
    
    public S2CPacketSpawnGlobalEntity() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnGlobalEntity(this);
    }
    
    public int func_149051_d() {
        return this.x;
    }
    
    public int func_149050_e() {
        return this.y;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int func_149049_f() {
        return this.z;
    }
    
    public S2CPacketSpawnGlobalEntity(final Entity entity) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor_double(entity.posX * 32.0);
        this.y = MathHelper.floor_double(entity.posY * 32.0);
        this.z = MathHelper.floor_double(entity.posZ * 32.0);
        if (entity instanceof EntityLightningBolt) {
            this.type = 1;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
    }
    
    public int func_149052_c() {
        return this.entityId;
    }
    
    public int func_149053_g() {
        return this.type;
    }
}
