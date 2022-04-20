package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S33PacketUpdateSign implements Packet
{
    private World world;
    private IChatComponent[] lines;
    private BlockPos blockPos;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateSign(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        while (true) {
            packetBuffer.writeChatComponent(this.lines[0]);
            int n = 0;
            ++n;
        }
    }
    
    public S33PacketUpdateSign() {
    }
    
    public BlockPos getPos() {
        return this.blockPos;
    }
    
    public S33PacketUpdateSign(final World world, final BlockPos blockPos, final IChatComponent[] array) {
        this.world = world;
        this.blockPos = blockPos;
        this.lines = new IChatComponent[] { array[0], array[1], array[2], array[3] };
    }
    
    public IChatComponent[] getLines() {
        return this.lines;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.lines = new IChatComponent[4];
        while (true) {
            this.lines[0] = packetBuffer.readChatComponent();
            int n = 0;
            ++n;
        }
    }
}
