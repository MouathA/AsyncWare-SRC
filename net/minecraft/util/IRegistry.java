package net.minecraft.util;

public interface IRegistry extends Iterable
{
    void putObject(final Object p0, final Object p1);
    
    Object getObject(final Object p0);
}
