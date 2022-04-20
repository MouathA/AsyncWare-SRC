package net.minecraft.item;

import net.minecraft.util.*;

public enum EnumRarity
{
    RARE("RARE", 2, EnumChatFormatting.AQUA, "Rare");
    
    private static final EnumRarity[] $VALUES;
    public final String rarityName;
    
    EPIC("EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    public final EnumChatFormatting rarityColor;
    
    UNCOMMON("UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"), 
    COMMON("COMMON", 0, EnumChatFormatting.WHITE, "Common");
    
    private EnumRarity(final String s, final int n, final EnumChatFormatting rarityColor, final String rarityName) {
        this.rarityColor = rarityColor;
        this.rarityName = rarityName;
    }
    
    static {
        $VALUES = new EnumRarity[] { EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC };
    }
}
