package net.minecraft.network.play.server;

import net.minecraft.world.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S41PacketServerDifficulty implements Packet
{
    private EnumDifficulty difficulty;
    private boolean difficultyLocked;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
    }
    
    public S41PacketServerDifficulty() {
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
    }
    
    public S41PacketServerDifficulty(final EnumDifficulty difficulty, final boolean difficultyLocked) {
        this.difficulty = difficulty;
        this.difficultyLocked = difficultyLocked;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleServerDifficulty(this);
    }
    
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
