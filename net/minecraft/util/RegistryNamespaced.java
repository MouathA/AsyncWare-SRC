package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable
{
    protected final ObjectIntIdentityMap underlyingIntegerMap;
    protected final Map inverseObjectRegistry;
    
    public void register(final int n, final Object o, final Object o2) {
        this.underlyingIntegerMap.put(o2, n);
        this.putObject(o, o2);
    }
    
    public int getIDForObject(final Object o) {
        return this.underlyingIntegerMap.get(o);
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    @Override
    public Iterator iterator() {
        return this.underlyingIntegerMap.iterator();
    }
    
    public RegistryNamespaced() {
        this.underlyingIntegerMap = new ObjectIntIdentityMap();
        this.inverseObjectRegistry = (Map)((BiMap)this.registryObjects).inverse();
    }
    
    public Object getNameForObject(final Object o) {
        return this.inverseObjectRegistry.get(o);
    }
    
    @Override
    public Object getObject(final Object o) {
        return super.getObject(o);
    }
    
    @Override
    protected Map createUnderlyingMap() {
        return (Map)HashBiMap.create();
    }
    
    public Object getObjectById(final int n) {
        return this.underlyingIntegerMap.getByValue(n);
    }
}
