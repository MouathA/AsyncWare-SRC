package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C0CPacketInput implements Packet
{
    private boolean jumping;
    private float forwardSpeed;
    private boolean sneaking;
    private float strafeSpeed;
    
    public boolean isJumping() {
        return this.jumping;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
    
    public C0CPacketInput(final float strafeSpeed, final float forwardSpeed, final boolean jumping, final boolean sneaking) {
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.strafeSpeed);
        packetBuffer.writeFloat(this.forwardSpeed);
        if (this.jumping) {
            final byte b = 1;
        }
        if (this.sneaking) {
            final byte b2 = 2;
        }
        packetBuffer.writeByte(0);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.strafeSpeed = packetBuffer.readFloat();
        this.forwardSpeed = packetBuffer.readFloat();
        final byte byte1 = packetBuffer.readByte();
        this.jumping = ((byte1 & 0x1) > 0);
        this.sneaking = ((byte1 & 0x2) > 0);
    }
    
    public float getForwardSpeed() {
        return this.forwardSpeed;
    }
    
    public C0CPacketInput() {
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processInput(this);
    }
    
    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }
}
