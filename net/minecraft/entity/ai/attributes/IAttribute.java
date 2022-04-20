package net.minecraft.entity.ai.attributes;

public interface IAttribute
{
    IAttribute func_180372_d();
    
    double getDefaultValue();
    
    String getAttributeUnlocalizedName();
    
    boolean getShouldWatch();
    
    double clampValue(final double p0);
}
