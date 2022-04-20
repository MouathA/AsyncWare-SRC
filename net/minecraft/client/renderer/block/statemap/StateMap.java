package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import com.google.common.collect.*;

public class StateMap extends StateMapperBase
{
    private final String suffix;
    private final List ignored;
    private final IProperty name;
    
    StateMap(final IProperty property, final String s, final List list, final StateMap$1 object) {
        this(property, s, list);
    }
    
    @Override
    protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
        String s;
        if (this.name == null) {
            s = ((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock())).toString();
        }
        else {
            s = this.name.getName(linkedHashMap.remove(this.name));
        }
        if (this.suffix != null) {
            s += this.suffix;
        }
        final Iterator<IProperty> iterator = this.ignored.iterator();
        while (iterator.hasNext()) {
            linkedHashMap.remove(iterator.next());
        }
        return new ModelResourceLocation(s, this.getPropertyString(linkedHashMap));
    }
    
    private StateMap(final IProperty name, final String suffix, final List ignored) {
        this.name = name;
        this.suffix = suffix;
        this.ignored = ignored;
    }
    
    public static class Builder
    {
        private final List ignored;
        private String suffix;
        private IProperty name;
        
        public Builder withSuffix(final String suffix) {
            this.suffix = suffix;
            return this;
        }
        
        public StateMap build() {
            return new StateMap(this.name, this.suffix, this.ignored, null);
        }
        
        public Builder ignore(final IProperty... array) {
            Collections.addAll(this.ignored, array);
            return this;
        }
        
        public Builder withName(final IProperty name) {
            this.name = name;
            return this;
        }
        
        public Builder() {
            this.ignored = Lists.newArrayList();
        }
    }
}
