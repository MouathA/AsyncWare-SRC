package net.minecraft.network.play.client;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.world.*;

public class C02PacketUseEntity implements Packet
{
    private Action action;
    private Vec3 hitVec;
    private int entityId;
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUseEntity(this);
    }
    
    public C02PacketUseEntity() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        if (this.action == Action.INTERACT_AT) {
            this.hitVec = new Vec3(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
        }
    }
    
    public C02PacketUseEntity(final Entity entity, final Vec3 hitVec) {
        this(entity, Action.INTERACT_AT);
        this.hitVec = hitVec;
    }
    
    public Vec3 getHitVec() {
        return this.hitVec;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C02PacketUseEntity(final Entity entity, final Action action) {
        this.entityId = entity.getEntityId();
        this.action = action;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeEnumValue(this.action);
        if (this.action == Action.INTERACT_AT) {
            packetBuffer.writeFloat((float)this.hitVec.xCoord);
            packetBuffer.writeFloat((float)this.hitVec.yCoord);
            packetBuffer.writeFloat((float)this.hitVec.zCoord);
        }
    }
    
    public Entity getEntityFromWorld(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public enum Action
    {
        private static final Action[] $VALUES;
        
        ATTACK("ATTACK", 1), 
        INTERACT_AT("INTERACT_AT", 2), 
        INTERACT("INTERACT", 0);
        
        private Action(final String s, final int n) {
        }
        
        static {
            $VALUES = new Action[] { Action.INTERACT, Action.ATTACK, Action.INTERACT_AT };
        }
    }
}
