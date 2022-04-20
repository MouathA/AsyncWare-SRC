package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class BaseAttributeMap
{
    protected final Map attributes;
    protected final Multimap field_180377_c;
    protected final Map attributesByName;
    
    public IAttributeInstance getAttributeInstanceByName(final String s) {
        return this.attributesByName.get(s);
    }
    
    public void func_180794_a(final IAttributeInstance attributeInstance) {
    }
    
    public BaseAttributeMap() {
        this.attributes = Maps.newHashMap();
        this.attributesByName = new LowerStringMap();
        this.field_180377_c = (Multimap)HashMultimap.create();
    }
    
    public IAttributeInstance registerAttribute(final IAttribute attribute) {
        if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        }
        final IAttributeInstance func_180376_c = this.func_180376_c(attribute);
        this.attributesByName.put(attribute.getAttributeUnlocalizedName(), func_180376_c);
        this.attributes.put(attribute, func_180376_c);
        for (IAttribute attribute2 = attribute.func_180372_d(); attribute2 != null; attribute2 = attribute2.func_180372_d()) {
            this.field_180377_c.put((Object)attribute2, (Object)attribute);
        }
        return func_180376_c;
    }
    
    protected abstract IAttributeInstance func_180376_c(final IAttribute p0);
    
    public IAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return this.attributes.get(attribute);
    }
    
    public void removeAttributeModifiers(final Multimap multimap) {
        for (final Map.Entry<String, V> entry : multimap.entries()) {
            final IAttributeInstance attributeInstanceByName = this.getAttributeInstanceByName(entry.getKey());
            if (attributeInstanceByName != null) {
                attributeInstanceByName.removeModifier((AttributeModifier)entry.getValue());
            }
        }
    }
    
    public void applyAttributeModifiers(final Multimap multimap) {
        for (final Map.Entry<String, V> entry : multimap.entries()) {
            final IAttributeInstance attributeInstanceByName = this.getAttributeInstanceByName(entry.getKey());
            if (attributeInstanceByName != null) {
                attributeInstanceByName.removeModifier((AttributeModifier)entry.getValue());
                attributeInstanceByName.applyModifier((AttributeModifier)entry.getValue());
            }
        }
    }
    
    public Collection getAllAttributes() {
        return this.attributesByName.values();
    }
}
