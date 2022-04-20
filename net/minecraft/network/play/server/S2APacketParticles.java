package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S2APacketParticles implements Packet
{
    private float xOffset;
    private float xCoord;
    private float yCoord;
    private int[] particleArguments;
    private float particleSpeed;
    private float zCoord;
    private int particleCount;
    private boolean longDistance;
    private float yOffset;
    private float zOffset;
    private EnumParticleTypes particleType;
    
    public S2APacketParticles(final EnumParticleTypes particleType, final boolean longDistance, final float xCoord, final float yCoord, final float zCoord, final float xOffset, final float yOffset, final float zOffset, final float particleSpeed, final int particleCount, final int... particleArguments) {
        this.particleType = particleType;
        this.longDistance = longDistance;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.particleSpeed = particleSpeed;
        this.particleCount = particleCount;
        this.particleArguments = particleArguments;
    }
    
    public boolean isLongDistance() {
        return this.longDistance;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.particleType.getParticleID());
        packetBuffer.writeBoolean(this.longDistance);
        packetBuffer.writeFloat(this.xCoord);
        packetBuffer.writeFloat(this.yCoord);
        packetBuffer.writeFloat(this.zCoord);
        packetBuffer.writeFloat(this.xOffset);
        packetBuffer.writeFloat(this.yOffset);
        packetBuffer.writeFloat(this.zOffset);
        packetBuffer.writeFloat(this.particleSpeed);
        packetBuffer.writeInt(this.particleCount);
        while (0 < this.particleType.getArgumentCount()) {
            packetBuffer.writeVarIntToBuffer(this.particleArguments[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.particleType = EnumParticleTypes.getParticleFromId(packetBuffer.readInt());
        if (this.particleType == null) {
            this.particleType = EnumParticleTypes.BARRIER;
        }
        this.longDistance = packetBuffer.readBoolean();
        this.xCoord = packetBuffer.readFloat();
        this.yCoord = packetBuffer.readFloat();
        this.zCoord = packetBuffer.readFloat();
        this.xOffset = packetBuffer.readFloat();
        this.yOffset = packetBuffer.readFloat();
        this.zOffset = packetBuffer.readFloat();
        this.particleSpeed = packetBuffer.readFloat();
        this.particleCount = packetBuffer.readInt();
        final int argumentCount = this.particleType.getArgumentCount();
        this.particleArguments = new int[argumentCount];
        while (0 < argumentCount) {
            this.particleArguments[0] = packetBuffer.readVarIntFromBuffer();
            int n = 0;
            ++n;
        }
    }
    
    public float getXOffset() {
        return this.xOffset;
    }
    
    public double getXCoordinate() {
        return this.xCoord;
    }
    
    public double getYCoordinate() {
        return this.yCoord;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public double getZCoordinate() {
        return this.zCoord;
    }
    
    public S2APacketParticles() {
    }
    
    public int getParticleCount() {
        return this.particleCount;
    }
    
    public float getYOffset() {
        return this.yOffset;
    }
    
    public EnumParticleTypes getParticleType() {
        return this.particleType;
    }
    
    public int[] getParticleArgs() {
        return this.particleArguments;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleParticles(this);
    }
    
    public float getParticleSpeed() {
        return this.particleSpeed;
    }
    
    public float getZOffset() {
        return this.zOffset;
    }
}
