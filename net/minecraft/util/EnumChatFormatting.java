package net.minecraft.util;

import java.util.regex.*;
import com.google.common.collect.*;
import java.util.*;

public enum EnumChatFormatting
{
    private final String name;
    
    RED("RED", 12, "RED", 'c', 12), 
    STRIKETHROUGH("STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true), 
    OBFUSCATED("OBFUSCATED", 16, "OBFUSCATED", 'k', true);
    
    private final int colorIndex;
    
    BLACK("BLACK", 0, "BLACK", '0', 0), 
    GRAY("GRAY", 7, "GRAY", '7', 7), 
    GOLD("GOLD", 6, "GOLD", '6', 6), 
    ITALIC("ITALIC", 20, "ITALIC", 'o', true), 
    BLUE("BLUE", 9, "BLUE", '9', 9), 
    BOLD("BOLD", 17, "BOLD", 'l', true), 
    DARK_GREEN("DARK_GREEN", 2, "DARK_GREEN", '2', 2), 
    DARK_PURPLE("DARK_PURPLE", 5, "DARK_PURPLE", '5', 5);
    
    private static final Map nameMapping;
    
    GREEN("GREEN", 10, "GREEN", 'a', 10), 
    LIGHT_PURPLE("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13);
    
    private final String controlString;
    
    YELLOW("YELLOW", 14, "YELLOW", 'e', 14);
    
    private final char formattingCode;
    
    DARK_RED("DARK_RED", 4, "DARK_RED", '4', 4);
    
    private static final EnumChatFormatting[] $VALUES;
    
    DARK_GRAY("DARK_GRAY", 8, "DARK_GRAY", '8', 8), 
    WHITE("WHITE", 15, "WHITE", 'f', 15), 
    AQUA("AQUA", 11, "AQUA", 'b', 11), 
    DARK_AQUA("DARK_AQUA", 3, "DARK_AQUA", '3', 3);
    
    private final boolean fancyStyling;
    
    UNDERLINE("UNDERLINE", 19, "UNDERLINE", 'n', true), 
    DARK_BLUE("DARK_BLUE", 1, "DARK_BLUE", '1', 1);
    
    private static final Pattern formattingCodePattern;
    
    RESET("RESET", 21, "RESET", 'r', -1);
    
    public static EnumChatFormatting getValueByName(final String s) {
        return (s == null) ? null : EnumChatFormatting.nameMapping.get(func_175745_c(s));
    }
    
    static {
        $VALUES = new EnumChatFormatting[] { EnumChatFormatting.BLACK, EnumChatFormatting.DARK_BLUE, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_RED, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.GOLD, EnumChatFormatting.GRAY, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.BLUE, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.RED, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.WHITE, EnumChatFormatting.OBFUSCATED, EnumChatFormatting.BOLD, EnumChatFormatting.STRIKETHROUGH, EnumChatFormatting.UNDERLINE, EnumChatFormatting.ITALIC, EnumChatFormatting.RESET };
        nameMapping = Maps.newHashMap();
        formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            EnumChatFormatting.nameMapping.put(func_175745_c(enumChatFormatting.name), enumChatFormatting);
            int n = 0;
            ++n;
        }
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final char c, final int n2) {
        this(s, n, s2, c, false, n2);
    }
    
    private EnumChatFormatting(final String s, final int n, final String name, final char formattingCode, final boolean fancyStyling, final int colorIndex) {
        this.name = name;
        this.formattingCode = formattingCode;
        this.fancyStyling = fancyStyling;
        this.colorIndex = colorIndex;
        this.controlString = "§" + formattingCode;
    }
    
    public static EnumChatFormatting func_175744_a(final int n) {
        if (n < 0) {
            return EnumChatFormatting.RESET;
        }
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            if (enumChatFormatting.getColorIndex() == n) {
                return enumChatFormatting;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private static String func_175745_c(final String s) {
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }
    
    public static String getTextWithoutFormattingCodes(final String s) {
        return (s == null) ? null : EnumChatFormatting.formattingCodePattern.matcher(s).replaceAll("");
    }
    
    public String getFriendlyName() {
        return this.name().toLowerCase();
    }
    
    public int getColorIndex() {
        return this.colorIndex;
    }
    
    public boolean isFancyStyling() {
        return this.fancyStyling;
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final char c, final boolean b) {
        this(s, n, s2, c, b, -1);
    }
    
    @Override
    public String toString() {
        return this.controlString;
    }
    
    public static Collection getValidValues(final boolean b, final boolean b2) {
        final ArrayList arrayList = Lists.newArrayList();
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            if ((enumChatFormatting != 0 || b) && (!enumChatFormatting.isFancyStyling() || b2)) {
                arrayList.add(enumChatFormatting.getFriendlyName());
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
}
