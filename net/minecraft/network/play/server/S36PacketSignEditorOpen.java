package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S36PacketSignEditorOpen implements Packet
{
    private BlockPos signPosition;
    
    public S36PacketSignEditorOpen(final BlockPos signPosition) {
        this.signPosition = signPosition;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.signPosition = packetBuffer.readBlockPos();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSignEditorOpen(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.signPosition);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S36PacketSignEditorOpen() {
    }
    
    public BlockPos getSignPosition() {
        return this.signPosition;
    }
}
