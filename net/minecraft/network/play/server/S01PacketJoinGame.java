package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S01PacketJoinGame implements Packet
{
    private int dimension;
    private int entityId;
    private WorldSettings.GameType gameType;
    private WorldType worldType;
    private boolean reducedDebugInfo;
    private boolean hardcoreMode;
    private int maxPlayers;
    private EnumDifficulty difficulty;
    
    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    public S01PacketJoinGame() {
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        final short unsignedByte = packetBuffer.readUnsignedByte();
        this.hardcoreMode = ((unsignedByte & 0x8) == 0x8);
        this.gameType = WorldSettings.GameType.getByID(unsignedByte & 0xFFFFFFF7);
        this.dimension = packetBuffer.readByte();
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.maxPlayers = packetBuffer.readUnsignedByte();
        this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
        if (this.worldType == null) {
            this.worldType = WorldType.DEFAULT;
        }
        this.reducedDebugInfo = packetBuffer.readBoolean();
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public S01PacketJoinGame(final int entityId, final WorldSettings.GameType gameType, final boolean hardcoreMode, final int dimension, final EnumDifficulty difficulty, final int maxPlayers, final WorldType worldType, final boolean reducedDebugInfo) {
        this.entityId = entityId;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gameType = gameType;
        this.maxPlayers = maxPlayers;
        this.hardcoreMode = hardcoreMode;
        this.worldType = worldType;
        this.reducedDebugInfo = reducedDebugInfo;
    }
    
    public boolean isHardcoreMode() {
        return this.hardcoreMode;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleJoinGame(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        int id = this.gameType.getID();
        if (this.hardcoreMode) {
            id |= 0x8;
        }
        packetBuffer.writeByte(id);
        packetBuffer.writeByte(this.dimension);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.maxPlayers);
        packetBuffer.writeString(this.worldType.getWorldTypeName());
        packetBuffer.writeBoolean(this.reducedDebugInfo);
    }
    
    public WorldType getWorldType() {
        return this.worldType;
    }
}
