package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;

public class S3BPacketScoreboardObjective implements Packet
{
    private int field_149342_c;
    private IScoreObjectiveCriteria.EnumRenderType type;
    private String objectiveValue;
    private String objectiveName;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleScoreboardObjective(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.objectiveName = packetBuffer.readStringFromBuffer(16);
        this.field_149342_c = packetBuffer.readByte();
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            this.objectiveValue = packetBuffer.readStringFromBuffer(32);
            this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(packetBuffer.readStringFromBuffer(16));
        }
    }
    
    public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
        return this.type;
    }
    
    public S3BPacketScoreboardObjective(final ScoreObjective scoreObjective, final int field_149342_c) {
        this.objectiveName = scoreObjective.getName();
        this.objectiveValue = scoreObjective.getDisplayName();
        this.type = scoreObjective.getCriteria().getRenderType();
        this.field_149342_c = field_149342_c;
    }
    
    public String func_149337_d() {
        return this.objectiveValue;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S3BPacketScoreboardObjective() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.objectiveName);
        packetBuffer.writeByte(this.field_149342_c);
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            packetBuffer.writeString(this.objectiveValue);
            packetBuffer.writeString(this.type.func_178796_a());
        }
    }
    
    public String func_149339_c() {
        return this.objectiveName;
    }
    
    public int func_149338_e() {
        return this.field_149342_c;
    }
}
