package net.minecraft.block.state;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class BlockStateBase implements IBlockState
{
    private static final Function MAP_ENTRY_TO_STRING;
    private int blockStateId;
    private static final Joiner COMMA_JOINER;
    private static final String __OBFID = "CL_00002032";
    private ResourceLocation blockLocation;
    private int metadata;
    private int blockId;
    
    public int getBlockStateId() {
        if (this.blockStateId < 0) {
            this.blockStateId = Block.getStateId(this);
        }
        return this.blockStateId;
    }
    
    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.getBlock());
        }
        return this.blockLocation;
    }
    
    @Override
    public IBlockState cycleProperty(final IProperty property) {
        return this.withProperty(property, (Comparable)cyclePropertyValue(property.getAllowedValues(), this.getValue(property)));
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.getBlock().getMetaFromState(this);
        }
        return this.metadata;
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.getBlock());
        }
        return this.blockId;
    }
    
    protected static Object cyclePropertyValue(final Collection collection, final Object o) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                return collection.iterator().next();
            }
        }
        return iterator.next();
    }
    
    static {
        COMMA_JOINER = Joiner.on(',');
        MAP_ENTRY_TO_STRING = (Function)new Function() {
            private static final String __OBFID = "CL_00002031";
            
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
            
            public String apply(final Map.Entry entry) {
                if (entry == null) {
                    return "<NULL>";
                }
                final IProperty property = entry.getKey();
                return property.getName() + "=" + property.getName((Comparable)entry.getValue());
            }
        };
    }
    
    public BlockStateBase() {
        this.blockId = -1;
        this.blockStateId = -1;
        this.metadata = -1;
        this.blockLocation = null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Block.blockRegistry.getNameForObject(this.getBlock()));
        if (!this.getProperties().isEmpty()) {
            sb.append("[");
            BlockStateBase.COMMA_JOINER.appendTo(sb, Iterables.transform((Iterable)this.getProperties().entrySet(), BlockStateBase.MAP_ENTRY_TO_STRING));
            sb.append("]");
        }
        return sb.toString();
    }
}
