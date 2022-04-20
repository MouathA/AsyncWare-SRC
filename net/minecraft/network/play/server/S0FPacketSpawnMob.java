package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S0FPacketSpawnMob implements Packet
{
    private byte headPitch;
    private int velocityX;
    private DataWatcher field_149043_l;
    private int velocityZ;
    private byte pitch;
    private List watcher;
    private int x;
    private int y;
    private int entityId;
    private int z;
    private int velocityY;
    private byte yaw;
    private int type;
    
    public int getY() {
        return this.y;
    }
    
    public S0FPacketSpawnMob(final EntityLivingBase entityLivingBase) {
        this.entityId = entityLivingBase.getEntityId();
        this.type = (byte)EntityList.getEntityID(entityLivingBase);
        this.x = MathHelper.floor_double(entityLivingBase.posX * 32.0);
        this.y = MathHelper.floor_double(entityLivingBase.posY * 32.0);
        this.z = MathHelper.floor_double(entityLivingBase.posZ * 32.0);
        this.yaw = (byte)(entityLivingBase.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entityLivingBase.rotationPitch * 256.0f / 360.0f);
        this.headPitch = (byte)(entityLivingBase.rotationYawHead * 256.0f / 360.0f);
        final double n = 3.9;
        double motionX = entityLivingBase.motionX;
        double motionY = entityLivingBase.motionY;
        double motionZ = entityLivingBase.motionZ;
        if (motionX < -n) {
            motionX = -n;
        }
        if (motionY < -n) {
            motionY = -n;
        }
        if (motionZ < -n) {
            motionZ = -n;
        }
        if (motionX > n) {
            motionX = n;
        }
        if (motionY > n) {
            motionY = n;
        }
        if (motionZ > n) {
            motionZ = n;
        }
        this.velocityX = (int)(motionX * 8000.0);
        this.velocityY = (int)(motionY * 8000.0);
        this.velocityZ = (int)(motionZ * 8000.0);
        this.field_149043_l = entityLivingBase.getDataWatcher();
    }
    
    public int getVelocityZ() {
        return this.velocityZ;
    }
    
    public int getEntityType() {
        return this.type;
    }
    
    public byte getPitch() {
        return this.pitch;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnMob(this);
    }
    
    public int getX() {
        return this.x;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getZ() {
        return this.z;
    }
    
    public List func_149027_c() {
        if (this.watcher == null) {
            this.watcher = this.field_149043_l.getAllWatched();
        }
        return this.watcher;
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    public S0FPacketSpawnMob() {
    }
    
    public int getVelocityY() {
        return this.velocityY;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type & 0xFF);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.headPitch);
        packetBuffer.writeShort(this.velocityX);
        packetBuffer.writeShort(this.velocityY);
        packetBuffer.writeShort(this.velocityZ);
        this.field_149043_l.writeTo(packetBuffer);
    }
    
    public byte getHeadPitch() {
        return this.headPitch;
    }
    
    public int getVelocityX() {
        return this.velocityX;
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = (packetBuffer.readByte() & 0xFF);
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.headPitch = packetBuffer.readByte();
        this.velocityX = packetBuffer.readShort();
        this.velocityY = packetBuffer.readShort();
        this.velocityZ = packetBuffer.readShort();
        this.watcher = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
}
