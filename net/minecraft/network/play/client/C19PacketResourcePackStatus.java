package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C19PacketResourcePackStatus implements Packet
{
    private String hash;
    private Action status;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hash);
        packetBuffer.writeEnumValue(this.status);
    }
    
    public C19PacketResourcePackStatus() {
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.handleResourcePackStatus(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.hash = packetBuffer.readStringFromBuffer(40);
        this.status = (Action)packetBuffer.readEnumValue(Action.class);
    }
    
    public C19PacketResourcePackStatus(String substring, final Action status) {
        if (substring.length() > 40) {
            substring = substring.substring(0, 40);
        }
        this.hash = substring;
        this.status = status;
    }
    
    public enum Action
    {
        ACCEPTED("ACCEPTED", 3), 
        FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2), 
        DECLINED("DECLINED", 1);
        
        private static final Action[] $VALUES;
        
        SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0);
        
        private Action(final String s, final int n) {
        }
        
        static {
            $VALUES = new Action[] { Action.SUCCESSFULLY_LOADED, Action.DECLINED, Action.FAILED_DOWNLOAD, Action.ACCEPTED };
        }
    }
}
