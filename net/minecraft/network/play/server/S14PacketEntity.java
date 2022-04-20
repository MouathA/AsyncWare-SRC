package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S14PacketEntity implements Packet
{
    protected boolean onGround;
    protected byte posX;
    protected int entityId;
    protected byte posZ;
    protected byte posY;
    protected byte yaw;
    protected boolean field_149069_g;
    protected byte pitch;
    
    public boolean func_149060_h() {
        return this.field_149069_g;
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }
    
    public byte func_149063_g() {
        return this.pitch;
    }
    
    public byte func_149061_d() {
        return this.posY;
    }
    
    public S14PacketEntity() {
    }
    
    public S14PacketEntity(final int entityId) {
        this.entityId = entityId;
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public byte func_149062_c() {
        return this.posX;
    }
    
    public byte func_149066_f() {
        return this.yaw;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
    
    public boolean getOnGround() {
        return this.onGround;
    }
    
    public byte func_149064_e() {
        return this.posZ;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMovement(this);
    }
    
    public static class S17PacketEntityLookMove extends S14PacketEntity
    {
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readByte();
            this.posY = packetBuffer.readByte();
            this.posZ = packetBuffer.readByte();
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
        
        public S17PacketEntityLookMove() {
            this.field_149069_g = true;
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.posX);
            packetBuffer.writeByte(this.posY);
            packetBuffer.writeByte(this.posZ);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }
        
        public S17PacketEntityLookMove(final int n, final byte posX, final byte posY, final byte posZ, final byte yaw, final byte pitch, final boolean onGround) {
            super(n);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.field_149069_g = true;
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
    }
    
    public static class S16PacketEntityLook extends S14PacketEntity
    {
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
        
        public S16PacketEntityLook(final int n, final byte yaw, final byte pitch, final boolean onGround) {
            super(n);
            this.yaw = yaw;
            this.pitch = pitch;
            this.field_149069_g = true;
            this.onGround = onGround;
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
        
        public S16PacketEntityLook() {
            this.field_149069_g = true;
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }
    }
    
    public static class S15PacketEntityRelMove extends S14PacketEntity
    {
        public S15PacketEntityRelMove(final int n, final byte posX, final byte posY, final byte posZ, final boolean onGround) {
            super(n);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.onGround = onGround;
        }
        
        public S15PacketEntityRelMove() {
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readByte();
            this.posY = packetBuffer.readByte();
            this.posZ = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.posX);
            packetBuffer.writeByte(this.posY);
            packetBuffer.writeByte(this.posZ);
            packetBuffer.writeBoolean(this.onGround);
        }
    }
}
