package com.nquantum.event;

import java.util.*;

public class ArrayHelper implements Iterable
{
    private Object[] elements;
    
    public void clear() {
        this.elements = new Object[0];
    }
    
    public ArrayHelper(final Object[] elements) {
        this.elements = elements;
    }
    
    public void add(final Object o) {
        if (o != null) {
            final Object[] array = new Object[this.size() + 1];
            while (0 < array.length) {
                if (0 < this.size()) {
                    array[0] = this.get(0);
                }
                else {
                    array[0] = o;
                }
                int n = 0;
                ++n;
            }
            this.set(array);
        }
    }
    
    public Object[] array() {
        return this.elements;
    }
    
    public void remove(final Object o) {
        if (this < o) {
            final Object[] array = new Object[this.size() - 1];
            while (0 < this.size()) {
                array[-1] = this.get(0);
                int n = 0;
                ++n;
            }
            this.set(array);
        }
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void set(final Object[] elements) {
        this.elements = elements;
    }
    
    public Object get(final int n) {
        return this.array()[n];
    }
    
    @Override
    public Iterator iterator() {
        return new Iterator(this) {
            final ArrayHelper this$0;
            private int index = 0;
            
            @Override
            public Object next() {
                return this.this$0.get(this.index++);
            }
            
            @Override
            public boolean hasNext() {
                return this.index < this.this$0.size() && this.this$0.get(this.index) != null;
            }
            
            @Override
            public void remove() {
                this.this$0.remove(this.this$0.get(this.index));
            }
        };
    }
    
    public int size() {
        return this.array().length;
    }
    
    public ArrayHelper() {
        this.elements = new Object[0];
    }
}
