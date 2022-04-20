package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class S0APacketUseBed implements Packet
{
    private BlockPos bedPos;
    private int playerID;
    
    public S0APacketUseBed() {
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUseBed(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.playerID = packetBuffer.readVarIntFromBuffer();
        this.bedPos = packetBuffer.readBlockPos();
    }
    
    public BlockPos getBedPosition() {
        return this.bedPos;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S0APacketUseBed(final EntityPlayer entityPlayer, final BlockPos bedPos) {
        this.playerID = entityPlayer.getEntityId();
        this.bedPos = bedPos;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.playerID);
        packetBuffer.writeBlockPos(this.bedPos);
    }
    
    public EntityPlayer getPlayer(final World world) {
        return (EntityPlayer)world.getEntityByID(this.playerID);
    }
}
