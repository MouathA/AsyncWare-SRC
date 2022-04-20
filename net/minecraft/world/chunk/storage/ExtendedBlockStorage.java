package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;

public class ExtendedBlockStorage
{
    private NibbleArray skylightArray;
    private int tickRefCount;
    private char[] data;
    private int blockRefCount;
    private int yBase;
    private static final String __OBFID = "CL_00000375";
    private NibbleArray blocklightArray;
    
    public void setExtSkylightValue(final int n, final int n2, final int n3, final int n4) {
        this.skylightArray.set(n, n2, n3, n4);
    }
    
    public char[] getData() {
        return this.data;
    }
    
    public void setData(final char[] data) {
        this.data = data;
    }
    
    public int getExtSkylightValue(final int n, final int n2, final int n3) {
        return this.skylightArray.get(n, n2, n3);
    }
    
    public void set(final int n, final int n2, final int n3, final IBlockState blockState) {
        final Block block = this.get(n, n2, n3).getBlock();
        final Block block2 = blockState.getBlock();
        if (block != Blocks.air) {
            --this.blockRefCount;
            if (block.getTickRandomly()) {
                --this.tickRefCount;
            }
        }
        if (block2 != Blocks.air) {
            ++this.blockRefCount;
            if (block2.getTickRandomly()) {
                ++this.tickRefCount;
            }
        }
        this.data[n2 << 8 | n3 << 4 | n] = (char)Block.BLOCK_STATE_IDS.get(blockState);
    }
    
    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }
    
    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }
    
    public IBlockState get(final int n, final int n2, final int n3) {
        final IBlockState blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[n2 << 8 | n3 << 4 | n]);
        return (blockState != null) ? blockState : Blocks.air.getDefaultState();
    }
    
    public int getYLocation() {
        return this.yBase;
    }
    
    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }
    
    public void setSkylightArray(final NibbleArray skylightArray) {
        this.skylightArray = skylightArray;
    }
    
    public Block getBlockByExtId(final int n, final int n2, final int n3) {
        return this.get(n, n2, n3).getBlock();
    }
    
    public void setBlocklightArray(final NibbleArray blocklightArray) {
        this.blocklightArray = blocklightArray;
    }
    
    public int getExtBlockMetadata(final int n, final int n2, final int n3) {
        final IBlockState value = this.get(n, n2, n3);
        return value.getBlock().getMetaFromState(value);
    }
    
    public void setExtBlocklightValue(final int n, final int n2, final int n3, final int n4) {
        this.blocklightArray.set(n, n2, n3, n4);
    }
    
    public boolean getNeedsRandomTick() {
        return this.tickRefCount > 0;
    }
    
    public void removeInvalidBlocks() {
        final List objectList = Block.BLOCK_STATE_IDS.getObjectList();
        final int size = objectList.size();
        while (true) {
            final char c = this.data[0];
            if (c > '\0') {
                int n = 0;
                ++n;
                if (c < size) {
                    final IBlockState blockState = objectList.get(c);
                    if (blockState != null && blockState.getBlock().getTickRandomly()) {
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public ExtendedBlockStorage(final int yBase, final boolean b) {
        this.yBase = yBase;
        this.data = new char[4096];
        this.blocklightArray = new NibbleArray();
        if (b) {
            this.skylightArray = new NibbleArray();
        }
    }
    
    public int getExtBlocklightValue(final int n, final int n2, final int n3) {
        return this.blocklightArray.get(n, n2, n3);
    }
}
