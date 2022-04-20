package net.minecraft.block.properties;

import java.util.*;

public interface IProperty
{
    Collection getAllowedValues();
    
    String getName(final Comparable p0);
    
    String getName();
    
    Class getValueClass();
}
