package net.minecraft.entity.ai.attributes;

import java.util.*;

public interface IAttributeInstance
{
    boolean hasModifier(final AttributeModifier p0);
    
    Collection getModifiersByOperation(final int p0);
    
    IAttribute getAttribute();
    
    AttributeModifier getModifier(final UUID p0);
    
    void removeAllModifiers();
    
    Collection func_111122_c();
    
    void setBaseValue(final double p0);
    
    void applyModifier(final AttributeModifier p0);
    
    void removeModifier(final AttributeModifier p0);
    
    double getBaseValue();
    
    double getAttributeValue();
}
