package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public class BlockStateMapper
{
    private Map blockStateMap;
    private Set setBuiltInBlocks;
    
    public void registerBlockStateMapper(final Block block, final IStateMapper stateMapper) {
        this.blockStateMap.put(block, stateMapper);
    }
    
    public BlockStateMapper() {
        this.blockStateMap = Maps.newIdentityHashMap();
        this.setBuiltInBlocks = Sets.newIdentityHashSet();
    }
    
    public void registerBuiltInBlocks(final Block... array) {
        Collections.addAll(this.setBuiltInBlocks, array);
    }
    
    public Map putAllStateModelLocations() {
        final IdentityHashMap identityHashMap = Maps.newIdentityHashMap();
        for (final Block block : Block.blockRegistry) {
            if (!this.setBuiltInBlocks.contains(block)) {
                identityHashMap.putAll(((IStateMapper)Objects.firstNonNull(this.blockStateMap.get(block), (Object)new DefaultStateMapper())).putStateModelLocations(block));
            }
        }
        return identityHashMap;
    }
}
