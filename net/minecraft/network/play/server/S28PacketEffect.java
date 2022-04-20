package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S28PacketEffect implements Packet
{
    private int soundData;
    private int soundType;
    private boolean serverWide;
    private BlockPos soundPos;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.soundType = packetBuffer.readInt();
        this.soundPos = packetBuffer.readBlockPos();
        this.soundData = packetBuffer.readInt();
        this.serverWide = packetBuffer.readBoolean();
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEffect(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.soundType);
        packetBuffer.writeBlockPos(this.soundPos);
        packetBuffer.writeInt(this.soundData);
        packetBuffer.writeBoolean(this.serverWide);
    }
    
    public BlockPos getSoundPos() {
        return this.soundPos;
    }
    
    public S28PacketEffect() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S28PacketEffect(final int soundType, final BlockPos soundPos, final int soundData, final boolean serverWide) {
        this.soundType = soundType;
        this.soundPos = soundPos;
        this.soundData = soundData;
        this.serverWide = serverWide;
    }
    
    public boolean isSoundServerwide() {
        return this.serverWide;
    }
    
    public int getSoundData() {
        return this.soundData;
    }
    
    public int getSoundType() {
        return this.soundType;
    }
}
