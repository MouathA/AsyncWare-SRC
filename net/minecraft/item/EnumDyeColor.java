package net.minecraft.item;

import net.minecraft.block.material.*;
import net.minecraft.util.*;

public enum EnumDyeColor implements IStringSerializable
{
    PURPLE("PURPLE", 10, 10, 5, "purple", "purple", MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE), 
    BROWN("BROWN", 12, 12, 3, "brown", "brown", MapColor.brownColor, EnumChatFormatting.GOLD);
    
    private final String name;
    
    MAGENTA("MAGENTA", 2, 2, 13, "magenta", "magenta", MapColor.magentaColor, EnumChatFormatting.AQUA), 
    ORANGE("ORANGE", 1, 1, 14, "orange", "orange", MapColor.adobeColor, EnumChatFormatting.GOLD), 
    RED("RED", 14, 14, 1, "red", "red", MapColor.redColor, EnumChatFormatting.DARK_RED), 
    PINK("PINK", 6, 6, 9, "pink", "pink", MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE);
    
    private static final EnumDyeColor[] $VALUES;
    private final int meta;
    
    LIGHT_BLUE("LIGHT_BLUE", 3, 3, 12, "light_blue", "lightBlue", MapColor.lightBlueColor, EnumChatFormatting.BLUE);
    
    private static final EnumDyeColor[] DYE_DMG_LOOKUP;
    
    WHITE("WHITE", 0, 0, 15, "white", "white", MapColor.snowColor, EnumChatFormatting.WHITE);
    
    private final String unlocalizedName;
    
    YELLOW("YELLOW", 4, 4, 11, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW), 
    GRAY("GRAY", 7, 7, 8, "gray", "gray", MapColor.grayColor, EnumChatFormatting.DARK_GRAY), 
    CYAN("CYAN", 9, 9, 6, "cyan", "cyan", MapColor.cyanColor, EnumChatFormatting.DARK_AQUA);
    
    private static final EnumDyeColor[] META_LOOKUP;
    
    BLACK("BLACK", 15, 15, 0, "black", "black", MapColor.blackColor, EnumChatFormatting.BLACK);
    
    private final int dyeDamage;
    private final MapColor mapColor;
    
    GREEN("GREEN", 13, 13, 2, "green", "green", MapColor.greenColor, EnumChatFormatting.DARK_GREEN), 
    LIME("LIME", 5, 5, 10, "lime", "lime", MapColor.limeColor, EnumChatFormatting.GREEN), 
    SILVER("SILVER", 8, 8, 7, "silver", "silver", MapColor.silverColor, EnumChatFormatting.GRAY);
    
    private final EnumChatFormatting chatColor;
    
    BLUE("BLUE", 11, 11, 4, "blue", "blue", MapColor.blueColor, EnumChatFormatting.DARK_BLUE);
    
    public static EnumDyeColor byDyeDamage(final int n) {
        if (0 >= EnumDyeColor.DYE_DMG_LOOKUP.length) {}
        return EnumDyeColor.DYE_DMG_LOOKUP[0];
    }
    
    public MapColor getMapColor() {
        return this.mapColor;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public int getMetadata() {
        return this.meta;
    }
    
    private EnumDyeColor(final String s, final int n, final int meta, final int dyeDamage, final String name, final String unlocalizedName, final MapColor mapColor, final EnumChatFormatting chatColor) {
        this.meta = meta;
        this.dyeDamage = dyeDamage;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.mapColor = mapColor;
        this.chatColor = chatColor;
    }
    
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    static {
        $VALUES = new EnumDyeColor[] { EnumDyeColor.WHITE, EnumDyeColor.ORANGE, EnumDyeColor.MAGENTA, EnumDyeColor.LIGHT_BLUE, EnumDyeColor.YELLOW, EnumDyeColor.LIME, EnumDyeColor.PINK, EnumDyeColor.GRAY, EnumDyeColor.SILVER, EnumDyeColor.CYAN, EnumDyeColor.PURPLE, EnumDyeColor.BLUE, EnumDyeColor.BROWN, EnumDyeColor.GREEN, EnumDyeColor.RED, EnumDyeColor.BLACK };
        META_LOOKUP = new EnumDyeColor[values().length];
        DYE_DMG_LOOKUP = new EnumDyeColor[values().length];
        final EnumDyeColor[] values = values();
        while (0 < values.length) {
            final EnumDyeColor enumDyeColor = values[0];
            EnumDyeColor.META_LOOKUP[enumDyeColor.getMetadata()] = enumDyeColor;
            EnumDyeColor.DYE_DMG_LOOKUP[enumDyeColor.getDyeDamage()] = enumDyeColor;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String toString() {
        return this.unlocalizedName;
    }
    
    public static EnumDyeColor byMetadata(final int n) {
        if (0 >= EnumDyeColor.META_LOOKUP.length) {}
        return EnumDyeColor.META_LOOKUP[0];
    }
    
    public int getDyeDamage() {
        return this.dyeDamage;
    }
}
