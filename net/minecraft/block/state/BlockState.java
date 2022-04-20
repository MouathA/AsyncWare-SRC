package net.minecraft.block.state;

import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class BlockState
{
    private static final Joiner COMMA_JOINER;
    private static final Function GET_NAME_FUNC;
    private final ImmutableList validStates;
    private final Block block;
    private final ImmutableList properties;
    
    public ImmutableList getValidStates() {
        return this.validStates;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    static {
        COMMA_JOINER = Joiner.on(", ");
        GET_NAME_FUNC = (Function)new Function() {
            public Object apply(final Object o) {
                return this.apply((IProperty)o);
            }
            
            public String apply(final IProperty property) {
                return (property == null) ? "<NULL>" : property.getName();
            }
        };
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", (Object)Iterables.transform((Iterable)this.properties, BlockState.GET_NAME_FUNC)).toString();
    }
    
    public Collection getProperties() {
        return (Collection)this.properties;
    }
    
    public IBlockState getBaseState() {
        return (IBlockState)this.validStates.get(0);
    }
    
    private List getAllowedValues() {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.properties.size()) {
            arrayList.add(((IProperty)this.properties.get(0)).getAllowedValues());
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public BlockState(final Block block, final IProperty... array) {
        this.block = block;
        Arrays.sort(array, new Comparator(this) {
            final BlockState this$0;
            
            public int compare(final IProperty property, final IProperty property2) {
                return property.getName().compareTo(property2.getName());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((IProperty)o, (IProperty)o2);
            }
        });
        this.properties = ImmutableList.copyOf((Object[])array);
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<List> iterator = Cartesian.cartesianProduct(this.getAllowedValues()).iterator();
        while (iterator.hasNext()) {
            final Map map = MapPopulator.createMap((Iterable)this.properties, iterator.next());
            final StateImplementation stateImplementation = new StateImplementation(block, ImmutableMap.copyOf(map), null);
            linkedHashMap.put(map, stateImplementation);
            arrayList.add(stateImplementation);
        }
        final Iterator<StateImplementation> iterator2 = (Iterator<StateImplementation>)arrayList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().buildPropertyValueTable(linkedHashMap);
        }
        this.validStates = ImmutableList.copyOf((Collection)arrayList);
    }
    
    static class StateImplementation extends BlockStateBase
    {
        private final Block block;
        private ImmutableTable propertyValueTable;
        private final ImmutableMap properties;
        
        @Override
        public boolean equals(final Object o) {
            return this == o;
        }
        
        @Override
        public int hashCode() {
            return this.properties.hashCode();
        }
        
        @Override
        public IBlockState withProperty(final IProperty property, final Comparable comparable) {
            if (!this.properties.containsKey((Object)property)) {
                throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            if (!property.getAllowedValues().contains(comparable)) {
                throw new IllegalArgumentException("Cannot set property " + property + " to " + comparable + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
            }
            return (this.properties.get((Object)property) == comparable) ? this : ((IBlockState)this.propertyValueTable.get((Object)property, (Object)comparable));
        }
        
        StateImplementation(final Block block, final ImmutableMap immutableMap, final BlockState$1 function) {
            this(block, immutableMap);
        }
        
        @Override
        public ImmutableMap getProperties() {
            return this.properties;
        }
        
        public void buildPropertyValueTable(final Map map) {
            if (this.propertyValueTable != null) {
                throw new IllegalStateException();
            }
            final HashBasedTable create = HashBasedTable.create();
            for (final IProperty property : this.properties.keySet()) {
                for (final Comparable comparable : property.getAllowedValues()) {
                    if (comparable != this.properties.get((Object)property)) {
                        ((Table)create).put((Object)property, (Object)comparable, map.get(this.getPropertiesWithValue(property, comparable)));
                    }
                }
            }
            this.propertyValueTable = ImmutableTable.copyOf((Table)create);
        }
        
        @Override
        public Block getBlock() {
            return this.block;
        }
        
        private Map getPropertiesWithValue(final IProperty property, final Comparable comparable) {
            final HashMap hashMap = Maps.newHashMap((Map)this.properties);
            hashMap.put(property, comparable);
            return hashMap;
        }
        
        @Override
        public Collection getPropertyNames() {
            return Collections.unmodifiableCollection((Collection<?>)this.properties.keySet());
        }
        
        @Override
        public Comparable getValue(final IProperty property) {
            if (!this.properties.containsKey((Object)property)) {
                throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            return property.getValueClass().cast(this.properties.get((Object)property));
        }
        
        private StateImplementation(final Block block, final ImmutableMap properties) {
            this.block = block;
            this.properties = properties;
        }
    }
}
