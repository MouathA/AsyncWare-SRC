package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S03PacketTimeUpdate implements Packet
{
    private long worldTime;
    private long totalWorldTime;
    
    public S03PacketTimeUpdate() {
    }
    
    public S03PacketTimeUpdate(final long totalWorldTime, final long worldTime, final boolean b) {
        this.totalWorldTime = totalWorldTime;
        this.worldTime = worldTime;
        if (!b) {
            this.worldTime = -this.worldTime;
            if (this.worldTime == 0L) {
                this.worldTime = -1L;
            }
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTimeUpdate(this);
    }
    
    public long getWorldTime() {
        return this.worldTime;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.totalWorldTime = packetBuffer.readLong();
        this.worldTime = packetBuffer.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.totalWorldTime);
        packetBuffer.writeLong(this.worldTime);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public long getTotalWorldTime() {
        return this.totalWorldTime;
    }
}
