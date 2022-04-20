package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S07PacketRespawn implements Packet
{
    private EnumDifficulty difficulty;
    private WorldType worldType;
    private int dimensionID;
    private WorldSettings.GameType gameType;
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    public WorldType getWorldType() {
        return this.worldType;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRespawn(this);
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public S07PacketRespawn(final int dimensionID, final EnumDifficulty difficulty, final WorldType worldType, final WorldSettings.GameType gameType) {
        this.dimensionID = dimensionID;
        this.difficulty = difficulty;
        this.gameType = gameType;
        this.worldType = worldType;
    }
    
    public S07PacketRespawn() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getDimensionID() {
        return this.dimensionID;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.dimensionID = packetBuffer.readInt();
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.gameType = WorldSettings.GameType.getByID(packetBuffer.readUnsignedByte());
        this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
        if (this.worldType == null) {
            this.worldType = WorldType.DEFAULT;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.dimensionID);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.gameType.getID());
        packetBuffer.writeString(this.worldType.getWorldTypeName());
    }
}
