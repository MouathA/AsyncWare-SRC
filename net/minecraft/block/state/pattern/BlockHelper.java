package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class BlockHelper implements Predicate
{
    private final Block block;
    
    public static BlockHelper forBlock(final Block block) {
        return new BlockHelper(block);
    }
    
    public boolean apply(final Object o) {
        return this.apply((IBlockState)o);
    }
    
    private BlockHelper(final Block block) {
        this.block = block;
    }
    
    public boolean apply(final IBlockState blockState) {
        return blockState != null && blockState.getBlock() == this.block;
    }
}
