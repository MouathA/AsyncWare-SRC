package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C03PacketPlayer implements Packet
{
    protected boolean moving;
    protected float pitch;
    protected double x;
    protected boolean rotating;
    protected double z;
    protected float yaw;
    protected boolean onGround;
    protected double y;
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayer(this);
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.onGround = (packetBuffer.readUnsignedByte() != 0);
    }
    
    public boolean getRotating() {
        return this.rotating;
    }
    
    public C03PacketPlayer() {
    }
    
    public double getPositionY() {
        return this.y;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public double getPositionZ() {
        return this.z;
    }
    
    public void setMoving(final boolean moving) {
        this.moving = moving;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public double getPositionX() {
        return this.x;
    }
    
    public C03PacketPlayer(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isMoving() {
        return this.moving;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.onGround ? 1 : 0);
    }
    
    public double getX() {
        return this.x;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public static class C04PacketPlayerPosition extends C03PacketPlayer
    {
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        public C04PacketPlayerPosition(final double x, final double y, final double z, final boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.onGround = onGround;
            this.moving = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            super.readPacketData(packetBuffer);
        }
        
        public C04PacketPlayerPosition() {
            this.moving = true;
        }
    }
    
    public static class C05PacketPlayerLook extends C03PacketPlayer
    {
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        public C05PacketPlayerLook() {
            this.rotating = true;
        }
        
        public C05PacketPlayerLook(final float yaw, final float pitch, final boolean onGround) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = true;
        }
    }
    
    public static class C06PacketPlayerPosLook extends C03PacketPlayer
    {
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }
        
        public C06PacketPlayerPosLook() {
            this.moving = true;
            this.rotating = true;
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }
        
        public C06PacketPlayerPosLook(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = true;
            this.moving = true;
        }
    }
}
