package net.minecraft.block.properties;

import com.google.common.base.*;

public abstract class PropertyHelper implements IProperty
{
    private final String name;
    private final Class valueClass;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final PropertyHelper propertyHelper = (PropertyHelper)o;
            return this.valueClass.equals(propertyHelper.valueClass) && this.name.equals(propertyHelper.name);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("name", (Object)this.name).add("clazz", (Object)this.valueClass).add("values", (Object)this.getAllowedValues()).toString();
    }
    
    @Override
    public int hashCode() {
        return 31 * this.valueClass.hashCode() + this.name.hashCode();
    }
    
    protected PropertyHelper(final String name, final Class valueClass) {
        this.valueClass = valueClass;
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Class getValueClass() {
        return this.valueClass;
    }
}
