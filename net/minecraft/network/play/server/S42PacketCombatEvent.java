package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class S42PacketCombatEvent implements Packet
{
    public Event eventType;
    public String deathMessage;
    public int field_179774_b;
    public int field_179772_d;
    public int field_179775_c;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCombatEvent(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.eventType = (Event)packetBuffer.readEnumValue(Event.class);
        if (this.eventType == Event.END_COMBAT) {
            this.field_179772_d = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
        }
        else if (this.eventType == Event.ENTITY_DIED) {
            this.field_179774_b = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
            this.deathMessage = packetBuffer.readStringFromBuffer(32767);
        }
    }
    
    public S42PacketCombatEvent() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S42PacketCombatEvent(final CombatTracker combatTracker, final Event eventType) {
        this.eventType = eventType;
        final EntityLivingBase func_94550_c = combatTracker.func_94550_c();
        switch (S42PacketCombatEvent$1.$SwitchMap$net$minecraft$network$play$server$S42PacketCombatEvent$Event[eventType.ordinal()]) {
            case 1: {
                this.field_179772_d = combatTracker.func_180134_f();
                this.field_179775_c = ((func_94550_c == null) ? -1 : func_94550_c.getEntityId());
                break;
            }
            case 2: {
                this.field_179774_b = combatTracker.getFighter().getEntityId();
                this.field_179775_c = ((func_94550_c == null) ? -1 : func_94550_c.getEntityId());
                this.deathMessage = combatTracker.getDeathMessage().getUnformattedText();
                break;
            }
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.eventType);
        if (this.eventType == Event.END_COMBAT) {
            packetBuffer.writeVarIntToBuffer(this.field_179772_d);
            packetBuffer.writeInt(this.field_179775_c);
        }
        else if (this.eventType == Event.ENTITY_DIED) {
            packetBuffer.writeVarIntToBuffer(this.field_179774_b);
            packetBuffer.writeInt(this.field_179775_c);
            packetBuffer.writeString(this.deathMessage);
        }
    }
    
    public enum Event
    {
        END_COMBAT("END_COMBAT", 1);
        
        private static final Event[] $VALUES;
        
        ENTER_COMBAT("ENTER_COMBAT", 0), 
        ENTITY_DIED("ENTITY_DIED", 2);
        
        static {
            $VALUES = new Event[] { Event.ENTER_COMBAT, Event.END_COMBAT, Event.ENTITY_DIED };
        }
        
        private Event(final String s, final int n) {
        }
    }
}
