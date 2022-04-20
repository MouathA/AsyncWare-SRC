package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S18PacketEntityTeleport implements Packet
{
    private byte yaw;
    private boolean onGround;
    private int entityId;
    private int posX;
    private byte pitch;
    private int posZ;
    private int posY;
    
    public int getY() {
        return this.posY;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public byte getPitch() {
        return this.pitch;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityTeleport(this);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public boolean getOnGround() {
        return this.onGround;
    }
    
    public S18PacketEntityTeleport(final Entity entity) {
        this.entityId = entity.getEntityId();
        this.posX = MathHelper.floor_double(entity.posX * 32.0);
        this.posY = MathHelper.floor_double(entity.posY * 32.0);
        this.posZ = MathHelper.floor_double(entity.posZ * 32.0);
        this.yaw = (byte)(entity.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entity.rotationPitch * 256.0f / 360.0f);
        this.onGround = entity.onGround;
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }
    
    public S18PacketEntityTeleport() {
    }
    
    public S18PacketEntityTeleport(final int entityId, final int posX, final int posY, final int posZ, final byte yaw, final byte pitch, final boolean onGround) {
        this.entityId = entityId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getZ() {
        return this.posZ;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }
}
