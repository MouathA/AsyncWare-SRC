package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C13PacketPlayerAbilities implements Packet
{
    private boolean creativeMode;
    private boolean flying;
    private boolean allowFlying;
    private float walkSpeed;
    private boolean invulnerable;
    private float flySpeed;
    
    public void setInvulnerable(final boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    
    public void setFlying(final boolean flying) {
        this.flying = flying;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        if (this.isInvulnerable()) {
            final byte b = 1;
        }
        if (this.isFlying()) {
            final byte b2 = 2;
        }
        if (this.isAllowFlying()) {
            final byte b3 = 4;
        }
        if (this.isCreativeMode()) {
            final byte b4 = 8;
        }
        packetBuffer.writeByte(0);
        packetBuffer.writeFloat(this.flySpeed);
        packetBuffer.writeFloat(this.walkSpeed);
    }
    
    public void setWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public C13PacketPlayerAbilities(final PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }
    
    public boolean isCreativeMode() {
        return this.creativeMode;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public boolean isFlying() {
        return this.flying;
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
    
    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }
    
    public C13PacketPlayerAbilities() {
    }
    
    public boolean isInvulnerable() {
        return this.invulnerable;
    }
    
    public boolean isAllowFlying() {
        return this.allowFlying;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerAbilities(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        final byte byte1 = packetBuffer.readByte();
        this.setInvulnerable((byte1 & 0x1) > 0);
        this.setFlying((byte1 & 0x2) > 0);
        this.setAllowFlying((byte1 & 0x4) > 0);
        this.setCreativeMode((byte1 & 0x8) > 0);
        this.setFlySpeed(packetBuffer.readFloat());
        this.setWalkSpeed(packetBuffer.readFloat());
    }
    
    public void setAllowFlying(final boolean allowFlying) {
        this.allowFlying = allowFlying;
    }
}
