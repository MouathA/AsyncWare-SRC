package net.minecraft.realms;

import net.minecraft.util.*;

public class RealmsSharedConstants
{
    public static char[] ILLEGAL_FILE_CHARACTERS;
    public static String VERSION_STRING;
    
    static {
        RealmsSharedConstants.VERSION_STRING = "1.8.8";
        RealmsSharedConstants.ILLEGAL_FILE_CHARACTERS = ChatAllowedCharacters.allowedCharactersArray;
    }
}
