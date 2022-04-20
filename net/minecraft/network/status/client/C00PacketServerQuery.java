package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C00PacketServerQuery implements Packet
{
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    public void processPacket(final INetHandlerStatusServer netHandlerStatusServer) {
        netHandlerStatusServer.processServerQuery(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusServer)netHandler);
    }
}
