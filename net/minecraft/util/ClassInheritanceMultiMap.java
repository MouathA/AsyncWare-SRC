package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public class ClassInheritanceMultiMap extends AbstractSet
{
    private final Set knownKeys;
    private static final Set field_181158_a;
    private final Map map;
    private final Class baseClass;
    private final List field_181745_e;
    
    @Override
    public Iterator iterator() {
        return (Iterator)(this.field_181745_e.isEmpty() ? Iterators.emptyIterator() : Iterators.unmodifiableIterator((Iterator)this.field_181745_e.iterator()));
    }
    
    private void func_181743_a(final Object o, final Class clazz) {
        final List<Object> list = this.map.get(clazz);
        if (list == null) {
            this.map.put(clazz, Lists.newArrayList(new Object[] { o }));
        }
        else {
            list.add(o);
        }
    }
    
    @Override
    public boolean add(final Object o) {
        for (final Class clazz : this.knownKeys) {
            if (clazz.isAssignableFrom(o.getClass())) {
                this.func_181743_a(o, clazz);
            }
        }
        return true;
    }
    
    protected void createLookup(final Class clazz) {
        ClassInheritanceMultiMap.field_181158_a.add(clazz);
        for (final Object next : this.field_181745_e) {
            if (clazz.isAssignableFrom(next.getClass())) {
                this.func_181743_a(next, clazz);
            }
        }
        this.knownKeys.add(clazz);
    }
    
    @Override
    public boolean remove(final Object o) {
        for (final Class clazz : this.knownKeys) {
            if (clazz.isAssignableFrom(o.getClass())) {
                final List list = this.map.get(clazz);
                if (list == null || list.remove(o)) {}
            }
        }
        return true;
    }
    
    public ClassInheritanceMultiMap(final Class baseClass) {
        this.map = Maps.newHashMap();
        this.knownKeys = Sets.newIdentityHashSet();
        this.field_181745_e = Lists.newArrayList();
        this.baseClass = baseClass;
        this.knownKeys.add(baseClass);
        this.map.put(baseClass, this.field_181745_e);
        final Iterator<Class> iterator = ClassInheritanceMultiMap.field_181158_a.iterator();
        while (iterator.hasNext()) {
            this.createLookup(iterator.next());
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        return Iterators.contains((Iterator)this.getByClass(o.getClass()).iterator(), o);
    }
    
    @Override
    public int size() {
        return this.field_181745_e.size();
    }
    
    public Iterable getByClass(final Class clazz) {
        return new Iterable(this, clazz) {
            final ClassInheritanceMultiMap this$0;
            final Class val$clazz;
            
            @Override
            public Iterator iterator() {
                final List list = ClassInheritanceMultiMap.access$000(this.this$0).get(this.this$0.func_181157_b(this.val$clazz));
                if (list == null) {
                    return (Iterator)Iterators.emptyIterator();
                }
                return (Iterator)Iterators.filter((Iterator)list.iterator(), this.val$clazz);
            }
        };
    }
    
    static Map access$000(final ClassInheritanceMultiMap classInheritanceMultiMap) {
        return classInheritanceMultiMap.map;
    }
    
    static {
        field_181158_a = Sets.newHashSet();
    }
    
    protected Class func_181157_b(final Class clazz) {
        if (this.baseClass.isAssignableFrom(clazz)) {
            if (!this.knownKeys.contains(clazz)) {
                this.createLookup(clazz);
            }
            return clazz;
        }
        throw new IllegalArgumentException("Don't know how to search for " + clazz);
    }
}
