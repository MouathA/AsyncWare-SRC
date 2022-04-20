package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.border.*;
import net.minecraft.network.*;

public class S44PacketWorldBorder implements Packet
{
    private double centerX;
    private int warningDistance;
    private int warningTime;
    private long timeUntilTarget;
    private double centerZ;
    private double targetSize;
    private Action action;
    private int size;
    private double diameter;
    
    public S44PacketWorldBorder() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        switch (S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
            case 1: {
                packetBuffer.writeDouble(this.targetSize);
                break;
            }
            case 2: {
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                break;
            }
            case 3: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                break;
            }
            case 4: {
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                break;
            }
            case 5: {
                packetBuffer.writeVarIntToBuffer(this.warningTime);
                break;
            }
            case 6: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                packetBuffer.writeVarIntToBuffer(this.size);
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                packetBuffer.writeVarIntToBuffer(this.warningTime);
                break;
            }
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWorldBorder(this);
    }
    
    public void func_179788_a(final WorldBorder worldBorder) {
        switch (S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
            case 1: {
                worldBorder.setTransition(this.targetSize);
                break;
            }
            case 2: {
                worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                break;
            }
            case 3: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                break;
            }
            case 4: {
                worldBorder.setWarningDistance(this.warningDistance);
                break;
            }
            case 5: {
                worldBorder.setWarningTime(this.warningTime);
                break;
            }
            case 6: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                if (this.timeUntilTarget > 0L) {
                    worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                }
                else {
                    worldBorder.setTransition(this.targetSize);
                }
                worldBorder.setSize(this.size);
                worldBorder.setWarningDistance(this.warningDistance);
                worldBorder.setWarningTime(this.warningTime);
                break;
            }
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S44PacketWorldBorder(final WorldBorder worldBorder, final Action action) {
        this.action = action;
        this.centerX = worldBorder.getCenterX();
        this.centerZ = worldBorder.getCenterZ();
        this.diameter = worldBorder.getDiameter();
        this.targetSize = worldBorder.getTargetSize();
        this.timeUntilTarget = worldBorder.getTimeUntilTarget();
        this.size = worldBorder.getSize();
        this.warningDistance = worldBorder.getWarningDistance();
        this.warningTime = worldBorder.getWarningTime();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        switch (S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
            case 1: {
                this.targetSize = packetBuffer.readDouble();
                break;
            }
            case 2: {
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                break;
            }
            case 3: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                break;
            }
            case 4: {
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                break;
            }
            case 5: {
                this.warningTime = packetBuffer.readVarIntFromBuffer();
                break;
            }
            case 6: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                this.size = packetBuffer.readVarIntFromBuffer();
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                this.warningTime = packetBuffer.readVarIntFromBuffer();
                break;
            }
        }
    }
    
    public enum Action
    {
        LERP_SIZE("LERP_SIZE", 1), 
        SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5), 
        INITIALIZE("INITIALIZE", 3), 
        SET_CENTER("SET_CENTER", 2), 
        SET_SIZE("SET_SIZE", 0), 
        SET_WARNING_TIME("SET_WARNING_TIME", 4);
        
        private static final Action[] $VALUES;
        
        static {
            $VALUES = new Action[] { Action.SET_SIZE, Action.LERP_SIZE, Action.SET_CENTER, Action.INITIALIZE, Action.SET_WARNING_TIME, Action.SET_WARNING_BLOCKS };
        }
        
        private Action(final String s, final int n) {
        }
    }
}
