package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private final List objectList;
    private final IdentityHashMap identityMap;
    private static final String __OBFID = "CL_00001203";
    
    public final Object getByValue(final int n) {
        return (n >= 0 && n < this.objectList.size()) ? this.objectList.get(n) : null;
    }
    
    public ObjectIntIdentityMap() {
        this.identityMap = new IdentityHashMap(512);
        this.objectList = Lists.newArrayList();
    }
    
    @Override
    public Iterator iterator() {
        return (Iterator)Iterators.filter((Iterator)this.objectList.iterator(), Predicates.notNull());
    }
    
    public void put(final Object o, final int n) {
        this.identityMap.put(o, n);
        while (this.objectList.size() <= n) {
            this.objectList.add(null);
        }
        this.objectList.set(n, o);
    }
    
    public List getObjectList() {
        return this.objectList;
    }
    
    public int get(final Object o) {
        final Integer n = this.identityMap.get(o);
        return (n == null) ? -1 : n;
    }
}
