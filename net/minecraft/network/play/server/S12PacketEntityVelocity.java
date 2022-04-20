package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S12PacketEntityVelocity implements Packet
{
    private int motionZ;
    private int entityID;
    private int motionX;
    private int motionY;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.motionX);
        packetBuffer.writeShort(this.motionY);
        packetBuffer.writeShort(this.motionZ);
    }
    
    public S12PacketEntityVelocity(final Entity entity) {
        this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.motionX = packetBuffer.readShort();
        this.motionY = packetBuffer.readShort();
        this.motionZ = packetBuffer.readShort();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityVelocity(this);
    }
    
    public int getMotionY() {
        return this.motionY;
    }
    
    public int getMotionX() {
        return this.motionX;
    }
    
    public int getMotionZ() {
        return this.motionZ;
    }
    
    public S12PacketEntityVelocity() {
    }
    
    public S12PacketEntityVelocity(final int entityID, double n, double n2, double n3) {
        this.entityID = entityID;
        final double n4 = 3.9;
        if (n < -n4) {
            n = -n4;
        }
        if (n2 < -n4) {
            n2 = -n4;
        }
        if (n3 < -n4) {
            n3 = -n4;
        }
        if (n > n4) {
            n = n4;
        }
        if (n2 > n4) {
            n2 = n4;
        }
        if (n3 > n4) {
            n3 = n4;
        }
        this.motionX = (int)(n * 8000.0);
        this.motionY = (int)(n2 * 8000.0);
        this.motionZ = (int)(n3 * 8000.0);
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
