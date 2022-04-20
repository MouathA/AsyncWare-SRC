package net.minecraft.network.play.client;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C07PacketPlayerDigging implements Packet
{
    private Action status;
    private EnumFacing facing;
    private BlockPos position;
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerDigging(this);
    }
    
    public C07PacketPlayerDigging(final Action status, final BlockPos position, final EnumFacing facing) {
        this.status = status;
        this.position = position;
        this.facing = facing;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public Action getStatus() {
        return this.status;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = (Action)packetBuffer.readEnumValue(Action.class);
        this.position = packetBuffer.readBlockPos();
        this.facing = EnumFacing.getFront(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C07PacketPlayerDigging() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getIndex());
    }
    
    public enum Action
    {
        DROP_ALL_ITEMS("DROP_ALL_ITEMS", 3);
        
        private static final Action[] $VALUES;
        
        DROP_ITEM("DROP_ITEM", 4), 
        STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2), 
        START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0), 
        ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK", 1), 
        RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5);
        
        private Action(final String s, final int n) {
        }
        
        static {
            $VALUES = new Action[] { Action.START_DESTROY_BLOCK, Action.ABORT_DESTROY_BLOCK, Action.STOP_DESTROY_BLOCK, Action.DROP_ALL_ITEMS, Action.DROP_ITEM, Action.RELEASE_USE_ITEM };
        }
    }
}
