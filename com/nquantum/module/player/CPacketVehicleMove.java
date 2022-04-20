package com.nquantum.module.player;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class CPacketVehicleMove implements Packet
{
    private float pitch;
    private float yaw;
    private double y;
    private double x;
    private double z;
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
    }
    
    public CPacketVehicleMove(final Entity entity) {
        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;
        this.yaw = entity.rotationYaw;
        this.pitch = entity.rotationPitch;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
    }
    
    public double getZ() {
        return this.z;
    }
    
    public double getY() {
        return this.y;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public CPacketVehicleMove() {
    }
    
    public double getX() {
        return this.x;
    }
    
    public float getPitch() {
        return this.pitch;
    }
}
