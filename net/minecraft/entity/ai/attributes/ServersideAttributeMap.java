package net.minecraft.entity.ai.attributes;

import com.google.common.collect.*;
import net.minecraft.server.management.*;
import java.util.*;

public class ServersideAttributeMap extends BaseAttributeMap
{
    private final Set attributeInstanceSet;
    protected final Map descriptionToAttributeInstanceMap;
    
    @Override
    public IAttributeInstance getAttributeInstanceByName(final String s) {
        return this.getAttributeInstanceByName(s);
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstanceByName(final String s) {
        IAttributeInstance attributeInstanceByName = super.getAttributeInstanceByName(s);
        if (attributeInstanceByName == null) {
            attributeInstanceByName = this.descriptionToAttributeInstanceMap.get(s);
        }
        return (ModifiableAttributeInstance)attributeInstanceByName;
    }
    
    @Override
    public IAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return this.getAttributeInstance(attribute);
    }
    
    public Set getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }
    
    @Override
    public void func_180794_a(final IAttributeInstance attributeInstance) {
        if (attributeInstance.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(attributeInstance);
        }
        final Iterator<IAttribute> iterator = this.field_180377_c.get((Object)attributeInstance.getAttribute()).iterator();
        while (iterator.hasNext()) {
            final ModifiableAttributeInstance attributeInstance2 = this.getAttributeInstance((IAttribute)iterator.next());
            if (attributeInstance2 != null) {
                attributeInstance2.flagForUpdate();
            }
        }
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
    }
    
    public ServersideAttributeMap() {
        this.attributeInstanceSet = Sets.newHashSet();
        this.descriptionToAttributeInstanceMap = new LowerStringMap();
    }
    
    @Override
    protected IAttributeInstance func_180376_c(final IAttribute attribute) {
        return new ModifiableAttributeInstance(this, attribute);
    }
    
    @Override
    public IAttributeInstance registerAttribute(final IAttribute attribute) {
        final IAttributeInstance registerAttribute = super.registerAttribute(attribute);
        if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), registerAttribute);
        }
        return registerAttribute;
    }
    
    public Collection getWatchedAttributes() {
        final HashSet hashSet = Sets.newHashSet();
        for (final IAttributeInstance attributeInstance : this.getAllAttributes()) {
            if (attributeInstance.getAttribute().getShouldWatch()) {
                hashSet.add(attributeInstance);
            }
        }
        return hashSet;
    }
}
