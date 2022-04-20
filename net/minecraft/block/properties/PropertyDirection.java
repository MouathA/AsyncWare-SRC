package net.minecraft.block.properties;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;

public class PropertyDirection extends PropertyEnum
{
    public static PropertyDirection create(final String s, final Predicate predicate) {
        return create(s, Collections2.filter((Collection)Lists.newArrayList((Object[])EnumFacing.values()), predicate));
    }
    
    public static PropertyDirection create(final String s) {
        return create(s, Predicates.alwaysTrue());
    }
    
    protected PropertyDirection(final String s, final Collection collection) {
        super(s, EnumFacing.class, collection);
    }
    
    public static PropertyDirection create(final String s, final Collection collection) {
        return new PropertyDirection(s, collection);
    }
}
