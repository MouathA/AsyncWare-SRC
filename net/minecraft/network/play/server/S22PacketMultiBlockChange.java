package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class S22PacketMultiBlockChange implements Packet
{
    private BlockUpdateData[] changedBlocks;
    private ChunkCoordIntPair chunkPosCoord;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMultiBlockChange(this);
    }
    
    static ChunkCoordIntPair access$000(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        return s22PacketMultiBlockChange.chunkPosCoord;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkPosCoord.chunkXPos);
        packetBuffer.writeInt(this.chunkPosCoord.chunkZPos);
        packetBuffer.writeVarIntToBuffer(this.changedBlocks.length);
        final BlockUpdateData[] changedBlocks = this.changedBlocks;
        while (0 < changedBlocks.length) {
            final BlockUpdateData blockUpdateData = changedBlocks[0];
            packetBuffer.writeShort(blockUpdateData.func_180089_b());
            packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(blockUpdateData.getBlockState()));
            int n = 0;
            ++n;
        }
    }
    
    public S22PacketMultiBlockChange(final int n, final short[] array, final Chunk chunk) {
        this.chunkPosCoord = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
        this.changedBlocks = new BlockUpdateData[n];
        while (0 < this.changedBlocks.length) {
            this.changedBlocks[0] = new BlockUpdateData(array[0], chunk);
            int n2 = 0;
            ++n2;
        }
    }
    
    public S22PacketMultiBlockChange() {
    }
    
    public BlockUpdateData[] getChangedBlocks() {
        return this.changedBlocks;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chunkPosCoord = new ChunkCoordIntPair(packetBuffer.readInt(), packetBuffer.readInt());
        this.changedBlocks = new BlockUpdateData[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.changedBlocks.length) {
            this.changedBlocks[0] = new BlockUpdateData(packetBuffer.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer()));
            int n = 0;
            ++n;
        }
    }
    
    public class BlockUpdateData
    {
        private final IBlockState blockState;
        final S22PacketMultiBlockChange this$0;
        private final short chunkPosCrammed;
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short chunkPosCrammed, final Chunk chunk) {
            this.this$0 = this$0;
            this.chunkPosCrammed = chunkPosCrammed;
            this.blockState = chunk.getBlockState(this.getPos());
        }
        
        public IBlockState getBlockState() {
            return this.blockState;
        }
        
        public BlockPos getPos() {
            return new BlockPos(S22PacketMultiBlockChange.access$000(this.this$0).getBlock(this.chunkPosCrammed >> 12 & 0xF, this.chunkPosCrammed & 0xFF, this.chunkPosCrammed >> 8 & 0xF));
        }
        
        public short func_180089_b() {
            return this.chunkPosCrammed;
        }
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short chunkPosCrammed, final IBlockState blockState) {
            this.this$0 = this$0;
            this.chunkPosCrammed = chunkPosCrammed;
            this.blockState = blockState;
        }
    }
}
