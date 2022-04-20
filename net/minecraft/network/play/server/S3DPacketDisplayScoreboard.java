package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;

public class S3DPacketDisplayScoreboard implements Packet
{
    private int position;
    private String scoreName;
    
    public S3DPacketDisplayScoreboard() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.position);
        packetBuffer.writeString(this.scoreName);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDisplayScoreboard(this);
    }
    
    public int func_149371_c() {
        return this.position;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readByte();
        this.scoreName = packetBuffer.readStringFromBuffer(16);
    }
    
    public String func_149370_d() {
        return this.scoreName;
    }
    
    public S3DPacketDisplayScoreboard(final int position, final ScoreObjective scoreObjective) {
        this.position = position;
        if (scoreObjective == null) {
            this.scoreName = "";
        }
        else {
            this.scoreName = scoreObjective.getName();
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
