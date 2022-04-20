package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class C0BPacketEntityAction implements Packet
{
    private Action action;
    private int auxData;
    private int entityID;
    
    public C0BPacketEntityAction() {
    }
    
    public int getAuxData() {
        return this.auxData;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEntityAction(this);
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action, final int auxData) {
        this.entityID = entity.getEntityId();
        this.action = action;
        this.auxData = auxData;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        this.auxData = packetBuffer.readVarIntFromBuffer();
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action) {
        this(entity, action, 0);
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.auxData);
    }
    
    public enum Action
    {
        OPEN_INVENTORY("OPEN_INVENTORY", 6), 
        RIDING_JUMP("RIDING_JUMP", 5), 
        STOP_SNEAKING("STOP_SNEAKING", 1);
        
        private static final Action[] $VALUES;
        
        STOP_SLEEPING("STOP_SLEEPING", 2), 
        STOP_SPRINTING("STOP_SPRINTING", 4), 
        START_SPRINTING("START_SPRINTING", 3), 
        START_SNEAKING("START_SNEAKING", 0);
        
        static {
            $VALUES = new Action[] { Action.START_SNEAKING, Action.STOP_SNEAKING, Action.STOP_SLEEPING, Action.START_SPRINTING, Action.STOP_SPRINTING, Action.RIDING_JUMP, Action.OPEN_INVENTORY };
        }
        
        private Action(final String s, final int n) {
        }
    }
}
