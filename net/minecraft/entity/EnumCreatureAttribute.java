package net.minecraft.entity;

public enum EnumCreatureAttribute
{
    ARTHROPOD("ARTHROPOD", 2), 
    UNDEAD("UNDEAD", 1), 
    UNDEFINED("UNDEFINED", 0);
    
    private static final EnumCreatureAttribute[] $VALUES;
    
    private EnumCreatureAttribute(final String s, final int n) {
    }
    
    static {
        $VALUES = new EnumCreatureAttribute[] { EnumCreatureAttribute.UNDEFINED, EnumCreatureAttribute.UNDEAD, EnumCreatureAttribute.ARTHROPOD };
    }
}
