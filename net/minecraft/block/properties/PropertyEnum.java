package net.minecraft.block.properties;

import com.google.common.base.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class PropertyEnum extends PropertyHelper
{
    private final Map nameToValue;
    private final ImmutableSet allowedValues;
    
    public static PropertyEnum create(final String s, final Class clazz, final Predicate predicate) {
        return create(s, clazz, Collections2.filter((Collection)Lists.newArrayList(clazz.getEnumConstants()), predicate));
    }
    
    public static PropertyEnum create(final String s, final Class clazz, final Enum... array) {
        return create(s, clazz, Lists.newArrayList((Object[])array));
    }
    
    public static PropertyEnum create(final String s, final Class clazz) {
        return create(s, clazz, Predicates.alwaysTrue());
    }
    
    public static PropertyEnum create(final String s, final Class clazz, final Collection collection) {
        return new PropertyEnum(s, clazz, collection);
    }
    
    public String getName(final Enum enum1) {
        return ((IStringSerializable)enum1).getName();
    }
    
    protected PropertyEnum(final String s, final Class clazz, final Collection collection) {
        super(s, clazz);
        this.nameToValue = Maps.newHashMap();
        this.allowedValues = ImmutableSet.copyOf(collection);
        for (final Enum enum1 : collection) {
            final String name = ((IStringSerializable)enum1).getName();
            if (this.nameToValue.containsKey(name)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + name + "'");
            }
            this.nameToValue.put(name, enum1);
        }
    }
    
    @Override
    public Collection getAllowedValues() {
        return (Collection)this.allowedValues;
    }
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName((Enum)comparable);
    }
}
