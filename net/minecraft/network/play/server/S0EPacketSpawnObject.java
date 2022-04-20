package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import net.minecraft.util.*;

public class S0EPacketSpawnObject implements Packet
{
    private int speedZ;
    private int z;
    private int speedY;
    private int pitch;
    private int y;
    private int x;
    private int yaw;
    private int type;
    private int entityId;
    private int field_149020_k;
    private int speedX;
    
    public S0EPacketSpawnObject(final Entity entity, final int n) {
        this(entity, n, 0);
    }
    
    public int getYaw() {
        return this.yaw;
    }
    
    public S0EPacketSpawnObject() {
    }
    
    public void func_149002_g(final int field_149020_k) {
        this.field_149020_k = field_149020_k;
    }
    
    public void setSpeedZ(final int speedZ) {
        this.speedZ = speedZ;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setSpeedX(final int speedX) {
        this.speedX = speedX;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeInt(this.field_149020_k);
        if (this.field_149020_k > 0) {
            packetBuffer.writeShort(this.speedX);
            packetBuffer.writeShort(this.speedY);
            packetBuffer.writeShort(this.speedZ);
        }
    }
    
    public void setSpeedY(final int speedY) {
        this.speedY = speedY;
    }
    
    public int getPitch() {
        return this.pitch;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getSpeedZ() {
        return this.speedZ;
    }
    
    public int getSpeedY() {
        return this.speedY;
    }
    
    public S0EPacketSpawnObject(final Entity entity, final int type, final int field_149020_k) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor_double(entity.posX * 32.0);
        this.y = MathHelper.floor_double(entity.posY * 32.0);
        this.z = MathHelper.floor_double(entity.posZ * 32.0);
        this.pitch = MathHelper.floor_float(entity.rotationPitch * 256.0f / 360.0f);
        this.yaw = MathHelper.floor_float(entity.rotationYaw * 256.0f / 360.0f);
        this.type = type;
        this.field_149020_k = field_149020_k;
        if (field_149020_k > 0) {
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            final double n = 3.9;
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
            this.speedX = (int)(motionX * 8000.0);
            this.speedY = (int)(motionY * 8000.0);
            this.speedZ = (int)(motionZ * 8000.0);
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.pitch = packetBuffer.readByte();
        this.yaw = packetBuffer.readByte();
        this.field_149020_k = packetBuffer.readInt();
        if (this.field_149020_k > 0) {
            this.speedX = packetBuffer.readShort();
            this.speedY = packetBuffer.readShort();
            this.speedZ = packetBuffer.readShort();
        }
    }
    
    public int func_149009_m() {
        return this.field_149020_k;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnObject(this);
    }
    
    public void setZ(final int z) {
        this.z = z;
    }
    
    public int getSpeedX() {
        return this.speedX;
    }
    
    public int getX() {
        return this.x;
    }
}
