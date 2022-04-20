package net.minecraft.util;

import com.google.common.base.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;

public class Cartesian
{
    private static Iterable arraysAsLists(final Iterable iterable) {
        return Iterables.transform(iterable, (Function)new GetList(null));
    }
    
    private static Object[] createArray(final Class clazz, final int n) {
        return (Object[])Array.newInstance(clazz, n);
    }
    
    public static Iterable cartesianProduct(final Iterable iterable) {
        return arraysAsLists(cartesianProduct(Object.class, iterable));
    }
    
    private static Object[] toArray(final Class clazz, final Iterable iterable) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        return arrayList.toArray(createArray(clazz, arrayList.size()));
    }
    
    public static Iterable cartesianProduct(final Class clazz, final Iterable iterable) {
        return new Product(clazz, (Iterable[])toArray(Iterable.class, iterable), null);
    }
    
    static Object[] access$200(final Class clazz, final int n) {
        return createArray(clazz, n);
    }
    
    static class Product implements Iterable
    {
        private final Iterable[] iterables;
        private final Class clazz;
        
        Product(final Class clazz, final Iterable[] array, final Cartesian$1 object) {
            this(clazz, array);
        }
        
        @Override
        public Iterator iterator() {
            return (Iterator)((this.iterables.length <= 0) ? Collections.singletonList(Cartesian.access$200(this.clazz, 0)).iterator() : new ProductIterator(this.clazz, this.iterables, null));
        }
        
        private Product(final Class clazz, final Iterable[] iterables) {
            this.clazz = clazz;
            this.iterables = iterables;
        }
        
        static class ProductIterator extends UnmodifiableIterator
        {
            private final Object[] results;
            private final Iterator[] iterators;
            private int index;
            private final Iterable[] iterables;
            
            ProductIterator(final Class clazz, final Iterable[] array, final Cartesian$1 object) {
                this(clazz, array);
            }
            
            private ProductIterator(final Class clazz, final Iterable[] iterables) {
                this.index = -2;
                this.iterables = iterables;
                this.iterators = (Iterator[])Cartesian.access$200(Iterator.class, this.iterables.length);
                while (0 < this.iterables.length) {
                    this.iterators[0] = iterables[0].iterator();
                    int n = 0;
                    ++n;
                }
                this.results = Cartesian.access$200(clazz, this.iterators.length);
            }
            
            public Object next() {
                return this.next();
            }
            
            public Object[] next() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: if_icmpne       12
                //     4: new             Ljava/util/NoSuchElementException;
                //     7: dup            
                //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
                //    11: athrow         
                //    12: aload_0        
                //    13: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.index:I
                //    16: aload_0        
                //    17: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.iterators:[Ljava/util/Iterator;
                //    20: arraylength    
                //    21: if_icmpge       60
                //    24: aload_0        
                //    25: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.results:[Ljava/lang/Object;
                //    28: aload_0        
                //    29: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.index:I
                //    32: aload_0        
                //    33: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.iterators:[Ljava/util/Iterator;
                //    36: aload_0        
                //    37: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.index:I
                //    40: aaload         
                //    41: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //    46: aastore        
                //    47: aload_0        
                //    48: dup            
                //    49: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.index:I
                //    52: iconst_1       
                //    53: iadd           
                //    54: putfield        net/minecraft/util/Cartesian$Product$ProductIterator.index:I
                //    57: goto            12
                //    60: aload_0        
                //    61: getfield        net/minecraft/util/Cartesian$Product$ProductIterator.results:[Ljava/lang/Object;
                //    64: invokevirtual   [java/lang/Object.clone:()Ljava/lang/Object;
                //    67: checkcast       [Ljava/lang/Object;
                //    70: checkcast       [Ljava/lang/Object;
                //    73: checkcast       [Ljava/lang/Object;
                //    76: areturn        
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            private void endOfData() {
                this.index = -1;
                Arrays.fill(this.iterators, null);
                Arrays.fill(this.results, null);
            }
        }
    }
    
    static class GetList implements Function
    {
        private GetList() {
        }
        
        public Object apply(final Object o) {
            return this.apply((Object[])o);
        }
        
        GetList(final Cartesian$1 object) {
            this();
        }
        
        public List apply(final Object[] array) {
            return Arrays.asList((Object[])array);
        }
    }
}
