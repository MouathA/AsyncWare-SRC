package net.minecraft.network.play.server;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.network.play.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.*;

public class S23PacketBlockChange implements Packet
{
    private IBlockState blockState;
    private BlockPos blockPosition;
    
    public S23PacketBlockChange(final World world, final BlockPos blockPosition) {
        this.blockPosition = blockPosition;
        this.blockState = world.getBlockState(blockPosition);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockChange(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
    
    public S23PacketBlockChange() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer());
    }
}
