package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public class BlockStateHelper implements Predicate
{
    private final BlockState blockstate;
    private final Map propertyPredicates;
    
    public boolean apply(final IBlockState blockState) {
        if (blockState != null && blockState.getBlock().equals(this.blockstate.getBlock())) {
            for (final Map.Entry<IProperty, V> entry : this.propertyPredicates.entrySet()) {
                if (!((Predicate)entry.getValue()).apply((Object)blockState.getValue(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private BlockStateHelper(final BlockState blockstate) {
        this.propertyPredicates = Maps.newHashMap();
        this.blockstate = blockstate;
    }
    
    public static BlockStateHelper forBlock(final Block block) {
        return new BlockStateHelper(block.getBlockState());
    }
    
    public boolean apply(final Object o) {
        return this.apply((IBlockState)o);
    }
    
    public BlockStateHelper where(final IProperty property, final Predicate predicate) {
        if (!this.blockstate.getProperties().contains(property)) {
            throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
        }
        this.propertyPredicates.put(property, predicate);
        return this;
    }
}
