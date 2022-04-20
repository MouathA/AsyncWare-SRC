package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.scoreboard.*;

public class S3CPacketUpdateScore implements Packet
{
    private int value;
    private String name;
    private Action action;
    private String objective;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public Action getScoreAction() {
        return this.action;
    }
    
    public S3CPacketUpdateScore() {
        this.name = "";
        this.objective = "";
    }
    
    public String getObjectiveName() {
        return this.objective;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readStringFromBuffer(40);
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        this.objective = packetBuffer.readStringFromBuffer(16);
        if (this.action != Action.REMOVE) {
            this.value = packetBuffer.readVarIntFromBuffer();
        }
    }
    
    public S3CPacketUpdateScore(final String name, final ScoreObjective scoreObjective) {
        this.name = "";
        this.objective = "";
        this.name = name;
        this.objective = scoreObjective.getName();
        this.value = 0;
        this.action = Action.REMOVE;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeString(this.objective);
        if (this.action != Action.REMOVE) {
            packetBuffer.writeVarIntToBuffer(this.value);
        }
    }
    
    public int getScoreValue() {
        return this.value;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateScore(this);
    }
    
    public S3CPacketUpdateScore(final String name) {
        this.name = "";
        this.objective = "";
        this.name = name;
        this.objective = "";
        this.value = 0;
        this.action = Action.REMOVE;
    }
    
    public String getPlayerName() {
        return this.name;
    }
    
    public S3CPacketUpdateScore(final Score score) {
        this.name = "";
        this.objective = "";
        this.name = score.getPlayerName();
        this.objective = score.getObjective().getName();
        this.value = score.getScorePoints();
        this.action = Action.CHANGE;
    }
    
    public enum Action
    {
        REMOVE("REMOVE", 1), 
        CHANGE("CHANGE", 0);
        
        private static final Action[] $VALUES;
        
        private Action(final String s, final int n) {
        }
        
        static {
            $VALUES = new Action[] { Action.CHANGE, Action.REMOVE };
        }
    }
}
