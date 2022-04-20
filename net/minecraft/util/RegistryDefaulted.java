package net.minecraft.util;

public class RegistryDefaulted extends RegistrySimple
{
    private final Object defaultObject;
    
    public RegistryDefaulted(final Object defaultObject) {
        this.defaultObject = defaultObject;
    }
    
    @Override
    public Object getObject(final Object o) {
        final Object object = super.getObject(o);
        return (object == null) ? this.defaultObject : object;
    }
}
