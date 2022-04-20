package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C16PacketClientStatus implements Packet
{
    private EnumState status;
    
    public EnumState getStatus() {
        return this.status;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C16PacketClientStatus() {
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientStatus(this);
    }
    
    public C16PacketClientStatus(final EnumState status) {
        this.status = status;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = (EnumState)packetBuffer.readEnumValue(EnumState.class);
    }
    
    public enum EnumState
    {
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2), 
        PERFORM_RESPAWN("PERFORM_RESPAWN", 0);
        
        private static final EnumState[] $VALUES;
        
        REQUEST_STATS("REQUEST_STATS", 1);
        
        static {
            $VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
        }
        
        private EnumState(final String s, final int n) {
        }
    }
}
