package net.minecraft.item;

public enum EnumAction
{
    private static final EnumAction[] $VALUES;
    
    BLOCK("BLOCK", 3), 
    BOW("BOW", 4), 
    DRINK("DRINK", 2), 
    NONE("NONE", 0), 
    EAT("EAT", 1);
    
    private EnumAction(final String s, final int n) {
    }
    
    static {
        $VALUES = new EnumAction[] { EnumAction.NONE, EnumAction.EAT, EnumAction.DRINK, EnumAction.BLOCK, EnumAction.BOW };
    }
}
