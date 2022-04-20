package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;

public class C14PacketTabComplete implements Packet
{
    private String message;
    private BlockPos targetBlock;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(32767);
        if (packetBuffer.readBoolean()) {
            this.targetBlock = packetBuffer.readBlockPos();
        }
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processTabComplete(this);
    }
    
    public C14PacketTabComplete(final String message, final BlockPos targetBlock) {
        this.message = message;
        this.targetBlock = targetBlock;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(StringUtils.substring(this.message, 0, 32767));
        final boolean b = this.targetBlock != null;
        packetBuffer.writeBoolean(b);
        if (b) {
            packetBuffer.writeBlockPos(this.targetBlock);
        }
    }
    
    public C14PacketTabComplete(final String s) {
        this(s, null);
    }
    
    public C14PacketTabComplete() {
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public BlockPos getTargetBlock() {
        return this.targetBlock;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
}
