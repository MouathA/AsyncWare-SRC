package net.minecraft.network.play.server;

import java.io.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S20PacketEntityProperties implements Packet
{
    private final List field_149444_b;
    private int entityId;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.field_149444_b.size());
        for (final Snapshot snapshot : this.field_149444_b) {
            packetBuffer.writeString(snapshot.func_151409_a());
            packetBuffer.writeDouble(snapshot.func_151410_b());
            packetBuffer.writeVarIntToBuffer(snapshot.func_151408_c().size());
            for (final AttributeModifier attributeModifier : snapshot.func_151408_c()) {
                packetBuffer.writeUuid(attributeModifier.getID());
                packetBuffer.writeDouble(attributeModifier.getAmount());
                packetBuffer.writeByte(attributeModifier.getOperation());
            }
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        while (0 < packetBuffer.readInt()) {
            final String stringFromBuffer = packetBuffer.readStringFromBuffer(64);
            final double double1 = packetBuffer.readDouble();
            final ArrayList arrayList = Lists.newArrayList();
            while (0 < packetBuffer.readVarIntFromBuffer()) {
                arrayList.add(new AttributeModifier(packetBuffer.readUuid(), "Unknown synced attribute modifier", packetBuffer.readDouble(), packetBuffer.readByte()));
                int n = 0;
                ++n;
            }
            this.field_149444_b.add(new Snapshot(stringFromBuffer, double1, arrayList));
            int n2 = 0;
            ++n2;
        }
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public S20PacketEntityProperties(final int entityId, final Collection collection) {
        this.field_149444_b = Lists.newArrayList();
        this.entityId = entityId;
        for (final IAttributeInstance attributeInstance : collection) {
            this.field_149444_b.add(new Snapshot(attributeInstance.getAttribute().getAttributeUnlocalizedName(), attributeInstance.getBaseValue(), attributeInstance.func_111122_c()));
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityProperties(this);
    }
    
    public S20PacketEntityProperties() {
        this.field_149444_b = Lists.newArrayList();
    }
    
    public List func_149441_d() {
        return this.field_149444_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public class Snapshot
    {
        private final double field_151413_c;
        private final String field_151412_b;
        private final Collection field_151411_d;
        final S20PacketEntityProperties this$0;
        
        public double func_151410_b() {
            return this.field_151413_c;
        }
        
        public Snapshot(final S20PacketEntityProperties this$0, final String field_151412_b, final double field_151413_c, final Collection field_151411_d) {
            this.this$0 = this$0;
            this.field_151412_b = field_151412_b;
            this.field_151413_c = field_151413_c;
            this.field_151411_d = field_151411_d;
        }
        
        public Collection func_151408_c() {
            return this.field_151411_d;
        }
        
        public String func_151409_a() {
            return this.field_151412_b;
        }
    }
}
