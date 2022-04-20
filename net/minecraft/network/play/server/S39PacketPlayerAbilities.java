package net.minecraft.network.play.server;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S39PacketPlayerAbilities implements Packet
{
    private float flySpeed;
    private boolean allowFlying;
    private float walkSpeed;
    private boolean flying;
    private boolean invulnerable;
    private boolean creativeMode;
    
    public void setAllowFlying(final boolean allowFlying) {
        this.allowFlying = allowFlying;
    }
    
    public void setWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public S39PacketPlayerAbilities(final PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }
    
    public void setInvulnerable(final boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
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
    
    public boolean isFlying() {
        return this.flying;
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public S39PacketPlayerAbilities() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public boolean isCreativeMode() {
        return this.creativeMode;
    }
    
    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerAbilities(this);
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    public boolean isInvulnerable() {
        return this.invulnerable;
    }
    
    public void setFlying(final boolean flying) {
        this.flying = flying;
    }
    
    public boolean isAllowFlying() {
        return this.allowFlying;
    }
}
