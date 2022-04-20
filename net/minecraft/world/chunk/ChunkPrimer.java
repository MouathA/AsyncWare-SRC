package net.minecraft.world.chunk;

import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class ChunkPrimer
{
    private final IBlockState defaultState;
    private final short[] data;
    
    public void setBlockState(final int n, final IBlockState blockState) {
        if (n >= 0 && n < this.data.length) {
            this.data[n] = (short)Block.BLOCK_STATE_IDS.get(blockState);
            return;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
    
    public IBlockState getBlockState(final int n) {
        if (n >= 0 && n < this.data.length) {
            final IBlockState blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[n]);
            return (blockState != null) ? blockState : this.defaultState;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
    
    public ChunkPrimer() {
        this.data = new short[65536];
        this.defaultState = Blocks.air.getDefaultState();
    }
    
    public void setBlockState(final int n, final int n2, final int n3, final IBlockState blockState) {
        this.setBlockState(n << 12 | n3 << 8 | n2, blockState);
    }
    
    public IBlockState getBlockState(final int n, final int n2, final int n3) {
        return this.getBlockState(n << 12 | n3 << 8 | n2);
    }
}
