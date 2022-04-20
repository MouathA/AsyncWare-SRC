package net.minecraft.util;

import org.apache.commons.lang3.*;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced
{
    private final Object defaultValueKey;
    private Object defaultValue;
    
    public void validateKey() {
        Validate.notNull(this.defaultValueKey);
    }
    
    @Override
    public Object getObjectById(final int n) {
        final Object objectById = super.getObjectById(n);
        return (objectById == null) ? this.defaultValue : objectById;
    }
    
    public RegistryNamespacedDefaultedByKey(final Object defaultValueKey) {
        this.defaultValueKey = defaultValueKey;
    }
    
    @Override
    public Object getObject(final Object o) {
        final Object object = super.getObject(o);
        return (object == null) ? this.defaultValue : object;
    }
    
    @Override
    public void register(final int n, final Object o, final Object defaultValue) {
        if (this.defaultValueKey.equals(o)) {
            this.defaultValue = defaultValue;
        }
        super.register(n, o, defaultValue);
    }
}
