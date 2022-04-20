package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyBool extends PropertyHelper
{
    private final ImmutableSet allowedValues;
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName((Boolean)comparable);
    }
    
    @Override
    public Collection getAllowedValues() {
        return (Collection)this.allowedValues;
    }
    
    protected PropertyBool(final String s) {
        super(s, Boolean.class);
        this.allowedValues = ImmutableSet.of((Object)true, (Object)false);
    }
    
    public String getName(final Boolean b) {
        return b.toString();
    }
    
    public static PropertyBool create(final String s) {
        return new PropertyBool(s);
    }
}
