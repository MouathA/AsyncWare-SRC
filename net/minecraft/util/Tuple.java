package net.minecraft.util;

public class Tuple
{
    private Object a;
    private Object b;
    
    public Object getFirst() {
        return this.a;
    }
    
    public Tuple(final Object a, final Object b) {
        this.a = a;
        this.b = b;
    }
    
    public Object getSecond() {
        return this.b;
    }
}
