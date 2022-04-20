package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C12PacketUpdateSign implements Packet
{
    private BlockPos pos;
    private IChatComponent[] lines;
    
    public BlockPos getPosition() {
        return this.pos;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.lines = new IChatComponent[4];
        while (true) {
            this.lines[0] = IChatComponent.Serializer.jsonToComponent(packetBuffer.readStringFromBuffer(384));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUpdateSign(this);
    }
    
    public C12PacketUpdateSign(final BlockPos pos, final IChatComponent[] array) {
        this.pos = pos;
        this.lines = new IChatComponent[] { array[0], array[1], array[2], array[3] };
    }
    
    public IChatComponent[] getLines() {
        return this.lines;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        while (true) {
            packetBuffer.writeString(IChatComponent.Serializer.componentToJson(this.lines[0]));
            int n = 0;
            ++n;
        }
    }
    
    public C12PacketUpdateSign() {
    }
}
